package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;

public class QuizParser {

    public Command parseCommand(String userInput, Answerable currentAnswerable) throws ParseException {

        if (currentAnswerable instanceof Mcq) {
            return new McqInputCommandParser().parse(userInput, currentAnswerable);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }





}

