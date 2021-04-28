package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaGana extends Pantalla{

    private Lux juego;
    private Texture texturafondo;
    private Stage escenaGana;

    public PantallaGana(Lux juego) {
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
        texturafondo = new Texture("Escenarios/Gana.png");
        crearGana();
    }

    private void crearGana() {
        texturafondo= new Texture("Escenarios/Gana.png");
        escenaGana = new Stage(vista);

        Button btnExit = crearBoton("Botones/btnExit.png","Botones/btnExitClick.png");
        btnExit.setPosition(ANCHO/2, ALTO/3.5f, Align.center);
        escenaGana.addActor(btnExit);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaGana);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.end();

        escenaGana.draw();
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

