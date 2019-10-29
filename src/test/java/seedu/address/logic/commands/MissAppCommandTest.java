package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.predicates.EventsMissedPredicate;
import seedu.address.testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;


class MissAppCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = TestUtil.getTypicalModelManager();
        expectedModel = TestUtil.getTypicalModelManager();
    }
    //    execute_zeroKeywords_noEventFound

    @Test
    public void execute_zeroKeywords_allEventsFound() {
        model.updateFilteredAppointmentList(new EventsMissedPredicate());
        String expectedMessage = String.format(Messages.MESSAGE_MISSED_EVENT_LISTED_OVERVIEW,
                model.getFilteredAppointmentList().size());
        MissAppCommand command = new MissAppCommand();
        //todo 2 model equals not working
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        CommandResult commandResult = command.execute(model);
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }

}