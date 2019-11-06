package seedu.mark.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;

class EditAnnotationCommandParserTest {
    private EditAnnotationCommandParser parser = new EditAnnotationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        EditAnnotationCommand expected = new EditAnnotationCommand(Index.fromOneBased(1),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                AnnotationNote.SAMPLE_NOTE, Highlight.ORANGE);

    }

    @Test
    public void parse_noTo_success() {

    }

    @Test
    public void parse_invalidValues_failure() {

    }

}
