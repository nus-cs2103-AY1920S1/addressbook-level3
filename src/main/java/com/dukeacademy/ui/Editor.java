package com.dukeacademy.ui;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.observable.Observable;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * Creates a Text Editor window for the user to input code.
 */
public class Editor extends UiPart<Region> {
    private static final String FXML = "Editor.fxml";
    private SimpleIntegerProperty numberOfLines = new SimpleIntegerProperty(1);

    @FXML
    private TextArea lineCounter;

    @FXML
    private TextArea textOutput;

    /**
     * Instantiates a new Editor.
     *
     * @param questionObservable the question observable
     */
    public Editor(Observable<Question> questionObservable) {
        super(FXML);

        initialize();
        textOutput.setPadding(new Insets(15, 20, 15, 20));

        questionObservable.addListener(question -> {
            if (question != null) {
                UserProgram savedProgram = question.getUserProgram();
                this.textOutput.setText(savedProgram.getSourceCode());
            }
        });
    }

    /**
     * Initializes the initial configurations for the Editor Window upon startup.
     */
    @FXML
    public void initialize() {
        textOutput.addEventHandler(KeyEvent.KEY_PRESSED, e1 -> {
            int currentCaretPosition = textOutput.getCaretPosition();

            if (e1.getCode() == KeyCode.TAB) {
                textOutput.insertText(currentCaretPosition, " ".repeat(2));
                e1.consume();
            } else if (e1.isShiftDown() && e1.getCode() == KeyCode.CLOSE_BRACKET) {
                if (isEmptyLine(textOutput.getText(), textOutput.getCaretPosition())) {
                    int previousNewlineCharPosition = getClosestNewlineCharPosition(currentCaretPosition);
                    int diff = currentCaretPosition - previousNewlineCharPosition;

                    if (diff < 4) {
                        textOutput.deleteText(previousNewlineCharPosition + 1, currentCaretPosition - 1);
                    } else {
                        textOutput.deleteText(currentCaretPosition - 2, currentCaretPosition - 1);
                    }
                }
            }
        });

        textOutput.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                int indentationCount = countUnclosedBraces(textOutput.getText(), textOutput.getCaretPosition());

                String tab = " ".repeat(2);
                textOutput.insertText(textOutput.getCaretPosition(), tab.repeat((int) indentationCount));
            }
        });

        textOutput.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                numberOfLines.setValue((textOutput.getText() + " ").split("\n").length);
                lineCounter.setText(generateLineCounterInput(numberOfLines));
            }
        });

        Platform.runLater(() -> {
            ScrollBar editorsb = (ScrollBar) textOutput.lookup(".scroll-bar:vertical");
            ScrollBar lineCountersb = (ScrollBar) lineCounter.lookup(".scroll-bar:vertical");

            lineCountersb.valueProperty().bind(editorsb.valueProperty());
        });
    }

    private boolean isEmptyLine(String string, int caret) {
        char[] chars = string.toCharArray();
        for (int i = caret - 1; i >= 0; i--)
        {
            if (chars[i] == '\n') {
                return true;
            }

            if (!Character.isWhitespace(chars[i])) {
                return false;
            }
        }

        return true;
    }

    private int countUnclosedBraces(String string, int caret) {
        char[] chars = string.toCharArray();
        int count = 0;

        for (int i = caret - 1; i >= 0; i--)
        {
            if (chars[i] == '{') {
                count++;
            } else if (chars[i] == '}') {
                count--;
            }
        }

        return count < 0 ? 0 : count;
    }

    private int getClosestNewlineCharPosition(int caret) {
        char[] s = textOutput.getText().toCharArray();

        for (int i = caret - 1; i >= 0; i--) {
            if (s[i] == '\n') {
                return i;
            }
        }

        return 0;
    }

    /**
     * Returns the current text in the editor.
     *
     * @return current text in editor.
     */
    public UserProgram getUserProgram() {
        return new UserProgram("Main", textOutput.getText().strip());
    }

    private String generateLineCounterInput(SimpleIntegerProperty n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n.getValue(); i++) {
            if (i == 1) {
                sb.append(i);
            } else {
                sb.append("\n" + i);
            }
        }

        return sb.toString();
    }
}
