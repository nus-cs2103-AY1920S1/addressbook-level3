package seedu.address.logic.parser.utility;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;

//@@author ambervoong
/**
 * Stores the details to update the {@code Fridge} with. Each non-empty field value will replace the
 * corresponding field value of the fridge.
 */
public class UpdateFridgeDescriptor implements UpdateEntityDescriptor {

    private Body body;
    private FridgeStatus fridgeStatus;

    public UpdateFridgeDescriptor() {
    }

    /**
     * Makes a copy of an existing UpdateFridgeDescriptor.
     */
    public UpdateFridgeDescriptor(UpdateFridgeDescriptor toCopy) {
        setBody(toCopy.body);
        setFridgeStatus(toCopy.fridgeStatus);
    }

    /**
     * Makes a copy of a Fridge's current fields.
     * @param fridge the fridge to be copied.
     * @returns UpdateFridgeDescriptor that contains the fridge's current fields.
     */
    public UpdateFridgeDescriptor(Fridge fridge) {
        this.body = fridge.getBody();
        this.fridgeStatus = fridge.getFridgeStatus();
    }

    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(body, fridgeStatus);
    }

    @Override
    public Entity apply(Entity entity) {
        assert entity != null;
        Fridge fridge = (Fridge) entity;
        fridge.setBody(this.getBody().orElse(fridge.getBody()));
        fridge.setFridgeStatus(this.getFridgeStatus().orElse(fridge.getFridgeStatus()));
        return entity;
    }

    // Getters and Setters
    public Optional<Body> getBody() {
        return Optional.ofNullable(body);
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Optional<FridgeStatus> getFridgeStatus() {
        return Optional.ofNullable(fridgeStatus);
    }

    public void setFridgeStatus(FridgeStatus fridgeStatus) {
        this.fridgeStatus = fridgeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateFridgeDescriptor)) {
            return false;
        }
        UpdateFridgeDescriptor that = (UpdateFridgeDescriptor) o;
        return getBody().equals(that.getBody())
                && getFridgeStatus().equals(that.getFridgeStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody(), getFridgeStatus());
    }
}
