package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.AnnotationCommand;
import seedu.mark.logic.commands.DeleteAnnotationAllCommand;
import seedu.mark.logic.commands.DeleteAnnotationClearAllCommand;
import seedu.mark.logic.commands.DeleteAnnotationCommand;
import seedu.mark.logic.commands.DeleteAnnotationHighlightCommand;
import seedu.mark.logic.commands.DeleteAnnotationNoteCommand;
import seedu.mark.model.annotation.ParagraphIdentifier;

class DeleteAnnotationCommandParserTest {
    private DeleteAnnotationCommandParser parser = new DeleteAnnotationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2   h/ false n/falSe",
                new DeleteAnnotationAllCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2   h/ false n/true",
                new DeleteAnnotationHighlightCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2   h/ true n/ falSe",
                new DeleteAnnotationNoteCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ all   h/ ignored; any n/ input doesnt matter",
                new DeleteAnnotationClearAllCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_missingOnlyHighlight_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2 n/true",
                new DeleteAnnotationHighlightCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2 n/false",
                new DeleteAnnotationAllCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));

    }

    @Test
    public void parse_missingOnlyNote_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2 h/true",
                new DeleteAnnotationNoteCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + "1 p/ g2 h/false",
                new DeleteAnnotationAllCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeStrayId(Index.fromOneBased(2))));

    }

    @Test
    public void parse_keepBoth_failure() {
        assertParseFailure(parser,
                "1 p/p1 n/true h/true",
                DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DO);
    }

    @Test
    public void parse_invalidValue_failure() {
        //index
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 of p/p3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAnnotationCommand.MESSAGE_USAGE));
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
        //highlight
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/p3 h/t",
                ParserUtil.MESSAGE_INVALID_BOOL);
        //empty note
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + "1 p/p3 n/  ",
                ParserUtil.MESSAGE_INVALID_BOOL);

    }


}
