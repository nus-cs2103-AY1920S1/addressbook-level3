//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.group.FindGroupCommand.MESSAGE_KEYWORD_SUCCESS;
import static tagline.logic.commands.group.GroupCommand.MESSAGE_GROUP_NOT_FOUND;
import static tagline.logic.commands.group.GroupCommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.group.GroupCommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalGroups.GUARDIANS;
import static tagline.testutil.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model) for {@code FindGroupCommand}.
 */
public class FindGroupCommandTest {

    private static final ViewType FIND_CONTACT_COMMAND_VIEW_TYPE = ViewType.GROUP_SINGLE;
    private Model model = new ModelManager(new AddressBook(), new NoteBook(),
        getTypicalGroupBook(), new TagBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new AddressBook(), new NoteBook(),
        getTypicalGroupBook(), new TagBook(), new UserPrefs());

    @Test
    public void equals() {
        GroupNameEqualsKeywordPredicate firstPredicate =
                new GroupNameEqualsKeywordPredicate(Collections.singletonList(new GroupName("first")));
        GroupNameEqualsKeywordPredicate secondPredicate =
                new GroupNameEqualsKeywordPredicate(Collections.singletonList(new GroupName("second")));

        FindGroupCommand findFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findSecondCommand = new FindGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindGroupCommand findFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_oneKeywords_noGroupFound() {
        String expectedMessage = String.format(MESSAGE_GROUP_NOT_FOUND);
        // MYSTIC_ARTS is not automatically added into TypicalGroupBook
        GroupNameEqualsKeywordPredicate predicate = preparePredicate(MYSTIC_ARTS.getGroupName().value);
        FindGroupCommand command = new FindGroupCommand(predicate);

        assertCommandFailure(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneKeywords_oneGroupFound() {
        String expectedMessage = String.format(MESSAGE_KEYWORD_SUCCESS, GUARDIANS);
        GroupNameEqualsKeywordPredicate predicate = preparePredicate(GUARDIANS.getGroupName().value);
        FindGroupCommand command = new FindGroupCommand(predicate);

        expectedModel.updateFilteredContactList(GroupCommand.memberIdsToContactIdPredicate(GUARDIANS.getMemberIds()));
        expectedModel.updateFilteredGroupList(predicate);

        assertCommandSuccess(command, model, expectedMessage, FIND_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
        assertEquals(Arrays.asList(GUARDIANS), model.getFilteredGroupList());
    }

    /**
     * Parses {@code userInput} into a {@code GroupNameEqualsKeywordPredicate}.
     */
    private GroupNameEqualsKeywordPredicate preparePredicate(String userInput) {
        return new GroupNameEqualsKeywordPredicate(Collections.singletonList(new GroupName(userInput)));
    }
}
