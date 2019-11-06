package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PICTURE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetPictureCommand;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Picture;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class SetPictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPictureCommand.MESSAGE_USAGE);

    private SetPictureCommandParser parser = new SetPictureCommandParser();

    @Test
    public void parse_validPictureNameButFileDoesNotExist_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" 1 pic/fail.jpg"));
        //the picture is a valid file name but it does not exist or is not in the same directory as TutorAid
        assertParseFailure(parser, "1 pic/doesNotExist.jpg", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "Make sure the picture exists "
                        + "and is in the same directory as TutorAid!"));
    }


    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PICTURE_BOB, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", SetPictureCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PICTURE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PICTURE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PICTURE_DESC, Picture.MESSAGE_CONSTRAINTS); // invalid picture
    }

    @Test
    public void parse_pictureExistsInvalidFormat_failure() {
        SetPictureCommandParserStub parserStub = new SetPictureCommandParserStub();
        assertParseFailure(parserStub, "1" + INVALID_PICTURE_DESC, Picture.MESSAGE_CONSTRAINTS); // invalid picture
    }

    @Test
    public void parse_validPictureSpecifiedAndPictureExists_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + PICTURE_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPicture(VALID_PICTURE_AMY).build();
        SetPictureCommand expectedCommand = new SetPictureCommand(targetIndex, descriptor);
        SetPictureCommandParserStub parserStub = new SetPictureCommandParserStub();
        assertParseSuccess(parserStub, userInput, expectedCommand);
    }

    /**
     * A stub for SetPictureCommandParser to test for the case where the picture file exists.
     */
    private static class SetPictureCommandParserStub implements Parser<SetPictureCommand> {

        /**
         * Parses the given {@code String} of arguments in the context of the SetPictureCommand
         * and returns an SetPictureCommand object for execution.
         * In this stub, the file always exists.
         * @throws ParseException if the user input does not conform the expected format.
         */
        public SetPictureCommand parse(String args) throws ParseException {
            requireNonNull(args);
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_PICTURE);

            Index index;

            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SetPictureCommand.MESSAGE_USAGE), pe);
            }

            EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

            if (argMultimap.getValue(PREFIX_PICTURE).isPresent()) {
                Picture pictureToSet = ParserUtil.parsePicture(argMultimap.getValue(PREFIX_PICTURE).get());
                boolean isImageExisting = true;

                if (!isImageExisting) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            "Make sure the picture exists "
                            + "and is in the same directory as TutorAid!"));
                }

                editPersonDescriptor.setPicture(pictureToSet);
            } else {
                throw new ParseException(SetPictureCommand.MESSAGE_NOT_EDITED);
            }

            return new SetPictureCommand(index, editPersonDescriptor);
        }
    }


}
