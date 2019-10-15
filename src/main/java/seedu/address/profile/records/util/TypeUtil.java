package seedu.address.profile.records.util;

import java.util.HashMap;

/**
 * Contains record types available for generating {@code HealthRecords}.
 */
public class TypeUtil {

    public static final String TYPE_BLOODPRESSURE = "bloodpressure";
    public static final String TYPE_GLUCOSE = "glucose";
    public static final String TYPE_WEIGHT = "weight";
    public static final String TYPE_HEIGHT = "height";
    public static final String TYPE_CALORIES = "calories";

    public static final String UNIT_BLOODPRESSURE = "mmHg";
    public static final String UNIT_GLUCOSE = "mmol/L";
    public static final String UNIT_WEIGHT = "kg";
    public static final String UNIT_HEIGHT = "cm";
    public static final String UNIT_CALORIES = "kcal";

    public static final HashMap<String, String> TYPE_LIST =
            new HashMap<>() {{
                put(TYPE_BLOODPRESSURE, UNIT_BLOODPRESSURE);
                put(TYPE_GLUCOSE, UNIT_GLUCOSE);
                put(TYPE_WEIGHT, UNIT_WEIGHT);
                put(TYPE_HEIGHT, UNIT_HEIGHT);
                put(TYPE_CALORIES, UNIT_CALORIES);
            }};

}
