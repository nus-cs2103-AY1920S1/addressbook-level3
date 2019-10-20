package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.gmaps.LocationGraph;

/**
 * This method is used to get update the serialized file
 */
public class UpdateGmapsSerialization {
    UpdateGmapsSerialization(){
    }

    /**
     * This method is used to save the instance of LocationGraph into memory.
     * @throws ConnectException
     */
    public static void updateAll() throws ConnectException, TimeBookInvalidState {
        ProcessVenues processVenues = new ProcessVenues().process();
        LocationGraph locationGraph = new LocationGraph(processVenues);
        ProcessLocationGraph processLocationGraph = new ProcessLocationGraph(locationGraph);
        processLocationGraph.populateMatrix();
        processLocationGraph.saveLocationGraph();
    }

    /**
     * This method is used to update the ProcessVenues Object
     * @throws ConnectException
     */
    public static void updateProcessVenues() throws ConnectException {
        ProcessVenues processVenues = new ProcessVenues().process();
    }

    /**
     * This method is used to update the Location Graph object only
     * @throws ConnectException
     */
    public static void updateLocationGraph() throws ConnectException, TimeBookInvalidState {
        ProcessVenues processVenues = new ProcessVenues().load();
        LocationGraph locationGraph = new LocationGraph(processVenues);
        ProcessLocationGraph processLocationGraph = new ProcessLocationGraph(locationGraph);
        processLocationGraph.populateMatrix();
        processLocationGraph.saveLocationGraph();
    }

    public static void main(String[] args) throws ConnectException, TimeBookInvalidState {
        UpdateGmapsSerialization.updateProcessVenues();
    }
}
