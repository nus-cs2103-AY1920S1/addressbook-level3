package seedu.guilttrip.logic.parser.remindercommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_LOWER_BOUND;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_UPPER_BOUND;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand.EditEntryReminderDescriptor;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand.EditGeneralReminderDescriptor;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand.EditReminderDescriptor;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditReminderCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditReminderCommand and returns an EditReminderCommand object for
     * execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC,
                        PREFIX_UPPER_BOUND, PREFIX_LOWER_BOUND,
                        PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG, PREFIX_PERIOD, PREFIX_FREQ);

        Index index;

        boolean isGeneralReminder = isPrefixPresent(argMultimap, PREFIX_UPPER_BOUND, PREFIX_LOWER_BOUND,
                PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG);
        boolean isEntryReminder = isPrefixPresent(argMultimap, PREFIX_PERIOD, PREFIX_FREQ);
        if (isEntryReminder && isGeneralReminder) {
            throw new ParseException(EditReminderCommand.MISMATCHING_REMINDER_TYPES);
        }

        EditReminderDescriptor editReminderDescriptor;
        if (isGeneralReminder) {
            editReminderDescriptor = new EditReminderCommand.EditGeneralReminderDescriptor();
            EditGeneralReminderDescriptor editGeneralReminderDescriptor =
                    (EditGeneralReminderDescriptor) editReminderDescriptor;
            if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
                editGeneralReminderDescriptor.setHeader(
                        ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
            }
            if (argMultimap.getValue(PREFIX_LOWER_BOUND).isPresent()) {
                editGeneralReminderDescriptor.setLowerBound(
                        ParserUtil.parseAmount(argMultimap.getValue(PREFIX_LOWER_BOUND).get()).value);
            }
            if (argMultimap.getValue(PREFIX_UPPER_BOUND).isPresent()) {
                editGeneralReminderDescriptor.setUpperBound(
                        ParserUtil.parseAmount(argMultimap.getValue(PREFIX_UPPER_BOUND).get()).value);
            }
            if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
                editGeneralReminderDescriptor.setStart(
                        ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
            }
            if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
                editGeneralReminderDescriptor.setEnd(
                        ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editGeneralReminderDescriptor::setTags);
            logger.info("Params match GeneralReminder.");
            if (!editGeneralReminderDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
            }
            return new EditReminderCommand(editGeneralReminderDescriptor);
        } else if (isEntryReminder) {
            editReminderDescriptor = new EditEntryReminderDescriptor();
            EditEntryReminderDescriptor editEntryReminderDescriptor =
                    (EditEntryReminderDescriptor) editReminderDescriptor;
            if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
                editEntryReminderDescriptor.setHeader(
                        ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
            }
            if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
                editEntryReminderDescriptor.setPeriod(
                        ParserUtil.parsePeriods(argMultimap.getValue(PREFIX_PERIOD).get()));
            }
            if (argMultimap.getValue(PREFIX_FREQ).isPresent()) {
                editEntryReminderDescriptor.setFrequency(
                        ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get()));
            }
            logger.info("Params match EntryReminder.");
            if (!editEntryReminderDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
            }
            return new EditReminderCommand(editEntryReminderDescriptor);

        } else if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editReminderDescriptor = new EditReminderDescriptor();
            editReminderDescriptor.setHeader(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
            return new EditReminderCommand(editReminderDescriptor);
        } else {
            throw new ParseException(EditReminderCommand.MISMATCHING_REMINDER_TYPES);
        }
    }


    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
