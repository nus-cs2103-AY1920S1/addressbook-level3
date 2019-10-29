package organice.model.person;

import static org.junit.jupiter.api.Assertions.*;
import static organice.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import organice.logic.commands.AddCommand;
import organice.logic.commands.AddCommandTest;
import organice.logic.commands.CommandResult;
import organice.testutil.PersonBuilder;

class TaskListTest {
    Task one = new Task("one");
    Task two = new Task("two");
    Task three = new Task("three");

    @Test
    void add() {
        AddCommandTest.ModelStubAcceptingPersonAdded modelStub = new AddCommandTest.ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);

    }

    @Test
    void size() {
    }

    @Test
    void get() {
    }

    @Test
    void remove() {
    }

    @Test
    void defaultList() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}