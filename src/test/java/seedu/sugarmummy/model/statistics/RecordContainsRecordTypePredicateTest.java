package seedu.sugarmummy.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.statistics.predicates.RecordContainsRecordTypePredicate;
import seedu.sugarmummy.model.time.DateTime;

//@@author chen-xi-cx

public class RecordContainsRecordTypePredicateTest {

    private RecordContainsRecordTypePredicate bloodSugarPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BLOODSUGAR);
    private RecordContainsRecordTypePredicate bmiPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BMI);
    private BloodSugar bloodSugar = new BloodSugar(new Concentration("6.9"), new DateTime("2019-08-11 05:09"));
    private Bmi bmi = new Bmi(new Height("1.7"), new Weight("50.3"), new DateTime("2019-08-11 05:09"));

    @Test
    public void test_recordTypeMatch_returnsTrue() {
        assertTrue(bloodSugarPredicate.test(bloodSugar));
        assertTrue(bmiPredicate.test(bmi));
    }

    @Test
    public void test_recordTypeMismatch_returnsFalse() {
        assertFalse(bloodSugarPredicate.test(bmi));
        assertFalse(bmiPredicate.test(bloodSugar));
    }

}
