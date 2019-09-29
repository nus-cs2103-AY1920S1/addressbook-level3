package seedu.address.model.Entity;

import java.util.Objects;

public class Location {
    private final int tableNumber;
    private Team team;

    /**
     * Constructor without team.
     * @param tableNumber
     */
    public Location(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    // Getter

    public int getTableNumber() {
        return tableNumber;
    }

    public Team getTeam() {
        return team;
    }

    // Setter


    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Constructor with team.
     *
     * @param tableNumber
     * @param team
     */
    public Location(int tableNumber, Team team) {
        this.tableNumber = tableNumber;
        this.team = team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tableNumber, this.team);
    }

    @Override
    public boolean equals(Object other) {
        Location otherLocation = ((Location) other);
        return otherLocation == this |
                (otherLocation.getTeam() == this.getTeam()
                && otherLocation.getTableNumber() == this.getTableNumber());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Table Number: ")
                .append(getTableNumber())
                .append(" Team: ")
                .append(getTeam());
        return builder.toString();
    }
}
