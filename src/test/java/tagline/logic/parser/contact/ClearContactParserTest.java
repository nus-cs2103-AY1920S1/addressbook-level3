//@@author tanlk99
package tagline.logic.parser.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tagline.logic.parser.CommandParserTestUtil.assertPromptRequest;
import static tagline.logic.parser.CommandParserTestUtil.getFilledPrompt;
import static tagline.logic.parser.contact.ClearContactParser.CONTACT_CLEAR_COMMAND_ABORTED_STRING;
import static tagline.logic.parser.contact.ClearContactParser.CONTACT_CLEAR_COMMAND_CONFIRM_STRING;
import static tagline.logic.parser.contact.ClearContactParser.CONTACT_CLEAR_CONFIRM_STRING;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;

public class ClearContactParserTest {
    private ClearContactParser parser = new ClearContactParser();

    @Test
    public void parse_noPrompts_throwsPromptRequestException() {
        assertPromptRequest(parser, ClearContactCommand.COMMAND_WORD, Collections.singletonList(new Prompt(
                "", CONTACT_CLEAR_COMMAND_CONFIRM_STRING)));
        assertPromptRequest(parser, ClearContactCommand.COMMAND_WORD + "3", Collections.singletonList(new Prompt(
                "", CONTACT_CLEAR_COMMAND_CONFIRM_STRING)));
    }

    @Test
    public void parse_validPromptResponse_returnsClearCommand() throws ParseException {
        Prompt validFilledPrompt = getFilledPrompt("",
                CONTACT_CLEAR_COMMAND_CONFIRM_STRING, CONTACT_CLEAR_CONFIRM_STRING);

        assertNotNull(parser.parse(ClearContactCommand.COMMAND_WORD,
                Collections.singletonList(validFilledPrompt)));
        assertNotNull(parser.parse(ClearContactCommand.COMMAND_WORD + "3",
                Collections.singletonList(validFilledPrompt)));
    }

    @Test
    public void parse_invalidPromptResponse_throwsParseException() {
        Prompt invalidFilledPrompt = getFilledPrompt("",
                CONTACT_CLEAR_COMMAND_CONFIRM_STRING, "BAD STRING");

        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(ClearContactCommand.COMMAND_WORD,
                Collections.singletonList(invalidFilledPrompt)));
        assertEquals(CONTACT_CLEAR_COMMAND_ABORTED_STRING, thrown.getMessage());
    }
}
