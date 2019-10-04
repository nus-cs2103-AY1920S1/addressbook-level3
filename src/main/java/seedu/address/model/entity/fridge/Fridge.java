package seedu.address.model.entity.fridge;

import java.util.Objects;

import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;

//@@author arjavibahety
/**
 * Represents a fridge entry in Mortago.
 * Guarantees: fridgeIdNum and status are guaranteed to be present and not null
 */
public class Fridge {

    // Identity field
    private final IdentificationNumber fridgeIdNum;

    // Data field
    private Body body;
    private Status status;

    public Fridge() {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        this.status = Status.UNOCCUPIED;
        this.body = null;
    }

    public Fridge(Body body) {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        this.body = body;
        if (body == null) {
            this.status = Status.UNOCCUPIED;
        } else {
            this.status = Status.OCCUPIED;
        }
    }

    public IdentificationNumber getFridgeIdNum() {
        return fridgeIdNum;
    }

    public Status getStatus() {
        return status;
    }

    public Body getBody() {
        return body;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBody(Body body) {
        this.body = body;
        if (body == null) {
            setStatus(Status.UNOCCUPIED);
        } else {
            setStatus(Status.OCCUPIED);
        }
    }

    /**
     * Returns true if both fridge have the same identity fields.
     * This defines a weaker notion of equality between two fridges.
     */
    public boolean isSameFridge(Fridge otherFridge) {
        if (otherFridge == this) {
            return true;
        }

        return otherFridge != null
                && otherFridge.getFridgeIdNum() == getFridgeIdNum();
    }

    /**
     * Returns true if both fridge have the same identity and data fields.
     * This defines a stronger notion of equality between two fridges.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Fridge)) {
            return false;
        }

        Fridge otherFridge = (Fridge) other;
        return otherFridge.getFridgeIdNum() == getFridgeIdNum()
                && otherFridge.getStatus() == getStatus()
                && otherFridge.getBody() == getBody();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(fridgeIdNum);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (getBody() != null) {
            builder.append(" Fridge ID: ")
                    .append(getFridgeIdNum())
                    .append(" Status: ")
                    .append(getStatus())
                    .append(" Body: ")
                    .append(getBody());
        }
        return builder.toString();
    }
}
