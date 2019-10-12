package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

// todo: remove this class when everyone has implemented their page
public class SamplePage {
    static Scene getScene() {
        Scene newScene = new Scene(new VBox());
        return newScene;
    }

    // todo-this-week: toString value should be overwritten to match SampleCommand's feedback
    @Override
    public String toString() {
        return "sample";
    }
}
