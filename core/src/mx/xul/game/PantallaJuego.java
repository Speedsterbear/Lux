package mx.xul.game;

import com.badlogic.gdx.graphics.Texture;

public class PantallaJuego extends Pantalla {
    private Texture texturafondo;

    public PantallaJuego(Lux juego) {
    }

    @Override
    public void show() {
        texturafondo= new Texture("Menu/fondo.jpg");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.end();

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


