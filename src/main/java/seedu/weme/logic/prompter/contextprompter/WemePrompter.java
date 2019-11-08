package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.generalcommand.ExitCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.prompter.commandprompter.generalcommandprompter.TabCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
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
        switch (commandWord) {

        case TabCommand.COMMAND_WORD:
            return new TabCommandPrompter().prompt(model, userInput);

        case UndoCommand.COMMAND_WORD:
            return new CommandPrompt(UndoCommand.MESSAGE_USAGE, userInput);

        case RedoCommand.COMMAND_WORD:
            return new CommandPrompt(RedoCommand.MESSAGE_USAGE, userInput);

        case ExitCommand.COMMAND_WORD:
            return new CommandPrompt(ExitCommand.MESSAGE_USAGE, userInput);

        case HelpCommand.COMMAND_WORD:
            return new CommandPrompt(HelpCommand.MESSAGE_USAGE, userInput);

        default:
            return new CommandPrompt(userInput);
        }
    }
}


