package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;


/**
 * Exports current data to CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_EXPORT_SUCCESS = "You have successfully exported the data into CSV file.";
    public static final String MESSAGE_EXPORT_FAIL = "Failed to export data into CSV file.";

    private Path customerFile;
    private Path phoneFile;
    private Path scheduleFile;
    private Path orderFile;

    private final String fileName;

    public ExportCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        UserPrefs userPrefs = new UserPrefs();
        customerFile = userPrefs.getCustomerBookFilePath();
        phoneFile = userPrefs.getPhoneBookFilePath();
        scheduleFile = userPrefs.getScheduleBookFilePath();
        orderFile = userPrefs.getOrderBookFilePath();
        String pathName = "data/" + fileName + ".csv";

        try {
            String dataCsv = this.getCsvString();
            FileUtil.writeToFile(Paths.get(pathName), dataCsv);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    public String getCsvString() throws IOException {
        StringBuilder data = new StringBuilder();

        // Building Customer CSV string.
        StringBuilder customerData = new StringBuilder();
        BufferedReader customerReader = new BufferedReader(new FileReader(String.valueOf(customerFile)));
        String currCustomerLine;
        while ((currCustomerLine = customerReader.readLine()) != null) {
            customerData.append(currCustomerLine);
        }
        JSONObject jsonObjectCustomerData;
        jsonObjectCustomerData = new JSONObject(customerData.toString());
        JSONArray customerDataArray = jsonObjectCustomerData.getJSONArray("customers");
        String customerCsv = "Our Customers:" + "\n" + CDL.toString(customerDataArray) + "\n" + "\n";
        customerReader.close();

        // Building Phone CSV string.
        StringBuilder phoneData = new StringBuilder();
        BufferedReader phoneReader = new BufferedReader(new FileReader(String.valueOf(phoneFile)));
        String currPhoneLine;
        while ((currPhoneLine = phoneReader.readLine()) != null) {
            phoneData.append(currPhoneLine);
        }
        JSONObject jsonObjectPhoneData;
        jsonObjectPhoneData = new JSONObject(phoneData.toString());
        JSONArray phoneDataArray = jsonObjectPhoneData.getJSONArray("phones");
        String phoneCsv = "Phones list:" + "\n" + CDL.toString(phoneDataArray) + "\n" + "\n";
        phoneReader.close();

        // Building Schedule CSV string.
        StringBuilder scheduleData = new StringBuilder();
        BufferedReader scheduleReader = new BufferedReader(new FileReader(String.valueOf(scheduleFile)));
        String currScheduleLine;
        while ((currScheduleLine = scheduleReader.readLine()) != null) {
            scheduleData.append(currScheduleLine);
        }
        JSONObject jsonObjectScheduleData;
        jsonObjectScheduleData = new JSONObject(scheduleData.toString());
        JSONArray scheduleDataArray = jsonObjectScheduleData.getJSONArray("schedules");
        String scheduleCsv = "Schedule list:" + "\n" + CDL.toString(scheduleDataArray) + "\n" + "\n";
        scheduleReader.close();

        // Building Order CSV string.
        StringBuilder orderData = new StringBuilder();
        BufferedReader orderReader = new BufferedReader(new FileReader(String.valueOf(orderFile)));
        String currOrderLine;
        while ((currOrderLine = orderReader.readLine()) != null) {
            orderData.append(currOrderLine);
        }
        JSONObject jsonObjectOrderData;
        jsonObjectOrderData = new JSONObject(orderData.toString());
        JSONArray orderDataArray = jsonObjectOrderData.getJSONArray("orders");
        String orderCsv = "Current orders:" + "\n" + CDL.toString(orderDataArray) + "\n" + "\n";
        orderReader.close();

        // Get CSVDataString
        data.append(customerCsv);
        data.append(phoneCsv);
        data.append(scheduleCsv);
        data.append(orderCsv);

        return data.toString();
    }

}
