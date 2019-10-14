package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adds a tag to an existing person in the address book.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag to the person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "t/ TAG [MORE_TAGS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/smoker t/diabetic";

    private static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to Person: %1$s";

    private final Index index;
    private final String[] tags;

    public AddTagCommand(Index index, String... tags) {
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

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Tag> newTags = new HashSet<>(personToEdit.getTags());

        for (String tag : tags) {
            newTags.add(new Tag(tag));
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getNric(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDateOfBirth(),
                personToEdit.getPolicies(),
                newTags
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_TAG_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index) && Arrays.equals(tags, e.tags);
    }
}
