package seedu.address.commons.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeetUtilTest {

    @Test
    void translateLeet_passwordWithFinitePossibilities() {
        List<String> possible = LeetUtil.translateLeet("a!!p055!b!3");
        String[] expectedArray = new String[] {"aiipossibie", "aiipossible", "aiiposslbie", "aiiposslble",
                "ailpossibie", "ailpossible", "ailposslbie", "ailposslble", "alipossibie", "alipossible",
                "aliposslbie", "aliposslble", "allpossibie", "allpossible", "allposslbie", "allposslble"};

        assertTrue(possible.size() == 16);
        assertArrayEquals(possible.toArray(), expectedArray);
    }

    @Test
    void traslateLeet_passwordWithAllDifferentLeet_notInUseScope() {
        List<String> possible = LeetUtil.translateLeet("!!!!");
        assertTrue(possible.isEmpty());
    }

    @Test
    void translateLeet_passwordWithTooManyPossibilities_stopAtHundred() {
        List<String> possible = LeetUtil.translateLeet("a!!poss!b!!!t!es");
        assertTrue(possible.size() == 100);
    }
}