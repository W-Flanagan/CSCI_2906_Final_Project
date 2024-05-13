import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane pane = new GridPane();
        VBox gameEnd = new VBox();
        TextField gameOver = new TextField("Game Over!");
        gameEnd.getChildren().add(gameOver);
        Chess thisGame = new Chess(pane, "Standard");
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(pane, 800, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
