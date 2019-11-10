package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.VIEW_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.VIEW_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the view context.
 */
public class ViewPrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(VIEW_COMMANDS
                    .stream()
                    .sorted()
                    .map(command -> VIEW_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    VIEW_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);

        // Ignore if trying to do undo or redo
        if (GENERAL_COMMANDS.contains(commandWord) && !commandWord.equals(UndoCommand.COMMAND_WORD)
            && !commandWord.equals(RedoCommand.COMMAND_WORD)) {
            return super.promptCommand(model, userInput);
        }

        if (arguments.isBlank()) {
            return promptCommandWord(VIEW_COMMANDS, commandWord, VIEW_COMMANDS_DESCRIPTION_MAP);
        } else {
            throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
