package seedu.moolah.model.statistics;

/**
 * Represents a utility class with an entry of 5 elements
 */
public class FiveElementTableEntry {

    private String name;

    private double overlapAmount;

    private int overlapNumEntries;

    private double differenceAmount;

    private int differenceNumEntries;

    public FiveElementTableEntry(String name, double overlapAmount, int overlapNumEntries,
                                  double differenceAmount, int differenceNumEntries) {

        this.name = name;
        this.overlapAmount = overlapAmount;
        this.overlapNumEntries = overlapNumEntries;
        this.differenceAmount = differenceAmount;
        this.differenceNumEntries = differenceNumEntries;
    }

    public static FiveElementTableEntry createEmptyWithName(String name) {
        return new FiveElementTableEntry(name, 0, 0, 0 , 0);
    }

    /**
     * Combines 2 ThreeElementTableEntries into 1 FiveElementTableEntry
     * @param unionTable A table containing elements in an overlapping period
     * @param differenceTable A table containing elements that differ between 2 periods
     */
    static FiveElementTableEntry combine(ThreeElementTableEntry unionTable, ThreeElementTableEntry differenceTable) {
        if (!unionTable.getName().equals(differenceTable.getName())) {
            return null;
        }
        return new FiveElementTableEntry(unionTable.getName(), unionTable.getAmount(), unionTable.getNumEntries(),
                differenceTable.getAmount(), differenceTable.getNumEntries());
    }

    public String getName() {
        return name;
    }

    public double getOverlapAmount() {
        return overlapAmount;
    }

    public int getOverlapNumEntries() {
        return overlapNumEntries;
    }

    public double getDifferenceAmount() {
        return differenceAmount;
    }

    public int getDifferenceNumEntries() {
        return differenceNumEntries;
    }

    @Override
    public String toString() {
        return String.format("[%s %f %d %f %d]", getName(), getOverlapAmount(),
                getOverlapNumEntries(), getDifferenceAmount(), getDifferenceNumEntries());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FiveElementTableEntry)
                && (name.equals(((FiveElementTableEntry) other).getName()))
                && (overlapAmount == (((FiveElementTableEntry) other).getOverlapAmount()))
                && (overlapNumEntries == (((FiveElementTableEntry) other).getOverlapNumEntries()))
                && (differenceAmount == (((FiveElementTableEntry) other).getDifferenceAmount()))
                && (differenceNumEntries == (((FiveElementTableEntry) other).getDifferenceNumEntries()));
    }



}
