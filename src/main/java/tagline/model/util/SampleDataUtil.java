package tagline.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import tagline.model.contact.Address;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.Description;
import tagline.model.contact.Email;
import tagline.model.contact.Name;
import tagline.model.contact.Phone;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.note.Content;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteId;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        // @formatter:off
        return new Contact[]{
            new Contact(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Description("friend"), new ContactId("1")),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Description("friend"),
                    new ContactId("2")),
            new Contact(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Description("friend"), new ContactId("3")),
            new Contact(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Description("friend"),
                    new ContactId("4")),
            new Contact(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Description("friend"), new ContactId("5")),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Description("friend"), new ContactId("6"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    public static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new NoteId(), new Title(""), new Content(""), new TimeCreated(), new TimeLastEdited(),
                new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat."), new TimeCreated(), new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>()),
            new Note(new NoteId(), new Title("Lorem Ipsum Dolor Sit"), new Content("Lorem ipsum dolor sit amet, "
                + "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna "
                + "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                + "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
                + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum."), new TimeCreated(),
                new TimeLastEdited(), new HashSet<>())
        };
    }

    public static ReadOnlyNoteBook getSampleNoteBook() {
        NoteBook sampleNb = new NoteBook();
        for (Note sampleNote : getSampleNotes()) {
            sampleNb.addNote(sampleNote);
        }
        return sampleNb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
