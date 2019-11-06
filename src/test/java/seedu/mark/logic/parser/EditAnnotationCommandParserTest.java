package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.AnnotationCommand;
import seedu.mark.logic.commands.EditAnnotationCommand;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;

class EditAnnotationCommandParserTest {
    private EditAnnotationCommandParser parser = new EditAnnotationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
            "1 p/p1 to/p2 h/orange n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                new EditAnnotationCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                        ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                        AnnotationNote.SAMPLE_NOTE, Highlight.ORANGE));
    }

    @Test
    public void parse_noTo_success() {
        assertParseSuccess(parser,
                "1 p/p1 h/orange n/" + AnnotationNote.SAMPLE_NOTE.toString(),
                new EditAnnotationCommand(Index.fromOneBased(1),
                        ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                        null,
                        AnnotationNote.SAMPLE_NOTE, Highlight.ORANGE));
    }

    @Test
    public void parse_invalidValues_failure() {

        //index
        assertParseFailure(parser,
                "1 of p/p3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAnnotationCommand.MESSAGE_USAGE));
        //pid
        assertParseFailure(parser,
                "1 p/3",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1 p/p p3",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1 p/* p33 #",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1 p/p3 to/4",
                AnnotationCommand.MESSAGE_CONSTRAINTS);
        //highlight
        assertParseFailure(parser,
                "1 p/p3 h/yel low",
                Highlight.MESSAGE_INVALID_COLOUR);
        //empty note
        assertParseFailure(parser,
                "1 p/p3 n/       \r \n    ",
                AnnotationNote.MESSAGE_BLANK_NOTE);
    }

}
