import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Client;
import view.App;


/**
 * The Main Class.
 * This class holds the start method
 * @author E.A NDAYIRAGIJE
 */
public class Main extends Application{
	
	/** The application. */
	public App application = new App(); //Main has an instance of the App class so it can access the necessary scenes.
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		launch();
	}
	
	/**
	 * Start.
	 *
	 * @param PrimaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage PrimaryStage) throws Exception {
		PrimaryStage.setScene(application.getCurrentScene());
		PrimaryStage.setTitle("SMTP Client Application");
		PrimaryStage.setResizable(false);
		PrimaryStage.show();
		
	}

}
