package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.logic.commands.memecommand.MemeFindCommand.COMMAND_WORD;
import static seedu.weme.logic.commands.memecommand.MemeFindCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarMemeArguments;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;
import seedu.weme.model.tag.Tag;

/**
 * Prompt arguments for MemeFindCommand.
 */
public class MemeFindCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;


    /**
     * Find similar tags in the records for the last tag in the input separated by white space.
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
        LastArgument lastArgument = new LastArgument(PREFIX_TAG, tokens[tokens.length - 1].trim());
        Comparator<String> tagComparator = (x, y) -> {
            int countX = model.getCountOfTag(new Tag(x));
            int countY = model.getCountOfTag(new Tag(y));
            if (countX == countY) {
                return x.compareTo(y);
            }

            return countY - countX;
        };

        if (lastArgument.getArgument().isEmpty()) {
            List<String> possibleArguments = model
                    .getTagRecords()
                    .stream()
                    .sorted(tagComparator)
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
        return promptSimilarMemeArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
