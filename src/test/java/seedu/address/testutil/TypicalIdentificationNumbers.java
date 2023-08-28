package seedu.address.testutil;

import seedu.address.model.entity.IdentificationNumber;

/**
 * A utility class containing a list of {@code IdentificationNumber} objects to be used in tests.
 */
public class TypicalIdentificationNumbers {
    public static final IdentificationNumber FIRST_BODY_ID_NUM = IdentificationNumber.customGenerateId("B", 1);
    public static final IdentificationNumber SECOND_BODY_ID_NUM = IdentificationNumber.customGenerateId("B", 2);
    public static final IdentificationNumber FIRST_WORKER_ID_NUM = IdentificationNumber.customGenerateId("W", 1);
    public static final IdentificationNumber SECOND_WORKER_ID_NUM = IdentificationNumber.customGenerateId("W", 2);
    public static final IdentificationNumber FIRST_FRIDGE_ID_NUM = IdentificationNumber.customGenerateId("F", 1);
    public static final IdentificationNumber SECOND_FRIDGE_ID_NUM = IdentificationNumber.customGenerateId("F", 2);
    public static final IdentificationNumber THIRD_FRIDGE_ID_NUM = IdentificationNumber.customGenerateId("F", 3);
}
