package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.stage.Window;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SaveVisitCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.logging.Logger;

public class VisitRecordWindow extends UiPart<Stage> {
    @FXML
    private TextArea medicine;

    @FXML
    private TextArea diagnosis;

    @FXML
    private TextArea remarks;

    @FXML
    private Button saveButton;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "VisitRecordForm.fxml";
    private int index;
    private String date;
    private Logic logic;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */

    public VisitRecordWindow(Stage root) {
        super(FXML, root);
        setup();
    }

    /**
     * Creates a new HelpWindow.
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

    @FXML
    protected void saveReport(ActionEvent event) throws CommandException {
        SaveVisitCommand save = new SaveVisitCommand(index, date, medicine.getText(), diagnosis.getText(), remarks.getText());
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
        if(button==null) {
            System.out.println("Button null!!");
        }
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException("setSaveAccelerator must be called when a button is attached to a scene");
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

