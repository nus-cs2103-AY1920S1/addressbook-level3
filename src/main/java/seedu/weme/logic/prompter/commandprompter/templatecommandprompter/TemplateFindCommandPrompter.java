package seedu.weme.logic.prompter.commandprompter.templatecommandprompter;

import static seedu.weme.logic.commands.templatecommand.TemplateFindCommand.COMMAND_WORD;
import static seedu.weme.logic.commands.templatecommand.TemplateFindCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarTemplateArguments;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt arguments for TemplateFindCommand.
 */
public class TemplateFindCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;


    /**
     * Find similar names in the records for the last name in the input separated by white space.
     */
    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        String[] tokens = arguments.trim().split(" ");
        LastArgument lastArgument = new LastArgument(PREFIX_NAME, tokens[tokens.length - 1].trim());

        if (lastArgument.getArgument().isEmpty()) {
            List<String> possibleArguments = model
                    .getTagRecords()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());

            return new CommandPrompt(
                    possibleArguments
                            .stream()
                            .reduce((t1, t2) -> t1 + '\n' + t2)
                            .orElse(""),
                    possibleArguments
                            .stream()
                            .findFirst()
                            .orElse(""));
        }

        String inputWithoutLastArgument;
        if (tokens.length == 1) {
            inputWithoutLastArgument = PREAMBLE;
        } else {
            inputWithoutLastArgument = PREAMBLE + String.join(" ",
                    Arrays.copyOfRange(tokens, 0, tokens.length - 1)) + COMMAND_DELIMITER;
        }
        return promptSimilarTemplateArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
