package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.IMPORT_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.importcommand.ImportCommand;
import seedu.weme.logic.commands.importcommand.LoadCommand;
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
            return new CommandPrompt(IMPORT_COMMANDS.stream().sorted().reduce((x, y) -> x + '\n' + y).orElse(""),
                    IMPORT_COMMANDS.stream().sorted().findFirst().orElse(""));
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
            return new CommandPrompt(userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(IMPORT_COMMANDS, commandWord);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
