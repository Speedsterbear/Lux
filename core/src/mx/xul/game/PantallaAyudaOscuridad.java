package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAyudaOscuridad extends Pantalla {
    private Lux juego;
    private Texture texturaFonfo;
    private Stage escenaMenu;

    public PantallaAyudaOscuridad(Lux juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFonfo = new Texture("Ayuda/oscuridadAyuda.jpeg");
        crearAyudaOscuridad();
    }

    private void crearAyudaOscuridad() {
        texturaFonfo = new Texture("Ayuda/oscuridadAyuda.jpeg");
        escenaMenu = new Stage(vista);

        Button btnBack = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyuda(juego));
            }
        });

        escenaMenu.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoClick) {
        Texture textureBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(textureBoton);
        Texture texturaClick = new Texture(archivoClick);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClick);
        return new Button(trdBtnBack, trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFonfo, 0, 0);
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
