package seedu.weme.logic.prompter.commandprompter.templatecommandprompter;

import static seedu.weme.logic.commands.templatecommand.TemplateUseCommand.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_LISTED_TEMPLATE;

import java.util.List;
import java.util.stream.Collectors;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt arguments for TemplateUseCommand.
 */
public class TemplateUseCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        List<String> possibleArguments = model
                .getFilteredTemplateList()
                .stream()
                .map(template -> String.valueOf(model.getFilteredTemplateList().indexOf(template) + 1))
                .sorted()
                .collect(Collectors.toList());

        return new CommandPrompt(
                possibleArguments
                        .stream()
                        .limit(MAX_RESULTS_DISPLAY)
                        .reduce((x, y) -> x + '\n' + y)
                        .orElse(NO_LISTED_TEMPLATE),
                PREAMBLE + possibleArguments
                        .stream()
                        .findFirst()
                        .orElse(NO_LISTED_TEMPLATE)
        );
    }
}
