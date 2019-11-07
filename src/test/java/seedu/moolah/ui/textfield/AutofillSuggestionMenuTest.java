package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class AutofillSuggestionMenuTest extends ApplicationTest {

    private AutofillSuggestionMenu menuWithNoSupportedCommands;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        CommandTextField textArea = new CommandTextField(string -> {
        });
        stage.setScene(new Scene(new VBox(textArea)));
        SimpleStringProperty commandPropertyStub = new SimpleStringProperty("");
        textArea.setContextMenu(menuWithNoSupportedCommands);
        menuWithNoSupportedCommands = new AutofillSuggestionMenu(textArea, commandPropertyStub);
        stage.show();
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    void withNoCommand_noShow() {
        assertFalse(menuWithNoSupportedCommands.enabledProperty().get());
        menuWithNoSupportedCommands.toggle();
        assertTrue(menuWithNoSupportedCommands.enabledProperty().get());
        Platform.runLater(() -> assertFalse(menuWithNoSupportedCommands.isShowing()));
    }
}
