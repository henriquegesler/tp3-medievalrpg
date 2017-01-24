package medievalRPG.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import medievalRPG.MedievalRPG;
import medievalRPG.Config;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Config.WORLD_WIDTH;
		config.height = Config.WORLD_HEIGHT;
		config.fullscreen = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new MedievalRPG(), config);
	}
}