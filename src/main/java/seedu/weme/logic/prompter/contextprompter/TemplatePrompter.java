package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.TEMPLATE_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.TEMPLATE_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchiveCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchivesCommand;
import seedu.weme.logic.commands.templatecommand.TemplateClearCommand;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.commands.templatecommand.TemplateFindCommand;
import seedu.weme.logic.commands.templatecommand.TemplateListCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUnarchiveCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUseCommand;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateAddCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateArchiveCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateEditCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateFindCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateUnarchiveCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.templatecommandprompter.TemplateUseCommandPrompter;
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
                    .map(command -> TEMPLATE_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    TEMPLATE_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case TemplateAddCommand.COMMAND_WORD:
            return new TemplateAddCommandPrompter().prompt(model, userInput);

        case TemplateArchiveCommand.COMMAND_WORD:
            return new TemplateArchiveCommandPrompter().prompt(model, userInput);

        case TemplateArchivesCommand.COMMAND_WORD:
            return new CommandPrompt(TemplateArchivesCommand.MESSAGE_USAGE, userInput);

        case TemplateClearCommand.COMMAND_WORD:
            return new CommandPrompt(TemplateClearCommand.MESSAGE_USAGE, userInput);

        case TemplateDeleteCommand.COMMAND_WORD:
            return new TemplateDeleteCommandPrompter().prompt(model, userInput);

        case TemplateEditCommand.COMMAND_WORD:
            return new TemplateEditCommandPrompter().prompt(model, userInput);

        case TemplateFindCommand.COMMAND_WORD:
            return new TemplateFindCommandPrompter().prompt(model, userInput);

        case TemplateListCommand.COMMAND_WORD:
            return new CommandPrompt(TemplateListCommand.MESSAGE_USAGE, userInput);

        case TemplateUnarchiveCommand.COMMAND_WORD:
            return new TemplateUnarchiveCommandPrompter().prompt(model, userInput);

        case TemplateUseCommand.COMMAND_WORD:
            return new TemplateUseCommandPrompter().prompt(model, userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(TEMPLATE_COMMANDS, commandWord, TEMPLATE_COMMANDS_DESCRIPTION_MAP);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
