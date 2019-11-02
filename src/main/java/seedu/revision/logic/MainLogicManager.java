package seedu.revision.logic;

import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.MainParser;
import seedu.revision.logic.parser.quiz.QuizCommandParser;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.storage.Storage;

/**
 * The main MainLogicManager of the app.
 */
public class MainLogicManager implements MainLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private static final Logger logger = LogsCenter.getLogger(MainLogicManager.class);

    protected final Model model;
    private final Storage storage;
    private final MainParser mainParser;

    public MainLogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mainParser = new MainParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = mainParser.parseCommand(commandText);
        //Executes the Command and stores the result
        commandResult = command.execute(model);

        try {
            //We can deduce that the previous line of code modifies model in some way
            //since it's being stored here.
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    /**
     * Executes commands while the Quiz session in operation. Takes in user input and determines command to execute.
     * @param commandText The command as entered by the user.
     * @param currentAnswerable The current question to be responded to.
     * @return commandResult to be executed.
     * @throws ParseException
     * @throws CommandException
     */
    @Override
    public CommandResult execute(String commandText, Answerable currentAnswerable)
            throws ParseException, CommandException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        QuizCommandParser quizCommandParser = new QuizCommandParser();
        Command command = quizCommandParser.parseCommand(commandText, currentAnswerable);
        commandResult = command.execute(model);

        //If user exits the quiz, restore the filtered list to original state.
        if (commandResult.isExit()) {
            ListCommand restoreList = new ListCommand(PREDICATE_SHOW_ALL_ANSWERABLE);
            restoreList.execute(model);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Answerable> getFilteredAnswerableList() {
        return model.getFilteredAnswerableList();
    }

    @Override
    public ObservableList<Answerable> getFilteredSortedAnswerableList() {
        return model.getFilteredAnswerableList().sorted(Comparator.comparing(a -> a.getDifficulty()));
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
