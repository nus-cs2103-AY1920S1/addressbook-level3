package seedu.address.model.module;

/**
 * Title of the module
 */
public class Title {
    private String title;

    public Title(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
