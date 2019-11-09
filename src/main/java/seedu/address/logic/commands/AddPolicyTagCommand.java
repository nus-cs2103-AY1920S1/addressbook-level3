package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to an existing policy in the address book.
 */
public class AddPolicyTagCommand extends Command {

    public static final String COMMAND_WORD = "addpolicytag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag to the policy identified "
            + "by the index number used in the last policy listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TAG [MORE_TAGS] (will be converted to lowercase)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/life t/accident";

    private static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to Policy: %1$s";

    private final Index index;
    private final String[] tags;

    public AddPolicyTagCommand(Index index, String... tags) {
        requireAllNonNull(index, tags);
        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        if (tags.length == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        for (String tag : tags) {
            if ((tag.length() == 0) || (tag.matches("^.*[^a-z0-9 ].*$"))) {
                throw new CommandException(
                        String.format(
                                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                MESSAGE_USAGE
                        )
                );
            }
        }

        Policy policyToEdit = lastShownList.get(index.getZeroBased());
        List<Tag> newTags = new ArrayList<>();

        for (String tag : tags) {
            newTags.add(new Tag(tag));
        }

        Policy editedPolicy = new PolicyBuilder(policyToEdit).addTags(newTags).build();

        model.setPolicy(policyToEdit, editedPolicy);

        // Update persons with the edited policy
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p.hasPolicy(policyToEdit)) {
                Person policyRemoved = new PersonBuilder(p).removePolicies(policyToEdit).build();
                Person editedPerson = new PersonBuilder(policyRemoved).addPolicies(editedPolicy).build();
                model.setPerson(p, editedPerson);
            }
        }

        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(generateSuccessMessage(editedPolicy));
    }

    private String generateSuccessMessage(Policy policyToEdit) {
        return String.format(MESSAGE_ADD_TAG_SUCCESS, policyToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPolicyTagCommand)) {
            return false;
        }

        AddPolicyTagCommand e = (AddPolicyTagCommand) other;
        return index.equals(e.index) && Arrays.equals(tags, e.tags);
    }
}
