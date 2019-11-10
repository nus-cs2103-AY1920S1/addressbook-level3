package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIGNATION_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIGNATION_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYMENT_STATUS_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYMENT_STATUS_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NUMBER_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NUMBER_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_ZACH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.entity.worker.Worker;

//@@author shaoyi1997
/**
 * A utility class containing a list of {@code Workers} objects to be used in tests.
 */
public class TypicalWorkers {

    // initialized with only mandatory fields
    public static final Worker ALICE = new WorkerBuilder().withName("Alice Pauline")
        .withPhone("94351253").withDateJoined("12/12/2019").withSex("FEMALE")
        .withEmploymentStatus("cleaning").build();
    public static final Worker BENSON = new WorkerBuilder().withName("Benson Meier")
        .withPhone("98765432").withDateJoined("12/12/2019").withSex("FEMALE")
        .withEmploymentStatus("transporting").build();
    public static final Worker CHARLIE = new WorkerBuilder().withName("Charlie Binder")
            .withPhone("98765432").withDateJoined("12/12/2019").withSex("MALE")
            .withEmploymentStatus("transporting").build();
    public static final Worker CLARA = new WorkerBuilder().withName("Clara Doe")
            .withPhone("84371233").withDateJoined("13/12/2019").withSex("FEMALE")
            .withEmploymentStatus("cleaning").build();

    // Manually added - Workers's details found in {@code CommandTestUtil}
    public static final Worker ZACH = new WorkerBuilder().withName(VALID_NAME_ZACH).withSex(VALID_SEX_ZACH)
            .withDateJoined(VALID_DATE_JOINED_ZACH).withDateOfBirth(VALID_DATE_OF_BIRTH_ZACH)
                    .withDesignation(VALID_DESIGNATION_ZACH).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_ZACH)
                            .withPhone(VALID_PHONE_NUMBER_ZACH).build();
    public static final Worker XENIA = new WorkerBuilder().withName(VALID_NAME_XENIA).withSex(VALID_SEX_XENIA)
            .withDateJoined(VALID_DATE_JOINED_XENIA).withDateOfBirth(VALID_DATE_OF_BIRTH_XENIA)
                    .withDesignation(VALID_DESIGNATION_XENIA).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_XENIA)
                            .withPhone(VALID_PHONE_NUMBER_XENIA).build();

    public static final String KEYWORD_MATCHING_ZACH = "Zach"; // A keyword that matches Zach

    private TypicalWorkers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Worker worker : getTypicalWorkers()) {
            ab.addEntity(worker);
        }
        return ab;
    }

    public static List<Worker> getTypicalWorkers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CHARLIE, CLARA));
    }
}
//@@author
