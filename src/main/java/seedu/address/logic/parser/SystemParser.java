package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RankCommand;
import seedu.address.logic.commands.insession.AddPartCommand;
import seedu.address.logic.commands.insession.AttemptLiftedCommand;
import seedu.address.logic.commands.insession.ListPartCommand;
import seedu.address.logic.commands.insession.NextLifterCommand;
import seedu.address.logic.commands.insession.SessionCommand;
import seedu.address.logic.commands.outofsession.AddCompetitionCommand;
import seedu.address.logic.commands.outofsession.AddPersonCommand;
import seedu.address.logic.commands.outofsession.ClearCommand;
import seedu.address.logic.commands.outofsession.DeleteCompetitionCommand;
import seedu.address.logic.commands.outofsession.DeletePersonCommand;
import seedu.address.logic.commands.outofsession.EditCompetitionCommand;
import seedu.address.logic.commands.outofsession.EditPersonCommand;
import seedu.address.logic.commands.outofsession.ExitCommand;
import seedu.address.logic.commands.outofsession.FindPersonCommand;
import seedu.address.logic.commands.outofsession.ListCompetitionCommand;
import seedu.address.logic.commands.outofsession.ListPersonCommand;
import seedu.address.logic.commands.outofsession.OutOfSessionHelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.participation.AddPartCommandParser;
import seedu.address.logic.parser.participation.ListPartCommandParser;
import seedu.address.logic.parser.session.AttemptLiftedCommandParser;
import seedu.address.logic.parser.session.NewSessionCommandParser;

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

        case AddPartCommand.COMMAND_WORD:
            return new AddPartCommandParser().parse(arguments);

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

        case ListPartCommand.COMMAND_WORD:
            return new ListPartCommandParser().parse(arguments);

        case RankCommand.COMMAND_WORD:
            return new RankCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case OutOfSessionHelpCommand.COMMAND_WORD:
            return new OutOfSessionHelpCommand();

        case NextLifterCommand.COMMAND_WORD:
            return new NextLifterCommand();

        case SessionCommand.COMMAND_WORD:
            return new NewSessionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

