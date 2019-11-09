package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;

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

    /**
     * Parses the {@code userInput} and determines whether a new {@link SimpleLeaderboardCommand} or
     * {@link LeaderboardWithRandomCommand} needs to be created and returned. If no tie-break methods are specified
     * then a {@link SimpleLeaderboardCommand} object without any comparators is created and
     * returned. However, if there are tie-break methods present, denoted by the
     * prefix "tb" then the {@code parse} method will parse the tie-break methods
     * specified and return a {@link SimpleLeaderboardCommand} with the appropriate comparators as arguments.
     * If "random" is selected as a method of tie-break as well then a {@link LeaderboardWithRandomCommand}
     * with the appropriate comparators will be created and returned.
     *
     * @return
     * @throws ParseException
     */
    @Override
    public LeaderboardCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer
                .tokenize(userInput, PREFIX_TIE_BREAK, PREFIX_SUBJECT_NAME);
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();

        SubjectName subject = null;

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)
                && !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaderboardCommand.MESSAGE_USAGE));
        }

        if (AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_SUBJECT_NAME)) {
            subject = AlfredParserUtil.parseSubject(argumentMultimap.getValue(PREFIX_SUBJECT_NAME).get());
        }

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)) {
            return new SimpleLeaderboardCommand(comparators, subject);
        }

        // Split on whitespace as this is what is required from the user.
        String[] tieBreakMethods = argumentMultimap.getValue(PREFIX_TIE_BREAK).get().split(METHOD_SPLIT_REGEX);

        comparators = AlfredParserUtil.processedComparators(tieBreakMethods);

        return AlfredParserUtil.isRandomPresent(tieBreakMethods)
                ? new LeaderboardWithRandomCommand(comparators, subject)
                : new SimpleLeaderboardCommand(comparators, subject);
    }





}
