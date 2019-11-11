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
                Type.Glucose,
                new Value("30"),
                new Timestamp("17/10/2019 13:10"),
                getRemarkSet("after meal", "lunch")
            ),
            new Record(
                Type.Glucose,
                new Value("34"),
                new Timestamp("20/10/2019 13:00"),
                getRemarkSet("after meal", "lunch")
            ),
            new Record(
                Type.Weight,
                new Value("58"),
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
                Type.Weight,
                new Value("65"),
                new Timestamp("23/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Weight,
                new Value("55"),
                new Timestamp("09/11/2019 19:05"),
                getRemarkSet()
            ),
            new Record(
                Type.Weight,
                new Value("55.5"),
                new Timestamp("09/11/2019 19:10"),
                getRemarkSet()
            ),
            new Record(
                Type.Height,
                new Value("168"),
                new Timestamp("25/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Height,
                new Value("167"),
                new Timestamp("05/10/2019 12:00"),
                getRemarkSet()
            ),
            new Record(
                Type.Height,
                new Value("165"),
                new Timestamp("03/10/2019 12:00"),
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
                Type.Calories,
                new Value("750"),
                new Timestamp("24/10/2019 12:05"),
                getRemarkSet("lunch")
            ),
            new Record(
                Type.Calories,
                new Value("550"),
                new Timestamp("24/10/2019 20:05"),
                getRemarkSet("dinner")
            ),
            new Record(
                Type.Calories,
                new Value("1050"),
                new Timestamp("23/10/2019 20:05"),
                getRemarkSet("dinner")
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
