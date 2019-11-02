package seedu.address.ui;

import java.util.ArrayList;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenerateProfileCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.VisitList;
import seedu.address.model.person.VisitReport;
import seedu.address.model.tag.Tag;

/**
 * Panel containing detailed information of the specified Person including
 * the usual details on PersonCard, and also associated Visit information.
 */
public class ProfileWindow extends UiPart<Stage> {
    private static final String FXML = "ProfileWindow.fxml";
    private static final String line = "==================================================================\n";

    private Logic logic;

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
    private TextArea visitField;

    @FXML
    private Label message;


    public ProfileWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ProfilePanel.
     */
    public ProfileWindow() {
        this(new Stage());
        this.getRoot().initModality(Modality.APPLICATION_MODAL);
        /*
         * Using default window instead.
        this.getRoot().initStyle(StageStyle.UTILITY);
         */
    }

    /**
     * Initializes the Profile Window with the particulars from the Person instance.
     * @param person Person instance to show in the Profile Window
     */
    public void setup(Person person, Logic logic) {
        this.logic = logic;

        nameField.setText(stringifyName(person.getName()));
        tagField.setText(stringifyTags(person.getTags()));
        phoneField.setText(stringifyPhone(person.getPhone()));
        emailField.setText(stringifyEmail(person.getEmail()));
        addressField.setText(stringifyAddress(person.getAddress()));
        visitField.setText(stringifyVisit(person.getVisitList()));
    }

    /**
     * @param name Name attribute of the Person in called in setup
     * @return a String representing the Person's Name attribute
     */
    private String stringifyName(Name name) {
        return name.fullName;
    }

    /**
     * @param tagSet Set of Tag objects attributed to the Person instance called in setup
     * @return a String representing all Tags in the Person's Tag attribute
     */
    private String stringifyTags(Set<Tag> tagSet) {
        StringBuilder sb = new StringBuilder();

        if (tagSet.size() > 0) {
            for (Tag tag : tagSet) {
                sb.append(tag.tagName);
                sb.append("; ");
            }
        }

        return sb.toString();
    }

    /**
     * @param phone Phone attribute of the Person in called in setup
     * @return a String representing the Person's Phone attribute
     */
    private String stringifyPhone(Phone phone) {
        return phone.value;
    }

    /**
     * @param email Email attribute of the Person in called in setup
     * @return a String representing the Person's Email attribute
     */
    private String stringifyEmail(Email email) {
        return email.value;
    }

    /**
     * @param address Phone attribute of the Person in called in setup
     * @return a String representing the Person's Phone attribute
     */
    private String stringifyAddress(Address address) {
        return address.value;
    }

    /**
     * @param visitList
     * @return
     */
    private String stringifyVisit(VisitList visitList) {
        if (visitList == null) {
            return "-";
        }

        ArrayList<VisitReport> visits = visitList.getRecords();

        if (visits.size() == 0) {
            return "-";
        }

        StringBuilder output = new StringBuilder();

        for (VisitReport visit : visits) {
            output.append(stringifyVisitReport(visit));
            output.append(line);
            output.append("\n");
        }

        return output.toString();
    }

    /**
     * @param report
     * @return
     */
    private String stringifyVisitReport(VisitReport report) {
        String date = report.date;
        String diagnosis = report.getDiagnosis();
        String medication = report.getMedication();
        String remarks = report.getRemarks();

        StringBuilder output = new StringBuilder();

        // [Report on the XX/XX/2XXX]
        output.append("[ Report on the " + date + "]\n\n");

        // *Diagnosis*: DIAGNOSIS
        output.append("*Diagnosis*:\n" + diagnosis + "\n\n");

        // *Medication prescribed*: MEDICATION
        output.append("*Medication prescribed*:\n" + medication + "\n\n");

        // *Remarks*: REMARKS
        output.append("*Remarks*:\n" + remarks + "\n\n");

        return output.toString();
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
        getRoot().show();
        getRoot().centerOnScreen();

        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                getRoot().hide();
            } else if (KeyCode.Q == event.getCode()) {
                getRoot().hide();
            } else if (KeyCode.P == event.getCode()) {
                try {
                    generateProfilePressed(new ActionEvent());
                } catch (CommandException e) {
                    // Action fails
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
                phoneField.getText(), emailField.getText(), addressField.getText(), visitField.getText());
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
