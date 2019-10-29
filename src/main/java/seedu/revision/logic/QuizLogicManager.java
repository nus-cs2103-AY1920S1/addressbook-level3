package seedu.revision.logic;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.quiz.QuizCommandParser;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.storage.Storage;

import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;


/**
 * The main QuizManager of the app.
 */
public class QuizLogicManager implements QuizLogic {
    private final Logger logger = LogsCenter.getLogger(MainLogicManager.class);

    private final Model model;
    private final Storage storage;
    private final QuizCommandParser quizCommandParser;

    public QuizLogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        quizCommandParser = new QuizCommandParser();
    }

    /**
     * Quiz session in operation. Takes in user input and determines command to execute.
     * @param commandText The command as entered by the user.
     * @param currentAnswerable The current question to be responded to.
     * @return commandResult to be executed.
     * @throws ParseException
     * @throws CommandException
     */
    public CommandResult execute(String commandText, Answerable currentAnswerable)
            throws ParseException, CommandException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = quizCommandParser.parseCommand(commandText, currentAnswerable);
        commandResult = command.execute(model);

        //If user exits the quiz, restore the filtered list to original state.
        if (commandResult.isExit()) {
            ListCommand restoreList = new ListCommand(PREDICATE_SHOW_ALL_ANSWERABLE);
            restoreList.execute(model);
        }

        return commandResult;
    }

    public Model getModel() {
        return model;
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
    public ObservableList<Answerable> getFilteredSortedAnswerableList(Comparator<Answerable> comparator) {
        return model.getFilteredSortedAnswerableList(comparator);
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
