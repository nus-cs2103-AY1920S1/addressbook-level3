package seedu.weme.testutil;

import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * A utility class to help with building Template objects.
 */
public class TemplateBuilder {


    public static final String DEFAULT_NAME = "Drake Reaction";
    public static final String DEFAULT_FILEPATH = "src/test/data/templates/drake_template.jpg";

    private Name name;
    private ImagePath filePath;

    public TemplateBuilder() {
        name = new Name(DEFAULT_NAME);
        filePath = new ImagePath(DEFAULT_FILEPATH);
    }

    /**
     * Initializes the MemeBuilder with the data of {@code templateToCopy}.
     */
    public TemplateBuilder(Template templateToCopy) {
        name = templateToCopy.getName();
        filePath = templateToCopy.getFilePath();
    }

    /**
     * Sets the {@code Name} of the {@code Template} that we are building.
     *
     * @param name
     */
    public TemplateBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code ImagePath} of the {@code Template} that we are building.
     *
     * @param filePath
     */
    public TemplateBuilder withFilePath(String filePath) {
        this.filePath = new ImagePath(filePath);
        return this;
    }

    public Template build() {
        return new Template(name, filePath);
    }

}
