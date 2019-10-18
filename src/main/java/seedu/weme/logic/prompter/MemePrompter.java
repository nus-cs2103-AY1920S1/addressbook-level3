package seedu.weme.logic.prompter;

import static seedu.weme.logic.parser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.WemeParser.COMMAND_WORD;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the meme context.
 */
public class MemePrompter extends WemePrompter {
    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return CommandPrompt.empty();
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        switch (commandWord) {
        case MemeAddCommand.COMMAND_WORD:
            return new MemeAddCommandPrompter().prompt(model, arguments);

        /*
        case MemeEditCommand.COMMAND_WORD:
            return new MemeEditCommandPrompter().prompt(arguments);

        case MemeDeleteCommand.COMMAND_WORD:
            return new MemeDeleteCommandPrompter().prompt(arguments);

        case MemeFindCommand.COMMAND_WORD:
            return new MemeFindCommandPrompter().prompt(arguments);

        case MemeClearCommand.COMMAND_WORD:
        case MemeListCommand.COMMAND_WORD:
            return CommandPrompt.empty();

        case MemeStageCommand.COMMAND_WORD:
            return new MemeStageCommandPrompter().prompt(arguments);
        */

        default:
            return super.promptCommand(model, userInput);
        }
    }
}
