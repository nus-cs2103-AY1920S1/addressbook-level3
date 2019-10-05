package seedu.address.model.tag;

/**
 * Represents default types of tags.
 */
public enum DefaultTagType {
    COMPLETED("completed"),
    CORE("core"),
    SU("s/u-able"),
    UE("ue"),
    ULR("ulr");

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
