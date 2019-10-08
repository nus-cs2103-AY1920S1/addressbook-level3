package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.SaveVisitCommand;
import seedu.address.logic.commands.exceptions.CommandException;


/**
 * Creates a new Form for user to enter visitation details.
 *
 */
public class VisitRecordWindow extends UiPart<Stage> {

    private static final String FXML = "VisitRecordForm.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    @FXML
    private TextArea medicine;

    @FXML
    private TextArea diagnosis;

    @FXML
    private TextArea remarks;

    @FXML
    private Button saveButton;

    private int index;
    private String date;
    private Logic logic;


    /**
     * Creates a new VisitRecordWindow.
     *
     * @param root Stage to use as the root of the VisitRecordWindow.
     */

    public VisitRecordWindow(Stage root) {
        super(FXML, root);
        setup();
    }

    /**
     * Creates a new VisitRecordWindow.
     */
    public VisitRecordWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing report form.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Saves report and closes window
     */
    @FXML
    protected void saveReport(ActionEvent event) throws CommandException {
        SaveVisitCommand save = new SaveVisitCommand(index, date, medicine.getText(),
                diagnosis.getText(), remarks.getText());
        logic.execute(save);
        medicine.clear();
        diagnosis.clear();
        remarks.clear();
        this.hide();
    }


    public void setReportInfo(int idx, String date, Logic logic) {
        this.index = idx;
        this.date = date;
        this.logic = logic;
    }

    public void setup() {
        setSaveAccelerator(saveButton);
    }


    private void setSaveAccelerator(Button button) throws IllegalArgumentException {
        if (button == null) {
            System.out.println("Button null!!");
        }
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException("setSaveAccelerator must be called"
                    + "when a button is attached to a scene");
        }

        scene.getAccelerators().put(
                KeyCombination.valueOf("F2"),
                new Runnable() {
                    @FXML public void run() {

                        button.fire();
                    }
                }
        );
    }


}

