package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Set;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;

/**
 * Maps of unique Identification Numbers for each Entity.
 */
public class UniqueIdentificationNumberMaps {

    private static HashMap<Integer, Worker> uniqueWorkerMap = new HashMap<>();
    private static HashMap<Integer, Body> uniqueBodyMap = new HashMap<>();
    private static HashMap<Integer, Fridge> uniqueFridgeMap = new HashMap<>();

    /**
     * Adds the {@code entity} to the respective map and returns the identification number assigned.
     */
    public static Integer addEntity(Entity entity) {
        requireNonNull(entity);
        if (entity instanceof Worker) {
            return putWorker((Worker) entity);
        } else if (entity instanceof Body) {
            return putBody((Body) entity);
        } else if (entity instanceof Fridge) {
            return putFridge((Fridge) entity);
        } else {
            LogsCenter.getLogger(UniqueIdentificationNumberMaps.class)
                    .severe("Not a valid entity subclass");
            return null;
        }
    }

    //@@author ambervoong
    /**
     * Adds the {@code entity} to the respective map with the given ID.
     * Guarantees: the entity is not null
     * @param entity entity to be added
     * @param id ID number the entity will be assigned
     */
    public static void addEntity(Entity entity, int id) {
        assert(entity != null);
        if (entity instanceof Body) {
            putBody((Body) entity, id);
        } else if (entity instanceof Worker) {
            putWorker((Worker) entity, id);
        } else {
            putFridge((Fridge) entity, id);
        }
    }
    //@@author

    /**
     * Adds the {@code worker} into the Map at an empty id or the next largest id.
     */
    private static Integer putWorker(Worker worker) {
        Set<Integer> keys = uniqueWorkerMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueWorkerMap.get(id) == null) {
                uniqueWorkerMap.put(id, worker);
                return id;
            }
        }
        int newId = numOfKeys + 1;
        uniqueWorkerMap.put(newId, worker);
        return newId;
    }

    //@@author ambervoong
    /**
     * Adds the {@code Worker} into the Map at the given ID.
     * @param worker the worker to be added
     * @param id the id the worker will be assigned to
     */
    private static void putWorker(Worker worker, int id) {
        if (uniqueWorkerMap.get(id) == null) {
            uniqueWorkerMap.put(id, worker);
        }
    }
    //@@author

    /**
     * Adds the {@code fridge} into the Map at an empty id or the next largest id.
     */
    private static Integer putFridge(Fridge fridge) {
        Set<Integer> keys = uniqueFridgeMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueFridgeMap.get(id) == null) {
                uniqueFridgeMap.put(id, fridge);
                return id;
            }
        }
        int newId = numOfKeys + 1;
        uniqueFridgeMap.put(newId, fridge);
        return newId;
    }

    //@@author ambervoong
    /**
     * Adds the {@code Fridge} into the Map at the given ID.
     */
    private static void putFridge(Fridge fridge, int id) {
        Set<Integer> keys = uniqueFridgeMap.keySet();
        if (uniqueFridgeMap.get(id) == null) {
            uniqueFridgeMap.put(id, fridge);
        }
    }
    //@@author

    /**
     * Adds the {@code body} into the Map at an empty id or the next largest id.
     */
    private static Integer putBody(Body body) {
        Set<Integer> keys = uniqueBodyMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueBodyMap.get(id) == null) {
                uniqueBodyMap.put(id, body);
                return id;
            }
        }
        int newId = numOfKeys + 1;
        uniqueBodyMap.put(newId, body);
        return newId;
    }

    //@@author ambervoong
    /**
     * Adds the {@code body} into the Map at the given ID.
     */
    private static void putBody(Body body, int id) {
        Set<Integer> keys = uniqueBodyMap.keySet();
        if (uniqueBodyMap.get(id) == null) {
            uniqueBodyMap.put(id, body);
        }
    }
    //@@author

    public boolean containsWorkerId(int id) {
        return uniqueWorkerMap.containsKey(id);
    }

    public boolean containsBodyId(int id) {
        return uniqueBodyMap.containsKey(id);
    }

    public boolean containsFridgeId(int id) {
        return uniqueFridgeMap.containsKey(id);
    }

    public Worker removeWorkerId(int id) {
        return uniqueWorkerMap.remove(id);
    }

    public Body removeBodyId(int id) {
        return uniqueBodyMap.remove(id);
    }

    public Fridge removeFridgeId(int id) {
        return uniqueFridgeMap.remove(id);
    }

    /**
     * Clears all mappings of id to entity.
     */
    public static void clearAllEntries() {
        uniqueFridgeMap.clear();
        uniqueBodyMap.clear();
        uniqueWorkerMap.clear();
    }

    /**
     * Returns the entity that belongs to the supplied Identification Number details.
     */
    protected static Entity getMapping(String typeOfEntity, int id) {
        if (typeOfEntity.equalsIgnoreCase("w")) {
            return uniqueWorkerMap.get(id);
        } else if (typeOfEntity.equalsIgnoreCase("b")) {
            return uniqueBodyMap.get(id);
        } else if (typeOfEntity.equalsIgnoreCase("f")) {
            return uniqueFridgeMap.get(id);
        } else {
            LogsCenter.getLogger(UniqueIdentificationNumberMaps.class).severe("Invalid typeOfEntity");
            return null;
        }
    }
}
