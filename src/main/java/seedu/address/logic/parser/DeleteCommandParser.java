package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECORD;
import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_EVENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeleteRecordCommand;
import seedu.address.logic.commands.DeleteTrainingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Event;

/**
 * Parses input arguments and creates a new appropriate DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        DeleteCommand deleteCommand;
        String flag = getFlag(trimmedArgs);
        if (flag.equals(FLAG_PERSON.getFlag())) {
            deleteCommand = parsePerson(trimmedArgs);
        } else if (flag.equals(FLAG_EVENT.getFlag())) {
            deleteCommand = parseEvent(trimmedArgs);
        } else if (flag.equals(FLAG_TRAINING.getFlag())) {
            deleteCommand = parseTraining(trimmedArgs);
        } else if (flag.equals(FLAG_RECORD.getFlag())) {
            deleteCommand = parseRecord(trimmedArgs);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return deleteCommand;
    }

    /**
     * Creates a DeletePersonCommand object if the flag given is for a person.
     */
    public DeletePersonCommand parsePerson(String args) throws ParseException {
        args = removeFlag(args);
        if (args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a DeleteRecordCommand object if the flag given is for a record.
     */
    public DeleteRecordCommand parseRecord(String args) throws ParseException {
        args = args.substring(2).trim();
        if (args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecordCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT, PREFIX_DATE);
        Index index = parseIndex(argMultimap);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecordCommand.MESSAGE_USAGE));
        }

        String eventName = ParserUtil.parseEvent(argMultimap.getValue(PREFIX_EVENT).get());
        AthletickDate athletickDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new DeleteRecordCommand(index, eventName, athletickDate);
    }

    /**
     * Parses the index of the athlete for DeleteRecordCommand.
     */
    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecordCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a DeleteEventCommand object if the flag given is for an event.
     */
    public DeleteEventCommand parseEvent(String args) throws ParseException {
        args = removeFlag(args);
        if (args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
        try {
            String eventName = ParserUtil.parseEvent(args);
            Event event = new Event(eventName);
            return new DeleteEventCommand(event);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a DeleteTrainingCommand object if the flag given is for a training.
     */
    public DeleteTrainingCommand parseTraining(String args) throws ParseException {
        args = removeFlag(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!isPrefixPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTrainingCommand.MESSAGE_USAGE));
        }
        AthletickDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        return new DeleteTrainingCommand(date);
    }

    /**
     * Extracts the valid flag from the argument string.
     */
    public String getFlag(String args) throws ParseException {
        requireNonNull(args);
        String[] stringArray = args.split("\\s+", 2);
        String flag = stringArray[0].trim();
        if (flag.length() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (!Flag.isValidFlag(flag)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return flag;
    }

    /**
     * Removes the flag (-p, -e, or -t) from the argument string.
     */
    private String removeFlag(String args) throws ParseException {
        try {
            return args.substring(2);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(DeleteCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Returns true if prefixes doesn't contains empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
