package seedu.address.ui.CustomTextField;

import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_DOWN;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;
import static org.fxmisc.wellbehaved.event.EventPattern.keyReleased;
import static seedu.address.ui.CustomTextField.SyntaxHighlightingSupportedInput.PLACE_HOLDER_REGEX;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.parser.Prefix;

/**
 * A single line text area utilising RichTextFX to support syntax highlighting of user input.
 * This has some code which is adapted from OverrideBehaviorDemo and JavaKeywordsDemo in RichTextFX.
 */
public class CommandTextField extends Region {

    private static final String PREFIX_STYLE_PREFIX = "prefix";
    private static final String ARGUMENT_STYLE_PREFIX = "arg";
    private static final String COMMAND_WORD_STYLE = "command-word";
    private static final String PLACEHOLDER_STYLE = "placeholder";
    private static final String STRING_STYLE = "string";

    private static InputMap<Event> consumeCopyPasteEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(C, SHIFT_ANY, SHORTCUT_DOWN),
            keyPressed(V, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(C, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(V, SHIFT_ANY, SHORTCUT_DOWN)));
    private static InputMap<Event> consumeEnterKeyEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(ENTER, SHIFT_ANY, SHORTCUT_ANY),
            keyReleased(ENTER, SHIFT_ANY, SHORTCUT_ANY)));
    private static InputMap<Event> consumeUndoRedoEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(Y, SHIFT_ANY, SHORTCUT_DOWN),
            keyPressed(KeyCode.Z, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(Y, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(KeyCode.Z, SHIFT_ANY, SHORTCUT_DOWN)));

    private TextField functionalTextField;
    private StyleClassedTextArea visibleTextArea;
    private Map<String, SyntaxHighlightingSupportedInput> stringToSupportedCommands;

    private AutofillSuggestionMenu autofillMenu;
    private StringProperty currentCommand;

    private Subscription syntaxHighlightSubscription;

    public CommandTextField() {
        super();

        // to store patterns/syntax
        stringToSupportedCommands = new HashMap<>();

        currentCommand = new SimpleStringProperty("");

        //--------------- actual text field with auto fill menu --------------

        functionalTextField = new TextField();
        autofillMenu = new AutofillSuggestionMenu(functionalTextField, currentCommand);
        functionalTextField.setContextMenu(autofillMenu);
        functionalTextField.setTextFormatter(new TextFormatter<String>(this::placeholderReplacement));

        //------------ visible text area ---------------

        visibleTextArea = new StyleClassedTextArea();
        visibleTextArea.setId("styled");
        visibleTextArea.setDisable(true);
        visibleTextArea.setShowCaret(Caret.CaretVisibility.ON);
        functionalTextField.caretPositionProperty().addListener((observableValue, number, t1) -> {
            visibleTextArea.displaceCaret((int)t1);
            visibleTextArea.requestFollowCaret();
        });

        // to overlay elements
        StackPane stackPane = new StackPane();
        stackPane.setId("SyntaxBox"); // for css styling css
        stackPane.getChildren().addAll(visibleTextArea, functionalTextField);
        getChildren().add(stackPane);

        //------------ for alignment of actual and visible text area ---------------

        functionalTextField.setPadding(Insets.EMPTY);
        functionalTextField.setBackground(Background.EMPTY);

        // ----- sizing ------

        functionalTextField.fontProperty().addListener((observableValue, font, t1) -> {
            double h = t1.getSize() + 5;
            visibleTextArea.setPrefHeight(h);
            visibleTextArea.setMaxHeight(h);
            visibleTextArea.setMinHeight(h);
            functionalTextField.setPrefHeight(h);
            functionalTextField.setMaxHeight(h);
            functionalTextField.setMinHeight(h);
        });

        widthProperty().addListener((observableValue, number, t1) -> {
            functionalTextField.setPrefWidth(t1.doubleValue());
            functionalTextField.setMinWidth(t1.doubleValue());
            functionalTextField.setMaxWidth(t1.doubleValue());

            visibleTextArea.setPrefWidth(t1.doubleValue());
            visibleTextArea.setMinWidth(t1.doubleValue());
            visibleTextArea.setMaxWidth(t1.doubleValue());
        });

        Nodes.addInputMap(functionalTextField, consumeCopyPasteEvent);
        Nodes.addInputMap(functionalTextField, consumeUndoRedoEvent);
        Nodes.addInputMap(visibleTextArea, consumeUndoRedoEvent);
        Nodes.addInputMap(visibleTextArea, consumeEnterKeyEvent);
    }

    /**
     * Clears the text.
     */
    public void clear() {
        functionalTextField.clear();
        visibleTextArea.clear();
    }

    /**
     * Filters placeholders from input before returning value.
     * @return The text property value of the text area with placeholders replaced with an empty String.
     */
    public String getText() {
        return functionalTextField.getText().replaceAll(PLACE_HOLDER_REGEX, "");
    }

    public StringProperty textProperty() {
        return functionalTextField.textProperty();
    }

    /**
     * Enable syntax highlighting.
     */
    public void enableSyntaxHighlighting() {
        syntaxHighlightSubscription =
                visibleTextArea.multiPlainChanges()
                    .successionEnds(Duration.ofMillis(500))
                        .subscribe(ignore -> {
                            visibleTextArea.setStyleSpans(
                                    0, computeHighlighting(visibleTextArea.getText()));
                        });
    }


    /**
     * Sets the style class of all the text to the style class provided.
     * @param styleClass style class to apply to the text in the text area.
     */
    public void overrideStyle(String styleClass) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(styleClass), visibleTextArea.getLength());
        visibleTextArea.setStyleSpans(0, spansBuilder.create());
        if (syntaxHighlightSubscription != null) {
            syntaxHighlightSubscription.unsubscribe();
        }
    }

    /**
     * Import the css stylesheet containing the different styles for the syntax highlighter.
     */
    public void importStyleSheet(Scene parentSceneOfNode) {
        parentSceneOfNode
                .getStylesheets()
                .add(CommandTextField.class.getResource("/view/syntax-highlighting.css")
                        .toExternalForm());
        enableSyntaxHighlighting();
    }


    /**
     * Add support for syntax highlighting and auto fill for the specified command.
     *  @param command The command word
     * @param prefixes List of prefixes required in the command
     * @param optionalPrefixes
     */
    public void createPattern(String command, List<Prefix> prefixes, List<Prefix> optionalPrefixes) {
        stringToSupportedCommands.put(command, new SyntaxHighlightingSupportedInput(command, prefixes, optionalPrefixes));
        autofillMenu.addCommand(command, prefixes, optionalPrefixes);
    }


    /**
     * Remove support for syntax highlighting and auto fill for the specified command.
     * @param command
     */
    public void removePattern(String command) {
        if (stringToSupportedCommands.containsKey(command)) {
            stringToSupportedCommands.remove(command);
            autofillMenu.removeCommand(command);
        }
    }

    /**
     * Returns the StyleSpans to apply rich text formatting to the text area.
     *
     * This method decides which pattern to use to highlight syntax.
     *
     * @param text The text to be formatted.
     * @return the StyleSpans to apply rich text formatting to the text area.
     */
    private StyleSpans<Collection<String>> computeHighlighting(String text) {

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        if (currentCommand.length().get() > 0) {
            Pattern p = stringToSupportedCommands.get(currentCommand.get()).getPattern();
            int prefixCount = stringToSupportedCommands.get(currentCommand.get()).getPrefixCount();

            return computeHighlighting(text, p, prefixCount);
        }

        // if not a command
        spansBuilder.add(Collections.emptyList(), text.length());
        return spansBuilder.create();
    }

    /**
     * Returns the StyleSpans to apply rich text formatting to the text area, using a given pattern.
     * (adapted from RichTextFX's JavaKeywordsDemo.java)
     *
     * @param text The text to be formatted (guaranteed that the input's command word matches this pattern).
     * @param pattern The pattern used to apply formatting.
     * @param prefixcount The number of prefixes in the command.
     * @return the StyleSpans to apply rich text formatting to the text area.
     */
    private StyleSpans<Collection<String>> computeHighlighting(String text, Pattern pattern, int prefixcount) {
        // pattern should match the command word
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        String lastPrefixStyle = null;

        // style command word
        if (matcher.find()) {
            if (currentCommand.length().get() > 0) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(COMMAND_WORD_STYLE), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
        }

        while (matcher.find()) {
            // highlight command word
            String styleClass = null;

            if (matcher.group(PLACEHOLDER_STYLE) != null) {
                styleClass = PLACEHOLDER_STYLE;
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
                continue;
            }

            // styleclass for prefix
            for (int groupNum = 1; groupNum <= prefixcount; groupNum++) {
                if (matcher.group(PREFIX_STYLE_PREFIX + groupNum) != null) {
                    int styleNumber = (groupNum % 4) + 1;
                    lastPrefixStyle = PREFIX_STYLE_PREFIX + styleNumber;
                    styleClass = lastPrefixStyle;
                    break;
                }
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
                continue;
            }

            // styleclass for arguments
            if (matcher.group(ARGUMENT_STYLE_PREFIX) != null) {
                if (lastPrefixStyle != null) {
                    styleClass = lastPrefixStyle.replace(PREFIX_STYLE_PREFIX, ARGUMENT_STYLE_PREFIX);
                } else {
                    styleClass = STRING_STYLE;
                }
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    /**
     * Take a {@code TextFormatter.Change} and returns a new {@code TextFormatter.Change} with appropriate modifcation
     * in order to handle auto fill and syntax suggestion.
     * @param change The original non-formatted changes.
     * @return The formatted changes.
     */
    private TextFormatter.Change placeholderReplacement(TextFormatter.Change change) {

        if (change.isContentChange()) {
            // prevent insertion of newline and < > characters
            change.setText(change.getText().replaceAll(PLACE_HOLDER_REGEX, ""));
            change.setText(change.getText().replaceAll("[<>\\n\\r]", ""));


            String commandWordRegex = String.join("|", stringToSupportedCommands.keySet());

            Matcher command =
                    Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")\\s+")
                            .matcher(change.getControlNewText());

            // add placeholder if match prefix
            if (command.find()) {
                String cmd = command.group("COMMAND");
                currentCommand.setValue(cmd);
                if (change.isAdded()) {
                    String beforecaret = change.getControlNewText().substring(0, change.getRangeEnd() + 1);
                    List<String> tokens = List.of(beforecaret.split("\\s+"));
                    String possiblePrefix = tokens.get(tokens.size() - 1);
                    if (stringToSupportedCommands.get(cmd).getPrefix(possiblePrefix) != null) {
                        String desc = stringToSupportedCommands.get(cmd).getDescription(possiblePrefix);
                        if (beforecaret.endsWith(" " + possiblePrefix)) {
                            change.setText(change.getText() + " <" + desc + ">");
                        }
                    }
                }
            } else {
                currentCommand.setValue("");
            }

            Pattern placeHolderPattern = Pattern.compile(PLACE_HOLDER_REGEX);
            Matcher placeholder = placeHolderPattern.matcher(change.getControlText());
            // replace the entire placeholder if change occurs within it
            while (placeholder.find()) {
                // find group until caret lies inside
                if (change.getCaretPosition() <= placeholder.end()
                        && change.getCaretPosition() >= placeholder.start()) {

                    String before = change.getControlText().substring(0, placeholder.start());
                    String mid = change.getText();
                    String after = change.getControlText().substring(placeholder.end());
                    String replacement = before + mid + after;

                    change.setRange(0, change.getControlText().length());

                    change.setText(replacement);
                    change.setCaretPosition((before + mid).length());
                    change.setAnchor((before + mid).length());
                    break;
                }
            }
        }

        if (change.isReplaced()) {
            visibleTextArea.replaceText(change.getRangeStart(), change.getRangeEnd(), change.getText());
        } else if (change.isDeleted()) {
            visibleTextArea.deleteText(change.getRangeStart(), change.getRangeEnd());
        } else if (change.isAdded()) {
            visibleTextArea.insertText(change.getRangeStart(), change.getText());
        }
        return change;
    }

}
