package seedu.address.logic;

import java.util.List;
import java.util.ArrayList;
import seedu.address.logic.actions.Action;
import seedu.address.logic.actions.ModeAction;
import seedu.address.logic.actions.AutoFillAction;

/**
 * The main CommandBoxManager of the app.
 */
public class CommandBoxHelper {

    public CommandBoxHelper() {

    }

    public List<AutoFillAction> getMenuItems(String text) {
        List<AutoFillAction> temp = new ArrayList<>();
        temp.add(new AutoFillAction());
        temp.add(new AutoFillAction());
        return temp;
    }

    public ModeAction getMode() {
        return null;
    }


}
