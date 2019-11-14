package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;

import organice.commons.core.LogsCenter;
import organice.logic.parser.MatchCommandParser;
import organice.model.Model;
import organice.model.person.BloodType;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.TissueType;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Matches patient and donor in ORGANice.
 */
public class MatchCommand extends Command {
    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Runs a matching algorithm on patients and donors to"
            + " identify patient-donor pairs that passes the organ matching tests. There are two types of"
            + " match commands:"
            + "\n\nTo find number of matching donors for every patient in ORGANice,"
            + " type in the following:\nic/all"
            + "\n\nTo get a list of all donors that match the patient with the specified NRIC, the"
            + " command format is as follows:\nic/PATIENT_NRIC";

    public static final String MESSAGE_SUCCESS = "Matched all patients and donors";
    public static final String MESSAGE_SUCCESS_MATCH_PATIENT = "Found %d matching donors for patient: %s(%s)";
    public static final String MESSAGE_NO_MATCHES = "Found no matching donors for patient: %s(%s)";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No patients with NRIC: %s found in ORGANice";

    public static final Double SUCCESSFUL_PERCENTAGE = 60.0;

    private static final Logger logger = LogsCenter.getLogger(MatchCommand.class);

    private String input;
    private Patient patient;

    public MatchCommand(String input) {
        logger.info("Input to MatchCommand: " + input);
        requireNonNull(input);
        this.input = input;
    }

    /**
     * Returns true if the donor and patient is a match.
     * A match happens when the donor and patient's blood type and tissue types are compatible.
     * @param donor {@code Donor} who will tested for suitability of donation
     * @param patient {@code Patient} in need of organ
     */
    public static boolean match(Person donor, Patient patient) {
        if (!(donor instanceof Donor)) {
            return false;
        }

        ArrayList patientsNotForMatching = ((Donor) donor).getPatientMatchedBefore();

        if (patientsNotForMatching.contains(patient.getNric())) {
            return false;
        }

        BloodType donorBloodType = ((Donor) donor).getBloodType();
        BloodType patientBloodType = patient.getBloodType();

        TissueType donorTissueType = ((Donor) donor).getTissueType();
        TissueType patientTissueType = patient.getTissueType();

        Double successRate = donorTissueType.getPercentageMatch(patientTissueType);

        if (patientBloodType.isCompatibleBloodType(donorBloodType) && successRate >= SUCCESSFUL_PERCENTAGE) {
            Donor donorToSet = (Donor) donor;
            donorToSet.addMatchResult(patient.getNric(), successRate);
            donorToSet.setSuccessRate(patient.getNric());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Matches all {@code Patient} to all {@code Donor} in ORGANice.
     */
    private CommandResult executeMatchAll(Model model) {
        model.removeMatches();
        model.matchAllPatients();
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        logger.info("Finished matching all patients and all donors in ORGANice!");
        return commandResult;
    }

    /**
     * Matches all {@code Donor} to a {@code Patient} with the specified {@code Nric}.
     */
    private CommandResult executeMatchNric(Nric patientNric, Model model) {
        try {
            if (model.hasPatient(patientNric)) {
                model.removeMatches();

                patient = model.getPatient(patientNric);
                model.matchDonors(patient);


                CommandResult commandResult;
                int numberOfMatches = model.numberOfMatches();
                if (numberOfMatches > 0) {
                    commandResult = new CommandResult(String.format(MESSAGE_SUCCESS_MATCH_PATIENT,
                            numberOfMatches, patient.getName().toString(), patientNric.toString()));
                } else {
                    commandResult = new CommandResult(String.format(MESSAGE_NO_MATCHES, patient.getName(),
                            patientNric.toString()));
                }

                logger.info(String.format("Finished matching Patient of NRIC: %s with number of matches: %d",
                        patientNric.toString(), numberOfMatches));
                return commandResult;
            } else {
                logger.info(String.format("Patient of NRIC: %s not found in ORGANice!",
                        patientNric.toString()));
                return new CommandResult(String.format(MESSAGE_PERSON_NOT_FOUND, patientNric));
            }
        } catch (PersonNotFoundException pne) {
            return new CommandResult(String.format(MESSAGE_PERSON_NOT_FOUND, patientNric));
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (input.equals(MatchCommandParser.ALL)) {
            return executeMatchAll(model);
        } else {
            Nric patientNric = new Nric(input);
            return executeMatchNric(patientNric, model);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && input.equals(((MatchCommand) other).input)); // state check
    }
}
