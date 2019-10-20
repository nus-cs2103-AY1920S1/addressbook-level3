package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Task;

/**
 * An Immutable AppData that is serializable to JSON format.
 */
@JsonRootName(value = "data")
class JsonSerializableAppData {
    public static final String MESSAGE_DUPLICATE_TITLE = "Lecture note list contains duplicate titles.";
    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate tasks.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "Question list contains duplicate questions.";
    public static final String MESSAGE_DUPLICATE_RESULT = "Quiz result contains duplicate results.";

    private final List<JsonAdaptedNote> notes = new ArrayList<>();
    private final List<JsonAdaptedTaskForNote> tasks = new ArrayList<>();
    private final List<JsonAdaptedQuestion> questions = new ArrayList<>();
    private final List<JsonAdaptedQuizResult> quizResults = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAppData} with the given lecture notes, tasks and questions.
     */
    @JsonCreator
    public JsonSerializableAppData(@JsonProperty("notes") List<JsonAdaptedNote> notes,
                                   @JsonProperty("tasks") List<JsonAdaptedTaskForNote> tasks,
                                   @JsonProperty("questions") List<JsonAdaptedQuestion> questions,
                                   @JsonProperty("quizResults") List<JsonAdaptedQuizResult> quizResults) {
        this.notes.addAll(notes);
        this.tasks.addAll(tasks);
        this.questions.addAll(questions);
        this.quizResults.addAll(quizResults);
    }

    /**
     * Converts a given {@code ReadOnlyAppData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppData}.
     */
    public JsonSerializableAppData(ReadOnlyAppData source) {
        notes.addAll(source.getNoteList().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTaskForNote::new).collect(Collectors.toList()));
        questions.addAll(source.getQuestionList().stream().map(JsonAdaptedQuestion::new).collect(Collectors.toList()));
        quizResults.addAll(source.getQuizResultList().stream().map(JsonAdaptedQuizResult::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AppData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AppData toModelType() throws IllegalValueException {
        AppData appData = new AppData();
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (appData.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TITLE);
            }
            appData.addNote(note);
        }

        for (JsonAdaptedTaskForNote jsonAdaptedTaskForNote : tasks) {
            Task task = jsonAdaptedTaskForNote.toModelType();
            if (appData.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            appData.addTask(task);
        }

        for (JsonAdaptedQuestion jsonAdaptedQuestion : questions) {
            Question question = jsonAdaptedQuestion.toModelType();
            if (appData.hasQuestion(question)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUESTION);
            }
            appData.addQuestion(question);
        }

        for (JsonAdaptedQuizResult jsonAdaptedQuizResult : quizResults) {
            QuizResult quizResult = jsonAdaptedQuizResult.toModelType();
            if (appData.hasQuizResult(quizResult)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESULT);
            }
            appData.addQuizResult(quizResult);
        }
        return appData;
    }
}
