//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.commons.core.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.note.NoteBook;
import tagline.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code GroupCommand}.
 */
public class GroupCommandTest {

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new UserPrefs());

    @Test
    public void findOneGroup_zeroKeywords_noGroupFound() {
        Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            getTypicalGroupBook(), new UserPrefs());

        // empty predicate
        GroupNameEqualsKeywordPredicate predicate = prepareGroupPredicate(MYSTIC_ARTS.getGroupName().value);
        assertThrows(CommandException.class, () -> GroupCommand.findOneGroup(model, "BTS"));
    }

    @Test
    public void findOneGroup_oneKeyword_noGroupFound() {
        Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            getTypicalGroupBook(), new UserPrefs());

        // empty predicate
        //GroupNameEqualsKeywordPredicate predicate = prepareGroupPredicate("");
        GroupNameEqualsKeywordPredicate predicate = prepareGroupPredicate("BTS");
        //assertThrows(CommandException.class, () -> GroupCommand.findOneGroup(model,""));
        assertThrows(CommandException.class, () -> GroupCommand.findOneGroup(model, "BTS"));
    }

    @Test
    public void findOneGroup_keyword_groupFound() {
        Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            getTypicalGroupBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        GroupNameEqualsKeywordPredicate predicate = prepareGroupPredicate("wakandan-royal-gUARd");
        try {
            GroupCommand.findOneGroup(model, "Wakandan-Royal-Guard");
        } catch (CommandException e) {
            // fails if it throws an exception
            assertTrue(false);
        }
    }

    @Test
    public void verifyMemberIdWithModel_test() {
        //  TypicalAddressBook contains Ids 1 to 7
        Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            new GroupBook(), new UserPrefs());

        // All GroupMembers verified and exists in ContactList
        Group testGroup = new GroupBuilder().withGroupName("BTS").withMemberIds("1", "00002").build();
        Group refGroup = new GroupBuilder().withGroupName("BTS").withMemberIds("1", "00002").build();
        assertTrue(refGroup.equals(GroupCommand.verifyGroupWithModel(model, testGroup)));

        // Some GroupMembers not in ContactList
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
                new GroupBook(), new UserPrefs());
        Group testGroup2 = new GroupBuilder().withGroupName("BTS").withMemberIds("1", "00002", "99999").build();
        Group refGroup2 = new GroupBuilder().withGroupName("BTS").withMemberIds("1", "00002").build();
        assertTrue(refGroup2.equals(GroupCommand.verifyGroupWithModel(model, testGroup2)));

        // All GroupMembers not in ContactList
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
                new GroupBook(), new UserPrefs());
        Group testGroup3 = new GroupBuilder().withGroupName("BTS").withMemberIds("70", "14", "99999").build();
        Group refGroup3 = new GroupBuilder().withGroupName("BTS").build();
        assertTrue(refGroup3.equals(GroupCommand.verifyGroupWithModel(model, testGroup3)));

        // All GroupMembers not in ContactList
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
                new GroupBook(), new UserPrefs());
        Group testGroup4 = new GroupBuilder().withGroupName("BTS").build();
        Group refGroup4 = new GroupBuilder().withGroupName("BTS").build();
        assertTrue(refGroup4.equals(GroupCommand.verifyGroupWithModel(model, testGroup4)));

    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private GroupNameEqualsKeywordPredicate prepareGroupPredicate(String userInput) {
        return new GroupNameEqualsKeywordPredicate(Arrays.asList(new GroupName(userInput)));
    }

}
