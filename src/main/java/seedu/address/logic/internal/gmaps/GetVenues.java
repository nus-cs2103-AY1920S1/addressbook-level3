package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.model.gmaps.Location;
import seedu.address.websocket.GmapsApi;
import seedu.address.websocket.NusmodApi;

/**
 * This class is used to get nus venues
 */
public class GetVenues {
    private JSONArray venuesNusMods;
    private ArrayList<Location> venues = new ArrayList<>();
    private GmapsApi gmapsApi = new GmapsApi();
    private LocationComparator locationComparator = new LocationComparator();
    GetVenues(){
    }
    GetVenues(JSONArray venuesNusMods, ArrayList<Location> venues) {
        this.venues = venues;
        this.venuesNusMods = venuesNusMods;
    }

    public ArrayList<Location> getVenues() throws ConnectException {
        GetVenues getVenuesWNusMods = getVenuesJsonArray();
        GetVenues getVenuesWVenues = getVenuesWNusMods.populateVenues();
        return getVenuesWVenues.venues;
    }

    private GetVenues populateVenues() throws ConnectException {
        if (venuesNusMods == null) {
            throw new InvalidParameterException("Cannot call getLocation before calling get"
                    + "getVenuesJsonArray");
        } else {
            for (int i = 0; i < venuesNusMods.size(); i++) {
                System.out.println("Processing " + venuesNusMods.get(i) + i + "/" + venuesNusMods.size());
                Location currLocation = getLocation(i);
                venues.add(currLocation);
            }
        }
//            venuesNusMods.parallelStream().forEach((locationName) -> {
//                int index = venuesNusMods.indexOf(locationName);
//                try {
//                    Location currLocation = getLocation(index);
//                    System.out.println("Processing " + venuesNusMods.get(index) +" "+ index + "/" + venuesNusMods.size());
//                    venues.add(currLocation);
//                } catch (ConnectException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        Collections.sort(venues, locationComparator);
        return new GetVenues(venuesNusMods, venues);
    }

    private GetVenues getVenuesJsonArray() {
        NusmodApi nusmodApi = new NusmodApi();
        JSONArray currVenuesNusMod = nusmodApi.getVenues("/1");
        return new GetVenues(currVenuesNusMod, venues);
    }

    private Location getLocation(int i) throws InvalidParameterException, ConnectException {
        if (venuesNusMods == null) {
            throw new InvalidParameterException("Cannot call getLocation before calling get"
                   + "getVenuesJsonArray");
        } else {
            String currLocationName = (String) venuesNusMods.get(i);
            Location currLocation = new Location(currLocationName, i);
            String gmapsRecognisedLocation = "NUS_" + currLocationName;
            JSONObject apiResponse = gmapsApi.getLocation(gmapsRecognisedLocation);
            String status = GmapsJsonUtils.getStatus(apiResponse);
            if (status.equals("OK")) {
                currLocation.setGoogleRecognisedLocation(gmapsRecognisedLocation);
            } else {
                String newGmapsRecognisedLocation = gmapsRecognisedLocation.split("-")[0];
                JSONObject newApiResponse = gmapsApi.getLocation(newGmapsRecognisedLocation);
                String newStatus = GmapsJsonUtils.getStatus(newApiResponse);
                if (newStatus.equals("OK")) {
                    currLocation.setGoogleRecognisedLocation(newGmapsRecognisedLocation);
                } else {
                    String newGmapsRecognisedLocation2 = "NUS_" + newGmapsRecognisedLocation.split("_")[1];
                    JSONObject newApiResponse2 = gmapsApi.getLocation(newGmapsRecognisedLocation2);
                    String newStatus2 = GmapsJsonUtils.getStatus(newApiResponse2);
                    if (newStatus2.equals("OK")) {
                        currLocation.setGoogleRecognisedLocation(newGmapsRecognisedLocation);
                    }
                }
            }
            return currLocation;
        }
    }
}
