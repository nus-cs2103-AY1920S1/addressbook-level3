package seedu.address.model.quiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Simulates a class which handles the exporting of quizzes.
 */
public class QuizExporter {

    /**
     * Exports a quiz to a html file.
     * @param quizId The identifier of the quiz.
     * @param formattedQuiz The formatted content of the quiz.
     * @return True if the file does not exist, false if the file already exists.
     * @throws IOException The exception to be thrown.
     */
    public static boolean exportQuiz(String quizId, String formattedQuiz) throws IOException {
        String fileName = quizId + ".html";
        String filePath = fileName;

        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(formattedQuiz.getBytes());
        fos.flush();
        fos.close();

        return true;

        /* File file = new File(filePath);
        if(file.exists()) {
            return false;
        } else {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(formattedQuiz);
            fileWriter.close();
            return true;
        } */
    }

}
