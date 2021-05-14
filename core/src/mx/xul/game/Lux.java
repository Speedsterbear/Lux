package mx.xul.game;
// ESTA ES UNA PRUEBA DE HACER PUSH :)
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import mx.xul.game.pantallaBienvenida.PantallaBienvenida;

public class Lux extends Game {
	// Administra la carga de los assets del juego
	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create() {
		// Hace que muestre la primer pantalla
		setScreen(new PantallaBienvenida(this));
		//setScreen(new PantallaMenu(this)); // Primera pantalla visible
	}

	// Método accesor de assetManager
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void dispose() {
		super.dispose();
		assetManager.clear();
	}
}
