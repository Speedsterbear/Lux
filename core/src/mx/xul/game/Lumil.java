package mx.xul.game;
/*
Representa a Lúmil, el personaje principal
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;

public class Lumil extends ObjetoAnimado
{
    private EstadoLumil estado; // Arriba - Abajo - Explota
    private Texture texturaJugando;
    private Texture texturePierde;

    public Lumil(Texture textura, float x, float y, int column, int row,float duracion, int tipo){
        super(textura, x, y, column, row,duracion,tipo);
    }

    public Lumil(Texture textura, Texture textura2, float x, float y, int column, int row,float duracion, int tipo){
        super(textura, x, y, column, row,duracion,tipo);
        this.texturaJugando = textura;
        this.texturePierde = textura2;
        estado=EstadoLumil.JUGANDO;
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
    
    public EstadoLumil getEstado() {
        return estado;
    }

    public void setEstado(EstadoLumil nuevoestado){
        this.estado=nuevoestado;
        if(nuevoestado==EstadoLumil.PIERDE){
            sprite.setTexture(texturePierde);
        }
    }


}