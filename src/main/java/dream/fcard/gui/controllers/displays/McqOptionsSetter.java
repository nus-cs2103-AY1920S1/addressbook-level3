package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Dispatcher;
import dream.fcard.model.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * A container to display and remember the multiple choice options the user has created.
 */
public class McqOptionsSetter extends ScrollPane {
    @FXML
    private VBox optionInputBox;

    private ArrayList<McqOptionInputRow> rows;
    private ToggleGroup rightAnswer = new ToggleGroup();
    private Consumer<McqOptionInputRow> deleteRow = row -> {
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

    public McqOptionsSetter() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/MCQOptionsSetter.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            rows = new ArrayList<>();
            addNewRow();
            Dispatcher.addConsumer(ConsumerSchema.ADD_NEW_ROW_MCQ, addNewRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes the list of McqOptionInputRow objects and renders them onto the McqOptionsSetter.
     * Shows the user the options that have currently been created.
     */
    private void renderOptions() {
        optionInputBox.getChildren().clear();
        for (int i = 0; i < rows.size(); i++) {
            McqOptionInputRow row = rows.get(i);
            row.setOptionNumber(i + 1); //switch to 1-indexing
            optionInputBox.getChildren().add(row);
        }
    }

    /**
     * Adds an empty McqOptionInputRow to the McqOptionsSetter for the user to enter another option.
     */
    private void addNewRow() {
        @SuppressWarnings("unchecked")
        Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
        clearMessage.accept(true);
        rows.add(new McqOptionInputRow(rightAnswer, deleteRow, addNewRow));
        renderOptions();
    }

    /**
     * Used by CardEditingWindow to pre-populate a MCQ card's MCQ choices one at a time into the editor.
     * @param text the option for the row being entered
     */
    public void addNewRow(String text, boolean isCorrectAnswer) {
        addNewRow();
        McqOptionInputRow row = rows.get(rows.size() - 1);
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

    /**
     * Determines whether the user has entered at least one option.
     * @return true if there is at least one filled row in the McqOptionInputRow and false otherwise.
     */
    public boolean hasAtLeastOneNonEmptyOption() {
        boolean hasOptions = false;
        for (McqOptionInputRow row : rows) {
            if (!row.isBlank()) {
                hasOptions = true;
            }
        }
        return hasOptions;
    }

    /**
     * Determines whether the user has chosen an option as the right answer.
     * @return true if the user has toggled a radio button to designate an option as the right answer,
     * and false otherwise.
     */
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
        for (McqOptionInputRow row : rows) {
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
