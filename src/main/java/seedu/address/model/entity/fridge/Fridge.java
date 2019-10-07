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
    private FridgeStatus fridgeStatus;

    public Fridge() {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        this.body = null;
    }

    public Fridge(boolean isTestFridge) {
        if (isTestFridge) {
            fridgeIdNum = IdentificationNumber.customGenerateId("F", 1);
        } else {
            this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        }
        this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        this.body = null;
    }

    public Fridge(Body body) {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        this.body = body;
        if (body == null) {
            this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        } else {
            this.fridgeStatus = FridgeStatus.OCCUPIED;
        }
    }

    public Fridge(Body body, boolean isTestFridge) {
        if (isTestFridge) {
            this.fridgeIdNum = IdentificationNumber.customGenerateId("F", 1);
        } else {
            this.fridgeIdNum = IdentificationNumber.generateNewFridgeId();
        }
        this.body = body;
        if (body == null) {
            this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        } else {
            this.fridgeStatus = FridgeStatus.OCCUPIED;
        }
    }

    public IdentificationNumber getFridgeIdNum() {
        return fridgeIdNum;
    }

    public FridgeStatus getFridgeStatus() {
        return fridgeStatus;
    }

    public Body getBody() {
        return body;
    }

    public void setFridgeStatus(FridgeStatus fridgeStatus) {
        this.fridgeStatus = fridgeStatus;
    }

    public void setBody(Body body) {
        this.body = body;
        if (body == null) {
            setFridgeStatus(FridgeStatus.UNOCCUPIED);
        } else {
            setFridgeStatus(FridgeStatus.OCCUPIED);
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
        return otherFridge.getFridgeIdNum().toString().equals(getFridgeIdNum().toString())
                && otherFridge.getFridgeStatus() == getFridgeStatus()
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
                    .append(getFridgeStatus())
                    .append(" Body: ")
                    .append(getBody());
        } else {
            builder.append(" Fridge ID: ")
                    .append(getFridgeIdNum())
                    .append(" Status: ")
                    .append(getFridgeStatus());
        }
        return builder.toString();
    }
}
