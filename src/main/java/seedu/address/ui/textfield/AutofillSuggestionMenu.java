package seedu.address.ui.textfield;

import static seedu.address.ui.textfield.SyntaxHighlightingSupportedInput.PLACEHOLDER_REGEX;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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

    private static final Color MATCHING_TEXT_COLOUR = Color.ORANGE;
    private static final Color COMPLETION_TEXT_COLOUR = Color.WHITE;
    private static final Color OPTIONAL_LABEL_BACKGROUND_COLOUR = Color.LIGHTGRAY;
    private static final Color REQUIRED_LABEL_BACKGROUND_COLOUR = Color.FIREBRICK;
    private static final Color OPTIONAL_TEXT_COLOUR = Color.BLACK;
    private static final Color REQUIRED_TEXT_COLOUR = Color.WHITE;

    private StringProperty currentCommand;
    private FilteredList<AutofillSupportedCommand> autofillSupportedCommands;
    private FilteredList<String> commandSuggestions;
    private ObservableList<AutofillSupportedCommand> autofillSupportedCommandList;
    private ObservableList<String> supportedCommandWords;
    private TextInputControl textInputControl;
    private SimpleStringProperty currentMatchingText;

    /**
     * Constructor for the {@code AutofillSuggestionMenu}.
     *
     * @param textInputControl                 The textInputControl which this autofill menu is bound to.
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

    /**
     * Add support for a command with prefixes to be autocompleted and highlighted.
     * @param command The command word
     * @param requiredPrefixes The required prefixes
     * @param optionalPrefixes The optional prefixes
     */
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
            this.show(textInputControl, Side.BOTTOM, 0, 0);
        } else {
            hide();
        }
    }

    /**
     * Replaces the text of the text area with the targeted suggestion.
     *
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
     *
     * @param m                   The context menu.
     * @param matchingSuggestions The list of suggestions.
     * @param match               The matching text.
     */
    public void populateList(ContextMenu m, FilteredList<AutofillSupportedCommand> matchingSuggestions,
                             FilteredList<String> commandSuggestion, String match) {
        m.getItems().clear();
        if (currentCommand.length().get() > 0) {
            AutofillSupportedCommand c = matchingSuggestions.get(0);
            List<Prefix>[] missing = c.getMissingPrefixes(match);
            for (Prefix p : missing[0]) {
                TextFlow graphic = requiredPrefixGraphic(p);
                MenuItem item = new MenuItem();
                // if ends with space can add prefix
                if (match.replaceAll(PLACEHOLDER_REGEX, "").stripTrailing().length() < match.length()) {
                    item.setId(p.getPrefix());
                    // else add a white space in order for it to be properly parsed as a prefix
                } else {
                    item.setId(" " + p.getPrefix());
                }
                item.setGraphic(graphic);
                m.getItems().add(item);
            }
            if (missing[0].size() > 0) {
                String all = missing[0].stream().map(Object::toString).collect(Collectors.joining(" "));
                MenuItem allPre = new MenuItem();
                allPre.setId(all);
                allPre.setGraphic(requiredPrefixGraphic(missing[0]));
                m.getItems().add(allPre);
            }
            if (missing[0].size() > 0 && missing[1].size() > 0) {
                m.getItems().add(new SeparatorMenuItem());
            }
            for (Prefix p : missing[1]) {
                TextFlow graphic = optionalPrefixGraphic(p);
                MenuItem item = new MenuItem();
                // if ends with space can add prefix
                if (match.replaceAll(PLACEHOLDER_REGEX, "").stripTrailing().length() < match.length()) {
                    item.setId(p.getPrefix());
                    // else add a white space in order for it to be properly parsed as a prefix
                } else {
                    item.setId(" " + p.getPrefix());
                }
                item.setGraphic(graphic);
                m.getItems().add(item);
            }
        } else {
            for (String suggestion : commandSuggestion.sorted((s1, s2) -> s1.length() - s2.length())) {
                String completion = suggestion.replaceFirst(match, "");
                TextFlow graphic = commandWordGraphic("", match, completion);
                MenuItem item = new MenuItem();
                item.setId(completion.replaceAll(PLACEHOLDER_REGEX, ""));
                item.setGraphic(graphic);
                m.getItems().add(item);
            }

        }
    }

    /**
     * Creates a TextFlow to represent the menu item being displayed with the matching text in the suggestion
     * highlighted.
     *
     * @param start The text before the match.
     * @param match The matching text.
     * @param after The text after the match.
     * @return The TextFlow used for the menu item's graphic.
     */
    public TextFlow commandWordGraphic(String start, String match, String after) {
        Text completionTextBeforeMatch = new Text(start);
        completionTextBeforeMatch.setFill(COMPLETION_TEXT_COLOUR);
        Text matchingText = new Text(match);
        matchingText.setFill(MATCHING_TEXT_COLOUR);
        Text completionTextAfterMatch = new Text(after);
        completionTextAfterMatch.setFill(COMPLETION_TEXT_COLOUR);
        TextFlow graphic = new TextFlow(completionTextBeforeMatch, matchingText, completionTextAfterMatch);
        return graphic;
    }

    /**
     * Creates TextFlow used for AutoFillMenu graphics for required prefixs.
     * @param p The prefix to generate a graphic for.
     * @return The graphic {@code TextFlow}
     */
    private TextFlow requiredPrefixGraphic(Prefix p) {
        TextFlow graphic = new TextFlow();
        graphic.setPadding(Insets.EMPTY);
        Label req = new Label("Missing:");
        req.setTextFill(REQUIRED_TEXT_COLOUR);
        req.setPadding(Insets.EMPTY);
        req.setBackground(new Background(
                new BackgroundFill(REQUIRED_LABEL_BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        Text prefix = new Text(" " + p.getPrefix() + "<" + p.getDescriptionOfArgument() + ">");
        prefix.setFill(REQUIRED_TEXT_COLOUR);
        graphic.getChildren().addAll(req, prefix);
        return graphic;
    }

    /**
     * Creates TextFlow used for AutoFillMenu graphics for list of required prefixs.
     * @param p The prefix to generate a graphic for.
     * @return The graphic {@code TextFlow}
     */
    private TextFlow requiredPrefixGraphic(List<Prefix> p) {
        TextFlow graphic = new TextFlow();
        graphic.setPadding(Insets.EMPTY);
        Label req = new Label("ALL MISSING");
        req.setTextFill(REQUIRED_TEXT_COLOUR);
        req.setPadding(Insets.EMPTY);
        req.setBackground(new Background(
                new BackgroundFill(REQUIRED_LABEL_BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        graphic.getChildren().addAll(req);
        return graphic;
    }


    /**
     * Creates graphic for optional prefixes in the drop down menu.
     * @param p The optional prefix
     * @return The graphic {@code TextFlow}
     */
    private TextFlow optionalPrefixGraphic(Prefix p) {
        TextFlow graphic = new TextFlow();
        graphic.setPadding(Insets.EMPTY);
        Label req = new Label("Optional:");
        req.setPadding(Insets.EMPTY);
        req.setBackground(new Background(
                new BackgroundFill(MATCHING_TEXT_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        Text prefix = new Text(" " + p.getPrefix() + "<" + p.getDescriptionOfArgument() + ">");
        prefix.setFill(REQUIRED_TEXT_COLOUR);
        graphic.getChildren().addAll(req, prefix);
        return graphic;
    }

}
