package seedu.address.model.tag;

/**
 * Represents default tag types.
 */
public enum DefaultTagType {
    COMPLETED("Completed", "A module with this tag has been completed"),
    CORE("Core", "A core module is compulsory for all students in the NUS Computer "
            + "Science course in order to fulfill the graduation requirements"),
    SU("S/U-able", "Satisfactory / Unsatisfactory Option"),
    UE("UE", "Unrestricted Elective"),
    ULR("ULR", "University Level Requirements"),
    COMSEC_P("ComSec:P", "Computer Security Primary"), //P is primary, E is elective
    COMSEC_E("ComSec:E", "Computer Security Elective"),
    SE_P("SE:P", "Software Engineering Primary"),
    SE_E("SE:E", "Software Engineering Elective"),
    CGG_P("CGG:P", "Computer Games and Graphics Primary"),
    CGG_E("CGG:E", "Computer Games and Graphics Elective"),
    ALGO_P("Algo:P", "Algorithm Design Primary"),
    ALGO_E("Algo:E", "Algorithm Design Elective"),
    PARA_P("Para:P", "Parallel Computing Primary"),
    PARA_E("Para:E", "Parallel Computing Elective"),
    MIR_P("MIR:P", "Multimedia Information Retrieval Primary"),
    MIR_E("MIR:E", "Multimedia Information Retrieval Elective"),
    AI_P("AI:P", "Artificial Intelligence Primary"),
    AI_E("AI:E", "Artificial Intelligence Elective"),
    NDS_P("NDS:P", "Networking and Distributed Systems Primary"),
    NDS_E("NDS:E", "Networking and Distributed Systems Elective"),
    PL_P("PL:P", "Programming Languages Primary"),
    PL_E("PL:E", "Programming Languages Elective"),
    DB_P("DB:P", "Database Systems Primary"),
    DB_E("DB:E", "Database Systems Elective");

    private String defaultTagTypeName;
    private String description;

    DefaultTagType(String defaultTagTypeName, String description) {
        this.defaultTagTypeName = defaultTagTypeName;
        this.description = description;
    }

    public String getDefaultTagTypeName() {
        return defaultTagTypeName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Checks if the given name is one of the default tag type names.
     *
     * @param name The given name that is to be checked.
     * @return True if it is one of the default tag type names.
     */
    public static boolean contains(String name) {
        for (DefaultTagType defaultTagType : DefaultTagType.values()) {
            if (defaultTagType.getDefaultTagTypeName().compareToIgnoreCase(name) == 0) {
                return true;
            }
        }
        return false;
    }
}
