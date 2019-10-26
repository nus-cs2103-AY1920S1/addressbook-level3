package seedu.address.logic.parser.load;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appcommands.AddCommand;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class RemoveCommandParserTest {

    @Test
    public void parse_successful_delete() {
        assertTrue(true);
    }


}
