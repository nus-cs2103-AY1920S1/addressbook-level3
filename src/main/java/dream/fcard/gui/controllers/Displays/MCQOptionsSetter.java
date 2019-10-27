package dream.fcard.gui.controllers.Displays;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import dream.fcard.model.ConsumerSchema;
import dream.fcard.model.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MCQOptionsSetter extends ScrollPane {
    @FXML
    private VBox optionInputBox;

    private ArrayList<MCQOptionInputRow> rows;
    private ToggleGroup rightAnswer = new ToggleGroup();
    private Consumer<MCQOptionInputRow> deleteRow = row -> {
        if (rows.size() > 1) {
            rows.remove(row);
            renderOptions();
        } else {
            @SuppressWarnings("unchecked")
            Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);
            displayMessage.accept("You need at least 1 option!");
        }
    };
    private Consumer<Boolean> addNewRow = b -> addNewRow();

    public MCQOptionsSetter() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/MCQOptionsSetter.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            rows = new ArrayList<>();
            addNewRow.accept(true);
            renderOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderOptions() {
        optionInputBox.getChildren().clear();
        for (int i = 0; i < rows.size(); i++) {
            MCQOptionInputRow row = rows.get(i);
            row.setOptionNumber(i + 1); //switch to 1-indexing
            optionInputBox.getChildren().add(row);
        }
    }

    private void addNewRow() {
        @SuppressWarnings("unchecked")
        Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
        clearMessage.accept(true);
        rows.add(new MCQOptionInputRow(rightAnswer, deleteRow, addNewRow));
        renderOptions();
    }

    /**
     * Used by CardEditingWindow to pre-populate a MCQ card's MCQ choices one at a time into the editor.
     * @param text the option for the row being entered
     */
    public void addNewRow(String text, boolean isCorrectAnswer) {
        addNewRow();
        MCQOptionInputRow row = rows.get(rows.size()-1);
        row.setOptionText(text);
        if (isCorrectAnswer) {
            row.setRightAnswerRadio();
        }
        renderOptions();
    }

    /**
     * Used by CardEditingWindow to remove the default first option.
     */
    public void deleteFirstRow() {
        rows.remove(0);
    }

    public boolean hasAtLeastOneNonEmptyOption() {
        boolean hasOptions = false;
        for (MCQOptionInputRow row : rows) {
            if (!row.isBlank()) {
                hasOptions = true;
            }
        }
        return hasOptions;
    }

    public boolean hasDesignatedRightAnswer() {
        RadioButton choice = (RadioButton) rightAnswer.getSelectedToggle();
        if (choice == null) {
            return false;
        }
        return true;
    }

    /**
     * Gets 1-based index of correct answer.
     * @return 1-based index of the correct answer.
     */
    public int getIndexOfRightAnswer() {
        AtomicInteger integer = new AtomicInteger(1);
        for (MCQOptionInputRow row : rows) {
            if (row.hasRightAnswer()) {
                return integer.get();
            }
            integer.incrementAndGet();
        }
        return -1;
    }

    public ArrayList<String> getChoices() {
        ArrayList<String> answers = new ArrayList<>();
        rows.forEach(row -> answers.add(row.getOption()));
        return answers;
    }
}
