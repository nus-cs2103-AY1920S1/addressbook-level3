package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Sets start of semester.
 * Keyword matching is case insensitive.
 */
public class SetSemStartCommand extends Command {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String MESSAGE_SET_SEM_START_SUCCESS = "Start of semester is now: %s";
    public static final String MESSAGE_PREVIOUS_SEM_START_HAS_BEEN_CHANGED = "Previous semester start was: %s";
    public static final String COMMAND_WORD = "setSemStart";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "setstart", "setst", "setsem"};

    // TODO: Update message to include index format
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets start of semester.\n"
            + "Parameters:\n"
            + PREFIX_START_DATE + "SEMESTER START DATE\n"
            + "Note:\n"
            + "Setting the start date of the semester will determine the dates of tutorials\n"
            + "and will automatically update events in all tutorials.\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_START_DATE + "31-12-2001 1200\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private final Date semStart;

    public SetSemStartCommand(Date semStart) {
        this.semStart = semStart;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // Removes outdated events when semStart changes
        for (Tutorial tutorial : lastShownList) {
            List<Event> tutEvents = tutorial.getTutorialasEvents();
            for (Event tutEvent : tutEvents) {
                tutorial.deleteEvent(tutEvent);
            }
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String previousDateMessage = "";

        // Check if semStart is null
        if (Module.hasSemesterStartBeenSet()) {

            if (isSameDate()) {
                throw new CommandException(String.format(
                        Messages.MESSAGE_INVALID_SEMESTER_START_DATE, dateFormat.format(Module.getSemStart())));
            }

            Date previousSemesterStart = Module.getSemStart();
            previousDateMessage = String.format(MESSAGE_PREVIOUS_SEM_START_HAS_BEEN_CHANGED + "\n",
                    dateFormat.format(previousSemesterStart));
        }


        Module.setSemStart(semStart);

        String successMessage = String.format(MESSAGE_SET_SEM_START_SUCCESS + "\n",
                dateFormat.format(semStart));
        String outputMessage = previousDateMessage + successMessage;

        return new CommandResult(outputMessage);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Compares if the day, month and year of the new semester start date and old semester start date are the same.
     *
     * @return Boolean
     */
    public Boolean isSameDate() {
        Date currentDate = Module.getSemStart();
        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date newDate = semStart;
        LocalDate localNewDate = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (localCurrentDate.getYear() == localNewDate.getYear())
                && (localCurrentDate.getMonth() == localNewDate.getMonth())
                && (localCurrentDate.getDayOfMonth() == localNewDate.getDayOfMonth());
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetSemStartCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }
}
