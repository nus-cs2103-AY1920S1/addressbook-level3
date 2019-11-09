package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.MissAppCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.predicates.EventsMissedPredicate;
import seedu.address.testutil.TestUtil;


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
        String expectedMessage = String.format(MissAppCommand.MESSAGE_MISSED_EVENT_LISTED_OVERVIEW,
                model.getFilteredAppointmentList().size());
        MissAppCommand command = new MissAppCommand();
        CommandResult commandResult = command.execute(model);
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }
}
