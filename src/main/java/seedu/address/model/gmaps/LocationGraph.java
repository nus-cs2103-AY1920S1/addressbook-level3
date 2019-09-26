package seedu.address.model.gmaps;

import java.util.Hashtable;

/**
 * This is the graph object that contains the information for location vertex
 */
public class LocationGraph {
    private Hashtable<String, LocationVertex> graph = new Hashtable<>();

    LocationGraph() {
    }

    LocationGraph(Hashtable<String, LocationVertex> graph) {
        this.graph = graph;
    }

    /**
     * Add vertex to the graph
     * @param locationVertex
     * @return a new LocationGraph with the newly added LocationGrpah
     */

    public LocationGraph addVertex(LocationVertex locationVertex) {
        graph.put(locationVertex.getLocationVertexName(), locationVertex);
        return new LocationGraph(graph);
    }

    public LocationVertex getVertex(String locationName) {
        return graph.get(locationName);
    }


}
