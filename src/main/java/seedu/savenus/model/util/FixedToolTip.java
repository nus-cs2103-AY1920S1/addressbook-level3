package seedu.savenus.model.util;

import javafx.scene.control.Tooltip;
import javafx.stage.Window;

//@@author robytanama
/**
 * This class is improved version of tooltip to prevent other windows from being hidden when displaying tooltip.
 */
public class FixedToolTip extends Tooltip {

    /**
     * Creates the new improved tool tip.
     * @param string The text to be displayed.
     */
    public FixedToolTip(String string) {
        super(string);
    }

    @Override
    protected void show() {
        Window owner = getOwnerWindow();
        if (owner.isFocused()) {
            super.show();
        }
    }
}
