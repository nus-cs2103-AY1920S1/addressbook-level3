package seedu.address.ui;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.logic.parser.Prefix;

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
        inlineCssTextArea.importStyleSheet(scene);
        inlineCssTextArea.createPattern("hi", List.of(new Prefix("d/")), "hi d/ <a>");
        inlineCssTextArea.enableSyntaxHighlighting();

        stage.setScene(scene);
        stage.show();

    }
}
