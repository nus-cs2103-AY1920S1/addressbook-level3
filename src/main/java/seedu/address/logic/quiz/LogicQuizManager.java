package seedu.address.logic.quiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.quiz.commands.Command;
import seedu.address.logic.quiz.commands.CommandResult;
import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.logic.quiz.parser.QuizBookParser;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ReadOnlyQuizBook;
import seedu.address.model.quiz.person.Question;
import seedu.address.storage.quiz.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicQuizManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicQuizManager.class);

    private final Model model;
    private final Storage storage;
    private final QuizBookParser quizBookParser;

    public LogicQuizManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        quizBookParser = new QuizBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        // Parse user input from String to a Command
        Command command = quizBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            // We can deduce that the previous line of code modifies model in some way
            // since it's being stored here.
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyQuizBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public int getQuestionNumber() {
        return model.getQuestionNumber();
    }

    @Override
    public boolean getShowAnswer() {
        return model.getShowAnswer();
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return model.getFilteredQuestionList();
    }

    @Override
    public ObservableList<Question> getFilteredShowQuestionList() {
        return model.getFilteredShowQuestionList();
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
}
