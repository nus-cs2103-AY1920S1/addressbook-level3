package seedu.address.inventory.logic;

import seedu.address.inventory.commands.PersonCommand;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.logic.parser.AddCommandParser;
import seedu.address.person.logic.parser.exceptions.ParseException;

public class PersonCommandParser {

    public PersonCommand parse(String args) throws ParseException  {
        AddCommandParser addCommandParser = new AddCommandParser();
        AddCommand personAddCommand = addCommandParser.parse(args);
        return new PersonCommand(personAddCommand);
    }
}
