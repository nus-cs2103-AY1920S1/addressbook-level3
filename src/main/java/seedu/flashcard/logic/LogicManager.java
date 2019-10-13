package seedu.flashcard.logic;

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
import seedu.flashcard.storage.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The main logic manager of the system.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Storage storage;
    private final FlashcardListParser flashcardListParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        flashcardListParser = new FlashcardListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = flashcardListParser.parseCommand(commandText);
        CommandResult commandResult = command.execute(model);
        try {
            storage.saveFlashcardList(model.getFlashcardList());
        } catch (IOException e) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + e, e);
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
}
