package com.typee.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.typee.logic.commands.CommandTestUtil;
import com.typee.model.AddressBook;
import com.typee.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
