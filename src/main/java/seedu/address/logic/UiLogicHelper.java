package seedu.address.logic;

import java.util.List;
import java.util.ArrayList;
import seedu.address.logic.util.ModeEnum;
import seedu.address.logic.util.Action;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.Logic;

/**
 * API of the Ui Logic Helper
 */
public interface UiLogicHelper {

    public List<AutoFillAction> getMenuItems(String text);

    public ModeEnum getMode();

    public List<ModeEnum> getModes();

}
