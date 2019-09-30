package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.AlfredException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;

public class ViewParticipantCommand extends ViewCommand {

    /* Possible Fields? */
    public static final String MESSAGE_SUCCESS = "Showed specified participant";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The participant index provided is invalid";

    // Eventually change to take in Name (or add a new constructor)
    public ViewParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Participant participantToView;
        try {
            participantToView = model.getParticipant(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        HashMap<String, String> fieldMap = participantToView.viewDetailed();
        StringBuilder toPrint = new StringBuilder();
        for (String key : fieldMap.keySet()) {
            toPrint.append(StringUtil.capitalize(key))
                   .append(" : ")
                   .append(fieldMap.get(key))
                   .append(" ");
        }
        System.out.println(toPrint.toString().trim());

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
