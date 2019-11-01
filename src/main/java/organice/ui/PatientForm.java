package organice.ui;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.logic.commands.AddCommand;
import organice.logic.commands.MatchCommand;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Type;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class PatientForm extends UiPart<Region> implements Form {

    private static final String FXML = "PatientForm.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label nric;
    @FXML
    private Label age;
    @FXML
    private Label organ;
    @FXML
    private Label bloodType;
    @FXML
    private Label tissueType;
    @FXML
    private Label priority;
    @FXML
    private Label doctorIc;
    @FXML
    private Label progressPercentage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button findMatch;

    private MainWindow mainWindow;
    private final int numberOfFields = 9;
    private int filledFields = 0;

    public PatientForm(MainWindow mainWindow) {
        super(FXML);
        name.setText("");
        phone.setText("");
        nric.setText("");
        age.setText("");
        organ.setText("");
        bloodType.setText("");
        tissueType.setText("");
        priority.setText("");
        doctorIc.setText("");
        this.mainWindow = mainWindow;
    }

    @Override
    public Label getName() {
        return name;
    }

    @Override
    public Label getPhone() {
        return phone;
    }

    @Override
    public Type getType() {
        return new Type(Type.PATIENT);
    }

    @Override
    public Label getNric() {
        return nric;
    }

    public Label getAge() {
        return age;
    }

    public Label getOrgan() {
        return organ;
    }

    public Label getBloodType() {
        return bloodType;
    }

    public Label getTissueType() {
        return tissueType;
    }

    public Label getPriority() {
        return priority;
    }

    public Label getDoctorIc() {
        return doctorIc;
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setNric(String nric) {
        this.nric.setText(nric);
    }

    @Override
    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    @Override
    public void increaseProgress() {
        filledFields++;

        //Button is visible if all fields are filled
        if (filledFields == numberOfFields) {
            FormAnimation.showButtonAnimation(findMatch);
            findMatch.setVisible(true);
        }

        double currentProgress = (double) filledFields / numberOfFields;
        FormAnimation.percentageChangeAnimation(currentProgress,
            String.format("%.1f", currentProgress * 100), progressPercentage, progressBar);
    }

    @Override
    public void decreaseProgress() {
        filledFields--;

        //Button is visible if all fields are filled
        if (filledFields == numberOfFields) {
            FormAnimation.showButtonAnimation(findMatch);
            findMatch.setVisible(true);
        } else {
            findMatch.setVisible(false);
        }

        double currentProgress = (double) filledFields / numberOfFields;
        FormAnimation.percentageChangeAnimation(currentProgress,
            String.format("%.1f", currentProgress * 100), progressPercentage, progressBar);
    }

    /**
     * Handles the action when the 'Find Match' button in the Form UI is clicked. It will execute the match command.
     */
    @FXML
    public void onHandleClick() throws ParseException, CommandException {
        String matchCommand = MatchCommand.COMMAND_WORD + " " + PREFIX_NRIC + nric.getText();
        String addCommand = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.PATIENT + " " + PREFIX_NAME
            + name.getText() + " " + PREFIX_NRIC + nric.getText() + " "
            + PREFIX_PHONE + phone.getText() + " " + PREFIX_AGE + age.getText() + " "
            + PREFIX_ORGAN + organ.getText() + " " + PREFIX_BLOOD_TYPE + bloodType.getText() + " "
            + PREFIX_TISSUE_TYPE + tissueType.getText() + " " + PREFIX_PRIORITY + priority.getText()
            + " " + PREFIX_DOCTOR_IN_CHARGE + doctorIc.getText();

        mainWindow.getLogic().execute(addCommand);
        FormAnimation.fadingAnimation(mainWindow);
        mainWindow.executeCommand(matchCommand);
        mainWindow.getCommandBoxPlaceholder().getChildren().add(mainWindow.getCommandBox().getRoot());
        mainWindow.getCommandBox().requestFocus();
        mainWindow.getResultDisplayPlaceholder().setMinHeight(200);
    }

    public void setAge(String age) {
        this.age.setText(age);
    }

    public void setOrgan(String organ) {
        this.organ.setText(organ);
    }

    public void setBloodType(String bloodType) {
        this.bloodType.setText(bloodType);
    }

    public void setTissueType(String tissueType) {
        this.tissueType.setText(tissueType);
    }

    public void setPriority(String priority) {
        this.priority.setText(priority);
    }

    public void setDoctorIc(String doctorIc) {
        this.doctorIc.setText(doctorIc);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoctorCard)) {
            return false;
        }

        // state check
        PatientForm form = (PatientForm) other;
        return name.getText().equals(form.name.getText())
            && phone.equals(form.phone.getText())
            && nric.equals(form.nric.getText())
            && age.equals(form.age.getText())
            && organ.equals(form.organ.getText())
            && bloodType.equals(form.bloodType.getText())
            && tissueType.equals(form.tissueType.getText())
            && priority.equals(form.priority.getText())
            && doctorIc.equals(form.doctorIc.getText());
    }
}
