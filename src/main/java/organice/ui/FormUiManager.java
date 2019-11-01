package organice.ui;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.LinkedList;
import java.util.logging.Logger;

import organice.logic.commands.AddCommand;
import organice.logic.commands.CommandResult;
import organice.logic.commands.ExitCommand;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.FormField;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * The manager of the Form UI component.
 */
public class FormUiManager {

    private static final String COMMAND_ABORT = "/abort";
    private static final String COMMAND_EXIT = "/exit";
    private static final String COMMAND_DONE = "/done";
    private static final String COMMAND_UNDO = "/undo";

    private static final String PROMPT_NAME = "Enter name:\n";
    private static final String PROMPT_NRIC = "Enter NRIC:\n";
    private static final String PROMPT_PHONE = "Enter phone number:\n";
    private static final String PROMPT_AGE = "Enter age:\n";
    private static final String PROMPT_ORGAN = "Enter organ:\n";
    private static final String PROMPT_BLOOD_TYPE = "Enter blood type:\n";
    private static final String PROMPT_TISSUE_TYPE = "Enter tissue type:\n";
    private static final String PROMPT_PRIORITY = "Enter priority:\n";
    private static final String PROMPT_DOCTOR_IC = "Enter doctor in charge's nric:\n";
    private static final String PROMPT_ORGAN_EXPIRY_DATE = "Enter organ expiry date:\n";
    private static final String PROMPT_DONE = "Please ensure you have typed in the correct details."
            + "\nType '/done' to confirm OR '/abort to cancel the add command";

    private static final String MESSAGE_ABORT = "Form successfully aborted!";
    private static final String MESSAGE_UNDO_SUCCESS = "Successfully undo the previous entry!";
    private static final String MESSAGE_UNDO_ERROR = "You can't undo at this stage!";

    private MainWindow mainWindow;
    private Type formType;
    private Model model;
    private Logger logger;

    // For undo feature purpose
    private LinkedList<String> history = new LinkedList<>();
    private int currentState = -1;


    public FormUiManager(MainWindow mainWindow, Type formType, Model model, Logger logger) {
        this.mainWindow = mainWindow;
        this.formType = formType;
        this.model = model;
        this.logger = logger;
    }

    private CommandResult getName(String personName) throws ParseException {
        personName = personName.trim();

        if (isSpecialCommand(personName)) {
            handleSpecialCommand(personName);
            return new CommandResult(personName);
        }

        if (!Name.isValidName(personName)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Name.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Name entered is not valid");
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personName, FormField.NAME);
        getPersonField(new CommandBox(this::getNric), PROMPT_NRIC + Nric.MESSAGE_CONSTRAINTS);

        return new CommandResult(personName);
    }

    private CommandResult getNric(String personNric) throws ParseException {
        assert !mainWindow.getForm().getName().getText().equals("");
        personNric = personNric.trim();

        if (isSpecialCommand(personNric)) {
            handleSpecialCommand(personNric);
            return new CommandResult(personNric);
        }

        if (!Nric.isValidNric(personNric)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Nric.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] NRIC entered is not valid");
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        } else if (model.hasPerson(new Nric(personNric))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DUPLICATE_PERSON);
            logger.warning("[ADD COMMAND FORM MODE] Person exists in ORGANice");
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }

        successFillingField(personNric, FormField.NRIC);
        getPersonField(new CommandBox(this::getPhone), PROMPT_PHONE + Phone.MESSAGE_CONSTRAINTS);

        return new CommandResult(personNric);
    }

    private CommandResult getPhone(String personPhone) throws ParseException {
        assert !mainWindow.getForm().getNric().getText().equals("");

        personPhone = personPhone.trim();

        if (isSpecialCommand(personPhone)) {
            handleSpecialCommand(personPhone);
            return new CommandResult(personPhone);
        }

        if (!Phone.isValidPhone(personPhone)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Phone.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Phone entered is not valid");
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personPhone, FormField.PHONE);

        if (formType.isDoctor()) {
            confirmPersonDetails();
        } else {
            getPersonField(new CommandBox(this::getAge), PROMPT_AGE + Age.MESSAGE_CONSTRAINTS);
        }

        return new CommandResult(personPhone);
    }

    private CommandResult getAge(String personAge) throws ParseException {
        assert !mainWindow.getForm().getNric().getText().equals("");

        personAge = personAge.trim();

        if (isSpecialCommand(personAge)) {
            handleSpecialCommand(personAge);
            return new CommandResult(personAge);
        }

        if (!Age.isValidAge(personAge)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Age.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Age entered is not valid");
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personAge, FormField.AGE);
        getPersonField(new CommandBox(this::getOrgan), PROMPT_ORGAN + Organ.MESSAGE_CONSTRAINTS);

        return new CommandResult(personAge);
    }

    private CommandResult getOrgan(String personOrgan) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isPatient()) {
            assert !((PatientForm) mainWindow.getForm()).getAge().getText().equals("");
        } else if (formType.isDonor()) {
            assert !((DonorForm) mainWindow.getForm()).getAge().getText().equals("");
        }

        personOrgan = personOrgan.trim();

        if (isSpecialCommand(personOrgan)) {
            handleSpecialCommand(personOrgan);
            return new CommandResult(personOrgan);
        }

        if (!Organ.isValidOrgan(personOrgan)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Organ.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Organ entered is not valid");
            throw new ParseException(Organ.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personOrgan, FormField.ORGAN);
        getPersonField(new CommandBox(this::getBloodType), PROMPT_BLOOD_TYPE + BloodType.MESSAGE_CONSTRAINTS);

        return new CommandResult(personOrgan);
    }

    private CommandResult getBloodType(String personBloodType) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isPatient()) {
            assert !((PatientForm) mainWindow.getForm()).getOrgan().getText().equals("");
        } else if (formType.isDonor()) {
            assert !((DonorForm) mainWindow.getForm()).getOrgan().getText().equals("");
        }

        personBloodType = personBloodType.trim();

        if (isSpecialCommand(personBloodType)) {
            handleSpecialCommand(personBloodType);
            return new CommandResult(personBloodType);
        }

        if (!BloodType.isValidBloodType(personBloodType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(BloodType.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Blood Type entered is not valid");
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personBloodType, FormField.BLOOD_TYPE);
        getPersonField(new CommandBox(this::getTissueType), PROMPT_TISSUE_TYPE + TissueType.MESSAGE_CONSTRAINTS);

        return new CommandResult(personBloodType);
    }

    private CommandResult getTissueType(String personTissueType) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isPatient()) {
            assert !((PatientForm) mainWindow.getForm()).getBloodType().getText().equals("");
        } else if (formType.isDonor()) {
            assert !((DonorForm) mainWindow.getForm()).getBloodType().getText().equals("");
        }

        personTissueType = personTissueType.trim();

        if (isSpecialCommand(personTissueType)) {
            handleSpecialCommand(personTissueType);
            return new CommandResult(personTissueType);
        }

        if (!TissueType.isValidTissueType(personTissueType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(TissueType.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Tissue Type entered is not valid");
            throw new ParseException(TissueType.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personTissueType, FormField.TISSUE_TYPE);

        if (formType.isPatient()) {
            getPersonField(new CommandBox(this::getPriority), PROMPT_PRIORITY + Priority.MESSAGE_CONSTRAINTS);
        } else if (formType.isDonor()) {
            getPersonField(new CommandBox(this::getOrganExpiryDate),
                    PROMPT_ORGAN_EXPIRY_DATE + OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }

        return new CommandResult(personTissueType);
    }

    private CommandResult getPriority(String personPriority) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isPatient()) {
            assert !((PatientForm) mainWindow.getForm()).getTissueType().getText().equals("");
        }

        personPriority = personPriority.trim();

        if (isSpecialCommand(personPriority)) {
            handleSpecialCommand(personPriority);
            return new CommandResult(personPriority);
        }

        if (!Priority.isValidPriority(personPriority)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Priority.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Priority entered is not valid");
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personPriority, FormField.PRIORITY);

        if (formType.isPatient()) {
            getPersonField(new CommandBox(this::getDoctorIc), PROMPT_DOCTOR_IC + DoctorInCharge.MESSAGE_CONSTRAINTS);
        }

        return new CommandResult(personPriority);
    }

    private CommandResult getDoctorIc(String personDoctorIc) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isPatient()) {
            assert !((PatientForm) mainWindow.getForm()).getPriority().getText().equals("");
        }

        personDoctorIc = personDoctorIc.trim();

        if (isSpecialCommand(personDoctorIc)) {
            handleSpecialCommand(personDoctorIc);
            return new CommandResult(personDoctorIc);
        }

        if (!DoctorInCharge.isValidDoctorInCharge(personDoctorIc)) {
            mainWindow.getResultDisplay().setFeedbackToUser(DoctorInCharge.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Doctor's NRIC entered is not valid");
            throw new ParseException(DoctorInCharge.MESSAGE_CONSTRAINTS);
        } else if (!model.hasDoctor(new Nric(personDoctorIc))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
            logger.warning("[ADD COMMAND FORM MODE] Doctor is not found in ORGANice");
            throw new ParseException(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
        }

        successFillingField(personDoctorIc, FormField.DOCTOR_IC);

        if (formType.isPatient()) {
            confirmPersonDetails();
        }

        return new CommandResult(personDoctorIc);
    }

    private CommandResult getOrganExpiryDate(String personOrganExpiryDate) throws ParseException {
        Type formType = mainWindow.getForm().getType();
        if (formType.isDonor()) {
            assert !((PatientForm) mainWindow.getForm()).getTissueType().getText().equals("");
        }

        personOrganExpiryDate = personOrganExpiryDate.trim();

        if (isSpecialCommand(personOrganExpiryDate)) {
            handleSpecialCommand(personOrganExpiryDate);
            return new CommandResult(personOrganExpiryDate);
        }

        if (!OrganExpiryDate.isValidExpiryDate(personOrganExpiryDate)) {
            mainWindow.getResultDisplay().setFeedbackToUser(OrganExpiryDate.MESSAGE_CONSTRAINTS);
            logger.warning("[ADD COMMAND FORM MODE] Organ Expiry Date entered is not valid");
            throw new ParseException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }

        successFillingField(personOrganExpiryDate, FormField.ORGAN_EXPIRY_DATE);

        if (formType.isDonor()) {
            confirmPersonDetails();
        }

        return new CommandResult(personOrganExpiryDate);
    }

    private void getPersonField(CommandBox commandBox, String prompt) {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(commandBox.getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(prompt);
    }

    public void getPersonDetails() {
        getPersonField(new CommandBox(this::getName), PROMPT_NAME + Name.MESSAGE_CONSTRAINTS);
    }

    private void confirmPersonDetails() {
        getPersonField(new CommandBox(this::setPersonDetails), PROMPT_DONE);
    }

    private CommandResult setPersonDetails(String commandText) throws ParseException, CommandException {
        commandText = commandText.trim();

        if (isSpecialCommand(commandText)) {
            handleSpecialCommand(commandText);
            return new CommandResult(commandText);
        } else if (commandText.equals(COMMAND_DONE)) {
            ResultDisplay resultDisplay = mainWindow.getResultDisplay();
            CommandResult commandResult = null;

            if (formType.isDoctor()) {
                commandResult = addDoctorToList();
            } else if (formType.isDonor()) {
                commandResult = addDonorToList();
            } else if (formType.isPatient()) {
                commandResult = addPatientToList();
            }

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            FormAnimation.fadingAnimation(mainWindow);
        }

        // Reset the UI display to the initial state
        mainWindow.resetInnerParts();

        return new CommandResult(commandText);
    }

    /**
     * Adds the given {@code Doctor} to the ORGANice list.
     */
    private CommandResult addDoctorToList() throws ParseException, CommandException {
        DoctorForm form = (DoctorForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.DOCTOR + " " + PREFIX_NAME
                + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
                + PREFIX_PHONE + form.getPhone().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    /**
     * Adds {@code Donor} to the ORGANice list.
     */
    private CommandResult addDonorToList() throws ParseException, CommandException {
        DonorForm form = (DonorForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.DONOR + " " + PREFIX_NAME
                + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
                + PREFIX_PHONE + form.getPhone().getText() + " " + PREFIX_AGE + form.getAge().getText() + " "
                + PREFIX_ORGAN + form.getOrgan().getText() + " " + PREFIX_BLOOD_TYPE
                + form.getBloodType().getText() + " "
                + PREFIX_TISSUE_TYPE + form.getTissueType().getText() + " "
                + PREFIX_ORGAN_EXPIRY_DATE + form.getOrganExpiryDate().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    /**
     * Adds {@code Patient} to the ORGANice list.
     */
    private CommandResult addPatientToList() throws ParseException, CommandException {
        PatientForm form = (PatientForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.PATIENT + " " + PREFIX_NAME
                + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
                + PREFIX_PHONE + form.getPhone().getText() + " " + PREFIX_AGE + form.getAge().getText() + " "
                + PREFIX_ORGAN + form.getOrgan().getText() + " " + PREFIX_BLOOD_TYPE
                + form.getBloodType().getText() + " " + PREFIX_TISSUE_TYPE + form.getTissueType().getText()
                + " " + PREFIX_PRIORITY + form.getPriority().getText() + " " + PREFIX_DOCTOR_IN_CHARGE
                + form.getDoctorIc().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    /**
     * Indicates that the user has successfully set a particular attribute with a value, it will set the user's input
     * into the form automatically as well as increase the progress bar and move the current state of the process.
     *
     * @param fieldValue Value of the attribute entered by the user.
     * @param formField A particular field in the form that the user specifed.
     */
    private void successFillingField(String fieldValue, String formField) {
        mainWindow.getForm().increaseProgress();
        currentState++;
        history.add(currentState, formField);
        FormAnimation.typingAnimation(mainWindow, fieldValue, formField);
        logger.info(String.format("----------------[USER INPUT][%s: %s]", formField.toUpperCase(), fieldValue));
    }

    /**
     * Returns true if the command entered is a special command.
     *
     * @param commandText Command entered by the user.
     */
    private boolean isSpecialCommand(String commandText) {
        return commandText.equals(COMMAND_UNDO) || commandText.equals(COMMAND_ABORT)
                || commandText.equals(COMMAND_EXIT);
    }

    /**
     * Handles special command entered by the user.
     *
     * @param commandText Command entered by the user
     */
    private void handleSpecialCommand(String commandText) throws ParseException {
        try {
            if (commandText.equals(COMMAND_ABORT)) {
                handleAbort();
            } else if (commandText.equals(COMMAND_UNDO)) {
                handleUndo();
            } else if (commandText.equals(COMMAND_EXIT)) {
                mainWindow.executeCommand(ExitCommand.COMMAND_WORD);
            }
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    private void handleAbort() {
        FormAnimation.fadingAnimation(mainWindow);
        mainWindow.getResultDisplay().setFeedbackToUser(MESSAGE_ABORT);
        mainWindow.resetInnerParts();
    }

    /**
     * Handles undo feature of the process. It will move a pointer back to the previous state and delete the latest
     * entry that the user specify.
     */
    private void handleUndo() {
        if (currentState == -1) {
            mainWindow.getResultDisplay().setFeedbackToUser(MESSAGE_UNDO_ERROR);
        } else {
            Type formType = mainWindow.getForm().getType();
            String currentField = history.get(currentState);
            currentState--;
            switch (currentField) {

            case FormField.NAME:
                mainWindow.getForm().setName("");
                getPersonField(new CommandBox(this::getName), PROMPT_NAME);
                break;

            case FormField.NRIC:
                mainWindow.getForm().getNric().setText("");
                getPersonField(new CommandBox(this::getNric), PROMPT_NRIC);
                break;

            case FormField.PHONE:
                mainWindow.getForm().getPhone().setText("");
                getPersonField(new CommandBox(this::getPhone), PROMPT_PHONE);
                break;
            //CHECKSTYLE.OFF: SeparatorWrap - Type casting causes checkstyle to ask for incorrect wrapping
            case FormField.AGE:
                if (formType.isPatient()) {
                    ((PatientForm) mainWindow.getForm()).getAge().setText("");
                } else if (formType.isDonor()) {
                    ((DonorForm) mainWindow.getForm()).getAge().setText("");
                }
                getPersonField(new CommandBox(this::getAge), PROMPT_AGE);
                break;

            case FormField.ORGAN:
                if (formType.isPatient()) {
                    ((PatientForm) mainWindow.getForm()).getOrgan().setText("");
                } else if (formType.isDonor()) {
                    ((DonorForm) mainWindow.getForm()).getOrgan().setText("");
                }
                getPersonField(new CommandBox(this::getOrgan), PROMPT_ORGAN);
                break;

            case FormField.BLOOD_TYPE:
                if (formType.isPatient()) {
                    ((PatientForm) mainWindow.getForm()).getBloodType().setText("");
                } else if (formType.isDonor()) {
                    ((DonorForm) mainWindow.getForm()).getBloodType().setText("");
                }
                getPersonField(new CommandBox(this::getBloodType), PROMPT_BLOOD_TYPE);
                break;

            case FormField.TISSUE_TYPE:
                if (formType.isPatient()) {
                    ((PatientForm) mainWindow.getForm()).getTissueType().setText("");
                } else if (formType.isDonor()) {
                    ((DonorForm) mainWindow.getForm()).getTissueType().setText("");
                }
                getPersonField(new CommandBox(this::getTissueType), PROMPT_TISSUE_TYPE);
                break;

            case FormField.PRIORITY:
                ((PatientForm) mainWindow.getForm()).getPriority().setText("");
                getPersonField(new CommandBox(this::getPriority), PROMPT_PRIORITY);
                break;

            case FormField.DOCTOR_IC:
                ((PatientForm) mainWindow.getForm()).getDoctorIc().setText("");
                getPersonField(new CommandBox(this::getDoctorIc), FormField.DOCTOR_IC);
                break;

            case FormField.ORGAN_EXPIRY_DATE:
                ((DonorForm) mainWindow.getForm()).getOrganExpiryDate().setText("");
                getPersonField(new CommandBox(this::getOrganExpiryDate), FormField.ORGAN_EXPIRY_DATE);
                break;

            default:
                break;
            }
            //CHECKSTYLE.ON: SeparatorWrap

            mainWindow.getForm().decreaseProgress();
            mainWindow.getResultDisplay().setFeedbackToUser(MESSAGE_UNDO_SUCCESS);
        }
    }
}
