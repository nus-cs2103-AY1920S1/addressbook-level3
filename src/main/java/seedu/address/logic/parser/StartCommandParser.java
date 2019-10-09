package seedu.address.logic.parser;

import seedu.address.logic.commands.StartCommand;

//@@author keiteo
/**
 * Parses input arguments and creates a new StartCommand object.
 */
public class StartCommandParser implements Parser<StartCommand> {

    private final AddressBookParser addressBookParser;

    StartCommandParser(AddressBookParser addressBookParser) {
        this.addressBookParser = addressBookParser;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the StartCommand
     * and returns a StartCommand object for execution.
     */
    public StartCommand parse(String args) {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new StartCommand(addressBookParser);
        }
        return new StartCommand(addressBookParser, trimmedArgs);
    }
}
