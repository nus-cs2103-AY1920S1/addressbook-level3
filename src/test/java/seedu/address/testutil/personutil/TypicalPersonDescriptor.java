package seedu.address.testutil.personutil;

import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.User;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Typical person descriptors.
 */
public class TypicalPersonDescriptor {

    public static final String WHITESPACE = " ";

    public static final PersonDescriptor ALICE = new PersonDescriptorBuilder()
            .withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRemark("Alice is a loser")
            .withTags("friends")
            .build();

    public static final PersonDescriptor BENSON = new PersonDescriptorBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withRemark("Benson is a dumbass")
            .withTags("owesMoney", "friends")
            .build();

    public static final PersonDescriptor CARL = new PersonDescriptorBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withRemark("WTF CARL")
            .withTags("LALALA").build();

    public static final PersonDescriptor DANIEL = new PersonDescriptorBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withRemark("LOL")
            .withTags("friends").build();

    public static final PersonDescriptor ELLE = new PersonDescriptorBuilder()
            .withName("Elle Meyer").withPhone("9482224")
            .withRemark("HDL?")
            .withTags("friends")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .build();

    public static final PersonDescriptor FIONA = new PersonDescriptorBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withRemark("BIAP")
            .withTags("friends")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .build();

    public static final PersonDescriptor GEORGE = new PersonDescriptorBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withRemark("GEORGEEEEEEE")
            .withTags("friends")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .build();

    public static final PersonDescriptor ZACK = new PersonDescriptorBuilder()
            .withName("Zack")
            .withPhone("81231236")
            .withTags("friends")
            .withEmail("zack@example.com")
            .withAddress("loser street")
            .withRemark("Im not on the list")
            .withTags("blabla").build();

    public static final PersonDescriptor USER = new PersonDescriptorBuilder()
            .withName("User").withPhone("99999999").withTags("friends")
            .withEmail("user@gmail.com").withAddress("bla bla")
            .withRemark("me").build();

    /**
     * Generates a typical PersonList.
     * All persons will be added except ZACK.
     */
    public static PersonList generateTypicalPersonList() {
        PersonList personList = new PersonList(new User(USER));
        try {
            personList.addPerson(ALICE);
            personList.addPerson(BENSON);
            personList.addPerson(CARL);
            personList.addPerson(DANIEL);
            personList.addPerson(ELLE);
            personList.addPerson(FIONA);
            personList.addPerson(GEORGE);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }
        return personList;
    }
}
