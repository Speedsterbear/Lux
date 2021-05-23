package mx.xul.game;

// Autor: Eduardo Alejandro García Grac
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAyudaPrimarios extends Pantalla {
    private Lux juego;
    private Texture texturaFondo;
    private Stage escenaMenu;
    private Shiblu shiblu;
    private Texture texturaShiblu;
    private Esgrun esgrun;
    private Texture textureEsgrun;
    private Rojel rojel;
    private Texture textureRojel;
    private float time = 0;

    public PantallaAyudaPrimarios(Lux juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Ayuda/Slide4.jpeg");
        texturaShiblu = new Texture("Personajes/Shiblu.png");
        shiblu = new Shiblu(texturaShiblu, ANCHO*3/4f, ALTO/2);
        textureEsgrun = new Texture("Personajes/Esgrun.png");
        esgrun = new Esgrun(textureEsgrun, ANCHO/2, ALTO/2);
        textureRojel = new Texture("Personajes/Rojel.png");
        rojel = new Rojel(textureRojel, ALTO/12, ANCHO/2);
        crearAyudaPrimarios();

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearAyudaPrimarios() {
        texturaFondo = new Texture("Ayuda/Slide4.jpeg");
        escenaMenu = new Stage(vista);

        Button btnBack = crearBoton("Menu/buttonback.png","Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                juego.setScreen(new PantallaCargando(juego, Pantallasenum.PANTALLAAYUDA));
            }
        });
        escenaMenu.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoClick) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(texturaBoton);
        Texture texturaClick = new Texture(archivoClick);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClick);
        return new Button(trdBtnBack,trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 1);
        time += delta;
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        // shiblu.
        batch.end();
        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
