package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.model.person.Person;
import unrealunity.visit.testutil.TypicalPersons;

/**
 * Contains unit tests for GenerateProfileCommand.
 */
public class GenerateProfileCommandTest {

    @Test
    public void equals() {
        Person firstPerson = TypicalPersons.ALICE.getClone();
        Person secondPerson = TypicalPersons.BENSON.getClone();
        final GenerateProfileCommand standardCommand = new GenerateProfileCommand(firstPerson);

        // same values -> returns true
        GenerateProfileCommand commandWithSameValues = new GenerateProfileCommand(firstPerson);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        GenerateProfileCommand otherCommand = new GenerateProfileCommand(secondPerson);
        assertFalse(standardCommand.equals(otherCommand));
    }

}
