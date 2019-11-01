package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.ListChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UnblockCurrentSemesterCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromAllCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.SortStudyPlansByPriorityTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.TagStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.commands.storage.DeleteStudyPlanCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.RevertCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.verification.ClearInvalidModsCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A text field that can handle autocompletion.
 */
public class Autocomplete extends TextField {
    public static final int MAX_ENTRIES = 10;
    private SortedSet<String> commandKeywords;
    private SortedSet<String> argumentKeywords;
    private UniqueTagList tags;
    private ContextMenu keywordMenu;
    private ReadOnlyModulePlanner modulePlanner;

    /**
     * Constructs a textfield that can handle autocompletion.
     */
    public Autocomplete(ReadOnlyModulePlanner modulePlanner) {
        super();
        this.modulePlanner = modulePlanner;
        tags = modulePlanner.getActiveTags();
        tags.asUnmodifiableObservableList().addListener((ListChangeListener<Tag>) change
            -> generateArgumentKeywords());
        generateCommandKeywords();
        generateArgumentKeywords();
        keywordMenu = new ContextMenu();
        focusedProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
        textProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
    }

    /**
     * Populate the entry set with the given search results.
     *
     * @param searchResult The list of matching strings.
     */
    private void populateMenu(List<String> searchResult) {
        ArrayList<CustomMenuItem> menuItems = new ArrayList<>();
        int noOfEntries = Math.min(searchResult.size(), MAX_ENTRIES);
        for (int i = 0; i < noOfEntries; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(unused -> {
                setAutocompleteText(result);
                keywordMenu.hide();
            });
            menuItems.add(item);
        }
        keywordMenu.getItems().clear();
        keywordMenu.getItems().addAll(menuItems);
    }

    /**
     * Handles the autocomplete process.
     */
    public void handleAutocomplete() {
        if (getText().length() == 0) {
            keywordMenu.hide();
        } else {
            String input = getText();
            input = input.trim();
            String[] inputArray = input.split(" ");
            if (inputArray.length == 1) {
                completeInput(inputArray[0], commandKeywords, " ");
            } else if (inputArray[0].equals(HelpCommand.COMMAND_WORD)) {
                // The Help command uses commands as arguments.
                completeInput(inputArray[inputArray.length - 1], commandKeywords, HelpCommand.COMMAND_WORD + " ");
            } else {
                StringBuilder finalText = new StringBuilder();
                for (int i = 0; i < inputArray.length - 1; i++) {
                    finalText.append(inputArray[i]);
                    finalText.append(" ");
                }
                completeInput(inputArray[inputArray.length - 1], argumentKeywords, finalText.toString());
            }
        }
    }

    /**
     * Completes the input in the textfield.
     *
     * @param input       The user's input to be autocompleted.
     * @param keywords    The sorted set of keywords for autocompletion.
     * @param currentText The current text that has been completed.
     */
    private void completeInput(String input, SortedSet<String> keywords, String currentText) {
        ArrayList<String> searchResult = new ArrayList<>(keywords.subSet(input,
                input + Character.MAX_VALUE));
        if (searchResult.size() == 1) {
            setAutocompleteText(searchResult.get(0));
        } else if (searchResult.size() > 1) {
            populateMenu(searchResult);
            if (!keywordMenu.isShowing()) {
                keywordMenu.show(Autocomplete.this, Side.BOTTOM, 15, 0);
            }
            keywordMenu.getSkin().getNode().lookup(".menu-item").requestFocus();
        } else {
            keywordMenu.hide();
        }
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
        commandKeywords.add(RemoveTagFromAllCommand.COMMAND_WORD);
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
        commandKeywords.add(CheckCommand.COMMAND_WORD);
        commandKeywords.add(ClearInvalidModsCommand.COMMAND_WORD);
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

    private void setAutocompleteText(String s) {
        int lastIndex = getText().lastIndexOf(" ");
        String finalText;
        if (lastIndex < 0) {
            finalText = s;
        } else {
            finalText = getText().substring(0, lastIndex)
                    + " " + s;
        }
        setText(finalText);
        positionCaret(finalText.length());
    }
}
