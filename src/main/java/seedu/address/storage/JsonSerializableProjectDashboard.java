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

/**
 * An Immutable ProjectDashboard that is serializable to JSON format.
 */
// TODO change serializable object root name
@JsonRootName(value = "projectdashboard")
class JsonSerializableProjectDashboard {

    public static final String MESSAGE_DUPLICATE_TASKS = "Tasks list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final HashMap<JsonAdaptedMember, HashSet<JsonAdaptedTask>> memberTaskMapping = new HashMap<>();
    private final HashMap<JsonAdaptedTask, HashSet<JsonAdaptedMember>> taskMemberMapping = new HashMap<>();

    /**
     * Constructs a {@code JsonSerializableProjectDashboard} with the given task.
     */
    @JsonCreator
    public JsonSerializableProjectDashboard(@JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                            @JsonProperty("members") List<JsonAdaptedMember> members,
                                            @JsonProperty("mappings") List<JsonAdaptedMapping> mappings) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyProjectDashboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProjectDashboard}.
     */
    public JsonSerializableProjectDashboard(ReadOnlyProjectDashboard source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new).collect(Collectors.toList()));
        HashMap<Member, HashSet<Task>> memberTaskMappingRaw = source.getMemberTaskMapping();
        for (Member member : memberTaskMappingRaw.keySet()) {
            JsonAdaptedMember adaptedMember = new JsonAdaptedMember(member);
            if (memberTaskMapping.get(adaptedMember) == null) {
                memberTaskMapping.put(adaptedMember, new HashSet<JsonAdaptedTask>());
            }
            HashSet<JsonAdaptedTask> taskSet = memberTaskMapping.get(adaptedMember);
            for (Task task : memberTaskMappingRaw.get(member)) {
                taskSet.add(new JsonAdaptedTask(task));
            }
        }
        HashMap<Task, HashSet<Member>> taskMemberMappingRaw = source.getTaskMemberMapping();
        for (Task task : taskMemberMappingRaw.keySet()) {
            JsonAdaptedTask adaptedTask = new JsonAdaptedTask(task);
            if (taskMemberMapping.get(adaptedTask) == null) {
                taskMemberMapping.put(adaptedTask, new HashSet<JsonAdaptedMember>());
            }
            HashSet<JsonAdaptedMember> memberSet = taskMemberMapping.get(adaptedTask);
            for (Member member : taskMemberMappingRaw.get(task)) {
                memberSet.add(new JsonAdaptedMember(member));
            }
        }
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
            if (projectDashboard.hasMember(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASKS);
            }
            projectDashboard.addMember(member);
        }
        for (JsonAdaptedMember adaptedMember : memberTaskMapping.keySet()) {
            HashSet<JsonAdaptedTask> taskSet = memberTaskMapping.get(adaptedMember);
            for (JsonAdaptedTask adaptedTask : taskSet) {
                projectDashboard.mapMemberTask(adaptedMember.toModelType(), adaptedTask.toModelType());
            }
        }
        return projectDashboard;
    }

}
