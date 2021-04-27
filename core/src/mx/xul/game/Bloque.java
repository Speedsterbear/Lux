package mx.xul.game;
/*
Esta Clase representa cada uno de los obstáculos que el personaje principal debe esquivar.
Autor:  Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;

public class Bloque extends ObjetoAnimado{

    public Bloque(Texture textura, float x, float y, int column, int row, float duracion, int tipo) {
        super(textura, x, y, column, row, duracion, tipo);
    }

    //Este método sirve para mover los bloques.
    // Recibe de parámetro la velocidad y el tiempo para calcular su desplazamiento

    public void mover (float delta, float velocidad){
        float dx = velocidad*delta*.35f;
        sprite.setX(sprite.getX()-dx);
    }

    public float getX() {
        return sprite.getX();
    }
}
