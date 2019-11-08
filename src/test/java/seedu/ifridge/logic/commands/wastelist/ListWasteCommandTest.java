package seedu.ifridge.logic.commands.wastelist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_NEXT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_NOT_IN_ARCHIVE;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;

public class ListWasteCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
    }

    @Test
    public void execute_wasteMonthInArchive_success() {
        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
        expectedModel.updateFilteredWasteItemList(WASTE_MONTH_CURRENT_MONTH);
        assertCommandSuccess(new ListWasteCommand(WASTE_MONTH_CURRENT_MONTH), model,
                String.format(ListWasteCommand.MESSAGE_SUCCESS, WASTE_MONTH_CURRENT_MONTH), expectedModel);
    }

    @Test
    public void execute_wasteMonthNotInArchive_throwsCommandException() {
        assertCommandFailure(new ListWasteCommand(WASTE_MONTH_NOT_IN_ARCHIVE), model,
                String.format(ListWasteCommand.MESSAGE_NO_WASTE_LIST_FOUND, WASTE_MONTH_NOT_IN_ARCHIVE));
    }

    @Test
    public void execute_wasteMonthAfterCurrentMonth_throwsCommandException() {
        assertCommandFailure(new ListWasteCommand(WASTE_MONTH_NEXT_MONTH), model,
                ListWasteCommand.MESSAGE_MONTH_RESTRICTION);
    }

}
