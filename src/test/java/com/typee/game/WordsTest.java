package com.typee.game;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class WordsTest {

    @Test
    public void get() {
        assertNotNull(Words.SIZE);
        assertThrows(IllegalArgumentException.class, () -> Words.get(-1));
        assertThrows(IllegalArgumentException.class, () -> Words.get(Integer.MIN_VALUE));
        assertDoesNotThrow(() -> Words.get(0));
        assertDoesNotThrow(() -> Words.get(Words.SIZE - 1));
    }
}
