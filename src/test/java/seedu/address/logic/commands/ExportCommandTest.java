package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.modelutil.TypicalModel;

public class ExportCommandTest {

    private ModelManager model;
    private Person alice = new Person(ALICE);
    private Person benson = new Person(BENSON);

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void equalsTest() {
        ExportCommand exportCommandAlice = new ExportCommand(alice.getName());
        ExportCommand exportCommandBenson = new ExportCommand(benson.getName());

        //Same person.
        ExportCommand exportCommandAlice2 = new ExportCommand(alice.getName());
        assertEquals(exportCommandAlice, exportCommandAlice2);

        //Different person.
        assertNotEquals(exportCommandAlice, exportCommandBenson);
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExportCommand(null));
    }

    @Test
    public void exportSuccess() {
        try {
            CommandResult actualMessage = (new ExportCommand(alice.getName())).execute(model);
            CommandResult expectedMessage = new CommandResult(String.format(ExportCommand.MESSAGE_SUCCESS, alice));
            assertEquals(expectedMessage, actualMessage);
        } catch (CommandException e) {
            assertEquals(1, 2);
        }
    }

    @Test
    public void exportFail() {
        //Person not found in the person list.
        assertThrows(CommandException.class, () -> new ExportCommand(new Name("Abedagge")).execute(model));
    }

}
