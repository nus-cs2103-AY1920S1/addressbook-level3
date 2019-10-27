package calofit.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ObservableListUtilTest {

    abstract static class ObservableListBaseTest {

        private ObservableList<Integer> sourceList;

        private ObservableList<? extends Integer> mappedList;

        //Mocked, but we need to generate Answer at runtime
        private Function<Integer, Integer> mapFunc;
        @Mock
        private ListChangeListener<Integer> listener;
        @Captor
        private ArgumentCaptor<Integer> argCaptor;
        @Captor
        private ArgumentCaptor<ListChangeListener.Change<Integer>> changeCaptor;

        private int square(int x) {
            return x * x;
        }

        /**
         * Allows subclass to construct tested object, from source list and mapping function.
         * This function is generic in the source/target types, to prevent subclasses from cheating.
         * @param source Source list
         * @param func Mapping function
         * @param <S> Source type
         * @param <T> Target type
         * @return Mapped list to test
         */
        protected abstract <S, T> ObservableList<? extends T> makeMappedList(ObservableList<S> source,
                                                                             Function<? super S, ? extends T> func);

        @BeforeEach
        void setUp() {
            MockitoAnnotations.initMocks(this);

            sourceList = FXCollections.observableArrayList(1, 2, 3, 4, 5);

            mapFunc = mock(Function.class, AdditionalAnswers.answer(this::square));

            mappedList = makeMappedList(sourceList, mapFunc);
            mappedList.addListener(listener);
        }

        @Test
        void testGet() {
            assertEquals(mappedList.get(2), 9);
            verify(mapFunc, atLeastOnce()).apply(argCaptor.capture());
            assertTrue(argCaptor.getAllValues().contains(sourceList.get(2)));
        }

        @Test
        void testMapping() {
            List<Integer> expected = sourceList.stream().map(this::square).collect(Collectors.toList());
            assertEquals(expected, mappedList);
        }

        @Test
        void testRemove() {
            sourceList.remove(Integer.valueOf(2));
            verify(listener, times(1)).onChanged(changeCaptor.capture());

            ListChangeListener.Change<Integer> change = changeCaptor.getValue();
            assertTrue(change.next());
            assertEquals(1, change.getFrom());
            assertEquals(1, change.getTo());
            assertTrue(change.wasRemoved());
            assertEquals(List.of(4), change.getRemoved());
            assertFalse(change.next());

            testMapping();
        }

        @Test
        void testSet() {
            sourceList.set(1, 9);
            verify(listener, times(1)).onChanged(changeCaptor.capture());

            ListChangeListener.Change<Integer> change = changeCaptor.getValue();
            assertTrue(change.next());
            assertEquals(1, change.getFrom());
            assertEquals(2, change.getTo());
            assertTrue(change.wasReplaced());
            assertEquals(List.of(81), mappedList.subList(change.getFrom(), change.getTo()));
            assertFalse(change.next());

            testMapping();
        }

        @Test
        void testAdd() {
            sourceList.add(6);
            verify(listener, times(1)).onChanged(changeCaptor.capture());

            ListChangeListener.Change<Integer> change = changeCaptor.getValue();
            assertTrue(change.next());
            assertEquals(5, change.getFrom());
            assertEquals(6, change.getTo());
            assertTrue(change.wasAdded());
            assertEquals(List.of(36), mappedList.subList(change.getFrom(), change.getTo()));
            assertFalse(change.next());

            testMapping();
        }
    }

    @Nested
    static class MappedListTest extends ObservableListBaseTest {
        @Override
        protected <S, T> ObservableList<? extends T> makeMappedList(ObservableList<S> source,
                                                                    Function<? super S, ? extends T> func) {
            return ObservableListUtil.lazyMap(source, func);
        }
    }

    @Nested
    static class BoundListTest extends ObservableListBaseTest {
        @Override
        protected <S, T> ObservableList<? extends T> makeMappedList(ObservableList<S> source,
                                                                    Function<? super S, ? extends T> func) {
            return ObservableListUtil.map(source, func);
        }
    }

    @Test
    void testSum() {
        ObservableList<Double> source = FXCollections.observableArrayList(3.0, 1.0, 4.0, 1.0, 5.0, 9.0);
        DoubleBinding sum = ObservableListUtil.sum(source);
        assertEquals(source.stream().mapToDouble(Double::doubleValue).sum(), sum.get());

        source.set(2, 5.0);
        assertEquals(source.stream().mapToDouble(Double::doubleValue).sum(), sum.get());

        source.add(9.0);
        assertEquals(source.stream().mapToDouble(Double::doubleValue).sum(), sum.get());

        source.remove(source.size() - 2);
        assertEquals(source.stream().mapToDouble(Double::doubleValue).sum(), sum.get());
    }
}
