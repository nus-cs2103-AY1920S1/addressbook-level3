package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.leaderboardcommand.LeaderboardCommand;
import seedu.address.logic.commands.leaderboardcommand.LeaderboardWithRandomCommand;
import seedu.address.logic.commands.leaderboardcommand.SimpleLeaderboardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Parses the user's leaderboard command in the context of a
 * {@code LeaderboardCommand} command.
 */
public class LeaderboardCommandParser implements Parser<LeaderboardCommand> {

    private static final String METHOD_SPLIT_REGEX = "\\s+";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the {@code userInput} and returns a new {@link SimpleLeaderboardCommand} or
     * {@link LeaderboardWithRandomCommand} object depending on the situation. If no tie-break methods are specified
     * then a {@link SimpleLeaderboardCommand} object without any comparators is created and
     * returned. If there are tie-break methods present, denoted by the prefix "tb/" then the {@code parse}
     * method will parse the tie-break methods specified and return a {@link SimpleLeaderboardCommand} with the
     * appropriate comparators as arguments. If "random" is selected as a method of tie-break as well then a
     * {@link LeaderboardWithRandomCommand} with the appropriate comparators will be created and returned.
     *
     * @return new LeaderboardCommand object.
     * @throws ParseException if the user input is in an incorrect format.
     */
    @Override
    public LeaderboardCommand parse(String userInput) throws ParseException {
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();
        SubjectName subject = null;

        ArgumentMultimap argumentMultimap = ArgumentTokenizer
                .tokenize(userInput, PREFIX_TIE_BREAK, PREFIX_SUBJECT_NAME);

        // Ensure there is no extra text before first valid prefix.
        ensureEmptyPreamble(argumentMultimap);

        if (AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_SUBJECT_NAME)) {
            logger.info("Subject Name specified for Leaderboard Command.");
            subject = AlfredParserUtil.parseSubject(argumentMultimap.getValue(PREFIX_SUBJECT_NAME).get());
        }

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)) {
            logger.info("No Tiebreak Methods specified for Leaderboard Command.");
            return new SimpleLeaderboardCommand(comparators, subject);
        }

        // Split on whitespace as this is what is required from the user.
        String[] tieBreakMethods = argumentMultimap.getValue(PREFIX_TIE_BREAK).get().split(METHOD_SPLIT_REGEX);

        comparators = AlfredParserUtil.processedComparators(tieBreakMethods);

        return AlfredParserUtil.isRandomPresent(tieBreakMethods)
                ? new LeaderboardWithRandomCommand(comparators, subject)
                : new SimpleLeaderboardCommand(comparators, subject);
    }

    /**
     * Checks whether the {@code ArgumentMultimap} 's preamble (text before the first valid prefix)
     * is empty.
     *
     * @param argumentMultimap the {@code ArgumentMultimap} whose preamble is to be checked.
     * @throws ParseException if the preamble is not empty.
     */
    private void ensureEmptyPreamble(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)
                && !argumentMultimap.getPreamble().isEmpty()) {
            logger.severe("Preamble must be empty.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaderboardCommand.MESSAGE_USAGE));
        }
    }
}
