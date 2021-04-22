package mx.xul.game;

/*
Esta clase representa la sección verde (Green Section) del juego, por eso se llama GS
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class JuegoGS extends Pantalla {

    private Lux juego;

    //Fondo de bosque en movimiento
    private Texture bosquefondo;
    private Texture bosqueatras;
    private Texture bosquemedio;
    private Texture bosquefrente;
    private float velocidadBosque = 1000;
    private FondoEnMovimiento bosque;

    // Texto
    private Texto texto; // Muestra los valores en la pantalla

    //Personaje principal Lumil
    private Lumil lumil;
    private Texture texturaLumil;
    float tiempoLumil =0f; //Acumulador
    private static float DELTA_Y = 10; //Avance vertical de lumil
    private Vector3 posicionDedo;
    private boolean isMooving = false; //indica si el perosnaje principal debe moverse hacia arriba o abajo

    //Enemigo principal: La Oscuridad
    private Oscuridad oscuridad;
    private Texture texturaOscuridad;
    float tiempoOsc =0f; //Acumulador
    private float velocidadOsc = velocidadBosque/2f;

    //General
    private float velocidad = velocidadBosque;

    public JuegoGS (Lux juego) {
        this.juego=juego;
    }


    @Override
    public void show() {
        posicionDedo = new Vector3(0, 0, 0); //Posición del dedo
        crearFondo();
        crearPersonajes();

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());

    }

    private void crearPersonajes() {
        //Personaje principal: Lumil
        texturaLumil = new Texture ("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(texturaLumil,ANCHO/2, ALTO/2,4,1,1/10f,1);

        //Enemigo principal: Oscuridad
        texturaOscuridad = new Texture("Personajes/Oscuridad_Sprites.png");
        oscuridad = new Oscuridad(texturaOscuridad,-300,ALTO/2,4,1,1/8f,2);

        texto=new Texto();
    }


    private void crearFondo() {
        bosquefondo = new Texture("Escenarios/bosque_fondo.jpg");
        bosqueatras = new Texture("Escenarios/bosque_atras.png");
        bosquemedio = new Texture("Escenarios/bosque_medio.png");
        bosquefrente = new Texture("Escenarios/bosque_frente.png");
        bosque = new FondoEnMovimiento(bosquefondo,bosqueatras,bosquemedio,bosquefrente);
    }

    @Override
    public void render(float delta) {
        tiempoLumil +=Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.
        tiempoOsc +=Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.

        borrarPantalla(0,1,0);
        batch.setProjectionMatrix(camara.combined);

        actuaizar(delta);

        batch.begin();
        //Se dibujan los elementos
        bosque.movSeccionesCompletas(velocidad,delta,batch,true);
        lumil.animationRender(batch,tiempoLumil);
        oscuridad.animationRender(batch,tiempoOsc);
        // Dibuja el marcador
        texto.mostrarMensaje(batch,"SCORE",ANCHO/2,ALTO-25);

        batch.end();

    }

    private void actuaizar(float delta) {

        //Esta programado para que la oscuridad avance poco a poco, pero según yo esto no va a pasar en el juego de verdad,
        //al menos al inicio, solo que lo puse así para apreciar el funcionamiento.
        //Cuando la velocidad sea = 0, la oscuridad avanzará rápido por nuestro personaje.
        oscuridad.mover(velocidad*.5f,velocidad*.5f + 10,delta);
        //Mover nave?

        if (isMooving){
            lumil.mover(DELTA_Y,posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //Procesador de pantalla

    private class ProcesadorEntrada implements InputProcessor
    {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        // se ejecuta justo cuando toca la pantall (a penas pone el dedo)
        //Guarda las coordenadas del dedo y habilita el movimiento hacia rriba y abajo del personaje principal
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            posicionDedo.x=screenX;
            posicionDedo.y=screenY;
            camara.unproject(posicionDedo);
            isMooving = true;

            return true; //Porque el juego ya procesó el evento (si si hacemos algo hay que regresar TRUE)
        }

        // se ejecuta justo cuando el usuario quita el dedo de la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            isMooving = false;

            return true;

        }

        //Cuando arrastro el dedo por la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }

}
