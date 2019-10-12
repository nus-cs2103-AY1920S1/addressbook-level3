package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    @Test
    public void equals() {
        List<String> myFields = new ArrayList<>();
        myFields.add("PRICE");
        SortCommand command = new SortCommand(myFields);
        assertTrue(command.equals(command));

        assertFalse(command.equals(new SortCommand(null)));
    }

}
