package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Shows the answer to a flashcard during the test.
 */
public class ShowAnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    public ShowAnswerCommand(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String answer = model.getTestAnswer();
        keyboardFlashCardsParser.setAwaitingAnswer(false);
        return new CommandResult(answer);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowAnswerCommand) // instanceof handles nulls
                && keyboardFlashCardsParser.equals(((ShowAnswerCommand) other).keyboardFlashCardsParser);
    }

    /*
    @Override
    public String toString() {
        return "placeholder";
    }
     */

}
