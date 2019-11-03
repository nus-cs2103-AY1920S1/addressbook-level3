package seedu.address.ui;

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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ProfileUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenerateProfileCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.VisitReport;


/**
 * Panel containing detailed information of the specified Person including
 * the usual details on PersonCard, and also associated Visit information.
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
     * Creates a new ProfilePanel.
     */
    public ProfileWindow() {
        this(new Stage());

        /*
         * [IMPORTANT]
         * Makes Profile Window monopolize the application focus.
         * Fixes previous concurrency issues when modifying Person while Profile Window is open.
         */
        this.getRoot().initModality(Modality.APPLICATION_MODAL);

        /*
         * Using default window instead. This removed the default control bar.
        this.getRoot().initStyle(StageStyle.UTILITY);
         */
    }

    /**
     * Initializes the Profile Window with the particulars from the Person instance.
     * @param person Person instance to show in the Profile Window
     */
    public void setup(Person person, Logic logic) {
        this.logic = logic;
        this.person = person;

        // Set Person Particulars
        nameField.setText(ProfileUtil.stringifyName(person.getName()));
        tagField.setText(ProfileUtil.stringifyTags(person.getTags()));
        phoneField.setText(ProfileUtil.stringifyPhone(person.getPhone()));
        emailField.setText(ProfileUtil.stringifyEmail(person.getEmail()));
        addressField.setText(ProfileUtil.stringifyAddress(person.getAddress()));
    }

    /**
     * Populates the ProfileWindow's ListView with the ProfileVisitListCells representing the VisitReport
     * instances contained within an ObservableList&lt;VisitReport&gt; instance.
     * @param visitList ObservableList&lt;VisitReport&gt; instance containing the VisitReports to be
     *                  visualized.
     */
    public void populateVisitList(ObservableList<VisitReport> visitList) {
        profileVisitList.setItems(visitList);
        profileVisitList.setCellFactory(listView -> new ProfileVisitListCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
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
        logger.info("Showing Profile Panel");
        getRoot().show();
        getRoot().centerOnScreen();

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
                    logger.info("User pressed 'p'. Generating Profile .pdf..");
                    generateProfilePressed(new ActionEvent());
                    logger.info("Profile .pdf generation successful.");
                } catch (CommandException e) {
                    logger.warning("Exception when generating Profile. Error: " + e.getMessage());
                }
            }
        }
        );
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
     * @param event
     * @throws CommandException
     */
    @FXML
    void generateProfilePressed(ActionEvent event) throws CommandException {
        GenerateProfileCommand generateProfile = new GenerateProfileCommand(nameField.getText(), tagField.getText(),
                phoneField.getText(), emailField.getText(), addressField.getText(),
                ProfileUtil.stringifyVisit(person.getVisitList()));
        try {
            CommandResult commandResult = logic.execute(generateProfile);
            message.setText("A log has been successfully created.");
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
