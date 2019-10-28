package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.EXPORT_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.prompter.commandprompter.exportcommandpropmter.UnstageCommandPrompter;
import seedu.weme.logic.prompter.commandwordprompter.ExportCommandWordPrompter;
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
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""));
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case UnstageCommand.COMMAND_WORD:
            return new UnstageCommandPrompter().prompt(model, arguments);

        case ExportCommand.COMMAND_WORD:
            // TODO: add prompter for export command.
            return CommandPrompt.empty();

        default:
            if (arguments.isBlank()) {
                return new ExportCommandWordPrompter().prompt(model, commandWord);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
