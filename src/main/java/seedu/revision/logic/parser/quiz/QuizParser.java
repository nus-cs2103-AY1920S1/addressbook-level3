package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.revision.logic.commands.QuizCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;

public class QuizParser {
    private final Answerable currentAnswerable;

    public QuizParser(Answerable currentAnswerable) {
        this.currentAnswerable = currentAnswerable;
    }

    public QuizCommand parseCommand(String userInput) throws ParseException {

        if (currentAnswerable instanceof Mcq) {
            return new McqInputCommandParser().parse(userInput);

        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }



}

