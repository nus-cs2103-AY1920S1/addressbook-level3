package seedu.address.model.entity;

//@@author shaoyi1997

import java.util.Objects;

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

    private static int countOfBodies = 0;
    private static int countOfWorkers = 0;
    private static int countOfFridges = 0;

    private int idNum;
    private String typeOfEntity;

    // todo: check for duplicates
    protected IdentificationNumber(String typeOfEntity) {
        this.typeOfEntity = typeOfEntity;
        switch (typeOfEntity) {
        case ID_PREFIX_BODY:
            countOfBodies++;
            idNum = countOfBodies;
            break;
        case ID_PREFIX_WORKER:
            countOfWorkers++;
            idNum = countOfWorkers;
            break;
        case ID_PREFIX_FRIDGE:
            countOfFridges++;
            idNum = countOfFridges;
            break;
        default:
            throw new IllegalArgumentException("Invalid entity type");
        }
    }

    private IdentificationNumber(String typeOfEntity, int idNum) {
        this.typeOfEntity = typeOfEntity;
        this.idNum = idNum;
    }

    public static IdentificationNumber generateNewBodyId() {
        return new IdentificationNumber(ID_PREFIX_BODY);
    }

    public static IdentificationNumber generateNewWorkerId() {
        return new IdentificationNumber(ID_PREFIX_WORKER);
    }

    public static IdentificationNumber generateNewFridgeId() {
        return new IdentificationNumber(ID_PREFIX_FRIDGE);
    }

    public static IdentificationNumber customGenerateId(String typeOfEntity, int idNum) {
        return new IdentificationNumber(typeOfEntity, idNum);
    }

    private static boolean isValidIdPrefix (String prefix) {
        return prefix.equalsIgnoreCase(ID_PREFIX_BODY) || (
                prefix.equalsIgnoreCase(ID_PREFIX_FRIDGE) || prefix.equalsIgnoreCase(ID_PREFIX_WORKER));
    }

    /**
     * Checks if given {@code String id} is a valid identification number.
     */
    public static boolean isValidIdentificationNumber(String id) {
        String idPrefix = id.charAt(0) + "";
        if (isValidIdPrefix(idPrefix)) {
            int numberLength = id.substring(1).length();
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
     * Checks if a given {@code IdentificationNumber id} already exists.
     * @param id
     * @return
     */
    public static boolean isExistingidentificationNumber(IdentificationNumber id) {
        if (isValidIdentificationNumber(id.toString())) {
            String idPrefix = id.toString().charAt(0) + "";
            switch (idPrefix) {
            case ID_PREFIX_BODY:
                return id.getIdNum() <= countOfBodies;
            case ID_PREFIX_WORKER:
                return id.getIdNum() <= countOfWorkers;
            case ID_PREFIX_FRIDGE:
                return id.getIdNum() <= countOfFridges;
            default:
                return false;
            }
        }
        return false;
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

    public static void decrementCountOfBodies() {
        countOfBodies--;
    }

    public static void decrementCountOfWorkers() {
        countOfWorkers--;
    }

    public static void decrementCountOfFridges() {
        countOfFridges--;
    }

    public static void resetCountOfBodies() {
        countOfBodies = 0;
    }

    public static void resetCountOfWorkers() {
        countOfWorkers = 0;
    }

    public static void resetCountOfFridges() {
        countOfFridges = 0;
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
        return typeOfEntity.equals(that.typeOfEntity) && idNum == that.idNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfEntity + idNum);
    }
}
