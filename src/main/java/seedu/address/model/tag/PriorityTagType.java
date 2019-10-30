package seedu.address.model.tag;

/**
 * Represents priority tag types.
 */
public enum PriorityTagType {
    HIGH("PRIORITY.HIGH", "highPriority"),
    MEDIUM("PRIORITY.MEDIUM", "mediumPriority"),
    LOW("PRIORITY.LOW", "lowPriority");

    private String priorityTagTypeName;
    private String style;

    PriorityTagType(String priorityTagTypeName, String style) {
        this.priorityTagTypeName = priorityTagTypeName;
        this.style = style;
    }

    /**
     * Checks whether the given string is a valid priority tag type
     * @param s
     * @return true if the given string is "HIGH", "MEDIUM" or "LOW"
     */
    public static boolean isValidPriorityTagString(String s) {
        String capitalised = s.toUpperCase();
        return capitalised.equals("HIGH") || capitalised.equals("MEDIUM") || capitalised.equals("LOW");
    }

    public String getPriorityTagTypeName() {
        return priorityTagTypeName;
    }

    public String getStyle() {
        return style;
    }
}
