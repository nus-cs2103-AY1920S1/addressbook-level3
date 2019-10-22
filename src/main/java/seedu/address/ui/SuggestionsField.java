package seedu.address.ui;

import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seedu.address.logic.parser.AddressBookParser;

public class SuggestionsField extends TextField {

    private TreeMap<String, String> commandList;
    private ContextMenu resultBox;

    public SuggestionsField() {
        super();
        commandList = AddressBookParser.getCommandList();
        resultBox = new ContextMenu();
        resultBox.setOpacity(0.7);
        resultBox.hide();

        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String str1, String str2) {
                switch (getText().length()) {
                case 0:
                    resultBox.hide();
                default:
                    findMatchingCommands();
                }
            }
        });
    }

    private void findMatchingCommands() {
        ArrayList<String> matchingCommands = new ArrayList<>();
       Set<String> commandWords = commandList.keySet();
        for (String command : commandWords) {
            if (command.contains(getText()) && command.startsWith(getText())) {
                matchingCommands.add(command);
            }
        }
        if (!matchingCommands.isEmpty()) {
            fillResultBox(matchingCommands);
            if (!resultBox.isShowing()) {
                resultBox.show(SuggestionsField.this, Side.BOTTOM,0, 0);
            }
        } else {
            resultBox.hide();
        }
    }

    private void fillResultBox(ArrayList<String> matchingCommands) {
        Stack<CustomMenuItem> itemsToDisplay = new Stack<>();
        int count = 0;
        while (count < matchingCommands.size()) {
            String commandWord = matchingCommands.get(count);
            Label commandDescription = new Label(
                    commandWord + " - executes a " + commandList.get(commandWord) + " command."
            );
            CustomMenuItem commandMenuItem = new CustomMenuItem(commandDescription, false);
            itemsToDisplay.push(commandMenuItem);
            EventHandler<ActionEvent> onEnter = new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    resultBox.hide();
                    setText(commandWord);
                }
            };

            commandMenuItem.setOnAction(onEnter);

            count++;

        }
        resultBox.getItems().clear();
        resultBox.getItems().addAll(itemsToDisplay);

    }
}