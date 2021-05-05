package mx.xul.game;

/*
Esta clase sirve para representar el brillo del personaje principal.
Es únicamente estético.
Autor: Carlos Arroyo.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class BrilloLumil {

    protected Sprite brilloAtras; // Imagen, posición
    protected Sprite brilloFrente; // Imagen, posición

    private float angulo = 0;

    public BrilloLumil(Texture textura, float x, float y) {
        //super(textura, x, y);
        brilloAtras = new Sprite(textura);
        brilloAtras.setPosition(x-textura.getWidth()/2,y-textura.getHeight()/2);

        brilloFrente = new Sprite(textura);
        brilloFrente.setPosition(x-textura.getWidth()/2,y-textura.getHeight()/2);
    }

    //Sirve para darle movimiento vertical al brillo.
    public  void mover (float y){
        brilloFrente.setY(y-(brilloFrente.getHeight()/2));
        brilloAtras.setY(y-(brilloAtras.getHeight()/2));

    }


    //Sirve para hacer la ilusión de brillo
    public void actualizar (float r, float g, float b){
        angulo ++;
        brilloAtras.rotate(0.3f);
        brilloAtras.setScale(0.9f + (MathUtils.sinDeg(angulo)*0.1f));
        brilloAtras.setColor(r,g,b,0.5f + (MathUtils.sinDeg(angulo)*0.3f));

        brilloFrente.rotate(-0.3f);
        brilloFrente.setScale(0.65f + (MathUtils.sinDeg(angulo)*0.1f));
        brilloAtras.setAlpha(0.6f + (MathUtils.sinDeg(angulo)*0.1f));

    }

    //Dibuja al brillo Lumil.
    public void render(SpriteBatch batch) {
        brilloAtras.draw(batch);
        brilloFrente.draw(batch);
    }
}
