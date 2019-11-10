package seedu.moolah.ui.textfield;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import static javafx.scene.input.KeyCode.UP;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Side;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seedu.moolah.logic.parser.Prefix;

/**
 * A single line text area utilising RichTextFX to support syntax highlighting of user input. This has some code which
 * is adapted from OverrideBehaviorDemo and JavaKeywordsDemo in RichTextFX.
 */
public class CommandTextField extends StyleClassedTextArea {

    public static final String ERROR_STYLE_CLASS = "error";
    static final String PREFIX_STYLE_PREFIX = "prefix";
    static final String ARGUMENT_STYLE_PREFIX = "arg";
    static final String COMMAND_WORD_STYLE = "command-word";
    static final String STRING_STYLE = "string";
    static final String SYNTAX_HIGHLIGHTING_CSS_FILE_PATH = "/view/syntax-highlighting.css";

    private static final Border enabledBorder = new Border(
            new BorderStroke(Color.GREEN, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(2)));
    private static final Border disabledBorder = new Border(
            new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(2)));

    private static final double TEXTFIELD_HEIGHT = 30;

    private InputHistory inputHistory;

    private Map<String, SyntaxHighlightingSupportedInput> stringToSupportedCommands;

    private AutofillSuggestionMenu autofillMenu;
    private StringProperty currentCommand;

    private Subscription syntaxHighlightSubscription;

    public CommandTextField(Consumer<String> textGetter) {
        super();

        setId("SyntaxHighlightingTextField"); // for css styling css

        // to store patterns/syntax
        stringToSupportedCommands = new HashMap<>();
        setBorder(disabledBorder);

        currentCommand = new SimpleStringProperty("");

        // ----- sizing ------

        // height to look like single line input
        setPrefHeight(TEXTFIELD_HEIGHT);
        setMaxHeight(TEXTFIELD_HEIGHT);
        setMinHeight(TEXTFIELD_HEIGHT);

        // autofill menu
        autofillMenu = new AutofillSuggestionMenu(this, currentCommand);
        setContextMenu(autofillMenu);

        autofillMenu.enabledProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                setBorder(enabledBorder);
            } else {
                setBorder(disabledBorder);
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                enableSyntaxHighlighting();
            } else {
                syntaxHighlightSubscription.unsubscribe();
            }
        });

        //------------ prevent entering to remain single line ---------------

        addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(ENTER)) {
                textGetter.accept(getText().replaceAll("[\\r\\n]", ""));
                keyEvent.consume();
            } else if (keyEvent.getCode().equals(TAB)) {
                autofillMenu.toggle();
                if (autofillMenu.enabledProperty().get()) {
                    autofillMenu.show(this, Side.BOTTOM, 0, 0);
                }
                keyEvent.consume();
            }
        });

        autofillMenu.showingProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                requestFocus();
            }
        });

        // --------- current command property -------
        textProperty().addListener((observableValue, s, t1) -> {
            String commandWordRegex = String.join("|", stringToSupportedCommands.keySet());

            Matcher command = Pattern.compile("^\\s*(?<COMMAND>" + commandWordRegex + ")(?=\\s|$)").matcher(t1);

            if (command.find()) {
                String cmd = command.group("COMMAND").trim();
                if (!currentCommand.getValue().equals(cmd)) {
                    currentCommand.setValue(cmd);
                }
            } else {
                currentCommand.setValue("");
            }
        });

        // --------- input history -------
        inputHistory = new InputHistory();
        addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
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
    }

    @Override
    public void paste() {
        super.paste();
        replaceText(getText().replaceAll("[\\n\\r]", ""));
    }

    @Override
    public void insertText(int position, String text) {
        super.insertText(position, text.replaceAll("[\\n\\r]", ""));
    }

    /**
     * Commits the text to history and clears the text field.
     */
    public void commitAndFlush() {
        String input = getText();
        inputHistory.push(input);
        clear();
    }


    public AutofillSuggestionMenu getAutofillMenu() {
        return autofillMenu;
    }

    public InputHistory getInputHistory() {
        return inputHistory;
    }

    /**
     * Inserts the previous input in command history character by character.
     * @throws NoSuchElementException
     */
    public void replaceWithPreviousInput() throws NoSuchElementException {
        String previous = inputHistory.getPreviousInput();
        clear();
        replaceText(previous);
    }

    /**
     * Inserts the next input in input history character by character.
     * @throws NoSuchElementException
     */
    public void replaceWithNextInput() throws NoSuchElementException {
        String next = inputHistory.getNextInput();
        clear();
        replaceText(next);
    }

    /**
     * Enable syntax highlighting.
     */
    public void enableSyntaxHighlighting() {
        syntaxHighlightSubscription =
                multiPlainChanges()
                        .successionEnds(Duration.ofMillis(300))
                        .subscribe(ignore -> {
                            setStyleSpans(
                                    0, computeHighlighting(getText()));
                        });
    }


    /**
     * Sets the style class of all the text to the style class provided.
     *
     * @param styleClass style class to apply to the text in the text area.
     */
    public void overrideStyle(String styleClass) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(Collections.singleton(styleClass), getLength());
        setStyleSpans(0, spansBuilder.create());
        if (syntaxHighlightSubscription != null) {
            syntaxHighlightSubscription.unsubscribe();
        }
    }

    /**
     * Add support for syntax highlighting and auto fill for the specified command.
     *
     * @param command          The command word
     * @param prefixes         List of prefixes required in the command
     * @param optionalPrefixes
     */
    public void addSupport(String command, List<Prefix> prefixes, List<Prefix> optionalPrefixes) {
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
    public void removeSupport(String command) {
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
     * @param prefixCount The number of prefixes in the command.
     * @return the StyleSpans to apply rich text formatting to the text area.
     */
    private StyleSpans<Collection<String>> computeHighlighting(String text, Pattern pattern, int prefixCount) {
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

            // styleclass for prefix
            for (int groupNum = 0; groupNum < prefixCount; groupNum++) {
                if (matcher.group(PREFIX_STYLE_PREFIX + groupNum) != null) {
                    int styleNumber = groupNum % 4;
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
}
