package cs.f10.t1.nursetraverse.testutil;

/**
 * Dummy mutable class for testing defensive copying.
 */
public class Mutable {
    private int data = 0;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Mutable) {
            Mutable o = (Mutable) other;
            return this.getData() == o.getData();
        } else {
            return false;
        }
    }
}
