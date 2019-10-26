package seedu.weme.logic.prompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION_STRING;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH_STRING;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG_STRING;
import static seedu.weme.model.ModelContext.CONTEXT_EXPORT;
import static seedu.weme.model.ModelContext.CONTEXT_IMPORT;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.model.ModelContext.CONTEXT_STATISTICS;
import static seedu.weme.model.ModelContext.CONTEXT_TEMPLATES;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;

import seedu.weme.logic.commands.ExitCommand;
import seedu.weme.logic.commands.ExportCommand;
import seedu.weme.logic.commands.HelpCommand;
import seedu.weme.logic.commands.ImportCommand;
import seedu.weme.logic.commands.LoadCommand;
import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.commands.MemeClearCommand;
import seedu.weme.logic.commands.MemeDeleteCommand;
import seedu.weme.logic.commands.MemeEditCommand;
import seedu.weme.logic.commands.MemeFindCommand;
import seedu.weme.logic.commands.MemeLikeCommand;
import seedu.weme.logic.commands.MemeListCommand;
import seedu.weme.logic.commands.MemeStageCommand;
import seedu.weme.logic.commands.RedoCommand;
import seedu.weme.logic.commands.TabCommand;
import seedu.weme.logic.commands.UndoCommand;
import seedu.weme.logic.commands.UnstageCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Contains utility methods used for prompting commands in the various *Prompter classes.
 */
public class PrompterUtil {
    public static final Set<String> CONTEXT_LIST = Set.of(
            CONTEXT_MEMES.getContextName(),
            CONTEXT_TEMPLATES.getContextName(),
            CONTEXT_STATISTICS.getContextName(),
            CONTEXT_EXPORT.getContextName(),
            CONTEXT_IMPORT.getContextName());

    public static final Set<String> COMMAND_LIST = Set.of(
            ExitCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ImportCommand.COMMAND_WORD,
            LoadCommand.COMMAND_WORD,
            MemeAddCommand.COMMAND_WORD,
            MemeClearCommand.COMMAND_WORD,
            MemeDeleteCommand.COMMAND_WORD,
            MemeEditCommand.COMMAND_WORD,
            MemeFindCommand.COMMAND_WORD,
            MemeLikeCommand.COMMAND_WORD,
            MemeListCommand.COMMAND_WORD,
            MemeStageCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,
            TabCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD,
            UnstageCommand.COMMAND_WORD);


    public static final WemePrompter WEME_PROMPTER = new WemePrompter();
    public static final WemePrompter MEME_PROMPTER = new MemePrompter();

    public static final int MAX_RESULTS_DISPLAY = 3;

    /**
     * Returns a Prompter depending on the given ModelContext.
     * @param modelContext current context
     * @return Prompter to prompt command
     */
    public static WemePrompter forContext(ModelContext modelContext) {
        switch (modelContext) {
        case CONTEXT_MEMES:
            return MEME_PROMPTER;
        case CONTEXT_TEMPLATES:
        case CONTEXT_STATISTICS:
        case CONTEXT_EXPORT:
        case CONTEXT_IMPORT:
            // TODO: This is a temporary placeholder until all tabs have been implemented
            return WEME_PROMPTER;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check whether a string (start) starts with another string (full).
     * @param start the shorter string as a start
     * @param full the string that starts with the shorter string
     */
    public static boolean startsWith(String start, String full) {
        if (start.length() > full.length()) {
            return false;
        }

        return full.substring(0, start.length()).equals(start);
    }

    /**
     * Compare two strings based on their similarity with the argument.
     * @return Int which is used by comparator
     */
    private static int compareStrings(String x, String y, String argument) {
        if (startsWith(argument, x) && startsWith(argument, y)) {
            return x.length() - y.length();
        } else if (startsWith(argument, x)) {
            return -1;
        } else if (startsWith(argument, y)) {
            return 1;
        } else {
            LevenshteinDistance ld = new LevenshteinDistance();
            return ld.apply(x, argument) - ld.apply(y, argument);
        }
    }

    /**
     * Find most similar records from current data for the argument in user input.
     * @return String containing the most similar records separated by line break.
     */
    public static String findSimilarStrings(Set<String> strings, String argument) {
        return strings
                .stream()
                .sorted((a, b) -> compareStrings(a.toLowerCase(), b.toLowerCase(), argument.toLowerCase()))
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse("");
    }


    /**
     * Find most similar arguments from current data for the last argument in user input.
     * @return CommandPrompt including the most similar arguments
     */
    public static CommandPrompt findSimilarArguments(Model model, LastArgument lastArgument)
            throws PromptException {
        switch (lastArgument.getPrefix().toString()) {
        case PREFIX_FILEPATH_STRING:
            return new CommandPrompt(findSimilarStrings(model.getPathRecords(), lastArgument.getArgument()));

        case PREFIX_DESCRIPTION_STRING:
            return new CommandPrompt(findSimilarStrings(model.getDescriptionRecords(), lastArgument.getArgument()));

        case PREFIX_TAG_STRING:
            return new CommandPrompt(findSimilarStrings(model.getTagRecords(), lastArgument.getArgument()));

        case PREFIX_NAME_STRING:
            return new CommandPrompt(findSimilarStrings(model.getNameRecords(), lastArgument.getArgument()));

        default:
            throw new PromptException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Returns contexts that start with the input string in alphabetical order.
     */
    public static List<String> findSimilarContexts(String input) {
        return CONTEXT_LIST
                .stream()
                .filter(c -> startsWith(input, c))
                .sorted()
                .limit(MAX_RESULTS_DISPLAY)
                .collect(Collectors.toList());
    }
}
