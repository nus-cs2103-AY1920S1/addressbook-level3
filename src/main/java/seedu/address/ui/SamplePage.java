package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * Sample page template to create a new place when required.
 */
// todo: remove this class when everyone has implemented their page
public class SamplePage implements Page {
    private static final PageType pageType = PageType.SAMPLE;

    private Scene sampleScene = new Scene(new VBox());

    public PageType getPageType() {
        return pageType;
    }

    public Scene getScene() {
        return sampleScene;
    }
}

