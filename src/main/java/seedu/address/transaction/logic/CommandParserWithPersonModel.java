package seedu.address.transaction.logic;

import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;

public interface CommandParserWithPersonModel {

    Command parse(String userInput, Model personModel) throws Exception;
}
