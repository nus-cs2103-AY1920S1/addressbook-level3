package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.testutil.TypicalAppeals.APPEAL1;
import static mams.testutil.TypicalMams.getTypicalMams;
import static mams.testutil.TypicalModules.CS1010;
import static mams.testutil.TypicalModules.CS1020;
import static mams.testutil.TypicalStudents.ALICE;
import static mams.testutil.TypicalStudents.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import mams.commons.core.Messages;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.StudentContainsKeywordsPredicate;

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

    @Test
    public void execute_singlePrefix_itemsFoundInSingleList() {

        // find in appeal list (prefix: a/), single keyword -> single result found
        String expectedMessage = String.format(Messages.MESSAGE_APPEALS_LISTED_OVERVIEW, 1);
        expectedMessage += "\n";
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(prepareAppealPredicate("C000001"));

        FindCommand firstCommand = new FindCommand(predicates);
        expectedModel.updateFilteredAppealList(predicates.get(0));
        assertCommandSuccess(firstCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(APPEAL1), model.getFilteredAppealList());

        // find in module list (prefix: m/), multiple keywords -> multiple results found
        expectedMessage = String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        expectedMessage += "\n";
        predicates = new ArrayList<>();
        predicates.add(prepareModulePredicate("CS1010 CS1020"));

        FindCommand secondCommand = new FindCommand(predicates);
        expectedModel.updateFilteredModuleList(predicates.get(0));
        assertCommandSuccess(secondCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS1010, CS1020), model.getFilteredModuleList());

        // find in student list (prefix: s/), multiple keywords -> single result found
        expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        expectedMessage += "\n";
        predicates = new ArrayList<>();
        predicates.add(prepareStudentPredicate("alice mary"));

        FindCommand thirdCommand = new FindCommand(predicates);
        expectedModel.updateFilteredStudentList(predicates.get(0));
        assertCommandSuccess(thirdCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredStudentList());
    }

    @Test
    public void execute_multiplePrefixes_itemsFoundInMultipleLists() {

        // find in all three lists
        String expectedMessage = String.format(Messages.MESSAGE_APPEALS_LISTED_OVERVIEW, 1);
        expectedMessage += "\n";
        expectedMessage += String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        expectedMessage += "\n";
        expectedMessage += String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        expectedMessage += "\n";

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(prepareAppealPredicate("C000001"));
        predicates.add(prepareModulePredicate("CS1010 CS1020"));
        predicates.add(prepareStudentPredicate("alice mary"));

        FindCommand findCommand = new FindCommand(predicates);
        expectedModel.updateFilteredAppealList(predicates.get(0));
        expectedModel.updateFilteredModuleList(predicates.get(1));
        expectedModel.updateFilteredStudentList(predicates.get(2));
        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(APPEAL1), model.getFilteredAppealList());
        assertEquals(Arrays.asList(CS1010, CS1020), model.getFilteredModuleList());
        assertEquals(Collections.singletonList(ALICE), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleKeywords_noItemsFound() {

        String expectedMessage = String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, 0);
        expectedMessage += "\n";
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(prepareModulePredicate("cs3230"));

        FindCommand findCommand = new FindCommand(predicates);
        expectedModel.updateFilteredModuleList(predicates.get(0));
        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());
    }

    @Test
    public void execute_keywordIsSubString_itemsFound() {

        // keyword: ben -> Benson found
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        expectedMessage += "\n";
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(prepareStudentPredicate("ben"));

        FindCommand findCommand = new FindCommand(predicates);
        expectedModel.updateFilteredStudentList(predicates.get(0));
        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredStudentList());

    }

    /**
     * Parses {@code userInput} into a {@code StudentContainsKeywordsPredicate}.
     */
    private StudentContainsKeywordsPredicate prepareStudentPredicate(String userInput) {
        return new StudentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code ModuleContainsKeywordsPredicate}.
     */
    private ModuleContainsKeywordsPredicate prepareModulePredicate(String userInput) {
        return new ModuleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AppealContainsKeywordsPredicate}.
     */
    private AppealContainsKeywordsPredicate prepareAppealPredicate(String userInput) {
        return new AppealContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
