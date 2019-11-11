package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag of an existing person in the address book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes tag of the person identified "
            + "by the index number used in the last person listing. (case insensitive)\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TAG [MORE_TAGS] (will be converted to lowercase)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/smoker t/diabetic";

    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag of Person: %1$s";

    private static final String MESSAGE_TAG_NOT_FOUND = "No matching tags found of Policy: %1$s";

    private final Index index;
    private final String[] tags;

    public DeleteTagCommand(Index index, String... tags) {
        requireAllNonNull(index, tags);
        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (tags.length == 0) {
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_USAGE
                    )
            );
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

        Person personToEdit = lastShownList.get(index.getZeroBased());
        List<Tag> removeTags = new ArrayList<>();

        for (String tag : tags) {
            removeTags.add(new Tag(tag));
        }

        boolean tagsRemoved = false;

        for (Tag removeTag : removeTags) {
            for (Tag tag : personToEdit.getTags()) {
                if (removeTag.equals(tag)) {
                    tagsRemoved = true;
                    break;
                }
            }
            if (tagsRemoved) {
                break;
            }
        }

        Person editedPerson = new PersonBuilder(personToEdit).removeTags(removeTags).build();

        model.setPerson(personToEdit, editedPerson);

        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(generateSuccessMessage(editedPerson, tagsRemoved));
    }

    /**
     * Parses {@code personToEdit} and {@code tagsRemoved} and returns a {@code String}.
     */
    private String generateSuccessMessage(Person personToEdit, boolean tagsRemoved) {
        if (tagsRemoved) {
            return String.format(MESSAGE_DELETE_TAG_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_TAG_NOT_FOUND, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand e = (DeleteTagCommand) other;
        return index.equals(e.index) && Arrays.equals(tags, e.tags);
    }
}
