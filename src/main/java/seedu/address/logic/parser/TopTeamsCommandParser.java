package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIE_BREAK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.topteamscommand.SimpleTopTeamsCommand;
import seedu.address.logic.commands.topteamscommand.TopTeamsCommand;
import seedu.address.logic.commands.topteamscommand.TopTeamsRandomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Parses input arguments and creates a new {@link TopTeamsCommand} object.
 */
public class TopTeamsCommandParser implements Parser<TopTeamsCommand> {

    private static final String METHOD_SPLIT_REGEX = "\\s+";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the {@code userInput} and returns a new {@link SimpleTopTeamsCommand} or
     * {@link TopTeamsRandomCommand} depending on the situation. If no tie-break methods are specified
     * then a {@link SimpleTopTeamsCommand} object without any comparators is created and returned. If there
     * are tie-break methods present, denoted by the prefix "tb" then the {@code parse} method will parse the tie-break
     * methods specified and return a {@link SimpleTopTeamsCommand} with the appropriate comparators as arguments. If
     * "random" is selected as a method of tie-break as well then a {@link TopTeamsRandomCommand} with the appropriate
     * comparators will be created and returned.
     *
     * @return a new TopTeamCommands object.
     * @throws ParseException if there user input is invalid.
     */
    @Override
    public TopTeamsCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        SubjectName subjectName = null;
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();

        ArgumentMultimap argumentMultimap = ArgumentTokenizer
                .tokenize(userInput, PREFIX_TIE_BREAK, PREFIX_SUBJECT_NAME);

        String numberOfTeams = argumentMultimap.getPreamble();
        validateValueOfUserInput(numberOfTeams);
        int topK = Integer.parseInt(numberOfTeams);

        if (argumentMultimap.getValue(PREFIX_SUBJECT_NAME).isPresent()) {
            logger.info("Subject name specified for Top Teams Command.");
            subjectName = AlfredParserUtil.parseSubject(argumentMultimap.getValue(PREFIX_SUBJECT_NAME).get());
        }

        if (argumentMultimap.getValue(PREFIX_TIE_BREAK).isEmpty()) {
            logger.info("No Tiebreak Methods specified for Top Teams Command.");
            return new SimpleTopTeamsCommand(topK, comparators, subjectName);
        }

        String[] tieBreakMethods = argumentMultimap.getValue(PREFIX_TIE_BREAK).get().split(METHOD_SPLIT_REGEX);
        comparators = AlfredParserUtil.processedComparators(tieBreakMethods);

        return AlfredParserUtil.isRandomPresent(tieBreakMethods)
                ? new TopTeamsRandomCommand(topK, comparators, subjectName)
                : new SimpleTopTeamsCommand(topK, comparators, subjectName);
    }

    /**
     * Checks whether the user inputted String {@code numberOfTeams} is a valid positive integer.
     *
     * @throws ParseException if the user inputted String is not of the correct format.
     */
    private void validateValueOfUserInput(String numberOfTeams) throws ParseException {
        if (!StringUtil.isNonZeroUnsignedInteger(numberOfTeams)) {
            logger.severe("Invalid value given for K - Value is not a valid positive integer: " + numberOfTeams);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TopTeamsCommand.INVALID_VALUE_WARNING));
        }
    }
}
