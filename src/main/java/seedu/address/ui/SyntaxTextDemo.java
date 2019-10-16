package seedu.address.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Stub. to be deleted.
 */
public class SyntaxTextDemo extends Application {

    /**
     * Stub.
     */
    public void start(Stage stage) {
        SyntaxHighlightTextArea inlineCssTextArea = new SyntaxHighlightTextArea();
        inlineCssTextArea.replaceText("");

        StackPane s = new StackPane(inlineCssTextArea);

        Scene scene = new Scene(s);
        inlineCssTextArea.importStyleSheet();

        stage.setScene(scene);
        stage.show();

    }
}
