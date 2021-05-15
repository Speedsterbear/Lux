package mx.xul.game;
/*
Representa a Lúmil, el personaje principal
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Lumil extends ObjetoAnimado
{
    private EstadoLumil estado; // Arriba - Abajo - Explota
    private Texture texturaJugando;

    public Lumil(Texture textura, float x, float y, int column, int row,float duracion, int tipo){
        super(textura, x, y, column, row,duracion,tipo);
    }

    //Metodo para mover el Sprite
    //El sprite incrementa o decrementa su posición en Y una cantidad dy hasta alcanzar la posición indicada
    public void mover(float dy,float ydeseada) {

        float deg = 15; //son los grados que va a girar el personaje al moverse
        float y  = ydeseada-sprite.getHeight()/2;

        if (y>sprite.getY()){
            sprite.setRotation(deg);
            if (y-sprite.getY()>=dy) {
                sprite.setY(sprite.getY()+dy);
            }else {sprite.setY(y);}
        }else {
            if (y<sprite.getY()){
                sprite.setRotation(-deg);
                if (sprite.getY()-y>=dy) {
                    sprite.setY(sprite.getY()-dy);
                }else {sprite.setY(y);}

            }else {sprite.setRotation(0);}
        }

    }

    //Regresa el rectángulo ajustado de Lumil, es decir, recorrido para no tomar en cuanta los bazos (no se si se va a usar mucho)
    public Rectangle getRectangle() {
        Rectangle rectangle = sprite.getBoundingRectangle();
        float margen = 50;
        rectangle.setX(rectangle.getX()+margen);
        rectangle.setWidth(rectangle.getWidth()-margen);
        return rectangle;
    }

    //Regresa un rectangulo ubicado en la parte superior del sprite
    public Rectangle getUpperRectangle() {
        float margenizq = 50;
        float margender = 10;
        float margensup = 0;
        float alto = 10;

        float xrect = sprite.getX()+margenizq;
        float yrect = sprite.getY()+sprite.getHeight()-margensup-alto;
        float anchorect = sprite.getWidth()-margenizq-margender;
        float altorect = alto;

        Rectangle rectBoton = new Rectangle(xrect,yrect,anchorect,altorect);
        return rectBoton;

    }

    //Regresa un rectangulo ubicado en la parte inferior del sprite
    public Rectangle getLowerRectangle() {
        float margenizq = 50;
        float margender = 10;
        float margeninf = 0;
        float alto = 10;

        float xrect = sprite.getX()+margenizq;
        float yrect = sprite.getY()+margeninf;
        float anchorect = sprite.getWidth()-margenizq-margender;
        float altorect = alto;

        Rectangle rectBoton = new Rectangle(xrect,yrect,anchorect,altorect);
        return rectBoton;

    }

    //Regresa un rectangulo ubicado en la parte derecha (frontal) del sprite
    public Rectangle getFrontRectangle() {
        float margeninf = 0;
        float margender = 10;
        float margensup = 0;
        float grosor = 10;

        float xrect = sprite.getX()+sprite.getWidth()-margender-grosor;
        float yrect = sprite.getY()+margensup;
        float anchorect = grosor;
        float altorect = sprite.getHeight()-margensup-margeninf;

        Rectangle rectBoton = new Rectangle(xrect,yrect,anchorect,altorect);
        return rectBoton;

    }


}