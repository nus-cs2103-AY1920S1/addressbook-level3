package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.EXPORT_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.EXPORT_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.exportcommand.ExportClearCommand;
import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.prompter.commandprompter.exportcommandpropmter.ExportCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.exportcommandpropmter.UnstageCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the export context.
 */
public class ExportPrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(EXPORT_COMMANDS
                    .stream()
                    .sorted()
                    .map(command -> EXPORT_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    EXPORT_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case UnstageCommand.COMMAND_WORD:
            return new UnstageCommandPrompter().prompt(model, userInput);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandPrompter().prompt(model, userInput);

        case ExportClearCommand.COMMAND_WORD:
            return new CommandPrompt(ExportClearCommand.MESSAGE_USAGE, userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(EXPORT_COMMANDS, commandWord, EXPORT_COMMANDS_DESCRIPTION_MAP);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
