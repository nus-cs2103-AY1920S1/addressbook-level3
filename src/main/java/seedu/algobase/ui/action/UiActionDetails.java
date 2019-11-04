package seedu.algobase.ui.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores the details of a {@code UiAction}.
 */
public class UiActionDetails {
    private final UiActionType actionType;
    private final List<Object> internalList;

    public UiActionDetails(UiActionType uiActionType, Object... objects) {
        this.actionType = uiActionType;
        this.internalList = new ArrayList<Object>();
        add(objects);
    }

    public void add(Object... objects) {
        this.internalList.addAll(Arrays.asList(objects));
    }

    public Object get(int index) {
        return internalList.get(index);
    }

    /**
     * Getter for the word of the action.
     */
    public UiActionType getActionWord() {
        return this.actionType;
    }

    /**
     * Makes a copy of the current UiActionDetails object.
     */
    public UiActionDetails copy() {
        UiActionDetails uiActionDetails = new UiActionDetails(actionType, internalList);
        return uiActionDetails;
    }
}
