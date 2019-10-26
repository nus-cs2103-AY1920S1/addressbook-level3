package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasksMembers.ELSA_KOH;
import static seedu.address.testutil.TypicalTasksMembers.GABRIEL_SEOW;
import static seedu.address.testutil.TypicalTasksMembers.SEAH_LYNN;
import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;
import seedu.address.model.member.MemberNameContainsKeywordsPredicate;

public class FindMemberCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
    private Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void equals() {
        MemberNameContainsKeywordsPredicate firstPredicate = new MemberNameContainsKeywordsPredicate(Collections.singletonList("John"));
        MemberNameContainsKeywordsPredicate secondPredicate = new MemberNameContainsKeywordsPredicate(Collections.singletonList("Gabriel"));

        FindMemberCommand findFirstCommand = new FindMemberCommand(firstPredicate);
        FindMemberCommand findSecondCommand = new FindMemberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMemberCommand findFirstCommandCopy = new FindMemberCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noMemberFound() {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 0);
        MemberNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindMemberCommand command = new FindMemberCommand(predicate);
        expectedModel.updateFilteredMembersList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMembersList());
    }

    @Test
    public void execute_multipleKeywords_multipleMembersFound() {
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 3);
        MemberNameContainsKeywordsPredicate predicate = preparePredicate("gabriel elsa lynn");
        FindMemberCommand command = new FindMemberCommand(predicate);
        expectedModel.updateFilteredMembersList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GABRIEL_SEOW, ELSA_KOH, SEAH_LYNN), model.getFilteredMembersList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private MemberNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MemberNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
