package seedu.address.model.aesthetics;

import java.util.ArrayList;
import java.util.List;

public class BackgroundImageArgs {
    public static final List<String> BACKGROUND_REPEAT_VALUES = new ArrayList<>(List.of("repeat-x", "repeat-y",
            "repeat", "space", "round", "no-repeat", ""));
    public static final List<String> BACKGROUND_SIZE_VALUES = new ArrayList<>(List.of("auto", "cover", "contain", ""));
}
