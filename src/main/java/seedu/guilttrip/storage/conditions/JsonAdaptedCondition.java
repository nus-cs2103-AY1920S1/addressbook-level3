package seedu.guilttrip.storage.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.KeyWordsCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Condition}.
 */
public class JsonAdaptedCondition {
    private String conditionType;
    private String entryType;
    private boolean isLowerBound;
    private double lowerBound;
    private double upperBound;
    private boolean isStart;
    private String start;
    private String end;
    private final List<String> keywords = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCondition} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedCondition(@JsonProperty("conditionType") String conditionType,
                                @JsonProperty("entryType") String entryType,
                                @JsonProperty("isStart") boolean isStart,
                                @JsonProperty("start") String start,
                                @JsonProperty("end") String end,
                                @JsonProperty("keywords") List<String> keywords,
                                @JsonProperty("isLowerBound") boolean isLowerBound,
                                @JsonProperty("lowerBound") double lowerBound,
                                @JsonProperty("upperBound") double upperBound,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.conditionType = conditionType;
        this.entryType = entryType;
        this.isStart = isStart;
        this.start = start;
        this.end = end;
        this.keywords.addAll(keywords);
        this.isLowerBound = isLowerBound;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedCondition(Condition condition) {
        if (condition instanceof TypeCondition) {
            this.conditionType = "entryCondition";
            this.entryType = ((TypeCondition) condition).getEntryType().toString();
        } else if (condition instanceof DateCondition) {
            this.conditionType = "dateCondition";
            DateCondition dateCondition = (DateCondition) condition;
            if (isStart) {
                this.start = ((DateCondition) condition).getDate().toString();
            } else {
                this.end = ((DateCondition) condition).getDate().toString();
            }
        } else if (condition instanceof KeyWordsCondition) {
            this.conditionType = "keyWordsCondition";
            this.keywords.addAll(((KeyWordsCondition) condition).getKeywords());
        } else if (condition instanceof QuotaCondition) {
            this.conditionType = "quotaCondition";
            QuotaCondition quotaCondition = (QuotaCondition) condition;
            if (isLowerBound) {
                this.lowerBound = quotaCondition.getQuota();
            } else {
                upperBound = quotaCondition.getQuota();
            }
        } else if (condition instanceof TagsCondition) {
            this.conditionType = "tagsCondition";
            this.tags.addAll(((TagsCondition) condition).getTagList().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
        }
    }

    /**
     * Converts this Jackson-friendly adapted condition object into the model's {@code Condition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Condition toModelType() throws IllegalValueException {
        switch (conditionType) {
        case "classCondition":
            return new TypeCondition(entryType);
        case "dateCondition":
            if (isStart) {
                return new DateCondition(new Date(start), true);
            } else {
                return new DateCondition(new Date(end), false);
            }
        case "keyWordsCondition":
            return new KeyWordsCondition(keywords);
        case "quotaCondition":
            if (isLowerBound) {
                return new QuotaCondition(lowerBound, true);
            } else {
                return new QuotaCondition(upperBound, false);
            }
        case "tagsCondition":
            final List<Tag> tagList = new ArrayList<>();
            for (JsonAdaptedTag tag : tags) {
                tagList.add(tag.toModelType());
            }
            return new TagsCondition(tagList);
        default:
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
    }
}
