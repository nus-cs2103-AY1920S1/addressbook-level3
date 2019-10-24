package dukecooks.logic.parser.exercise;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.exercise.FindExerciseCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.workout.exercise.components.ExerciseNameContainsKeywordsPredicate;

public class FindExerciseCommandParserTest {

    private FindExerciseCommandParser parser = new FindExerciseCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String
                .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindExerciseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindExerciseCommand expectedFindExerciseCommand =
                new FindExerciseCommand(new ExerciseNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Bob", expectedFindExerciseCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindExerciseCommand);
    }

}
