package mx.xul.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HijoOscuridad extends ObjetoAnimado {

    public HijoOscuridad(Texture textura, float x, float y, int column, int row,float duracion, int tipo) {
        super(textura, x, y, column, row,duracion,tipo);
    }

    //Metodo para mover el Sprite
    // Recibe velocidad de referencia para utilizar velocidad relativa.
    public void mover(float velocidadRef, float velocidadObj, float delta) {

        //Al restar las velocidades se puede simular el movimiento de la oscuridad
        float dx = (velocidadObj - velocidadRef) * delta;
        sprite.setX(sprite.getX() + dx);

    }

    public float getX() {
        return sprite.getX();
    }
}
