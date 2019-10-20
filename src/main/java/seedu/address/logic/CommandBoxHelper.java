package seedu.address.logic;

import java.util.List;
import java.util.ArrayList;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.actions.Action;
import seedu.address.logic.actions.ModeAction;
import seedu.address.logic.actions.AutoFillAction;
import seedu.address.logic.Logic;

/**
 * The main CommandBoxManager of the app.
 */
public class CommandBoxHelper {

    private Logic logic;

    public CommandBoxHelper(Logic logic) {
        this.logic = logic;
    }

    public List<AutoFillAction> getMenuItems(String text) {
        List<AutoFillAction> temp = new ArrayList<>();
        temp.add(new AutoFillAction());
        temp.add(new AutoFillAction());
        return temp;
    }

    public ModeEnum getMode() {
        ModeEnum temp = logic.getMode();
        return temp;
    }

    public List<ModeEnum> getModes() {
        List<ModeEnum> temp = new ArrayList<>();
        temp.add(ModeEnum.APP);
        temp.add(ModeEnum.LOAD);
        temp.add(ModeEnum.GAME);
        temp.add(ModeEnum.SETTINGS);
        return temp;
    }


}
