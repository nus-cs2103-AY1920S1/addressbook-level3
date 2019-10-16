package budgetbuddy.logic.parser.commandparsers;

import budgetbuddy.logic.commands.EvalCommand;
import budgetbuddy.logic.parser.CommandParser;

/**
 * Parses input arguments and creates a new EvalCommand object
 */
public class EvalCommandParser implements CommandParser<EvalCommand> {
    @Override
    public String name() {
        return "eval";
    }

    @Override
    public EvalCommand parse(String userInput) {
        return new EvalCommand(userInput);
    }
}
