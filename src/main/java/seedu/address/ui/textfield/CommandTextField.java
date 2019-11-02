package seedu.address.ui.textfield;

import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_DOWN;
import static org.fxmisc.wellbehaved.event.EventPattern.eventType;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;
import static org.fxmisc.wellbehaved.event.EventPattern.keyReleased;
import static org.fxmisc.wellbehaved.event.EventPattern.mousePressed;
import static seedu.address.ui.textfield.SyntaxHighlightingSupportedInput.PLACEHOLDER_REGEX;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.reactfx.Subscription;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.parser.Prefix;

/**
 * A single line text area utilising RichTextFX to support syntax highlighting of user input. This has some code which
 * is adapted from OverrideBehaviorDemo and JavaKeywordsDemo in RichTextFX.
 */
public class CommandTextField extends Region {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String PREFIX_STYLE_PREFIX = "prefix";
    private static final String ARGUMENT_STYLE_PREFIX = "arg";
    private static final String COMMAND_WORD_STYLE = "command-word";
    private static final String PLACEHOLDER_STYLE = "placeholder";
    private static final String STRING_STYLE = "string";
    private static final String CSS_FILE_PATH = "/view/syntax-highlighting.css";

    private static InputMap<Event> consumeCopyPasteEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(C, SHIFT_ANY, SHORTCUT_DOWN),
            keyPressed(V, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(C, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(V, SHIFT_ANY, SHORTCUT_DOWN)));
    private static InputMap<Event> consumeTabKey = InputMap.consume(EventPattern.anyOf(
            keyPressed(TAB, SHIFT_ANY, SHORTCUT_ANY)));

    private static InputMap<Event> consumeEnterKeyEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(ENTER, SHIFT_ANY, SHORTCUT_ANY),
            keyReleased(ENTER, SHIFT_ANY, SHORTCUT_ANY)));
    private static InputMap<Event> consumeUndoRedoEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(Y, SHIFT_ANY, SHORTCUT_DOWN),
            keyPressed(KeyCode.Z, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(Y, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(KeyCode.Z, SHIFT_ANY, SHORTCUT_DOWN)));
    private static InputMap<Event> consumeMouseDragEvent = InputMap.consume(EventPattern.anyOf(
            eventType(MouseEvent.MOUSE_DRAGGED),
            eventType(MouseEvent.DRAG_DETECTED),
            mousePressed().unless(e -> e.getClickCount() == 1 && !e.isShiftDown())));


    private TextField functionalTextField;
    private StyleClassedTextArea visibleTextArea;
    private InputHistory inputHistory;

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
        //functionalTextField.setTextFormatter(new TextFormatter<String>(this::placeholderReplacement));

        //------------ visible text area ---------------

        //visibleTextArea = new StyleClassedTextArea();
        //visibleTextArea.setDisable(true);
        //        functionalTextField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
        //            if (t1) {
        //                visibleTextArea.setShowCaret(Caret.CaretVisibility.ON);
        //            } else {
        //                clear();
        //                visibleTextArea.setShowCaret(Caret.CaretVisibility.OFF);
        //            }
        //        });

        //        functionalTextField.caretPositionProperty().addListener((unused1, unused2, position) -> {
        //        //    visibleTextArea.displaceCaret((int) position);
        //         //   visibleTextArea.requestFollowCaret();
        //        });


        // --------- input history -------
        inputHistory = new InputHistory();
        functionalTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(UP)) {
                try {
                    replaceWithPreviousInput();
                } catch (NoSuchElementException ignore) {
                    // ignore
                }
            } else if (keyEvent.getCode().equals(DOWN)) {
                try {
                    replaceWithNextInput();
                } catch (NoSuchElementException ignore) {
                    // ignore
                }
            }
        });

        // to overlay elements
        //functionalTextField.setBackground(Background.EMPTY);
        //functionalTextField.setOpacity(0.0);
        StackPane stackPane = new StackPane();
        stackPane.setId("SyntaxBox"); // for css styling css
        stackPane.getChildren().addAll(functionalTextField);
        //stackPane.getChildren().addAll(visibleTextArea, functionalTextField);
        getChildren().add(stackPane);

        //------------ for alignment of actual and visible text area ---------------

        functionalTextField.setPadding(Insets.EMPTY);
        //functionalTextField.setBackground(Color.WHITE);

        // ----- sizing ------

        functionalTextField.fontProperty().addListener((unused1, unused2, font) -> {
            double h = font.getSize() + 5;
            //            visibleTextArea.setPrefHeight(h);
            //            visibleTextArea.setMaxHeight(h);
            //            visibleTextArea.setMinHeight(h);
            functionalTextField.setPrefHeight(h);
            functionalTextField.setMaxHeight(h);
            functionalTextField.setMinHeight(h);
        });

        widthProperty().addListener((unused1, unused2, width) -> {
            functionalTextField.setPrefWidth(width.doubleValue());
            functionalTextField.setMinWidth(width.doubleValue());
            functionalTextField.setMaxWidth(width.doubleValue());

            //            visibleTextArea.setPrefWidth(width.doubleValue());
            //            visibleTextArea.setMinWidth(width.doubleValue());
            //            visibleTextArea.setMaxWidth(width.doubleValue());
            //clear();
        });

        // prevent certain behaviour
        // Nodes.addInputMap(functionalTextField, consumeCopyPasteEvent);
        // Nodes.addInputMap(functionalTextField, consumeUndoRedoEvent);
        // Nodes.addInputMap(functionalTextField, consumeTabKey);
        // Nodes.addInputMap(functionalTextField, consumeMouseDragEvent);
        //        Nodes.addInputMap(visibleTextArea, consumeUndoRedoEvent);
        //        Nodes.addInputMap(visibleTextArea, consumeEnterKeyEvent);
        //        Nodes.addInputMap(visibleTextArea, consumeTabKey);
        //        Nodes.addInputMap(visibleTextArea, consumeMouseDragEvent);

        // prevent selection of text
        //        functionalTextField.selectionProperty().addListener((observableValue, indexRange, t1) -> {
        //            if (t1.getLength() > 0) {
        //                functionalTextField.deselect();
        //                visibleTextArea.deselect();
        //            }
        //        });
    }

    /**
     * Clears the text.
     */
    public void clear() {
        functionalTextField.clear();
        //visibleTextArea.clear();
    }

    /**
     * Commits the text to history and clears the text field.
     */
    public void commitAndFlush() {
        String input = getText();
        inputHistory.push(input);
        clear();
    }

    /**
     * Inserts the previous input in command history character by character.
     * @throws NoSuchElementException
     */
    public void replaceWithPreviousInput() throws NoSuchElementException {
        String previous = inputHistory.getPreviousInput();
        // if no exception thrown
        clear();
        for (Character character : previous.toCharArray()) {
            functionalTextField.insertText(functionalTextField.getLength(), character.toString());
        }
    }

    /**
     * Inserts the next input in input history character by character.
     * @throws NoSuchElementException
     */
    public void replaceWithNextInput() throws NoSuchElementException {
        String next = inputHistory.getNextInput();
        // if no exception thrown
        clear();
        for (Character character : next.toCharArray()) {
            functionalTextField.insertText(functionalTextField.getLength(), character.toString());
        }
    }

    /**
     * Filters placeholders from input before returning value.
     *
     * @return The text property value of the text area with placeholders replaced with an empty String.
     */
    public String getText() {
        return functionalTextField.getText();
        //return functionalTextField.getText().replaceAll(PLACEHOLDER_REGEX, "");
    }

    public ReadOnlyStringProperty textProperty() {
        return functionalTextField.textProperty();
    }

    /**
     * Enable syntax highlighting.
     */
    public void enableSyntaxHighlighting() {
        //        syntaxHighlightSubscription =
        //                visibleTextArea.multiPlainChanges()
        //                        .successionEnds(Duration.ofMillis(500))
        //                        .subscribe(ignore -> {
        //                            visibleTextArea.setStyleSpans(
        //                                    0, computeHighlighting(visibleTextArea.getText()));
        //                        });
    }


    /**
     * Sets the style class of all the text to the style class provided.
     *
     * @param styleClass style class to apply to the text in the text area.
     */
    public void overrideStyle(String styleClass) {
        //        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        //        spansBuilder.add(Collections.singleton(styleClass), visibleTextArea.getLength());
        //        visibleTextArea.setStyleSpans(0, spansBuilder.create());
        //        if (syntaxHighlightSubscription != null) {
        //            syntaxHighlightSubscription.unsubscribe();
        //}
    }

    /**
     * Import the css stylesheet containing the different styles for the syntax highlighter.
     */
    public void importStyleSheet(Scene parentSceneOfNode) {
        //        parentSceneOfNode.getStylesheets()
        //                .add(CommandTextField.class.getResource(CSS_FILE_PATH).toExternalForm());
        //        enableSyntaxHighlighting();
    }


    /**
     * Add support for syntax highlighting and auto fill for the specified command.
     *
     * @param command          The command word
     * @param prefixes         List of prefixes required in the command
     * @param optionalPrefixes
     */
    public void addSupportFor(String command, List<Prefix> prefixes, List<Prefix> optionalPrefixes) {
        stringToSupportedCommands.put(
                command,
                new SyntaxHighlightingSupportedInput(command, prefixes, optionalPrefixes));
        autofillMenu.addCommand(command, prefixes, optionalPrefixes);
    }


    /**
     * Remove support for syntax highlighting and auto fill for the specified command.
     *
     * @param command
     */
    public void removeSupportFor(String command) {
        if (stringToSupportedCommands.containsKey(command)) {
            stringToSupportedCommands.remove(command);
            autofillMenu.removeCommand(command);
        }
    }

    /**
     * Returns the StyleSpans to apply rich text formatting to the text area.
     * <p>
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
     * Returns the StyleSpans to apply rich text formatting to the text area, using a given pattern. (adapted from
     * RichTextFX's JavaKeywordsDemo.java)
     *
     * @param text        The text to be formatted (guaranteed that the input's command word matches this pattern).
     * @param pattern     The pattern used to apply formatting.
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
     *
     * @param change The original non-formatted changes.
     * @return The formatted changes.
     */
    private TextFormatter.Change placeholderReplacement(TextFormatter.Change change) {

        if (change.isContentChange()) {
            // prevent insertion of newline and < > characters
            //            change.setText(change.getText().replaceAll(PLACEHOLDER_REGEX, ""));
            change.setText(change.getText().replaceAll("[\\n\\r]", ""));


            String commandWordRegex = String.join("|", stringToSupportedCommands.keySet());

            Matcher command =
                    Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")\\s+")
                            .matcher(change.getControlNewText());

            // add placeholder if match prefix
            if (command.find()) {
                String cmd = command.group("COMMAND");
                currentCommand.setValue(cmd);
            } else {
                currentCommand.setValue("");
            }


            // insert placeholder
            if (!currentCommand.get().isBlank() && change.isAdded()) {
                String cmd = currentCommand.get();
                String beforecaret = change.getControlNewText().substring(0, change.getRangeEnd() + 1);
                List<String> tokens = List.of(beforecaret.split("\\s+"));
                String possiblePrefix = tokens.get(tokens.size() - 1);
                if (stringToSupportedCommands.get(cmd).getPrefix(possiblePrefix) != null) {
                    String desc = stringToSupportedCommands.get(cmd).getDescription(possiblePrefix);
                    if (beforecaret.endsWith(" " + possiblePrefix)) {
                        change.setText(change.getText() + "<" + desc + ">");
                    }
                }
            }

            Pattern placeHolderPattern = Pattern.compile(PLACEHOLDER_REGEX);
            Matcher placeholder = placeHolderPattern.matcher(change.getControlText());
            // replace the entire placeholder if change occurs within it
            while (placeholder.find()) {
                // find group until caret lies inside
                if ((change.isAdded()
                        && change.getControlCaretPosition() <= placeholder.end()
                        && change.getControlCaretPosition() >= placeholder.start())
                        || ((change.isDeleted()
                        && change.getControlCaretPosition() <= placeholder.end()
                        && change.getControlCaretPosition() >= placeholder.start()))) {

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
