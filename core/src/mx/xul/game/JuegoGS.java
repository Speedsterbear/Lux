package mx.xul.game;

/*
Esta clase representa la sección verde (Green Section) del juego, por eso se llama GS
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

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
    private Texture texturaLumilJugando;
    private Texture texturaLumilPierde;
    float tiempoLumil =0f; //Acumulador
    private static float DELTA_Y = 10; //Avance vertical de lumil
    private Vector3 posicionDedo;
    private boolean isMooving = false; //indica si el perosnaje principal debe moverse hacia arriba o abajo

    //Enemigo principal: La Oscuridad
    private Oscuridad oscuridad;
    private Texture texturaOscuridad;
    float tiempoOsc =0f; //Acumulador
    private float velocidadOsc = velocidadBosque/2f;
    private float positionXStart = -900;
    private float positionX = -300.0f;
    private float positionY = ALTO/2;
    private long startTimeOscuridad = 0;
    private EstadoOscuridad estadoOscuridad = EstadoOscuridad.QUIETO;

    //General
    private float velocidad = velocidadBosque;

    //Vidas
    private int vidas = 3;

    //Estado Juego
    private EstadoJuego estado = EstadoJuego.JUGANDO;

    //Gemas
    private Array<Gema> arrGemas; // Guarda todos los aliens
    private float DX_PASO_GEMA=10;
    private long startTime = 0;

    //Hijo de Oscuridad
    private  HijoOscuridad hijoOscuridad;
    private Texture texturaHijoOscuridad;
    private float DX_PASO_HIJOOSCURIDAD=150;


    public JuegoGS (Lux juego) {
        this.juego=juego;
    }


    @Override
    public void show() {
        posicionDedo = new Vector3(0, 0, 0); //Posición del dedo
        crearFondo();
        crearPersonajes();
        crearTexturaHijoOscuridad();
        startTime = TimeUtils.nanoTime();
        startTimeOscuridad = TimeUtils.nanoTime();
        crearGemas();

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());

    }

    private void crearPersonajes() {
        //Personaje principal: Lumil
        texturaLumilJugando = new Texture ("Personajes/Lumil_Sprites.png");
        texturaLumilPierde = new Texture ("Personajes/Oscuridad_Sprites.png");
        lumil = new Lumil(texturaLumilJugando,texturaLumilPierde,ANCHO/2, ALTO/2,4,1,1/10f,1);

        //Enemigo principal: Oscuridad
        texturaOscuridad = new Texture("Personajes/Oscuridad_Sprites.png");
        oscuridad = new Oscuridad(texturaOscuridad,positionX,positionY,4,1,1/8f,2);

        texto=new Texto();
    }

    private void crearTexturaHijoOscuridad(){
        texturaHijoOscuridad = new Texture("Personajes/HijoOsc_sprite.png");
    }

    private void crearGemas(){
        Texture texturaGemaAzul=new Texture("Personajes/gemaazul.png");
        // Texture texturaGemaRoja=new Texture("Space/enemigoAbajo.png");
        // Texture texturaGemaVerde=new Texture("Space/enemigoExplota.png");
        arrGemas=new Array<>();
        Gema gema= new Gema(texturaGemaAzul, ANCHO,positionY);
        arrGemas.add(gema);  // Lo guarda en el arreglo
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


        actuaizar(delta);

        if(vidas==0){
            estado = EstadoJuego.PIERDE;
        }

        tiempoLumil +=Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.
        tiempoOsc +=Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.

        borrarPantalla(0,1,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //Se dibujan los elementos
        bosque.movSeccionesCompletas(velocidad,delta,batch,true);
        lumil.animationRender(batch,tiempoLumil);

        if(hijoOscuridad!=null){
            hijoOscuridad.animationRender(batch,tiempoOsc);
        }


        oscuridad.animationRender(batch,tiempoOsc);
        // Dibuja el marcador
        texto.mostrarMensaje(batch,"SCORE",ANCHO/2,ALTO-25);

        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            // if time passed since the time you set startTime at is more than 1 second

            //your code here
            crearGemas();
            //also you can set the new startTime
            //so this block will execute every one second
            startTime = TimeUtils.nanoTime();
        }

        for (Gema gema:arrGemas) { // Automaticamente visita cada objeto del arreglo
            gema.render(batch);
        }



        batch.end();

    }

    private void moverOscuridad(float delta, EstadoOscuridad estado){
        Gdx.app.log("Distance", Float.toString(oscuridad.sprite.getX()));

        if(estado == EstadoOscuridad.ADELANTE){
            oscuridad.mover(velocidad*0.5f,velocidad*.5f + 100, delta);
        }else if(estado == EstadoOscuridad.ATRAS){
                oscuridad.mover(velocidad*0.5f,velocidad*.5f + 100, -delta);
        }else if(estado == EstadoOscuridad.QUIETO){
            oscuridad.mover(0,0, delta);
        }
    }

    private void actuaizar(float delta) {

        //Esta programado para que la oscuridad avance poco a poco, pero según yo esto no va a pasar en el juego de verdad,
        //al menos al inicio, solo que lo puse así para apreciar el funcionamiento.
        //Cuando la velocidad sea = 0, la oscuridad avanzará rápido por nuestro personaje.
        if(estado == EstadoJuego.JUGANDO){

            oscuridadColision();
            if(hijoOscuridad!=null){
                hijoOscuridadColision();
            }


            actuaizarHijoOscuridad(delta);

            //Gdx.app.log("TIME", Long.toString(TimeUtils.nanoTime()));

            moverOscuridad(delta, estadoOscuridad);

            if (TimeUtils.timeSinceNanos(startTimeOscuridad) > 2000000000 && estadoOscuridad == EstadoOscuridad.QUIETO) {
                estadoOscuridad = EstadoOscuridad.ADELANTE;

                //hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, oscuridad.sprite.getX() + oscuridad.sprite.getWidth() / 2 - texturaHijoOscuridad.getWidth() / 2, oscuridad.sprite.getY() + oscuridad.sprite.getHeight());

                hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, 0,ALTO/2,3,1,1/8f,2);
            }

            if(oscuridad.sprite.getX() > -500){
                estadoOscuridad = EstadoOscuridad.ATRAS;
            }

            if (oscuridad.sprite.getX() < -780 && estadoOscuridad == EstadoOscuridad.ATRAS) {
                Gdx.app.log("Distance", "QUIETO");
                startTimeOscuridad = TimeUtils.nanoTime();
                estadoOscuridad = EstadoOscuridad.QUIETO;
            }

            if (TimeUtils.timeSinceNanos(startTimeOscuridad) > 1000000000 && estadoOscuridad == EstadoOscuridad.QUIETO) {
                hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, 0,ALTO/2,3,1,1/8f,2);
            }


            moverGemas();

            if (isMooving){
                lumil.mover(DELTA_Y,posicionDedo.y);
            } else {lumil.sprite.setRotation(0);}
        }



    }

    private void hijoOscuridadColision() {
        if(estado == EstadoJuego.JUGANDO && lumil.sprite.getBoundingRectangle().overlaps(hijoOscuridad.sprite.getBoundingRectangle())){
            //lumil.setEstado(EstadoLumil.PIERDE);
            vidas--;
            //Gdx.app.log("hit", "hit");
            hijoOscuridad = null;
            Gdx.app.log("Vidas", Integer.toString(vidas));
        }
    }

    private void actuaizarHijoOscuridad(float delta) {
        if(hijoOscuridad!=null){
            hijoOscuridad.mover(velocidad*0.5f,velocidad*.5f + 500, delta);
            if(hijoOscuridad.sprite.getX() > ANCHO){
                hijoOscuridad=null;
            }
        }
    }

    private void depurarGemas() {
        for(int i=arrGemas.size-1;i>=0;i--){
            Gema gema= arrGemas.get(i);
            if(lumil.sprite.getBoundingRectangle().overlaps(gema.sprite.getBoundingRectangle())){
                Gdx.app.log("Hit", "Hit Gema");
                arrGemas.removeIndex(i);
            }
        }
    }

    private void oscuridadColision() {
        if(estado == EstadoJuego.JUGANDO && lumil.sprite.getBoundingRectangle().overlaps(oscuridad.sprite.getBoundingRectangle())){
            //lumil.setEstado(EstadoLumil.PIERDE);
            vidas--;
            //Gdx.app.log("hit", "hit");
            Gdx.app.log("Vidas", Integer.toString(vidas));
        }
    }

    private void moverGemas(){
        for(Gema gema: arrGemas){
            gema.moverHorizontal(DX_PASO_GEMA);
        }
        depurarGemas();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaLumilJugando.dispose();
        texturaLumilPierde.dispose();
        texturaOscuridad.dispose();
        texturaHijoOscuridad.dispose();
        arrGemas.clear();
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

            if(estado==EstadoJuego.PIERDE){
                vidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
            }else {
                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);
                isMooving = true;
            }

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
