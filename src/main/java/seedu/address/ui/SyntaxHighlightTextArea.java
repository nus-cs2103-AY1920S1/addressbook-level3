package seedu.address.ui;

import static javafx.scene.input.KeyCode.A;
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
import static org.fxmisc.wellbehaved.event.EventPattern.mousePressed;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private static final Pattern ADD_COMMAND_PATTERN = Pattern.compile(
            "((?<pream>^\\s*)(?<COMMAND>add))|"
                    + "(?<prefix1>(?:p)/)|"
                    + "(?<prefix2>(?:d)/)|"
                    + "(?<arg>\\S+)"
    );
    private static final Pattern BUDGET_COMMAND_PATTERN = Pattern.compile(
            "((?<pream>^\\s*)(?<COMMAND>budget))|"
                    + "(?<prefix1>(?:d)/)|"
                    + "(?<prefix2>(?:s)/)|"
                    + "(?<prefix3>(?:p)/)|"
                    + "(?<prefix4>(?:pr)/)|"
                    + "(?<arg>\\S+)"
    );

    private InputMap<Event> consumeKeyPress;

    private EventHandler<KeyEvent> enterKeyPressedHandler;

    private EventHandler<KeyEvent> disableAutoCompletionOnBackspaceDownHandler;

    private EventHandler<KeyEvent> enableAutoCompletionOnBackspaceReleasedHandler;

    private ChangeListener<String> autoFillAddCompulsoryFieldsListener;

    public SyntaxHighlightTextArea() {
        super();

        enterKeyPressedHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(ENTER)) {
                    // placeholder handle input
                    clear();
                }
            }
        };

        consumeKeyPress = InputMap.consume(EventPattern.anyOf(
                // enter
                keyPressed(ENTER, SHIFT_ANY, SHORTCUT_ANY),
                // arrow key select
                keyPressed(LEFT, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(KP_LEFT, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(UP, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(KP_UP, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(DOWN, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(KP_DOWN, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
                keyPressed(KP_RIGHT, SHIFT_DOWN, SHORTCUT_ANY),
                // select all
                keyPressed(A, SHIFT_ANY, SHORTCUT_DOWN),
                // paste cut copy
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
            if (t1.trim().equals("add")) {
                replaceText("add d/ <description> p/ <price>");
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
        addEventHandler(KeyEvent.KEY_PRESSED, enterKeyPressedHandler);
        addEventHandler(KeyEvent.KEY_PRESSED, disableAutoCompletionOnBackspaceDownHandler);
        addEventHandler(KeyEvent.KEY_RELEASED, enableAutoCompletionOnBackspaceReleasedHandler);

        // copy from DEMO
        Subscription cleanupWhenNoLongerNeedIt =
                multiPlainChanges()
                .successionEnds(Duration.ofMillis(200))
                .subscribe(ignore -> {
                    this.setStyleSpans(0, computeHighlighting(this.getText()));
                });
        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`
    }

    /**
     * Import the css stylesheet containing the different styles for the syntax highlighter.
     */
    public void importStyleSheet() {
        this.getScene()
                .getStylesheets()
                .add(SyntaxHighlightTextArea.class.getResource("/view/syntax-highlight.css")
                        .toExternalForm());
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

        return Pattern.compile(String.format(
                "((?<preamble>^\\s*)(?<COMMAND>%s))|%s(?<arg>\\S+)",
                commandWord, prefixesPatterns.toString()));
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
        Matcher m = Pattern.compile("^\\s*(?<command>\\S+)").matcher(text);

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        if (m.find()) {
            Pattern p =
                m.group("command").equals("add") ? ADD_COMMAND_PATTERN
                        : m.group("command").equals("budget") ? BUDGET_COMMAND_PATTERN
                        : null;

            int prefixCount =
                    m.group("command").equals("add") ? 2
                            : m.group("command").equals("budget") ? 4
                            : 0;

            if (p == null) {
                spansBuilder.add(Collections.emptyList(), text.length());
                return spansBuilder.create();
            } else {
                return computeHighlighting(text, p, prefixCount);
            }
        }

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
        // pattern2 should be a list of elements in the command
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        String command = null;
        String lastPrefix = null;
        int offset = 0;
        while (matcher.find()) {

            String styleClass = null;
            if (matcher.group("COMMAND") != null) {
                command = matcher.group("COMMAND");
                offset = matcher.group("preamble").length();
                styleClass = "keyword";
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
