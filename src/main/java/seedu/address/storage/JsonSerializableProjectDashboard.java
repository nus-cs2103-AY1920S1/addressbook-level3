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
import seedu.address.model.calendar.CalendarWrapper;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.TasMemMapping;

/**
 * An Immutable ProjectDashboard that is serializable to JSON format.
 */
@JsonRootName(value = "projectdashboard")
class JsonSerializableProjectDashboard {

    public static final String MESSAGE_DUPLICATE_TASKS = "Tasks list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_INVENTORIES = "Inventory list contains duplicate inventory(s).";
    public static final String MESSAGE_DUPLICATE_MEMBERS = "Members list contains duplicate member(s).";
    public static final String MESSAGE_DUPLICATE_MAPPINGS = "Mappings list contains duplicate mapping(s).";
    public static final String MESSAGE_DUPLICATE_CALENDARS = "Mappings list contains duplicate calendar(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedInventory> inventory = new ArrayList<>();
    private final List<JsonAdaptedInvMemMapping> invMemMappings = new ArrayList<>();
    private final List<JsonAdaptedInvTasMapping> invTasMappings = new ArrayList<>();
    private final List<JsonAdaptedTasMemMapping> tasMemMappings = new ArrayList<>();
    private final List<JsonAdaptedCalendar> calendars = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProjectDashboard} with the given task.
     */
    @JsonCreator
    public JsonSerializableProjectDashboard(@JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                            @JsonProperty("members") List<JsonAdaptedMember> members,
                                            @JsonProperty("invMemMappings")
                                                        List<JsonAdaptedInvMemMapping> invMemMappings,
                                            @JsonProperty("invTasMappings")
                                                        List<JsonAdaptedInvTasMapping> invTasMappings,
                                            @JsonProperty("tasMemMappings")
                                                        List<JsonAdaptedTasMemMapping> tasMemMappings,
                                            @JsonProperty("inventory") List<JsonAdaptedInventory> inventory,
                                            @JsonProperty("calendars") List<JsonAdaptedCalendar> calendars) {
        this.tasks.addAll(tasks);
        this.inventory.addAll(inventory);
        this.members.addAll(members);
        this.invMemMappings.addAll(invMemMappings);
        this.invTasMappings.addAll(invTasMappings);
        this.tasMemMappings.addAll(tasMemMappings);
        this.calendars.addAll(calendars);
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
        invMemMappings.addAll(source.getInvMemMappingList().stream().map(JsonAdaptedInvMemMapping::new)
                .collect(Collectors.toList()));
        invTasMappings.addAll(source.getInvTasMappingList().stream().map(JsonAdaptedInvTasMapping::new)
                .collect(Collectors.toList()));
        tasMemMappings.addAll(source.getTasMemMappingList().stream().map(JsonAdaptedTasMemMapping::new)
                .collect(Collectors.toList()));
        calendars.addAll(source.getCalendarList().stream().map(JsonAdaptedCalendar::new)
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
        for (JsonAdaptedInvMemMapping jsonAdaptedInvMemMapping : invMemMappings) {
            InvMemMapping invMemMapping = jsonAdaptedInvMemMapping.toModelType();
            if (projectDashboard.hasMapping(invMemMapping)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MAPPINGS);
            }
            projectDashboard.addMapping(invMemMapping);
        }
        for (JsonAdaptedInvTasMapping jsonAdaptedInvTasMapping : invTasMappings) {
            InvTasMapping invTasMapping = jsonAdaptedInvTasMapping.toModelType();
            if (projectDashboard.hasMapping(invTasMapping)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MAPPINGS);
            }
            projectDashboard.addMapping(invTasMapping);
        }
        for (JsonAdaptedTasMemMapping jsonAdaptedTasMemMapping : tasMemMappings) {
            TasMemMapping tasMemMapping = jsonAdaptedTasMemMapping.toModelType();
            if (projectDashboard.hasMapping(tasMemMapping)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MAPPINGS);
            }
            projectDashboard.addMapping(tasMemMapping);
        }
        for (JsonAdaptedCalendar jsonAdaptedCalendar : calendars) {
            CalendarWrapper calendar = jsonAdaptedCalendar.toModelType();
            if (projectDashboard.hasCalendar(calendar)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CALENDARS);
            }
            projectDashboard.addCalendar(calendar);
        }
        return projectDashboard;
    }
}
