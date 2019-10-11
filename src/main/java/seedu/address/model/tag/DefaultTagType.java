package seedu.address.model.tag;

/**
 * Represents default types of tags.
 */
public enum DefaultTagType {
    COMPLETED("Completed"),
    CORE("Core"),
    SU("S/U-able"),
    UE("UE"),
    ULR("ULR"),
    PRIMARY("P"),
    ELECTIVE("E");

    private String defaultName;

    private DefaultTagType(String defaultName) {
        this.defaultName = defaultName;
    }

    /**
     * Returns the name of the default type.
     * @return Name of the default type.
     */
    public String getDefaultTagTypeName() {
        return defaultName;
    }
}
