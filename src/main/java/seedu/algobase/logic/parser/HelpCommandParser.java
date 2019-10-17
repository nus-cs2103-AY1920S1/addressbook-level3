package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_NAME;
import static seedu.algobase.commons.util.AppUtil.getClassStringField;

import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.HelpCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

public class HelpCommandParser implements Parser<HelpCommand> {

    @Override
    public HelpCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if(trimmedArgs.isEmpty()) {
            return new HelpCommand(null, true);
        } else {
            for(Class command: Command.commandList) {
                String commandWord = getClassStringField(command, "COMMAND_WORD");
                if(commandWord.equals(trimmedArgs)) {
                    return new HelpCommand(command, false);
                }
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_NAME, trimmedArgs));
        }
    }
}