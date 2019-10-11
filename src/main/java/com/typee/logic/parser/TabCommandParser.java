package com.typee.logic.parser;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.TabCommand;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.ui.Tab;

/**
 * Parses the given {@code String} of arguments in the context of the TabCommand
 * and returns an Tab Command object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class TabCommandParser implements Parser<TabCommand> {
    @Override
    public TabCommand parse(String userInput) throws ParseException {
        String tabName = userInput.trim();

        if (tabName.equals("") || userInput == null) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        } else {
            Tab tab = new Tab(tabName);
            return new TabCommand(tab);
        }
    }
}
