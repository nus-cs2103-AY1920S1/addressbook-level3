package seedu.address.ui;

import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCode.X;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_ANY;
import static javafx.scene.input.KeyCombination.SHORTCUT_DOWN;
import static org.fxmisc.wellbehaved.event.EventPattern.eventType;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;
import static org.fxmisc.wellbehaved.event.EventPattern.mousePressed;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;

import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import seedu.address.logic.parser.Prefix;

/**
 * A single line text area utilising RichTextFX to support syntax highlighting of user input.
 * This code is adapted from OverrideBehaviorDemo and JavaKeywordsDemo in RichTextFX.
 */
public class SyntaxHighlightTextArea extends StyleClassedTextArea {

    // temporary static patterns before proper integration
    private static final String INPUT_PATTERN_TEMPLATE = "((?<preamble>^\\s*)(?<COMMAND>%s))|%s(?<arg>\\S+)";

    private Map<String, Pattern> stringPatternMap;

    private Map<String, Integer> stringIntMap;

    private Map<String, String> stringAutofillMap;

    private InputMap<Event> consumeKeyPress;

    private EventHandler<KeyEvent> disableAutoCompletionOnBackspaceDownHandler;

    private EventHandler<KeyEvent> enableAutoCompletionOnBackspaceReleasedHandler;

    private ChangeListener<String> autoFillAddCompulsoryFieldsListener;

    private Subscription cleanupWhenNoLongerNeedIt;

    public SyntaxHighlightTextArea() {
        super();

        stringPatternMap = new HashMap<>();
        stringIntMap = new HashMap<>();
        stringAutofillMap = new HashMap<>();

        consumeKeyPress = InputMap.consume(EventPattern.anyOf(
                // enter
                keyPressed(ENTER, SHIFT_ANY, SHORTCUT_ANY),
                // disable paste cut copy
                keyPressed(C, SHIFT_ANY, SHORTCUT_DOWN),
                keyPressed(X, SHIFT_ANY, SHORTCUT_DOWN),
                keyPressed(V, SHIFT_ANY, SHORTCUT_DOWN),
                // undo redo
                keyPressed(Y, SHIFT_ANY, SHORTCUT_DOWN),
                keyPressed(KeyCode.Z, SHIFT_ANY, SHORTCUT_DOWN),
                // mouse select
                eventType(MouseEvent.MOUSE_DRAGGED),
                eventType(MouseEvent.DRAG_DETECTED),
                mousePressed().unless(e -> e.getClickCount() == 1 && !e.isShiftDown())
        ));

        disableAutoCompletionOnBackspaceDownHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(BACK_SPACE)) {
                    textProperty().removeListener(autoFillAddCompulsoryFieldsListener);
                }
            }
        };

        enableAutoCompletionOnBackspaceReleasedHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(BACK_SPACE)) {
                    textProperty().addListener(autoFillAddCompulsoryFieldsListener);
                }
            }
        };

        autoFillAddCompulsoryFieldsListener = (obser, s, t1) -> {
            if (stringAutofillMap.containsKey(t1.trim())) {
                replaceText(stringAutofillMap.get(t1.trim()));
            }
        };

        // temporary sizes
        double h = 40;
        setHeight(h);
        setPrefHeight(h);
        setMaxHeight(h);
        setMinHeight(h);
        setMinWidth(200);

        Nodes.addInputMap(this, consumeKeyPress);

        // listeners for auto completion
        textProperty().addListener(autoFillAddCompulsoryFieldsListener);

        // key event handlers
        //addEventHandler(KeyEvent.KEY_PRESSED, enterKeyPressedHandler);
        addEventHandler(KeyEvent.KEY_PRESSED, disableAutoCompletionOnBackspaceDownHandler);
        addEventHandler(KeyEvent.KEY_RELEASED, enableAutoCompletionOnBackspaceReleasedHandler);

        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`
    }

    /**
     * Enable syntax highlighting.
     */
    public void enableSyntaxHighlighting() {
        cleanupWhenNoLongerNeedIt =
                multiPlainChanges()
                    .successionEnds(Duration.ofMillis(200))
                        .subscribe(ignore -> {
                            this.setStyleSpans(0, computeHighlighting(this.getText()));
                        });
    }

    /**
     * Sets the style class of all the text to the style class provided.
     * @param styleClass style class to apply to the text in the text area.
     */
    public void overrideStyle(String styleClass) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(styleClass), getLength());
        setStyleSpans(0, spansBuilder.create());
        if (cleanupWhenNoLongerNeedIt != null) {
            cleanupWhenNoLongerNeedIt.unsubscribe();
        }
    }

    /**
     * Import the css stylesheet containing the different styles for the syntax highlighter.
     */
    public void importStyleSheet(Scene scene) {
        scene
                .getStylesheets()
                .add(SyntaxHighlightTextArea.class.getResource("/view/syntax-highlighting.css")
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
     * @param requiredSyntax Syntax for the autofill to replace text with
     */
    public void createPattern(String command, List<Prefix> prefixes, String requiredSyntax) {
        Pattern p = compileCommandPattern(command, prefixes);
        stringPatternMap.put(command, p);
        stringIntMap.put(command, prefixes.size());
        stringAutofillMap.put(command, requiredSyntax);
    }

    /**
     * Remove support for syntax highlighting and auto fill for the specified command.
     * @param command
     */
    public void removePattern(String command) {
        if (stringPatternMap.containsKey(command)) {
            stringPatternMap.remove(stringPatternMap.get(command));
            stringIntMap.remove(stringIntMap.get(command));
            stringAutofillMap.remove(stringAutofillMap.get(command));
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
        Matcher m = Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")\\s*$").matcher(text);

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        if (m.find()) {
            Pattern p = stringPatternMap.get(m.group("COMMAND"));
            int prefixCount = stringIntMap.get(m.group("COMMAND"));

            if (p == null) {
                spansBuilder.add(Collections.emptyList(), text.length());
                return spansBuilder.create();
            } else {
                return computeHighlighting(text, p, prefixCount);
            }
        }

        // if not a command
        spansBuilder.add(Collections.emptyList(), text.length());
        return spansBuilder.create();
    }

    /**
     * Returns the StyleSpans to apply rich text formatting to the text area, using a given pattern.
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
        String command = null;
        String lastPrefix = null;
        int offset = 0;
        while (matcher.find()) {

            // highlight command word
            String styleClass = null;
            if (matcher.group("COMMAND") != null) {
                command = matcher.group("COMMAND");
                // compute the whitespaces before the command word to be unstyled
                offset = matcher.group("preamble").length();
                styleClass = "command-word";
            } else {
                if (command == null) {
                    break;
                }
                for (int groupNum = 1; groupNum <= prefixcount; groupNum++) {
                    if (matcher.group("prefix" + groupNum) != null) {
                        lastPrefix = "prefix" + groupNum;
                        styleClass = "style" + groupNum;
                        break;
                    } else if (matcher.group("arg") != null) {
                        if (lastPrefix != null) {
                            styleClass = lastPrefix.replace("prefix", "arg");
                        } else {
                            styleClass = "string";
                        }
                        break;
                    }
                }
            }
            /* should not remain null */ assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() + offset - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start() - offset);
            offset = 0;
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

        return spansBuilder.create();
    }

}
