package seedu.address.model.tag;

/**
 * Represents default types of tags.
 */
public enum Default {
    COMPLETED("completed"),
    CORE("core"),
    SU("s/u-able"),
    UE("ue"),
    ULR("ulr");

    private String defaultName;

    private Default(String defaultName) {
        this.defaultName = defaultName;
    }

    /**
     * Returns the name of the default type.
     * @return Name of the default type.
     */
    public String getDefaultName() {
        return defaultName;
    }
}
