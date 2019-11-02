package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TimeBookInvalidState;

class GenerateImageTest {

    @Test
    void execute() {
        assertThrows(TimeBookInvalidState.class, () -> new GenerateImage(new ArrayList<>()).execute());
    }
}
