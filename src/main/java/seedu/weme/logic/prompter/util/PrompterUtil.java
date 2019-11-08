package seedu.weme.logic.prompter.util;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE_STRING;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE_STRING;
import static seedu.weme.model.ModelContext.CONTEXT_CREATE;
import static seedu.weme.model.ModelContext.CONTEXT_EXPORT;
import static seedu.weme.model.ModelContext.CONTEXT_IMPORT;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.model.ModelContext.CONTEXT_PREFERENCES;
import static seedu.weme.model.ModelContext.CONTEXT_STATISTICS;
import static seedu.weme.model.ModelContext.CONTEXT_TEMPLATES;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.text.similarity.LevenshteinDistance;

import seedu.weme.logic.commands.createcommand.AbortCreationCommand;
import seedu.weme.logic.commands.createcommand.CreateCommand;
import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.commands.createcommand.TextDeleteCommand;
import seedu.weme.logic.commands.createcommand.TextEditCommand;
import seedu.weme.logic.commands.createcommand.TextMoveCommand;
import seedu.weme.logic.commands.exportcommand.ExportClearCommand;
import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.commands.generalcommand.ExitCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.commands.importcommand.ImportClearCommand;
import seedu.weme.logic.commands.importcommand.ImportCommand;
import seedu.weme.logic.commands.importcommand.ImportDeleteCommand;
import seedu.weme.logic.commands.importcommand.ImportEditCommand;
import seedu.weme.logic.commands.importcommand.LoadCommand;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeViewCommand;
import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchiveCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchivesCommand;
import seedu.weme.logic.commands.templatecommand.TemplateClearCommand;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.commands.templatecommand.TemplateFindCommand;
import seedu.weme.logic.commands.templatecommand.TemplateListCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUnarchiveCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUseCommand;
import seedu.weme.logic.prompter.contextprompter.CreatePrompter;
import seedu.weme.logic.prompter.contextprompter.ExportPrompter;
import seedu.weme.logic.prompter.contextprompter.ImportPrompter;
import seedu.weme.logic.prompter.contextprompter.MemePrompter;
import seedu.weme.logic.prompter.contextprompter.PreferencePrompter;
import seedu.weme.logic.prompter.contextprompter.StatisticsPrompter;
import seedu.weme.logic.prompter.contextprompter.TemplatePrompter;
import seedu.weme.logic.prompter.contextprompter.ViewPrompter;
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

    public static final Map<String, String> GENERAL_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_DESCRIPTION);
            put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_DESCRIPTION);
            put(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_DESCRIPTION);
            put(TabCommand.COMMAND_WORD, TabCommand.MESSAGE_DESCRIPTION);
            put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_DESCRIPTION);
        }};

    public static final Set<String> MEME_COMMANDS = Stream
            .concat(Stream.of(
                    MemeAddCommand.COMMAND_WORD,
                    MemeArchiveCommand.COMMAND_WORD,
                    MemeArchivesCommand.COMMAND_WORD,
                    MemeClearCommand.COMMAND_WORD,
                    MemeDeleteCommand.COMMAND_WORD,
                    MemeDislikeCommand.COMMAND_WORD,
                    MemeEditCommand.COMMAND_WORD,
                    MemeFindCommand.COMMAND_WORD,
                    MemeLikeCommand.COMMAND_WORD,
                    MemeListCommand.COMMAND_WORD,
                    MemeStageCommand.COMMAND_WORD,
                    MemeUnarchiveCommand.COMMAND_WORD,
                    MemeViewCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Map<String, String> MEME_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(MemeAddCommand.COMMAND_WORD, MemeAddCommand.MESSAGE_DESCRIPTION);
            put(MemeArchiveCommand.COMMAND_WORD, MemeArchiveCommand.MESSAGE_DESCRIPTION);
            put(MemeArchivesCommand.COMMAND_WORD, MemeArchivesCommand.MESSAGE_DESCRIPTION);
            put(MemeClearCommand.COMMAND_WORD, MemeClearCommand.MESSAGE_DESCRIPTION);
            put(MemeDeleteCommand.COMMAND_WORD, MemeDeleteCommand.MESSAGE_DESCRIPTION);
            put(MemeDislikeCommand.COMMAND_WORD, MemeDislikeCommand.MESSAGE_DESCRIPTION);
            put(MemeEditCommand.COMMAND_WORD, MemeEditCommand.MESSAGE_DESCRIPTION);
            put(MemeFindCommand.COMMAND_WORD, MemeFindCommand.MESSAGE_DESCRIPTION);
            put(MemeLikeCommand.COMMAND_WORD, MemeLikeCommand.MESSAGE_DESCRIPTION);
            put(MemeListCommand.COMMAND_WORD, MemeListCommand.MESSAGE_DESCRIPTION);
            put(MemeStageCommand.COMMAND_WORD, MemeStageCommand.MESSAGE_DESCRIPTION);
            put(MemeUnarchiveCommand.COMMAND_WORD, MemeUnarchiveCommand.MESSAGE_DESCRIPTION);
            put(MemeViewCommand.COMMAND_WORD, MemeViewCommand.MESSAGE_DESCRIPTION);
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
        }};

    public static final Set<String> TEMPLATE_COMMANDS = Stream
            .concat(Stream.of(
                    TemplateAddCommand.COMMAND_WORD,
                    TemplateArchiveCommand.COMMAND_WORD,
                    TemplateArchivesCommand.COMMAND_WORD,
                    TemplateClearCommand.COMMAND_WORD,
                    TemplateDeleteCommand.COMMAND_WORD,
                    TemplateEditCommand.COMMAND_WORD,
                    TemplateFindCommand.COMMAND_WORD,
                    TemplateListCommand.COMMAND_WORD,
                    TemplateUnarchiveCommand.COMMAND_WORD,
                    TemplateUseCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Map<String, String> TEMPLATE_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(TemplateAddCommand.COMMAND_WORD, TemplateAddCommand.MESSAGE_DESCRIPTION);
            put(TemplateArchiveCommand.COMMAND_WORD, TemplateArchiveCommand.MESSAGE_DESCRIPTION);
            put(TemplateArchivesCommand.COMMAND_WORD, TemplateArchivesCommand.MESSAGE_DESCRIPTION);
            put(TemplateClearCommand.COMMAND_WORD, TemplateClearCommand.MESSAGE_DESCRIPTION);
            put(TemplateDeleteCommand.COMMAND_WORD, TemplateDeleteCommand.MESSAGE_DESCRIPTION);
            put(TemplateEditCommand.COMMAND_WORD, TemplateEditCommand.MESSAGE_DESCRIPTION);
            put(TemplateFindCommand.COMMAND_WORD, TemplateFindCommand.MESSAGE_DESCRIPTION);
            put(TemplateListCommand.COMMAND_WORD, TemplateListCommand.MESSAGE_DESCRIPTION);
            put(TemplateUnarchiveCommand.COMMAND_WORD, TemplateUnarchiveCommand.MESSAGE_DESCRIPTION);
            put(TemplateUseCommand.COMMAND_WORD, TemplateUseCommand.MESSAGE_DESCRIPTION);
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
        }};

    public static final Set<String> CREATE_COMMANDS = Stream
            .concat(Stream.of(
                    AbortCreationCommand.COMMAND_WORD,
                    CreateCommand.COMMAND_WORD,
                    TextAddCommand.COMMAND_WORD,
                    TextDeleteCommand.COMMAND_WORD,
                    TextEditCommand.COMMAND_WORD,
                    TextMoveCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Map<String, String> CREATE_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(AbortCreationCommand.COMMAND_WORD, AbortCreationCommand.MESSAGE_DESCRIPTION);
            put(CreateCommand.COMMAND_WORD, CreateCommand.MESSAGE_DESCRIPTION);
            put(TextAddCommand.COMMAND_WORD, TextAddCommand.MESSAGE_DESCRIPTION);
            put(TextDeleteCommand.COMMAND_WORD, TextDeleteCommand.MESSAGE_DESCRIPTION);
            put(TextEditCommand.COMMAND_WORD, TextEditCommand.MESSAGE_DESCRIPTION);
            put(TextMoveCommand.COMMAND_WORD, TextMoveCommand.MESSAGE_DESCRIPTION);
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
        }};

    public static final Set<String> STATISTICS_COMMANDS = new HashSet<>(GENERAL_COMMANDS);
    public static final Map<String, String> STATISTICS_COMMANDS_DESCRIPTION_MAP = new HashMap<>(
            GENERAL_COMMANDS_DESCRIPTION_MAP);

    public static final Set<String> EXPORT_COMMANDS = Stream
            .concat(Stream.of(
                    ExportCommand.COMMAND_WORD,
                    UnstageCommand.COMMAND_WORD,
                    ExportClearCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Map<String, String> EXPORT_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(ExportCommand.COMMAND_WORD, ExportCommand.MESSAGE_DESCRIPTION);
            put(UnstageCommand.COMMAND_WORD, UnstageCommand.MESSAGE_DESCRIPTION);
            put(ExportClearCommand.COMMAND_WORD, ExportClearCommand.MESSAGE_DESCRIPTION);
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
        }};

    public static final Set<String> IMPORT_COMMANDS = Stream
            .concat(Stream.of(
                    ImportClearCommand.COMMAND_WORD,
                    ImportCommand.COMMAND_WORD,
                    ImportDeleteCommand.COMMAND_WORD,
                    ImportEditCommand.COMMAND_WORD,
                    LoadCommand.COMMAND_WORD
            ), GENERAL_COMMANDS.stream())
            .collect(Collectors.toSet());

    public static final Map<String, String> IMPORT_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            put(ImportClearCommand.COMMAND_WORD, ImportClearCommand.MESSAGE_DESCRIPTION);
            put(ImportCommand.COMMAND_WORD, ImportCommand.MESSAGE_DESCRIPTION);
            put(ImportDeleteCommand.COMMAND_WORD, ImportDeleteCommand.MESSAGE_DESCRIPTION);
            put(ImportEditCommand.COMMAND_WORD, ImportEditCommand.MESSAGE_DESCRIPTION);
            put(LoadCommand.COMMAND_WORD, LoadCommand.MESSAGE_DESCRIPTION);
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
        }};

    public static final Set<String> PREFERENCES_COMMANDS = new HashSet<>(GENERAL_COMMANDS);
    public static final Map<String, String> PREFERENCES_COMMANDS_DESCRIPTION_MAP = new HashMap<>(
            GENERAL_COMMANDS_DESCRIPTION_MAP);

    public static final Set<String> VIEW_COMMANDS = new HashSet<>() {{
            addAll(GENERAL_COMMANDS);
            remove(UndoCommand.COMMAND_WORD);
            remove(RedoCommand.COMMAND_WORD);
        }};

    public static final Map<String, String> VIEW_COMMANDS_DESCRIPTION_MAP = new HashMap<>() {{
            putAll(GENERAL_COMMANDS_DESCRIPTION_MAP);
            remove(UndoCommand.COMMAND_WORD);
            remove(RedoCommand.COMMAND_WORD);
        }};

    public static final String X_COORDINATE_PROMPT = "0.2\n0.4\n0.6\n0.8";
    public static final String X_COORDINATE_AUTO_COMPLETION = "0.2";
    public static final String Y_COORDINATE_PROMPT = "0.2\n0.4\n0.6\n0.8";
    public static final String Y_COORDINATE_AUTO_COMPLETION = "0.2";

    public static final Set<String> COLORS = Set.of("black", "red", "blue", "yellow", "green", "pink");
    public static final Set<String> STYLES = Set.of("plain", "bold", "italic");
    public static final Set<String> SIZES = Set.of("1", "2", "3", "4", "5", "6");

    public static final WemePrompter MEME_PROMPTER = new MemePrompter();
    public static final WemePrompter TEMPLATE_PROMPTER = new TemplatePrompter();
    public static final WemePrompter CREATE_PROMPTER = new CreatePrompter();
    public static final WemePrompter STATISTICS_PROMPTER = new StatisticsPrompter();
    public static final WemePrompter EXPORT_PROMPTER = new ExportPrompter();
    public static final WemePrompter IMPORT_PROMPTER = new ImportPrompter();
    public static final WemePrompter PREFERENCE_PROMPTER = new PreferencePrompter();
    public static final WemePrompter VIEW_PROMPTER = new ViewPrompter();

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
        case CONTEXT_VIEW:
            return VIEW_PROMPTER;
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
    private static Stream<String> sortStringsBySimilarity(Set<String> strings, String argument) {
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
    public static CommandPrompt promptCommandWord(Set<String> commandWords, String commandWord,
                                                  Map<String, String> commandsDescriptionMap) {
        String similarCommands = sortStringsBySimilarity(commandWords, commandWord)
                .map(command -> commandsDescriptionMap.get(command))
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse("");
        String mostSimilarCommand = findMostSimilarString(commandWords, commandWord);
        return new CommandPrompt(similarCommands, mostSimilarCommand + COMMAND_DELIMITER);
    }

    /**
     * Find most similar arguments from records for the last argument in user input in meme context.
     * @return CommandPrompt containing the most similar arguments
     */
    public static CommandPrompt promptSimilarMemeArguments(
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

        default:
            throw new PromptException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Find most similar arguments from records for the last argument in user input in template context.
     * @return CommandPrompt containing the most similar arguments
     */
    public static CommandPrompt promptSimilarTemplateArguments(
            Model model, String inputWithoutLastArgument, LastArgument lastArgument) throws PromptException {
        String lastArgumentValue = lastArgument.getArgument();
        switch (lastArgument.getPrefix().toString()) {
        case PREFIX_FILEPATH_STRING:
            Set<String> pathRecords = model.getPathRecords();
            return new CommandPrompt(findSimilarStrings(pathRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(pathRecords, lastArgumentValue));

        case PREFIX_NAME_STRING:
            Set<String> nameRecords = model.getNameRecords();
            return new CommandPrompt(findSimilarStrings(nameRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(nameRecords, lastArgumentValue));

        default:
            throw new PromptException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Find most similar arguments from records for the last argument in user input in create context.
     * @return CommandPrompt containing the most similar arguments
     */
    public static CommandPrompt promptSimilarCreateArguments(
            Model model, String inputWithoutLastArgument, LastArgument lastArgument) throws PromptException {
        String lastArgumentValue = lastArgument.getArgument();
        switch (lastArgument.getPrefix().toString()) {
        case PREFIX_X_COORDINATE_STRING:
            return new CommandPrompt(X_COORDINATE_PROMPT, inputWithoutLastArgument + X_COORDINATE_AUTO_COMPLETION);

        case PREFIX_Y_COORDINATE_STRING:
            return new CommandPrompt(Y_COORDINATE_PROMPT, inputWithoutLastArgument + Y_COORDINATE_AUTO_COMPLETION);

        case PREFIX_TEXT_STRING:
            Set<String> textRecords = model.getTextRecords();
            return new CommandPrompt(findSimilarStrings(textRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(textRecords, lastArgumentValue));

        case PREFIX_COLOR_STRING:
            return new CommandPrompt(findSimilarStrings(COLORS, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(COLORS, lastArgumentValue));

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

    /**
     * Find most similar arguments from records for the last argument in user input in export context.
     * @return CommandPrompt containing the most similar arguments
     */
    public static CommandPrompt promptSimilarImportExportArguments(
            Model model, String inputWithoutLastArgument, LastArgument lastArgument) throws PromptException {
        String lastArgumentValue = lastArgument.getArgument();
        switch (lastArgument.getPrefix().toString()) {
        case PREFIX_FILEPATH_STRING:
            Set<String> pathRecords = model.getPathRecords();
            return new CommandPrompt(findSimilarStrings(pathRecords, lastArgumentValue),
                    inputWithoutLastArgument + findMostSimilarString(pathRecords, lastArgumentValue));

        default:
            throw new PromptException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

}
