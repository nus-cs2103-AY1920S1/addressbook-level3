package seedu.ezwatchlist.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the loading panel that is displayed while api is loading.
 */
public class LoadingPanel extends UiPart<Region> {
    private static final String FXML = "LoadingPanel.fxml";

    @FXML
    private StackPane loadingView;

    public LoadingPanel() {
        super(FXML);
    }
}
