package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;


// Clase que implementa la pantalla de la historia parte 2
// Autor: Andrea Espinosa Azuela

public class PantallaHistoriados extends Pantalla{
    private Lux juego;
    private Texture texturafondo;
    private Stage escenaHistoriados;

    public PantallaHistoriados(Lux juego) {
        this.juego=juego;
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        texturafondo = new Texture("Escenarios/Historiados.png");
        crearGana();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearGana() {
        texturafondo= new Texture("Escenarios/Historiados.png");
        escenaHistoriados = new Stage(vista);

        Button btnIniciar = crearBoton("Nosotros/flechaDerOff.png","Nosotros/flechaDerOn.png");
        btnIniciar.setPosition(ANCHO/2+400, ALTO/5.5f, Align.center);
        escenaHistoriados.addActor(btnIniciar);
        btnIniciar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new JuegoGS(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaHistoriados);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaAyuda(juego));
        }

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.end();

        escenaHistoriados.draw();
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


