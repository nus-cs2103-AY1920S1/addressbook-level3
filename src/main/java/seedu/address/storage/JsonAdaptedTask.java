package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonAdaptedPerson.java
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
=======
>>>>>>> team/master:src/main/java/seedu/address/storage/JsonAdaptedTask.java
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final TaskStatus taskStatus;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonAdaptedPerson.java
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
=======
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                           @JsonProperty("email") String email,
=======
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("status") TaskStatus taskStatus,
>>>>>>> team/master
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
>>>>>>> team/master:src/main/java/seedu/address/storage/JsonAdaptedTask.java
        this.name = name;
        this.taskStatus = taskStatus;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName().fullName;
        taskStatus = source.getTaskStatus();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (taskStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskStatus.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final TaskStatus modelTaskStatus = TaskStatus.valueOf(taskStatus.name());
        final Set<Tag> modelTags = new HashSet<>(personTags);
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonAdaptedPerson.java
        return new Person(modelName, modelPhone, modelEmail, modelTags);
=======
        return new Task(modelName, modelPhone, modelEmail, modelTags);
>>>>>>> team/master:src/main/java/seedu/address/storage/JsonAdaptedTask.java
=======
        return new Task(modelName, modelTaskStatus, modelTags);
>>>>>>> team/master
    }

}
