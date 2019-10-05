package seedu.address.logic.parser;

import seedu.address.logic.commands.blueprints.CommandBuilder;
import seedu.address.logic.commands.blueprints.CommandBlueprints;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandBlueprintParser implements Parser<CommandBuilder> {

    @Override
    public CommandBuilder parse(String userInput) throws ParseException {
        return CommandBlueprints.get(userInput);
    }
}
