package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.card.exceptions.HintOutOfBoundsException;

class FormattedHintTest {

    @Test
    void constructor() {
        int testSize = 10;
        FormattedHint fHint = new FormattedHint(testSize);
        try {
            final Field field = fHint.getClass().getDeclaredField("arrayOfHintCharacters");
            field.setAccessible(true);
            char[] fHintArr = (char[]) field.get(fHint);

            for (int i = 0; i < testSize; i++) {
                int currChar = (int) fHintArr[i];
                // all should be initialized to ASCII null character.
                assertEquals(currChar, 0);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void updateHintArray() {
        int testSize = 4;
        FormattedHint fHint = new FormattedHint(testSize);

        Hint dummyHintAtPosZero = new Hint('A', Index.fromZeroBased(0));
        Hint dummyHintAtPosTwo = new Hint('R', Index.fromZeroBased(2));

        fHint.updateHintArray(dummyHintAtPosZero);
        fHint.updateHintArray(dummyHintAtPosTwo);

        try {
            final Field field = fHint.getClass().getDeclaredField("arrayOfHintCharacters");
            field.setAccessible(true);
            char[] fHintArr = (char[]) field.get(fHint);

            assertEquals(fHintArr[0], 'A');
            assertEquals(fHintArr[2], 'R');
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    void updateHintArray_outOfBounds_throwsHintOutOfBoundsException() {
        int testSize = 1;
        FormattedHint fHint = new FormattedHint(testSize);
        Hint dummyHintAtPosTwo = new Hint('R', Index.fromZeroBased(2));

        assertThrows(HintOutOfBoundsException.class, () -> {
            fHint.updateHintArray(dummyHintAtPosTwo);
        });
    }

    @Test
    void testToString_hintsUpdated_success() {
        int testSize = 4;
        FormattedHint fHint = new FormattedHint(testSize);

        Hint dummyHintAtPosZero = new Hint('A', Index.fromZeroBased(0));
        Hint dummyHintAtPosTwo = new Hint('R', Index.fromZeroBased(2));

        fHint.updateHintArray(dummyHintAtPosZero);
        fHint.updateHintArray(dummyHintAtPosTwo);

        String expectedString = "A_R_";
        assertEquals(fHint.toString(), expectedString);
    }

    @Test
    void testToString_hintsNotUpdated_success() {
        int testSize = 145;
        FormattedHint fHint = new FormattedHint(testSize);

        StringBuilder sb = new StringBuilder(testSize);
        for (int i = 0; i < testSize; i++) {
            sb.append('_');
        }

        String expectedString = sb.toString();
        assertEquals(expectedString, fHint.toString());
    }
}
