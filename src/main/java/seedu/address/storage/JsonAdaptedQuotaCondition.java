package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.QuotaCondition;

/**
 * Jackson-friendly version of {@link QuotaCondition}.
 */
public class JsonAdaptedQuotaCondition {
    private final String desc;
    private final long quota;
    private final String trackerType;
    private final long currSum;
    private final long trackerQuota;
    /**
     * Constructs a {@code JsonAdaptedClassCondition} with the given condition details.
     */
    @JsonCreator
    public JsonAdaptedQuotaCondition(@JsonProperty("desc") String desc,
                                     @JsonProperty("quota") Long quota,
                                     @JsonProperty("trackerType") String trackerType,
                                     @JsonProperty("currSum") Long currSum,
                                     @JsonProperty("trackerQuota") Long trackerQuota) {
        this.desc = desc;
        this.quota = quota;
        this.trackerType = trackerType;
        if (!trackerType.equals("none")) {
            this.currSum = currSum;
            this.trackerQuota = trackerQuota;
        } else {
            this.currSum = 0;
            this.trackerQuota = 0;
        }
    }
    public JsonAdaptedQuotaCondition(QuotaCondition source) {
        this.desc = source.getDesc().toString();
        this.quota = source.getQuota();
        this.trackerType = source.getTrackerType().toString();
        if (!trackerType.equals("none")) {
            this.currSum = source.getCurrSum();
            this.trackerQuota = source.getTrackerQuota();
        } else {
            this.currSum = 0;
            this.trackerQuota = 0;
        }
    }

    /**
     * Converts this Jackson-friendly adapted ClassCondition object into the model's {@code ClassCondition} object.
     * @return
     * @throws IllegalValueException
     */
    public QuotaCondition toModelType() throws IllegalValueException {
        final Description modelDesc = new Description(desc);
        QuotaCondition modelCondition = new QuotaCondition(modelDesc, quota);
        switch (trackerType) {
        case "none":
            modelCondition.setTracker("none", currSum, trackerQuota);
            break;
        case "num":
            modelCondition.setTracker("num", currSum, trackerQuota);
            break;
        case "amount":
            modelCondition.setTracker("amount", currSum, trackerQuota);
            break;
        }
        return modelCondition;
    }
}
