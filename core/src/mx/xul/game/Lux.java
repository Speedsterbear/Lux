package mx.xul.game;
// ESTA ES UNA PRUEBA DE HACER PUSH :)
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import mx.xul.game.pantallaBienvenida.PantallaBienvenida;

public class Lux extends Game {
	@Override
	public void create () {
		// Hace que muestre la primer pantalla
		setScreen(new PantallaBienvenida(this));
		//setScreen(new PantallaMenu(this)); // Primera pantalla visible
	}
}
