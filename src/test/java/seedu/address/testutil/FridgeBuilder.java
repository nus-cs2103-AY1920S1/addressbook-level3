package seedu.address.testutil;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;

//@@author arjavibahety
/**
 * A utility class to build Fridge objects.
 */
public class FridgeBuilder {

    public static final Body DEFAULT_BODY = null;

    private Body body;

    public FridgeBuilder() {
        body = DEFAULT_BODY;
    }


    /**
     * Initializes the FridgeBuilder with the data of {@code fridgeToCopy}.
     * @param fridgeToCopy
     */
    public FridgeBuilder(Fridge fridgeToCopy) {
        body = fridgeToCopy.getBody();
    }

    /**
     * Sets the {@code Body} of the {@code Fridge} that we are building.
     */
    public FridgeBuilder withBody(Body body) {
        this.body = body;
        return this;
    }

    /**
     * Creates a fridge using the parameters currently in this FridgeBuilder object.
     * @return Fridge
     */
    public Fridge build() {
        if (body == null) {
            return new Fridge();
        } else {
            return new Fridge(body);
        }
    }
}
