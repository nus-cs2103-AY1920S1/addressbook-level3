package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Modules {
    private String moduleCode;
    private String title;
    private int credit;
    private String faculty;
    private boolean su;

    public Modules(String moduleCode, String title, int credit, String faculty, boolean su) {
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
