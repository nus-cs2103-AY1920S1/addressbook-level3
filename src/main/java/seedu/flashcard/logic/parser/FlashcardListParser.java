package seedu.flashcard.logic.parser;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.commands.Command;
import seedu.flashcard.logic.commands.DeleteCommand;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.logic.commands.ExitCommand;
import seedu.flashcard.logic.commands.FindCommand;
import seedu.flashcard.logic.commands.FlipCommand;
import seedu.flashcard.logic.commands.HelpCommand;
import seedu.flashcard.logic.commands.ListCommand;
import seedu.flashcard.logic.commands.TagCommand;
import seedu.flashcard.logic.commands.ViewCommand;

import seedu.flashcard.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.commons.core.Messages.MESSAGE_UNKOWN_COMMAND;

public class FlashcardListParser {

/**
     * Used for initial separation of command word and args.
     */

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FlipCommand.COMMAND_WORD:
            return new FlipCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKOWN_COMMAND);
        }
    }

}
