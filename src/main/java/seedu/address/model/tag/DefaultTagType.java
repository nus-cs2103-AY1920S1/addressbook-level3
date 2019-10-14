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
    COMSEC_P("ComSec:P"), // P means primary, E means elective
    COMSEC_E("ComSec:E"), // computer security
    SE_P("SE:P"), // software engineering
    SE_E("SE:E"),
    CGG_P("CGG:P"), // computer games and graphics
    CGG_E("CGG:E"),
    ALGO_P("Algo:P"), // algorithm design
    ALGO_E("Algo:E"),
    PARA_P("Para:P"), // parallel computing
    PARA_E("Para:E"),
    MIR_P("MIR:P"), // multimedia information retrieval
    MIR_E("MIR:E"),
    AI_P("AI:P"), // artificial intelligence
    AI_E("AI:E"),
    NDS_P("NDS:P"), // networking and distributed systems
    NDS_E("NDS:E"),
    PL_P("PL:P"), // programming languages
    PL_E("PL:E"),
    DB_P("DB:P"), // database systems
    DB_E("DB:E");

    private String defaultTagTypeName;

    private DefaultTagType(String defaultTagTypeName) {
        this.defaultTagTypeName = defaultTagTypeName;
    }

    public String getDefaultTagTypeName() {
        return defaultTagTypeName;
    }

    /**
     * Checks if the given name is one of the default tag type names.
     * @param name The given name that is to be checked.
     * @return True if it is one of the default tag type names.
     */
    public static boolean contains(String name) {
        for (DefaultTagType defaultTagType : DefaultTagType.values()) {
            if (defaultTagType.getDefaultTagTypeName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
