package seedu.address.testutil;

import seedu.address.model.entity.worker.Worker;

/**
 * A utility class containing a list of {@code Workers} objects to be used in tests.
 */
public class TypicalWorkers {

    public static final Worker ALICE = new WorkerBuilder().withName("Alice Pauline")
        .withPhone("94351253").withDateJoined("12/12/2019").withSex("Female")
        .withEmploymentStatus("cleaning").build();
    public static final Worker BENSON = new WorkerBuilder().withName("Benson Meier")
        .withPhone("98765432").withDateJoined("12/12/2019").withSex("Male")
        .withEmploymentStatus("transporting").build();

    private TypicalWorkers() {} // prevents instantiation
}
