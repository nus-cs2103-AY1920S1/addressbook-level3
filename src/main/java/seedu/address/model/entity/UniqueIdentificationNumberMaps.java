package seedu.address.model.entity;

import java.util.HashMap;
import java.util.Set;

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
        if (entity instanceof Worker) {
            return putWorker((Worker) entity);
        } else if (entity instanceof Body) {
            return putBody((Body) entity);
        } else {
            return putFridge((Fridge) entity);
        }
    }

    /**
     * Adds the {@code worker} into the Map at an empty id or the next largest id.
     */
    private static Integer putWorker(Worker worker) {
        Set<Integer> keys = uniqueWorkerMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueWorkerMap.get(id) == null) {
                uniqueWorkerMap.put(id, worker);
                break;
            }
        }
        int newId = numOfKeys + 1;
        uniqueWorkerMap.put(newId, worker);
        return newId;
    }

    /**
     * Adds the {@code fridge} into the Map at an empty id or the next largest id.
     */
    private static Integer putFridge(Fridge fridge) {
        Set<Integer> keys = uniqueFridgeMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueFridgeMap.get(id) == null) {
                uniqueFridgeMap.put(id, fridge);
                break;
            }
        }
        int newId = numOfKeys + 1;
        uniqueFridgeMap.put(newId, fridge);
        return newId;
    }

    /**
     * Adds the {@code body} into the Map at an empty id or the next largest id.
     */
    private static Integer putBody(Body body) {
        Set<Integer> keys = uniqueBodyMap.keySet();
        int numOfKeys = keys.size();
        for (int id = 1; id <= numOfKeys; id++) {
            if (uniqueBodyMap.get(id) == null) {
                uniqueBodyMap.put(id, body);
                break;
            }
        }
        int newId = numOfKeys + 1;
        uniqueBodyMap.put(newId, body);
        return newId;
    }

    public boolean containsWorkerId(int id) {
        return uniqueWorkerMap.containsKey(id);
    }

    public boolean containsBodyId(int id) {
        return uniqueBodyMap.containsKey(id);
    }

    public boolean containsFridgeId(int id) {
        return uniqueFridgeMap.containsKey(id);
    }

    public void removeWorkerId(int id) {
        uniqueWorkerMap.remove(id);
    }

    public void removeBodyId(int id) {
        uniqueBodyMap.remove(id);
    }

    public void removeFridgeId(int id) {
        uniqueFridgeMap.remove(id);
    }

    /**
     * Clears all mappings of id to entity.
     */
    public void clearAllEntries() {
        uniqueFridgeMap.clear();
        uniqueBodyMap.clear();
        uniqueWorkerMap.clear();
    }
}
