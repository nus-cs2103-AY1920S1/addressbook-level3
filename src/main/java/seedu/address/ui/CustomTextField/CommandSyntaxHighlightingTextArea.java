package seedu.address.ui;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.BACK_SLASH;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.KP_DOWN;
import static javafx.scene.input.KeyCode.KP_LEFT;
import static javafx.scene.input.KeyCode.KP_RIGHT;
import static javafx.scene.input.KeyCode.KP_UP;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCode.X;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static javafx.scene.input.KeyCombination.SHIFT_DOWN;
import static javafx.scene.input.KeyCombination.SHORTCUT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_DOWN;
import static org.fxmisc.wellbehaved.event.EventPattern.eventType;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;
import static org.fxmisc.wellbehaved.event.EventPattern.keyReleased;
import static org.fxmisc.wellbehaved.event.EventPattern.mousePressed;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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

import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.parser.Prefix;

/**
 * A single line text area utilising RichTextFX to support syntax highlighting of user input.
 * This has some code which is adapted from OverrideBehaviorDemo and JavaKeywordsDemo in RichTextFX.
 */
public class CommandSyntaxHighlightingTextArea extends Region {

    private static final String PLACE_HOLDER_REGEX = "(?<placeholder><[^>]+>)";
    private static final String INPUT_PATTERN_TEMPLATE = "(?<COMMAND>%s)|" + PLACE_HOLDER_REGEX + "|%s(?<arg>\\S+)";
    private static final KeyEvent RIGHT_ARROW = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "",
            KeyCode.RIGHT, false, false, false, false);
    private static final KeyEvent LEFT_ARROW = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "",
            KeyCode.LEFT, false, false, false, false);
    private static Predicate<KeyEvent> addsCharacterToTextArea = keyEvent -> keyEvent.getCode().isLetterKey()
            || keyEvent.getCode().isDigitKey()
            || keyEvent.getCode().equals(KeyCode.COLON) || keyEvent.getCode().equals(KeyCode.SEMICOLON)
            || keyEvent.getCode().equals(KeyCode.BRACELEFT) || keyEvent.getCode().equals(KeyCode.BRACERIGHT)
            || keyEvent.getCode().equals(KeyCode.LEFT_PARENTHESIS)
            || keyEvent.getCode().equals(KeyCode.RIGHT_PARENTHESIS)
            || keyEvent.getCode().equals(KeyCode.CLOSE_BRACKET) || keyEvent.getCode().equals(KeyCode.OPEN_BRACKET)
            || keyEvent.getCode().equals(KeyCode.PERIOD) || keyEvent.getCode().equals(KeyCode.COMMA)
            || keyEvent.getCode().equals(KeyCode.SLASH) || keyEvent.getCode().equals(BACK_SLASH)
            || keyEvent.getCode().equals(KeyCode.QUOTE) || keyEvent.getCode().equals(KeyCode.BACK_QUOTE)
            || keyEvent.getCode().equals(KeyCode.SPACE) || keyEvent.getCode().equals(KeyCode.EQUALS)
            || keyEvent.getCode().equals(KeyCode.MINUS);
    private static InputMap<Event> consumeMassDeletionEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(BACK_SPACE, SHIFT_ANY, SHORTCUT_DOWN),
            keyPressed(X, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(BACK_SPACE, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(X, SHIFT_ANY, SHORTCUT_DOWN)));
    private static InputMap<Event> consumeMassSelectionEvent = InputMap.consume(EventPattern.anyOf(
            keyPressed(LEFT, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(KP_LEFT, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(KP_RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(DOWN, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(KP_DOWN, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(UP, SHIFT_DOWN, SHORTCUT_ANY),
            keyPressed(KP_UP, SHIFT_DOWN, SHORTCUT_ANY),

            keyReleased(LEFT, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(KP_LEFT, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(KP_RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(DOWN, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(KP_DOWN, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(UP, SHIFT_DOWN, SHORTCUT_ANY),
            keyReleased(KP_UP, SHIFT_DOWN, SHORTCUT_ANY),

            keyPressed(A, SHIFT_ANY, SHORTCUT_DOWN),
            keyReleased(A, SHIFT_ANY, SHORTCUT_DOWN),

            eventType(MouseEvent.MOUSE_DRAGGED),
            eventType(MouseEvent.DRAG_DETECTED),
            mousePressed().unless(e -> e.getClickCount() == 1 && !e.isShiftDown())));
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
    private static InputMap<Event> consumeContextMenuRequestEvent = InputMap.consume(EventPattern.anyOf(
            eventType(ContextMenuEvent.CONTEXT_MENU_REQUESTED)));

    private AutofillSuggestionMenu autofillMenu;
    private TextArea functionalTextArea;
    private StyleClassedTextArea visibleTextArea;
    private Map<String, Pattern> stringPatternMap;
    private Map<String, Integer> stringIntMap;
    private Map<String, String> stringAutofillMap;
    private Subscription syntaxHighlightSubscription;

    public CommandSyntaxHighlightingTextArea() {
        super();

        // to store patterns/syntax
        stringPatternMap = new HashMap<>();
        stringIntMap = new HashMap<>();
        stringAutofillMap = new HashMap<>();

        //--------------- actual text area ---------------

        functionalTextArea = new TextArea();
        autofillMenu = new AutofillSuggestionMenu(functionalTextArea);
        functionalTextArea.setContextMenu(autofillMenu);

        // textformatter which handles syntax/placeholder insertion and replacement of placeholder
        functionalTextArea.setTextFormatter(new TextFormatter<String>(this::autofillAndPlaceholderReplacement));
        //textArea.setOpacity(0);
        functionalTextArea.setBackground(Background.EMPTY);

        //------------ visible text area ---------------
        // richtextfx element to underlay text area for syntax highlighting
        visibleTextArea = new StyleClassedTextArea();
        visibleTextArea.setId("styled");
        visibleTextArea.setDisable(true);
        visibleTextArea.setShowCaret(Caret.CaretVisibility.ON);

        // ---------- mirror text property and caret position --------
        functionalTextArea.textProperty().addListener((observableValue, s, t1) -> {
            visibleTextArea.replaceText(t1);
        });

        functionalTextArea.caretPositionProperty().addListener((observableValue, number, t1) -> {
            try {
                if ((int) t1 != visibleTextArea.getCaretPosition()) {
                    visibleTextArea.displaceCaret((int) t1);
                }
            } catch (IndexOutOfBoundsException e) {
                visibleTextArea.displaceCaret(visibleTextArea.getLength());
            }
        });

        // to overlay elements
        StackPane stackPane =  new StackPane();
        stackPane.setId("SyntaxBox");
        stackPane.getChildren().addAll(visibleTextArea, functionalTextArea);
        getChildren().add(stackPane);

        //------------ for alignment of actual and visible text area ---------------

        functionalTextArea.setPadding(Insets.EMPTY);
        functionalTextArea.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            mirrorViewportMovement(visibleTextArea, keyEvent);
        });

        // disable selection of text
        functionalTextArea.selectionProperty().addListener((observableValue, indexRange, t1) -> {
            if (t1.getLength() > 0) {
                functionalTextArea.deselect();
            }
        });

        // ----- sizing ------
        functionalTextArea.fontProperty().addListener((observableValue, font, t1) -> {
            double h = t1.getSize() + 5;
            visibleTextArea.setPrefHeight(h);
            visibleTextArea.setMaxHeight(h);
            visibleTextArea.setMinHeight(h);
            functionalTextArea.setPrefHeight(h);
            functionalTextArea.setMaxHeight(h);
            functionalTextArea.setMinHeight(h);
        });

        widthProperty().addListener((observableValue, number, t1) -> {
            functionalTextArea.setPrefWidth(t1.doubleValue());
            functionalTextArea.setMinWidth(t1.doubleValue());
            functionalTextArea.setMaxWidth(t1.doubleValue());

            visibleTextArea.setPrefWidth(t1.doubleValue());
            visibleTextArea.setMinWidth(t1.doubleValue());
            visibleTextArea.setMaxWidth(t1.doubleValue());
        });

        Nodes.addInputMap(functionalTextArea, consumeMassSelectionEvent);
        Nodes.addInputMap(functionalTextArea, consumeContextMenuRequestEvent);
        Nodes.addInputMap(functionalTextArea, consumeCopyPasteEvent);
        Nodes.addInputMap(functionalTextArea, consumeUndoRedoEvent);
        Nodes.addInputMap(functionalTextArea, consumeMassDeletionEvent);
        Nodes.addInputMap(functionalTextArea, consumeEnterKeyEvent);

        Nodes.addInputMap(visibleTextArea, consumeMassSelectionEvent);
        Nodes.addInputMap(visibleTextArea, consumeContextMenuRequestEvent);
        Nodes.addInputMap(visibleTextArea, consumeCopyPasteEvent);
        Nodes.addInputMap(visibleTextArea, consumeUndoRedoEvent);
        Nodes.addInputMap(visibleTextArea, consumeMassDeletionEvent);
        Nodes.addInputMap(visibleTextArea, consumeEnterKeyEvent);
    }

    /**
     * Clears the text.
     */
    public void clear() {
        functionalTextArea.clear();
        visibleTextArea.clear();
    }

    /**
     * Filters placeholders from input before returning value.
     * @return The text property value of the text area with placeholders replaced with an empty String.
     */
    public String getText() {
        return functionalTextArea.getText().replaceAll(PLACE_HOLDER_REGEX, "");
    }

    public StringProperty textProperty() {
        return functionalTextArea.textProperty();
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
     * Fires a keyEvent in the visible text area, if it will result in a change in the visible text.
     * @param visibleTextArea The visible text area to mirror the key event event
     * @param keyEvent The keyEvent which may need to be mirrored.
     */
    private void mirrorViewportMovement(StyleClassedTextArea visibleTextArea, KeyEvent keyEvent) {
        // mirrors navigation
        if (keyEvent.getCode().isNavigationKey()) {
            visibleTextArea.fireEvent(keyEvent);

            // mirrors view port shifting right on character input
        } else if (addsCharacterToTextArea.test(keyEvent)) {
            if (keyEvent.isShortcutDown()) {
                return;
            }
            visibleTextArea.fireEvent(RIGHT_ARROW);

            // mirrors viewport shifting on character deletion
        } else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            visibleTextArea.fireEvent(LEFT_ARROW);
        }
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
                .add(CommandSyntaxHighlightingTextArea.class.getResource("/view/syntax-highlighting.css")
                        .toExternalForm());
        enableSyntaxHighlighting();
    }

    /**
     * Compile pattern for a command input syntax used for matching during syntax highlighting.
     * @param commandWord The command word of the command.
     * @param prefixes The list of prefixes of the command.
     * @return The compiled pattern.
     */
    private Pattern compileCommandPattern(String commandWord, List<Prefix> prefixes) {
        StringBuilder prefixesPatterns = new StringBuilder();
        int count = 0;
        for (Prefix prefix : prefixes) {
            count++;
            prefixesPatterns.append(String.format("(?<prefix%s>%s)|", count, prefix.getPrefix()));
        }

        return Pattern.compile(String.format(INPUT_PATTERN_TEMPLATE, commandWord, prefixesPatterns.toString()));
    }

    /**
     * Add support for syntax highlighting and auto fill for the specified command.
     *
     * @param command The command word
     * @param prefixes List of prefixes required in the command
     */
    public void createPattern(String command, List<Prefix> prefixes) {
        Pattern p = compileCommandPattern(command, prefixes);
        stringPatternMap.put(command, p);
        stringIntMap.put(command, prefixes.size());
        StringBuilder autofill = new StringBuilder();
        autofill.append(command);
        for (Prefix prefix : prefixes) {
            autofill.append(" ");
            autofill.append(prefix.getPrefix());
            autofill.append(" <");
            autofill.append(prefix.getDescriptionOfArgument());
            autofill.append(">");
        }
        stringAutofillMap.put(command, autofill.toString());
        autofillMenu.setSuggestions(stringPatternMap.keySet());
    }


    /**
     * Remove support for syntax highlighting and auto fill for the specified command.
     * @param command
     */
    public void removePattern(String command) {
        if (stringPatternMap.containsKey(command)) {
            stringPatternMap.remove(command);
            stringIntMap.remove(command);
            stringAutofillMap.remove(command);
            autofillMenu.setSuggestions(stringPatternMap.keySet());
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
        String commandWordRegex = String.join("|", stringPatternMap.keySet());
        Matcher m = Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")\\b").matcher(text);
        Matcher onlyOne = Pattern.compile("^\\s*\\S+\\s*$").matcher(text);

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        if (m.find()) {
            Pattern p = stringPatternMap.get(m.group("COMMAND"));
            int prefixCount = stringIntMap.get(m.group("COMMAND"));

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
        String lastPrefix = null;

        // style command word
        if (matcher.find()) {
            if (matcher.group("COMMAND") != null) {
                String styleClass = "command-word";
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
        }

        while (matcher.find()) {
            // highlight command word
            String styleClass = null;

            if (matcher.group("placeholder") != null) {
                styleClass = "placeholder";
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
                continue;
            }

            // styleclass for prefix
            for (int groupNum = 1; groupNum <= prefixcount; groupNum++) {
                if (matcher.group("prefix" + groupNum) != null) {
                    int styleNumber = (groupNum % 4) + 1;
                    lastPrefix = "prefix" + styleNumber;
                    styleClass = "style" + styleNumber;
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
            if (matcher.group("arg") != null) {
                if (lastPrefix != null) {
                    styleClass = lastPrefix.replace("prefix", "arg");
                } else {
                    styleClass = "string";
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
    private TextFormatter.Change autofillAndPlaceholderReplacement(TextFormatter.Change change) {
        if (change.isContentChange()) {
            // prevent insertion of newline and < > characters
            change.setText(change.getText().replaceAll("[<>\\n\\r]", ""));

            String commandWordRegex = String.join("|", stringPatternMap.keySet());

            Matcher command =
                    Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")\\s*")
                            .matcher(change.getControlNewText());
            // if adding a character results in a command word, auto fill the command's syntax.
            if (command.matches()) {
                if (change.isAdded()) {
                    int commandWordEnd = command.group().stripTrailing().length();
                    int commandWordStart = command.group().length() - command.group().stripLeading().length();
                    if (change.getCaretPosition() <= commandWordEnd
                            && change.getCaretPosition() >= commandWordStart) {
                        change.setRange(0, change.getControlNewText().length() - 1);
                        change.setText(stringAutofillMap.get(command.group("COMMAND")));
                        return change;
                    }
                }

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

                    return change;
                }
            }

        }
        return change;
    }

}
