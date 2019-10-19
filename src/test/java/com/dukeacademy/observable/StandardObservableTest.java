package com.dukeacademy.observable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardObservableTest {
    private StandardObservable<String> stringStandardObservable;
    private StandardObservable<Integer> integerStandardObservable;
    private StandardObservable<Boolean> booleanStandardObservable;

    @BeforeEach
    void initializeTest() {
        stringStandardObservable = new StandardObservable<>("Hello world!");

        integerStandardObservable = new StandardObservable<>(100);

        booleanStandardObservable = new StandardObservable<>();
    }

    @Test
    void getValue() {
        assertTrue(stringStandardObservable.getValue().isPresent());
        assertEquals("Hello world!", stringStandardObservable.getValue().get());

        assertTrue(integerStandardObservable.getValue().isPresent());
        assertEquals(100, integerStandardObservable.getValue().get());

        assertFalse(booleanStandardObservable.getValue().isPresent());
    }

    @Test
    void setValue() {
        stringStandardObservable.setValue("DukeAcademy");
        assertTrue(stringStandardObservable.getValue().isPresent());
        assertEquals("DukeAcademy", stringStandardObservable.getValue().get());

        integerStandardObservable.setValue(-100);
        assertTrue(integerStandardObservable.getValue().isPresent());
        assertEquals( -100, integerStandardObservable.getValue().get());

        booleanStandardObservable.setValue(true);
        assertTrue(booleanStandardObservable.getValue().isPresent());
        assertTrue(booleanStandardObservable.getValue().get());
    }

    @Test
    void addListener() {
        TestListener<String> stringTestListener = new TestListener<>();
        stringStandardObservable.addListener(stringTestListener);
        stringStandardObservable.setValue("DukeAcademy");
        assertEquals("DukeAcademy", stringTestListener.getLatestValue());

        TestListener<Integer> integerTestListener = new TestListener<>();
        integerStandardObservable.addListener(integerTestListener);
        integerStandardObservable.setValue(-100);
        assertEquals(-100, integerTestListener.getLatestValue());

        TestListener<Boolean> booleanTestListener = new TestListener<>();
        booleanStandardObservable.addListener(booleanTestListener);
        booleanStandardObservable.setValue(true);
        assertEquals(true, booleanTestListener.getLatestValue());
    }

    @Test
    void removeListener() {
        TestListener<String> stringTestListener = new TestListener<>();
        stringStandardObservable.addListener(stringTestListener);
        stringStandardObservable.removeListener(stringTestListener);
        stringStandardObservable.setValue("DukeAcademy");
        assertNull(stringTestListener.getLatestValue());

        TestListener<Integer> integerTestListener = new TestListener<>();
        integerStandardObservable.addListener(integerTestListener);
        integerStandardObservable.removeListener(integerTestListener);
        integerStandardObservable.setValue(-100);
        assertNull(integerTestListener.getLatestValue());

        TestListener<Boolean> booleanTestListener = new TestListener<>();
        booleanStandardObservable.addListener(booleanTestListener);
        booleanStandardObservable.removeListener(booleanTestListener);
        booleanStandardObservable.setValue(true);
        assertNull(booleanTestListener.getLatestValue());
    }
}