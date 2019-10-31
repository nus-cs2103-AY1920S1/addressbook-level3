package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.insession.AttemptLiftedCommand;
import seedu.system.logic.commands.insession.NextLifterCommand;
import seedu.system.logic.commands.insession.RankCommand;
import seedu.system.logic.commands.insession.RanklistCommand;
import seedu.system.logic.commands.outofsession.AddCompetitionCommand;
import seedu.system.logic.commands.outofsession.AddParticipationCommand;
import seedu.system.logic.commands.outofsession.AddPersonCommand;
import seedu.system.logic.commands.outofsession.ClearCommand;
import seedu.system.logic.commands.outofsession.DeleteCompetitionCommand;
import seedu.system.logic.commands.outofsession.DeletePersonCommand;
import seedu.system.logic.commands.outofsession.EditCompetitionCommand;
import seedu.system.logic.commands.outofsession.EditPersonCommand;
import seedu.system.logic.commands.outofsession.ExitCommand;
import seedu.system.logic.commands.outofsession.FindPersonCommand;
import seedu.system.logic.commands.outofsession.ListCompetitionCommand;
import seedu.system.logic.commands.outofsession.ListParticipationCommand;
import seedu.system.logic.commands.outofsession.ListPersonCommand;
import seedu.system.logic.commands.outofsession.OutOfSessionHelpCommand;
import seedu.system.logic.commands.outofsession.OverallRankCommand;
import seedu.system.logic.commands.outofsession.StartSessionCommand;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.logic.parser.insession.AddParticipationCommandParser;
import seedu.system.logic.parser.insession.AttemptLiftedCommandParser;
import seedu.system.logic.parser.insession.ListParticipationCommandParser;
import seedu.system.logic.parser.insession.NewSessionCommandParser;
import seedu.system.logic.parser.insession.RankCommandParser;
import seedu.system.logic.parser.insession.RanklistCommandParser;
import seedu.system.logic.parser.outofsession.AddCompetitionCommandParser;
import seedu.system.logic.parser.outofsession.AddPersonCommandParser;
import seedu.system.logic.parser.outofsession.DeleteCompetitionCommandParser;
import seedu.system.logic.parser.outofsession.DeletePersonCommandParser;
import seedu.system.logic.parser.outofsession.EditCompetitionCommandParser;
import seedu.system.logic.parser.outofsession.EditPersonCommandParser;
import seedu.system.logic.parser.outofsession.FindPersonCommandParser;

/**
 * Parses user input.
 */
public class SystemParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                OutOfSessionHelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case AddCompetitionCommand.COMMAND_WORD:
            return new AddCompetitionCommandParser().parse(arguments);

        case AddParticipationCommand.COMMAND_WORD:
            return new AddParticipationCommandParser().parse(arguments);

        case AttemptLiftedCommand.COMMAND_WORD:
            return new AttemptLiftedCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditPersonCommandParser().parse(arguments);

        case EditCompetitionCommand.COMMAND_WORD:
            return new EditCompetitionCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case DeleteCompetitionCommand.COMMAND_WORD:
            return new DeleteCompetitionCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case ListPersonCommand.COMMAND_WORD:
            return new ListPersonCommand();

        case ListCompetitionCommand.COMMAND_WORD:
            return new ListCompetitionCommand();

        case ListParticipationCommand.COMMAND_WORD:
            return new ListParticipationCommandParser().parse(arguments);

        case RankCommand.COMMAND_WORD:
            return new RankCommandParser().parse(arguments);

        case RanklistCommand.COMMAND_WORD:
            return new RanklistCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case OutOfSessionHelpCommand.COMMAND_WORD:
            return new OutOfSessionHelpCommand();

        case NextLifterCommand.COMMAND_WORD:
            return new NextLifterCommand();

        case StartSessionCommand.COMMAND_WORD:
            return new NewSessionCommandParser().parse(arguments);

        case OverallRankCommand.COMMAND_WORD:
            return new OverallRankCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

