package seedu.address.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;

/**
 * Tells reminder when to activate. All types of conditions extend form this class.
 */
public abstract class Condition {
    private Description description;
    private TrackerType trackerType = TrackerType.none;
    private Predicate<Entry> pred;
    private long currSum;
    private long trackerQuota;
    private Status status = Status.unmet;
    public Condition(Description desc) {
        this.description = desc;
        this.pred = pred;
    }
    /**
     * checks if condition is met.
     * @param entry
     */
    public void update(Entry entry) {
        if (trackerType == TrackerType.none) {
            if (pred.test(entry)) {
                status = Status.met;
            }
        } else if (trackerType == TrackerType.num) {
            if (pred.test(entry)) {
                currSum += 1;
                if (currSum == trackerQuota) {
                    status = Status.met;
                } else if (currSum > trackerQuota) {
                    status = Status.exceeded;
                }
            }
        } else if (trackerType == TrackerType.amount) {
            if (pred.test(entry)) {
                currSum += entry.getAmount().value;
                if (currSum == trackerQuota) {
                    status = Status.met;
                } else if (currSum > trackerQuota) {
                    status = Status.exceeded;
                }
            }
        }
    }
    public Description getDesc() {
        return description;
    }
    public Status getStatus() {
        return status;
    }
    protected void setPred(Predicate<Entry> pred) {
        this.pred = pred;
    }
    public void setTracker(String trackerType, long quota) {
        parseTracker(trackerType);
        if (!trackerType.equals(TrackerType.none)) {
            currSum = 0;
            this.trackerQuota = quota;
        }
    }
    public void setTracker(String trackerType, long currSum, long quota) {
        parseTracker(trackerType);
        if (!trackerType.equals(TrackerType.none)) {
            this.currSum = currSum;
            this.trackerQuota = quota;
        }
    }

    public enum TrackerType {
        none,
        num,
        amount;
        @Override
        public String toString() {
            switch(this) {
            case num:
                return "num";
            case amount:
                return "amount";
            default:
                return "none";

            }
        }
    }
    /**
     * converts String to TrackerType.
     * @param trackerType
     */
    private void parseTracker(String trackerType) {
        switch (trackerType) {
        case "none":
            this.trackerType = TrackerType.none;
            break;
        case "num":
            this.trackerType = TrackerType.num;
            break;
        case "amount":
            this.trackerType = TrackerType.amount;
            break;
        }
    }

    public TrackerType getTrackerType() {
        return trackerType;
    }
    public long getCurrSum() {
        return currSum;
    }
    public long getTrackerQuota() {
        return trackerQuota;
    }
    private enum Status {
        unmet,
        met,
        exceeded
    }
}
