package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.address.testutil.EateryBuilder.DEFAULT_TAG;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand.EditEateryDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Tag;
import seedu.address.testutil.EateryBuilder;

public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());

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
                new ModelManager(new AddressBook(model.getAddressBook()), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), editedEatery);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEateryIndexFilteredList_failure() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);
        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEateryList().size());

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, new EditEateryDescriptor());

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }
}
