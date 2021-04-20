package mx.xul.game;


/*
Representa objetos en el juego (Personajes, enemigos, etc..)
Es particularmente para los no animados
Autor: Carlos Uriel Arroyo Herrera
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Objeto
{
    //protected es para quelas clases que heredan puedan acceder a la variable
    protected Sprite sprite; // Imagen, posición

    //Constructor. Inicializa el objeto con la imagen y la posición
    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }

    // Constructor por default
    public Objeto () {
    }

    // Dibujar el objeto -> Begin     end<-
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

}
