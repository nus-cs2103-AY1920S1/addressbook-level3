package seedu.weme.logic.prompter;

import static seedu.weme.logic.parser.ParserUtil.MESSAGE_INVALID_CONTEXT;
import static seedu.weme.logic.prompter.PrompterUtil.CONTEXT_LIST;
import static seedu.weme.logic.prompter.PrompterUtil.findSimilarContexts;

import java.util.List;

import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;

/**
 * Prompt possible contexts for a tab command.
 */
public class TabCommandPrompter implements Prompter {
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        String context = arguments.trim();
        if (context.isBlank()) {
            return new CommandPrompt(CONTEXT_LIST.stream().reduce((x, y) -> x + '\n' + y).get());
        }

        StringBuilder prompt = new StringBuilder();
        List<String> possibleContexts = findSimilarContexts(context);
        if (possibleContexts.isEmpty()) {
            throw new PromptException(MESSAGE_INVALID_CONTEXT);
        }
        for (String possibleContext: possibleContexts) {
            prompt.append(possibleContext + '\n');
        }
        return new CommandPrompt(prompt.toString());
    }
}
