package dream.fcard.gui.controllers.windows;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX Application experimenting CLIEditor for user inputs.
 * TODO constructor takes in textarea
 */
public class CliEditor extends Application {

    /**
     * Caret character.
     */
    private final String caretChar = "_";
    /**
     * Prompt when in multiline is false.
     */
    private final String promptChar = "»";
    /**
     * Message display at beginning of multiline input mode.
     */
    private final String multilineMessage = "(ctrl+d to submit)";
    /**
     * How fast the cursor blinks.
     */
    private final int blinkSpeed = 500;
    /**
     * Number of rows to be rendered.
     */
    private final int renderRows = 5;
    // setup constants --------------------------------------------------------

    /**
     * Text area itself.
     */
    private TextArea textArea = new TextArea();
    /**
     * Cursor blinker.
     */
    private Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(blinkSpeed),
        x -> toggleCaret()
    ));
    // objects ----------------------------------------------------------------

    /**
     * All texts printed to the text area.
     */
    private ArrayList<String> lines = new ArrayList<>(Arrays.asList(""));
    /**
     * History of single line inputs.
     */
    private ArrayList<String> history = new ArrayList<>();
    /**
     * Current history index.
     */
    private int historyIndex = -1;
    /**
     * Flag that detects just scrolled up in history
     */
    private boolean justHistory = false;
    /**
     * Caret position at the current line.
     */
    private int linecaret = lines.get(0).length();
    /**
     * The current line; index in lines.
     */
    private int line = 0;
    /**
     * The top most line to be rendered
     */
    private int renderline = line;
    /**
     * Upper bound where user can edit and traverse
     */
    private int editableLine = line;
    /**
     * Left bound where user can edit and traverse
     */
    private int editableCaret = 0;
    /**
     * Flag if multiline mode or single line mode.
     */
    private boolean multiline = true;
    /**
     * Blink flag.
     */
    private boolean showCaret = false;
    // variables --------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) {

        textArea.setEditable(false);
        textArea.setText("");
        textArea.setOnKeyPressed(this::processKeyInput);
        textArea.setFont(Font.loadFont(
                getClass().getClassLoader().getResource("fonts/Inconsolata.otf").toExternalForm(), 12));
        textArea.setWrapText(true);

        StackPane root = new StackPane();
        root.getChildren().add(textArea);
        primaryStage.setScene(new Scene(root, 500, 250));
        primaryStage.show();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        printNewLine("Welcome to FlashCardPro", false, false);
        printMultiEdit("{\n  \"key\" : 123\n}");
    }

    /**
     * Print a single line of text and enter single line mode.
     * @param str
     */
    public void printNewLine(String str) {
        printNewLine(str, true, false);
    }

    /**
     * Print a single line of text.
     * @param str           string to be printed
     * @param newPreLine    add a new line before printing
     * @param multi         true; enters multiline mode following printing
     */
    public void printNewLine(String str, Boolean newPreLine, Boolean multi) {
        multiline = true;
        gotoEnd();
        if (newPreLine) {
            newLine();
        }
        characterInput(str);
        newLine();
        if (!multi) {
            printPrompt();
        } else {
            editableLine = line;
            editableCaret = linecaret;
        }
    }

    /**
     * Print a multiline string that the user is able to edit
     * @param str   string
     */
    public void printMultiEdit(String str) {
        printNewLine(multilineMessage, true, true);
        if (str.isEmpty()) {
            return;
        }
        for (String s : str.split("\n")) {
            characterInput(s);
            newLine();
        }
        backSpace();
    }

    // TODO assign printNewLine methods to consumer

    /**
     * KeyEvent handler for textArea.
     * @param e KeyEvent argument
     */
    private void processKeyInput(KeyEvent e) {
        int code = e.getCode().getCode();
        if (code == 68 && e.isControlDown() && multiline) { //multiline escape
            multilineEscape();
        } else if (code == 67 && e.isControlDown() && !multiline) { // clear line
            clearSingleLine();
        } else if (code == 37) { // left
            moveLeft();
        } else if (code == 38) { // up
            moveUp();
        } else if (code == 39) { // right
            moveRight();
        } else if (code == 40) { // down
            moveDown();
        } else if (code == 10) { // enter
            newLine();
        } else if (code == 8) { // backspace
            backSpace();
        } else if (validKey(code)) {
            characterInput(e.getText());
        }
        if (code != 38 && code != 40) {
            justHistory = false;
        }
        render();
    }

    /**
     * User wants to submit input
     */
    private void sendInput() {
        String str = getInput();
        if (!multiline) {
            history.add(str);
        }
        System.out.println("YOUR TEXT:\n" + str + "\n---");
        //TODO call responder with str
        historyIndex = -1;
    }

    /**
     * End multiline mode
     */
    private void multilineEscape() {
        sendInput();
        gotoEnd();
        newLine();
        multiline = false;
        printPrompt();
    }

    /**
     * Navigation left.
     */
    private void moveLeft() {
        if (line == editableLine && linecaret <= editableCaret) {
            return;
        }
        if (linecaret > 0) {
            linecaret--;
        } else if (line > 0) {
            line--;
            linecaret = lines.get(line).length();
        }
    }

    /**
     * Nagivation up.
     */
    private void moveUp() {
        if (!multiline && history.size() > 0) {
            if (historyIndex == 0) {
                return;
            }
            if (historyIndex == -1) {
                historyIndex = history.size() - 1;
            } else {
                historyIndex--;
            }
            printHistory();
        }
        if (line == editableLine) {
            return;
        } else if (line > 0) {
            line--;
            linecaret = Math.min(linecaret, lines.get(line).length());
        }
    }

    /**
     * Navigation right.
     */
    private void moveRight() {
        if (line == lines.size() - 1 && linecaret >= lines.get(line).length()) {
            return;
        }
        if (linecaret == lines.get(line).length()) {
            line++;
            linecaret = 0;
        } else {
            linecaret++;
        }
    }

    /**
     * Navigation down.
     */
    private void moveDown() {
        if (justHistory) {
            if (historyIndex < history.size() - 1) {
                historyIndex++;
                printHistory();
            } else {
                clearSingleLine();
            }
        }
        if (line < lines.size() - 1) {
            line++;
            linecaret = Math.min(linecaret, lines.get(line).length());
        }
    }

    /**
     * Newline action.
     */
    private void newLine() {
        if (multiline) {
            String lineString = lines.get(line);
            lines.set(line, lineString.substring(0, linecaret));
            lines.add(line + 1, lineString.substring(linecaret));
            line++;
            linecaret = 0;
        } else {
            sendInput();
            lines.add("");
            printPrompt();
        }
    }

    /**
     * Backspace action.
     */
    private void backSpace() {
        if (line == editableLine && linecaret == editableCaret) {
            historyIndex = -1;
            return;
        }
        if (linecaret == 0) {
            linecaret = lines.get(line - 1).length();
            lines.set(line - 1, lines.get(line - 1) + lines.get(line));
            lines.remove(line);
            line--;
        } else {
            String lineString = lines.get(line);
            lines.set(line, lineString.substring(0, linecaret - 1) + lineString
                    .substring(linecaret));
            linecaret--;
        }
    }

    /**
     * Single line key in string.
     * @param c string keyed in
     */
    private void characterInput(String c) {
        String lineString = lines.get(line);
        lines.set(line, lineString.substring(0, linecaret) + c + lineString.substring(linecaret));
        linecaret += c.length();
    }

    /**
     * Set cursor to end of all texts.
     */
    private void gotoEnd() {
        line = lines.size() - 1;
        linecaret = lines.get(line).length();
    }

    /**
     * Clears the user input for single line.
     */
    private void clearSingleLine() {
        if (!multiline) {
            historyIndex = -1;
            justHistory = false;
            lines.set(line, lines.get(line).substring(0, editableCaret));
            linecaret = editableCaret;
        }
    }

    /**
     * Print user prompt.
     */
    private void printPrompt() {
        String status = "default";
        gotoEnd();
        characterInput("(" + status + ")" + promptChar);
        editableLine = line;
        editableCaret = linecaret;
        multiline = false;
    }

    /**
     * Print history.
     */
    private void printHistory() {
        String lineString = lines.get(line);
        String newLine = lineString.substring(0, editableCaret) + history.get(historyIndex);
        lines.set(line, newLine);
        linecaret = newLine.length();
        justHistory = true;
    }

    /**
     * Check if keycode is a valid printable character.
     * @param keycode   keycode
     * @return          true; is valid
     */
    private boolean validKey(int keycode) {
        return keycode == 13
                || (keycode > 185 && keycode < 193)
                || (keycode > 218 && keycode < 223)
                || (keycode >= 32 && keycode <= 126);
    }

    /**
     * Collate user input into a single string.
     * @return  user input
     */
    private String getInput() {
        String str = lines.get(editableLine).substring(editableCaret);
        for (int i = editableLine + 1; i < lines.size(); i++) {
            str += "\n" + lines.get(i);
        }
        return str;
    }

    /**
     * Render with caret
     */
    private void render() {
        render(true);
    }

    /**
     * Render
     * @param withCaret true; with caret
     */
    private void render(Boolean withCaret) {
        String totalOutput = "";
        if (line >= renderline + renderRows) {
            renderline = line - renderRows + 1;
        } else if (line < renderline) {
            renderline = line;
        }
        for (int i = renderline; i < Math.min(lines.size(), renderline + renderRows); i++) {
            String lineString = lines.get(i);
            totalOutput += i == line && withCaret ? addCaret(lineString) : lineString;
            if (i < lines.size() - 1) {
                totalOutput += "\n";
            }
        }
        textArea.setText(totalOutput);
        textArea.setScrollTop(Double.MAX_VALUE);
        if (withCaret) {
            showCaret = true;
            timeline.stop();
            timeline.play();
        }
    }

    /**
     * Add caret to current line string.
     * @param lineString    current line string
     * @return              current line string with caret
     */
    private String addCaret(String lineString) {
        return linecaret == lineString.length() ? lineString + caretChar
                : lineString.substring(0, linecaret) + caretChar + lineString.substring(linecaret + 1);
    }

    /**
     * Used by timer for caret blinking.
     */
    private void toggleCaret() {
        render(showCaret);
        showCaret = !showCaret;
    }
}
