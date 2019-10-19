package seedu.address.storage.dashboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Name;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.dashboard.components.DashboardName;
import seedu.address.model.recipe.components.Calories;
import seedu.address.model.recipe.components.Carbs;
import seedu.address.model.recipe.components.Fats;
import seedu.address.model.recipe.components.Ingredient;
import seedu.address.model.recipe.components.Protein;
import seedu.address.model.recipe.components.Recipe;

/**
 * Jackson-friendly version of {@link Dashboard}.
 */
class JsonAdaptedDashboard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Dashboard's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedDashboard} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDashboard(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Dashboard} into this class for Jackson use.
     */
    public JsonAdaptedDashboard(Dashboard source) {
        name = source.getDashboardName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted dashboard object into the model's {@code Dashboard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted dashboard.
     */
    public Dashboard toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DashboardName.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(DashboardName.MESSAGE_CONSTRAINTS);
        }
        final DashboardName modelName = new DashboardName(name);

        return new Dashboard(modelName);
    }

}
