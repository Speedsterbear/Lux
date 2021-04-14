package mx.xul.game;


/*
Representa objetos en el juego (Personajes, enemigos, etc..)
puede usarse tanto para los animados como para los no animados
Autor: Carlos Uriel Arroyo Herrera
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Objeto
{
    //protected es para quelas clases que heredan puedan acceder a la variable
    //Usar como sprite
    protected Sprite sprite; // Imagen, posición

    //Para animar
    protected Animation animation;
    protected TextureRegion[] regionVector;
    protected TextureRegion [] [] regionMatriz;
    private TextureRegion frameActual;

    //Constructor. Inicializa el objeto con la imagen y la posición
    public Objeto(Texture textura, float x, float y, int column, int row) {
        sprite = new Sprite(textura);
        sprite.setPosition(x - (textura.getWidth()/column/2),y-(textura.getHeight()/row/2));
        regionMatriz = TextureRegion.split(textura,textura.getWidth()/column, textura.getHeight()/row);

        //Convertir la matiz en Vector para poder usarla
        regionVector = new TextureRegion[column*row];
        int k=0;
        for (int i =0; i<row; i++){
            for (int j=0; j<column; j++) {
                regionVector [k] = regionMatriz [i][j];
                k++;
            }
        }

    }

    // Constructor por default
    public Objeto () {
    }

    // Dibujar el objeto -> Begin     end<-
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }


    //Dibujar el objeto animándolo
    public void animationRender(SpriteBatch batch, float duracion, float tiempo,boolean looping){
        float Deg = sprite.getRotation();
        float X = sprite.getX();
        float Y = sprite.getY();
        animation = new Animation (duracion,regionVector);
        frameActual = (TextureRegion) animation.getKeyFrame(tiempo,looping);
        sprite = new Sprite(frameActual);
        sprite.setPosition(X,Y);
        sprite.setRotation(Deg);
        sprite.draw(batch);

    }

}
