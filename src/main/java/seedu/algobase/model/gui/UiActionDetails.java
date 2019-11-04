package seedu.algobase.model.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores the details of a {@code UiAction}.
 */
public class UiActionDetails {
    private final List<Object> internalList;

    public UiActionDetails() {
        this.internalList = new ArrayList<Object>();
    }

    public void add(Object... objects) {
        this.internalList.addAll(Arrays.asList(objects));
    }

    public Object get(int index) {
        return internalList.get(index);
    }

    /**
     * Makes a copy of the current UiActionDetails object.
     */
    public UiActionDetails copy() {
        UiActionDetails uiActionDetails = new UiActionDetails();
        uiActionDetails.add(internalList);
        return uiActionDetails;
    }
}
