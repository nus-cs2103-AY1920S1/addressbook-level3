package seedu.weme.logic.prompter.commandprompter.generalcommandprompter;

import static seedu.weme.logic.commands.generalcommand.TabCommand.COMMAND_WORD;
import static seedu.weme.logic.commands.generalcommand.TabCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ParserUtil.MESSAGE_INVALID_TAB;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.CONTEXTS;
import static seedu.weme.logic.prompter.util.PrompterUtil.findMostSimilarString;
import static seedu.weme.logic.prompter.util.PrompterUtil.findSimilarStrings;

import java.util.regex.Matcher;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt possible contexts for a tab command.
 */
public class TabCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);
        String context = arguments.trim();

        if (context.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        String similarContexts = findSimilarStrings(CONTEXTS, context);
        String mostSimilarContext = findMostSimilarString(CONTEXTS, context);
        if (similarContexts.isEmpty()) {
            throw new PromptException(MESSAGE_INVALID_TAB);
        }
        return new CommandPrompt(similarContexts, PREAMBLE + mostSimilarContext);
    }
}
