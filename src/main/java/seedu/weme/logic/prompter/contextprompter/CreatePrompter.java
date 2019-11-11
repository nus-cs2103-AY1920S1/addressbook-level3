package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.CREATE_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.CREATE_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.createcommand.AbortCreationCommand;
import seedu.weme.logic.commands.createcommand.CreateCommand;
import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.commands.createcommand.TextDeleteCommand;
import seedu.weme.logic.commands.createcommand.TextEditCommand;
import seedu.weme.logic.commands.createcommand.TextMoveCommand;
import seedu.weme.logic.prompter.commandprompter.createcommandprompter.TextAddCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.createcommandprompter.TextDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.createcommandprompter.TextEditCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.createcommandprompter.TextMoveCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the create context.
 */
public class CreatePrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(CREATE_COMMANDS
                    .stream()
                    .sorted()
                    .map(command -> CREATE_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    CREATE_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case TextAddCommand.COMMAND_WORD:
            return new TextAddCommandPrompter().prompt(model, userInput);

        case TextDeleteCommand.COMMAND_WORD:
            return new TextDeleteCommandPrompter().prompt(model, userInput);

        case TextEditCommand.COMMAND_WORD:
            return new TextEditCommandPrompter().prompt(model, userInput);

        case TextMoveCommand.COMMAND_WORD:
            return new TextMoveCommandPrompter().prompt(model, userInput);

        case AbortCreationCommand.COMMAND_WORD:
            return new CommandPrompt(AbortCreationCommand.MESSAGE_USAGE, userInput);

        case CreateCommand.COMMAND_WORD:
            return new CommandPrompt(CreateCommand.MESSAGE_USAGE, userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(CREATE_COMMANDS, commandWord, CREATE_COMMANDS_DESCRIPTION_MAP);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
