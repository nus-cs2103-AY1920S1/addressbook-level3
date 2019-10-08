package seedu.jarvis.testutil.cca;

import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.Name;

/**
 * A utility class to help with building Person objects.
 */
public class CcaBuilder {

    public static final String DEFAULT_NAME = "Canoeing";

    private Name name;

    public CcaBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the CcaBuilder with the data of {@code ccaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        name = ccaToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Cca} that we are building.
     */
    public CcaBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Cca build() {
        return new Cca(name);
    }

}
