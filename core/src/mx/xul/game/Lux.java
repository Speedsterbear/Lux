package mx.xul.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Lux extends Game {
	@Override
	public void create () {
		// Hace que muestre la primer pantalla
		setScreen(new PantallaMenu(this)); // Primera pantalla visible
	}
}
