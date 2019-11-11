package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.ContactIdEqualsSearchIdPredicate;
import tagline.model.group.MemberId;
import tagline.model.note.NoteContainsTagsPredicate;
import tagline.model.tag.ContactTag;
import tagline.model.tag.GroupTag;
import tagline.model.tag.Tag;

/**
 * Shows a contact in address book whose id matches the query.
 */
public class ShowContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Successfully showed profile for %1$s";
    public static final String MESSAGE_FAILED = "Failed to find contact with id: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
        + ": Shows a contact profile whose id matches the id given in the command\n"
        + "Parameters: CONTACT_ID (must be a positive integer)\n"
        + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " 1";

    private final ContactId contactId;

    public ShowContactCommand(ContactId contactId) {
        this.contactId = contactId;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Tag> associatedTags = new ArrayList<>();
        associatedTags.add(new ContactTag(contactId));
        associatedTags.addAll(model.findGroupsWithMember(new MemberId(contactId)).stream()
            .map(group -> new GroupTag(group.getGroupName()))
            .collect(Collectors.toList()));

        NoteContainsTagsPredicate predicateNote = new NoteContainsTagsPredicate(associatedTags);
        ContactIdEqualsSearchIdPredicate predicateContact = new ContactIdEqualsSearchIdPredicate(contactId);

        Optional<Contact> optionalContact = model.findContact(contactId);

        if (optionalContact.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FAILED, contactId), ViewType.NONE);
        }

        model.updateFilteredContactList(predicateContact);
        model.updateFilteredNoteList(predicateNote);
        return new CommandResult(String.format(MESSAGE_SUCCESS, optionalContact.get().getName()),
            ViewType.CONTACT_PROFILE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ShowContactCommand // instanceof handles nulls
            && contactId.equals(((ShowContactCommand) other).contactId)); // state check
    }
}
