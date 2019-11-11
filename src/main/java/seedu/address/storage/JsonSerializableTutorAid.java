package seedu.address.storage;

import static seedu.address.logic.commands.AddEarningsCommand.MESSAGE_DUPLICATE_EARNINGS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.TutorAid;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;
import seedu.address.storage.commands.JsonAdaptedCommand;
import seedu.address.storage.earnings.JsonAdaptedEarnings;


/**
 * An Immutable TutorAid that is serializable to JSON format.
 */
@JsonRootName(value = "tutoraid")
class JsonSerializableTutorAid {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_COMMAND = "Commands list contains duplicate command(s).";
    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminder list contains duplicate reminder(s).";
    public static final String MESSAGE_DUPLICATE_NOTE = "Notes list contains duplicate note(s)";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEarnings> earning = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedCommand> commands = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorAid} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTutorAid(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                    @JsonProperty("earning") List<JsonAdaptedEarnings> earning,
                                    @JsonProperty("commands") List<JsonAdaptedCommand> commands,
                                    @JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                    @JsonProperty("reminders") List<JsonAdaptedReminder> reminders,
                                    @JsonProperty("notes") List<JsonAdaptedNote> notes) {

        this.persons.addAll(persons);
        this.earning.addAll(earning);
        this.commands.addAll(commands);
        this.tasks.addAll(tasks);
        this.reminders.addAll(reminders);
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyTutorAid} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorAid}.
     */
    public JsonSerializableTutorAid(ReadOnlyTutorAid source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        earning.addAll(source.getEarningsList().stream().map(JsonAdaptedEarnings::new).collect(Collectors.toList()));
        commands.addAll(source.getCommandsList().stream().map(JsonAdaptedCommand::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
        notes.addAll(source.getNotesList().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TutorAid} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorAid toModelType() throws IllegalValueException {
        TutorAid tutorAid = new TutorAid();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (tutorAid.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            tutorAid.addPerson(person);
        }
        for (JsonAdaptedEarnings jsonAdaptedEarnings : earning) {
            Earnings earnings = jsonAdaptedEarnings.toModelType();
            if (tutorAid.hasEarnings(earnings)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EARNINGS);
            }
            tutorAid.addEarnings(earnings);
        }

        for (JsonAdaptedCommand jsonAdaptedCommand : commands) {
            CommandObject command = jsonAdaptedCommand.toModelType();
            if (tutorAid.hasCommand(command)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMMAND);
            }
            tutorAid.addCommand(command);
        }

        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (tutorAid.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            tutorAid.addTask(task);
        }

        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (tutorAid.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            tutorAid.addReminder(reminder);
        }

        for (JsonAdaptedNote jsonAdaptedNotes : notes) {
            Notes notes = jsonAdaptedNotes.toModelType();
            if (tutorAid.hasNotes(notes)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            tutorAid.addNotes(notes);
        }
        return tutorAid;
    }

}
