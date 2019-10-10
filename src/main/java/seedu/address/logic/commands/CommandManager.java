package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.listeners.UiListener;

/**
 * Represents a class that invokes commands based on command input it receives from a Ui.
 */
public class CommandManager implements UiListener {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final CommandParser commandParser;

    CommandManager(CommandManagerBuilder builder) {
        this.commandParser = new CommandParser(builder.getKeywordParser());
    }

    public static CommandManagerBuilder newBuilder() {
        return new CommandManagerBuilder();
    }

    @Override
    public void onCommandInput(String input) {
        try {
            Command command = this.commandParser.parse(input);
            CommandResult commandResult = command.execute();
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + input);
        }
    }
}
