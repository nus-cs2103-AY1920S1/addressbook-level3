package seedu.address.ui.textfield;

import java.util.List;
import java.util.function.Supplier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import seedu.address.logic.parser.Prefix;

/**
 * Context Menu to show suggestions to replace text in a Text Area. Cycle through suggestions using TAB key and press
 * ENTER to finalize choice.
 */
public class AutofillSuggestionMenu extends ContextMenu {

    private StringProperty currentCommand;
    private FilteredList<AutofillSupportedCommand> autofillSupportedCommands;
    private FilteredList<String> commandSuggestions;

    private ObservableList<AutofillSupportedCommand> autofillSupportedCommandList;
    private ObservableList<String> supportedCommandWords;

    private TextInputControl textInputControl;
    private SimpleStringProperty currentMatchingText;

    private Color matchColour;
    private Color completionColour;

    /**
     * Constructor for the {@code AutofillSuggestionMenu}.
     * @param textInputControl The textInputControl which this autofill menu is bound to.
     * @param currentCommandWordStringProperty The 'current matching command word' of the {@code textInputControl}.
     */
    public AutofillSuggestionMenu(TextInputControl textInputControl, StringProperty currentCommandWordStringProperty) {
        super();
        this.textInputControl = textInputControl;

        currentCommand = new SimpleStringProperty("");
        currentCommandWordStringProperty.addListener((observableValue, s, t1) -> {
            currentCommand.setValue(t1);
        });
        currentMatchingText = new SimpleStringProperty();

        supportedCommandWords = FXCollections.observableArrayList();
        autofillSupportedCommandList = FXCollections.observableArrayList();
        autofillSupportedCommands = new FilteredList<>(autofillSupportedCommandList);
        commandSuggestions = new FilteredList<>(supportedCommandWords);

        matchColour = Color.ORANGE;
        completionColour = Color.WHITE;

        textInputControl.textProperty().addListener((a, b, text) -> {
            currentMatchingText.setValue(text.trim());

            autofillSupportedCommands.setPredicate(supportedInput -> {
                Supplier<Boolean> bool = () -> supportedInput.getCommand().equals(currentCommand.get());
                return bool.get();
            });

            String tail = text.stripLeading();
            commandSuggestions.setPredicate(x -> x.startsWith(tail));

            if (currentMatchingText.get().length() > 0) {
                showSuggestions();
                textInputControl.requestFocus();
            } else {
                hide();
            }
        });

        addEventFilter(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getCode().isArrowKey()) {
                hide();
            }
        });

        setOnAction(this::appendChosenCompletion);
    }

     void addCommand(String command, List<Prefix> requiredPrefixes, List<Prefix> optionalPrefixes) {
        supportedCommandWords.removeIf(commandName -> commandName.equals(command));
        supportedCommandWords.add(command);

        autofillSupportedCommandList
                .removeIf(commandName -> commandName.getCommand().equals(command));
        autofillSupportedCommandList.add(new AutofillSupportedCommand(command, requiredPrefixes, optionalPrefixes));
    }

     void removeCommand(String command) {
        supportedCommandWords.removeIf(commandName -> commandName.equals(command));
        autofillSupportedCommandList.removeIf(supportedInput -> supportedInput.getCommand().equals(command));
    }

    /**
     * Show the context menu.
     */
    private void showSuggestions() {
        if (autofillSupportedCommands.size() > 0 || commandSuggestions.size() > 0) {
            this.show(textInputControl, Side.LEFT, 0,20);
        } else {
            hide();
        }
    }

    /**
     * Replaces the text of the text area with the targeted suggestion.
     * @param event The event which targets the menu item containing the suggestion.
     */
    private void appendChosenCompletion(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();

        String completion = menuItem.getId();
        for (Character c : completion.toCharArray()) {
            textInputControl.insertText(textInputControl.getLength(), c.toString());
        }
    }

    @Override
    public void show(Node anchor, Side side, double dx, double dy) {
        if (currentMatchingText.get().isEmpty()) {
            return;
        }
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(anchor, side, dx, dy);
    }


    // disable other show methods
    @Override
    protected void show() {
        if (currentMatchingText.get().isEmpty()) {
            return;
        }
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show();
    }

    @Override
    public void show(Window owner) {
        if (currentMatchingText.get().isEmpty()) {
            return;
        }
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(owner);
    }

    @Override
    public void show(Window ownerWindow, double anchorX, double anchorY) {
        if (currentMatchingText.get().isEmpty()) {
            return;
        }
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(ownerWindow, anchorX, anchorY);
    }

    /**
     * Populates the context menu with suggestions.
     * @param m The context menu.
     * @param matchingSuggestions The list of suggestions.
     * @param match The matching text.
     */
    public void populateList(ContextMenu m, FilteredList<AutofillSupportedCommand> matchingSuggestions,
                             FilteredList<String> commandSuggestion, String match) {
        m.getItems().clear();
        if (currentCommand.length().get() > 0) {
            AutofillSupportedCommand c = matchingSuggestions.get(0);
            for (Prefix p : c.getMissingPrefixes(match)) {
                TextFlow graphic = getGraphic("", match.replaceAll("<[^>]+>", ""), " " + p.getPrefix() + "<" + p.getDescriptionOfArgument() + ">");
                MenuItem item = new MenuItem();
                if (match.replaceAll("<[^>]+>", "").stripTrailing().length() < match.length()) {
                    item.setId(p.getPrefix());
                } else {
                    item.setId(" " + p.getPrefix());
                }
                item.setGraphic(graphic);
                m.getItems().add(item);
            }

//            Optional<String[]> textForCompletionGraphicOptional = c.completion(match, "\n");
//            if (!textForCompletionGraphicOptional.isPresent()) {
//                return;
//            }
//            String textForCompletion = textForCompletionGraphicOptional.get()[0];
//            String textForGraphicRequired = textForCompletionGraphicOptional.get()[1];
//            String textForGraphicOptional = textForCompletionGraphicOptional.get()[2];
//
//            TextFlow graphic = getGraphic(
//                    match + "\n",
//                    textForGraphicRequired + "\n",
//                    textForGraphicOptional);
//
//            MenuItem item = new MenuItem();
//            item.setId(textForCompletion);
//            item.setGraphic(graphic);
//            m.getItems().add(item);

        } else {
            for (String suggestion : commandSuggestion.sorted((s1, s2) -> s1.length() - s2.length())) {
                String completion = suggestion.replaceFirst(match, "");
                TextFlow graphic = getGraphic("", match, completion);
                MenuItem item = new MenuItem();
                item.setId(completion.replaceAll("<[^<]*>", ""));
                item.setGraphic(graphic);
                m.getItems().add(item);
            }

        }
    }

    /**
     * Creates a TextFlow to represent the menu item being displayed with the matching text in the suggestion highlighted.
     * @param start The text before the match.
     * @param match The matching text.
     * @param after The text after the match.
     * @return The TextFlow used for the menu item's graphic.
     */
    public TextFlow getGraphic(String start, String match, String after) {
        Text completionTextBeforeMatch = new Text(start);
        completionTextBeforeMatch.setFill(completionColour);
        Text matchingText = new Text(match);
        matchingText.setFill(matchColour);
        Text completionTextAfterMatch = new Text(after);
        completionTextAfterMatch.setFill(completionColour);
        TextFlow graphic = new TextFlow(completionTextBeforeMatch, matchingText, completionTextAfterMatch);
        return graphic;
    }

}
