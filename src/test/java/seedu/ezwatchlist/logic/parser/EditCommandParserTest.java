package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_SHOW_TYPE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.*;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_THIRD_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ACTOR_EMPTY = " " + PREFIX_ACTOR;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ANNABELLE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        /*assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_IS_WATCHED_DESC, MESSAGE_INVALID_SHOW_TYPE); // invalid type
        assertParseFailure(parser, "1" + INVALID_RUNNING_TIME_DESC, RunningTime.MESSAGE_CONSTRAINTS); // invalid running time
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description

        // invalid running time followed by valid type
        assertParseFailure(parser, "1" + INVALID_RUNNING_TIME_DESC + WATCHED_DESC_ANNABELLE, RunningTime.MESSAGE_CONSTRAINTS);

        // valid running time followed by invalid running time. The test case for invalid running time followed by valid running time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + RUNNING_TIME_DESC_ANNABELLE + INVALID_RUNNING_TIME_DESC, RunningTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ACTOR} alone will reset the actors of the {@code Show} being edited,
        // parsing it together with a valid show results in error
        assertParseFailure(parser, "1" + ACTOR_DESC_ANNABELLE + ACTOR_DESC_BOB_THE_BUILDER + ACTOR_EMPTY, Actor.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ACTOR_DESC_BOB_THE_BUILDER + ACTOR_EMPTY + ACTOR_DESC_BOB_THE_BUILDER, Actor.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ACTOR_EMPTY + ACTOR_DESC_ANNABELLE + ACTOR_DESC_BOB_THE_BUILDER, Actor.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC + INVALID_IS_WATCHED_DESC + VALID_DESCRIPTION_ANNABELLE + VALID_ACTOR_ANNABELLE,
                MESSAGE_INVALID_SHOW_TYPE);

         */
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SHOW;

        String userInput = targetIndex.getOneBased() + TYPE_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER
                            + ACTOR_DESC_ANNABELLE + DATE_DESC_ANNABELLE + WATCHED_DESC_ANNABELLE
                            + NAME_DESC_ANNABELLE + ACTOR_DESC_BOB_THE_BUILDER;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_ANNABELLE)
                .withType(VALID_TYPE_BOB_THE_BUILDER).withDateOfRelease(VALID_DATE_ANNABELLE)
                .withIsWatched(VALID_WATCHED_ANNABELLE).withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER)
                .withActors(VALID_ACTOR_ANNABELLE, VALID_ACTOR_BOB_THE_BUILDER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased() + DATE_DESC_BOB_THE_BUILDER + WATCHED_DESC_ANNABELLE;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER)
                .withIsWatched(VALID_WATCHED_ANNABELLE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_SHOW;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ANNABELLE;
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_ANNABELLE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withType(VALID_TYPE_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        //assertParseSuccess(parser, userInput, expectedCommand);

        // date of release
        userInput = targetIndex.getOneBased() + DATE_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withDateOfRelease(VALID_DATE_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withDescription(VALID_DESCRIPTION_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // isWatched
        userInput = targetIndex.getOneBased() + WATCHED_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withIsWatched(VALID_WATCHED_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // running time
        userInput = targetIndex.getOneBased() + RUNNING_TIME_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withRunningTime(VALID_RUNNING_TIME_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // actors
        userInput = targetIndex.getOneBased() + ACTOR_DESC_ANNABELLE;
        descriptor = new EditShowDescriptorBuilder().withActors(VALID_ACTOR_ANNABELLE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased() + RUNNING_TIME_DESC_ANNABELLE + DESCRIPTION_DESC_ANNABELLE
                + DATE_DESC_ANNABELLE + ACTOR_DESC_ANNABELLE + RUNNING_TIME_DESC_ANNABELLE
                + WATCHED_DESC_BOB_THE_BUILDER + TYPE_DESC_ANNABELLE
                + DESCRIPTION_DESC_ANNABELLE + DATE_DESC_ANNABELLE + ACTOR_DESC_BOB_THE_BUILDER
                + RUNNING_TIME_DESC_BOB_THE_BUILDER + DESCRIPTION_DESC_BOB_THE_BUILDER + DATE_DESC_BOB_THE_BUILDER;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withDateOfRelease(VALID_DATE_BOB_THE_BUILDER)
                .withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER).withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER)
                .withActors(VALID_ACTOR_ANNABELLE, VALID_ACTOR_BOB_THE_BUILDER)
                .withIsWatched(VALID_WATCHED_BOB_THE_BUILDER).withType(VALID_TYPE_ANNABELLE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SHOW;
        String userInput = targetIndex.getOneBased() + INVALID_RUNNING_TIME_DESC + RUNNING_TIME_DESC_BOB_THE_BUILDER;
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_BOB_THE_BUILDER + INVALID_RUNNING_TIME_DESC + WATCHED_DESC_BOB_THE_BUILDER
                + RUNNING_TIME_DESC_BOB_THE_BUILDER;
        descriptor = new EditShowDescriptorBuilder().withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER)
                .withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER)
                .withIsWatched(VALID_WATCHED_BOB_THE_BUILDER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        //assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetActors_success() {
        Index targetIndex = INDEX_THIRD_SHOW;
        String userInput = targetIndex.getOneBased() + ACTOR_EMPTY;

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withActors().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
