package seedu.weme.logic.prompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.prompter.PrompterUtil.COMMAND_LIST;
import static seedu.weme.logic.prompter.PrompterUtil.findSimilarStrings;

import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;

/**
 * Prompt possible command word.
 */
public class CommandPrompter implements Prompter {
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        String similarCommands = findSimilarStrings(COMMAND_LIST, arguments);
        if (similarCommands.isEmpty()) {
            throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
        }
        return new CommandPrompt(similarCommands);
    }
}
