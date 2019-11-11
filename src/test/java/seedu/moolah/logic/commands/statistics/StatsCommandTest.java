package seedu.moolah.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_DESCRIPTOR;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelHistory;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void equals() {
        final StatsCommand standardCommand = new StatsCommand(VALID_STATS_DESCRIPTOR);

        // same values -> returns true
        StatsDescriptor copyDescriptor = new StatsDescriptor(VALID_STATS_DESCRIPTOR);
        StatsCommand commandWithSameValues = new StatsCommand(copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());
    }

}

    /*
    @Test
    //to vary the descriptor
    public void run_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();//just empty lol, why this one can just change

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST, descriptor);
        //forces a constructor call
        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);
        //checks for error message
        //and expected model
        Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                new UserPrefs(), new ModelHistory());//make a new one, but use back the MooLah from the old init
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);//force make the changes
        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));

        assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel);
    }




    @Test
    void calculateStats_validInputForStats_success() {

        ObservableList<Expense> VALID_EXPENSE_LIST = FXCollections.observableArrayList();
        VALID_EXPENSE_LIST.add(VALID_EXPENSE);
        String commandWord = StatsCommand.COMMAND_WORD;

        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
    }

     @Test
     public void run_someFieldsSpecifiedUnfilteredList_success() {
         Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
         Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

         ExpenseBuilder expenseInList = new ExpenseBuilder(lastExpense);//now filter first
         Expense editedExpense = expenseInList
                 .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI)
                 .withPrice(VALID_EXPENSE_PRICE_TAXI)
                 .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();

         EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                 .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI)
                 .withPrice(VALID_EXPENSE_PRICE_TAXI)
                 .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();//identical
         EditExpenseCommand editExpenseCommand = new EditExpenseCommand(indexLastExpense, descriptor);

         String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

         Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                 new UserPrefs(), new ModelHistory());
         expectedModel.setExpense(lastExpense, editedExpense);
         expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));

         assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel);
     }

     @Test
     public void run_noFieldSpecifiedUnfilteredList_success() {
         EditExpenseCommand editExpenseCommand =
                 new EditExpenseCommand(INDEX_FIRST, new EditExpenseDescriptor());
         Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST.getZeroBased());

         String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

         Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                 new UserPrefs(), new ModelHistory());
         expectedModel.commitModel("");

         assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel);
     }

     @Test
     public void run_filteredList_success() {
         showExpenseAtIndex(model, INDEX_FIRST);

         Expense expenseInFilteredList = model
                 .getFilteredExpenseList()
                 .get(INDEX_FIRST.getZeroBased());
         Expense editedExpense = new ExpenseBuilder(expenseInFilteredList)
                 .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
         EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST,
                 new EditExpenseDescriptorBuilder()
                         .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build());

         String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

         Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
                 new UserPrefs(), new ModelHistory());
         expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedExpense);
         expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));

         assertCommandSuccess(editExpenseCommand, model, expectedMessage, expectedModel);
     }

      Editing an expense to have the same details as another should not result in failure


     // Editing an expense to have the same details as another should not result in failure

     //vulnerability of the validate is tested, one for out of bound index in 2 cases, and want the double boolean check
     @Test
     public void run_invalidExpenseIndexUnfilteredList_failure() {
         Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
         EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                 .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
         EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

         assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
     }




        @Test
        public void run_invalidExpenseIndexFilteredList_failure() {
            showExpenseAtIndex(model, INDEX_FIRST);
            Index outOfBoundIndex = INDEX_SECOND;
            // ensures that outOfBoundIndex is still in bounds of address book list
            assertTrue(outOfBoundIndex.getZeroBased() < model.getMooLah().getExpenseList().size());

            EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex,
                    new EditExpenseDescriptorBuilder().withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build());

            assertCommandFailure(editExpenseCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }



    //dk how to test my validate which by design wont rlly happen for me
    //
    // execute method is the logic of creating, but raised the issue of creation to them

    //I want to test my PieChart creation private method, copying over from the statistics test










    public static final Expense VALID_EXPENSE = new ExpenseBuilder().build();
    public static final ObservableList<Expense> VALID_EXPENSE_LIST =
            FXCollections.observableList(List.of(VALID_EXPENSE));


    public static final String EMPTY_STRING_COMMAND_WORD = "";
    public static final String WHITE_SPACE_COMMAND_WORD = " ";
    public static final String INVALID_COMMAND_WORD = "random";
     */




    /*
    public static final Budget VALID_BUDGET = new BudgetBuilder().build();
    public static final Budget INVALID_BUDGET = null;
    public static final Timestamp VALID_EARLY_TIMESTAMP = Timestamp.createTimestampIfValid("14-01-2019").get();
    public static final Timestamp VALID_LATE_TIMESTAMP = Timestamp.createTimestampIfValid("16-09-2019").get();
    public static final Timestamp INVALID_TIMESTAMP = null;
    */

    /*
    @Test
    public void constructor_nullParameters_throwsNullPointerException() {

        List<Category> categories = Category.getValidCategories();

        assertThrows(NullPointerException.class, () -> new Statistics(VALID_EXPENSE_LIST, null));

        assertThrows(NullPointerException.class, () -> new Statistics(null, categories));

        //assertThrows(NullPointerException.class, () -> new Statistics(EMPTY_EXPENSE_LIST, categories));

    }






    @Test
    void calculateStats_validInputForStats_success() {

        ObservableList<Expense> VALID_EXPENSE_LIST = FXCollections.observableArrayList();
        VALID_EXPENSE_LIST.add(VALID_EXPENSE);
        String commandWord = StatsCommand.COMMAND_WORD;

        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
    }




    //the methods that call the run method, now depreciated because run method quite different and
    //we want to map the command there instead of the model object
    //now the run method will always take in non-null values because handled elsewhere, so it stops checking
    //these tests should be migrated
    @Test
    void calculateStats_validInputForStats_success() {

        String commandWord = StatsCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof PieChartStatistics);
    }




    @Test
    void calculateStats_validInputForStatsCompare_success() {

        String commandWord = StatsCompareCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof TabularStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TabularStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_LATE_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, false) instanceof TabularStatistics);
    }



    @Test
    void calculateStats_validInputForStatsTrend_success() {

        String commandWord = StatsTrendCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
//        assertTrue(Statistics.calculateStats(commandWord,
//                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof TrendStatistics);
//        assertTrue(Statistics.calculateStats(commandWord,
//                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
//        assertTrue(Statistics.calculateStats(commandWord,
//                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof TrendStatistics);
    }

    //Handled by parsing

    /*
    @Test
    void calculateStats_emptyCommandWord_throwsException() {

        ObservableList<Expense> VALID_EXPENSE_LIST = FXCollections.observableArrayList();
        VALID_EXPENSE_LIST.add(VALID_EXPENSE);

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        EMPTY_STRING_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        WHITE_SPACE_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        INVALID_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
    }

     */

    /*
    @Test
    void calculateStats_invalidInputForStats_throwsException() {

        String commandWord = StatsCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));
    }

    @Test
    void calculateStats_invalidInputForStatsCompare_throwsException() {

        String commandWord = StatsCompareCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));


        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));


        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
    }

    @Test
    void calculateStats_invalidInputForStatsTrend_throwsException() {

        String commandWord = StatsTrendCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));
    }
    */
