package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
// Implementa al personaje verde
// Autor: Carlos Arroyo

public class Esgrun extends Objeto {

    public Esgrun(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void moverHorizontal(float dx){
        sprite.setX(sprite.getX()-dx);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }
}
