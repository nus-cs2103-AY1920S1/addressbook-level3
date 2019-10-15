package seedu.address.model.notif;

import static seedu.address.model.entity.body.BodyStatus.ARRIVED;

import java.util.Objects;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;

//@@author arjavibahety

/**
 * Represents a notification in Mortago.
 * Guarantees: body is guanranteed to be present.
 */
public class Notif {
    private Body body;
    private Runnable alert;

    public Notif(Body body) {
        this.body = body;
        this.alert = new Runnable() {
            @Override
            public void run() {
                if (body.getBodyStatus() == ARRIVED) {
                    body.setBodyStatus(BodyStatus.parseBodyStatus(
                            "pending police report"));
                }
            }
        };
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Runnable getAlert() {
        return alert;
    }

    /**
     * Returns whether another object is equal to this object. Equality is defined as having identical attributes. Null
     * objects are not considered equal.
     * @param other An object.
     * @return whether the object is equal to this object.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Notif notif = (Notif) other;
        return getBody().equals(notif.getBody())
                && getAlert().equals(notif.getAlert());
    }

    public int hashCode() {
        return Objects.hash(body, alert);
    }

    /**
     * Returns whether an object is equal to this notif. The definition of equality is relaxed here to only include
     * body.
     * @param other An object.
     * @return whether the object is equal to this object.
     */
    public boolean isSameNotif(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Notif)) {
            return false;
        } else {
            return other != null
                    && ((Notif) other).getBody() == getBody();
        }
    }

}
