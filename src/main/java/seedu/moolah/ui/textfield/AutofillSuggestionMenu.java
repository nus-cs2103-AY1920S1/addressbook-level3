package seedu.moolah.ui.textfield;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import javafx.util.Pair;
import seedu.moolah.logic.parser.Prefix;

/**
 * Context Menu to show suggestions to replace text in a Text Area. Cycle through suggestions using TAB key and press
 * ENTER to finalize choice.
 */
public class AutofillSuggestionMenu extends ContextMenu {

    private static final Color MATCHING_TEXT_COLOUR = Color.ORANGE;
    private static final Color COMPLETION_TEXT_COLOUR = Color.WHITE;
    private static final Color OPTIONAL_LABEL_BACKGROUND_COLOUR = Color.ORANGE;
    private static final Color REQUIRED_LABEL_BACKGROUND_COLOUR = Color.FIREBRICK;
    private static final Color PREFIX_TEXT_COLOUR = Color.WHITE;

    private StringProperty currentCommand;
    private FilteredList<AutofillSupportedCommand> autofillSupportedCommands;
    private FilteredList<String> commandSuggestions;
    private ObservableList<AutofillSupportedCommand> autofillSupportedCommandList;
    private ObservableList<String> supportedCommandWords;
    private CommandTextField textInputControl;
    private SimpleStringProperty currentMatchingText;
    private SimpleBooleanProperty enabled;

    /**
     * Constructor for the {@code AutofillSuggestionMenu}.
     *
     * @param textArea           The textInputControl which this autofill menu is bound to.
     * @param currentCommandWord The 'current matching command word' of the {@code textInputControl}.
     */
    public AutofillSuggestionMenu(CommandTextField textArea, StringProperty currentCommandWord) {
        super();
        this.textInputControl = textArea;

        currentCommand = new SimpleStringProperty("");
        currentCommandWord.addListener((observableValue, s, t1) -> {
            currentCommand.setValue(t1);
        });
        currentMatchingText = new SimpleStringProperty("");
        enabled = new SimpleBooleanProperty(false);

        supportedCommandWords = FXCollections.observableArrayList();
        autofillSupportedCommandList = FXCollections.observableArrayList();
        autofillSupportedCommands = new FilteredList<>(autofillSupportedCommandList);
        commandSuggestions = new FilteredList<>(supportedCommandWords);

        textArea.textProperty().addListener((a, b, text) -> {
            currentMatchingText.setValue(text.stripLeading());

            autofillSupportedCommands.setPredicate(supportedInput -> {
                Supplier<Boolean> bool = () -> supportedInput.getCommand().equals(currentCommand.get());
                return bool.get();
            });

            commandSuggestions.setPredicate(x -> {
                if (currentMatchingText.get().isBlank()) {
                    return true;
                } else {
                    return x.startsWith(currentMatchingText.get());
                }
            });

            if (enabled.get()) {
                hide();
                showSuggestions();
            }
        });

        addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.RIGHT)) {
                hide();
            } else if (keyEvent.getCode().equals(KeyCode.TAB)) {
                enabled.setValue(false);
                hide();
            }
        });

        setOnAction(this::appendChosenCompletion);
    }

    /**
     * Add support for a command with prefixes to be autocompleted and highlighted.
     *
     * @param command          The command word
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

    void toggle() {
        enabled.setValue(!enabled.get());
    }

    BooleanProperty enabledProperty() {
        return enabled;
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
        textInputControl.appendText(completion);
        textInputControl.moveTo(textInputControl.getLength());
    }

    @Override
    public void show(Node anchor, Side side, double dx, double dy) {
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(anchor, side, dx, dy);
    }

    @Override
    public void show(Node anchor, double screenX, double screenY) {
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(anchor, screenX, screenY);
    }

    @Override
    public void show(Window owner) {
        populateList(this, autofillSupportedCommands, commandSuggestions, currentMatchingText.get());
        super.show(owner);
    }

    @Override
    public void show(Window ownerWindow, double anchorX, double anchorY) {
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
    void populateList(ContextMenu m, FilteredList<AutofillSupportedCommand> matchingSuggestions,
                      FilteredList<String> commandSuggestion, String match) {
        m.getItems().clear();
        if (!commandSuggestion.isEmpty()) {
            for (String suggestion : commandSuggestion.sorted(String::compareTo)) {
                String completion = suggestion.replaceFirst(match, "");
                TextFlow graphic = match.isBlank()
                        ? commandWordGraphic("", " ", completion)
                        : commandWordGraphic("", match, completion);
                MenuItem item = new MenuItem();
                item.setId(completion);
                item.setGraphic(graphic);
                m.getItems().add(item);
            }
        } else if (!currentCommand.get().isEmpty()) {
            AutofillSupportedCommand c = matchingSuggestions.get(0);
            Pair<List<Prefix>, List<Prefix>> missing = c.getMissingPrefixes(match);
            for (Prefix p : missing.getKey()) {
                TextFlow graphic = requiredPrefixGraphic(p);
                MenuItem item = new MenuItem();
                if (match.stripTrailing().length() < match.length()) {
                    item.setId(p.getPrefix());
                    // else add a white space in order for it to be properly parsed as a prefix
                } else {
                    item.setId(" " + p.getPrefix());
                }
                item.setGraphic(graphic);
                m.getItems().add(item);
            }
            if (missing.getKey().size() > 0) {
                String all = missing.getKey().stream().map(Object::toString).collect(Collectors.joining(" "));
                MenuItem allPre = new MenuItem();
                allPre.setId(all);
                allPre.setGraphic(requiredPrefixGraphic(missing.getKey()));
                m.getItems().add(allPre);
            }
            if (missing.getKey().size() > 0 && missing.getValue().size() > 0) {
                m.getItems().add(new SeparatorMenuItem());
            }
            for (Prefix p : missing.getValue()) {
                TextFlow graphic = optionalPrefixGraphic(p);
                MenuItem item = new MenuItem();
                // if ends with space can add prefix
                if (match.stripTrailing().length() < match.length()) {
                    item.setId(p.getPrefix());
                    // else add a white space in order for it to be properly parsed as a prefix
                } else {
                    item.setId(" " + p.getPrefix());
                }
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
    private TextFlow commandWordGraphic(String start, String match, String after) {
        Text completionTextBeforeMatch = new Text(start);
        completionTextBeforeMatch.setFill(COMPLETION_TEXT_COLOUR);
        Text matchingText = new Text(match);
        matchingText.setFill(MATCHING_TEXT_COLOUR);
        Text completionTextAfterMatch = new Text(after);
        completionTextAfterMatch.setFill(COMPLETION_TEXT_COLOUR);
        return new TextFlow(completionTextBeforeMatch, matchingText, completionTextAfterMatch);
    }

    /**
     * Creates TextFlow used for AutoFillMenu graphics for required prefixs.
     *
     * @param p The prefix to generate a graphic for.
     * @return The graphic {@code TextFlow}
     */
    private TextFlow requiredPrefixGraphic(Prefix p) {
        TextFlow graphic = new TextFlow();
        Label req = new Label("MISSING");
        req.setTextFill(PREFIX_TEXT_COLOUR);
        req.setBackground(new Background(
                new BackgroundFill(REQUIRED_LABEL_BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        Text prefix = new Text(" " + p.getPrefix() + "<" + p.getDescriptionOfArgument() + ">");
        prefix.setFill(PREFIX_TEXT_COLOUR);
        graphic.getChildren().addAll(req, prefix);
        return graphic;
    }

    /**
     * Creates TextFlow used for AutoFillMenu graphics for list of required prefixs.
     *
     * @param p The prefix to generate a graphic for.
     * @return The graphic {@code TextFlow}
     */
    private TextFlow requiredPrefixGraphic(List<Prefix> p) {
        TextFlow graphic = new TextFlow();
        Label req = new Label("ALL MISSING");
        req.setTextFill(PREFIX_TEXT_COLOUR);
        req.setBackground(new Background(
                new BackgroundFill(REQUIRED_LABEL_BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        graphic.getChildren().addAll(req);
        return graphic;
    }

    /**
     * Creates graphic for optional prefixes in the drop down menu.
     *
     * @param p The optional prefix
     * @return The graphic {@code TextFlow}
     */
    private TextFlow optionalPrefixGraphic(Prefix p) {
        TextFlow graphic = new TextFlow();
        Label req = new Label("OPTIONAL");
        req.setBackground(new Background(
                new BackgroundFill(OPTIONAL_LABEL_BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        Text prefix = new Text(" " + p.getPrefix() + "<" + p.getDescriptionOfArgument() + ">");
        prefix.setFill(PREFIX_TEXT_COLOUR);
        graphic.getChildren().addAll(req, prefix);
        return graphic;
    }

}
