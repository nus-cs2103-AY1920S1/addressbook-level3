package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.main.StatsCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.StatsCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.quiz.Statistics;

public class StatsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Model actualModel = new ModelManager();
    private Statistics statistics = new Statistics("25/25,10/10,8/8,7/7");


    @Test
    public void execute_statsEmptyModel_success() throws CommandException, ParseException {
        CommandResult expectedCommandResult = new CommandResultBuilder()
                .withFeedBack("You have not attempted any quizzes yet!").build();
        assertCommandSuccess(new StatsCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_statsActualModel_success() throws CommandException, ParseException {
        this.actualModel.addStatistics(statistics);
        CommandResult expectedCommandResult = new CommandResultBuilder()
                .withFeedBack(MESSAGE_SUCCESS).withStats(true).build();
        assertCommandSuccess(new StatsCommand(), actualModel, expectedCommandResult, actualModel);
    }
}
