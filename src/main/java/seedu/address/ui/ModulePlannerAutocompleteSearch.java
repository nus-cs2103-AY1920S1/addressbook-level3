package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.ListChangeListener;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UnblockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveAllTagsCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.ShowCoreCommand;
import seedu.address.logic.commands.datamanagement.ShowFocusCommand;
import seedu.address.logic.commands.datamanagement.SortStudyPlansByPriorityTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.TagStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.ChangeThemeCommand;
import seedu.address.logic.commands.gui.CollapseAllCommand;
import seedu.address.logic.commands.gui.CollapseCommand;
import seedu.address.logic.commands.gui.ExpandAllCommand;
import seedu.address.logic.commands.gui.ExpandCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.AddSemesterCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DefaultStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.commands.storage.DeleteSemesterCommand;
import seedu.address.logic.commands.storage.DeleteStudyPlanCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.RevertCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.storage.ViewStudyPlanCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.verification.ClearInvalidModsCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Handles autocompletion searching.
 */
public class ModulePlannerAutocompleteSearch {
    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    private final ReadOnlyModulePlanner modulePlanner;

    private SortedSet<String> commandKeywords;
    private SortedSet<String> argumentKeywords;
    private UniqueTagList tags;

    public ModulePlannerAutocompleteSearch(ReadOnlyModulePlanner modulePlanner) {
        super();
        requireNonNull(modulePlanner);
        this.modulePlanner = modulePlanner;
        tags = modulePlanner.getActiveTags();
        tags.asUnmodifiableObservableList().addListener((ListChangeListener<Tag>) change
            -> generateArgumentKeywords());
        generateCommandKeywords();
        generateArgumentKeywords();
    }

    /**
     * Generates the sorted set of required command keywords.
     */
    private void generateCommandKeywords() {
        commandKeywords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        commandKeywords.add(AddModuleCommand.COMMAND_WORD);
        commandKeywords.add(BlockCurrentSemesterCommand.COMMAND_WORD);
        commandKeywords.add(DeleteModuleCommand.COMMAND_WORD);
        commandKeywords.add(UnblockCurrentSemesterCommand.COMMAND_WORD);
        commandKeywords.add(SetCurrentSemesterCommand.COMMAND_WORD);
        commandKeywords.add(FindModuleCommand.COMMAND_WORD);
        commandKeywords.add(CommitStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(CreateStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(DeleteStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(DeleteCommitCommand.COMMAND_WORD);
        commandKeywords.add(RevertCommitCommand.COMMAND_WORD);
        commandKeywords.add(TagModuleCommand.COMMAND_WORD);
        commandKeywords.add(ViewCommitHistoryCommand.COMMAND_WORD);
        commandKeywords.add(RemoveTagFromModuleCommand.COMMAND_WORD);
        commandKeywords.add(ViewModuleTagsCommand.COMMAND_WORD);
        commandKeywords.add(DeleteTagCommand.COMMAND_WORD);
        commandKeywords.add(RenameTagCommand.COMMAND_WORD);
        commandKeywords.add(ViewAllTagsCommand.COMMAND_WORD);
        commandKeywords.add(ViewDefaultTagsCommand.COMMAND_WORD);
        commandKeywords.add(ViewTaggedCommand.COMMAND_WORD);
        commandKeywords.add(EditTitleCommand.COMMAND_WORD);
        commandKeywords.add(ActivateStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(ListAllStudyPlansCommand.COMMAND_WORD);
        commandKeywords.add(HelpCommand.COMMAND_WORD);
        commandKeywords.add(TagStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(RemoveTagFromStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(SortStudyPlansByPriorityTagCommand.COMMAND_WORD);
        commandKeywords.add(ValidModsCommand.COMMAND_WORD);
        commandKeywords.add(DescriptionCommand.COMMAND_WORD);
        commandKeywords.add(ShowCoreCommand.COMMAND_WORD);
        commandKeywords.add(ShowFocusCommand.COMMAND_WORD);
        commandKeywords.add(CheckCommand.COMMAND_WORD);
        commandKeywords.add(ClearInvalidModsCommand.COMMAND_WORD);
        commandKeywords.add(DeleteSemesterCommand.COMMAND_WORD);
        commandKeywords.add(AddSemesterCommand.COMMAND_WORD);
        commandKeywords.add(ViewCommitCommand.COMMAND_WORD);
        commandKeywords.add(ViewStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(DefaultStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(CollapseCommand.COMMAND_WORD);
        commandKeywords.add(ExpandCommand.COMMAND_WORD);
        commandKeywords.add(CollapseAllCommand.COMMAND_WORD);
        commandKeywords.add(ExpandAllCommand.COMMAND_WORD);
        commandKeywords.add(UndoCommand.COMMAND_WORD);
        commandKeywords.add(RedoCommand.COMMAND_WORD);
        commandKeywords.add(RemoveAllTagsCommand.COMMAND_WORD);
        commandKeywords.add(ExitCommand.COMMAND_WORD);
        commandKeywords.add(ChangeThemeCommand.COMMAND_WORD);
    }

    /**
     * Generates the sorted set of required argument keywords.
     */
    private void generateArgumentKeywords() {
        argumentKeywords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (tags != null) {
            argumentKeywords.addAll(tags.asListOfStrings());
        }
        argumentKeywords.addAll(modulePlanner.getModuleCodes());
        argumentKeywords.addAll(modulePlanner.getActiveListOfSemesterNames());
    }

    /**
     * Resets the argument keywords when there is a change in the active study plan.
     */
    public void handleChangeOfActiveStudyPlan() {
        tags = modulePlanner.getActiveStudyPlan().getModuleTags();
        tags.asUnmodifiableObservableList().addListener((ListChangeListener<Tag>) change
            -> generateArgumentKeywords());
        generateArgumentKeywords();
    }

    /**
     * Gets a list of search results based on an input, according to the Module Planner implementation.
     * @param input User input.
     * @return A list of search results.
     */
    public List<String> getSearchResults(String input) {
        requireNonNull(input);
        // Splits the input into an array including the delimiter " ".
        // Example:
        // "addmod CS1101S CS2030 " becomes ["addmod", " ", "CS1101S", " ", "CS2030", " "]
        // This avoids situations where the user tries to autocomplete on a space.
        String[] inputArray = input.split(String.format(WITH_DELIMITER, " "));
        if (inputArray.length == 1) {
            return performSearch(inputArray[0], commandKeywords);
        } else if (inputArray[0].equals(HelpCommand.COMMAND_WORD)) {
            // The Help command uses commands as arguments.
            return performSearch(inputArray[inputArray.length - 1], commandKeywords);
        } else {
            return performSearch(inputArray[inputArray.length - 1], argumentKeywords);
        }
    }

    /**
     * Performs a search based on the input.
     * @param input One user input word.
     * @param keywords Set of keywords to do searching on.
     * @return A list of matching keywords.
     */
    private List<String> performSearch(String input, SortedSet<String> keywords) {
        requireAllNonNull(input, keywords);
        List<String> searchResults = new ArrayList<>(keywords.subSet(input,
                input + Character.MAX_VALUE));
        return searchResults;
    }
}
