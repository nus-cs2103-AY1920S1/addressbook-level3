package seedu.address.model.display.detailwindow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClosestCommonLocationDataTest {
    private ClosestCommonLocationData closestCommonLocationData;
    @BeforeEach
    void init() {
        closestCommonLocationData = new ClosestCommonLocationData();
    }
    @Test
    void getImage() {
        closestCommonLocationData.setFirstClosest("LT17");
        assertDoesNotThrow(()->closestCommonLocationData.getImage());
    }

    @Test
    void getFirstClosest() {
        closestCommonLocationData.setFirstClosest("FOO");
        assertEquals("FOO", closestCommonLocationData.getFirstClosest());
    }

    @Test
    void getSecondClosest() {
        closestCommonLocationData.setSecondClosest("FOO");
        assertEquals("FOO", closestCommonLocationData.getSecondClosest());
    }

    @Test
    void getThirdClosest() {
        closestCommonLocationData.setThirdClosest("FOO");
        assertEquals("FOO", closestCommonLocationData.getThirdClosest());
    }

    @Test
    void getFirstAvg() {
        closestCommonLocationData.setFirstAvg((long) Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, closestCommonLocationData.getFirstAvg());
    }

    @Test
    void getSecondAvg() {
        closestCommonLocationData.setSecondAvg((long) Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, closestCommonLocationData.getSecondAvg());
    }

    @Test
    void getThirdAvg() {
        closestCommonLocationData.setThirdAvg((long) Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, closestCommonLocationData.getThirdAvg());
    }

    @Test
    void getInvalidLocation() {
        assertNull(closestCommonLocationData.getInvalidLocation());
        ArrayList<String> input = new ArrayList<>(Arrays.asList("FOO", "BAR", "HELLO"));
        closestCommonLocationData.setInvalidLocation(input);
        assertEquals(input, closestCommonLocationData.getInvalidLocation());
    }

    @Test
    void isOk() {
        assertEquals(closestCommonLocationData.isOk(), false);
        closestCommonLocationData.setOk(true);
        assertEquals(closestCommonLocationData.isOk(), true);
    }
}
