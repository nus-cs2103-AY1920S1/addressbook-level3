package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Project;
import seedu.address.model.project.Description;
import seedu.address.model.project.Task;
import seedu.address.model.project.Title;
import seedu.address.model.tag.Tag;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String title;
    private final String description;
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("title") String title, @JsonProperty("description") String description,
                              @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.title = title;
        this.description = description;
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException, ParseException {

        final List<Task> taskList = new ArrayList<>();
        for (JsonAdaptedTask task : tasks) {
            taskList.add(task.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);
        final Set<Task> modelTasks = new HashSet<>(taskList);
        return new Project(modelTitle, modelDescription, modelTasks);
    }

}
