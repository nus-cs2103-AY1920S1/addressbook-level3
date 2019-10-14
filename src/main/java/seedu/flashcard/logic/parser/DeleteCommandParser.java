package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_PARSE_ERROR;

import seedu.flashcard.logic.commands.DeleteCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.CardId;



/**
 * Parses input arguments to generate a {@Code DeleteCommand}
 */
public class DeleteCommandParser implements Parser {

    /**
     * Parses the given context into a delete command
     * @param userInput the input from the user
     * @return new delete command
     */
    @Override
    public DeleteCommand parse(String userInput) throws ParseException {
        try {
            int index = userInput.indexOf("/");
            CardId id = new CardId(Integer.parseInt(userInput.substring(index + 2).trim()));
            return new DeleteCommand(id);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_PARSE_ERROR);
        }
    }
}
