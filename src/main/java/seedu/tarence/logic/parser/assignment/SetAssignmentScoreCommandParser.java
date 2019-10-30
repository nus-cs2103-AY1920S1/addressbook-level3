package seedu.tarence.logic.parser.assignment;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.assignment.SetAssignmentScoreCommand;
import seedu.tarence.logic.parser.ArgumentMultimap;
import seedu.tarence.logic.parser.ArgumentTokenizer;
import seedu.tarence.logic.parser.Parser;
import seedu.tarence.logic.parser.ParserUtil;
import seedu.tarence.logic.parser.Prefix;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;

/**
 * Parses input arguments and creates a new SetAssignmentScoreCommand object
 */
public class SetAssignmentScoreCommandParser implements Parser<SetAssignmentScoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetAssignmentScoreCommand
     * and returns a SetAssignmentScoreCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public SetAssignmentScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX, PREFIX_SCORE);

        ModCode modCode = null;
        TutName tutName = null;
        Index tutIndex = null;
        Index assignIndex = null;
        Index studentIndex = null;
        Integer score = null;
        if (validateModCodeTutNameFormat(argMultimap)) {
            modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
            tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
            assignIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(0));
            studentIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(1));
        } else if (validateTutIndexFormat(argMultimap)) {
            tutIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(0));
            assignIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(1));
            studentIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(2));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetAssignmentScoreCommand.MESSAGE_USAGE));
        }

        try {
            score = Integer.parseInt(argMultimap.getValue(PREFIX_SCORE).get());
            if (score < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new ParseException(Assignment.MESSAGE_CONSTRAINTS_SCORE);
        }

        return new SetAssignmentScoreCommand(modCode, tutName, tutIndex, assignIndex,
                studentIndex, score);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the first format with modcode and tutorial name,
     * else false.
     */
    public static boolean validateModCodeTutNameFormat(ArgumentMultimap argMultimap) {
        // modcode, tutorial name present without tutorial index - first format
        return (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_SCORE)
                && argMultimap.getAllValues(PREFIX_INDEX).size() == 2);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the second format with tutorial index,
     * else false.
     */
    public static boolean validateTutIndexFormat(ArgumentMultimap argMultimap) {
        return (arePrefixesPresent(argMultimap, PREFIX_INDEX)
                && argMultimap.getAllValues(PREFIX_INDEX).size() == 3);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the format with assignment name,
     * max score, start and end date, else false.
     */
    public static boolean validateAssignmentFormat(ArgumentMultimap argMultimap) {
        return (arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SCORE, PREFIX_START_DATE, PREFIX_END_DATE)
                && argMultimap.getAllValues(PREFIX_INDEX).size() <= 1);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the format with assignment index,
     * else false.
     */
    public static boolean validateAssignIndexFormat(ArgumentMultimap argMultimap) {
        return (argMultimap.getAllValues(PREFIX_INDEX).size() == 2
                && arePrefixesAbsent(argMultimap, PREFIX_NAME, PREFIX_SCORE, PREFIX_START_DATE, PREFIX_END_DATE));
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

