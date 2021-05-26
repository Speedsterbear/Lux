package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// Clase del color primario rojo
// Autor: Carlos Uriel Arroyo

public class Rojel extends Objeto {

    public Rojel(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void moverHorizontal(float dx){
        sprite.setX(sprite.getX()-dx);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }
}
