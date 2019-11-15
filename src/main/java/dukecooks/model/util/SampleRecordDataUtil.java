package dukecooks.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleRecordDataUtil {

    public static Record[] getSampleRecords () {
        return new Record[]{
            new Record(
                Type.Glucose,
                    new Value("20"),
                    new Timestamp("14/10/2019 20:10"),
                    getRemarkSet("after meal", "dinner")
            ),
            new Record(
                Type.Weight,
                new Value("67"),
                new Timestamp("26/10/2019 12:10"),
                getRemarkSet()
            ),
            new Record(
                Type.Weight,
                new Value("63"),
                new Timestamp("24/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Height,
                new Value("168"),
                new Timestamp("25/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Calories,
                new Value("500"),
                new Timestamp("25/10/2019 22:00"),
                getRemarkSet("on a diet", "dinner")
            ),
            new Record(
                Type.Calories,
                new Value("300"),
                new Timestamp("24/10/2019 11:00"),
                getRemarkSet("on a diet", "breakfast", "no carbs")
            ),
            new Record(
                Type.Carbs,
                new Value("8"),
                new Timestamp("24/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Fats,
                new Value("20"),
                new Timestamp("24/10/2019 12:00"),
                getRemarkSet("lunch")
            ),
            new Record(
                Type.Protein,
                new Value("22"),
                new Timestamp("24/10/2019 12:00"),
                getRemarkSet()
            )
        };
    }

    public static ReadOnlyHealthRecords getSampleHealthRecords () {
        HealthRecords sampleDc = new HealthRecords();
        for (Record sampleRecord : getSampleRecords()) {
            sampleDc.addRecord(sampleRecord);
        }
        return sampleDc;
    }

    /**
     * Returns a remark set containing the list of strings given.
     */
    public static Set<Remark> getRemarkSet(String... strings) {
        return Arrays.stream(strings)
                .map(Remark::new)
                .collect(Collectors.toSet());
    }

}
