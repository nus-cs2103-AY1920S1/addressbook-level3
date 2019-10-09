package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_ZACH;

import seedu.address.model.entity.worker.Worker;

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
    public static final Worker CLARA = new WorkerBuilder().withName("Clara Doe")
            .withPhone("84371233").withDateJoined("13/12/2019").withSex("FEMALE")
            .withEmploymentStatus("cleaning").build();

    // Manually added - Workers's details found in {@code CommandTestUtil}
    public static final Worker ZACH = new WorkerBuilder().withName(VALID_NAME_ZACH).withSex(VALID_SEX_ZACH)
        .withDateJoined(VALID_DATE_JOINED_ZACH).build();
    public static final Worker XENIA = new WorkerBuilder().withName(VALID_NAME_XENIA).withSex(VALID_SEX_XENIA)
        .withDateJoined(VALID_DATE_JOINED_XENIA).build();

    private TypicalWorkers() {} // prevents instantiation
}
