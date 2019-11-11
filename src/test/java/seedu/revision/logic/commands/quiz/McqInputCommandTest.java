package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.COMMAND_RESULT_BUILDER_CORRECT_HELP_FALSE_EXIT_FALSE;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A_COMMAND;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.answerable.Answer;


public class McqInputCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @Test
    public void execute_mcqCorrectAnswer_feedbackCorrect() throws ParseException {
        String rightOption = getRightOption();
        String rightOptionUppercase = rightOption.toUpperCase();

        Command command = new McqInputCommand(rightOption, MCQ_A);
        Command commandUpperCase = new McqInputCommand(rightOptionUppercase, MCQ_A);
        CommandResult expectedCommandResult = new CommandResult(COMMAND_RESULT_BUILDER_CORRECT_HELP_FALSE_EXIT_FALSE);

        try {
            assertCommandSuccess(command, model,
                    expectedCommandResult, expectedModel);
            assertCommandSuccess(commandUpperCase, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_mcqWrongAnswer_feedbackWrong() throws ParseException {
        ArrayList<String> wrongOptionsList = getWrongOptionsList();
        String firstWrongOption = wrongOptionsList.get(0);


        Command command = new McqInputCommand(firstWrongOption, MCQ_A);
        Command commandUppercase = new McqInputCommand(firstWrongOption.toUpperCase(), MCQ_A);
        CommandResult expectedCommandResult = new CommandResult(COMMAND_RESULT_BUILDER_CORRECT_HELP_FALSE_EXIT_FALSE);

        try {
            assertCommandSuccess(command, model,
                    expectedCommandResult, expectedModel);
            assertCommandSuccess(commandUppercase, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }


    @Test
    public void equals() {
        // same values -> returns true
        Command commandCopy = new McqInputCommand("a", MCQ_A);
        assertTrue(MCQ_A_COMMAND.equals(commandCopy));

        // same object -> returns true
        assertTrue(commandCopy.equals(commandCopy));

        assertFalse(commandCopy.equals(null));

        // different type -> returns false
        assertFalse(commandCopy.equals(10));
        assertFalse(commandCopy.equals("TestString"));
    }

    private String getRightOption() {
        Answer correctAnswer = MCQ_A.getCorrectAnswerList().get(0);
        int index = MCQ_A.getCombinedAnswerList().indexOf(correctAnswer);
        String rightOption;
        switch (index) {
        case 0:
            rightOption = "a";
            break;
        case 1:
            rightOption = "b";
            break;
        case 2:
            rightOption = "c";
            break;
        case 3:
            rightOption = "d";
            break;
        default:
            rightOption = null;
        }
        requireNonNull(rightOption);
        return rightOption;
    }

    private ArrayList<String> getWrongOptionsList() {
        String correctOption = getRightOption();
        ArrayList<String> wrongOptionsList = new ArrayList<>();
        wrongOptionsList.add("a");
        wrongOptionsList.add("b");
        wrongOptionsList.add("c");
        wrongOptionsList.add("d");
        wrongOptionsList.remove(correctOption);

        assertEquals(3, wrongOptionsList.size());
        return wrongOptionsList;
    }
}
