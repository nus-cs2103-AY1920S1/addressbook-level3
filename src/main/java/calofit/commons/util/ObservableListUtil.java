package calofit.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.TransformationList;

/**
 * Utility class for working with JavaFx Observables.
 */
public class ObservableListUtil {
    /**
     * Constructs an ObservableList of the results of applying the given function to the given list.
     * No values are stored or cached, and the function will be called (and backing list accessed)
     * every time the returned list is accessed.
     * @param source Source list
     * @param mapper Mapping function
     * @param <T> Target type
     * @param <S> Source type
     * @return ObservableList of mapped values
     */
    public static <T, S> ObservableList<T> lazyMap(ObservableList<? extends S> source,
                                                   ObservableValue<? extends Function<? super S, ? extends T>> mapper) {
        return new MappedList<>(source, mapper);
    }

    public static <T, S> ObservableList<T> lazyMap(ObservableList<? extends S> source,
                                                   Function<? super S, ? extends T> mapper) {
        return new MappedList<>(source, new SimpleObjectProperty<>(mapper));
    }

    /**
     * Represents an list updater function, given a source value and previously cached value.
     * @param <T> Target type
     * @param <S> Source type
     */
    @FunctionalInterface
    public interface Updater<T, S> {
        /**
         * Given a value in the source list and its index, produce a mapped value.
         * The previous cached value is given to support reuse, or null if no cached value existed previously.
         * @param index List index
         * @param source Source value
         * @param target Previous cached value (or null if none)
         * @return New cached value
         */
        T update(int index, S source, T target);
    }

    /**
     * Constructs an ObservableList from a source list and a mapper function, both observable.
     * The returned list will update in response to changes in either the list or mapper.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <T> Target type
     * @param <S> Source type
     * @return Mapped list
     */
    public static <T, S> ObservableList<T> map(ObservableList<? extends S> source,
                                               ObservableValue<? extends Function<? super S, ? extends T>> mapper) {
        ObservableValue<Updater<T, ? super S>> updater = Bindings.createObjectBinding(() -> {
            Function<? super S, ? extends T> mapFunc = mapper.getValue();
            return (index, sourceVal, target) -> mapFunc.apply(sourceVal);
        }, mapper);
        return new BoundList<>(source, updater);
    }

    /**
     * Constructs an ObservableList from a source list and a mapper function, both observable.
     * The returned list will update in response to changes in either the list or mapper.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <T> Target type
     * @param <S> Source type
     * @return Mapped list
     */
    public static <T, S> ObservableList<T> map(ObservableList<? extends S> source,
                                               Function<? super S, ? extends T> mapper) {
        Updater<T, ? super S> updater = (index, sourceVal, target) -> mapper.apply(sourceVal);
        return new BoundList<>(source, updater);
    }

    /**
     * Constructs an ObservableList from a source list and a mapper function, both observable.
     * The mapping function will be provided both the source value and index.
     * The returned list will update in response to changes in either the list or mapper.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <T> Target type
     * @param <S> Source type
     * @return Mapped list
     */
    public static <T, S> ObservableList<T> mapWithIndex(
        ObservableList<? extends S> source,
        ObservableValue<? extends BiFunction<Integer, ? super S, ? extends T>> mapper) {
        ObservableValue<Updater<T, ? super S>> updater = Bindings.createObjectBinding(() -> {
            BiFunction<Integer, ? super S, ? extends T> mapFunc = mapper.getValue();
            return (index, sourceVal, target) -> mapFunc.apply(index, sourceVal);
        }, mapper);
        return new BoundList<>(source, updater);
    }

    /**
     * Constructs an ObservableList from an observable source list and a mapper function.
     * The mapping function will be provided both the source value and index.
     * The returned list will update in response to changes in the list.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <T> Target type
     * @param <S> Source type
     * @return Mapped list
     */
    public static <T, S> ObservableList<T> mapWithIndex(
        ObservableList<? extends S> source,
        BiFunction<Integer, ? super S, ? extends T> mapper) {
        Updater<T, ? super S> updater = (index, sourceVal, target) -> mapper.apply(index, sourceVal);
        return new BoundList<>(source, updater);
    }


    /**
     * Constructs an ObservableList from an observable source list and an updater function.
     * The mapping function will be provided the source value and index, and the previously cached value.
     * The returned list will update in response to changes in the list and updater.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param updater Updater function
     * @param <E> Target type
     * @param <F> Source type
     * @return Mapped list
     */
    public static <E, F> ObservableList<E> mapUpdate(
        ObservableList<? extends F> source,
        ObservableValue<? extends Updater<E, ? super F>> updater) {
        return new BoundList<E, F>(source, updater);
    }

    /**
     * Represents a mapped observable list, without no value caching.
     * @param <T> Mapped element type
     * @param <S> Source element type
     */
    private static class MappedList<T, S> extends TransformationList<T, S> {
        private ObjectProperty<Function<? super S, ? extends T>> mapper;

        private MappedList(ObservableList<? extends S> source,
                           ObservableValue<? extends Function<? super S, ? extends T>> mapper) {
            super(source);
            this.mapper = new SimpleObjectProperty<>();
            this.mapper.bind(mapper);
            this.mapper.addListener(observable -> onMapperChanged());
        }


        /**
         * Handles firing a whole-list update when the mapper changes.
         */
        private void onMapperChanged() {
            fireChange(new ListChangeListener.Change<T>(ObservableListUtil.MappedList.this) {
                private boolean ready = true;
                @Override
                public boolean next() {
                    boolean wasReady = ready;
                    ready = false;
                    return wasReady;
                }

                @Override
                public void reset() {
                    ready = true;
                }

                @Override
                public int getFrom() {
                    return 0;
                }

                @Override
                public boolean wasUpdated() {
                    return true;
                }

                @Override
                public int getTo() {
                    return size();
                }

                @Override
                public List<T> getRemoved() {
                    throw new IllegalStateException();
                }

                @Override
                protected int[] getPermutation() {
                    throw new IllegalStateException();
                }
            });
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<? extends S> c) {
            Function<? super S, ? extends T> func = mapper.get();
            fireChange(new WrappedChange<S, T>(this, c) {
                @Override
                protected T transformValue(S source) {
                    return func.apply(source);
                }
            });
        }

        @Override
        public int getSourceIndex(int index) {
            return index;
        }

        @Override
        public int getViewIndex(int index) {
            return index;
        }

        @Override
        public T get(int i) {
            //Re-evaluate function every time we call get().
            return mapper.get().apply(getSource().get(i));
        }

        @Override
        public int size() {
            return getSource().size();
        }

        public ObjectProperty<Function<? super S, ? extends T>> mapperProperty() {
            return mapper;
        }

    }

    /**
     * Represents a mapped list, wrapping a source list and caching the mapped values.
     * @param <T> Mapped element type
     * @param <S> Source element type
     */
    private static class BoundList<T, S> extends TransformationList<T, S> {
        private List<T> cachedValues;
        private ObjectProperty<ObservableListUtil.Updater<T, ? super S>> updater = new SimpleObjectProperty<>();

        private BoundList(ObservableList<? extends S> source,
                          ObservableValue<? extends ObservableListUtil.Updater<T, ? super S>> updaterValue) {
            this(source, updaterValue.getValue());
            this.updater.bind(updaterValue);
        }

        private BoundList(ObservableList<? extends S> source,
                          ObservableListUtil.Updater<T, ? super S> updater) {
            super(source);
            this.updater.set(updater);
            this.updater.addListener(this::onUpdaterChanged);

            this.cachedValues = new ArrayList<>();
            for (int i = 0; i < source.size(); i++) {
                cachedValues.add(updater.update(i, source.get(i), null));
            }
        }

        /**
         * Handles firing whole-list update changes when the updater function changes.
         * @param unused
         */
        private void onUpdaterChanged(Observable unused) {
            ObservableListUtil.Updater<T, ? super S> func = updater.get();
            List<? extends S> source = getSource();
            for (int i = 0; i < cachedValues.size(); i++) {
                T nextValue = func.update(i, source.get(i), cachedValues.get(i));
                cachedValues.set(i, nextValue);
            }
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<? extends S> c) {
            //Here, we try to reuse the original cachedValues list as much as we can.
            //We avoid allocating a target list, unless we encounter an add/remove operation.
            //Hence, either we are in the middle of the source list, and have a fresh target,
            //or we have went past the end and are adding items, so target == source.
            List<T> target = null;
            int changeEnd = 0;
            int elementDelta = 0;
            ObservableListUtil.Updater<T, ? super S> func = updater.get();
            List<? extends S> source = getSource();

            beginChange();
            while (c.next()) {
                if (target != null && target.size() < cachedValues.size()) {
                    //Target list exists, and we skipped ahead to c.getFrom() in the original
                    //so we catch up and add the unchanged elements.
                    target.addAll(cachedValues.subList(target.size(), c.getFrom()));
                }
                changeEnd = c.getTo();

                if (c.wasPermutated()) {
                    if (target != null) {
                        //Add directly to target, in permuted order.
                        IntStream.range(c.getFrom(), c.getTo())
                            .map(c::getPermutation)
                            .mapToObj(cachedValues::get)
                            .forEachOrdered(target::add);
                    } else {
                        //Copy out the sublist, then put it back in permuted order.
                        List<T> temp = new ArrayList<>(cachedValues.subList(c.getFrom(), c.getTo()));
                        for (int i = c.getFrom(); i < c.getTo(); i++) {
                            int nextIndex = c.getPermutation(i);
                            cachedValues.set(nextIndex, temp.get(i));
                        }
                    }
                    //Manually regenerate the permutation indices array.
                    //Original change value does not expose underlying array for reuse.
                    int[] perm = IntStream.range(c.getFrom(), c.getTo())
                        .map(c::getPermutation)
                        .toArray();
                    nextPermutation(c.getFrom(), c.getTo(), perm);
                } else if (c.wasUpdated()) {
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        T current = cachedValues.get(i);
                        T next = func.update(i, source.get(i), current);
                        if (target != null) {
                            target.add(next);
                        }
                        nextUpdate(i);
                    }
                } else {
                    if (target == null) {
                        if (c.getFrom() >= cachedValues.size()) {
                            //We can directly reuse cachedValues, as we've went past the end.
                            target = cachedValues;
                        } else {
                            //Modification in middle of previous list, we need to allocate a fresh copy.
                            target = new ArrayList<>(cachedValues.subList(0, c.getFrom()));
                        }
                    }
                    if (c.wasRemoved()) {
                        //Here, c.getFrom() < cachedValues.size().
                        //This means we aren't reusing cachedValues as target.
                        List<T> changed = cachedValues.subList(c.getFrom(), c.getFrom() + c.getRemovedSize());
                        nextRemove(c.getFrom(), new ArrayList<>(changed));
                        elementDelta += c.getRemovedSize();
                    }
                    if (c.wasAdded()) {
                        //Here, we add to the target, no matter if it's the copy or the original.
                        int pos = c.getFrom();
                        for (S sourceValue : c.getAddedSubList()) {
                            //Use updater function to generate fresh value
                            T nextValue = func.update(pos, sourceValue, null);
                            target.add(nextValue);
                            pos++;
                        }
                        nextAdd(c.getFrom(), c.getTo());
                        elementDelta -= c.getAddedSize();
                    }
                }
            }

            if (target != null) {
                //If we copied the list, update our current cache to point to the copy.
                //Still safe if we didn't copy but added to end, as then cachedValues == target.
                if (target != cachedValues) {
                    int validCachedSuffix = changeEnd + elementDelta;
                    if (validCachedSuffix < cachedValues.size()) {
                        target.addAll(cachedValues.subList(validCachedSuffix, cachedValues.size()));
                    }
                }
                this.cachedValues = target;
            }
            endChange();
        }

        @Override
        public int getSourceIndex(int index) {
            return index;
        }

        @Override
        public int getViewIndex(int index) {
            return index;
        }

        @Override
        public T get(int i) {
            return cachedValues.get(i);
        }

        @Override
        public int size() {
            return cachedValues.size();
        }
    }

    /**
     * Represents a concatenated pair of observable lists.
     * @param <E> Element type
     */
    private static class ConcatList<E> extends ObservableListBase<E> {

        private final ObservableList<? extends E> left;
        private final ObservableList<? extends E> right;

        private ConcatList(ObservableList<? extends E> left, ObservableList<? extends E> right) {
            this.left = left;
            this.right = right;

            left.addListener((ListChangeListener<? super E>) c -> this.onChanged(0, c));
            right.addListener((ListChangeListener<? super E>) c -> this.onChanged(left.size(), c));
        }

        /**
         * Handles change events on the sublists.
         * @param offset Offset of the target list
         * @param change Change event for the list
         */
        private void onChanged(int offset, ListChangeListener.Change<? extends E> change) {
            fireChange(new WrappedChange<E, E>(this, change) {
                @Override
                protected E transformValue(E source) {
                    return source;
                }

                @Override
                protected int transformIndex(int source) {
                    return source + offset;
                }
            });
        }


        @Override
        public E get(int i) {
            if (i < left.size()) {
                return left.get(i);
            } else {
                return right.get(i - left.size());
            }
        }

        @Override
        public int size() {
            return left.size() + right.size();
        }
    }

    public static <E> ObservableList<E> concat(ObservableList<? extends E> left, ObservableList<? extends E> right) {
        return new ConcatList<>(left, right);
    }

    /**
     * Wrapper for a {@link ListChangeListener.Change} in the source list.
     * @param <S> Source element type
     * @param <T> Target element type
     */
    private abstract static class WrappedChange<S, T> extends ListChangeListener.Change<T> {
        private final ListChangeListener.Change<? extends S> c;
        private List<T> cachedRemovals;
        private int[] cachedPermutation;

        public WrappedChange(ObservableList<T> source, ListChangeListener.Change<? extends S> c) {
            super(source);
            this.c = c;
        }

        @Override
        public boolean next() {
            this.cachedPermutation = null;
            this.cachedRemovals = null;
            return c.next();
        }

        @Override
        public void reset() {
            c.reset();
        }

        @Override
        public int getFrom() {
            return transformIndex(c.getFrom());
        }

        @Override
        public int getTo() {
            return transformIndex(c.getTo());
        }

        @Override
        public boolean wasUpdated() {
            return c.wasUpdated();
        }

        @Override
        public List<T> getRemoved() {
            if (cachedRemovals == null) {
                cachedRemovals = c.getRemoved().stream()
                    .map(this::transformValue)
                    .collect(Collectors.toUnmodifiableList());
            }
            return cachedRemovals;
        }

        protected abstract T transformValue(S source);
        protected int transformIndex(int source) {
            return source;
        }

        @Override
        public boolean wasPermutated() {
            return c.wasPermutated();
        }

        @Override
        protected int[] getPermutation() {
            if (cachedPermutation == null) {
                cachedPermutation = new int[getTo() - getFrom()];
                for (int i = getFrom(); i < getTo(); i++) {
                    cachedPermutation[i - getTo()] = transformIndex(c.getPermutation(i));
                }
            }
            return cachedPermutation;
        }
    }

    /**
     * Computes the sum of an observable list of doubles.
     * This method will watch for list updates and update the sum accordingly.
     * @param values List of values to sum
     * @return Sum of values
     */
    public static DoubleBinding sum(ObservableList<Double> values) {
        return Bindings.createDoubleBinding(() -> {
            double total = 0;
            for (double val : values) {
                total += val;
            }
            return total;
        }, values);
    }
}
