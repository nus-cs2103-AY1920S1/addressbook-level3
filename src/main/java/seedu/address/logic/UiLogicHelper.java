// @@author sreesubbash
package seedu.address.logic;

import java.util.List;

import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;

/**
 * API of the Ui Logic Helper
 */
public interface UiLogicHelper {

    public List<AutoFillAction> getMenuItems(String text);

    public ModeEnum getMode();

    public List<ModeEnum> getModes();

}
