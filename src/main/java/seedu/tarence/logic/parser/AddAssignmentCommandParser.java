package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MAX_SCORE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.assignment.AddAssignmentCommand;
import seedu.tarence.logic.commands.assignment.AssignmentCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;


/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddAssignmentCommandParser implements Parser<AssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAssignmentCommand
     * and returns an AddAssignmentCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX, PREFIX_NAME,
                PREFIX_MAX_SCORE, PREFIX_START_DATE, PREFIX_END_DATE);

        ModCode modCode = null;
        TutName tutName = null;
        Index tutIndex = null;
        if (validateModCodeTutNameFormat(argMultimap)) {
            modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
            tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
        } else if (validateIndexFormat(argMultimap)) {
            tutIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAssignmentCommand.MESSAGE_USAGE));
        }

        String assignName = argMultimap.getValue(PREFIX_NAME).get();
        Integer maxScore;
        try {
            maxScore = Integer.parseInt(argMultimap.getValue(PREFIX_MAX_SCORE).get());
        } catch (NumberFormatException e) {
            throw new ParseException(Assignment.MESSAGE_CONSTRAINTS_MAX_SCORE);
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Assignment.DATE_FORMAT);
        Date startDate;
        Date endDate;
        try {
            startDate = dateFormatter.parse(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = dateFormatter.parse(argMultimap.getValue(PREFIX_END_DATE).get());
        } catch (java.text.ParseException e) {
            throw new ParseException(Assignment.MESSAGE_CONSTRAINTS_START_END_DATE);
        }

        return new AddAssignmentCommand(modCode, tutName, tutIndex, assignName, maxScore, startDate, endDate);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the first format with modcode and tutorial name,
     * else false.
     */
    public static boolean validateModCodeTutNameFormat(ArgumentMultimap argMultimap) {
        // modcode, tutorial name present without tutorial index - first format
        return (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_NAME,
                PREFIX_MAX_SCORE, PREFIX_START_DATE, PREFIX_END_DATE)
                && arePrefixesAbsent(argMultimap, PREFIX_INDEX));
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the second format with tutorial index,
     * else false.
     */
    public static boolean validateIndexFormat(ArgumentMultimap argMultimap) {
        // tutorial index present without modcode or tutorial name - second format
        return (arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME,
                PREFIX_MAX_SCORE, PREFIX_START_DATE, PREFIX_END_DATE)
                && arePrefixesAbsent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}

