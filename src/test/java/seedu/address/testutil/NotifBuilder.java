package seedu.address.testutil;

import static seedu.address.testutil.TypicalBodies.BOB;

import seedu.address.model.entity.body.Body;
import seedu.address.model.notif.Notif;

/**
 * A utility class to help with building Notif objects.
 */
public class NotifBuilder {

    public static final Body DEFUALT_BODY = BOB;

    private Body body;

    public NotifBuilder() {
        body = DEFUALT_BODY;
    }

    /**
     * Initializes the NotifBuilder with the data of {@code bodyToCopy}.
     * @param bodyToCopy
     */
    public NotifBuilder(Body bodyToCopy) {
        body = bodyToCopy;
    }

    /**
     * Sets the {@code Body} of the {@code Notif} that we are building.
     */
    public NotifBuilder withBody(Body body) {
        this.body = body;
        return this;
    }

    public Notif build() {
        return new Notif(body);
    }

}
