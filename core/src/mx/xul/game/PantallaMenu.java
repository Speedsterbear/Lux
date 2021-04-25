package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaMenu extends Pantalla {
    private Lux juego;
    private Texture texturafondo;
    private Stage escenaMenu;

    public PantallaMenu(Lux juego) {
        this.juego=juego;
       // Gdx.app.log("Juego:", String.valueOf(juego));
    }

    @Override
    public void show() {

        crearMenu();
    }

    private void crearMenu() {
        //Fondo
        texturafondo= new Texture("Menu/fondo.jpg");

        // Escena
        escenaMenu=new Stage(vista);

        // Actores (botones)
        Button btnJuego = crearBoton("Menu/buttonjuego.png", "Menu/clickjuego.png");
        btnJuego.setPosition(2*ANCHO/3,1.5f*ALTO/2, Align.center);
        btnJuego.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaJuego(juego));
                juego.setScreen(new JuegoGS(juego));
            }
        });


        Button btnNosotros = crearBoton("Menu/buttonnosotros.png","Menu/clicknosotros.png");
        btnNosotros.setPosition(2*ANCHO/3,1.2f*ALTO/2.5f, Align.center);
        // Registrar el evento click para el boton
        btnNosotros.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Space Invaders
                juego.setScreen(new PantallaNosotros(juego));
            }
        });

        Button btnAyuda= crearBoton("Menu/buttonayuda.png", "Menu/clickayuda.png");
        btnAyuda.setPosition(2*ANCHO/3,0.8f*ALTO/4, Align.center);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Space Invaders
                juego.setScreen(new PantallaAyuda(juego));
            }
        });


        // Agregar a la escena el boton
        escenaMenu.addActor(btnJuego);
        escenaMenu.addActor(btnNosotros);
        escenaMenu.addActor(btnAyuda);

        // Escena se encarga de atender los eventos de entrada
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    // Se ejecuta automaticamente
    // delta es el tiempo que ha transcurrido el frame
    @Override
    public void render(float delta) {
        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.end();

        // Escena despues del fondo
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


