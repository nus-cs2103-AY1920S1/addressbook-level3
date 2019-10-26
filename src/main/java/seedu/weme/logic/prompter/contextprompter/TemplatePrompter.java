package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.TEMPLATE_COMMANDS;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateAddCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateEditCommandPrompter;
import seedu.weme.logic.prompter.commandwordprompter.TemplateCommandWordPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the template context.
 */
public class TemplatePrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(TEMPLATE_COMMANDS
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
        case TemplateAddCommand.COMMAND_WORD:
            return new TemplateAddCommandPrompter().prompt(model, arguments);

        case TemplateDeleteCommand.COMMAND_WORD:
            return new TemplateDeleteCommandPrompter().prompt(model, arguments);

        case TemplateEditCommand.COMMAND_WORD:
            return new TemplateEditCommandPrompter().prompt(model, arguments);

        default:
            if (arguments.isBlank()) {
                return new TemplateCommandWordPrompter().prompt(model, commandWord);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
