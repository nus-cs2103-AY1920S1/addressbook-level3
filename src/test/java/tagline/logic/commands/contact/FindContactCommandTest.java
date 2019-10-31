package tagline.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.commons.core.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.contact.TypicalContacts.CARL;
import static tagline.testutil.contact.TypicalContacts.ELLE;
import static tagline.testutil.contact.TypicalContacts.FIONA;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.NameContainsKeywordsPredicate;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model) for {@code FindContactCommand}.
 */
public class FindContactCommandTest {

    private static final ViewType FIND_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new TagBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new TagBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindContactCommand findFirstCommand = new FindContactCommand(firstPredicate);
        FindContactCommand findSecondCommand = new FindContactCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindContactCommand findFirstCommandCopy = new FindContactCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindContactCommand command = new FindContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, FIND_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleKeywords_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindContactCommand command = new FindContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, FIND_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
