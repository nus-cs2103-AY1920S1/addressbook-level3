package calofit.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

/**
 * Utility class for working with JavaFx Observables.
 */
public class ObservableUtil {
    /**
     * Constructs an ObservableList of the results of applying the given function to the given list.
     * No values are stored or cached, and the function will be called (and backing list accessed)
     * every time the returned list is accessed.
     * @param source Source list
     * @param mapper Mapping function
     * @param <E> Target type
     * @param <F> Source type
     * @return ObservableList of mapped values
     */
    public static <E, F> ObservableList<E> lazyMap(ObservableList<? extends F> source,
                                                  ObservableValue<? extends Function<? super F, ? extends E>> mapper) {
        return new MappedList<>(source, mapper);
    }

    /**
     * Represents an list updater function, given a source value and previously cached value.
     * @param <E> Target type
     * @param <F> Source type
     */
    @FunctionalInterface
    public interface Updater<E, F> {
        /**
         * Given a value in the source list and its index, produce a mapped value.
         * The previous cached value is given to support reuse, or null if no cached value existed previously.
         * @param index List index
         * @param source Source value
         * @param target Previous cached value (or null if none)
         * @return New cached value
         */
        E update(int index, F source, E target);
    }

    /**
     * Constructs an ObservableList from a source list and a mapper function, both observable.
     * The returned list will update in response to changes in either the list or mapper.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <E> Target type
     * @param <F> Source type
     * @return Mapped list
     */
    public static <E, F> ObservableList<E> map(ObservableList<? extends F> source,
                                              ObservableValue<? extends Function<? super F, ? extends E>> mapper) {
        ObservableValue<Updater<E, ? super F>> updater = Bindings.createObjectBinding(() -> {
            Function<? super F, ? extends E> mapFunc = mapper.getValue();
            return (index, sourceVal, target) -> mapFunc.apply(sourceVal);
        }, mapper);
        return new BoundList<>(source, updater);
    }

    /**
     * Constructs an ObservableList from a source list and a mapper function, both observable.
     * The mapping function will be provided both the source value and index.
     * The returned list will update in response to changes in either the list or mapper.
     * The results will be cached to avoid re-evaluation.
     * @param source Source list
     * @param mapper Mapping function
     * @param <E> Target type
     * @param <F> Source type
     * @return Mapped list
     */
    public static <E, F> ObservableList<E> mapWithIndex(
        ObservableList<? extends F> source,
        ObservableValue<? extends BiFunction<Integer, ? super F, ? extends E>> mapper) {
        ObservableValue<Updater<E, ? super F>> updater = Bindings.createObjectBinding(() -> {
            BiFunction<Integer, ? super F, ? extends E> mapFunc = mapper.getValue();
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
     * @param <E> Target type
     * @param <F> Source type
     * @return Mapped list
     */
    public static <E, F> ObservableList<E> mapWithIndex(
        ObservableList<? extends F> source,
        BiFunction<Integer, ? super F, ? extends E> mapper) {
        Updater<E, ? super F> updater = (index, sourceVal, target) -> mapper.apply(index, sourceVal);
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
     * @param <E> Mapped element type
     * @param <F> Source element type
     */
    private static class MappedList<E, F> extends TransformationList<E, F> {
        private ObjectProperty<Function<? super F, ? extends E>> mapper;

        private MappedList(ObservableList<? extends F> source,
                           ObservableValue<? extends Function<? super F, ? extends E>> mapper) {
            super(source);
            this.mapper.bind(mapper);
            this.mapper.addListener(observable -> onMapperChanged());
        }


        /**
         * Handles firing a whole-list update when the mapper changes.
         */
        private void onMapperChanged() {
            fireChange(new ListChangeListener.Change<E>(MappedList.this) {
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
                public List<E> getRemoved() {
                    throw new IllegalStateException();
                }

                @Override
                protected int[] getPermutation() {
                    throw new IllegalStateException();
                }
            });
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<? extends F> c) {
            fireChange(new MappedChange(c));
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
        public E get(int i) {
            //Re-evaluate function every time we call get().
            return mapper.get().apply(getSource().get(i));
        }

        @Override
        public int size() {
            return getSource().size();
        }

        public ObjectProperty<Function<? super F, ? extends E>> mapperProperty() {
            return mapper;
        }

        /**
         * Wrapper for a {@link ListChangeListener.Change} in the source list.
         */
        private class MappedChange extends ListChangeListener.Change<E> {
            private final ListChangeListener.Change<? extends F> c;
            private List<E> cachedRemovals;
            private int[] cachedPermutation;

            public MappedChange(ListChangeListener.Change<? extends F> c) {
                super(MappedList.this);
                this.c = c;
            }

            @Override
            public boolean next() {
                this.cachedPermutation = null;
                return c.next();
            }

            @Override
            public void reset() {
                c.reset();
            }

            @Override
            public int getFrom() {
                return c.getFrom();
            }

            @Override
            public int getTo() {
                return c.getTo();
            }

            @Override
            public List<E> getRemoved() {
                if (cachedRemovals == null) {
                    cachedRemovals = c.getRemoved().stream()
                        .map(mapper.get())
                        .collect(Collectors.toUnmodifiableList());
                }
                return cachedRemovals;
            }

            @Override
            protected int[] getPermutation() {
                if (cachedPermutation == null) {
                    cachedPermutation = new int[getTo() - getFrom()];
                    for (int i = getFrom(); i < getTo(); i++) {
                        cachedPermutation[i] = c.getPermutation(i);
                    }
                }
                return cachedPermutation;
            }
        }
    }

    /**
     * Represents a mapped list, wrapping a source list and caching the mapped values.
     * @param <E> Mapped element type
     * @param <F> Source element type
     */
    private static class BoundList<E, F> extends TransformationList<E, F> {
        private List<E> cachedValues;
        private ObjectProperty<ObservableUtil.Updater<E, ? super F>> updater = new SimpleObjectProperty<>();

        private BoundList(ObservableList<? extends F> source,
                          ObservableValue<? extends ObservableUtil.Updater<E, ? super F>> updaterValue) {
            super(source);
            this.updater.bind(updaterValue);
            this.updater.addListener(this::onUpdaterChanged);

            ObservableUtil.Updater<E, ? super F> func = updaterValue.getValue();
            this.cachedValues = new ArrayList<>();
            for (int i = 0; i < source.size(); i++) {
                cachedValues.add(func.update(i, source.get(i), null));
            }
        }

        private BoundList(ObservableList<? extends F> source,
                          ObservableUtil.Updater<E, ? super F> updater) {
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
            ObservableUtil.Updater<E, ? super F> func = updater.get();
            List<? extends F> source = getSource();
            for (int i = 0; i < cachedValues.size(); i++) {
                E nextValue = func.update(i, source.get(i), cachedValues.get(i));
                cachedValues.set(i, nextValue);
            }
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<? extends F> c) {
            //Here, we try to reuse the original cachedValues list as much as we can.
            //We avoid allocating a target list, unless we encounter an add/remove operation.
            //Hence, either we are in the middle of the source list, and have a fresh target,
            //or we have went past the end and are adding items, so target == source.
            List<E> target = null;
            ObservableUtil.Updater<E, ? super F> func = updater.get();
            List<? extends F> source = getSource();

            beginChange();
            while (c.next()) {
                if (target != null && target.size() < cachedValues.size()) {
                    //Target list exists, and we skipped ahead to c.getFrom() in the original
                    //so we catch up and add the unchanged elements.
                    target.addAll(cachedValues.subList(target.size(), c.getFrom()));
                }

                if (c.wasPermutated()) {
                    if (target != null) {
                        //Add directly to target, in permuted order.
                        IntStream.range(c.getFrom(), c.getTo())
                            .map(c::getPermutation)
                            .mapToObj(cachedValues::get)
                            .forEachOrdered(target::add);
                    } else {
                        //Copy out the sublist, then put it back in permuted order.
                        List<E> temp = new ArrayList<>(cachedValues.subList(c.getFrom(), c.getTo()));
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
                        E current = cachedValues.get(i);
                        E next = func.update(i, source.get(i), current);
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
                        List<E> changed = cachedValues.subList(c.getFrom(), c.getTo());
                        nextRemove(c.getFrom(), new ArrayList<>(changed));
                    }
                    if (c.wasAdded()) {
                        //Here, we add to the target, no matter if it's the copy or the original.
                        int pos = c.getFrom();
                        for (F sourceValue : c.getAddedSubList()) {
                            //Use updater function to generate fresh value
                            E nextValue = func.update(pos, sourceValue, null);
                            target.add(nextValue);
                            pos++;
                        }
                        nextAdd(c.getFrom(), c.getTo());
                    }
                }
            }

            if (target != null) {
                //If we copied the list, update our current cache to point to the copy.
                //Still safe if we didn't copy but added to end, as then cachedValues == target.
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
        public E get(int i) {
            return cachedValues.get(i);
        }

        @Override
        public int size() {
            return cachedValues.size();
        }
    }
}
