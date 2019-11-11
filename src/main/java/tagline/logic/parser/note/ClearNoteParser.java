//@@author tanlk99
package tagline.logic.parser.note;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tagline.logic.commands.note.ClearNoteCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.ParserUtil;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;

/**
 * Parses input arguments and creates a new ClearNoteCommand object.
 */
public class ClearNoteParser implements Parser<ClearNoteCommand> {
    public static final String NOTE_CLEAR_COMMAND_CONFIRM_STRING = "Are you sure you want to clear your note list? "
            + "Enter 'Y' to continue.";
    public static final String NOTE_CLEAR_COMMAND_ABORTED_STRING = "The current command has been aborted.";
    public static final String NOTE_CLEAR_CONFIRM_STRING = "Y";

    /**
     * Validates and parses the given {@code String} of arguments and a list of prompts in the context of a
     * ClearNoteCommand.
     *
     * @throws PromptRequestException if the prompt list is empty
     * @throws ParseException if the prompt response does not match the confirmation string
     */
    public ClearNoteCommand parse(String args, List<Prompt> promptList) throws ParseException {
        if (promptList.isEmpty()) {
            throw new PromptRequestException(Arrays.asList(
                    new Prompt("", NOTE_CLEAR_COMMAND_CONFIRM_STRING)));
        }

        if (ParserUtil.getPromptResponseFromPrefix("", promptList)
                .equals(NOTE_CLEAR_CONFIRM_STRING)) {
            return new ClearNoteCommand();
        } else {
            throw new ParseException(NOTE_CLEAR_COMMAND_ABORTED_STRING);
        }
    }

    /**
     * @see ClearNoteParser#parse(String, List).
     *
     * Calling this method will always throw a {@code PromptRequestException} containing a single prompt requesting
     * confirmation.
     */
    public ClearNoteCommand parse(String args) throws ParseException {
        return parse(args, Collections.emptyList());
    }
}
