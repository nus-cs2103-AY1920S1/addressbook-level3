package seedu.address.model.trip;

import java.util.UUID;

public class TripId {
    private final UUID tripUuid;

    TripId() {
        this.tripUuid = UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return tripUuid.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TripId)) {
            return false;
        }

        return tripUuid.equals(((TripId) other).tripUuid);
    }
}
