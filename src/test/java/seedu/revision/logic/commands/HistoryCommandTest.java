package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.main.HistoryCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.HistoryCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.quiz.Statistics;

public class HistoryCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Model actualModel = new ModelManager();
    private Statistics statistics = new Statistics("25/25,10/10,8/8,7/7");

    @Test
    public void execute_historyEmptyModel_success() throws CommandException, ParseException {
        CommandResult expectedCommandResult = new CommandResultBuilder()
                .withFeedBack("You have not attempted any quizzes yet!").build();
        assertCommandSuccess(new HistoryCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_historyActualModel_success() throws CommandException, ParseException {
        this.actualModel.addStatistics(statistics);
        CommandResult expectedCommandResult = new CommandResultBuilder()
                .withFeedBack(String.format(MESSAGE_SUCCESS + actualModel.getStatisticsList()
                        + "\nYou have attempted " + actualModel.getStatisticsList().size()
                        + " quizzes so far.")).withHistory(true).build();
        assertCommandSuccess(new HistoryCommand(), actualModel, expectedCommandResult, actualModel);
    }
}
