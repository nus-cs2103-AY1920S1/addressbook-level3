//package seedu.guilttrip.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.guilttrip.testutil.TypicalEntries.CARL;
//import static seedu.guilttrip.testutil.TypicalEntries.ELLE;
//import static seedu.guilttrip.testutil.TypicalEntries.FIONA;
//import static seedu.guilttrip.testutil.TypicalEntries.getTypicalAddressBook;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ModelManager;
//import seedu.guilttrip.model.UserPrefs;
//import seedu.guilttrip.model.entry.predicates.entries.DescriptionContainsKeywordsPredicate;
//
///**
// * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
// */
//public class FindCommandTest {
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
//    @Test
//    public void equals() {
//        DescriptionContainsKeywordsPredicate firstPredicate =
//                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
//        DescriptionContainsKeywordsPredicate secondPredicate =
//                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));
//
//        FindExpenseCommand findFirstCommand = new FindExpenseCommand(firstPredicate);
//        FindExpenseCommand findSecondCommand = new FindExpenseCommand(secondPredicate);
//
//        // same object -> returns true
//        assertTrue(findFirstCommand.equals(findFirstCommand));
//
//        // same values -> returns true
//        FindExpenseCommand findFirstCommandCopy = new FindExpenseCommand(firstPredicate);
//        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(findFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(findFirstCommand.equals(null));
//
//        // different entry -> returns false
//        assertFalse(findFirstCommand.equals(findSecondCommand));
//    }
//
//    @Test
//    public void execute_zeroKeywords_noPersonFound() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
//        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
//        FindExpenseCommand command = new FindExpenseCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_multipleKeywords_multiplePersonsFound() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
//        DescriptionContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
//        FindExpenseCommand command = new FindExpenseCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
//    }
//
//    /**
//     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
//     */
//    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
//        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
//    }
//}
