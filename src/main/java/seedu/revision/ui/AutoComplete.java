package seedu.revision.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.commands.main.DeleteCommand;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.main.FindCommand;
import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.commands.main.StartQuizCommand;


/**
 * This class is a TextField which implements an "autocomplete" functionality.
 * Uses a supplied list of entries (questions from stored answerables).
 */
public class AutoComplete extends TextField {
    public static final int MAX_ENTRIES = 10;
    private SortedSet<String> commandKeywords;
    private ContextMenu keywordMenu;

    /**
     * Constructs a textfield that can handle autocompletion.
     */
    public AutoComplete() {
        super();
        generateCommandKeywords();
        keywordMenu = new ContextMenu();
        focusedProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
        textProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
    }

    /**
     * Populate the entry set with the given search results.
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
                int lastIndex = getText().lastIndexOf(" ");
                String finalText;
                if (lastIndex < 0) {
                    finalText = result;
                } else {
                    finalText = getText().substring(0, getText().lastIndexOf(" "))
                            + " " + result;
                }
                setText(finalText);
                positionCaret(finalText.length());
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
            String[] inputArray = input.split(" ");
            completeInput(inputArray[0], commandKeywords);
        }
    }

    /**
     * Completes the input in the textfield.
     * @param input The user's input to be autocompleted.
     * @param keywords The sorted set of keywords for autocompletion.
     */
    private void completeInput(String input, SortedSet<String> keywords) {
        ArrayList<String> searchResult = new ArrayList<>(keywords.subSet(input,
                input + Character.MAX_VALUE));

        if (searchResult.size() >= 1) {
            populateMenu(searchResult);
            if (!keywordMenu.isShowing()) {
                keywordMenu.show(AutoComplete.this, Side.BOTTOM, 20, 0);
            }
            keywordMenu.getSkin().getNode().lookup(".menu-item").requestFocus();
        } else {
            keywordMenu.hide();
        }
    }

    /**
     * Checks if menu is open.
     * @return keywordMenu.
     */
    public boolean isMenuShowing() {
        return keywordMenu.isShowing();
    }

    /**
     * Generates the sorted set of required command keywords.
     */
    private void generateCommandKeywords() {
        commandKeywords = new TreeSet<>();
        commandKeywords.add(AddCommand.COMPLETE_COMMAND);
        commandKeywords.add(ClearCommand.COMMAND_WORD);
        commandKeywords.add(DeleteCommand.COMMAND_WORD);
        commandKeywords.add(EditCommand.COMMAND_WORD);
        commandKeywords.add(ExitCommand.COMMAND_WORD);
        commandKeywords.add(FindCommand.COMMAND_WORD);
        commandKeywords.add(ListCommand.COMMAND_WORD);
        commandKeywords.add(ListCommand.COMPLETE_COMMAND);
        commandKeywords.add(HelpCommand.COMMAND_WORD);
        commandKeywords.add(RestoreCommand.COMMAND_WORD);
        commandKeywords.add(StartQuizCommand.COMMAND_AUTOCOMPLETE);
    }
}
