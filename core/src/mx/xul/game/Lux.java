package mx.xul.game;
// ESTA ES UNA PRUEBA DE HACER PUSH :)
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import mx.xul.game.pantallaBienvenida.PantallaBienvenida;

public class Lux extends Game {
	// Administra la carga de los assets del juego

	//Musica
	private Music musicaPantallasSecundarias;
	private Music musicaPantallasSecundariasIntro;
	private final float VOLUMEN_DESEADO = 0.7f;
	private float volumenMusica=VOLUMEN_DESEADO;

	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create() {
		crearMusica();
		// Hace que muestre la primer pantalla
		setScreen(new PantallaBienvenida(this));
		//setScreen(new PantallaMenu(this)); // Primera pantalla visible
	}

	private void crearMusica() {

		musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundarias.ogg"));
		musicaPantallasSecundarias.setLooping(true);
		musicaPantallasSecundarias.setVolume(VOLUMEN_DESEADO);

		musicaPantallasSecundariasIntro = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundariasIntro.ogg"));
		musicaPantallasSecundariasIntro.setVolume(VOLUMEN_DESEADO);
	}

	protected void playMusica(){
		if(musicaPantallasSecundariasIntro.isPlaying()==false && musicaPantallasSecundarias.isPlaying()==false){
			musicaPantallasSecundariasIntro.play();
			//volumenMusica = VOLUMEN_DESEADO;
		}
		musicaPantallasSecundariasIntro.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				musicaPantallasSecundarias.play();
			}
		});

	}
	protected void fadeOutMusica(){
		if (musicaPantallasSecundarias.isPlaying()){
			volumenMusica -= 0.04f;
			musicaPantallasSecundarias.setVolume(volumenMusica);
		}

		if (musicaPantallasSecundariasIntro.isPlaying()){
			volumenMusica -= 0.04f;
			musicaPantallasSecundariasIntro.setVolume(volumenMusica);
		}

	}
	protected void stopMusica(){

		musicaPantallasSecundarias.stop();
		musicaPantallasSecundariasIntro.stop();
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
