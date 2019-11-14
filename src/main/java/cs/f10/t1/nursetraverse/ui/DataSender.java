//@@author francislow

package cs.f10.t1.nursetraverse.ui;

/**
 * An interface for GUI to allow transfer of String data to another GUI
 * This interface was created so as to provide a facade as gui class transfers data to another gui class
 */
public interface DataSender {
    String[] sendData();
}
