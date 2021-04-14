package mx.xul.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mx.xul.game.Lux;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Definir el tamaño inicial de la ventana
		config.width= 1280/2;
		config.height = 720/2;
		new LwjglApplication(new Lux(), config);
	}
}
