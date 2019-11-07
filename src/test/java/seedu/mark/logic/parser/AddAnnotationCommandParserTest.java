package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.AddAnnotationCommand.MESSAGE_GENERAL_MUST_HAVE_NOTE;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.AddAnnotationCommand;
import seedu.mark.logic.commands.AnnotationCommand;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;

class AddAnnotationCommandParserTest {
    private AddAnnotationCommandParser parser = new AddAnnotationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddAnnotationCommand expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(2)), AnnotationNote.SAMPLE_NOTE, Highlight.PINK);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2   h/  pink n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                expectedNonGeneralCommand);

        expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)), AnnotationNote.SAMPLE_NOTE, Highlight.PINK);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ p2   h/  pink n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                expectedNonGeneralCommand);

        AddAnnotationCommand expectedGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1), Highlight.GREEN,
                AnnotationNote.SAMPLE_NOTE);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ null h/grEen n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                expectedGeneralCommand);

    }

    @Test
    public void parse_onlyHighlightMissing_success() {
        AddAnnotationCommand expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(2)), AnnotationNote.SAMPLE_NOTE, Highlight.YELLOW);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2 n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                expectedNonGeneralCommand);
        expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)), AnnotationNote.SAMPLE_NOTE, Highlight.YELLOW);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/p2 n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                expectedNonGeneralCommand);
    }

    @Test
    public void parse_onlyNoteMissing_success() {
        AddAnnotationCommand expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(2)), null, Highlight.PINK);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2   h/  pink ",
                expectedNonGeneralCommand);

        expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)), null, Highlight.PINK);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/p2   h/  pink ",
                expectedNonGeneralCommand);

    }

    @Test
    public void parse_allOptionalFieldsMissing_success() {
        AddAnnotationCommand expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(2)), null, Highlight.YELLOW);
        //stray invalidity handled in execute of command
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/g2",
                expectedNonGeneralCommand);

        expectedNonGeneralCommand = new AddAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)), null, Highlight.YELLOW);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/p2",
                expectedNonGeneralCommand);

    }

    @Test
    public void parse_compulsoryIndexMissing_failure() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "p/ g2   h/  pink ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAnnotationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryParagraphMissing_failure() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAnnotationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noNoteForGeneralNote_failure() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/null",
                MESSAGE_GENERAL_MUST_HAVE_NOTE);
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/null h/orange",
                MESSAGE_GENERAL_MUST_HAVE_NOTE);
    }

    @Test
    public void parse_invalidValue_failure() {
        //index
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 of p/p3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAnnotationCommand.MESSAGE_USAGE));
        //pid
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/3",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/p p3",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/* p33 #",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        //highlight colour
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/p3 h/yel low",
                Highlight.MESSAGE_INVALID_COLOUR);
        //empty note
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/p3 n/       \r \n    ",
                AnnotationNote.MESSAGE_BLANK_NOTE);
    }

}
