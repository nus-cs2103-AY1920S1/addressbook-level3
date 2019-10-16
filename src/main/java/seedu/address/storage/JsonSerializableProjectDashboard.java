package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.task.Task;
import seedu.address.model.member.Member;
import seedu.address.model.mapping.Mapping;

/**
 * An Immutable ProjectDashboard that is serializable to JSON format.
 */
// TODO change serializable object root name
@JsonRootName(value = "projectdashboard")
class JsonSerializableProjectDashboard {

    public static final String MESSAGE_DUPLICATE_TASKS = "Tasks list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_MEMBERS = "Members list contains duplicate member(s).";
    public static final String MESSAGE_DUPLICATE_MAPPINGS = "Mappings list contains duplicate mapping(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedMapping> mappings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProjectDashboard} with the given task.
     */
    @JsonCreator
    public JsonSerializableProjectDashboard(@JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                            @JsonProperty("members") List<JsonAdaptedMember> members,
                                            @JsonProperty("mappings") List<JsonAdaptedMapping> mappings) {
        this.tasks.addAll(tasks);
        this.members.addAll(members);
        this.mappings.addAll(mappings);
    }

    /**
     * Converts a given {@code ReadOnlyProjectDashboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProjectDashboard}.
     */
    public JsonSerializableProjectDashboard(ReadOnlyProjectDashboard source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new).collect(Collectors.toList()));
        mappings.addAll(source.getMappingList().stream().map(JsonAdaptedMapping::new).collect(Collectors.toList()));
    }

    /**
     * Converts this project information into the model's {@code ProjectDashboard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProjectDashboard toModelType() throws IllegalValueException {
        ProjectDashboard projectDashboard = new ProjectDashboard();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (projectDashboard.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASKS);
            }
            projectDashboard.addTask(task);
        }
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
//            if (projectDashboard.hasMember(member)) {
//                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBERS);
//            }
            projectDashboard.addMember(member);
        }
        for (JsonAdaptedMapping jsonAdaptedMapping : mappings) {
            Mapping mapping = jsonAdaptedMapping.toModelType();
//            if (projectDashboard.hasMember(member)) {
//                throw new IllegalValueException(MESSAGE_DUPLICATE_MAPPINGS);
//            }
            projectDashboard.addMapping(mapping);
        }
        return projectDashboard;
    }

}
