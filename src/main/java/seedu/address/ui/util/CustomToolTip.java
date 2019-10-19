package seedu.address.ui.util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A class to construct tool tip for free time slots.
 */
public class CustomToolTip {
    private ContextMenu tooltip;
    private ImageView imageView;
    private HBox toolTipContainer;
    private VBox details;
    public CustomToolTip(ImageView imageView, List<String> locationDetails) {
        //this.imageView = imageView;
        this.toolTipContainer = new HBox();
        this.details = new VBox();
        //toolTipContainer.getChildren().add(imageView);
        for (String s : locationDetails) {
            details.getChildren().add(new Label(s));
        }
        MenuItem tooltipContent = new MenuItem();
        tooltipContent.setGraphic(details);
        this.tooltip = new ContextMenu();
        tooltip.getItems().add(tooltipContent);
    }

    public ContextMenu getTooltip() {
        return tooltip;
    }
}
