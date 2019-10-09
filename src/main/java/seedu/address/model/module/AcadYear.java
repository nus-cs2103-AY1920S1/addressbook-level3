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

    public String toStringDashed() {
        return acadYear.replace("/", "-");
    }

    /**
     * Checks if this AcadYear is equal to other AcadYear.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(AcadYear other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.acadYear)) {
            return true;
        } else {
            return false;
        }
    }
}
