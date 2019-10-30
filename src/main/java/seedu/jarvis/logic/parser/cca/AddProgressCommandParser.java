package seedu.jarvis.logic.parser.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL_NAMES;

import java.util.stream.Stream;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.cca.AddProgressCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;

/**--
 * Parses input arguments and creates a new {@code AddProgressCommand} object.
 */
public class AddProgressCommandParser implements Parser<AddProgressCommand> {

    @Override
    public AddProgressCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROGRESS_LEVEL_NAMES);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProgressCommand.MESSAGE_USAGE), pe);
        }


        if (!arePrefixesPresent(argumentMultimap, PREFIX_PROGRESS_LEVEL_NAMES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProgressCommand.MESSAGE_USAGE));
        }

        CcaMilestoneList ccaMilestoneList = CcaParserUtil.parseCcaMilestones(argumentMultimap
                .getAllValues(PREFIX_PROGRESS_LEVEL_NAMES));

        return new AddProgressCommand(index, ccaMilestoneList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
