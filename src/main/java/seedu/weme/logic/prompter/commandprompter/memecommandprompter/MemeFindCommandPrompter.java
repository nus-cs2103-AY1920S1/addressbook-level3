package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.logic.commands.memecommand.MemeFindCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.prompter.util.PrompterUtil.findSimilarArguments;

import java.util.Comparator;

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

    /**
     * Find similar tags in the records for the last tag in the input separated by white space.
     */
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE);
        }

        String[] tokens = arguments.split(" ");
        LastArgument lastArgument = new LastArgument(PREFIX_TAG, tokens[tokens.length - 1].trim());
        if (lastArgument.getArgument().isEmpty()) {
            return new CommandPrompt(model
                    .getTagRecords()
                    .stream()
                    .sorted()
                    .sorted(Comparator.comparingInt(x -> -model.getCountOfTag(new Tag(x))))
                    .reduce((t1, t2) -> t1 + '\n' + t2)
                    .orElse("")
            );
        }

        return findSimilarArguments(model, lastArgument);
    }
}
