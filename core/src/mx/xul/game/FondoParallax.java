package mx.xul.game;
/*
Esta clase representa el fondo del juego. Por medio de ésta se puede crear el efecto "Parallax"
Para esta clase las texturas se nombran como capas y se dibujan en orden ascendente.
Capa 0
Capa Estática
Capa 1
Capa 2
Capa 3
Capa 4
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoParallax {

    //Se declaran las texturas que se necesitarán para el efecto Parallax
    protected Texture capaCero;
    protected Texture capaEstatica;
    protected Texture capaUno;
    protected Texture capaDos;
    protected Texture capaTresTipoUno;//El tipo 1 y el tipo 2 se van a suceder.
    protected Texture capaTresTipoDos;//El tipo 1 y el tipo 2 se van a suceder.
    protected Texture capaCuatroTipoUno;//El tipo 1 y el tipo 2 se van a suceder.
    protected Texture capaCuatroTipoDos;//El tipo 1 y el tipo 2 se van a suceder.
    protected Texture capaTresSimple;
    protected Texture capaTresCompleta;
    protected Texture capaCuatroSimple;
    protected Texture capaCuatroCompleta;
    protected Texture capaFrente;
    protected Texture capaFrenteVerde;
    protected Texture capaFrenteaDibujar;

    protected float xCapaCero =0;
    protected float xCapaUno =0;
    protected float xCapaDos=0;
    protected float xCapaTres=0;
    protected float xCapaCuatro=0;
    protected float xCapaFrente=0;

    public FondoParallax (Texture texCapaCero,Texture texCapaEstatica,Texture texCapaUno,Texture texCapaDos,
                          Texture texCapaTresSimple,Texture texCapaTresCompleta,Texture texCapaCuatroSimple,
                          Texture texCapaCuatroCompleta,Texture texCapaFrente, Texture texCapaFrenteVerde) {
        capaCero = texCapaCero;
        capaEstatica = texCapaEstatica;
        capaUno = texCapaUno;
        capaDos = texCapaDos;
        capaTresSimple = texCapaTresSimple;
        capaTresCompleta = texCapaTresCompleta;
        capaCuatroSimple = texCapaCuatroSimple;
        capaCuatroCompleta = texCapaCuatroCompleta;
        capaFrente = texCapaFrente;
        capaFrenteVerde = texCapaFrenteVerde;

        capaTresTipoUno = capaTresCompleta;
        capaTresTipoDos = capaTresCompleta;

        capaCuatroTipoUno = capaCuatroCompleta;
        capaCuatroTipoDos = capaCuatroCompleta;

        capaFrenteaDibujar = capaFrente;

    }

    public void actualizar (float velocidad, float delta, EstadoSeccion seccion){
        //Capa 0
        xCapaCero -= velocidad*.05f*delta;
        if (xCapaCero <= -capaCero.getWidth()) {
            xCapaCero = 0;
        }

        //Capa 1
        xCapaUno-= velocidad*.1f*delta;
        if (xCapaUno <= -capaUno.getWidth()) {
            xCapaUno = 0;
        }

        //Capa 2
        xCapaDos-= velocidad*.3f*delta;
        if (xCapaDos <= -capaDos.getWidth()) {
            xCapaDos = 0;
        }


        //Capa 3
        xCapaTres -= velocidad*.65f*delta;
        if (xCapaTres <= -capaTresTipoUno.getWidth()) {
            xCapaTres = 0;
        }

        //Capa 4
        xCapaCuatro -= velocidad*delta;
        if (xCapaCuatro <= -capaCuatroTipoUno.getWidth()) {
            xCapaCuatro = 0;
        }

        //Capa Frente
        xCapaFrente -= velocidad*delta;
        if (xCapaFrente <= -capaFrenteaDibujar.getWidth()) {
            xCapaFrente = 0;
            capaFrente= capaFrenteaDibujar;
        }

        if(seccion!=EstadoSeccion.VERDE){
            capaFrenteaDibujar = capaFrenteVerde;

        }


        /*
        switch (seccion){
            case VERDE:

                //Capa 3
                xCapaTres -= velocidad*.65f*delta;
                if (xCapaTres <= -capaTresTipoUno.getWidth()) {
                    xCapaTres = 0;
                    capaTresTipoUno = capaTresTipoDos;
                    if (capaTresTipoDos == capaTresSimple){
                        capaTresTipoDos = capaTresCompleta;
                    } else {capaTresTipoDos = capaTresCompleta;}
                }

                //Capa 4
                xCapaCuatro-= velocidad*delta;
                if (xCapaCuatro <= -capaCuatroTipoUno.getWidth()) {
                    xCapaCuatro = 0;
                }

                break;
            case ROJO:

                capaTresTipoDos = capaTresCompleta;

                //Capa 3
                xCapaTres -= velocidad*.65f*delta;
                if (xCapaTres <= -capaTresTipoUno.getWidth()) {
                    xCapaTres = 0;
                    capaTresTipoUno = capaTresTipoDos;
                }

                //Capa 4
                xCapaCuatro -= velocidad*delta;
                if (xCapaCuatro <= -capaCuatroTipoUno.getWidth()) {
                    xCapaCuatro = 0;
                    capaCuatroTipoUno = capaCuatroTipoDos;
                    if (capaCuatroTipoDos == capaCuatroSimple){
                        capaCuatroTipoDos = capaCuatroCompleta;
                    } else {capaCuatroTipoDos = capaCuatroCompleta;}
                }
                break;
            case AZUL:

                capaCuatroTipoDos = capaCuatroCompleta;

                //Capa 3
                xCapaTres -= velocidad*.65f*delta;
                if (xCapaTres <= -capaTresTipoUno.getWidth()) {
                    xCapaTres = 0;
                }

                //Capa 4
                xCapaCuatro -= velocidad*delta;
                if (xCapaCuatro <= -capaCuatroTipoUno.getWidth()) {
                    xCapaCuatro = 0;
                    capaCuatroTipoUno = capaCuatroTipoDos;
                }

                break;
            case BLANCO:

                //Capa 3
                xCapaTres -= velocidad*.65f*delta;
                if (xCapaTres <= -capaTresTipoUno.getWidth()) {
                    xCapaTres = 0;
                }

                //Capa 4
                xCapaCuatro -= velocidad*delta;
                if (xCapaCuatro <= -capaCuatroTipoUno.getWidth()) {
                    xCapaCuatro = 0;
                }
                break;
        }

         */

    }

    public void render (SpriteBatch batch, EstadoSeccion seccion){

        // Se dibujan las texturas

        //Capa 0
        batch.draw(capaCero,xCapaCero,0);
        batch.draw(capaCero, xCapaCero+ capaCero.getWidth(),0);

        //Capa Estática
        batch.draw(capaEstatica,0,0);

        //Capa 1
        batch.draw(capaUno,xCapaUno,0);
        batch.draw(capaUno, xCapaUno+ capaUno.getWidth(),0);

        //Capa 2
        batch.draw(capaDos,xCapaDos,0);
        batch.draw(capaDos, xCapaDos+ capaDos.getWidth(),0);

        //Capa 3
        batch.draw(capaTresTipoUno,xCapaTres,0);
        batch.draw(capaTresTipoDos, xCapaTres+ capaTresTipoUno.getWidth(),0);

        //Capa 4
        batch.draw(capaCuatroTipoUno,xCapaCuatro,0);
        batch.draw(capaCuatroTipoDos, xCapaCuatro+ capaCuatroTipoUno.getWidth(),0);
    }

    public void renderFrente (SpriteBatch batch){
        //Capa Frente
        batch.draw(capaFrente,xCapaFrente,0);
        batch.draw(capaFrenteaDibujar, xCapaFrente+ capaFrente.getWidth(),0);
    }



}
