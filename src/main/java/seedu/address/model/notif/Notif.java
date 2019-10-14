package seedu.address.model.notif;

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
        this.alert = () -> this.body.setBodyStatus(BodyStatus.parseBodyStatus("pending police report"));
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

    public void setAlert(Runnable alert) {
        this.alert = alert;
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
