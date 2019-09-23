package seedu.address.model.module;

/**
 * Description of the module
 */
public class Description {
    private String description;

    public Description(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
