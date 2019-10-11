package seedu.address.logic.commands;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Ends the current flashcard test.
 */
public class EndTestCommand extends Command {

    public static final String COMMAND_WORD = "end";

    private AddressBookParser addressBookParser;

    public EndTestCommand(AddressBookParser addressBookParser) {
        this.addressBookParser = addressBookParser;
    }

    @Override
    public CommandResult execute(Model model) {
        // TODO: integrate statistics
        addressBookParser.endTest();
        return new CommandResult("Test ended");
    }
}
