package seedu.guilttrip.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_MISSING_ARGUMENT_FORMAT;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_MISSING_INDEX;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_REDUNDANT_PREAMBLE_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.commons.core.step.Step;
import seedu.guilttrip.commons.util.StringUtil;
import seedu.guilttrip.logic.commands.ClearCommand;
import seedu.guilttrip.logic.commands.ExitCommand;
import seedu.guilttrip.logic.commands.HelpCommand;
import seedu.guilttrip.logic.commands.HistoryCommand;
import seedu.guilttrip.logic.commands.ListBudgetCommand;
import seedu.guilttrip.logic.commands.ListCategoriesCommand;
import seedu.guilttrip.logic.commands.ListCommand;
import seedu.guilttrip.logic.commands.ListWishCommand;
import seedu.guilttrip.logic.commands.PurchaseWishCommand;
import seedu.guilttrip.logic.commands.RedoCommand;
import seedu.guilttrip.logic.commands.UndoCommand;
import seedu.guilttrip.logic.commands.addcommands.AddAutoExpenseCommand;
import seedu.guilttrip.logic.commands.addcommands.AddBudgetCommand;
import seedu.guilttrip.logic.commands.addcommands.AddCategoryCommand;
import seedu.guilttrip.logic.commands.addcommands.AddExpenseCommand;
import seedu.guilttrip.logic.commands.addcommands.AddIncomeCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteAutoExpenseCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteBudgetCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteCategoryCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteExpenseCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteIncomeCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteWishCommand;
import seedu.guilttrip.logic.commands.editcommands.EditAutoExpenseCommand;
import seedu.guilttrip.logic.commands.editcommands.EditBudgetCommand;
import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand;
import seedu.guilttrip.logic.commands.editcommands.EditWishCommand;
import seedu.guilttrip.logic.commands.findcommands.FindAutoExpenseCommand;
import seedu.guilttrip.logic.commands.findcommands.FindBudgetCommand;
import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
import seedu.guilttrip.logic.commands.findcommands.FindIncomeCommand;
import seedu.guilttrip.logic.commands.findcommands.FindWishCommand;
import seedu.guilttrip.logic.commands.remindercommands.AddGeneralReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.DeleteReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.ListRemindersCommand;
import seedu.guilttrip.logic.commands.remindercommands.RemoveConditionFromReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.SelectReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.SetReminderCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortAutoExpenseCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortBudgetCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortExpenseCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortIncomeCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortWishCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewBarChartCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewEntryCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewPieChartCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewTableCommand;
import seedu.guilttrip.logic.commands.uicommands.ChangeFontCommand;
import seedu.guilttrip.logic.commands.uicommands.SetDarkThemeCommand;
import seedu.guilttrip.logic.commands.uicommands.SetLightThemeCommand;
import seedu.guilttrip.logic.commands.uicommands.TogglePanelCommand;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.entry.SortSequence;
import seedu.guilttrip.model.entry.SortType;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.ui.util.FontName;
import seedu.guilttrip.ui.util.PanelName;

/**
 * Contains utility methods used for parsing strings in the various Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed. if the specified index is invalid
     * (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code String indexes} into a {@code List<Index}.
     */
    public static List<Index> parseIndexes(String indexes) throws ParseException {
        String[] indexArr = indexes.split(",");
        List<Index> indexList = new ArrayList<>();
        for (String indexString : indexArr) {
            indexList.add(parseIndex(indexString.trim()));
        }
        return indexList;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code List<Index>}.
     */
    public static List<Index> parseIndexes(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);
        final List<Index> indexList = new ArrayList<>();
        for (String indexString : indexes) {
            indexList.add(parseIndex(indexString));
        }
        return indexList;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Description parseDescription(String desc) throws ParseException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
    }

    /**
     * Parses a stringAmt into an Amount.
     *
     * @param stringAmt the amount as a String.
     * @return an Amount.
     */
    public static Amount parseAmount(String stringAmt) {
        requireNonNull(stringAmt);
        return new Amount(stringAmt);
    }

    /**
     * Parses a date in String to Date.
     *
     * @param date the date as a String.
     * @return the specified date as Date.
     */
    public static Date parseDate(String date) {
        requireNonNull(date);
        return new Date(date);
    }

    /**
     * Parses a date in String to Date.
     *
     * @param date the date as a String.
     * @return the specified date as Date which has been parsed in the month format.
     */
    public static Date parseMonth(String date) {
        requireNonNull(date);
        return new Date(date, true);
    }

    /**
     * Parses a time in {@code Optional}
     *
     * @param time the time as a String.
     * @return the specified time as Time.
     */
    public static Date parseTime(Optional<String> time) {
        requireNonNull(time);
        return time.isPresent() ? new Date(time.get()) : Date.now();
    }

    /**
     * Parses step
     *
     * @param step the time as a String.
     * @return the specified time as Time.
     */
    public static Step parseStep(String step) throws ParseException {
        String trimmedStep = step.trim();
        if (!Step.isValidStep(trimmedStep)) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        if (trimmedStep.isEmpty()) {
            return new Step("1");
        } else {
            return new Step(trimmedStep);
        }
    }

    /**
     * Parses a time in String to ArrayList. Period is sorted so that the smaller period is first.
     *
     * @param period the time as a String.
     * @return the specified time as Date.
     */
    public static List<Date> parseStartAndEndPeriod(String period) {
        String[] dateArr = period.split(",");
        List<Date> startAndEnd = Arrays.stream(dateArr).map(dateString -> new Date(dateString.trim(), true))
                .collect(Collectors.toList());
        startAndEnd.sort((start, end) -> start.getDate().compareTo(end.getDate()));
        return startAndEnd;
    }

    /**
     * Parses a string with 2 dates
     *
     * @param dates
     * @return List of length 2.
     */
    public static List<Date> parseStartAndEndDate(String dates) {
        String[] dateArr = dates.split(",");
        List<Date> startAndEnd = Arrays.stream(dateArr).map(dateString -> new Date(dateString.trim()))
                .collect(Collectors.toList());
        return startAndEnd;
    }

    /**
     * Parses a frequency from String to Frequency.
     *
     * @param stringFreq the frequency as a String.
     * @return the specified frequency as Frequency.
     */
    public static Frequency parseFrequency(String stringFreq) {
        requireNonNull(stringFreq);
        return Frequency.parse(stringFreq);
    }

    /**
     * Parses a category from String to CategoryType.
     *
     * @param catType the type of category as a String.
     * @return the type of category as CategoryType.
     */
    public static Category parseCategory(String catName, String catType) {
        requireNonNull(catType);
        return Category.parseCategory(catName, catType);
    }

    /**
     * Parses a categoryType from String to CategoryType.
     *
     * @param catType the type of category as a String.
     * @return the type of category as CategoryType.
     */
    public static CategoryType parseCategoryType(String catType) {
        requireNonNull(catType);
        return CategoryType.parse(catType);
    }

    /**
     * Parses a time in String to ArrayList.
     *
     * @param period the time as a String.
     * @return the specified time as Date.
     */
    public static Period parsePeriods(String period) throws ParseException {
        requireNonNull(period);
        String trimmedPeriod = period.trim();

        long duration;
        char interval;

        duration = Long.parseLong(period.substring(0, period.length() - 1));
        interval = period.charAt(period.length() - 1);

        return new Period(duration, interval);
    }

    /**
     * Parses a type of sorting in String to SortType.
     *
     * @param type the time as a String.
     * @return the specified time as SortType.
     */
    public static SortType parseSortType(String type) throws IllegalArgumentException {
        requireNonNull(type);
        return new SortType(type);
    }

    /**
     * Parses a type of sequencesorting in String to SortSequence.
     *
     * @param sequence the sequence of sorting as a String.
     * @return the specified time as SortSequence.
     */
    public static SortSequence parseSortSequence(String sequence) {
        requireNonNull(sequence);
        return new SortSequence(sequence);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String tags} into a {@code List<Tag>}.
     */
    public static List<Tag> parseTags(String tags) throws ParseException {
        requireNonNull(tags);
        final String[] tagNames = tags.trim().split(",");
        final List<Tag> tagList = new ArrayList<Tag>();
        for (String tagName : tagNames) {
            tagList.add(parseTag(tagName));
        }
        return tagList;
    }

    /**
     * Parses {@code String keywords} into a {@code List<String>}.
     */
    public static List<String> parseKeyWords(String keyWords) throws ParseException {
        requireNonNull(keyWords);
        final List<String> keyWordList = Arrays.asList(keyWords.trim().split(","));
        return keyWordList;
    }

    /**
     * Parses {@code String panelNamee} into a {@code PanelName}.
     */
    public static PanelName parsePanelName(String panelName) throws ParseException {
        requireNonNull(panelName);
        return PanelName.parse(panelName);
    }

    /**
     * Parses {@code String fontName} into a {@code FontName}.
     */
    public static FontName parseFontName(String fontName) throws ParseException {
        requireNonNull(fontName);
        return FontName.parse(fontName);
    }

    /**
     * Throws specific errors if preamble is missing when needed or present when not needed.
     * Throws specific errors if a compulsory prefix is missing.
     *
     * @param messageUsage       MESSAGE_USAGE string for the current Command.
     * @param argMultimap        the parsed arguments as a {@link ArgumentMultimap}
     * @param needPreamble       a boolean stating whether a preamble is needed
     * @param compulsoryPrefixes {@link Prefix} that are necessary for the command.
     * @throws ParseException
     */
    public static void errorIfCompulsoryPrefixMissing(String messageUsage, ArgumentMultimap argMultimap,
                                                      boolean needPreamble, Prefix... compulsoryPrefixes)
            throws ParseException {
        // Missing preamble or redundant preamble
        String preamble = argMultimap.getPreamble();
        if (needPreamble && preamble.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_INDEX + "\n" + messageUsage);
        } else if (!needPreamble && !preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_REDUNDANT_PREAMBLE_FORMAT, preamble) + "\n" + messageUsage);
        }

        // Missing compulsory Prefix
        for (Prefix compulsoryPrefix : compulsoryPrefixes) {
            if (argMultimap.getValue(compulsoryPrefix).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_MISSING_ARGUMENT_FORMAT, compulsoryPrefix.getFullUsage())
                        + "\n" + messageUsage);
            }
        }
    }

    /**
     * Parses boolean value
     */
    public static boolean parseBool(String bool) throws ParseException {
        switch (bool.trim().toLowerCase()) {
        case "true":
            return true;
        case "false":
            return false;
        default:
            throw new ParseException("Please enter a correct boolean value");
        }
    }

    /**
     * @return a set of Command Words.
     */
    public static Set<String> setOfCommandWords() {
        return Set.of(
                AddExpenseCommand.COMMAND_WORD,
                AddIncomeCommand.COMMAND_WORD,
                AddBudgetCommand.COMMAND_WORD,
                AddCategoryCommand.COMMAND_WORD,
                EditExpenseCommand.COMMAND_WORD,
                EditIncomeCommand.COMMAND_WORD,
                EditCategoryCommand.COMMAND_WORD,
                EditWishCommand.COMMAND_WORD,
                EditBudgetCommand.COMMAND_WORD,
                DeleteExpenseCommand.COMMAND_WORD,
                DeleteIncomeCommand.COMMAND_WORD,
                DeleteCategoryCommand.COMMAND_WORD,
                DeleteWishCommand.COMMAND_WORD,
                DeleteBudgetCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                FindExpenseCommand.COMMAND_WORD,
                FindAutoExpenseCommand.COMMAND_WORD,
                FindIncomeCommand.COMMAND_WORD,
                FindWishCommand.COMMAND_WORD,
                FindBudgetCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                ListCategoriesCommand.COMMAND_WORD,
                ListWishCommand.COMMAND_WORD,
                ListBudgetCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                SortExpenseCommand.COMMAND_WORD,
                SortIncomeCommand.COMMAND_WORD,
                SortBudgetCommand.COMMAND_WORD,
                SortAutoExpenseCommand.COMMAND_WORD,
                SortWishCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                AddGeneralReminderCommand.COMMAND_WORD,
                SetReminderCommand.COMMAND_WORD,
                EditReminderCommand.COMMAND_WORD,
                SelectReminderCommand.COMMAND_WORD,
                DeleteReminderCommand.COMMAND_WORD,
                ListRemindersCommand.COMMAND_WORD,
                RemoveConditionFromReminderCommand.COMMAND_WORD,
                AddAutoExpenseCommand.COMMAND_WORD,
                EditAutoExpenseCommand.COMMAND_WORD,
                DeleteAutoExpenseCommand.COMMAND_WORD,
                ViewBarChartCommand.COMMAND_WORD,
                ViewTableCommand.COMMAND_WORD,
                ViewPieChartCommand.COMMAND_WORD,
                ViewEntryCommand.COMMAND_WORD,
                PurchaseWishCommand.COMMAND_WORD,
                TogglePanelCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ChangeFontCommand.COMMAND_WORD,
                SetLightThemeCommand.COMMAND_WORD,
                SetDarkThemeCommand.COMMAND_WORD
        );
    }

    /**
     * @return a map of Command Words to One-liner descriptions.
     */
    public static Map<String, String> mapOfOneLinerDescs() {
        Map<String, String> toReturn = new HashMap<>();
        toReturn.put(AddExpenseCommand.COMMAND_WORD, AddExpenseCommand.ONE_LINER_DESC);
        toReturn.put(AddIncomeCommand.COMMAND_WORD, AddIncomeCommand.ONE_LINER_DESC);
        toReturn.put(AddBudgetCommand.COMMAND_WORD, AddBudgetCommand.ONE_LINER_DESC);
        toReturn.put(AddCategoryCommand.COMMAND_WORD, AddCategoryCommand.ONE_LINER_DESC);
        toReturn.put(EditExpenseCommand.COMMAND_WORD, EditExpenseCommand.ONE_LINER_DESC);
        toReturn.put(EditIncomeCommand.COMMAND_WORD, EditIncomeCommand.ONE_LINER_DESC);
        toReturn.put(EditCategoryCommand.COMMAND_WORD, EditCategoryCommand.ONE_LINER_DESC);
        toReturn.put(EditWishCommand.COMMAND_WORD, EditWishCommand.ONE_LINER_DESC);
        toReturn.put(EditBudgetCommand.COMMAND_WORD, EditBudgetCommand.ONE_LINER_DESC);
        toReturn.put(DeleteExpenseCommand.COMMAND_WORD, DeleteExpenseCommand.ONE_LINER_DESC);
        toReturn.put(DeleteIncomeCommand.COMMAND_WORD, DeleteIncomeCommand.ONE_LINER_DESC);
        toReturn.put(DeleteCategoryCommand.COMMAND_WORD, DeleteCategoryCommand.ONE_LINER_DESC);
        toReturn.put(DeleteWishCommand.COMMAND_WORD, DeleteWishCommand.ONE_LINER_DESC);
        toReturn.put(DeleteBudgetCommand.COMMAND_WORD, DeleteBudgetCommand.ONE_LINER_DESC);
        toReturn.put(ClearCommand.COMMAND_WORD, ClearCommand.ONE_LINER_DESC);
        toReturn.put(FindExpenseCommand.COMMAND_WORD, FindExpenseCommand.ONE_LINER_DESC);
        toReturn.put(FindAutoExpenseCommand.COMMAND_WORD, FindAutoExpenseCommand.ONE_LINER_DESC);
        toReturn.put(FindIncomeCommand.COMMAND_WORD, FindIncomeCommand.ONE_LINER_DESC);
        toReturn.put(FindWishCommand.COMMAND_WORD, FindWishCommand.ONE_LINER_DESC);
        toReturn.put(FindBudgetCommand.COMMAND_WORD, FindBudgetCommand.ONE_LINER_DESC);
        toReturn.put(ListCommand.COMMAND_WORD, ListCommand.ONE_LINER_DESC);
        toReturn.put(ListCategoriesCommand.COMMAND_WORD, ListCategoriesCommand.ONE_LINER_DESC);
        toReturn.put(ListWishCommand.COMMAND_WORD, ListWishCommand.ONE_LINER_DESC);
        toReturn.put(ListBudgetCommand.COMMAND_WORD, ListBudgetCommand.ONE_LINER_DESC);
        toReturn.put(ExitCommand.COMMAND_WORD, ExitCommand.ONE_LINER_DESC);
        toReturn.put(SortExpenseCommand.COMMAND_WORD, SortExpenseCommand.ONE_LINER_DESC);
        toReturn.put(SortIncomeCommand.COMMAND_WORD, SortIncomeCommand.ONE_LINER_DESC);
        toReturn.put(SortBudgetCommand.COMMAND_WORD, SortBudgetCommand.ONE_LINER_DESC);
        toReturn.put(SortAutoExpenseCommand.COMMAND_WORD, SortAutoExpenseCommand.ONE_LINER_DESC);
        toReturn.put(SortWishCommand.COMMAND_WORD, SortWishCommand.ONE_LINER_DESC);
        toReturn.put(HelpCommand.COMMAND_WORD, HelpCommand.ONE_LINER_DESC);
        toReturn.put(AddGeneralReminderCommand.COMMAND_WORD, AddGeneralReminderCommand.ONE_LINER_DESC);
        toReturn.put(SetReminderCommand.COMMAND_WORD, SetReminderCommand.ONE_LINER_DESC);
        toReturn.put(EditReminderCommand.COMMAND_WORD, EditReminderCommand.ONE_LINER_DESC);
        toReturn.put(DeleteReminderCommand.COMMAND_WORD, DeleteReminderCommand.ONE_LINER_DESC);
        toReturn.put(SelectReminderCommand.COMMAND_WORD, SelectReminderCommand.ONE_LINER_DESC);
        toReturn.put(ListRemindersCommand.COMMAND_WORD, ListRemindersCommand.ONE_LINER_DESC);
        toReturn.put(RemoveConditionFromReminderCommand.COMMAND_WORD,
                RemoveConditionFromReminderCommand.ONE_LINER_DESC);
        toReturn.put(AddAutoExpenseCommand.COMMAND_WORD, AddAutoExpenseCommand.ONE_LINER_DESC);
        toReturn.put(EditAutoExpenseCommand.COMMAND_WORD, EditAutoExpenseCommand.ONE_LINER_DESC);
        toReturn.put(DeleteAutoExpenseCommand.COMMAND_WORD, DeleteAutoExpenseCommand.ONE_LINER_DESC);
        toReturn.put(PurchaseWishCommand.COMMAND_WORD, PurchaseWishCommand.ONE_LINER_DESC);
        toReturn.put(ViewBarChartCommand.COMMAND_WORD, ViewBarChartCommand.ONE_LINER_DESC);
        toReturn.put(ViewTableCommand.COMMAND_WORD, ViewTableCommand.ONE_LINER_DESC);
        toReturn.put(ViewPieChartCommand.COMMAND_WORD, ViewPieChartCommand.ONE_LINER_DESC);
        toReturn.put(ViewEntryCommand.COMMAND_WORD, ViewEntryCommand.ONE_LINER_DESC);
        toReturn.put(TogglePanelCommand.COMMAND_WORD, TogglePanelCommand.ONE_LINER_DESC);
        toReturn.put(UndoCommand.COMMAND_WORD, UndoCommand.ONE_LINER_DESC);
        toReturn.put(RedoCommand.COMMAND_WORD, RedoCommand.ONE_LINER_DESC);
        toReturn.put(HistoryCommand.COMMAND_WORD, HistoryCommand.ONE_LINER_DESC);
        toReturn.put(ChangeFontCommand.COMMAND_WORD, ChangeFontCommand.ONE_LINER_DESC);
        toReturn.put(SetLightThemeCommand.COMMAND_WORD, SetLightThemeCommand.ONE_LINER_DESC);
        toReturn.put(SetDarkThemeCommand.COMMAND_WORD, SetDarkThemeCommand.ONE_LINER_DESC);
        return toReturn;
    }

    /**
     * @return a map of Command Words to Message Usages.
     */
    public static Map<String, String> mapOfMessageUsages() {
        Map<String, String> toReturn = new HashMap<>();
        toReturn.put(AddExpenseCommand.COMMAND_WORD, AddExpenseCommand.MESSAGE_USAGE);
        toReturn.put(AddIncomeCommand.COMMAND_WORD, AddIncomeCommand.MESSAGE_USAGE);
        toReturn.put(AddBudgetCommand.COMMAND_WORD, AddBudgetCommand.MESSAGE_USAGE);
        toReturn.put(AddCategoryCommand.COMMAND_WORD, AddCategoryCommand.MESSAGE_USAGE);
        toReturn.put(EditExpenseCommand.COMMAND_WORD, EditExpenseCommand.MESSAGE_USAGE);
        toReturn.put(EditIncomeCommand.COMMAND_WORD, EditIncomeCommand.MESSAGE_USAGE);
        toReturn.put(EditCategoryCommand.COMMAND_WORD, EditCategoryCommand.MESSAGE_USAGE);
        toReturn.put(EditWishCommand.COMMAND_WORD, EditWishCommand.MESSAGE_USAGE);
        toReturn.put(EditBudgetCommand.COMMAND_WORD, EditBudgetCommand.MESSAGE_USAGE);
        toReturn.put(DeleteExpenseCommand.COMMAND_WORD, DeleteExpenseCommand.MESSAGE_USAGE);
        toReturn.put(DeleteIncomeCommand.COMMAND_WORD, DeleteIncomeCommand.MESSAGE_USAGE);
        toReturn.put(DeleteCategoryCommand.COMMAND_WORD, DeleteCategoryCommand.MESSAGE_USAGE);
        toReturn.put(DeleteWishCommand.COMMAND_WORD, DeleteWishCommand.MESSAGE_USAGE);
        toReturn.put(DeleteBudgetCommand.COMMAND_WORD, DeleteBudgetCommand.MESSAGE_USAGE);
        toReturn.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        toReturn.put(FindExpenseCommand.COMMAND_WORD, FindExpenseCommand.MESSAGE_USAGE);
        toReturn.put(FindAutoExpenseCommand.COMMAND_WORD, FindAutoExpenseCommand.MESSAGE_USAGE);
        toReturn.put(FindIncomeCommand.COMMAND_WORD, FindIncomeCommand.MESSAGE_USAGE);
        toReturn.put(FindWishCommand.COMMAND_WORD, FindWishCommand.MESSAGE_USAGE);
        toReturn.put(FindBudgetCommand.COMMAND_WORD, FindBudgetCommand.MESSAGE_USAGE);
        toReturn.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        toReturn.put(ListCategoriesCommand.COMMAND_WORD, ListCategoriesCommand.MESSAGE_USAGE);
        toReturn.put(ListWishCommand.COMMAND_WORD, ListWishCommand.MESSAGE_USAGE);
        toReturn.put(ListBudgetCommand.COMMAND_WORD, ListBudgetCommand.MESSAGE_USAGE);
        toReturn.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        toReturn.put(SortExpenseCommand.COMMAND_WORD, SortExpenseCommand.MESSAGE_USAGE);
        toReturn.put(SortIncomeCommand.COMMAND_WORD, SortIncomeCommand.MESSAGE_USAGE);
        toReturn.put(SortBudgetCommand.COMMAND_WORD, SortBudgetCommand.MESSAGE_USAGE);
        toReturn.put(SortAutoExpenseCommand.COMMAND_WORD, SortAutoExpenseCommand.MESSAGE_USAGE);
        toReturn.put(SortWishCommand.COMMAND_WORD, SortWishCommand.MESSAGE_USAGE);
        toReturn.put(PurchaseWishCommand.COMMAND_WORD, PurchaseWishCommand.MESSAGE_USAGE);
        toReturn.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        toReturn.put(AddGeneralReminderCommand.COMMAND_WORD, AddGeneralReminderCommand.MESSAGE_USAGE);
        toReturn.put(SetReminderCommand.COMMAND_WORD, SetReminderCommand.MESSAGE_USAGE);
        toReturn.put(EditReminderCommand.COMMAND_WORD, EditReminderCommand.MESSAGE_USAGE);
        toReturn.put(DeleteReminderCommand.COMMAND_WORD, DeleteReminderCommand.MESSAGE_USAGE);
        toReturn.put(ListRemindersCommand.COMMAND_WORD, ListRemindersCommand.MESSAGE_USAGE);
        toReturn.put(SelectReminderCommand.COMMAND_WORD, SelectReminderCommand.MESSAGE_USAGE);
        toReturn.put(RemoveConditionFromReminderCommand.COMMAND_WORD, RemoveConditionFromReminderCommand.MESSAGE_USAGE);
        toReturn.put(AddAutoExpenseCommand.COMMAND_WORD, AddAutoExpenseCommand.MESSAGE_USAGE);
        toReturn.put(EditAutoExpenseCommand.COMMAND_WORD, EditAutoExpenseCommand.MESSAGE_USAGE);
        toReturn.put(DeleteAutoExpenseCommand.COMMAND_WORD, DeleteAutoExpenseCommand.MESSAGE_USAGE);
        toReturn.put(ViewBarChartCommand.COMMAND_WORD, ViewBarChartCommand.MESSAGE_USAGE);
        toReturn.put(ViewTableCommand.COMMAND_WORD, ViewTableCommand.MESSAGE_USAGE);
        toReturn.put(ViewPieChartCommand.COMMAND_WORD, ViewPieChartCommand.MESSAGE_USAGE);
        toReturn.put(ViewEntryCommand.COMMAND_WORD, ViewEntryCommand.MESSAGE_USAGE);
        toReturn.put(TogglePanelCommand.COMMAND_WORD, TogglePanelCommand.MESSAGE_USAGE);
        toReturn.put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
        toReturn.put(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE);
        toReturn.put(HistoryCommand.COMMAND_WORD, HistoryCommand.MESSAGE_USAGE);
        toReturn.put(ChangeFontCommand.COMMAND_WORD, ChangeFontCommand.MESSAGE_USAGE);
        toReturn.put(SetLightThemeCommand.COMMAND_WORD, SetLightThemeCommand.MESSAGE_USAGE);
        toReturn.put(SetDarkThemeCommand.COMMAND_WORD, SetDarkThemeCommand.MESSAGE_USAGE);
        return toReturn;
    }
}

