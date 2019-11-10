package unrealunity.visit.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unrealunity.visit.commons.core.LogsCenter;
import unrealunity.visit.commons.util.ProfileUtil;
import unrealunity.visit.logic.Logic;
import unrealunity.visit.logic.commands.CommandResult;
import unrealunity.visit.logic.commands.GenerateProfileCommand;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.VisitReport;


/**
 * Panel containing detailed information of the specified {@code Person} including
 * the usual details on {@code PersonCard}, and also associated {@code VisitReport} information.
 */
public class ProfileWindow extends UiPart<Stage> {
    private static final String FXML = "ProfileWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(ProfileWindow.class);

    private Logic logic;
    private Person person;

    @FXML
    private TextArea nameField;

    @FXML
    private TextArea tagField;

    @FXML
    private TextArea phoneField;

    @FXML
    private TextArea emailField;

    @FXML
    private TextArea addressField;

    @FXML
    private ListView<VisitReport> profileVisitList;

    @FXML
    private Label message;


    public ProfileWindow(Stage root) {
        super(FXML, root);
        populateVisitList(FXCollections.observableArrayList());
    }

    /**
     * Creates a new {@code ProfilePanel}.
     */
    public ProfileWindow() {
        this(new Stage());

        /*
         * [IMPORTANT]
         * Makes Profile Window monopolize the application focus.
         * Fixes previous concurrency issues when modifying Person while Profile Window is open.
         */
        this.getRoot().initModality(Modality.APPLICATION_MODAL);

        // Add handlers to ProfileWindow for user actions.
        // (esc, q - Exit), (p - Generate .txt for user Profile)
        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                logger.info("User pressed 'esc'. Closing Profile Panel..");
                getRoot().hide();
                logger.info("Profile Panel Closed.");
            } else if (KeyCode.Q == event.getCode()) {
                logger.info("User pressed 'q'. Closing Profile Panel.");
                getRoot().hide();
                logger.info("Profile Panel Closed.");
            } else if (KeyCode.P == event.getCode()) {
                try {
                    generateProfilePressed(new ActionEvent());
                } catch (CommandException e) {
                    logger.warning("Exception when generating Profile. Error: " + e.getMessage());
                    message.setText("Exception when generating Profile.");
                }
            }
        }
        );
    }

    /**
     * Initializes the Profile Window with the particulars from the Person instance.
     *
     * @param person Person instance to show in the Profile Window
     */
    public void setup(Person person, Logic logic) {
        this.logic = logic;
        this.person = person;

        // Set Person Particulars into relevant fields
        nameField.setText(ProfileUtil.stringifyName(person.getName()));
        tagField.setText(ProfileUtil.stringifyTags(person.getTags()));
        phoneField.setText(ProfileUtil.stringifyPhone(person.getPhone()));
        emailField.setText(ProfileUtil.stringifyEmail(person.getEmail()));
        addressField.setText(ProfileUtil.stringifyAddress(person.getAddress()));

        // Set Person Visits into ListView
        populateVisitList(person.getVisitList().getObservableRecords());
    }

    /**
     * Populates the ProfileWindow's ListView with the ProfileVisitListCells representing the VisitReport
     * instances contained within an ObservableList&lt;VisitReport&gt; instance.
     *
     * @param visitList ObservableList&lt;VisitReport&gt; instance containing the VisitReports to be
     *                  visualized.
     */
    public void populateVisitList(ObservableList<VisitReport> visitList) {
        profileVisitList.setItems(visitList);
        profileVisitList.setCellFactory(listView -> new ProfileVisitListCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code VisitReport} using a {@code ProfileVisitCard}.
     */
    class ProfileVisitListCell extends ListCell<VisitReport> {
        @Override
        protected void updateItem(VisitReport report, boolean empty) {
            super.updateItem(report, empty);

            if (empty || report == null) {
                setGraphic(null);
                setText(null);
            } else {
                ProfileVisitCard card = new ProfileVisitCard(report);
                setGraphic(card.getRoot());
            }
        }
    }

    /**
     * Shows the Profile Panel.
     *
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
     *         if {@code ProfileWindow} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.info("Showing Profile Panel");
        getRoot().show();
        getRoot().centerOnScreen();
        message.setText("");
    }

    /**
     * Returns true if the Profile panel is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Profile panel.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Profile panel.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Generates a {@code GenerateProfileCommand} instance with the details of the current for the current
     * Profile Window to generate the patient profile.
     *
     * @param event {@code ActionEvent} instance triggering the profile generation
     * @throws CommandException when the .txt file fails to write to the path
     */
    @FXML
    void generateProfilePressed(ActionEvent event) throws CommandException {
        logger.info("Generating Profile .txt..");
        GenerateProfileCommand generateProfile = new GenerateProfileCommand(person);
        try {
            CommandResult commandResult = logic.execute(generateProfile);
            message.setText("Profile .txt created in /generated_profiles/.");
            logger.info("Profile .txt generation successful.");
        } catch (CommandException e) {
            throw e;
        }
    }

    @FXML
    void mouseEnterExit(MouseEvent e) {
        Label exitLabel = (Label) e.getSource();
        exitLabel.setUnderline(true);
    }

    @FXML
    void mouseLeaveExit(MouseEvent e) {
        Label exitLabel = (Label) e.getSource();
        exitLabel.setUnderline(false);
    }

    @FXML
    void mouseClickExit(MouseEvent e) {
        getRoot().hide();
    }
}
