package seedu.jarvis.storage.history.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedAddAddressCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedClearAddressCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedDeleteAddressCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedEditAddressCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedAddCcaCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedAddProgressCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedDeleteCcaCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedEditCcaCommand;
import seedu.jarvis.storage.history.commands.course.JsonAdaptedAddCourseCommand;
import seedu.jarvis.storage.history.commands.course.JsonAdaptedDeleteCourseCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedEditInstallmentCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedRemoveInstallmentCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedRemovePaidCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetInstallmentCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetMonthlyLimitCommand;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetPaidCommand;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedAddTaskCommand;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedDeleteTaskCommand;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedDoneTaskCommand;

/**
 * Abstract class that represents a Jackson-Friendly command.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        // addressbook
        @Type(value = JsonAdaptedAddAddressCommand.class, name = "JsonAdaptedAddAddressCommand"),
        @Type(value = JsonAdaptedClearAddressCommand.class, name = "JsonAdaptedClearAddressCommand"),
        @Type(value = JsonAdaptedDeleteAddressCommand.class, name = "JsonAdaptedDeleteAddressCommand"),
        @Type(value = JsonAdaptedEditAddressCommand.class, name = "JsonAdaptedEditAddressCommand"),
        // courseplanner
        @Type(value = JsonAdaptedAddCourseCommand.class, name = "JsonAdaptedAddCourseCommand"),
        @Type(value = JsonAdaptedDeleteCourseCommand.class, name = "JsonAdaptedDeleteCourseCommand"),
        // financetracker
        @Type(value = JsonAdaptedEditInstallmentCommand.class, name = "JsonAdaptedEditInstallmentCommand"),
        @Type(value = JsonAdaptedSetPaidCommand.class, name = "JsonAdaptedSetPaidCommand"),
        @Type(value = JsonAdaptedSetInstallmentCommand.class, name = "JsonAdaptedSetInstallmentCommand"),
        @Type(value = JsonAdaptedRemovePaidCommand.class, name = "JsonAdaptedRemovePaidCommand"),
        @Type(value = JsonAdaptedRemoveInstallmentCommand.class, name = "JsonAdaptedRemoveInstallmentCommand"),
        @Type(value = JsonAdaptedEditInstallmentCommand.class, name = "JsonAdaptedEditInstallmentCommand"),
        @Type(value = JsonAdaptedSetMonthlyLimitCommand.class, name = "JsonAdaptedSetMonthlyLimitCommand"),
        // planner
        @Type(value = JsonAdaptedAddTaskCommand.class, name = "JsonAdaptedAddTaskCommand"),
        @Type(value = JsonAdaptedDeleteTaskCommand.class, name = "JsonAdaptedDeleteTaskCommand"),
        @Type(value = JsonAdaptedDoneTaskCommand.class, name = "JsonAdaptedDoneTaskCommand"),
        // ccatracker
        @Type(value = JsonAdaptedAddCcaCommand.class, name = "JsonAdaptedAddCcaCommand"),
        @Type(value = JsonAdaptedAddProgressCommand.class, name = "JsonAdaptedAddProgressCommand"),
        @Type(value = JsonAdaptedDeleteCcaCommand.class, name = "JsonAdaptedDeleteCcaCommand"),
        @Type(value = JsonAdaptedEditCcaCommand.class, name = "JsonAdaptedEditCcaCommand")
})
public abstract class JsonAdaptedCommand implements JsonAdapter<Command> {
    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException If there were any data constraints violated in the adapted command.
     */
    @Override
    public abstract Command toModelType() throws IllegalValueException;
}
