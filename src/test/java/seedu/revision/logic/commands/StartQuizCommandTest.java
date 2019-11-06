package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.Messages;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.quiz.ArcadeMode;
import seedu.revision.model.quiz.CustomMode;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;

public class StartQuizCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Mode modeNormal = new NormalMode();
    private Mode modeArcade = new ArcadeMode();
    private Mode modeCustom = new CustomMode();

    @Test
    public void execute_startNormal_success() throws ParseException {
        CommandResult expectedCommandResult = new CommandResult().withFeedBack(
                String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW,
                model.getFilteredAnswerableList().size())).withStart(true).withMode(modeNormal).build();

        assertCommandSuccess(new StartQuizCommand(modeNormal), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_startArcade_success() throws ParseException {
        CommandResult expectedCommandResult = new CommandResult().withFeedBack(
                String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW,
                        model.getFilteredAnswerableList().size())).withStart(true).withMode(modeArcade).build();

        assertCommandSuccess(new StartQuizCommand(modeArcade), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_startCustom_success() throws ParseException {
        CommandResult expectedCommandResult = new CommandResult().withFeedBack(
                String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW,
                        model.getFilteredAnswerableList().size())).withStart(true).withMode(modeCustom).build();

        assertCommandSuccess(new StartQuizCommand(modeCustom), model, expectedCommandResult, expectedModel);
    }
}
