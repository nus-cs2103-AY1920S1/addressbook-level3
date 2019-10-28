package seedu.weme.logic.prompter.commandprompter.generalcommandprompter;

import static seedu.weme.logic.parser.util.ParserUtil.MESSAGE_INVALID_TAB;
import static seedu.weme.logic.prompter.util.PrompterUtil.CONTEXTS;
import static seedu.weme.logic.prompter.util.PrompterUtil.findSimilarContexts;

import java.util.List;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt possible contexts for a tab command.
 */
public class TabCommandPrompter implements Prompter {
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        String context = arguments.trim();
        if (context.isBlank()) {
            return new CommandPrompt(CONTEXTS.stream().reduce((x, y) -> x + '\n' + y).get());
        }

        StringBuilder prompt = new StringBuilder();
        List<String> possibleContexts = findSimilarContexts(context);
        if (possibleContexts.isEmpty()) {
            throw new PromptException(MESSAGE_INVALID_TAB);
        }
        for (String possibleContext: possibleContexts) {
            prompt.append(possibleContext + '\n');
        }
        return new CommandPrompt(prompt.toString());
    }
}
