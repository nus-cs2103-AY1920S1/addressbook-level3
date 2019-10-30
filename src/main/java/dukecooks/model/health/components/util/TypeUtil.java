package dukecooks.model.health.components.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains record types available for generating {@code HealthRecords}.
 */
public class TypeUtil {

    public static final String BEHAVIOUR_LATEST = "latest";
    public static final String BEHAVIOUR_SUM = "sum";

    public static final String TYPE_GLUCOSE = "glucose";
    public static final String TYPE_WEIGHT = "weight";
    public static final String TYPE_HEIGHT = "height";
    public static final String TYPE_CALORIES = "calories";
    public static final String TYPE_CARBS = "carbs";
    public static final String TYPE_PROTEIN = "protein";
    public static final String TYPE_FATS = "fats";

    public static final String UNIT_GLUCOSE = "mmol/L";
    public static final String UNIT_WEIGHT = "kg";
    public static final String UNIT_HEIGHT = "cm";
    public static final String UNIT_CALORIES = "kcal";
    public static final String UNIT_CARBS = "g";
    public static final String UNIT_PROTEIN = "g";
    public static final String UNIT_FATS = "g";

    public static final String BEHAVIOUR_GLUCOSE = "latest";
    public static final String BEHAVIOUR_WEIGHT = "latest";
    public static final String BEHAVIOUR_HEIGHT = "latest";
    public static final String BEHAVIOUR_CALORIES = "sum";
    public static final String BEHAVIOUR_CARBS = "sum";
    public static final String BEHAVIOUR_PROTEIN = "sum";
    public static final String BEHAVIOUR_FATS = "sum";

    public static final Map<String, String> TYPE_LIST =
            new HashMap<>() {{
                put(TYPE_GLUCOSE, UNIT_GLUCOSE);
                put(TYPE_WEIGHT, UNIT_WEIGHT);
                put(TYPE_HEIGHT, UNIT_HEIGHT);
                put(TYPE_CALORIES, UNIT_CALORIES);
                put(TYPE_CARBS, UNIT_CARBS);
                put(TYPE_PROTEIN, UNIT_PROTEIN);
                put(TYPE_FATS, UNIT_FATS);
            }};

    public static final Map<String, String> TYPE_BEHAVIOUR =
            new HashMap<>() {{
                put(TYPE_GLUCOSE, BEHAVIOUR_GLUCOSE);
                put(TYPE_WEIGHT, BEHAVIOUR_WEIGHT);
                put(TYPE_HEIGHT, BEHAVIOUR_HEIGHT);
                put(TYPE_CALORIES, BEHAVIOUR_CALORIES);
                put(TYPE_CARBS, BEHAVIOUR_CARBS);
                put(TYPE_PROTEIN, BEHAVIOUR_PROTEIN);
                put(TYPE_FATS, BEHAVIOUR_FATS);
            }};

}
