package seedu.revision.logic;


import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.parser.quiz.QuizParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.storage.Storage;


/**
 * The main QuizManager of the app.
 */
public class QuizLogicManager implements QuizLogic {
    private final Logger logger = LogsCenter.getLogger(MainLogicManager.class);

    protected final Model model;
    private final Storage storage;
    private final QuizParser quizParser;

    public QuizLogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        quizParser = new QuizParser();
    }


    public CommandResult execute(String commandText, Answerable currentAnswerable)
            throws ParseException, CommandException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = quizParser.parseCommand(commandText, currentAnswerable);
        commandResult = command.execute(model);

        //If user exits the quiz, restore the filtered list to original state.
        if (commandResult.isExit()) {
            ListCommand originalList = new ListCommand(null, null);
            originalList.execute(model);
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
