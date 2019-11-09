package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_START_END_DATES;
import static seedu.system.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.system.logic.commands.outofsession.AddCompetitionCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.competition.Competition;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

/**
 * Parses input arguments and creates a new AddCompetitionCommand object.
 */
public class AddCompetitionCommandParser implements Parser<AddCompetitionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCompetitionCommand
     * and returns an AddCompetitionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCompetitionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCompetitionCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        CustomDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        CustomDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        if (!ParserUtil.isBefore(startDate, endDate)) {
            throw new ParseException(MESSAGE_INVALID_START_END_DATES);
        }

        Competition competition = new Competition(name, startDate, endDate);

        return new AddCompetitionCommand(competition);
    }
}
