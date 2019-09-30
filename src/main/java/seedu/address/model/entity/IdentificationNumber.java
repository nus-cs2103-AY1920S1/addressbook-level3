package seedu.address.model.entity;

//@@author shaoyi1997

import java.util.Objects;

/**
 * Represents the ID number of each entity.
 * Guarantees: immutable, unique ID number will be generated from within the class
 */
public class IdentificationNumber {

    private static final String ID_PREFIX_BODY = "B";
    private static final String ID_PREFIX_WORKER = "W";
    private static final String ID_PREFIX_FRIDGE = "F";

    private static int countOfBodies = 0;
    private static int countOfWorkers = 0;
    private static int countOfFridges = 0;

    private int idNum;
    private String typeOfEntity;

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

    public static IdentificationNumber generateNewBodyId() {
        return new IdentificationNumber(ID_PREFIX_BODY);
    }

    public static IdentificationNumber generateNewWorkerId() {
        return new IdentificationNumber(ID_PREFIX_WORKER);
    }

    public static IdentificationNumber generateNewFridgeId() {
        return new IdentificationNumber(ID_PREFIX_FRIDGE);
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
