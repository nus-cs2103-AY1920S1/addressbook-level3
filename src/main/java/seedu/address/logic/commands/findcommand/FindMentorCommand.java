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
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.PrefixType;

/**
 * Implements the find command for mentors.
 */
public class FindMentorCommand extends FindCommand {
    public static final String COMMAND_WORD = "find mentor";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the mentor by the name "
            + "given. Parameters: name to search for "
            + "and/or phone and/or email and/or organization to search for "
            + "Example: " + COMMAND_WORD + " n/John Doe";
    public static final String MESSAGE_SUCCESS = "Successfully ran the find command.";

    private String nameNorm;
    private String emailNorm;
    private String phoneNorm;
    private String organizationNorm;
    private String nameExclude;
    private String emailExcude;
    private String phoneExclude;
    private String organizationExclude;
    private Predicate<Mentor> findPredicate;

    public FindMentorCommand(
            FindCommandUtilEnum type,
            Optional<String> nameNorm,
            Optional<String> emailNorm,
            Optional<String> phoneNorm,
            Optional<String> organizationNorm,
            Optional<String> nameExclude,
            Optional<String> emailExclude,
            Optional<String> phoneExclude,
            Optional<String> organizationExclude
    ) {
        List<Predicate<Mentor>> filterPredicates = new ArrayList<>();
        if (nameNorm.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByName(nameNorm.get(), false));
        }

        if (phoneNorm.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByPhone(phoneNorm.get(), false));
        }

        if (emailNorm.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByEmail(emailNorm.get(), false));
        }

        if (organizationNorm.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindMentorByOrganization(organizationNorm.get(), false));
        }

        if (nameExclude.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByName(nameExclude.get(), true));
        }

        if (phoneExclude.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByPhone(phoneExclude.get(), true));
        }

        if (emailExclude.isPresent()) {
            filterPredicates.add(Predicates.getPredicateFindMentorByEmail(emailExclude.get(), true));
        }

        if (organizationExclude.isPresent()) {
            filterPredicates.add(
                    Predicates.getPredicateFindMentorByOrganization(organizationExclude.get(), true));
        }

        this.findPredicate = Predicates.predicateReducer(filterPredicates);
        this.nameNorm = nameNorm.orElse("");
        this.emailNorm = emailNorm.orElse("");
        this.phoneNorm = phoneNorm.orElse("");
        this.organizationNorm = organizationNorm.orElse("");
        this.nameExclude = nameExclude.orElse("");
        this.emailExcude = emailExclude.orElse("");
        this.phoneExclude = phoneExclude.orElse("");
        this.organizationExclude = organizationExclude.orElse("");
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Mentor> results = model.findMentor(this.findPredicate);
        listResults(results, PrefixType.P);
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.M);
    }

    @Override
    public boolean equals(Object otherFindCommand) {
        if (otherFindCommand == this) {
            return true;
        }

        if (!(otherFindCommand instanceof FindMentorCommand)) {
            return false;
        }

        return nameNorm.equals(((FindMentorCommand) otherFindCommand).nameNorm)
                && emailNorm.equals(((FindMentorCommand) otherFindCommand).emailNorm)
                && phoneNorm.equals(((FindMentorCommand) otherFindCommand).phoneNorm)
                && organizationNorm.equals(((FindMentorCommand) otherFindCommand).organizationNorm)
                && nameExclude.equals(((FindMentorCommand) otherFindCommand).nameExclude)
                && emailExcude.equals(((FindMentorCommand) otherFindCommand).emailExcude)
                && phoneExclude.equals(((FindMentorCommand) otherFindCommand).phoneExclude)
                && organizationExclude.equals(((FindMentorCommand) otherFindCommand).organizationExclude);
    }
}
