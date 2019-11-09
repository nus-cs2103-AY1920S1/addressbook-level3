package seedu.exercise.ui.util;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * Represents a utility class that contains methods that acts on a {@code Label} object.
 */
public class LabelUtil {

    public static void setLabelTooltip(Label label) {
        Tooltip tooltip = new Tooltip(label.getText());
        label.setTooltip(tooltip);
    }
}
