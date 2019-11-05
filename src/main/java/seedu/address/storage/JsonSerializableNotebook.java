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
import seedu.address.model.lesson.Lesson;

/**
 * An Immutable Notebook that is serializable to JSON format.
 */
@JsonRootName(value = "notebook")
class JsonSerializableNotebook {

    public static final String MESSAGE_DUPLICATE_CLASSROOM = "Classroom list contains duplicate classrooms";
    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";

    private final List<JsonSerializableClassroom> classrooms = new ArrayList<>();
    private final List<JsonAdaptedLesson> monLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> tueLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> wedLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> thurLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> friLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> satLessons = new ArrayList<>();
    private final List<JsonAdaptedLesson> sunLessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNotebook} with the given students.
     */
    @JsonCreator
    public JsonSerializableNotebook(@JsonProperty("classrooms") List<JsonSerializableClassroom> classrooms,
                                    @JsonProperty("monLessons") List<JsonAdaptedLesson> monLessons,
                                    @JsonProperty("tueLessons") List<JsonAdaptedLesson> tueLessons,
                                    @JsonProperty("wedLessons") List<JsonAdaptedLesson> wedLessons,
                                    @JsonProperty("thurLessons") List<JsonAdaptedLesson> thurLessons,
                                    @JsonProperty("friLessons") List<JsonAdaptedLesson> friLessons,
                                    @JsonProperty("satLessons") List<JsonAdaptedLesson> satLessons,
                                    @JsonProperty("sunLessons") List<JsonAdaptedLesson> sunLessons) {
        this.classrooms.addAll(classrooms);
        this.monLessons.addAll(monLessons);
        this.tueLessons.addAll(tueLessons);
        this.wedLessons.addAll(wedLessons);
        this.thurLessons.addAll(thurLessons);
        this.friLessons.addAll(friLessons);
        this.satLessons.addAll(satLessons);
        this.sunLessons.addAll(sunLessons);
    }

    /**
     * Converts a given {@code Notebook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNotebook}.
     */
    public JsonSerializableNotebook(ReadOnlyNotebook source) {
        classrooms.addAll(source.getClassroomList().stream()
                                  .map(JsonSerializableClassroom::new).collect(Collectors.toList()));
        monLessons.addAll(source.getLessonWeekList().get(0).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        tueLessons.addAll(source.getLessonWeekList().get(1).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        wedLessons.addAll(source.getLessonWeekList().get(2).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        thurLessons.addAll(source.getLessonWeekList().get(3).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        friLessons.addAll(source.getLessonWeekList().get(4).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        satLessons.addAll(source.getLessonWeekList().get(5).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
        sunLessons.addAll(source.getLessonWeekList().get(6).asUnmodifiableObservableList()
                .stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
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
        for (JsonAdaptedLesson jsonAdaptedLesson : monLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : tueLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : wedLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : thurLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : friLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : satLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : sunLessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (notebook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            notebook.addLesson(lesson);
        }
        return notebook;
    }

}
