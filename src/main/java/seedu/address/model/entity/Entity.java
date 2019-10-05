package seedu.address.model.entity;

import java.util.HashMap;

/**
 * Represents a {@code Entity} in the address book.
 * Entity is the superclass of Participant, Mentor, Team.
 */
public abstract class Entity {

    protected final Id id; // Note: id is unique and should never be mutable.
    protected Name name;

    /**
     * Constructs an {@code Entity}.
     *
     * @param id Identification number of Entity.
     * @param name Name of Entity.
     */
    public Entity(Id id, Name name) {
        this.id = id;
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    public Id getId() {
        return this.id;
    }

    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the bare details of the Entity in the HashMap format
     *
     * @return HashMap Maps String to a String, each key name is mapped to name of Entity,
     * key id is mapped to id of Entity.
     */
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("name", this.getName().toString());
        fields.put("id", this.getId().toString());
        return fields;
    };

    /**
     * Returns the full details of the Entity, according to Entity type, in the HashMap format.
     *
     * @return HashMap Maps String to a String, each key is mapped to a data field of the specific Entity type.
     */
    public abstract HashMap<String, String> viewDetailed();
}
