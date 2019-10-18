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
 * Deletes a tag of an existing policy in the address book.
 */
public class DeletePolicyTagCommand extends Command {

    public static final String COMMAND_WORD = "deletepolicytag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes tag of the policy identified "
            + "by the index number used in the last policy listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "t/ TAG [MORE_TAGS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/life t/accident";

    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag of Policy: %1$s";

    private static final String MESSAGE_TAG_NOT_FOUND = "No matching tags found of Policy: %1$s";

    private final Index index;
    private final String[] tags;

    public DeletePolicyTagCommand(Index index, String... tags) {
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
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_USAGE
                    )
            );
        }

        Policy policyToEdit = lastShownList.get(index.getZeroBased());
        List<Tag> removeTags = new ArrayList<>();

        for (String tag : tags) {
            removeTags.add(new Tag(tag));
        }

        boolean tagsRemoved = false;

        for (Tag removeTag : removeTags) {
            for (Tag tag : policyToEdit.getTags()) {
                if (removeTag == tag) {
                    tagsRemoved = true;
                    break;
                }
            }
            if (tagsRemoved) {
                break;
            }
        }

        Policy editedPolicy = new PolicyBuilder(policyToEdit)
                .removeTags(removeTags)
                .build();

        model.setPolicy(policyToEdit, editedPolicy);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);

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
        return new CommandResult(generateSuccessMessage(editedPolicy, tagsRemoved));
    }

    /**
     * Parses {@code personToEdit} and {@code tagsRemoved} and returns a {@code String}.
     */
    private String generateSuccessMessage(Policy policyToEdit, boolean tagsRemoved) {
        if (tagsRemoved) {
            return String.format(MESSAGE_DELETE_TAG_SUCCESS, policyToEdit);
        } else {
            return String.format(MESSAGE_TAG_NOT_FOUND, policyToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeletePolicyTagCommand)) {
            return false;
        }

        DeletePolicyTagCommand e = (DeletePolicyTagCommand) other;
        return index.equals(e.index) && Arrays.equals(tags, e.tags);
    }
}
