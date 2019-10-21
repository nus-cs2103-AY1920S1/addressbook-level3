package seedu.address.logic.internal.gmaps;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;

import org.json.simple.JSONArray;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.GmapsApi;
import seedu.address.websocket.NusModsApi;

/**
 * This class is used to get nus venues
 */
public class ProcessVenues implements Serializable {
    private static final long serialVersionUID = 652968509826775762L;
    private JSONArray venuesNusMods;
    private ArrayList<Location> venues = new ArrayList<>();
    private transient GmapsApi gmapsApi = new GmapsApi();
    private SanitizeLocation sanitizeLocation = new SanitizeLocation(gmapsApi);

    public ProcessVenues(){
    }
    private ProcessVenues(JSONArray venuesNusMods, ArrayList<Location> venues, SanitizeLocation sanitizeLocation) {
        this.venues = venues;
        this.sanitizeLocation = sanitizeLocation;
        this.venuesNusMods = venuesNusMods;
    }

    public ArrayList<Location> getLocations() throws TimeBookInvalidState {
        if (venuesNusMods == null) {
            throw new TimeBookInvalidState("Cannot call getLocation before getVenuesJsonArray");
        }
        return this.venues;
    }

    /**
     * This method is used to load the previously processed location instead of initialising again.
     * @return
     */

    public ProcessVenues load() {
        ProcessVenues processVenues = null;
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("data/ProcessVenuesSerialization");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            processVenues = (ProcessVenues) in.readObject();

            in.close();
            file.close();

            System.out.println("ProcessVenuesSerialization has been deserialized ");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        return processVenues;
    }

    /**
     * This method is used to process the venues with the latest information from NUSmods and Google Maps
     * @return
     * @throws ConnectException
     */
    public ProcessVenues process() throws ConnectException {
        ProcessVenues processVenuesWNusMods = getVenuesJsonArray();
        ProcessVenues processVenuesWVenues = processVenuesWNusMods.populateVenues();
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("data/ProcessVenuesSerialization");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(processVenuesWVenues);

            out.close();
            file.close();

            System.out.println("ProcessVenues has been serialized");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return processVenuesWVenues;
    }

    public ArrayList<String> getGmapsRecognisedLocationList() {
        return sanitizeLocation.getValidLocationList();
    }

    /**
     * This method is used to process the nus mods venues api.
     * @return
     * @throws ConnectException
     */
    private ProcessVenues populateVenues() throws ConnectException {
        if (venuesNusMods == null) {
            throw new IllegalStateException("Cannot call getLocation before calling get"
                    + "getVenuesJsonArray");
        } else {
            for (int i = 0; i < venuesNusMods.size(); i++) {
                System.out.println("Processing " + venuesNusMods.get(i) + " " + i + "/" + venuesNusMods.size());
                Location currLocation = getLocation(i);
                venues.add(currLocation);
            }
        }
        sanitizeLocation.save();
        return new ProcessVenues(venuesNusMods, venues, sanitizeLocation);
    }

    private ProcessVenues getVenuesJsonArray() {
        NusModsApi nusModApi = new NusModsApi();
        JSONArray currVenuesNusMod = nusModApi.getVenues("/1").orElse(new JSONArray());
        return new ProcessVenues(currVenuesNusMod, venues, sanitizeLocation);
    }

    private Location getLocation(int i) throws ConnectException {
        if (venuesNusMods == null) {
            throw new IllegalStateException("Cannot call getLocation before calling get"
                   + "getVenuesJsonArray");
        } else {
            String locationName = (String) venuesNusMods.get(i);
            Location currLocation = new Location(locationName);
            try {
                String validLocation = sanitizeLocation.sanitize(locationName);
                currLocation.setValidLocation(validLocation);
                System.out.println(locationName + " identified as " + validLocation);
            } catch (TimeBookInvalidLocation e) {
                System.out.println(e.getMessage());
            }
            return currLocation;
        }
    }
}
