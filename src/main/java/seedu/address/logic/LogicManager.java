package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.quiz.QuizParser;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.note.Note;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static boolean isQuiz = false;
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);


    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final QuizParser quizParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        quizParser = new QuizParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        if (!isQuiz) {
            logger.info("----------------[USER COMMAND][" + commandText + "]");

            CommandResult commandResult;
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model);

            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }

            return commandResult;
        } else {
            logger.info("----------------[USER INPUT][" + commandText + "]");

            CommandResult commandResult;
            Command command = quizParser.parseCommand(commandText);
            commandResult = command.execute(model);

            return commandResult;
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return model.getFilteredNoteList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    public void setIsQuiz(boolean isQuiz) {
        LogicManager.isQuiz = isQuiz;
    }

    public boolean getIsQuiz() {
        return isQuiz;
    }
}
