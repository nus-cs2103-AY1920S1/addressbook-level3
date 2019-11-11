package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.EateryBuilder.DEFAULT_TAG;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.AddTagCommand.EditEateryDescriptor;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.testutil.EateryBuilder;

public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedFilteredList_success() {
        Eatery editedEatery = new EateryBuilder().withTags(VALID_TAG_NO_PREFIX_NICE, DEFAULT_TAG).build();
        String expectedMessage = String.format(AddTagCommand.ADD_TAG_SUCCESS, editedEatery);

        EditEateryDescriptor editEateryDescriptor = new EditEateryDescriptor();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_NO_PREFIX_NICE));
        editEateryDescriptor.addTags(tags);
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_EATERY, editEateryDescriptor);

        Model expectedModel =
                new ModelManager(new EateryList(model.getEateryList()), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), editedEatery);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEateryIndexFilteredList_failure() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);
        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, new EditEateryDescriptor());

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }
}
