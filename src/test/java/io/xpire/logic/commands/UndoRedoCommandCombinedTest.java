package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.executeCommandAndUpdateStateManager;
import static io.xpire.logic.commands.RedoCommand.MESSAGE_REDO_SUCCESS;
import static io.xpire.logic.commands.UndoCommand.MESSAGE_UNDO_SUCCESS;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIFTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.IN_A_MONTH;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BAGEL;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_COOKIE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_SWEET;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.ReminderThresholdExceededPredicate;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.state.StackManager;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.testutil.ItemBuilder;
import io.xpire.testutil.XpireItemBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Tests for Undo and Redo commands combined as Redo requires and Undo before.
 */
public class UndoRedoCommandCombinedTest {

    private Model model;
    private StateManager stateManager;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        stateManager = new StackManager();
    }

    //--------------------XPIRE VIEW-----------------------------------------------------------------------

    //Testing Undo/Redo for AddCommand
    @Test
    public void execute_undoRedoAddCommand_success() throws CommandException, ParseException {
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_CORIANDER),
                new ExpiryDate(VALID_EXPIRY_DATE_CORIANDER), new Quantity(VALID_QUANTITY_CORIANDER));
        executeCommandAndUpdateStateManager(model, addCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem apple = new XpireItemBuilder().withName(VALID_NAME_CORIANDER)
                .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                .withQuantity(VALID_QUANTITY_CORIANDER).build();
        expectedModel.addItem(XPIRE, apple);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for CheckCommand(ReminderThresholdExceededPredicate)
    @Test
    public void execute_undoRedoCheckCommandReminderPredicate_success() throws CommandException, ParseException {
        ReminderThresholdExceededPredicate predicate = new ReminderThresholdExceededPredicate();
        CheckCommand checkCommand = new CheckCommand(predicate);
        executeCommandAndUpdateStateManager(model, checkCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.filterCurrentList(XPIRE, predicate);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for CheckCommand(ExpiringSoonPredicate)
    @Test
    public void execute_undoRedoCheckCommandExpiringPredicate_success() throws CommandException, ParseException {
        ExpiringSoonPredicate predicate = new ExpiringSoonPredicate(12);
        CheckCommand checkCommand = new CheckCommand(predicate, 12);
        executeCommandAndUpdateStateManager(model, checkCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.filterCurrentList(XPIRE, predicate);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for ClearCommand
    @Test
    public void execute_undoRedoClear_success() throws CommandException, ParseException {
        ClearCommand clearCommand = new ClearCommand(XPIRE);
        executeCommandAndUpdateStateManager(model, clearCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        ObservableList<? extends Item> currentList = FXCollections.observableArrayList(model.getCurrentList());
        currentList.forEach(item -> expectedModel.deleteItem(XPIRE, item));
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for DeleteCommand(Item)
    @Test
    public void execute_undoRedoDeleteItem_success() throws CommandException, ParseException {
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIRST_ITEM);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.deleteItem(XPIRE, xpireItemToDelete);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for DeleteCommand(Tags)
    @Test
    public void execute_undoRedoDeleteTags_success() throws CommandException, ParseException {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRIDGE));
        set.add(new Tag(VALID_TAG_PROTEIN));
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_FOURTH_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FOURTH_ITEM, set);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_DUCK)
                .withExpiryDate(VALID_EXPIRY_DATE_DUCK)
                .build();
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for DeleteCommand(Quantity)
    @Test
    public void execute_undoRedoDeleteQuantity_success() throws CommandException, ParseException {
        Quantity quantityToDeduct = new Quantity("2");
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIFTH_ITEM, quantityToDeduct);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_EGG)
                .withExpiryDate(VALID_EXPIRY_DATE_EGG)
                .withQuantity("8")
                .build();
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for DeleteCommand(Quantity + Shift)
    @Test
    public void execute_undoRedoDeleteQuantityAndShift_success() throws CommandException, ParseException {
        Quantity quantityToDeduct = new Quantity("1");
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_THIRD_ITEM, quantityToDeduct);
        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_THIRD_ITEM.getZeroBased());
        Name itemName = xpireItemToDelete.getName();
        Set<Tag> itemTags = xpireItemToDelete.getTags();
        Item adaptedItem = new Item(itemName, itemTags);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.deleteItem(XPIRE, xpireItemToDelete);
        expectedModel.addItem(REPLENISH, adaptedItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);

    }

    //Testing Undo/Redo for SearchCommand
    @Test
    public void execute_undoRedoSearch_success() throws CommandException, ParseException {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList("Pineapple|Pear|#Cold"
                .split("\\|")));
        SearchCommand searchCommand = new SearchCommand(XPIRE, predicate);
        executeCommandAndUpdateStateManager(model, searchCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.filterCurrentList(XPIRE, predicate);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for SetReminderCommand
    @Test
    public void execute_undoRedoSetReminder_success() throws CommandException, ParseException {
        ReminderThreshold threshold = new ReminderThreshold("1");
        SetReminderCommand setReminderCommand = new SetReminderCommand(INDEX_SECOND_ITEM, threshold);
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        executeCommandAndUpdateStateManager(model, setReminderCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_BANANA)
                .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
                .withQuantity(VALID_QUANTITY_BANANA)
                .withReminderThreshold("1")
                .build();
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for ShiftToReplenishCommand
    @Test
    public void execute_undoRedoShiftToReplenish_success() throws CommandException, ParseException {
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(INDEX_SECOND_ITEM);
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        Name itemName = targetXpireItem.getName();
        Set<Tag> itemTags = targetXpireItem.getTags();
        Item adaptedItem = new Item(itemName, itemTags);
        executeCommandAndUpdateStateManager(model, shiftToReplenishCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.deleteItem(XPIRE, targetXpireItem);
        expectedModel.addItem(REPLENISH, adaptedItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for SortCommand
    @Test
    public void execute_undoRedoSort_success() throws CommandException, ParseException {
        XpireMethodOfSorting xpireMethodOfSorting = new XpireMethodOfSorting("date");
        SortCommand sortCommand = new SortCommand(xpireMethodOfSorting);
        executeCommandAndUpdateStateManager(model, sortCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.sortXpire(xpireMethodOfSorting);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for TagCommand
    @Test
    public void execute_undoRedoTag_success() throws CommandException, ParseException {
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        executeCommandAndUpdateStateManager(model, tagCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                .build();
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for ViewCommand
    @Test
    public void execute_undoRedoView_success() throws CommandException, ParseException {
        ViewCommand viewCommand = new ViewCommand(REPLENISH);
        executeCommandAndUpdateStateManager(model, viewCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.setCurrentList(REPLENISH);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //--------------------REPLENISH VIEW-----------------------------------------------------------------------
    //Testing Undo/Redo for ClearCommand
    @Test
    public void execute_undoRedoClearReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        ClearCommand clearCommand = new ClearCommand(REPLENISH);
        executeCommandAndUpdateStateManager(model, clearCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        ObservableList<? extends Item> currentList = FXCollections.observableArrayList(model.getCurrentList());
        currentList.forEach(item -> expectedModel.deleteItem(REPLENISH, item));
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for SearchCommand
    @Test
    public void execute_undoRedoSearchReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList("#Sweet|Bagel"
                .split("\\|")));
        SearchCommand searchCommand = new SearchCommand(REPLENISH, predicate);
        executeCommandAndUpdateStateManager(model, searchCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.filterCurrentList(REPLENISH, predicate);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for DeleteCommand(Item)
    @Test
    public void execute_undoRedoDeleteItemReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        Item itemToDelete = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.deleteItem(REPLENISH, itemToDelete);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for DeleteCommand(Tag)
    @Test
    public void execute_undoRedoDeleteTagsReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_SWEET));
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FOURTH_ITEM, set);
        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        Item itemToDelete = model.getCurrentList().get(INDEX_FOURTH_ITEM.getZeroBased());
        Item expectedItem = new ItemBuilder().withName(VALID_NAME_COOKIE)
                .build();
        expectedModel.setItem(REPLENISH, itemToDelete, expectedItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for TagCommand
    @Test
    public void execute_undoRedoTagReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        TagCommand tagCommand = new TagCommand(REPLENISH, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        executeCommandAndUpdateStateManager(model, tagCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        Item itemToDelete = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        Item expectedItem = new ItemBuilder().withName(VALID_NAME_BAGEL)
                .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                .build();
        expectedModel.setItem(REPLENISH, itemToDelete, expectedItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for ViewCommand
    @Test
    public void execute_undoRedoViewReplenish_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        ViewCommand viewCommand = new ViewCommand(XPIRE);
        executeCommandAndUpdateStateManager(model, viewCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        expectedModel.setCurrentList(XPIRE);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for ShiftToMainCommand
    @Test
    public void execute_undoRedoShiftToMain_success() throws CommandException, ParseException {
        model.setCurrentList(REPLENISH);
        ShiftToMainCommand shiftToMainCommand = new ShiftToMainCommand(INDEX_FIRST_ITEM,
                new ExpiryDate(IN_A_MONTH), new Quantity("3"));
        executeCommandAndUpdateStateManager(model, shiftToMainCommand, stateManager);
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        UndoCommand undoCommand = new UndoCommand();
        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);

        //Redo Portion
        RedoCommand redoCommand = new RedoCommand();
        Item itemToDelete = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_BAGEL)
                .withExpiryDate(IN_A_MONTH)
                .withQuantity("3")
                .build();
        expectedModel.deleteItem(REPLENISH, itemToDelete);
        expectedModel.addItem(XPIRE, expectedXpireItem);
        CommandResult expectedRedoMessage = new CommandResult(MESSAGE_REDO_SUCCESS);
        assertCommandSuccess(redoCommand, model, expectedRedoMessage, expectedModel, stateManager);
    }

    //Testing Undo/Redo for Multiple Commands
    @Test
    public void execute_undoRedoMultipleCommands_success() throws CommandException, ParseException {

        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRIDGE));
        set.add(new Tag(VALID_TAG_PROTEIN));
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FOURTH_ITEM, set);
        AddCommand addCommand = new AddCommand(new Name(VALID_NAME_APPLE), new ExpiryDate(VALID_EXPIRY_DATE_APPLE),
                new Quantity(VALID_QUANTITY_APPLE));
        ViewCommand viewCommand = new ViewCommand(REPLENISH);
        ShiftToMainCommand shiftToMainCommand = new ShiftToMainCommand(INDEX_FIRST_ITEM,
                new ExpiryDate(IN_A_MONTH), new Quantity("3"));

        executeCommandAndUpdateStateManager(model, deleteCommand, stateManager);
        executeCommandAndUpdateStateManager(model, addCommand, stateManager);
        executeCommandAndUpdateStateManager(model, viewCommand, stateManager);
        executeCommandAndUpdateStateManager(model, shiftToMainCommand, stateManager);

        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();

        executeCommandAndUpdateStateManager(model, undoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, undoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, undoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, redoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, redoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, undoCommand, stateManager);
        executeCommandAndUpdateStateManager(model, undoCommand, stateManager);

        CommandResult expectedMessage = new CommandResult(MESSAGE_UNDO_SUCCESS);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel, stateManager);
    }

}
