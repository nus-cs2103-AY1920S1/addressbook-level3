package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.logic.commands.leaderboardcommand.LeaderboardCommand;
import seedu.address.logic.commands.leaderboardcommand.ShowLeaderboardWithRandomCommand;
import seedu.address.logic.commands.leaderboardcommand.ShowSimpleLeaderboardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Team;

/**
 * Parses the user's leaderboard command in the context of a
 * {@code LeaderboardCommand} command.
 */
public class ShowLeaderBoardCommandParser implements Parser<LeaderboardCommand> {

    private static final String METHOD_SPLIT_REGEX = "\\s+";

    /**
     * Parses the {@code userInput} and determines whether a new {@link ShowSimpleLeaderboardCommand} or
     * {@link ShowLeaderboardWithRandomCommand} needs to be created and returned. If no tie-break methods are specified
     * then a {@link ShowSimpleLeaderboardCommand} object without any comparators is created and
     * returned. However, if there are tie-break methods present, denoted by the
     * prefix "tb" then the {@code parse} method will parse the tie-break methods
     * specified and return a {@link ShowSimpleLeaderboardCommand} with the appropriate comparators as arguments.
     * If "random" is selected as a method of tie-break as well then a {@link ShowLeaderboardWithRandomCommand}
     * with the appropriate comparators will be created and returned.
     *
     * @return
     * @throws ParseException
     */
    @Override
    public LeaderboardCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TIE_BREAK);
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)
                && !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaderboardCommand.MESSAGE_USAGE));
        }

        if (!AlfredParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_TIE_BREAK)) {
            return new ShowSimpleLeaderboardCommand(comparators);
        }

        // Split on whitespace as this is what is required from the user.
        String[] tieBreakMethods = argumentMultimap.getValue(PREFIX_TIE_BREAK).get().split(METHOD_SPLIT_REGEX);

        comparators = AlfredParserUtil.processedComparators(tieBreakMethods);

        return AlfredParserUtil.isRandomPresent(tieBreakMethods) ? new ShowLeaderboardWithRandomCommand(comparators)
                : new ShowSimpleLeaderboardCommand(comparators);
    }





}
