package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.moolah.logic.parser.Prefix;

/**
 * AutofillSuggestionMenuTest for AutofillSuggestionMenu with supported command which is to be removed.
 */
class AutofillSuggestionMenuTest3 extends ApplicationTest {

    private static final String COMMAND = "COMMAND";
    private static final List<Prefix> REQUIRED = new ArrayList<>();
    private static final List<Prefix> OPTIONAL = new ArrayList<>();
    private AutofillSuggestionMenu menuWithCommandToBeRemoved;
    private CommandTextField stubToRemove;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stubToRemove = new CommandTextField(string -> {
        });
        stage.setScene(new Scene(new VBox(stubToRemove)));
        stubToRemove.setContextMenu(menuWithCommandToBeRemoved);
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        menuWithCommandToBeRemoved = new AutofillSuggestionMenu(stubToRemove, commandPropertyStub);
        menuWithCommandToBeRemoved.addCommand(COMMAND, REQUIRED, OPTIONAL);
        stage.show();
    }

    @Test
    void removeCommand_doesNotShowAfterRemoving() {
        menuWithCommandToBeRemoved.toggle();
        menuWithCommandToBeRemoved.removeCommand(COMMAND);
        new FxRobot().write(COMMAND);
        Platform.runLater(() -> assertFalse(menuWithCommandToBeRemoved.isShowing()));
    }

}
