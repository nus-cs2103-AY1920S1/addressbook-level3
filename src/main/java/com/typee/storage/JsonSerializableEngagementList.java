package com.typee.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.typee.commons.exceptions.IllegalValueException;
import com.typee.model.EngagementList;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.engagement.Engagement;

/**
 * An Immutable EngagementList that is serializable to JSON format.
 */
@JsonRootName(value = "engagementlist")
class JsonSerializableEngagementList {

    public static final String MESSAGE_DUPLICATE_ENGAGEMENT = "Engagement list contains duplicate engagement(s).";

    private final List<JsonAdaptedEngagement> engagements = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEngagementList} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEngagementList(@JsonProperty("engagements") List<JsonAdaptedEngagement> engagements) {
        this.engagements.addAll(engagements);
    }

    /**
     * Converts a given {@code ReadOnlyEngagementList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEngagementList}.
     */
    public JsonSerializableEngagementList(ReadOnlyEngagementList source) {
        engagements.addAll(source.getEngagementList().stream().map(JsonAdaptedEngagement::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code EngagementList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EngagementList toModelType() throws IllegalValueException {
        EngagementList engagementList = new EngagementList();
        for (JsonAdaptedEngagement jsonAdaptedEngagement : engagements) {
            Engagement engagement = jsonAdaptedEngagement.toModelType();
            if (engagementList.hasEngagement(engagement)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENGAGEMENT);
            }
            engagementList.addEngagement(engagement);
        }
        return engagementList;
    }

}
