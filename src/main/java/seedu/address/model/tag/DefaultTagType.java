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
