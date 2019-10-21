package seedu.address.logic.parser.event;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.question.*;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new {@code EventCommand} object
 */
public class EventCommandParser implements Parser<EventCommand> {

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
                    PREFIX_EVENTNAME,
                    PREFIX_STARTDATETIME,
                    PREFIX_ENDDATETIME,
                    PREFIX_RECUR,
                    PREFIX_COLOR);

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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionEditCommand.MESSAGE_USAGE),
                pe);
        }

//        if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
//            return deleteCommand(index, argMultimap);
//        } else if (isEdit) { // Edit command
//            return editCommand(index, argMultimap);
//        } else { // Create command
//            return addCommand(argMultimap);
//        }

        // weiboon for now testing add command
        return addCommand(argMultimap);

    }

    /**
     * Performs validation and return the EventAddCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return EventAddCommand object.
     * @throws ParseException
     */
    private EventAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENTNAME, PREFIX_STARTDATETIME,
                PREFIX_ENDDATETIME, PREFIX_RECUR, PREFIX_COLOR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENTNAME).orElse("");
        String startDateTime = argMultimap.getValue(PREFIX_STARTDATETIME).orElse("");
        String endDateTime = argMultimap.getValue(PREFIX_ENDDATETIME).orElse("");
        String recurType = argMultimap.getValue(PREFIX_RECUR).orElse("");
        String colorNumber = argMultimap.getValue(PREFIX_COLOR).orElse("");

        return new EventAddCommand(eventName, startDateTime, endDateTime, recurType, colorNumber);
    }

    /**
     * Performs validation and return the QuestionEditCommand object.
     *
     * @param index       of question in the list.
     * @param argMultimap for tokenized input.
     * @return QuestionEditCommand object.
     * @throws ParseException
     */
    private QuestionEditCommand editCommand(Index index, ArgumentMultimap argMultimap) {
        // Add parameters to be edited. Note: the fields are optional
        // options is compulsory for mcq
        HashMap<String, String> fields = new HashMap<>();
        HashMap<String, String> options = new HashMap<>();

        String question = argMultimap.getValue(PREFIX_QUESTION).orElse("");
        String answer = argMultimap.getValue(PREFIX_ANSWER).orElse("");
        String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

        fields.put("question", question);
        fields.put("answer", answer);
        fields.put("type", typeName);

        options.put("optionA", argMultimap.getValue(PREFIX_OPTIONA).orElse(""));
        options.put("optionB", argMultimap.getValue(PREFIX_OPTIONB).orElse(""));
        options.put("optionC", argMultimap.getValue(PREFIX_OPTIONC).orElse(""));
        options.put("optionD", argMultimap.getValue(PREFIX_OPTIOND).orElse(""));

        return new QuestionEditCommand(index, fields, options);
    }

    /**
     * Performs validation and return the QuestionDeleteCommand object.
     *
     * @param index       of question in the list.
     * @param argMultimap for tokenized input.
     * @return QuestionDeleteCommand object.
     * @throws ParseException
     */
    private QuestionDeleteCommand deleteCommand(Index index, ArgumentMultimap argMultimap)
        throws ParseException {
        try {
            int indexToDelete = Integer
                .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

            if (indexToDelete <= 0) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        QuestionDeleteCommand.MESSAGE_USAGE));
            }
            index.fromOneBased(indexToDelete);
        } catch (NumberFormatException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    QuestionDeleteCommand.MESSAGE_USAGE));
        }

        return new QuestionDeleteCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
