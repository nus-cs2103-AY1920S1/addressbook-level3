package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TimeBook;
import seedu.address.model.group.Group;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.person.Person;

/**
 * A TimeBook serialized to JSON format.
 */
@JsonRootName(value = "timebook")
class JsonSerializableTimeBook {

    private final List<JsonAdaptedPerson> personList = new ArrayList<>();
    private final List<JsonAdaptedGroup> groupList = new ArrayList<>();
    private final List<JsonAdaptedMapping> mappingList = new ArrayList<>();

    @JsonCreator
    public JsonSerializableTimeBook(@JsonProperty("personList") List<JsonAdaptedPerson> personList,
                                    @JsonProperty("groupList") List<JsonAdaptedGroup> groupList,
                                    @JsonProperty("mappingList") List<JsonAdaptedMapping> mappingList) {

        this.personList.addAll(personList);
        this.groupList.addAll(groupList);
        this.mappingList.addAll(mappingList);
    }

    /**
     * Converts a given {@code TimeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTimeBook}.
     */
    public JsonSerializableTimeBook(TimeBook source) {

        personList.addAll(source.getUnmodifiablePersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));

        groupList.addAll(source.getUnmodifiableGroupList().stream()
                .map(JsonAdaptedGroup::new).collect(Collectors.toList()));

        mappingList.addAll(source.getUnmodifiableMappingList().stream()
                .map(JsonAdaptedMapping::new).collect(Collectors.toList()));

    }

    /**
     * Converts this TimeBook into the model's {@code TimeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TimeBook toModelType() throws IllegalValueException {
        TimeBook timeBook = new TimeBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : personList) {
            Person person = jsonAdaptedPerson.toModelType();
            timeBook.addPerson(person);
        }

        for (JsonAdaptedGroup jsonAdaptedGroup : groupList) {
            Group group = jsonAdaptedGroup.toModelType();
            timeBook.addGroup(group);
        }

        for (JsonAdaptedMapping jsonAdaptedMapping : mappingList) {
            PersonToGroupMapping map = jsonAdaptedMapping.toModelType();
            timeBook.addMapping(map);
        }

        return timeBook;
    }

}
