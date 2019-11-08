package organice.model.person;

import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a Donor in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Donor extends Person {
    //fields
    private final Age age;
    private final BloodType bloodType;
    private final TissueType tissueType;
    private final Organ organ;
    private final OrganExpiryDate organExpiryDate;
    private TaskList processingTodoList;
    private Status status;
    private HashMap<Nric, Double> successRateMap;
    private String successRate;
    private Nric patientNric;
    private ArrayList<Nric> patientsMatchedBefore;

    /**
     * Every field must be present and not null.
     */
    public Donor(Type type, Nric nric, Name name, Phone phone, Age age,
                 BloodType bloodType, TissueType tissueType, Organ organ, OrganExpiryDate organExpiryDate,
                 Status status) {
        super(type, nric, name, phone);
        requireAllNonNull(age, bloodType, tissueType, organ, organExpiryDate, status);
        this.age = age;
        this.bloodType = bloodType;
        this.tissueType = tissueType;
        this.organ = organ;
        this.organExpiryDate = organExpiryDate;
        processingTodoList = new TaskList("");
        this.processingTodoList = processingTodoList;
        this.status = status;
        successRateMap = new HashMap<>();
        patientsMatchedBefore = new ArrayList<>();
    }

    public Age getAge() {
        return age;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public TissueType getTissueType() {
        return tissueType;
    }

    public Organ getOrgan() {
        return organ;
    }

    public OrganExpiryDate getOrganExpiryDate() {
        return organExpiryDate;
    }

    public TaskList getProcessingList(Nric patientNric) {
        return processingTodoList;
    }

    public Nric getPatientNric() {
        return patientNric;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns a {@code String} detailing the success rate to be displayed in the {@code DonorCard}.
     */
    public String getSuccessRate() {
        return successRate;
    }

    /**
     * Sets the success rate of a match with the specified {@code Patient}.
     */
    public void setSuccessRate(Nric patientMatched) {
        Double successRate = successRateMap.get(patientMatched);

        if (successRate == null) {
            this.successRate = "";
        } else {
            this.successRate = String.format("%.2f%%", successRate);
        }
    }

    public void addMatchResult(Nric patientMatched, Double successRate) {
        successRateMap.put(patientMatched, successRate);
    }

    /**
     * Set the status of the donor.
     * @param newStatus
     */
    public void setStatus(String newStatus) {
        Status updatedStatus = new Status(newStatus);
        this.status = updatedStatus;
    }

    public void setEmptyList() {
        TaskList updatedProcessingList = new TaskList("");
        this.processingTodoList = updatedProcessingList;
    }

    public void setProcessingList(String newProcessingList) {
        TaskList updatedProcessingList = new TaskList("");
        if (newProcessingList == null || newProcessingList.equals("")) {
            this.processingTodoList = updatedProcessingList;
        } else {
            String[] taskString = newProcessingList.split("\n");
            for (int i = 0; i < taskString.length; i++) {
                String currentTaskString = taskString[i];
                if (!currentTaskString.isEmpty()) {
                    String[] taskDes = currentTaskString.split("]");
                    Task toBeAddedTask = new Task(taskDes[1]);
                    if (taskDes[0].equals("[\u2713")) {
                        toBeAddedTask.markAsDone(toBeAddedTask);
                    }
                    updatedProcessingList.add(toBeAddedTask);
                }
                this.processingTodoList = updatedProcessingList;
            }
        }
    }

    /**
     * Mark the task given as done
     * @param taskNumber of the task needed to mark as done in the list
     */
    public void markTaskAsDone(int taskNumber) {
        TaskList updatedTaskList = getProcessingList(patientNric);
        updatedTaskList.get(taskNumber - 1).markAsDone(updatedTaskList.get(taskNumber - 1));
        this.processingTodoList = updatedTaskList;
    }

    /**
     * Get the ArrayList containing all the Nrics of the patients who have done a matching with this donor
     * before but failed in the end.
     * @return ArrayList of Nrics
     */
    public ArrayList getPatientMatchedBefore() {
        return new ArrayList<>(patientsMatchedBefore);
    }

    public void setPatientsMatchedBefore(String newList) {
        newList = newList.replaceAll("\\[", "").replaceAll("\\]", "");
        ArrayList<Nric> updatedList = new ArrayList<>();
        if (newList == null || newList.equals("") || newList.equals("[]")) {
            this.patientsMatchedBefore = updatedList;
        } else if (newList.lastIndexOf(",") == -1) {
            updatedList.add(new Nric(newList));
            this.patientsMatchedBefore = updatedList;
        } else {
            String[] patientNricString = newList.split(",");
            for (int i = 0; i < patientNricString.length; i++) {
                String currentPatientNric = patientNricString[i];
                if (!currentPatientNric.trim().isEmpty()) {
                    updatedList.add(new Nric(currentPatientNric.trim()));
                }
                this.patientsMatchedBefore = updatedList;
            }
        }
    }

    /**
     * Returns true if both donors have the same identity and data fields.
     * This defines a stronger notion of equality between two donors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Donor)) {
            return false;
        }

        Donor otherPerson = (Donor) other;
        return otherPerson.getNric().equals(getNric())
            && otherPerson.getName().equals(getName())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getType().equals(getType())
            && otherPerson.getAge().equals(getAge())
            && otherPerson.getBloodType().equals(getBloodType())
            && otherPerson.getTissueType().equals(getTissueType())
            && otherPerson.getOrgan().equals(getOrgan())
            && otherPerson.getOrganExpiryDate().equals(getOrganExpiryDate())
            && otherPerson.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
            .append(" Age: ")
            .append(getAge())
            .append(" Blood Type: ")
            .append(getBloodType())
            .append(" Tissue Type: ")
            .append(getTissueType())
            .append(" Organ: ")
            .append(getOrgan())
            .append(" Organ Expiry Date: ")
            .append(getOrganExpiryDate())
            .append(" Status: ")
            .append(getStatus());

        return builder.toString();
    }

}
