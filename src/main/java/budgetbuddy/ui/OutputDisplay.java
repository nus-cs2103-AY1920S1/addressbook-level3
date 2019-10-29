package budgetbuddy.ui;

import static javafx.scene.control.TabPane.STYLE_CLASS_FLOATING;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Represents the output display component that shows the appropriate panels.
 */
public class OutputDisplay extends UiPart<TabPane> {

    private static final String FXML = "OutputDisplay.fxml";

    public OutputDisplay(Tab... tabs) {
        super(FXML);
        getRoot().getStyleClass().add(STYLE_CLASS_FLOATING);
        getRoot().getTabs().addAll(tabs);
    }
}
