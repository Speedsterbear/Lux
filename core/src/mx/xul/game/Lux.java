package mx.xul.game;
// // Administra la carga de los assets del juego
// Autor: Ricardo Solis y Carlos Arroyo
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
		musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaMenuLoop.mp3"));
		musicaPantallasSecundarias.setLooping(true);
		musicaPantallasSecundarias.setVolume(VOLUMEN_DESEADO);

		musicaPantallasSecundariasIntro = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaMenuIntro.mp3"));
		musicaPantallasSecundariasIntro.setVolume(VOLUMEN_DESEADO);
	}

	public void playMusica(){
		if(musicaPantallasSecundariasIntro.isPlaying()==false && musicaPantallasSecundarias.isPlaying()==false){
			musicaPantallasSecundariasIntro.play();
			//volumenMusica = VOLUMEN_DESEADO;
		}
		musicaPantallasSecundariasIntro.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				musicaPantallasSecundariasIntro.stop();
				musicaPantallasSecundarias.play();
			}
		});

	}
	public void fadeOutMusica(){
		if (musicaPantallasSecundarias.isPlaying()){
			volumenMusica -= 0.04f;
			musicaPantallasSecundarias.setVolume(volumenMusica);
		}

		if (musicaPantallasSecundariasIntro.isPlaying()){
			volumenMusica -= 0.04f;
			musicaPantallasSecundariasIntro.setVolume(volumenMusica);
		}

	}
	public void stopMusica(){

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
