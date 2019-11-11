package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Instantiates an EndTestcommand that allows users to end the current flashcard test.
 */
public class EndTestCommand extends Command {

    public static final String COMMAND_WORD = "end";

    private static Logger logger = Logger.getLogger("Foo");
    private KeyboardFlashCardsParser keyboardFlashCardsParser;

    public EndTestCommand(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);

        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updatePerformance(model);
        logger.log(Level.INFO, "Updating performance");

        keyboardFlashCardsParser.endTestMode();
        logger.log(Level.INFO, "Enabling KeyboardFlashCardsParser to accept normal commands");

        CommandResult result = new CommandResult("Test ended");
        result.setTestMode(false, true);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTestCommand // instanceof handles nulls
                && keyboardFlashCardsParser.equals(((EndTestCommand) other).keyboardFlashCardsParser));
    }
}
