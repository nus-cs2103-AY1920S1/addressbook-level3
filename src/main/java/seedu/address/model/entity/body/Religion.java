package seedu.address.model.entity.body;

//@@author ambervoong
/**
 * Enumerates all of the major religions.
 */
public enum Religion {
    CHRISTIANITY, ISLAM, NONRELIGIOUS, HINDUISM, BUDDHISM, SIKHISM,
            JUCHE, SPIRITISM, JUDAISM, JAINISM, SHINTO;

    /**
     * Parses {@code String religion} to return the corresponding {@code Religion}.
     */
    public static Religion parseReligion(String religion) {
        assert(religion != null);
        String religionLowerCaps = religion.toLowerCase();
        switch(religionLowerCaps) {
        case "christianity":
            return CHRISTIANITY;
        case "islam":
            return ISLAM;
        case "hinduism":
            return HINDUISM;
        case "buddhism":
            return BUDDHISM;
        case "sikhism":
            return SIKHISM;
        case "juche":
            return JUCHE;
        case "spiritism":
            return SPIRITISM;
        case "shinto":
            return SHINTO;
        case "judaism":
            return JUDAISM;
        case "jainism":
            return JAINISM;
        default:
            return NONRELIGIOUS;
        }
    }
}
