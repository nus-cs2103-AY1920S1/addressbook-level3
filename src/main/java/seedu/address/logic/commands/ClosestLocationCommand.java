package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATIONS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.internal.gmaps.LocationArrayListUtils;
import seedu.address.model.Model;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;

/**
 * This is the class for the command to find the closest location
 */
public class ClosestLocationCommand extends Command {

    public static final String COMMAND_WORD = "closestlocation";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_LOCATIONS + " LIST OF LOCATIONS ";
    public static final String MESSAGE_SUCCESS = "Closest location found: ";
    public static final String MESSAGE_FAILURE = "Cannot find closest location.";

    private LocationGraph locationGraph;
    private ArrayList<String> locationNameList;

    public ClosestLocationCommand(ArrayList<String> locationNameList) {
        this.locationNameList = locationNameList;
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("data/LocationGraphSerialization");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            locationGraph = (LocationGraph) in.readObject();

            in.close();
            file.close();

            System.out.println("LocationGraph has been deserialized");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    /**
     * This method is used to find the closes location from the location graph
     * @return
     */
    private String closestLocation() {
        ArrayList<ArrayList<Long>> currMatrix = new ArrayList<ArrayList<Long>>();
        ArrayList<String> gmapsRecognisedLocationList = locationGraph.getGmapsRecognisedLocationList();
        ArrayList<Location> locations = locationGraph.getLocations();
        for (int i = 0; i < locationNameList.size(); i++) {
            int indexLocations = LocationArrayListUtils.getIndex(locations, locationNameList.get(i));
            Location currLocation = locations.get(indexLocations);
            String gmapsRecognisedLocation = currLocation.getGoogleRecognisedLocation();
            if (gmapsRecognisedLocation != null) {
                System.out.println(gmapsRecognisedLocationList);
                int indexGmapsRecognisedLocation = gmapsRecognisedLocationList.indexOf(gmapsRecognisedLocation);
                System.out.println("Index of " + gmapsRecognisedLocation + " is " + indexGmapsRecognisedLocation);
                ArrayList<Long> currRow = locationGraph.getLocationRow(indexGmapsRecognisedLocation);
                currMatrix.add(currRow);
            } else {
                locationNameList.remove(i);
            }
        }
        ArrayList<Long> totalDistance = new ArrayList<>();
        for (int j = 0; j < currMatrix.get(0).size(); j++) {
            totalDistance.add((long) 0);
            for (int i = 0; i < locationNameList.size(); i++) {
                Long newDistance = totalDistance.get(j) + currMatrix.get(i).get(j);
                totalDistance.set(j, newDistance);
            }
        }
        int minIndex = 0;
        Long min = Long.MAX_VALUE;
        for (int i = 0; i < totalDistance.size(); i++) {
            if (totalDistance.get(i) < min) {
                minIndex = i;
                min = totalDistance.get(i);
            }
        }
        return gmapsRecognisedLocationList.get(minIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS + closestLocation());
    }

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof ClosestLocationCommand); // instanceof handles nulls
    }

}
