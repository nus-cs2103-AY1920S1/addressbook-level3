package seedu.tarence.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.Date;
import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.builder.EventBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Deletes assignment in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class EditEventCommand extends EventCommand {

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "%1$s edited successfully";
    public static final String COMMAND_WORD = "editEvent";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "edev", "editev", "edite"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an event for a tutorial.\n"
            + "Parameters:\n"
            + PREFIX_INDEX + "TARGET EVENT INDEX "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_NAME + "EVENT NAME (OPTIONAL) "
            + PREFIX_START_DATE + "START TIME (OPTIONAL) "
            + PREFIX_END_DATE + "END TIME (OPTIONAL)\n"
            + PREFIX_INDEX + "TARGET TUTORIAL INDEX "
            + PREFIX_INDEX + "TARGET EVENT INDEX "
            + PREFIX_NAME + "EVENT NAME (OPTIONAL) "
            + PREFIX_START_DATE + "START TIME (OPTIONAL) "
            + PREFIX_END_DATE + "END TIME (OPTIONAL)\n"
            + "Note:\n"
            + "Tutorial index and event index must be specified in the order shown above.\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_NAME + "Lab01 "
            + PREFIX_START_DATE + "09-11-2001 0000 "
            + PREFIX_END_DATE + "31-10-2019 2359\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "1 "
            + PREFIX_NAME + "Lab01 "
            + PREFIX_START_DATE + "09-11-2001 0000 "
            + PREFIX_END_DATE + "31-10-2019 2359\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    public EditEventCommand(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
            String eventName, Date startTime, Date endTime) {
        super(modCode, tutName, tutIndex, eventIndex, eventName, startTime, endTime);
        requireNonNull(eventIndex);
    }

    public EditEventCommand() {
        super();
    }

    @Override
    public EventCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
            String eventName, Date startTime, Date endTime) {
        return new EditEventCommand(modCode, tutName, tutIndex, eventIndex,
                eventName, startTime, endTime);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Consider cases with multiple matching tutorials, students?
        Tutorial targetTutorial = null;
        if (targetModCode.isPresent() && targetTutName.isPresent()) {
            // format with modcode and tutname
            targetTutorial = lastShownList.stream()
                    .filter(tut -> tut.getTutName().equals(targetTutName.get())
                    && tut.getModCode().equals(targetModCode.get()))
                    .findFirst()
                    .orElse(null);
            if (targetTutorial == null) {
                return handleSuggestedCommands(model,
                        new EditEventCommand());
            }
        } else if (targetTutIndex.isPresent()) {
            // format with tutorial index
            try {
                targetTutorial = lastShownList.get(targetTutIndex.get().getZeroBased());
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX));
            }
        }

        Event targetEvent;
        try {
            targetEvent = targetTutorial.getEventLog().get(targetEventIndex.get().getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        EventBuilder eventBuilder = new EventBuilder(targetEvent);
        if (eventName.isPresent()) {
            eventBuilder = eventBuilder.withEventName(eventName.get());
        }
        if (startTime.isPresent()) {
            eventBuilder = eventBuilder.withStartTime(startTime.get());
        }
        if (endTime.isPresent()) {
            eventBuilder = eventBuilder.withEndTime(endTime.get());
        }
        Event editedEvent = eventBuilder.build();

        targetTutorial.deleteEvent(targetEvent);
        targetTutorial.addEvent(editedEvent);

        return new CommandResult(
                    String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent.eventName));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
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
                || (other instanceof EditEventCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }


}
