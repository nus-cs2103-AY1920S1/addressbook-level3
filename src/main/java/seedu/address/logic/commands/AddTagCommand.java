package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Adds a tag to an existing person in the address book.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TAG [MORE_TAGS] (will be converted to lowercase)\n"
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
        List<Tag> newTags = new ArrayList<>();

        for (String tag : tags) {
            newTags.add(new Tag(tag));
        }

        Person editedPerson = new PersonBuilder(personToEdit)
                .addTags(newTags)
                .build();

        model.setPerson(personToEdit, editedPerson);

        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
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
