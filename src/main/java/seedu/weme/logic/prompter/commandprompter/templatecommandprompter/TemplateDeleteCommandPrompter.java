package seedu.weme.logic.prompter.commandprompter.templatecommandprompter;

import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_LISTED_MEME;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt arguments for TemplateDeleteCommand.
 */
public class TemplateDeleteCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        return new CommandPrompt(model
                .getFilteredTemplateList()
                .stream()
                .map(meme -> String.valueOf(model.getFilteredTemplateList().indexOf(meme) + 1))
                .sorted()
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse(NO_LISTED_MEME));
    }
}
