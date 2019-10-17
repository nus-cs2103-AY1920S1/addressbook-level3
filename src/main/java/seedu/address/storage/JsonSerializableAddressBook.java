package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.note.Note;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_TITLE = "Lecture note list contains duplicate titles.";
    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate tasks.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "Question list contains duplicate questions.";
    public static final String MESSAGE_DUPLICATE_RESULT = "Quiz result contains duplicate results.";

    private final List<JsonAdaptedNote> notes = new ArrayList<>();
    private final List<JsonAdaptedTaskForNote> tasks = new ArrayList<>();
    private final List<JsonAdaptedQuizResult> quizResults = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given lecture notes, tasks and questions.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("notes") List<JsonAdaptedNote> notes,
                                       @JsonProperty("tasks") List<JsonAdaptedTaskForNote> tasks,
                                       @JsonProperty("quizResults") List<JsonAdaptedQuizResult> quizResults) {
        this.notes.addAll(notes);
        this.tasks.addAll(tasks);
        this.quizResults.addAll(quizResults);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        notes.addAll(source.getNoteList().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTaskForNote::new).collect(Collectors.toList()));
        quizResults.addAll(source.getQuizResultList().stream().map(JsonAdaptedQuizResult::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (addressBook.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TITLE);
            }
            addressBook.addNote(note);
        }

        for (JsonAdaptedTaskForNote jsonAdaptedTaskForNote : tasks) {
            Task task = jsonAdaptedTaskForNote.toModelType();
            if (addressBook.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            addressBook.addTask(task);
        }

        for (JsonAdaptedQuizResult jsonAdaptedQuizResult : quizResults) {
            QuizResult quizResult = jsonAdaptedQuizResult.toModelType();
            if (addressBook.hasQuizResult(quizResult)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESULT);
            }
            addressBook.addQuizResult(quizResult);
        }
        return addressBook;
    }
}