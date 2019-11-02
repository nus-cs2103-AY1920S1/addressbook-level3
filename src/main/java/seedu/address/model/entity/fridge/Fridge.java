package seedu.address.model.entity.fridge;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;

//@@author arjavibahety
/**
 * Represents a fridge entry in Mortago.
 * Guarantees: fridgeIdNum and status are guaranteed to be present and not null
 */
public class Fridge implements Entity {

    // Identity field
    private IdentificationNumber fridgeIdNum;

    // Data field
    private Optional<Body> body;
    private FridgeStatus fridgeStatus;

    //@@author ambervoong
    private int bodyId;
    //@@author

    public Fridge() {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId(this);
        this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        this.body = Optional.ofNullable(null);
    }

    public Fridge(Body body) {
        this.fridgeIdNum = IdentificationNumber.generateNewFridgeId(this);
        this.body = Optional.ofNullable(body);
        if (body == null) {
            this.fridgeStatus = FridgeStatus.UNOCCUPIED;
        } else {
            this.fridgeStatus = FridgeStatus.OCCUPIED;
        }
    }

    //@@author ambervoong
    /**
     * Creates a new Fridge object. If the fridge wasStored, no ID is set when constructing the Fridge.
     * If the fridge was not stored before, a default ID is given.
     * This ID is not added to the UniqueIdentificationNumberMaps.
     * @param wasStored boolean indicating if the fridge was previously stored.
     */
    private Fridge(boolean wasStored) {
        if (!wasStored) {
            fridgeIdNum = IdentificationNumber.customGenerateId("F", 1);
        }
    }

    /**
     * Generates a new Fridge with a custom ID. Only used for creating a Fridge from storage.
     * @param id ID of the stored Fridge.
     * @return Fridge
     */
    public static Fridge generateNewStoredFridge(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        Fridge fridge = new Fridge(true);
        fridge.fridgeIdNum = IdentificationNumber.generateNewFridgeId(fridge, id);
        return fridge;
    }
    //@@author

    public IdentificationNumber getIdNum() {
        return fridgeIdNum;
    }

    public FridgeStatus getFridgeStatus() {
        return fridgeStatus;
    }

    public Optional<Body> getBody() {
        return body;
    }

    public void setFridgeStatus(FridgeStatus fridgeStatus) {
        this.fridgeStatus = fridgeStatus;
    }

    public void setBody(Body body) {
        this.body = Optional.ofNullable(body);
        if (body == null) {
            setFridgeStatus(FridgeStatus.UNOCCUPIED);
            setBodyId(0);

        } else {
            setFridgeStatus(FridgeStatus.OCCUPIED);
            setBodyId(body.getIdNum().getIdNum());
        }
    }

    //@@author ambervoong
    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }
    //@@author

    /**
     * Returns true if both fridge have the same identity fields.
     * This defines a weaker notion of equality between two fridges.
     */
    public boolean isSameFridge(Object otherFridge) {
        if (otherFridge == this) {
            return true;
        } else if (otherFridge instanceof Fridge) {
            return otherFridge != null
                && ((Fridge) otherFridge).getIdNum() == getIdNum();
        } else {
            return false;
        }
    }

    public boolean isSameEntity(Object otherFridge) {
        return isSameFridge(otherFridge);
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
        return otherFridge.getIdNum().toString().equals(getIdNum().toString())
                && otherFridge.getFridgeStatus().equals(getFridgeStatus())
                && otherFridge.getBody().equals(getBody());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getIdNum());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (getBody() != null) {
            builder.append(" Fridge ID: ")
                    .append(getIdNum())
                    .append(" Status: ")
                    .append(getFridgeStatus())
                    .append(" Body: ")
                    .append(getBody());
        } else {
            builder.append(" Fridge ID: ")
                    .append(getIdNum())
                    .append(" Status: ")
                    .append(getFridgeStatus());
        }
        return builder.toString();
    }
}
//@@author
