package dream.fcard.gui.components;

import java.io.IOException;

import dream.fcard.core.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Application controlling a Java editor.
 */
public class JavaEditorApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(Main.class.getResource("/view/Windows/JavaEditor.fxml"));
            AnchorPane ap = fxmlloader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("FlashCoder Java");
            TextArea textArea = (TextArea) ap.getChildren().get(2);
            textArea.setText(boilerPlate());
            ap.getChildren().get(2).requestFocus();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The boilerplate Java code for the user when the editor is first loaded.
     * @return basic class code
     */
    private String boilerPlate() {
        return "package dream.fcard.util.code.data;\n"
                + "\n"
                + "/**\n"
                + " * Do not change the class name!\n"
                + " */\n"
                + "public class Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Hello world\");\n"
                + "    }\n"
                + "}\n";
    }
}
