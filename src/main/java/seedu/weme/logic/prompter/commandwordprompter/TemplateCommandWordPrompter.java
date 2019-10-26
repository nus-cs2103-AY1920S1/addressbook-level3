package seedu.weme.logic.prompter.commandwordprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.prompter.util.PrompterUtil.TEMPLATE_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.findSimilarStrings;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt possible command word under template context.
 */
public class TemplateCommandWordPrompter implements Prompter {
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        String similarCommands = findSimilarStrings(TEMPLATE_COMMANDS, arguments);
        if (similarCommands.isEmpty()) {
            throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
        }
        return new CommandPrompt(similarCommands);
    }
}
