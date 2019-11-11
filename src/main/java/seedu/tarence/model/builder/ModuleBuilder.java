package seedu.tarence.model.builder;

import java.util.ArrayList;
import java.util.List;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;


/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODCODE = "CS2100";
    public static final List<Tutorial> DEFAULT_TUTORIALS = new ArrayList<>();

    private ModCode modCode;
    private List<Tutorial> tutorials;

    public ModuleBuilder() {
        modCode = new ModCode(DEFAULT_MODCODE);
        tutorials = DEFAULT_TUTORIALS;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        modCode = moduleToCopy.getModCode();
        tutorials = moduleToCopy.getTutorials();
    }

    /**
     * Sets the {@code ModCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModCode(String modCode) {
        this.modCode = new ModCode(modCode);
        return this;
    }

    /**
     * Sets the {@code List<Class>} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
        return this;
    }

    public Module build() {
        return new Module(modCode, tutorials);
    }

}
