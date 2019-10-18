package seedu.weme.logic.prompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.WemeParser.COMMAND_WORD;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.ExitCommand;
import seedu.weme.logic.commands.HelpCommand;
import seedu.weme.logic.commands.RedoCommand;
import seedu.weme.logic.commands.TabCommand;
import seedu.weme.logic.commands.UndoCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;

/**
 * Prompt commands based on user input. Base context prompter that all context prompter inherit from.
 */
public class WemePrompter {

    /**
     * Provide a command prompt given user input.
     * @param userInput full user input string
     * @return command prompt based on user input
     * @throws PromptException if the user input does not conform the expected format
     */
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return CommandPrompt.empty();
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        switch (commandWord) {

        case TabCommand.COMMAND_WORD:
            return new TabCommandPrompter().prompt(model, arguments);

        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
            return CommandPrompt.empty();

        default:
            if (arguments.isBlank()) {
                return new CommandPrompter().prompt(model, commandWord);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}


