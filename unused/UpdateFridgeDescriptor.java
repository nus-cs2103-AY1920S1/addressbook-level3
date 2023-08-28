package seedu.address.logic.parser.utility;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;

//@@author ambervoong-unused
/**
 * Stores the details to update the {@code Fridge} with. Each non-empty field value will replace the
 * corresponding field value of the fridge.
 */
public class UpdateFridgeDescriptor implements UpdateEntityDescriptor {

    private IdentificationNumber bodyId;
    private FridgeStatus fridgeStatus;
    private Body newBody;

    public UpdateFridgeDescriptor() {
    }

    /**
     * Makes a copy of an existing UpdateFridgeDescriptor.
     */
    public UpdateFridgeDescriptor(UpdateFridgeDescriptor toCopy) {
        setBodyId(toCopy.bodyId);
        setFridgeStatus(toCopy.fridgeStatus);
        setNewBody(toCopy.newBody);
    }

    /**
     * Makes a copy of a Fridge's current fields.
     * @param fridge the fridge to be copied.
     * @returns UpdateFridgeDescriptor that contains the fridge's current fields.
     */
    public UpdateFridgeDescriptor(Fridge fridge) {
        Optional<Body> body = fridge.getBody();
        if (!body.equals(Optional.empty())) {
            this.bodyId = body.get().getIdNum();
            this.newBody = body.get();
        } else {
            this.newBody = null;
        }
        this.fridgeStatus = fridge.getFridgeStatus();
    }

    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(bodyId, fridgeStatus, newBody);
    }

    @Override
    public Entity apply(Entity entity) {
        assert entity != null;
        Fridge fridge = (Fridge) entity;
        fridge.setFridgeStatus(this.getFridgeStatus().orElse(fridge.getFridgeStatus()));
        fridge.setBody(this.getNewBody().orElse(null));
        return entity;
    }

    @Override
    public Entity applyOriginal(Entity entity) {
        return null;
    }

    // Getters and Setters
    public Optional<IdentificationNumber> getBodyId() {
        return Optional.ofNullable(bodyId);
    }

    public void setBodyId(IdentificationNumber bodyId) {
        this.bodyId = bodyId;
    }

    public Optional<FridgeStatus> getFridgeStatus() {
        return Optional.ofNullable(fridgeStatus);
    }

    public void setFridgeStatus(FridgeStatus fridgeStatus) {
        this.fridgeStatus = fridgeStatus;
    }

    public Optional<Body> getNewBody() {
        return Optional.ofNullable(newBody);
    }

    public void setNewBody(Body newBody) {
        this.newBody = newBody;
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
        return getBodyId().equals(that.getBodyId())
                && getFridgeStatus().equals(that.getFridgeStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBodyId(), getFridgeStatus());
    }
}
//@@author
