package mx.xul.game;

// Autor: Eduardo Alejandro García García A01338772
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAyuda extends Pantalla {
    private Lux juego;
    private Texture texturafondo;
    // private Sprite help;
    private Stage escenaMenu;

    public PantallaAyuda(Lux juego) {

        this.juego=juego;
    }

    /*
    private void crearAyuda(){
        texturafondo= new Texture("Menu/Fondo_Montana.jpg");
        escenaMenu=new Stage(vista);
        help= new Sprite(new Texture("Menu/clickayuda.png"));
        help.setPosition(ANCHO/2-help.getWidth(),ALTO/2-help.getHeight());
        help.setSize(794,454);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaMenu.addActor(btnRegresar);
        Gdx.input.setInputProcessor(escenaMenu);
    }
     */


    private Button crearBoton(String archivo, String archivoclick) {
        // Texture texturaBoton = new Texture("Personajes/Lumil.png");
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        texturafondo = new Texture("Menu/fondo.jpg");
        crearAyuda();
    }

    private void crearAyuda(){
        texturafondo= new Texture("Menu/Fondo_Montana.jpg");
        escenaMenu = new Stage(vista);

        Button btnLumil = crearBoton("Botones/LumilB.png");
        btnLumil.setPosition(ANCHO/2, ALTO/1.5f, Align.center);
        escenaMenu.addActor(btnLumil);
        btnLumil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaLumil(juego));
            }
        });


        Button btnOscuridad = crearBoton("Botones/OscuridaB.png");
        btnOscuridad.setPosition(ANCHO/2, ALTO/1.83f, Align.center);
        escenaMenu.addActor(btnOscuridad);
        btnOscuridad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaOscuridad(juego));
            }
        });

        Button btnHabitantes = crearBoton("Botones/btnhijos.png");
        btnHabitantes.setPosition(ANCHO/2, ALTO/2.6f, Align.bottom);
        escenaMenu.addActor(btnHabitantes);
        btnHabitantes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaHabitantes(juego));
            }
        });

        Button btnPrimarios = crearBoton("Botones/PrimariosB.png");
        btnPrimarios.setPosition(ANCHO/2, ALTO/4, Align.bottom);
        escenaMenu.addActor(btnPrimarios);
        btnPrimarios.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaPrimarios(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenu);
        // help= new Sprite(new Texture("Menu/clickayuda.png"));
        // help.setPosition(ANCHO/2-help.getWidth(),ALTO/2-help.getHeight());
        // help.setSize(794,454);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaMenu.addActor(btnRegresar);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnLumil = new TextureRegionDrawable(texturaBoton);
        return new Button(trdBtnLumil);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        // help.draw(batch);
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

