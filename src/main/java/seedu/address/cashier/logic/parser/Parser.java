package seedu.address.cashier.logic.parser;

import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.model.Model;

/**
 * Represents a Parser that is able to parse user input into a {@code Command}.
 */
public interface Parser {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args the user input
     * @param modelManager the model which the parser operates on
     * @param personModel the person model consisting of the cashier
     * @return command that execute according to the user input
     * @throws Exception if the args are invalid
     */
    Command parse(String args, Model modelManager, seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws Exception;
}
