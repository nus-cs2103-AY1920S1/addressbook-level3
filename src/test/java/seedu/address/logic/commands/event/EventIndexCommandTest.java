package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.EventUtil.formatIndexVEventPair;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.EventIndexCommand.MESSAGE_SUGGESTION_EVENT;
import static seedu.address.logic.commands.event.EventIndexCommand.NO_EVENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT1;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Test Event Index Command. Note that this is a integration test as it uses the modelManager directly.
 */
public class EventIndexCommandTest {
    private static final String VALID_DESIRED_EVENT_NAME = "valid event name";
    private static final String VALID_OTHER_DESIRED_EVENT_NAME = "other valid event name";
    private Model model = new ModelManager();

    public EventIndexCommandTest() {
        model.setEventRecord(getTypicalEventsRecord());
    }

    @Test
    public void execute_validIndexOf_showsRelevantEvent() {
        Model expectedModel = new ModelManager();
        expectedModel.setEventRecord(getTypicalEventsRecord());

        String desiredEventName = VEVENT1.getSummary().getValue();

        String expectedResult = formatIndexVEventPair(new Pair(Index.fromOneBased(1), VEVENT1));

        assertCommandSuccess(new EventIndexCommand(desiredEventName), model,
                new CommandResult(expectedResult, CommandResultType.SHOW_SCHEDULE), expectedModel);
    }

    @Test
    public void execute_desiredEventNameNotFound_showsMostSimilarEvent() {
        Model expectedModel = new ModelManager();
        expectedModel.setEventRecord(getTypicalEventsRecord());

        String desiredEventName = VEVENT1.getSummary().getValue().substring(0, 6);
        Pair<Index, VEvent> expectedEventIndexPair = new Pair(Index.fromOneBased(1), VEVENT1);
        String expectedResult = String.format(MESSAGE_SUGGESTION_EVENT, formatIndexVEventPair(expectedEventIndexPair));

        assertCommandSuccess(new EventIndexCommand(desiredEventName), model,
                new CommandResult(expectedResult, CommandResultType.SHOW_SCHEDULE), expectedModel);
    }

    @Test
    public void constructor_nullDesiredEventName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventIndexCommand(null));
    }

    @Test
    public void execute_emptyEventRecord_throwsCommandException() {
        Model model = new ModelManager();
        EventIndexCommand indexCommand = new EventIndexCommand("test name");
        assertThrows(CommandException.class, NO_EVENT, () -> indexCommand.execute(model));
    }

    @Test
    public void equals() {
        EventIndexCommand indexEventCommand = new EventIndexCommand(VALID_DESIRED_EVENT_NAME);
        EventIndexCommand otherIndexEventCommand = new EventIndexCommand(VALID_OTHER_DESIRED_EVENT_NAME);

        // same object -> returns true
        assertTrue(indexEventCommand.equals(indexEventCommand));

        // same values -> returns true
        assertTrue(indexEventCommand.equals(new EventIndexCommand(VALID_DESIRED_EVENT_NAME)));

        // different types -> returns false
        assertFalse(indexEventCommand.equals(1));

        // null -> returns false
        assertFalse(indexEventCommand.equals(null));

        // different note -> returns false
        assertFalse(indexEventCommand.equals(otherIndexEventCommand));
    }

}
