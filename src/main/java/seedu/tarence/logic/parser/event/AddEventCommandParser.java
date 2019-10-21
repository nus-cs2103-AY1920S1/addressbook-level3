package seedu.tarence.logic.parser.event;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.event.AddEventCommand;
import seedu.tarence.logic.parser.ArgumentMultimap;
import seedu.tarence.logic.parser.ArgumentTokenizer;
import seedu.tarence.logic.parser.Parser;
import seedu.tarence.logic.parser.ParserUtil;
import seedu.tarence.logic.parser.Prefix;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX,
                PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);

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
                    AddEventCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_NAME).get();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);
        Date startTime;
        Date endTime;
        try {
            startTime = dateFormatter.parse(argMultimap.getValue(PREFIX_START_DATE).get());
            endTime = dateFormatter.parse(argMultimap.getValue(PREFIX_END_DATE).get());
        } catch (java.text.ParseException e) {
            throw new ParseException(Event.MESSAGE_CONSTRAINTS_START_END_TIME);
        }

        return new AddEventCommand(modCode, tutName, tutIndex, eventName, startTime, endTime);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the first format with modcode and tutorial name,
     * else false.
     */
    public static boolean validateModCodeTutNameFormat(ArgumentMultimap argMultimap) {
        // modcode, tutorial name present without tutorial index - first format
        return (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME,
                PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE)
                && arePrefixesAbsent(argMultimap, PREFIX_INDEX));
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the second format with tutorial index,
     * else false.
     */
    public static boolean validateIndexFormat(ArgumentMultimap argMultimap) {
        // tutorial index present without modcode or tutorial name - second format
        return (arePrefixesPresent(argMultimap, PREFIX_INDEX,
                PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE)
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

