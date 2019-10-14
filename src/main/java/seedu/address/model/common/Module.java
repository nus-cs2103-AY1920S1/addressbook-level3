package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a NUS modules in the address book.
 */
public class Module {
    private String moduleCode;
    private String title;
    private int credit;
    private String faculty;
    private boolean su;

    /**
     *  Constructs a {@code Module}.
     *
     * @param moduleCode Representative codes for the module.
     * @param title The title for the module.
     * @param credit Module credits that provides the weight
     * @param faculty The faculty the module is held at.
     * @param su Satisfactory and unsatisfactory option for grade
     */
    public Module(String moduleCode, String title, int credit, String faculty, boolean su) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(credit);
        requireNonNull(faculty);
        requireNonNull(su);
        this.moduleCode = moduleCode;
        this.title = title;
        this.credit = credit;
        this.faculty = faculty;
        this.su = su;
    }

    @Override
    public String toString() {
        return moduleCode + " " + title;
    }
}
