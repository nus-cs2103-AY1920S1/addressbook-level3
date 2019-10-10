package seedu.address.model.incident;

public class Description {
    private String desc;

    /**
     * Creates a filled Description.
     * @param desc the description of the event filled in by the operator.
     */
    public Description(String desc) {
        this.desc = desc;
    }

    /**
     * Creates a new Description that is empty.
     * Used to facilitate fast creation of incident reports, descriptions can be added during edits.
     */
    public Description() {
        this.desc = "";
    }


    @Override
    public String toString() {
        return desc;
    }
}
