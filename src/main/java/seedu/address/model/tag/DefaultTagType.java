package seedu.address.model.tag;

/**
 * Represents default tag types.
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
     * Checks if the given name is one of the default tag type names.
     * @param name The given name that is to be checked.
     * @return True if it is one of the default tag type names.
     */
    public static boolean contains(String name) {
        for (DefaultTagType defaultTagType : DefaultTagType.values()) {
            if (defaultTagType.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
