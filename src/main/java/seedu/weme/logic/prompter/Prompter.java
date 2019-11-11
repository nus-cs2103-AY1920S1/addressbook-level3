package seedu.weme.logic.prompter;

import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Represent an prompter that suggest command prompts given command arguments.
 */
public interface Prompter {
    /**
     * Suggest possible command arguments for the user input.
     */
    CommandPrompt prompt(Model model, String userInput) throws PromptException;
}
