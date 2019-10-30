package seedu.address.storage.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.ClassCondition;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.reminders.conditions.DateCondition;
import seedu.address.model.reminders.conditions.KeyWordsCondition;
import seedu.address.model.reminders.conditions.QuotaCondition;
import seedu.address.model.reminders.conditions.TagsCondition;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Condition}.
 */
public class JsonAdaptedCondition {
    private String conditionType;
    private String entryType;
    private String start;
    private String end;
    private final List<String> keywords = new ArrayList<>();
    private double quota;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCondition} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedCondition(@JsonProperty("conditionType") String conditionType,
                                @JsonProperty("entryType") String entryType,
                                @JsonProperty("start") String start,
                                @JsonProperty("end") String end,
                                @JsonProperty("keywords") List<String> keywords,
                                @JsonProperty("quota") double quota,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.conditionType = conditionType;
        this.entryType = entryType;
        this.start = start;
        this.end = end;
        this.keywords.addAll(keywords);
        this.quota = quota;
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedCondition(Condition condition) {
        if (condition instanceof ClassCondition) {
            this.conditionType = "classCondition";
            this.entryType = ((ClassCondition) condition).getEntryType().toString();
        } else if (condition instanceof DateCondition) {
            this.conditionType = "dateCondition";
            this.start = ((DateCondition) condition).getStart().toString();
            this.end = ((DateCondition) condition).getEnd().toString();
        } else if (condition instanceof KeyWordsCondition) {
            this.conditionType = "keyWordsCondition";
            this.keywords.addAll(((KeyWordsCondition) condition).getKeywords());
        } else if (condition instanceof QuotaCondition) {
            this.conditionType = "quotaCondition";
            this.quota = ((QuotaCondition) condition).getQuota();
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
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Condition toModelType() throws IllegalValueException {
        switch (conditionType) {
        case "classCondition":
            return new ClassCondition(entryType);
        case "dateCondition":
            return new DateCondition(new Date(start), new Date(end));
        case "keyWordsCondition":
            return new KeyWordsCondition(keywords);
        case "quotaCondition":
            return new QuotaCondition(quota);
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
