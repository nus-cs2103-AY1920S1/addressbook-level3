package dukecooks.logic.parser.exercise;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.exercise.EditExerciseDescriptorBuilder;

public class EditExerciseCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_DISTANCE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditExerciseCommand.MESSAGE_USAGE);

    private EditExerciseCommandParser parser = new EditExerciseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_PUSHUP, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditExerciseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_PUSHUP,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_PUSHUP,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 l/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC,
                ExerciseName.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_SETS_DESC,
                ExerciseDetail.MESSAGE_CONSTRAINTS); // invalid detail

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.SETS_DESC_FIVE
                + CommandTestUtil.REPS_DESC_SIXTY + TAG_EMPTY, ExerciseDetail.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.SETS_DESC_FIVE
                + TAG_EMPTY + CommandTestUtil.REPS_DESC_SIXTY, ExerciseDetail.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY
                + CommandTestUtil.SETS_DESC_FIVE + CommandTestUtil.REPS_DESC_SIXTY,
                ExerciseDetail.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC,
                ExerciseName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_EXERCISE;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.REPS_DESC_SIXTY
                 + CommandTestUtil.NAME_DESC_PUSHUP + CommandTestUtil.SETS_DESC_FIVE;

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_PUSHUP)
                .withDetails(null, null, null, null, CommandTestUtil.VALID_REPS_SIXTY, CommandTestUtil.VALID_SETS_FIVE)
                .build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_PUSHUP;
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_PUSHUP).build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.SETS_DESC_FIVE;
        descriptor = new EditExerciseDescriptorBuilder()
                .withDetails(null, null, null, null, null, CommandTestUtil.VALID_SETS_FIVE)
                .build();
        expectedCommand = new EditExerciseCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_EXERCISE;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.SETS_DESC_FIVE + CommandTestUtil.SETS_DESC_FIVE
                + CommandTestUtil.REPS_DESC_SIXTY;

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withDetails(null, null, null, null,
                        CommandTestUtil.VALID_REPS_SIXTY, CommandTestUtil.VALID_SETS_FIVE)
                .build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*
    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withDetails(null, null, null, null, null, null)
                .build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    */
}
