package seedu.address.model.module;

import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AcadYear)) {
            return false;
        }
        AcadYear ay = (AcadYear) other;
        if (ay == this) {
            return true;
        } else if (ay.acadYear.equals(this.acadYear)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(acadYear);
    }
}
