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
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyEngagementList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyEngagementList source) {
        persons.addAll(source.getEngagementList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code EngagementList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EngagementList toModelType() throws IllegalValueException {
        EngagementList engagementList = new EngagementList();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Engagement engagement = jsonAdaptedPerson.toModelType();
            if (engagementList.hasEngagement(engagement)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            engagementList.addEngagement(engagement);
        }
        return engagementList;
    }

}
