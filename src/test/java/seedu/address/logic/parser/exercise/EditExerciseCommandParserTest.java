package seedu.address.logic.parser.exercise;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOOD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SETS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.REPS_DESC_SIXTY;
import static seedu.address.logic.commands.CommandTestUtil.SETS_DESC_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPS_SIXTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SETS_FIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exercise.EditExerciseCommand;
import seedu.address.logic.commands.exercise.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.model.exercise.components.ExerciseName;
import seedu.address.model.exercise.details.ExerciseDetail;
import seedu.address.testutil.exercise.EditExerciseDescriptorBuilder;

public class EditExerciseCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_DISTANCE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExerciseCommand.MESSAGE_USAGE);

    private EditExerciseCommandParser parser = new EditExerciseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_PUSHUP, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExerciseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_PUSHUP, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_PUSHUP, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_FOOD_NAME_DESC, ExerciseName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_SETS_DESC, ExerciseDetail.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + SETS_DESC_FIVE
                + REPS_DESC_SIXTY + TAG_EMPTY, ExerciseDetail.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SETS_DESC_FIVE
                + TAG_EMPTY + REPS_DESC_SIXTY, ExerciseDetail.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY
                + SETS_DESC_FIVE + REPS_DESC_SIXTY, ExerciseDetail.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_FOOD_NAME_DESC,
                ExerciseName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EXERCISE;
        String userInput = targetIndex.getOneBased() + REPS_DESC_SIXTY
                 + NAME_DESC_PUSHUP + SETS_DESC_FIVE;

        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder().withName(VALID_NAME_PUSHUP)
                .withDetails(null, null, null, null, VALID_REPS_SIXTY, VALID_SETS_FIVE)
                .build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PUSHUP;
        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder().withName(VALID_NAME_PUSHUP).build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + SETS_DESC_FIVE;
        descriptor = new EditExerciseDescriptorBuilder()
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();
        expectedCommand = new EditExerciseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EXERCISE;
        String userInput = targetIndex.getOneBased()
                + SETS_DESC_FIVE + SETS_DESC_FIVE
                + REPS_DESC_SIXTY;

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withDetails(null, null, null, null,
                        VALID_REPS_SIXTY, VALID_SETS_FIVE)
                .build();
        EditExerciseCommand expectedCommand = new EditExerciseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

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
}
