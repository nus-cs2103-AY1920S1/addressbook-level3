package calofit.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class ObservableListUtilTest {

    abstract static class ObservableListBaseTest {

        private ObservableList<Integer> sourceList;
        private ObservableList<Integer> mappedList;
        private Function<Integer, Integer> mapFunc;
        private ListChangeListener<Integer> listener;
        private ArgumentCaptor<Integer> argCaptor;
        private ArgumentCaptor<ListChangeListener.Change<Integer>> changeCaptor;

        protected abstract <S, T> ObservableList<? extends T> makeMappedList(ObservableList<S> source,
                                                                             Function<? super S, ? extends T> func);

        @BeforeEach
        void setUp() {
            mapFunc = Mockito.mock(Function.class);
            Mockito.when(mapFunc.apply(Mockito.anyInt())).thenAnswer(new Answer<Integer>() {
                @Override
                public Integer answer(InvocationOnMock invocation) throws Throwable {
                    int val = invocation.getArgument(0);
                    return val * val;
                }
            });
            argCaptor = ArgumentCaptor.forClass(Integer.class);

            sourceList = FXCollections.observableArrayList(1, 2, 3, 4, 5);
            mappedList = ObservableListUtil.map(sourceList, mapFunc);

            listener = Mockito.mock(ListChangeListener.class);
            mappedList.addListener(listener);

            changeCaptor = ArgumentCaptor.forClass(ListChangeListener.Change.class);
        }

        @Test
        void testGet() {
            assertEquals(mappedList.get(2), 9);
            Mockito.verify(mapFunc, Mockito.atLeastOnce()).apply(argCaptor.capture());
            assertTrue(argCaptor.getAllValues().contains(sourceList.get(2)));
        }

        @Test
        void testMapping() {
            List<Integer> expected = sourceList.stream().map(mapFunc).collect(Collectors.toList());
            assertEquals(expected, mappedList);
        }

        @Test
        void testRemove() {
            sourceList.remove(Integer.valueOf(2));
            Mockito.verify(listener, Mockito.times(1)).onChanged(changeCaptor.capture());

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
            Mockito.verify(listener, Mockito.times(1)).onChanged(changeCaptor.capture());

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
            Mockito.verify(listener, Mockito.times(1)).onChanged(changeCaptor.capture());

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
