package seedu.address.testutil;

import seedu.address.model.entity.IdentificationNumber;

/**
 * A utility class containing a list of {@code IdentificationNumber} objects to be used in tests.
 */
public class TypicalIdentificationNumbers {
    public static final IdentificationNumber FIRST_BODY_ID_NUM = IdentificationNumber.customGenerateTestId("B", 0);
    public static final IdentificationNumber SECOND_BODY_ID_NUM = IdentificationNumber.customGenerateTestId("B", 1);
    public static final IdentificationNumber FIRST_WORKER_ID_NUM = IdentificationNumber.customGenerateTestId("W", 0);
    public static final IdentificationNumber SECOND_WORKER_ID_NUM = IdentificationNumber.customGenerateTestId("W", 1);
    public static final IdentificationNumber FIRST_FRIDGE_ID_NUM = IdentificationNumber.customGenerateTestId("F", 0);
    public static final IdentificationNumber SECOND_FRIDGE_ID_NUM = IdentificationNumber.customGenerateTestId("F", 1);
}
