package budgetbuddy.ui.tab;

import static java.util.Objects.requireNonNull;

import budgetbuddy.ui.panel.DisplayPanel;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * Represents an abstract tab component that displays its corresponding panel when selected.
 */
public abstract class PanelTab extends Tab {

    private DisplayPanel primaryPanel;
    private DisplayPanel secondaryPanel;
    private boolean isPrimaryPanelSelected;

    public PanelTab(DisplayPanel panel, String tabName) {
        this(panel, null, tabName);
    }

    public PanelTab(DisplayPanel primaryPanel, DisplayPanel secondaryPanel, String tabName) {
        this.primaryPanel = primaryPanel;
        this.secondaryPanel = secondaryPanel;
        this.isPrimaryPanelSelected = true;

        setClosable(false);
        setContent(primaryPanel.getRoot());

        // Create tab
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#3f556f")), new Stop(1, Color.web("#2c3b4d"))};
        LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        Rectangle rectangle = new Rectangle(145, 55, lg);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setVisible(false);

        Label label = new Label(tabName);

        StackPane tab = new StackPane(rectangle, label);
        setGraphic(tab);

        // Change effect depending on state
        setOnSelectionChanged(event -> {
            if (isSelected()) {
                rectangle.setVisible(true);
            } else {
                rectangle.setVisible(false);
            }
        });
    }

    public void setPrimaryPanel() {
        setContent(primaryPanel.getRoot());
        isPrimaryPanelSelected = true;
    }

    public void setSecondaryPanel() {
        requireNonNull(secondaryPanel);
        setContent(secondaryPanel.getRoot());
        isPrimaryPanelSelected = false;
    }

    public boolean hasSecondaryPanel() {
        return secondaryPanel != null;
    }

    public boolean isPrimaryPanelSelected() {
        return isPrimaryPanelSelected;
    }
}
