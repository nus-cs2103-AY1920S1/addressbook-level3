package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.entity.worker.Worker;
import seedu.address.ui.WorkerCard;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4 with major modifications
/**
 * Provides a handle to a person card in the person list panel.
 */
public class WorkerCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String SEX_FIELD_ID = "#sex";
    private static final String WORKER_ID_FIELD_ID = "#workerId";
    private static final String DATE_JOINED_FIELD_ID = "#dateJoined";
    private static final String DATE_OF_BIRTH_FIELD_ID = "#dateOfBirth";
    private static final String PHONE_FIELD_ID = "#phoneNumber";
    private static final String DESIGNATION_FIELD_ID = "#designation";
    private static final String EMPLOYMENT_STATUS_FIELD_ID = "#employmentStatus";

    private final Label idLabel;
    private final Label workerIdLabel;
    private final Label nameLabel;
    private final Label sexLabel;
    private final Label dateJoinedLabel;
    private final Label dateOfBirthLabel;
    private final Label phoneLabel;
    private final Label designationLabel;
    private final Label employmentStatusLabel;

    public WorkerCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        workerIdLabel = getChildNode(WORKER_ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        sexLabel = getChildNode(SEX_FIELD_ID);
        dateJoinedLabel = getChildNode(DATE_JOINED_FIELD_ID);
        dateOfBirthLabel = getChildNode(DATE_OF_BIRTH_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        designationLabel = getChildNode(DESIGNATION_FIELD_ID);
        employmentStatusLabel = getChildNode(EMPLOYMENT_STATUS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getWorkerId() {
        return workerIdLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getSex() {
        return sexLabel.getText();
    }

    public String getDateJoined() {
        return dateJoinedLabel.getText();
    }

    public String getDateOfBirth() {
        return dateOfBirthLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getDesignation() {
        return designationLabel.getText();
    }

    public String getEmploymentStatus() {
        return employmentStatusLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code worker}.
     */
    public boolean equals(Worker worker) {
        return getName().equals(worker.getName().toString())
                && getSex().equalsIgnoreCase(worker.getSex().toString())
                && getWorkerId().equals(worker.getIdNum().toString())
                && getDateJoined().equals(WorkerCard.formatDate(worker.getDateJoined()))
                && getDateOfBirth().equals(worker.getDateOfBirth().isPresent()
                        ? WorkerCard.formatDate(worker.getDateOfBirth().get()) : "-")
                && getDesignation().equals(worker.getDesignation().isPresent()
                        ? worker.getDesignation().get() : "-")
                && getEmploymentStatus().equals(worker.getEmploymentStatus().isPresent()
                        ? worker.getEmploymentStatus().get() : "-");
    }
}
//@@author
