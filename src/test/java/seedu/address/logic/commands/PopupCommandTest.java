package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

class PopupCommandTest {
    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }


    /*@Test
    void executeCannotFindGroup() throws CommandException {

        PopupCommand popupCommand = new PopupCommand(0, 1);
        assertEquals("Cannot recognise GROUP_NAME. Make sure you entered the correct value.",
                popupCommand.execute(model).getFeedbackToUser());
    }

    @Test
    void executeCannotFindAllNoPreviousLocation() throws CommandException {
        PopupCommand popupCommand = new PopupCommand(0, 1);
        assertEquals("We could not find a common location because:\n"
                        + "Everyone has not started their schedule yet. Feel free to meet up any time.",
                popupCommand.execute(model).getFeedbackToUser());
    }

    @Test
    void executeInvalidIdExceedUpperLimit() throws CommandException {
        PopupCommand popupCommand = new PopupCommand(3, Integer.MAX_VALUE);
        assertEquals("Invalid time slot ID: 2147483647. Please enter a valid id as shown in the GUI.",
                popupCommand.execute(model).getFeedbackToUser());
    }

    @Test
    void executeInvalidIdExceedLowerLimit() throws CommandException {
        PopupCommand popupCommand = new PopupCommand(3, 0);
        assertEquals("Invalid time slot ID: 0. Please enter a valid id as shown in the GUI.",
                popupCommand.execute(model).getFeedbackToUser());
    }

    @Test
    void testEquals() {
        PopupCommand popupCommand = new PopupCommand(0, 1);
        assertTrue(popupCommand.equals(popupCommand));
    }*/
}
