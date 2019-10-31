package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.SPECIFY_MODE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.LogicManager;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.cheatsheet.AddCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.DeleteCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.FilterCheatSheetByTagCommand;
import seedu.address.logic.commands.cheatsheet.ViewCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.ViewSpecificCheatSheetTagCommand;
import seedu.address.logic.commands.flashcard.AddFlashcardCommand;
import seedu.address.logic.commands.flashcard.DeleteFlashcardCommand;
import seedu.address.logic.commands.flashcard.FilterFlashcardByTagCommand;
import seedu.address.logic.commands.flashcard.RemindCommand;
import seedu.address.logic.commands.flashcard.ShowFlashcardAnswerCommand;
import seedu.address.logic.commands.flashcard.StartTimeTrialCommand;
import seedu.address.logic.commands.flashcard.ViewFlashcardCommand;
import seedu.address.logic.commands.global.ExitCommand;
//import seedu.address.logic.commands.global.GlobalTagFilterCommand;
import seedu.address.logic.commands.global.FilterAllByTagCommand;
import seedu.address.logic.commands.global.HelpCommand;
import seedu.address.logic.commands.global.ListTagCommand;
import seedu.address.logic.commands.global.SwitchModeCommand;
import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.logic.commands.note.DeleteNoteCommand;
import seedu.address.logic.commands.note.FilterNoteByTagCommand;
import seedu.address.logic.commands.note.ViewNoteCommand;
import seedu.address.logic.commands.note.ViewRawNoteCommand;
import seedu.address.logic.parser.cheatsheet.AddCheatSheetCommandParser;
import seedu.address.logic.parser.cheatsheet.DeleteCheatSheetCommandParser;
import seedu.address.logic.parser.cheatsheet.EditCheatSheetCommandParser;
import seedu.address.logic.parser.cheatsheet.FilterCheatSheetByTagCommandParser;
import seedu.address.logic.parser.cheatsheet.ViewCheatSheetCommandParser;
import seedu.address.logic.parser.cheatsheet.ViewSpecificCheatSheetTagCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.flashcard.AddFlashcardCommandParser;
import seedu.address.logic.parser.flashcard.DeleteFlashcardCommandParser;
import seedu.address.logic.parser.flashcard.FilterFlashcardByTagCommandParser;
import seedu.address.logic.parser.flashcard.StartTimeTrialCommandParser;
import seedu.address.logic.parser.flashcard.ViewFlashcardCommandParser;
import seedu.address.logic.parser.note.AddNoteCommandParser;
import seedu.address.logic.parser.note.DeleteNoteCommandParser;
import seedu.address.logic.parser.note.FilterNoteByTagCommandParser;
import seedu.address.logic.parser.note.ViewNoteCommandParser;
import seedu.address.logic.parser.note.ViewRawNoteCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = (matcher.group("commandWord")).toLowerCase();
        final String arguments = matcher.group("arguments");

        switch (commandWord) { //global commands?
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListTagCommand.COMMAND_WORD:
            return new ListTagCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SwitchModeCommand.COMMAND_WORD:
            return new SwitchModeCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case FilterAllByTagCommand.COMMAND_WORD:
            return new FilterAllByTagCommandParser().parse(arguments);

        default:

            switch (LogicManager.getMode()) {
            case CHEATSHEET:
                return parseCheatSheetCommands(commandWord, arguments);

            case FLASHCARD:
                return parseFlashcardCommands(commandWord, arguments);

            case NOTE:
                return parseNoteCommands(commandWord, arguments);

            default:
                throw new ParseException(SPECIFY_MODE);
            }
        }
    }

    /**
     * Parses user input into command for execution.
     * @param commandWord the command to execute
     * @param arguments the parameters supplied to command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseCheatSheetCommands(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddCheatSheetCommand.COMMAND_WORD:
            return new AddCheatSheetCommandParser().parse(arguments);

        case DeleteCheatSheetCommand.COMMAND_WORD:
            return new DeleteCheatSheetCommandParser().parse(arguments);

        case EditCheatSheetCommand.COMMAND_WORD:
            return new EditCheatSheetCommandParser().parse(arguments);

        case FilterCheatSheetByTagCommand.COMMAND_WORD:
            return new FilterCheatSheetByTagCommandParser().parse(arguments);

        case ViewCheatSheetCommand.COMMAND_WORD:
            return new ViewCheatSheetCommandParser().parse(arguments);

        case ViewSpecificCheatSheetTagCommand.COMMAND_WORD:
            return new ViewSpecificCheatSheetTagCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution.
     * @param commandWord the command to execute
     * @param arguments the parameters supplied to command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseFlashcardCommands(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddFlashcardCommand.COMMAND_WORD:
            return new AddFlashcardCommandParser().parse(arguments);

        case DeleteFlashcardCommand.COMMAND_WORD:
            return new DeleteFlashcardCommandParser().parse(arguments);

        case FilterFlashcardByTagCommand.COMMAND_WORD:
            return new FilterFlashcardByTagCommandParser().parse(arguments);

        case ViewFlashcardCommand.COMMAND_WORD:
            return new ViewFlashcardCommandParser().parse(arguments);

        case StartTimeTrialCommand.COMMAND_WORD:
            return new StartTimeTrialCommandParser().parse(arguments);

        case ShowFlashcardAnswerCommand.COMMAND_WORD:
            return new ShowFlashcardAnswerCommand();

        case RemindCommand.COMMAND_WORD:
            return new RemindCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution.
     * @param commandWord the command to execute
     * @param arguments the parameters supplied to command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseNoteCommands(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddNoteCommand.COMMAND_WORD:
            return new AddNoteCommandParser().parse(arguments);

        case DeleteNoteCommand.COMMAND_WORD:
            return new DeleteNoteCommandParser().parse(arguments);

        case FilterNoteByTagCommand.COMMAND_WORD:
            return new FilterNoteByTagCommandParser().parse(arguments);

        case ViewNoteCommand.COMMAND_WORD:
            return new ViewNoteCommandParser().parse(arguments);

        case ViewRawNoteCommand.COMMAND_WORD:
            return new ViewRawNoteCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
