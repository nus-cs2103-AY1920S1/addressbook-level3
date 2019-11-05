package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Ends the current flashcard test.
 */
public class EndTestCommand extends Command {

    public static final String COMMAND_WORD = "end";

    private KeyboardFlashCardsParser keyboardFlashCardsParser;

    public EndTestCommand(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    @Override
    public CommandResult execute(Model model) {

        model.updatePerformance(model);
        keyboardFlashCardsParser.endTestMode();
        return new CommandResult("Test ended");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTestCommand // instanceof handles nulls
                && keyboardFlashCardsParser.equals(((EndTestCommand) other).keyboardFlashCardsParser));
    }
}
