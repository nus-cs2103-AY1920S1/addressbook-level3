package mams.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static mams.testutil.TypicalMams.getTypicalMams;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.StudentContainsKeywordsPredicate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FindCommandTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());

    @Test
    public void equals() {
        StudentContainsKeywordsPredicate firstStudentPredicate =
                new StudentContainsKeywordsPredicate(Collections.singletonList("alice"));
        StudentContainsKeywordsPredicate secondStudentPredicate =
                new StudentContainsKeywordsPredicate(Collections.singletonList("bob"));
        ModuleContainsKeywordsPredicate firstModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("cs1010"));
        ModuleContainsKeywordsPredicate secondModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("cs1231"));
        AppealContainsKeywordsPredicate firstAppealPredicate =
                new AppealContainsKeywordsPredicate(Collections.singletonList("resolved"));
        AppealContainsKeywordsPredicate secondAppealPredicate =
                new AppealContainsKeywordsPredicate(Collections.singletonList("unresolved"));

        List<Predicate> firstPredicate = new ArrayList<>(Arrays.asList(firstStudentPredicate,
                firstModulePredicate, firstAppealPredicate));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student predicate -> returns false
        List<Predicate> secondPredicate = new ArrayList<>(Arrays.asList(secondStudentPredicate,
                firstModulePredicate, firstAppealPredicate));
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different module predicate -> returns false
        secondPredicate = new ArrayList<>(Arrays.asList(firstStudentPredicate,
                secondModulePredicate, firstAppealPredicate));
        findSecondCommand = new FindCommand(secondPredicate);
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different appeal predicate -> returns false
        secondPredicate = new ArrayList<>(Arrays.asList(firstStudentPredicate,
                firstModulePredicate, secondAppealPredicate));
        findSecondCommand = new FindCommand(secondPredicate);
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private List<Predicate> preparePredicate(String userInput) {
        return null;
    }

}
