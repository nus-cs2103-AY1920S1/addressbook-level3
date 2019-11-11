package seedu.elisa.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_SNOOZE;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.logic.LogicManager;
import seedu.elisa.logic.commands.SnoozeCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SnoozeCommandParser implements Parser<SnoozeCommand> {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private Duration defaultSnoozeDuration = Duration.of(5, ChronoUnit.MINUTES);

    /**
     * Parses the given {@code description} and {@code args} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SnoozeCommand parse(String description, String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = new ArgumentMultimap();
        String processArgs = args + " "; // account for the possibility that --tk or --r or --e is given with no space
        try {
            argMultiMap = ArgumentTokenizer.tokenize(processArgs, PREFIX_SNOOZE);
        } catch (Exception e) {
            logger.info("Failure to tokenize arguments: SnoozeCommand");
            throw new ParseException("Snooze command format is incorrect.");
        }

        Index index;
        boolean hasIndex;

        if (!description.isBlank()) {
            try {
                index = ParserUtil.parseIndex(description);
                hasIndex = true;
            } catch (ParseException pe) {
                //TODO: Auto snooze most recent reminder
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SnoozeCommand.MESSAGE_USAGE), pe);
            }
        } else {
            index = null;
            hasIndex = false;
        }

        LocalDateTime newReminderOccurrence = null;

        if (argMultiMap.getValue(PREFIX_SNOOZE).isPresent()) {
            newReminderOccurrence = ParserUtil.parseSnooze(argMultiMap.getValue(PREFIX_SNOOZE).get()).get();
        } else {
            newReminderOccurrence = LocalDateTime.now().plus(defaultSnoozeDuration);
        }

        return new SnoozeCommand(hasIndex, index, newReminderOccurrence);
    }

}
