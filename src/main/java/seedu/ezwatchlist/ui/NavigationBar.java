package seedu.ezwatchlist.ui;

import javafx.scene.layout.Region;

/**
 * A ui for the navigation bar that is displayed at the side of the application.
 */
public class NavigationBar extends UiPart<Region> {
    private static final String FXML = "NavigationBar.fxml";

    public NavigationBar() {
        super(FXML);
    }

}
