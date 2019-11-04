package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.Predicates;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.findcommandparser.FindCommandUtilEnum;
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

    private String nameNorm;
    private String emailNorm;
    private String phoneNorm;
    private String nameExclude;
    private String emailExclude;
    private String phoneExclude;
    private Predicate<Participant> findPredicate;

    public FindParticipantCommand(
            FindCommandUtilEnum type,
            Optional<String> nameNorm,
            Optional<String> emailNorm,
            Optional<String> phoneNorm,
            Optional<String> nameExclude,
            Optional<String> emailExclude,
            Optional<String> phoneExclude
    ) {
        List<Predicate<Participant>> filterPredicates = new ArrayList<>();
        if (nameNorm.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByName(nameNorm.get(), false));
        }

        if (emailNorm.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByEmail(emailNorm.get(), false));
        }

        if (phoneNorm.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByPhone(phoneNorm.get(), false));
        }

        if (nameExclude.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByName(nameNorm.get(), true));
        }

        if (emailExclude.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByEmail(emailNorm.get(), true));
        }

        if (phoneExclude.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindParticipantByPhone(phoneNorm.get(), true));
        }

        this.findPredicate = type == FindCommandUtilEnum.AND
                ? Predicates.predicateReducer(filterPredicates)
                : Predicates.predicateReducerOr(filterPredicates);

        this.nameNorm = nameNorm.orElse("");
        this.emailNorm = emailNorm.orElse("");
        this.phoneNorm = emailNorm.orElse("");
        this.phoneNorm = emailNorm.orElse("");
        this.nameExclude = nameExclude.orElse("");
        this.emailExclude = emailExclude.orElse("");
        this.phoneExclude = phoneExclude.orElse("");
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Participant> results = model.findParticipant(this.findPredicate);
        listResults(results, PrefixType.P);
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.P);
    }

    @Override
    public boolean equals(Object otherFindCommand) {
        if (otherFindCommand == this) {
            return true;
        }

        if (!(otherFindCommand instanceof FindParticipantCommand)) {
            return false;
        }

        return nameNorm.equals(((FindParticipantCommand) otherFindCommand).nameNorm)
                && emailNorm.equals(((FindParticipantCommand) otherFindCommand).emailNorm)
                && phoneNorm.equals(((FindParticipantCommand) otherFindCommand).phoneNorm)
                && nameExclude.equals(((FindParticipantCommand) otherFindCommand).nameExclude)
                && emailExclude.equals(((FindParticipantCommand) otherFindCommand).emailExclude)
                && phoneExclude.equals(((FindParticipantCommand) otherFindCommand).phoneExclude);
    }
}
