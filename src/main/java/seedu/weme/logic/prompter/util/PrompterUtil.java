package seedu.weme.logic.prompter.util;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE_STRING;
import static seedu.weme.model.ModelContext.CONTEXT_CREATE;
import static seedu.weme.model.ModelContext.CONTEXT_EXPORT;
import static seedu.weme.model.ModelContext.CONTEXT_IMPORT;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.model.ModelContext.CONTEXT_PREFERENCES;
import static seedu.weme.model.ModelContext.CONTEXT_STATISTICS;
import static seedu.weme.model.ModelContext.CONTEXT_TEMPLATES;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.text.similarity.LevenshteinDistance;

import seedu.weme.logic.commands.createcommand.AbortCreationCommand;
import seedu.weme.logic.commands.createcommand.CreateCommand;
import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.commands.generalcommand.ExitCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.commands.importcommand.ImportCommand;
import seedu.weme.logic.commands.importcommand.LoadCommand;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUseCommand;
import seedu.weme.logic.prompter.contextprompter.CreatePrompter;
import seedu.weme.logic.prompter.contextprompter.ExportPrompter;
import seedu.weme.logic.prompter.contextprompter.ImportPrompter;
import seedu.weme.logic.prompter.contextprompter.MemePrompter;
import seedu.weme.logic.prompter.contextprompter.PreferencePrompter;
import seedu.weme.logic.prompter.contextprompter.StatisticsPrompter;
import seedu.weme.logic.prompter.contextprompter.TemplatePrompter;
import seedu.weme.logic.prompter.contextprompter.WemePrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Contains utility methods used for prompting commands in the various *Prompter classes.
 */
public class PrompterUtil {
    public static final int PREFIX_LENGTH = 2;

    // Delimiter separating command word and arguments.
    public static final String COMMAND_DELIMITER = " ";

    public static final int MAX_RESULTS_DISPLAY = 3;

    public static final String NO_LISTED_MEME = "No meme listed for now!";

    public static final String NO_STAGED_MEME = "No meme found in staging!";

    public static final String NO_LISTED_TEMPLATE = "No template listed for now!";

    public static final Set<String> CONTEXTS = Set.of(
            CONTEXT_MEMES.getContextName(),
            CONTEXT_TEMPLATES.getContextName(),
            CONTEXT_CREATE.getContextName(),
            CONTEXT_STATISTICS.getContextName(),
            CONTEXT_EXPORT.getContextName(),
            CONTEXT_IMPORT.getContextName(),
            CONTEXT_PREFERENCES.getContextName());

    public static final Set<String> GENERAL_COMMANDS = Set.of(
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,
            TabCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD
    );

    public static final Set<String> MEME_COMMANDS = Stream
            .concat(Stream.of(
                    MemeAddCommand.COMMAND_WORD,
                    MemeClearCommand.COMMAND_WORD,
                    MemeDeleteCommand.COMMAND_WORD,
                    MemeEditCommand.COMMAND_WORD,
                    MemeFindCommand.COMMAND_WORD,
                    MemeLikeCommand.COMMAND_WORD,
                    MemeListCommand.COMMAND_WORD,
                    MemeStageCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Set<String> TEMPLATE_COMMANDS = Stream
            .concat(Stream.of(
                    TemplateAddCommand.COMMAND_WORD,
                    TemplateDeleteCommand.COMMAND_WORD,
                    TemplateEditCommand.COMMAND_WORD,
                    TemplateUseCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Set<String> CREATE_COMMANDS = Stream
            .concat(Stream.of(
                    AbortCreationCommand.COMMAND_WORD,
                    CreateCommand.COMMAND_WORD,
                    TextAddCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Set<String> STATISTICS_COMMANDS = new HashSet<>(GENERAL_COMMANDS);

    public static final Set<String> EXPORT_COMMANDS = Stream
            .concat(Stream.of(
                    ExportCommand.COMMAND_WORD,
                    UnstageCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Set<String> IMPORT_COMMANDS = Stream
            .concat(Stream.of(
                    ImportCommand.COMMAND_WORD,
                    LoadCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Set<String> PREFERENCES_COMMANDS = new HashSet<>(GENERAL_COMMANDS);

    public static final String X_COORDINATE_PROMPT = "0.2\n0.4\n0.6\n0.8";
    public static final String X_COORDINATE_AUTO_COMPLETION = "0.2";
    public static final String Y_COORDINATE_PROMPT = "0.2\n0.4\n0.6\n0.8";
    public static final String Y_COORDINATE_AUTO_COMPLETION = "0.2";

    public static final Set<String> STYLES = Set.of("plain", "bold", "italic");
    public static final Set<String> SIZES = Set.of("1", "2", "3", "4", "5", "6");

    public static final WemePrompter MEME_PROMPTER = new MemePrompter();
    public static final WemePrompter TEMPLATE_PROMPTER = new TemplatePrompter();
    public static final WemePrompter CREATE_PROMPTER = new CreatePrompter();
    public static final WemePrompter STATISTICS_PROMPTER = new StatisticsPrompter();
    public static final WemePrompter EXPORT_PROMPTER = new ExportPrompter();
    public static final WemePrompter IMPORT_PROMPTER = new ImportPrompter();
    public static final WemePrompter PREFERENCE_PROMPTER = new PreferencePrompter();

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
            return TEMPLATE_PROMPTER;
        case CONTEXT_CREATE:
            return CREATE_PROMPTER;
        case CONTEXT_EXPORT:
            return EXPORT_PROMPTER;
        case CONTEXT_IMPORT:
            return IMPORT_PROMPTER;
        case CONTEXT_STATISTICS:
            return STATISTICS_PROMPTER;
        case CONTEXT_PREFERENCES:
            return PREFERENCE_PROMPTER;
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
        if (argument.isBlank()) {
            return x.compareTo(y);
        }

        if (startsWith(argument, x) && startsWith(argument, y)) {
            return x.length() - y.length();
        }

        if (startsWith(argument, x)) {
            return -1;
        }

        if (startsWith(argument, y)) {
            return 1;
        }

        LevenshteinDistance ld = new LevenshteinDistance();
        return ld.apply(x, argument) - ld.apply(y, argument);
    }

    /**
     * Sort given set of strings by similarity to the argument.
     * @return Stream containing the sorted records.
     */
    public static Stream<String> sortStringsBySimilarity(Set<String> strings, String argument) {
        return strings
                .stream()
                .sorted((a, b) -> compareStrings(a.toLowerCase(), b.toLowerCase(), argument.toLowerCase()));
    }

    /**
     * Find most similar strings for the argument, limited by {@code MAX_RESULT_DISPLAY}.
     * @return String containing the most similar records separated by line break.
     */
    public static String findSimilarStrings(Set<String> strings, String argument) {
        return sortStringsBySimilarity(strings, argument)
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse("");
    }


    /**
     * Find the most similar string to the argument.
     */
    public static String findMostSimilarString(Set<String> strings, String argument) {
        return sortStringsBySimilarity(strings, argument).findFirst().orElse("");
    }

    /**
     * Find most similar command words to the input command word.
     * @return CommandPrompt containing the most similar command words
     */
    public static CommandPrompt promptCommandWord(Set<String> commandWords, String commandWord) {
        String similarCommands = findSimilarStrings(commandWords, commandWord);
        String mostSimilarCommand = findMostSimilarString(commandWords, commandWord);
        return new CommandPrompt(similarCommands, mostSimilarCommand);
    }

    /**
     * Find most similar arguments from records for the last argument in user input.
     * @return CommandPrompt containing the most similar arguments
     */
    public static CommandPrompt promptSimilarArguments(
            Model model, String inputWithoutLastArgument, LastArgument lastArgument) throws PromptException {
        String lastArgumentValue = lastArgument.getArgument();
        switch (lastArgument.getPrefix().toString()) {
        case PREFIX_FILEPATH_STRING:
            Set<String> pathRecords = model.getPathRecords();
            return new CommandPrompt(findSimilarStrings(pathRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(pathRecords, lastArgumentValue));

        case PREFIX_DESCRIPTION_STRING:
            Set<String> descriptionRecords = model.getDescriptionRecords();
            return new CommandPrompt(findSimilarStrings(descriptionRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(descriptionRecords, lastArgumentValue));

        case PREFIX_TAG_STRING:
            Set<String> tagRecords = model.getTagRecords();
            return new CommandPrompt(findSimilarStrings(tagRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(tagRecords, lastArgumentValue));

        case PREFIX_NAME_STRING:
            Set<String> nameRecords = model.getNameRecords();
            return new CommandPrompt(findSimilarStrings(nameRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(nameRecords, lastArgumentValue));

        case PREFIX_X_COORDINATE_STRING:
            return new CommandPrompt(X_COORDINATE_PROMPT, inputWithoutLastArgument + X_COORDINATE_AUTO_COMPLETION);

        case PREFIX_Y_COORDINATE_STRING:
            return new CommandPrompt(Y_COORDINATE_PROMPT, inputWithoutLastArgument + Y_COORDINATE_AUTO_COMPLETION);

        case PREFIX_COLOR_STRING:
            Set<String> colorRecords = model.getColorRecords();
            return new CommandPrompt(findSimilarStrings(colorRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(colorRecords, lastArgumentValue));

        case PREFIX_STYLE_STRING:
            return new CommandPrompt(findSimilarStrings(STYLES, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(STYLES, lastArgumentValue));

        case PREFIX_SIZE_STRING:
            return new CommandPrompt(findSimilarStrings(SIZES, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(SIZES, lastArgumentValue));

        default:
            throw new PromptException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
}
