package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;

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
    public static void execute() throws ConnectException {
        ProcessVenues processVenues = new ProcessVenues().process();
        LocationGraph locationGraph = new LocationGraph(processVenues);
        ProcessLocationGraph processLocationGraph = new ProcessLocationGraph(locationGraph);
        processLocationGraph.populateMatrix();
        processLocationGraph.saveLocationGraph();
    }
}
