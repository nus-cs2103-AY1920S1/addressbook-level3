package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Notebook;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.classroom.Classroom;

/**
 * An Immutable Notebook that is serializable to JSON format.
 */
@JsonRootName(value = "notebook")
class JsonSerializableNotebook {

    public static final String MESSAGE_DUPLICATE_CLASSROOM = "Classroom list contains duplicate classrooms";

    private final List<JsonSerializableClassroom> classrooms = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNotebook} with the given students.
     */
    @JsonCreator
    public JsonSerializableNotebook(@JsonProperty("classrooms") List<JsonSerializableClassroom> classrooms) {
        this.classrooms.addAll(classrooms);
    }

    /**
     * Converts a given {@code Notebook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNotebook}.
     */
    public JsonSerializableNotebook(ReadOnlyNotebook source) {
        classrooms.addAll(source.getClassroomList().stream()
                                  .map(JsonSerializableClassroom::new).collect(Collectors.toList()));

    }

    /**
     * Converts this classroom into the model's {@code Notebook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Notebook toModelType() throws IllegalValueException {
        Notebook notebook = new Notebook();
        for (JsonSerializableClassroom jsc : classrooms) {
            Classroom classroom = jsc.toModelType();
            if (notebook.hasClassroom(classroom)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLASSROOM);
            }
            notebook.addClassroom(classroom);
        }
        return notebook;
    }

}
