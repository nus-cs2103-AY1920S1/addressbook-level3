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
import java.util.Optional;
import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.event.EditEventCommand;
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
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns a EditEventCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public EditEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX, PREFIX_NAME,
                PREFIX_START_DATE, PREFIX_END_DATE);

        ModCode modCode = null;
        TutName tutName = null;
        Index tutIndex = null;
        Index eventIndex = null;
        String assignName = null;
        Date startDate = null;
        Date endDate = null;
        if (validateModCodeTutNameFormat(argMultimap)) {
            modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
            tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
            eventIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(0));
        } else if (validateTutIndexFormat(argMultimap)) {
            tutIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(0));
            eventIndex = ParserUtil.parseIndex(argMultimap.getAllValues(PREFIX_INDEX).get(1));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }

        assignName = argMultimap.getValue(PREFIX_NAME).orElse(null);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);
        try {
            Optional<String> startDateArg = argMultimap.getValue(PREFIX_START_DATE);
            Optional<String> endDateArg = argMultimap.getValue(PREFIX_END_DATE);
            if (startDateArg.isPresent()) {
                startDate = dateFormatter.parse(argMultimap.getValue(PREFIX_START_DATE).get());
            }
            if (endDateArg.isPresent()) {
                endDate = dateFormatter.parse(argMultimap.getValue(PREFIX_END_DATE).get());
            }
        } catch (java.text.ParseException e) {
            throw new ParseException(Event.MESSAGE_CONSTRAINTS_START_END_TIME);
        }

        return new EditEventCommand(modCode, tutName, tutIndex, eventIndex,
                assignName, startDate, endDate);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the first format with modcode
     * and tutorial name and event index, else false.
     */
    public static boolean validateModCodeTutNameFormat(ArgumentMultimap argMultimap) {
        // modcode, tutorial name present without tutorial index - first format
        return (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME)
                && argMultimap.getAllValues(PREFIX_INDEX).size() == 1);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the second format with tutorial
     * and event index, else false.
     */
    public static boolean validateTutIndexFormat(ArgumentMultimap argMultimap) {
        return (argMultimap.getAllValues(PREFIX_INDEX).size() == 2
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

