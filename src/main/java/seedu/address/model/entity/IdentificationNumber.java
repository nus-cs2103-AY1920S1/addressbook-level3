package seedu.address.model.entity;

//@@author shaoyi1997

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;

/**
 * Represents the ID number of each entity.
 * Guarantees: immutable, unique ID number will be generated from within the class
 */
public class IdentificationNumber {

    public static final String MESSAGE_CONSTRAINTS =
        "IdentificationNumber should have either of the following formats:\n" + "- B########\n" + "- W#####" + "- F##";

    private static final String ID_PREFIX_BODY = "B";
    private static final String ID_PREFIX_WORKER = "W";
    private static final String ID_PREFIX_FRIDGE = "F";
    private static final String INVALID_ENTITY_SUBCLASS =
            "IdentificationNumber is not implemented for this entity subclass.";

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();

    private int idNum;

    private String typeOfEntity;

    protected IdentificationNumber(Entity entity) {
        requireNonNull(entity);
        idNum = uniqueIds.addEntity(entity);
        if (entity instanceof Worker) {
            typeOfEntity = "W";
        } else if (entity instanceof Body) {
            typeOfEntity = "B";
        } else if (entity instanceof Fridge) {
            typeOfEntity = "F";
        } else {
            LogsCenter.getLogger(IdentificationNumber.class).severe(INVALID_ENTITY_SUBCLASS);
        }
    }

    //@@author ambervoong
    protected IdentificationNumber(Entity entity, int id) {
        requireNonNull(entity);
        assert(id > 0);
        idNum = id;
        UniqueIdentificationNumberMaps.addEntity(entity, id);
        if (entity instanceof Worker) {
            typeOfEntity = "W";
        } else if (entity instanceof Body) {
            typeOfEntity = "B";
        } else {
            typeOfEntity = "F";
        }
    }
    //@@author

    private IdentificationNumber(String typeOfEntity, int idNum) {
        this.typeOfEntity = typeOfEntity;
        this.idNum = idNum;
    }

    /**
     * Factory method for assigning a new {@code IdentificationNumber} to {@code body}
     */
    public static IdentificationNumber generateNewBodyId(Body body) {
        return new IdentificationNumber(body);
    }

    /**
     * Factory method for creating a custom {@code IdentificationNumber} with {@code id} to {@code body}
     */
    public static IdentificationNumber generateNewBodyId(Body body, int id) {
        return new IdentificationNumber(body, id);
    }

    /**
     * Factory method for assigning a new {@code IdentificationNumber} to {@code worker}
     */
    public static IdentificationNumber generateNewWorkerId(Worker worker) {
        return new IdentificationNumber(worker);
    }

    /**
     * Factory method for creating a custom {@code IdentificationNumber} with {@code id} to {@code worker}
     */
    public static IdentificationNumber generateNewWorkerId(Worker worker, int id) {
        return new IdentificationNumber(worker, id);
    }

    /**
     * Factory method for assigning a new {@code IdentificationNumber} to {@code fridge}
     */
    public static IdentificationNumber generateNewFridgeId(Fridge fridge) {
        return new IdentificationNumber(fridge);
    }

    /**
     * Factory method for creating a custom {@code IdentificationNumber} with {@code id} to {@code worker}
     */
    public static IdentificationNumber generateNewFridgeId(Fridge fridge, int id) {
        return new IdentificationNumber(fridge, id);
    }

    /**
     * Custom generates an {@code IdentificationNumber} with {@code typeOfEntity} and {@code idNum}
     */
    public static IdentificationNumber customGenerateId(String typeOfEntity, int idNum) {
        return new IdentificationNumber(typeOfEntity, idNum);
    }

    /**
     * Checks if the given {@code prefix} is a valid prefix for an entity.
     */
    private static boolean isValidIdPrefix (String prefix) {
        return prefix.equalsIgnoreCase(ID_PREFIX_BODY) || (
                prefix.equalsIgnoreCase(ID_PREFIX_FRIDGE) || prefix.equalsIgnoreCase(ID_PREFIX_WORKER));
    }

    /**
     * Checks if given {@code String id} is a valid identification number.
     */
    public static boolean isValidIdentificationNumber(String fullIdString) {
        int idLength = fullIdString.length();
        if (idLength < 3) {
            return false;
        }
        String idPrefix = fullIdString.charAt(0) + "";
        if (isValidIdPrefix(idPrefix)) {
            int numberLength = idLength - 1;
            switch (idPrefix) {
            case ID_PREFIX_BODY:
                return numberLength == 8;
            case ID_PREFIX_WORKER:
                return numberLength == 5;
            case ID_PREFIX_FRIDGE:
                return numberLength == 2;
            default:
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if given {@code String fullIdString} already exists in Mortago.
     */
    public static boolean isExistingIdentificationNumber(String fullIdString) {
        String typeOfEntity = fullIdString.charAt(0) + "";
        int idNum = Integer.parseInt(fullIdString.substring(1));
        switch (typeOfEntity) {
        case ID_PREFIX_BODY:
            return uniqueIds.containsBodyId(idNum);
        case ID_PREFIX_FRIDGE:
            return uniqueIds.containsFridgeId(idNum);
        case ID_PREFIX_WORKER:
            return uniqueIds.containsWorkerId(idNum);
        default:
            return false;
        }
    }

    /**
     * Checks if given {@code IdentificationNumber id} already exists in Mortago.
     */
    public static boolean isExistingIdentificationNumber(IdentificationNumber id) {
        return isExistingIdentificationNumber(id.toString());
    }

    public String getTypeOfEntity() {
        return typeOfEntity;
    }

    public int getIdNum() {
        return idNum;
    }

    @Override
    public String toString() {
        String paddedId;
        switch (typeOfEntity) {
        case ID_PREFIX_BODY:
            paddedId = String.format("%08d", idNum);
            break;
        case ID_PREFIX_WORKER:
            paddedId = String.format("%05d", idNum);
            break;
        case ID_PREFIX_FRIDGE:
            paddedId = String.format("%02d", idNum);
            break;
        default:
            paddedId = "Invalid ID";
        }
        return typeOfEntity + paddedId;
    }

    /**
     * Removes the mapping of the Id Number to its entity in the respective UniqueEntityList.
     */
    public void removeMapping() {
        switch (typeOfEntity) {
        case ID_PREFIX_BODY:
            uniqueIds.removeBodyId(idNum);
            break;
        case ID_PREFIX_WORKER:
            uniqueIds.removeWorkerId(idNum);
            break;
        case ID_PREFIX_FRIDGE:
            uniqueIds.removeFridgeId(idNum);
            break;
        default:
            return;
        }
    }

    //@@author ambervoong
    /**
     * Adds a mapping of the given Entity to this IdentificationNumber's numerical id number
     * in UniqueIdentificationNumberMaps.
     * @param entity
     */
    public void addMapping(Entity entity) {
        uniqueIds.addEntity(entity, idNum);
    }
    //@@author

    public Entity getMapping() {
        return uniqueIds.getMapping(typeOfEntity, idNum);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentificationNumber that = (IdentificationNumber) o;
        return idNum == that.idNum && typeOfEntity.equals(that.typeOfEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfEntity + idNum);
    }
}
