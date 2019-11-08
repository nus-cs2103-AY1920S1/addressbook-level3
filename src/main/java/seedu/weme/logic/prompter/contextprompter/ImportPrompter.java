package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.IMPORT_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.IMPORT_COMMANDS_DESCRIPTION_MAP;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.importcommand.ImportClearCommand;
import seedu.weme.logic.commands.importcommand.ImportCommand;
import seedu.weme.logic.commands.importcommand.ImportDeleteCommand;
import seedu.weme.logic.commands.importcommand.ImportEditCommand;
import seedu.weme.logic.commands.importcommand.LoadCommand;
import seedu.weme.logic.prompter.commandprompter.importcommandprompter.ImportDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.importcommandprompter.ImportEditCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.importcommandprompter.LoadCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the import context.
 */
public class ImportPrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(IMPORT_COMMANDS
                    .stream()
                    .sorted()
                    .map(command -> IMPORT_COMMANDS_DESCRIPTION_MAP.get(command))
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(""),
                    IMPORT_COMMANDS.stream().sorted().findFirst().orElse("") + COMMAND_DELIMITER);
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case LoadCommand.COMMAND_WORD:
            return new LoadCommandPrompter().prompt(model, userInput);

        case ImportCommand.COMMAND_WORD:
            return new CommandPrompt(ImportCommand.MESSAGE_USAGE, userInput);

        case ImportDeleteCommand.COMMAND_WORD:
            return new ImportDeleteCommandPrompter().prompt(model, userInput);

        case ImportEditCommand.COMMAND_WORD:
            return new ImportEditCommandPrompter().prompt(model, userInput);

        case ImportClearCommand.COMMAND_WORD:
            return new CommandPrompt(ImportClearCommand.MESSAGE_USAGE, userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(IMPORT_COMMANDS, commandWord, IMPORT_COMMANDS_DESCRIPTION_MAP);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
