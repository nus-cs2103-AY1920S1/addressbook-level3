package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.KeyWordsCondition;
/**
 * Jackson-friendly version of {@link KeyWordsCondition}.
 */
public class JsonAdaptedKeyWordsCondition {
    public final String desc;
    private final List<String> keywords = new ArrayList<>();
    private final String trackerType;
    private final long currSum;
    private final long trackerQuota;
    /**
     * Constructs a {@code JsonAdaptedKeyWordsCondition} with the given condition details.
     */
    @JsonCreator
    public JsonAdaptedKeyWordsCondition(@JsonProperty("desc") String desc,
                                     @JsonProperty("keywords") List<String> keywords,
                                        @JsonProperty("trackerType") String trackerType,
                                        @JsonProperty("currSum") Long currSum,
                                        @JsonProperty("trackerQuota") Long trackerQuota) {
        this.desc = desc;
        if (keywords != null) {
            this.keywords.addAll(keywords);
        }
        this.trackerType = trackerType;
        if (!trackerType.equals("none")) {
            this.currSum = currSum;
            this.trackerQuota = trackerQuota;
        } else {
            this.currSum = 0;
            this.trackerQuota = 0;
        }
    }
    public JsonAdaptedKeyWordsCondition(KeyWordsCondition source) {
        this.desc = source.getDesc().toString();
        this.keywords.addAll(source.getKeywords().stream().collect(Collectors.toList()));
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
    public KeyWordsCondition toModelType() throws IllegalValueException {
        final Description modelDesc = new Description(desc);
        final List<String> conditionKeyWords = new ArrayList<>();
        for (String keyword : keywords) {
            conditionKeyWords.add(keyword);
        }
        KeyWordsCondition modelCondition = new KeyWordsCondition(modelDesc, conditionKeyWords);
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
