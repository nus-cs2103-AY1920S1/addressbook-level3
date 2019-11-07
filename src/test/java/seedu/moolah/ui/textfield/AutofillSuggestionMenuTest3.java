package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

class AutofillSuggestionMenuTest3 extends ApplicationTest {

    private AutofillSuggestionMenu sutWithOneCommandToBeRemoved;

    private static final String command = "COMMAND";
    private static final List<Prefix> required = new ArrayList<>();
    private static final List<Prefix> optional = new ArrayList<>();

    private StyleClassedTextArea stubToRemove;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stubToRemove = new StyleClassedTextArea();
        stage.setScene(new Scene(new VBox(stubToRemove)));
        stubToRemove.setContextMenu(sutWithOneCommandToBeRemoved);
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        sutWithOneCommandToBeRemoved = new AutofillSuggestionMenu(stubToRemove, commandPropertyStub);
        sutWithOneCommandToBeRemoved.addCommand(command, required, optional);
        stage.show();
    }

    @Test
    void removeCommand_doesNotShowAfterRemoving() {
        sutWithOneCommandToBeRemoved.removeCommand(command);
        new FxRobot().write(command);
        Platform.runLater(() -> assertFalse(sutWithOneCommandToBeRemoved.isShowing()));
    }

}