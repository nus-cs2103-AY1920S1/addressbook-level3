package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.ScheduleCommand;
import seedu.exercise.logic.commands.ScheduleCompleteCommand;
import seedu.exercise.logic.commands.ScheduleRegimeCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Schedule;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

    public static final String MESSAGE_COMMAND_NOT_UNDOABLE =
        "The command \'%1$s\' cannot be stored as an undoable event.";
    public static final String MESSAGE_UNIQUE_IDENTIFIER_NOT_FOUND =
            "The resource type \'%1$s\' of the \'%2$s\' command is not known.";

    /**
     * Generates an Event object that can execute the behaviour of a given Command as well
     * as its opposite behaviour.
     *
     * @param command a {@code UndoableCommand} to be represented with using an Event object
     * @return an {@code Event} that can be undone or redone
     * @throws CommandException if command provided is not undoable
     */
    public static Event commandToEvent(UndoableCommand command) throws CommandException {
        String commandWord = command.getUndoableCommandWord();

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return generateEventFromAddCommand((AddCommand) command);

        case DeleteCommand.COMMAND_WORD:
            return generateEventFromDeleteCommand((DeleteCommand) command);

        case EditCommand.COMMAND_WORD:
            return new EditExerciseEvent(((EditCommand) command).getPayload());

        case ClearCommand.COMMAND_WORD:
            return new ClearEvent(((ClearCommand) command).getPayload());

        case ScheduleCommand.COMMAND_WORD:
            return generateEventFromScheduleCommand((ScheduleCommand) command);

        case ResolveCommand.COMMAND_WORD:
            return new ResolveEvent(((ResolveCommand) command).getPayload());

        default:
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, commandWord));
        }
    }

    /**
     * Generates a schedule regime or schedule complete event based on the command type.
     *
     * @param command a {@link ScheduleCommand} to be represented with using an Event object
     * @return a {@link ScheduleRegimeEvent} or a {@link ScheduleCompleteEvent}
     * that can be undone or redone
     */
    static Event generateEventFromScheduleCommand(ScheduleCommand command) throws CommandException {
        String resourceType = command.getCommandTypeIdentifier();
        EventPayload<Schedule> eventPayload;
        switch (resourceType) {
        case ScheduleRegimeCommand.UNIQUE_IDENTIFIER:
            eventPayload = ((ScheduleRegimeCommand) command).getPayload();
            return new ScheduleRegimeEvent(eventPayload);

        case ScheduleCompleteCommand.UNIQUE_IDENTIFIER:
            eventPayload = ((ScheduleCompleteCommand) command).getPayload();
            return new ScheduleCompleteEvent(eventPayload);

        default:
            throw new CommandException(
                    String.format(MESSAGE_UNIQUE_IDENTIFIER_NOT_FOUND,
                            resourceType,
                            command.getUndoableCommandWord()));
        }
    }

    /**
     * Generates a add exercise or add regime event based on the command type.
     *
     * @param command a {@link AddCommand} to be represented with using an Event object
     * @return an {@link AddExerciseEvent}, {@link AddRegimeEvent} or {@link EditRegimeEvent}
     * that can be undone or redone
     */
    protected static Event generateEventFromAddCommand(AddCommand command) throws CommandException {
        String resourceType = command.getCommandTypeIdentifier();
        switch (resourceType) {
        case AddExerciseCommand.RESOURCE_TYPE:
            EventPayload<Exercise> eventPayload = ((AddExerciseCommand) command).getPayload();
            return new AddExerciseEvent(eventPayload);

        case AddRegimeCommand.RESOURCE_TYPE:
            AddRegimeCommand addRegimeCommand = (AddRegimeCommand) command;
            boolean isRegimeEdited = (boolean) addRegimeCommand
                    .getPayload()
                    .get(EditRegimeEvent.KEY_IS_REGIME_EDITED);

            if (isRegimeEdited) {
                return new EditRegimeEvent(addRegimeCommand.getPayload());
            } else {
                return new AddRegimeEvent(addRegimeCommand.getPayload());
            }

        default:
            throw new CommandException(
                    String.format(MESSAGE_UNIQUE_IDENTIFIER_NOT_FOUND,
                            resourceType,
                            command.getUndoableCommandWord()));
        }
    }

    /**
     * Generates a delete exercise or delete regime event based on the command type.
     *
     * @param command a {@link DeleteCommand} to be represented with using an Event object
     * @return an {@link DeleteExerciseEvent}, {@link DeleteRegimeEvent} or {@link EditRegimeEvent}
     * that can be undone or redone
     */
    protected static Event generateEventFromDeleteCommand(DeleteCommand command) throws CommandException {
        String resourceType = command.getCommandTypeIdentifier();
        switch (resourceType) {
        case DeleteExerciseCommand.RESOURCE_TYPE:
            EventPayload<Exercise> eventPayload = ((DeleteExerciseCommand) command).getPayload();
            return new DeleteExerciseEvent(eventPayload);

        case DeleteRegimeCommand.RESOURCE_TYPE:
            DeleteRegimeCommand deleteRegimeCommand = (DeleteRegimeCommand) command;
            boolean isRegimeEdited = (boolean) deleteRegimeCommand
                    .getPayload()
                    .get(EditRegimeEvent.KEY_IS_REGIME_EDITED);

            if (isRegimeEdited) {
                return new EditRegimeEvent(deleteRegimeCommand.getPayload());
            } else {
                return new DeleteRegimeEvent(deleteRegimeCommand.getPayload());
            }

        default:
            throw new CommandException(
                    String.format(MESSAGE_UNIQUE_IDENTIFIER_NOT_FOUND,
                            resourceType,
                            command.getUndoableCommandWord()));
        }
    }

}
