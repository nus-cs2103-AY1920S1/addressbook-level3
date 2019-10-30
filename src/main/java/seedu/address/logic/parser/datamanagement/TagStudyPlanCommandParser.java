package seedu.address.logic.parser.datamanagement;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.datamanagement.TagStudyPlanCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.PriorityTagType;

/**
 * Parses input arguments and creates a new TagStudyPlanCommand object
 */
public class TagStudyPlanCommandParser implements Parser<TagStudyPlanCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the TagStudyPlanCommand
     * and returns an TagStudyPlanCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagStudyPlanCommand parse(String args) throws ParseException {
        String[] tokens = args.trim().split(" ");
        if (tokens.length < 2 || !PriorityTagType.isValidPriorityTagString(tokens[0])
                || !tokens[1].matches("\\d")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagStudyPlanCommand.MESSAGE_USAGE));
        }
        return new TagStudyPlanCommand(tokens[0].toUpperCase(), Integer.parseInt(tokens[1]));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof TagStudyPlanCommandParser;
    }
}
