package seedu.tarence.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
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
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.exceptions.DuplicateEventException;
import seedu.tarence.storage.Storage;

/**
 * Adds Event to a Tutorial.
 * Keyword matching is case insensitive.
 */
public class AddEventCommand extends EventCommand {

    public static final String MESSAGE_ADD_EVENT_SUCCESS = "%1$s added successfully";
    public static final String COMMAND_WORD = "addEvent";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "adde", "addev", "addevnt"};

    // TODO: Update message to include index format
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an event for a tutorial.\n"
            + "Parameters:\n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_NAME + "EVENT NAME "
            + PREFIX_START_DATE + "START TIME "
            + PREFIX_END_DATE + "END TIME\n"
            + PREFIX_INDEX + "TUTORIAL INDEX "
            + PREFIX_NAME + "EVENT NAME "
            + PREFIX_START_DATE + "START TIME "
            + PREFIX_END_DATE + "END TIME\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_NAME + "Lab01 "
            + PREFIX_START_DATE + "09-11-2001 0000 "
            + PREFIX_END_DATE + "31-10-2019 2359\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_NAME + "Lab01 "
            + PREFIX_START_DATE + "09-11-2001 0000 "
            + PREFIX_END_DATE + "31-10-2019 2359\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    public AddEventCommand(ModCode modCode, TutName tutName, Index tutIndex,
            String eventName, Date startTime, Date endTime) {
        super(modCode, tutName, tutIndex, null, eventName, startTime, endTime);
        requireAllNonNull(eventName, startTime, endTime);
    }

    public AddEventCommand() {
        super();
    }

    @Override
    public EventCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
            String eventName, Date startTime, Date endTime) {
        return new AddEventCommand(modCode, tutName, tutIndex, eventName, startTime, endTime);
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
                return handleSuggestedCommands(model, new AddEventCommand());
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

        try {
            targetTutorial.addEvent(new Event(
                eventName.get(),
                startTime.get(),
                endTime.get()));
        } catch (IllegalArgumentException | DuplicateEventException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(
                String.format(MESSAGE_ADD_EVENT_SUCCESS, eventName.get()));
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
                || (other instanceof AddEventCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }


}
