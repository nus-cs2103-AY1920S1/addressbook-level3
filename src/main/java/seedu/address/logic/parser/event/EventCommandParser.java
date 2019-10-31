package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GET_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW_MODE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.event.EventDeleteCommand;
import seedu.address.logic.commands.event.EventEditCommand;
import seedu.address.logic.commands.event.EventExportCommand;
import seedu.address.logic.commands.event.EventIndexCommand;
import seedu.address.logic.commands.event.EventViewCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new {@code EventCommand} object
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EventAddCommand
     * and returns an EventAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_EVENT,
                    PREFIX_EVENT_NAME,
                    PREFIX_START_DATETIME,
                    PREFIX_END_DATETIME,
                    PREFIX_RECUR,
                    PREFIX_COLOR,
                    PREFIX_GET_INDEX,
                    PREFIX_VIEW,
                    PREFIX_DELETE,
                    PREFIX_EXPORT,
                    PREFIX_DIRECTORY,
                    PREFIX_VIEW_MODE,
                    PREFIX_VIEW_DATE);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE),
                pe);
        }
        if (argMultimap.getValue(PREFIX_VIEW).isPresent()) { // View command
            return viewCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_EXPORT).isPresent()) { //Export Command
            return exportCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_GET_INDEX).isPresent()) { //get Index Of Command
            return indexOfCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            return deleteCommand(index, argMultimap);
        } else if (isEdit) { // Delete command
            return editCommand(index, argMultimap);
        } else {
            return addCommand(argMultimap);
        }
    }

    /**
     * Performs validation and return the EventView Object
     *
     * @param argMultimap for tokenized input.
     * @return EventExportCommand object.
     * @throws ParseException
     */
    private EventViewCommand viewCommand(ArgumentMultimap argMultimap) {
        HashMap<String, String> fields = new HashMap<>();

        String viewMode = argMultimap.getValue(PREFIX_VIEW_MODE).orElse("");
        String viewDate = argMultimap.getValue(PREFIX_VIEW_DATE).orElse("");

        fields.put("viewMode", viewMode);
        fields.put("viewDate", viewDate);

        return new EventViewCommand(fields);
    }

    /**
     * Performs validation and return the EventExport Object
     *
     * @param argMultimap for tokenized input.
     * @return EventExportCommand object.
     * @throws ParseException
     */
    private EventExportCommand exportCommand(ArgumentMultimap argMultimap) {
        String targetDirectory = argMultimap.getValue(PREFIX_DIRECTORY).orElse("");

        return new EventExportCommand(targetDirectory);
    }

    /**
     * Performs validation and return the EventEdit object.
     *
     * @param index       of vEvent in the list.
     * @param argMultimap for tokenized input.
     * @return EventEditCommand object.
     * @throws ParseException
     */
    private EventEditCommand editCommand(Index index, ArgumentMultimap argMultimap) {
        HashMap<String, String> fields = new HashMap<>();

        String eventName = argMultimap.getValue(PREFIX_EVENT_NAME).orElse("");
        String startDateTime = argMultimap.getValue(PREFIX_START_DATETIME).orElse("");
        String endDateTime = argMultimap.getValue(PREFIX_END_DATETIME).orElse("");
        String recurType = argMultimap.getValue(PREFIX_RECUR).orElse("");
        String colorString = argMultimap.getValue(PREFIX_COLOR).orElse("");

        fields.put("eventName", eventName);
        fields.put("startDateTime", startDateTime);
        fields.put("endDateTime", endDateTime);
        fields.put("recurType", recurType);
        fields.put("colorString", colorString);

        return new EventEditCommand(index, fields);
    }

    /**
     * Performs validation and return the EventDeleteCommand object.
     *
     * @param index       of vEvent in the list.
     * @param argMultimap for tokenized input.
     * @return EventDeleteCommand object.
     * @throws ParseException
     */
    private EventDeleteCommand deleteCommand(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        try {
            int indexToDelete = Integer
                    .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

            if (indexToDelete <= 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                EventDeleteCommand.MESSAGE_USAGE));
            }
            index.fromOneBased(indexToDelete);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            EventDeleteCommand.MESSAGE_USAGE));
        }

        return new EventDeleteCommand(index);
    }

    /**
     * Performs validation and return the EventAddCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return EventAddCommand object.
     * @throws ParseException
     */
    private EventAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_START_DATETIME,
                PREFIX_END_DATETIME, PREFIX_RECUR, PREFIX_COLOR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENT_NAME).orElse("");
        String startDateTime = argMultimap.getValue(PREFIX_START_DATETIME).orElse("");
        String endDateTime = argMultimap.getValue(PREFIX_END_DATETIME).orElse("");
        String recurType = argMultimap.getValue(PREFIX_RECUR).orElse("");
        String colorNumber = argMultimap.getValue(PREFIX_COLOR).orElse("");

        return new EventAddCommand(eventName, startDateTime, endDateTime, recurType, colorNumber);
    }

    /**
     * Performs validation and return the EventIndexCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return EventAddCommand object.
     * @throws ParseException
     */
    private EventIndexCommand indexOfCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GET_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, EventIndexCommand.MESSAGE_USAGE));
        }

        String desiredEventName = argMultimap.getValue(PREFIX_GET_INDEX).orElse("");

        return new EventIndexCommand(desiredEventName);
    }

}
