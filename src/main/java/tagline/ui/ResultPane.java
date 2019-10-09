package tagline.ui;

import javafx.scene.layout.VBox;

/**
 * Abstract class for a UI component that displays a command result.
 */
public abstract class ResultPane extends UiPart<VBox> {
    public ResultPane(String fxml) {
        super(fxml);
    }
}
