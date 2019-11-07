package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class AutofillSuggestionMenuTest extends ApplicationTest {

    private AutofillSuggestionMenu sutEmpty;

    private static final String command = "COMMAND";

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        StyleClassedTextArea stubToNotShow = new StyleClassedTextArea();
        stage.setScene(new Scene(new VBox(stubToNotShow)));
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        stubToNotShow.setContextMenu(sutEmpty);
        sutEmpty = new AutofillSuggestionMenu(stubToNotShow, commandPropertyStub);
        stage.show();
    }

    @Test
    void withNoCommand_noMatch() {
        new FxRobot().write(command);
        Platform.runLater(() -> assertFalse(sutEmpty.isShowing()));
    }

}