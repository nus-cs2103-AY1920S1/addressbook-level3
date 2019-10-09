package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.EndDateTime;
import seedu.address.model.visit.Remark;
import seedu.address.model.visit.StartDateTime;
import seedu.address.model.visit.Visit;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Visit}.
 */
class JsonAdaptedVisit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final String remark;
    private final String startDateTime;
    private final String endDateTime;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("remark") String remark,
                            @JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.remark = remark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        remark = source.getRemark().remark;
        startDateTime = source.getStartDateTime().toJacksonJsonString();
        endDateTime = source.getEndDateTime().toJacksonJsonString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        final List<Tag> visitTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            visitTags.add(tag.toModelType());
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidStartDateTime(startDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_CONSTRAINTS);
        }
        final StartDateTime modelStartDateTime = new StartDateTime(startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName()));
        }
        if (!EndDateTime.isValidEndDateTime(endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_CONSTRAINTS);
        }
        final EndDateTime modelEndDateTime = new EndDateTime(endDateTime);

        final Set<Tag> modelTags = new HashSet<>(visitTags);
        return new Visit(modelRemark, modelStartDateTime, modelEndDateTime, modelTags);
    }

}
