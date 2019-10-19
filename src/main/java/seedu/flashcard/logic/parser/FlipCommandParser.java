package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashcard.logic.commands.FlipCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Answer;

public class FlipCommandParser implements Parser<FlipCommand> {

    @Override
    public FlipCommand parse(String args) throws ParseException {
        try {
            Answer answer = ParserUtil.parseAnswer(args);
            return new FlipCommand(answer);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
    }
}
