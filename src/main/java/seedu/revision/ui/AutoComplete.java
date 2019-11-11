package seedu.revision.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import seedu.revision.logic.commands.main.HistoryCommand;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.commands.main.StartCommand;
import seedu.revision.logic.commands.main.StatsCommand;

/**
 * This class is a TextField which implements an "autocomplete" functionality.
 * Entries based on Command_Usage.
 * @@author ShaunNgTX
 */
public class AutoComplete extends TextField {
    /** Entries based on Command_Usage. */
    private static final SortedSet<String> entries = new TreeSet<>();
    private static final SortedSet<String> entriesWithFixedCommand = new TreeSet<>();
    /** The popup of Auto-Complete list. */
    private ContextMenu popUpEntries;
    private boolean isFixedCommand;

    /**
     * Constructor for autocomplete.
     */
    public AutoComplete() {
        super();
        this.setEntries();
        this.setEntriesWithFixedCommand();
        this.isFixedCommand = false;
        popUpEntries = new ContextMenu();
        textProperty().addListener((observableValue, s, s2) -> {
            if (getText().length() == 0) {
                popUpEntries.hide();
            } else {
                for (String subItems : entriesWithFixedCommand) {
                    if (isContain(subItems, getText())) {
                        isFixedCommand = true;
                    }
                }
                if (isFixedCommand) {
                    popUpEntries.hide();
                } else {
                    popUpEntries.hide();
                    LinkedList<String> searchResult = new LinkedList<>();
                    searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                    populatePopup(searchResult);
                    if (!popUpEntries.isShowing()) {
                        popUpEntries.show(AutoComplete.this, Side.BOTTOM, 20, 0);
                    }
                }
            }
            isFixedCommand = false;
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) ->
                popUpEntries.hide());
    }

    /**
     * Get the existing set of autocomplete entries.
     * Basically the respective command words.
     */
    private void setEntries() {
        entries.add(AddCommand.COMPLETE_COMMAND);
        entries.add(ClearCommand.COMMAND_WORD);
        entries.add(DeleteCommand.COMMAND_WORD);
        entries.add(EditCommand.COMMAND_WORD);
        entries.add(ExitCommand.COMMAND_WORD);
        entries.add(FindCommand.COMMAND_WORD);
        entries.add(ListCommand.COMMAND_WORD);
        entries.add(ListCommand.COMPLETE_COMMAND_DIFF);
        entries.add(ListCommand.COMPLETE_COMMAND_CAT);
        entries.add(HelpCommand.COMMAND_WORD);
        entries.add(RestoreCommand.COMMAND_WORD);
        entries.add(StartCommand.COMMAND_AUTOCOMPLETE);
        entries.add(StartCommand.COMMAND_AUTOCOMPLETE_NORMAL);
        entries.add(StartCommand.COMMAND_AUTOCOMPLETE_ARCADE);
        entries.add(StartCommand.COMMAND_AUTOCOMPLETE_CUSTOM);
        entries.add(HistoryCommand.COMMAND_WORD);
        entries.add(StatsCommand.COMMAND_WORD);
    }
    /**
     * Set another entries to remove the double enter problem.
     */
    private void setEntriesWithFixedCommand() {
        entriesWithFixedCommand.add(ClearCommand.COMMAND_WORD);
        entriesWithFixedCommand.add(ExitCommand.COMMAND_WORD);
        entriesWithFixedCommand.add(ListCommand.COMMAND_WORD);
        entriesWithFixedCommand.add(HelpCommand.COMMAND_WORD);
        entriesWithFixedCommand.add(RestoreCommand.COMMAND_WORD);
        entriesWithFixedCommand.add(HistoryCommand.COMMAND_WORD);
    }
    /**
     * Populate the entry set with the given search results.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        // Most probably won't exceed 10 but just in case.
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                setText(result);
                popUpEntries.hide();
            });
            menuItems.add(item);
        }
        popUpEntries.getItems().clear();
        popUpEntries.getItems().addAll(menuItems);

    }

    /**
     *
     * @param source String that you comparing to.
     * @param subItem String that you using to compare.
     * @return True if subItem is in source, false otherwise.
     */
    private static boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }


}
