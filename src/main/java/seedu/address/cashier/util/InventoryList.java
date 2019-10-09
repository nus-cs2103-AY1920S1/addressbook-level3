package seedu.address.cashier.util;

import java.util.ArrayList;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.model.Item;

public class InventoryList {

    private static ArrayList<Item> iList;

    public InventoryList() {
        iList = new ArrayList<>();
    }

    public InventoryList(ArrayList<Item> iList) {
        this.iList = iList;
    }

    public int getIndex(String description) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return i;
            }
        }
        throw new NoSuchItemException(CashierUi.NO_SUCH_ITEM_CASHIER);
    }

    public static Item getOriginalItem(String description) throws seedu.address.inventory.model.exception.NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return iList.get(i);
            }
        }
        throw new seedu.address.inventory.model.exception.NoSuchItemException(CashierUi.NO_SUCH_ITEM_CASHIER);
    }

    public static Item getOriginalItem(Item item) throws seedu.address.inventory.model.exception.NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).isSameItem(item)) {
                return iList.get(i);
            }
        }
        throw new seedu.address.inventory.model.exception.NoSuchItemException(CashierUi.NO_SUCH_ITEM_CASHIER);
    }

    public static Item getItemByIndex(int index) throws NoSuchIndexException {
        if (index >= iList.size()) {
            throw new NoSuchIndexException(CashierUi.NO_SUCH_INDEX_CASHIER);
        } else {
            return iList.get(index);
        }
    }

    public static int size() {
        return iList.size();
    }


    public void set(int i, Item item) {
        iList.set(i, item);
    }

}
