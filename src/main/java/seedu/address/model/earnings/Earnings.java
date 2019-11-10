package seedu.address.model.earnings;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.model.classid.ClassId;

/**
 * Represents a Tutor's earnings.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Earnings {

    private static List<Earnings> earningsList = new ArrayList<>();
    private static ArrayList<Amount> totalEarnings = new ArrayList<>();
    private static HashMap<String, ArrayList<Earnings>> earningsListToAdd = new HashMap<String, ArrayList<Earnings>>();

    // Identity fields
    private Date date;
    private final ClassId classId;
    private final Amount amount;
    private final Type type;
    private Count count;
    private Claim claim;


    /**
     * Every field must be present and not null.
     */
    public Earnings(Date date, ClassId classId, Amount amount, Type type) {
        requireAllNonNull(date, classId, amount, type);
        this.date = date;
        this.classId = classId;
        this.amount = amount;
        this.type = type;
        this.count = new Count("0");
        this.claim = new Claim("pending submission");
    }

    public Date getDate() {
        return date;
    }

    public ClassId getClassId() {
        return classId;
    }

    public Amount getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public Claim getClaim() {
        return claim;
    }

    public Count getCount() {
        return count;
    }

    public HashMap getListOfAutoEarnings() {
        return earningsListToAdd;
    }

    public ArrayList<Earnings> getArrayListOfAutoEarnings(String key) {
        return earningsListToAdd.get(key);
    }

    public void putIntoList(String key, ArrayList<Earnings> list) {
        earningsListToAdd.put(key, list);
    }

    public static void removeEarnings(String currentDay, Earnings earnings) {
        earningsListToAdd.get(currentDay).remove(earnings);
    }

    /**
     * Reduces the number of times the earnings is auto added in.
     */
    public void reduceCount() {
        int number = Integer.parseInt(this.count.count);
        number--;
        this.count = new Count(String.valueOf(number));
    }

    public static void setList(List<Earnings> list) {
        earningsList = list;
    }

    public static void setHashMap(HashMap<String, ArrayList<Earnings>> map) {
        earningsListToAdd = map;
    }

    public static List<Earnings> getEarningsList() {
        return earningsList;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public static String getTotalEarnings() {
        double total = totalEarnings.stream().mapToDouble(Amount::doubleValue).sum();
        return String.format("%.2f", total);
    }

    public static void addToTotalEarnings(Amount amt) {
        totalEarnings.add(amt);
    }

    public static void replacePreviousEarnings(Index index, Amount amt) {
        totalEarnings.set(index.getZeroBased(), amt);
    }

    public static void deleteEarnings(Index index) {
        totalEarnings.remove(index.getZeroBased());
    }

    /**
     * Returns true if both earnings of the same date and classId have an identity field that is the same.
     * This defines a weaker notion of equality between two earnings.
     */
    public boolean isSameEarnings(Earnings otherEarnings) {
        if (otherEarnings == this) {
            return true;
        }

        return otherEarnings != null
                && otherEarnings.getDate().equals(getDate())
                && otherEarnings.getClassId().equals(getClassId())
                && otherEarnings.getType().equals(getType());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Earnings)) {
            return false;
        }

        Earnings otherEarnings = (Earnings) other;
        return otherEarnings.getDate().equals(getDate())
                && otherEarnings.getClassId().equals(getClassId())
                && otherEarnings.getAmount().equals(getAmount())
                && otherEarnings.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, classId, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Date: ")
                .append(getDate())
                .append(" Type: ")
                .append(getType())
                .append(" ClassId: ")
                .append(getClassId())
                .append(" Amount: ")
                .append(getAmount());
        return builder.toString();
    }
}
