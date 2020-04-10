package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event name is missing!";

    private final String name;
    private final HashMap<String, List<JsonAdaptedRecord>> performances = new HashMap<>();


    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("performances") HashMap<String, List<JsonAdaptedRecord>> performances) {
        this.name = name;
        if (performances != null) {
            this.performances.putAll(performances);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName();
        HashMap<String, List<JsonAdaptedRecord>> tempPerformances = new HashMap<>(); // for json
        HashMap<Person, List<Record>> sourcePerformances = source.getRecords(); // actual performances
        sourcePerformances.forEach((person, records) -> {
            JsonAdaptedPerson newPerson = new JsonAdaptedPerson(person);
            List<JsonAdaptedRecord> tempRecords = new ArrayList<>();
            tempRecords.addAll(records.stream()
                    .map(JsonAdaptedRecord::new)
                    .collect(Collectors.toList()));
            try {
                String personJsonString = JsonUtil.toJsonString(newPerson);
                tempPerformances.put(personJsonString, tempRecords);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        performances.putAll(tempPerformances);
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        if (!Event.isValidName(name)) {
            throw new IllegalValueException(Event.MESSAGE_CONSTRAINTS);
        }

        final String modelName = name.trim().toLowerCase();

        HashMap<Person, List<Record>> initialisedPerformances = new HashMap<>(); // actual performances
        performances.forEach((jsonAdaptedPerson, jsonAdaptedRecords) -> {
            List<Record> tempRecords = new ArrayList<>();
            for (JsonAdaptedRecord jsonAdaptedRecord : jsonAdaptedRecords) {
                try {
                    tempRecords.add(jsonAdaptedRecord.toModelType());
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
            try {
                Person person = JsonUtil.fromJsonString(jsonAdaptedPerson, JsonAdaptedPerson.class).toModelType();
                initialisedPerformances.put(person, tempRecords);
            } catch (IllegalValueException | IOException e) {
                e.printStackTrace();
            }
        });

        final HashMap<Person, List<Record>> modelPerformances = initialisedPerformances;
        return new Event(modelName, modelPerformances);
    }
}
