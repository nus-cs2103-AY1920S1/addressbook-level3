package seedu.address.logic.internal.gmaps;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.model.gmaps.Location;
import seedu.address.websocket.GmapsApi;
import seedu.address.websocket.NusmodApi;

/**
 * This class is used to get nus venues
 */
public class ProcessVenues implements Serializable {
    private JSONArray venuesNusMods;
    private ArrayList<Location> venues = new ArrayList<>();
    private transient GmapsApi gmapsApi = new GmapsApi();
    private ArrayList<String> gmapsRecognisedLocationList = new ArrayList<>();

    public ProcessVenues(){
    }
    private ProcessVenues(JSONArray venuesNusMods, ArrayList<String> gmapsRecognisedLocationList,
                          ArrayList<Location> venues) {
        this.gmapsRecognisedLocationList = gmapsRecognisedLocationList;
        this.venues = venues;
        this.venuesNusMods = venuesNusMods;
    }

    public ArrayList<Location> getLocations() {
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
        return gmapsRecognisedLocationList;
    }

    /**
     * This method is used to process the nus mods venues api.
     * @return
     * @throws ConnectException
     */
    private ProcessVenues populateVenues() throws ConnectException {
        if (venuesNusMods == null) {
            throw new InvalidParameterException("Cannot call getLocation before calling get"
                    + "getVenuesJsonArray");
        } else {
            for (int i = 0; i < venuesNusMods.size(); i++) {
                System.out.println("Processing " + venuesNusMods.get(i) + " " + i + "/" + venuesNusMods.size());
                Location currLocation = getLocation(i);
                venues.add(currLocation);
            }
        }
        return new ProcessVenues(venuesNusMods, gmapsRecognisedLocationList, venues);
    }

    private ProcessVenues getVenuesJsonArray() {
        NusmodApi nusmodApi = new NusmodApi();
        JSONArray currVenuesNusMod = nusmodApi.getVenues("/1");
        return new ProcessVenues(currVenuesNusMod, gmapsRecognisedLocationList, venues);
    }

    private Location getLocation(int i) throws InvalidParameterException, ConnectException {
        if (venuesNusMods == null) {
            throw new InvalidParameterException("Cannot call getLocation before calling get"
                   + "getVenuesJsonArray");
        } else {
            String currLocationName = (String) venuesNusMods.get(i);
            Location currLocation = new Location(currLocationName, i);
            String gmapsRecognisedLocation = "NUS_" + currLocationName;
            String newGmapsRecognisedLocation = gmapsRecognisedLocation.split("-")[0];
            if (gmapsRecognisedLocationList.contains(newGmapsRecognisedLocation)) {
                currLocation.setGoogleRecognisedLocation(newGmapsRecognisedLocation);
            } else {
                JSONObject apiResponse = gmapsApi.getLocation(newGmapsRecognisedLocation);
                String status = GmapsJsonUtils.getStatus(apiResponse);
                if (status.equals("OK")) {
                    currLocation.setGoogleRecognisedLocation(newGmapsRecognisedLocation);
                    gmapsRecognisedLocationList.add(newGmapsRecognisedLocation);
                    System.out.println(gmapsRecognisedLocationList);
                } else {
                    String newGmapsRecognisedLocation2 = "NUS_" + newGmapsRecognisedLocation.split("_")[1];
                    JSONObject newApiResponse2 = gmapsApi.getLocation(newGmapsRecognisedLocation2);
                    String newStatus2 = GmapsJsonUtils.getStatus(newApiResponse2);
                    if (newStatus2.equals("OK")) {
                        currLocation.setGoogleRecognisedLocation(newGmapsRecognisedLocation2);
                        System.out.println(gmapsRecognisedLocationList);
                        gmapsRecognisedLocationList.add(newGmapsRecognisedLocation2);
                    } else {
                        System.out.println("Cannot identify" + newGmapsRecognisedLocation2);
                    }
                }
            }

            return currLocation;
        }
    }
}
