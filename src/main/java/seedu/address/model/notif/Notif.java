package seedu.address.model.notif;

import static seedu.address.commons.core.Messages.MESSAGE_BODY_COULD_NOT_BE_UPDATED;
import static seedu.address.model.entity.body.BodyStatus.ARRIVED;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.NotifCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;

//@@author arjavibahety

/**
 * Represents a notification in Mortago.
 * Guarantees: body is guanranteed to be present.
 */
public class Notif {
    private static final Logger logger = LogsCenter.getLogger(NotifCommand.class);

    private Body body;
    private Runnable alert;
    private Date notifCreationTime;

    public Notif(Body body) {
        this.body = body;
        this.alert = new Runnable() {
            @Override
            public void run() {
                if (body.getBodyStatus().equals(Optional.of(ARRIVED))) {
                    try {
                        body.setBodyStatus(BodyStatus.parseBodyStatus(
                                "contact police"));
                    } catch (ParseException exp) {
                        logger.info(MESSAGE_BODY_COULD_NOT_BE_UPDATED);
                    }
                }
            }
        };
        this.notifCreationTime = new Date();
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

    public Date getNotifCreationTime() {
        return notifCreationTime;
    }

    public void setNotifCreationTime(Date notifCreationTime) {
        this.notifCreationTime = notifCreationTime;
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
//@@author
