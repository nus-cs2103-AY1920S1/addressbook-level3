package seedu.address.logic.commands;

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
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    @Override
    public CommandResult execute(Model model) {
        // TODO: integrate statistics
        keyboardFlashCardsParser.endTest();
        return new CommandResult("Test ended");
    }
}
