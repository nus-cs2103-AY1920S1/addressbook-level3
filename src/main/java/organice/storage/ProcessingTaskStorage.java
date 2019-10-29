package organice.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import organice.model.person.Donor;
import organice.model.person.Task;
import organice.model.person.TaskList;

/**
 * Storage class to store the tasks and load the files which contain the list of tasks
 * It also run a check to see if the donor have been processed before or not
 */
public class ProcessingTaskStorage {

    private final Donor donor;
    protected TaskList taskList;
    protected File taskListText;

    public ProcessingTaskStorage(TaskList taskList, Donor donor){
        this.taskList = taskList;
        this.donor = donor;
    }

    public void LoadFile() throws IOException {
        try {
            CheckFile();
            taskListText = new File(donor.getNric().toString() + ".txt");
            BufferedReader TasksFile = new BufferedReader(new FileReader(taskListText));
            String LineFile = "";
            while ((LineFile = TasksFile.readLine()) != null) {
                String[] WordsFile = LineFile.split("`");
                Task taskFile = new Task(WordsFile[2]);
                taskList.add(taskFile);
                if (WordsFile[1].equals("\u2713")) {
                    taskFile.markAsDone(taskFile);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void UpdateFile() throws IOException {
        try (PrintStream out = new PrintStream(new FileOutputStream(taskListText))) {
            for (int i = 0; i < taskList.size(); i++) {
                Task t = taskList.get(i);
                out.print(t.getType() + "`" + t.getStatusIcon() + "`" + t.getDescription() + "`" + "\n");
            }
        }
    }

    public void CheckFile() throws IOException {
        File tmpDir = new File(donor.getNric().toString() + ".txt");
        if (!tmpDir.exists()) {
            tmpDir.createNewFile();
        }
    }
}