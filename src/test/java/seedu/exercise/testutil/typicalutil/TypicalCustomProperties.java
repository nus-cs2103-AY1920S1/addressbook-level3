package seedu.exercise.testutil.typicalutil;

import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_REMARK;

import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.testutil.builder.CustomPropertyBuilder;

/**
 * A utility class containing a list of {@code CustomProperty} objects to be used in tests.
 */
public class TypicalCustomProperties {

    // Manually added
    public static final CustomProperty PRIORITY = new CustomPropertyBuilder().withPrefix("d")
            .withFullName("Priority").withParameterType("Number").build();
    public static final CustomProperty INSTRUCTIONS = new CustomPropertyBuilder().withPrefix("e")
            .withFullName("Instructions").withParameterType("Text").build();
    public static final CustomProperty EXPECTEDRECOVERY = new CustomPropertyBuilder().withPrefix("f")
            .withFullName("Recovery").withParameterType("Date").build();

    // Manually added - Custom Property's details found in {@code CommandTestUtil}
    public static final CustomProperty RATING = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
    public static final CustomProperty REMARK = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_REMARK)
            .withFullName(VALID_FULL_NAME_REMARK).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
    public static final CustomProperty ENDDATE = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_END_DATE)
            .withFullName(VALID_FULL_NAME_END_DATE).withParameterType(VALID_PARAMETER_TYPE_END_DATE).build();

}
