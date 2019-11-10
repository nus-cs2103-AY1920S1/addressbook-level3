package seedu.address.financialtracker.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.logic.commands.SwitchCommand;
import seedu.address.financialtracker.ui.CountriesDropdown;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchCommand object
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchCommand
     * and returns a SwitchCommand object for execution.
     */
    public SwitchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        String country = (args.substring(0, 1).toUpperCase() + args.substring(1));
        if (CountriesDropdown.isValidDropdownCountry(country)) {
            return new SwitchCommand(country);
        } else {
            throw new ParseException(country + " is invalid!\n" + SwitchCommand.MESSAGE_USAGE);
        }
    }
}
