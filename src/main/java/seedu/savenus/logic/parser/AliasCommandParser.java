package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.savenus.logic.commands.AliasCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    @Override
    public AliasCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(AliasCommand.PROPER_USAGE, AliasCommand.NO_ARGUMENTS_USAGE)));
        }

        String[] fieldKeyWords = trimmedArgs.split("\\s+");

        if (hasOnlyCommandWord(fieldKeyWords)) {
            return new AliasCommand(fieldKeyWords[0], "");
        } else if (hasCommandAndKAliasWord(fieldKeyWords)) {
            return new AliasCommand(fieldKeyWords[0], fieldKeyWords[1]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(AliasCommand.PROPER_USAGE, AliasCommand.TOO_MANY_ARGUMENTS_USAGE)));
        }

    }

    /**
     * Checks if the array of keywords has only a command word or not.
     * @param keywords the array of keywords.
     * @return true if the array has only one command word. False if otherwise.
     */
    public boolean hasOnlyCommandWord(String[] keywords) {
        return keywords.length == 1;
    }

    /**
     * Checks if the array of keywords has only a command word and alias word.
     * @param keywords the array of keywords.
     * @return true if the array has only one command word and one alias word. False if otherwise.
     */
    public boolean hasCommandAndKAliasWord(String[] keywords) {
        return keywords.length == 2;
    }
}
