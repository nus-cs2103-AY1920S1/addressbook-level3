package seedu.tarence.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
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
import seedu.tarence.storage.Storage;

/**
 * Lists Events in a Tutorial.
 * Keyword matching is case insensitive.
 */
public class ListEventsCommand extends EventCommand {

    public static final String MESSAGE_LIST_EVENT_SUCCESS = "Hours logged: %d\nEvent log for %s %s:";
    public static final String COMMAND_WORD = "listEvents";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "liste", "listev", "listevnt"};

    // TODO: Update message to include index format
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List events in a tutorial.\n"
            + "Parameters:\n"
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_MODULE + "MODULE CODE\n"
            + PREFIX_INDEX + "TUTORIAL INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    public ListEventsCommand(ModCode modCode, TutName tutName, Index tutIndex) {
        super(modCode, tutName, tutIndex, null, null, null, null);
    }

    public ListEventsCommand() {
        super();
    }

    @Override
    public EventCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index eventIndex,
            String eventName, Date startTime, Date endTime) {
        return new ListEventsCommand(modCode, tutName, tutIndex);
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
                return handleSuggestedCommands(model, new ListEventsCommand());
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

        List<Event> eventLog = targetTutorial.getEventLog();
        StringBuffer stringBuffer = new StringBuffer();
        int index = 1;
        for (Event event : eventLog) {
            stringBuffer.append(index + ". " + event + "\n");
            index++;
        }

        return new CommandResult(
                String.format(MESSAGE_LIST_EVENT_SUCCESS + "\n"
                + stringBuffer.toString(),
                targetTutorial.getHours(),
                targetTutorial.getTutName(),
                targetTutorial.getModCode()));
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
                || (other instanceof ListEventsCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }


}
