package seedu.address.logic.parser.quiz;

import seedu.address.logic.commands.quiz.QuitQuizModeCommand;
import seedu.address.logic.parser.Parser;

public class QuitQuizModeParser implements Parser<QuitQuizModeCommand> {
    public QuitQuizModeCommand parse(String userInput) {
        return new QuitQuizModeCommand();
    }
}
