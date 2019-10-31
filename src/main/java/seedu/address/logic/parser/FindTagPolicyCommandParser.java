package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindTagPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindTagPolicyCommand object
 */
public class FindTagPolicyCommandParser implements Parser<FindTagPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagPolicyCommand
     * and returns a FindTagPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagPolicyCommand.MESSAGE_USAGE));
        }

        List<String> tagNames = argMultimap.getAllValues(PREFIX_TAG);

        tagNames.replaceAll(String::toLowerCase);

        return new FindTagPolicyCommand(tagNames);
    }

}
