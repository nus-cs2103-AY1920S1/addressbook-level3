package seedu.billboard.logic.parser;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.logic.commands.DisplayStatsCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.statistics.formats.StatisticsFormat;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DisplayStatsCommandParser implements Parser<DisplayStatsCommand> {
    @Override
    public DisplayStatsCommand parse(String userInput) throws ParseException {
        StatisticsFormat formatChosen;

        try {
            formatChosen = StatisticsFormat.formatFromName(userInput.trim());
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayStatsCommand.MESSAGE_USAGE), e);
        }

        return new DisplayStatsCommand(formatChosen);
    }
}
