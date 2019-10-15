package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

/**
 * An Immutable ProjectDashboard that is serializable to JSON format.
 */
// TODO change serializable object root name
@JsonRootName(value = "projectdashboard")
class JsonSerializableProjectDashboard {

    public static final String MESSAGE_DUPLICATE_TASKS = "Tasks list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_INVENTORIES = "Inventory list contains duplicate inventory(s).";
    public static final String MESSAGE_DUPLICATE_MEMBERS = "Member list contains duplicate member(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedInventory> inventory = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProjectDashboard} with the given task.
     */
    @JsonCreator
    public JsonSerializableProjectDashboard(@JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                            @JsonProperty("members") List<JsonAdaptedMember> members,
                                            @JsonProperty("inventories") List<JsonAdaptedInventory> inventory) {
        this.tasks.addAll(tasks);
        this.inventory.addAll(inventory);
        this.members.addAll(members);
    }

    /**
     * Converts a given {@code ReadOnlyProjectDashboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProjectDashboard}.
     */
    public JsonSerializableProjectDashboard(ReadOnlyProjectDashboard source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new)
                .collect(Collectors.toList()));
        inventory.addAll(source.getInventoryList().stream().map(JsonAdaptedInventory::new)
                .collect(Collectors.toList()));
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

        for (JsonAdaptedInventory jsonAdaptedInv : inventory) {
            Inventory inventory = jsonAdaptedInv.toModelType();
            if (projectDashboard.hasInventory(inventory)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INVENTORIES);
            }
            projectDashboard.addInventory(inventory);
        }

        for (JsonAdaptedMember jsonAdaptedMem: members) {
            Member member = jsonAdaptedMem.toModelType();
            if (projectDashboard.hasMember(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBERS);
            }
            projectDashboard.addMember(member);
        }
        return projectDashboard;
    }

}
