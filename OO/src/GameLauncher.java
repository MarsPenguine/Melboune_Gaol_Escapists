import controller.mainController.GraphicControl;
import controller.observer.StateObserver;
import controller.mainController.Prompt;
import controller.mainController.KeyLayoutController;
import view.SetupJFame;

public class GameLauncher {
	static SetupJFame gameFrame;
	static GraphicControl dc;
	static StateObserver kc;

	public static void main(String[] args) throws InterruptedException {
		new Prompt();
		kc= StateObserver.getInstance();
		KeyLayoutController eContext= KeyLayoutController.getInstance();
		gameFrame = SetupJFame.getInstance();
		gameFrame.setLocationRelativeTo(null);
		dc = GraphicControl.getInstance();
		dc.addObserver(gameFrame);
		dc.refresh();
	}
}