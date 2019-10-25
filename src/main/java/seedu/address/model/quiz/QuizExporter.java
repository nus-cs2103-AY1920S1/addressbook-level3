package seedu.address.model.quiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simulates a class which handles the exporting of quizzes.
 */
public class QuizExporter {

    public static boolean exportQuiz(String quizId, String formattedQuiz) throws IOException {
        String fileName = quizId + ".html";
        String filePath = fileName;

        File file = new File(filePath);
        if(file.exists()) {
            return false;
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(formattedQuiz.getBytes());
        fos.flush();
        fos.close();

        return true;

//        File file = new File(filePath);
//        if(file.exists()) {
//            return false;
//        } else {
//            file.createNewFile();
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(formattedQuiz);
//            fileWriter.close();
//            return true;
//        }
    }

}
