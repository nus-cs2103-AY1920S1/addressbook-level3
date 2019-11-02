package budgetbuddy.ui.tab;

import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.util.AppUtil;
import budgetbuddy.ui.panel.DisplayPanel;
import javafx.scene.control.Tab;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

/**
 * Represents an abstract tab component that displays its corresponding panel when selected.
 */
public abstract class PanelTab extends Tab {

    private DisplayPanel primaryPanel;
    private DisplayPanel secondaryPanel;

    public PanelTab(DisplayPanel panel, String imageFileName) {
        this(panel, null, imageFileName);
    }

    public PanelTab(DisplayPanel primaryPanel, DisplayPanel secondaryPanel, String imageFileName) {
        this.primaryPanel = primaryPanel;
        this.secondaryPanel = secondaryPanel;

        setClosable(false);
        setContent(primaryPanel.getRoot());

        // Create image
        ImageView currImage = new ImageView(AppUtil.getImage(imageFileName));
        ColorAdjust desaturateEffect = new ColorAdjust(0, -1, 0, 0);
        ColorAdjust saturateEffect = new ColorAdjust(0, 0, 0, 0);
        currImage.setEffect(desaturateEffect);
        setGraphic(currImage);

        // Change effect depending on state
        setOnSelectionChanged(event -> {
            if (isSelected()) {
                currImage.setEffect(saturateEffect);
            } else {
                currImage.setEffect(desaturateEffect);
            }
        });
    }

    public void setPrimaryPanel() {
        setContent(primaryPanel.getRoot());
    }

    public void setSecondaryPanel() {
        requireNonNull(secondaryPanel);
        setContent(secondaryPanel.getRoot());
    }
}
