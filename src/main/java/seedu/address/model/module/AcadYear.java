package seedu.address.model.module;

/**
 * The acad year of the module
 */
public class AcadYear {
    private String acadYear;

    public AcadYear(String acadYear) {
        this.acadYear = acadYear;
    }

    @Override
    public String toString() {
        return acadYear;
    }
}
