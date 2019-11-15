package dukecooks.testutil.health;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.components.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final String WEIGHT_FIRST_WEIGHT = "49.0";
    public static final String HEIGHT_FIRST_HEIGHT = "155.0";

    public static final Record GLUCOSE_FIRST = new RecordBuilder().withType("Glucose")
            .withRemarks("two cups of milk tea")
            .withValue("18.0").withTimestamp("01/11/2019 12:00").build();
    public static final Record CALORIES_FIRST = new RecordBuilder().withType("Calories")
            .withRemarks("Dinner", "three course meal")
            .withValue("1058.0").withTimestamp("01/11/2019 12:00").build();
    public static final Record FATS_FIRST = new RecordBuilder().withType("Fats")
            .withRemarks("Dinner")
            .withValue("29.0").withTimestamp("01/11/2019 12:00").build();
    public static final Record WEIGHT_FIRST = new RecordBuilder().withType("Weight")
            .withRemarks()
            .withValue(WEIGHT_FIRST_WEIGHT).withTimestamp("01/11/2019 12:00").build();
    public static final Record HEIGHT_FIRST = new RecordBuilder().withType("Height")
            .withRemarks()
            .withValue(HEIGHT_FIRST_HEIGHT).withTimestamp("01/11/2019 12:00").build();
    public static final Record CARBS_FIRST = new RecordBuilder().withType("Carbs")
            .withRemarks("No carbs diet")
            .withValue("1.0").withTimestamp("01/11/2019 12:00").build();
    public static final Record PROTEIN_FIRST = new RecordBuilder().withType("Protein")
            .withRemarks("Fish challenge")
            .withValue("12.0").withTimestamp("01/11/2019 12:00").build();

    // Manually added
    public static final Record GLUCOSE_SECOND = new RecordBuilder().withType("Glucose")
            .withRemarks("three slices of cheesecake")
            .withValue("30.0").withTimestamp("04/11/2019 12:00").build();
    public static final Record CALORIES_SECOND = new RecordBuilder().withType("Calories")
            .withRemarks("No carbs diet")
            .withValue("600.0").withTimestamp("04/11/2019 12:00").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record GLUCOSE = new RecordBuilder().withType(CommandTestUtil.VALID_TYPE_GLUCOSE)
            .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE)
            .withValue(CommandTestUtil.VALID_VALUE_GLUCOSE).withTimestamp(CommandTestUtil.VALID_TIMESTAMP_GLUCOSE)
            .build();;
    public static final Record CALORIES = new RecordBuilder().withType(CommandTestUtil.VALID_TYPE_CALORIES)
            .withRemarks(CommandTestUtil.VALID_REMARK_CALORIES)
            .withValue(CommandTestUtil.VALID_VALUE_CALORIES).withTimestamp(CommandTestUtil.VALID_TIMESTAMP_CALORIES)
            .build();


    private TypicalRecords() {} // prevents instantiation

    /**
     * Returns an {@code HealthRecords} with all the typical records.
     */
    public static HealthRecords getTypicalHealthRecords() {
        HealthRecords dc = new HealthRecords();
        for (Record record : getTypicalRecords()) {
            dc.addRecord(record);
        }
        return dc;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(GLUCOSE_FIRST, CALORIES_FIRST, FATS_FIRST, WEIGHT_FIRST, HEIGHT_FIRST,
                CARBS_FIRST, PROTEIN_FIRST));
    }
}
