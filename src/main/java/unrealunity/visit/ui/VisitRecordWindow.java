package unrealunity.visit.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import unrealunity.visit.commons.core.LogsCenter;
import unrealunity.visit.logic.Logic;
import unrealunity.visit.logic.commands.CommandResult;
import unrealunity.visit.logic.commands.SaveVisitCommand;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.person.VisitReport;


/**
 * Creates a new Form for user to enter visitation details.
 *
 */
public class VisitRecordWindow extends UiPart<Stage> {

    private static final String FXML = "VisitRecordForm.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final int INVALID_REPORT_INDEX = -1;

    @FXML
    private TextArea medicine;

    @FXML
    private TextArea diagnosis;

    @FXML
    private TextArea remarks;

    @FXML
    private Button saveButton;

    private int index;
    private int reportIdx;
    private String date;
    private String msg = "";
    private Logic logic;


    /**
     * Creates a new VisitRecordWindow.
     *
     * @param root Stage to use as the root of the VisitRecordWindow.
     */

    public VisitRecordWindow(Stage root, EventHandler<WindowEvent> e) {
        super(FXML, root);
        root.setOnHidden(e);
        setup();
    }

    /**
     * Creates a new VisitRecordWindow.
     */
    public VisitRecordWindow(EventHandler<WindowEvent> e) {
        this(new Stage(), e);
    }

    /**
     * Shows the window.
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
        logger.fine("Showing Visit Record Window.");
        getRoot().show();
        getRoot().centerOnScreen();
        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                this.hide();
            }
        });
    }

    /**
     * Returns true if the window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Clears all text in textareas.
     */
    public void clearFields() {
        medicine.clear();
        diagnosis.clear();
        remarks.clear();
    }

    /**
     * Focuses on the window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Saves report and closes window
     */
    @FXML
    protected void saveReport(ActionEvent event) throws CommandException {

        SaveVisitCommand save = new SaveVisitCommand(index, reportIdx, date, medicine.getText(),
                    diagnosis.getText(), remarks.getText());

        CommandResult commandResult = logic.execute(save);
        this.msg = commandResult.getFeedbackToUser();
        this.hide();
        this.msg = "";

    }


    public void setReportInfo(int idx, String date, Logic logic) {
        this.index = idx;
        this.reportIdx = INVALID_REPORT_INDEX;
        this.date = date;
        this.logic = logic;
    }

    public void setOldReportInfo(int idx, int reportIdx, VisitReport report, Logic logic) {
        this.index = idx;
        this.reportIdx = reportIdx;
        this.date = report.date;
        this.logic = logic;

        medicine.setText(report.getMedication());
        diagnosis.setText(report.getDiagnosis());
        remarks.setText(report.getRemarks());
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

    public String getMessage() {
        return this.msg;
    }


}

