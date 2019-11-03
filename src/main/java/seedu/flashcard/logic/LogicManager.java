package seedu.flashcard.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.logic.commands.Command;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.FlashcardListParser;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.Statistics;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.storage.Storage;

/**
 * The main logic manager of the system.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final FlashcardListParser flashcardListParser;
    private boolean flashcardListModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        flashcardListParser = new FlashcardListParser();

        // Set flashcardListModified to true whenever the models' flashcard list is modified.
        model.getFlashcardList().addListener(observable -> flashcardListModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        flashcardListModified = false;

        CommandResult commandResult;
        try {
            Command command = flashcardListParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (flashcardListModified) {
            logger.info("Flashcard List modified, saving to file.");
            try {
                storage.saveFlashcardList(model.getFlashcardList());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
        return commandResult;
    }

    @Override
    public ReadOnlyFlashcardList getFlashcardList() {
        return model.getFlashcardList();
    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return model.getFilteredFlashcardList();
    }

    @Override
    public Path getFlashcardListFilePath() {
        return model.getFlashcardListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public Statistics getStatistics () {
        return model.getStatistics();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }
}
