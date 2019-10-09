package tagline.ui;

import javafx.scene.layout.VBox;

/**
 * Abstract class for a UI component that displays a command result.
 */
public abstract class ResultView extends UiPart<VBox> {
    public ResultView(String fxml) {
        super(fxml);
    }
}
