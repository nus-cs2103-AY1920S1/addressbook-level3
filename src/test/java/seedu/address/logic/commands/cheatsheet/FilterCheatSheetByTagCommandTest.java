package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCheatSheets.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cheatsheet.CheatSheetContainsTagPredicate;
import seedu.address.model.tag.Tag;

public class FilterCheatSheetByTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Set<Tag> firstUserInput = new HashSet<>();
        firstUserInput.add(new Tag("first"));
        Set<Tag> secondUserInput = new HashSet<>();
        secondUserInput.add(new Tag("second"));

        ArrayList<String> firstUserDisplay = new ArrayList<>();
        firstUserDisplay.add("first");
        ArrayList<String> secondUserDisplay = new ArrayList<>();
        firstUserDisplay.add("second");

        CheatSheetContainsTagPredicate firstPredicate =
                new CheatSheetContainsTagPredicate(firstUserInput);
        CheatSheetContainsTagPredicate secondPredicate =
                new CheatSheetContainsTagPredicate(secondUserInput);

        FilterCheatSheetByTagCommand findFirstCommand =
                new FilterCheatSheetByTagCommand(firstPredicate, firstUserDisplay);
        FilterCheatSheetByTagCommand findSecondCommand =
                new FilterCheatSheetByTagCommand(secondPredicate, secondUserDisplay);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterCheatSheetByTagCommand findFirstCommandCopy =
                new FilterCheatSheetByTagCommand(firstPredicate, firstUserDisplay);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
}
