package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.Predicates;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * Implements the find command for participants.
 */
public class FindParticipantCommand extends FindCommand {
    public static final String COMMAND_WORD = "find participant";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the participant by the name "
            + "given. Parameters: name to search for "
            + "and/or phone and/or email to search for "
            + "Example: " + COMMAND_WORD + " n/John Doe";
    public static final String MESSAGE_SUCCESS = "Successfully ran the find command.";

    private Predicate<Participant> findPredicate;

    public FindParticipantCommand(
            Optional<String> name,
            Optional<String> email,
            Optional<String> phone
    ) {
        List<Predicate<Participant>> filterPredicates = new ArrayList<>();
        if (name.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByName(name.get()));
        }

        if (email.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByEmail(email.get()));
        }

        if (phone.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByPhone(phone.get()));
        }

        this.findPredicate = Predicates.predicateReducer(filterPredicates);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Participant> results = model.findParticipant(this.findPredicate);
        listResults(results, PrefixType.P);
        model.updateHistory(this);
        return new CommandResult(MESSAGE_SUCCESS, CommandType.P);
    }
}
