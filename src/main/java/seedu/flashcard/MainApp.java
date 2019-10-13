package seedu.flashcard;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.flashcard.logic.Logic;
import seedu.flashcard.model.Model;
import seedu.flashcard.storage.Storage;
import seedu.flashcard.ui.Ui;

import java.util.logging.Logger;

/**
 * Runs the application and controls the general execution logic of the whole program.
 */
public class MainApp extends Application {

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;

    @Override
    public void start(Stage stage) throws Exception {
        super.init();

    }
}
