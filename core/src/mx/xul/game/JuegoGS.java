package mx.xul.game;

/*
Esta clase representa la sección verde (Green Section) del juego, por eso se llama GS
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class JuegoGS extends Pantalla {

    private Lux juego;

    //Escena para Botones
    //private Stage escenaJuego;


    //Márgenes
    private float margen = 20;
    //Valor mínimo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMinExt = ANCHO + 200;
    //Valor máximo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMaxExt = ANCHO *1.5f;
    //Valor mínimo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMinMarg = 0;
    //Valor máximo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMaxMarg = ALTO;

    //Fondo de bosque en movimiento
    private Texture bosquefondo;
    private Texture bosqueatras;
    private Texture bosquemedio;
    private Texture bosquefrente;
    private float velocidadBosque = 300;
    private FondoEnMovimiento bosque;

    // Texto
    //Muestra los valores en la pantalla
    private Texto texto;

    //Personaje principal Lumil
    private Lumil lumil;
    private Texture texturaLumilJugando;
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
    private int contadorVidas = 3;
    private Texture texturaVida;
    private LuzVida luzVida;
    private Vidas vidas;
    

    //Estado Juego
    private EstadoJuego estado = EstadoJuego.JUGANDO;

    //Gemas
    private Array<Gema> arrGemas; // Guarda todos los aliens
    private float DX_PASO_GEMA=10;
    private long startTime = 0;
    private float timerCrearGema = 0;
    private float tiempoParaCrearGema =1;

    //Hijo de Oscuridad: Del tipo que quita vidas
    private  HijoOscuridad hijoOscuridad;
    private Texture texturaHijoOscuridad;
    private float DX_PASO_HIJOOSCURIDAD=150;

    //Hijos de la Oscuridad: Del tipo que Bloquean el paso
    private Bloque bloque;
    private Texture texturaBloque;
    private Array<Bloque> arrBloques;
    private float timerCrearBloque = 0;//Acumulador
    private float tiempoParaCrearBloque =4; //Se espera esos segundos en crear el bloque.

    //Barra Avance
    private BarraAvance barraGS;
    private BarraAvance barraRS;
    private BarraAvance barraBS;
    private BarraAvance barraWS;
    private float distanciaRecorridaG = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaR = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaB = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaW = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaControl = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.

    //Duración de las secciones
    private float duracionVerde = 20; //Valor que representa los segundos de duración aproximados de la sección 1.
    private float duracionRojo = 20; //Valor que representa los segundos de duración aproximados de la sección 2.
    private float duracionAzul = 20; //Valor que representa los segundos de duración aproximados de la sección 3.
    private float duracionBlanco = 20; //Valor que representa los segundos de duración aproximados de la sección 4.

    //Velocidad normal de las secciones
    private float velocidadVerde = 300; //Valor que repreenta los segundos de duración aproximados de la sección 1.
    private float velocidadRojo = 600; //Valor que repreenta los segundos de duración aproximados de la sección 2.
    private float velocidadAzul = 700; //Valor que repreenta los segundos de duración aproximados de la sección 3.
    private float velocidadBlanco = 800; //Valor que repreenta los segundos de duración aproximados de la sección 4.

    //Secciones
    private EstadoSeccion seccion = EstadoSeccion.VERDE;

    //Botones

    private Texture texturaBack;

    public JuegoGS (Lux juego) {
        this.juego=juego;
    }


    @Override
    public void show() {
        posicionDedo = new Vector3(0, 0, 0); //Posición del dedo
        crearFondo();
        crearPersonajes();
        crearVidas();
        crearTexturaHijoOscuridad();
        startTime = TimeUtils.nanoTime();
        startTimeOscuridad = TimeUtils.nanoTime();
        crearGemas();
        crearBarra();
        crearBotonBack();

        //Escena y Botón
        //escenaJuego=new Stage(vista);


        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaPausa(juego));
            }
        });

        // escenaJuego.addActor(btnRegresar);
        //Gdx.input.setInputProcessor(escenaJuego);



        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());
    }

    private void crearBotonBack() {
        texturaBack= new Texture("Menu/buttonback.png");

    }

    private void crearBarra() {
        //Los parametros de ancho final y distancia son para escalar el avance.
        barraGS = new BarraAvance(0,1,0,1,(ANCHO/4)+70, ALTO-margen*3,12,ANCHO/8,velocidadVerde*duracionVerde);//27000 es 1 minuto y medio
        barraRS = new BarraAvance(1,0,0,1,(ANCHO/4)+(ANCHO/8)+70, ALTO-margen*3,12,ANCHO/8,velocidadRojo*duracionRojo);//54000 es para 3 minutos
        barraBS = new BarraAvance(0,0,1,1,(ANCHO/4)+(ANCHO*2/8)+70, ALTO-margen*3,12,ANCHO/8,velocidadAzul*duracionAzul);//54000 es para 3 minutos
        barraWS = new BarraAvance(1,1,1,1,(ANCHO/4)+(ANCHO*3/8)+70, ALTO-margen*3,12,ANCHO/8,velocidadBlanco*duracionBlanco);//54000 es para 3 minutos
    }

    private void crearVidas() {
        texturaVida = new Texture("Utileria/vida.png");
        //Se usa getHeight en ambas (x y) para que quede el mismo margen tanto arriba como a la derecha
        vidas = new Vidas(texturaVida,ANCHO-margen-(texturaVida.getHeight()/2),margen+(texturaVida.getHeight()/2));
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    private void crearPersonajes() {
        //Personaje principal: Lumil
        texturaLumilJugando = new Texture ("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(texturaLumilJugando,ANCHO/2, ALTO/2,4,1,1/10f,1);

        //Enemigo principal: Oscuridad
        texturaOscuridad = new Texture("Personajes/oscuridad.png");
        oscuridad = new Oscuridad(texturaOscuridad,positionX,positionY,4,1,1/8f,2);

        texto=new Texto();
    }

    private void crearTexturaHijoOscuridad(){
        //Para el que quita vida
        texturaHijoOscuridad = new Texture("Personajes/HijoOsc_sprite.png");

        //Para el que bloquea
        texturaBloque = new Texture("Personajes/bloque.png");
        arrBloques = new Array<>();
    }

    private void crearGemas(){
        Texture texturaGemaAzul=new Texture("Personajes/gemaazul.png");
        arrGemas=new Array<>();
        Gema gema= new Gema(texturaGemaAzul, ANCHO,positionY);
        arrGemas.add(gema);  // Lo guarda en el arreglo
    }

    //Este método sirve para crear los objetos que se moveran y bloquearán el paso al jugador
    private void crearBloques() {
        //Crear Enemigo
        float xBloque = MathUtils.random(valorXMinExt,valorXMaxExt);
        float yBloque = MathUtils.random(valorYMinMarg,valorYMaxMarg);
        Bloque bloque = new Bloque(texturaBloque,xBloque,yBloque,3,1,1/8f,1);
        arrBloques.add(bloque);
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

        //Estado del juego
        if (contadorVidas == 0) {
            estado = EstadoJuego.PIERDE;
        }

        //Tiempo que pasó entre render.
        tiempoLumil += Gdx.graphics.getDeltaTime();
        //Tiempo que pasó entre render.
        tiempoOsc += Gdx.graphics.getDeltaTime();

        borrarPantalla(0, 1, 0);
        batch.setProjectionMatrix(camara.combined);

        //Se dibujan los elementos
        batch.begin();

        //Dibujar elementos del bosque
        bosque.movSeccionesCompletas(velocidad, delta, batch, true);

        //Dibujar vidas
        vidas.vidasRender(contadorVidas, batch);

        //Dibujar personaje principal
        lumil.animationRender(batch, tiempoLumil);

        //Dibujar enemigo oscuridad
        oscuridad.animationRender(batch, tiempoOsc);

        //Dibujar hijos Oscuridad
        if (hijoOscuridad != null) {
            hijoOscuridad.animationRender(batch, tiempoOsc);
        }

        //Dibujar los bloques
        for (Bloque bloque : arrBloques) {
            bloque.animationRender(batch, tiempoLumil);
        }

        //Dibujar Gemas
        if (arrGemas != null) {
            for (Gema gema : arrGemas) {
                gema.render(batch);
            }
        }


        // Dibuja el marcador
        //texto.mostrarMensaje(batch, "SCORE", ANCHO / 2, ALTO - 25);

        //Botones
        //escenaJuego.draw();


        // Dibujar back
        batch.draw(texturaBack,margen-20,ALTO-margen-texturaBack.getHeight()+20);


        batch.end();

        distanciaRecorridaControl += velocidad*delta;
        //System.out.println("distancia recorridaCONTROL:"+ distanciaRecorridaControl);
        //Velocidad
        //Eso divide la pantalla en las secciones de cada color.
        //Para cambiar de seccion se debe cumpir la condición de la distancia y de que chocaste con el monito
        if (distanciaRecorridaControl>velocidad*duracionVerde && distanciaRecorridaControl <= velocidad*duracionVerde*2){
            seccion = EstadoSeccion.ROJO;
        }else if (distanciaRecorridaControl>(velocidad*duracionVerde*2) && distanciaRecorridaControl <= (velocidad*duracionVerde*3)){
            seccion = EstadoSeccion.AZUL;
        } else if(distanciaRecorridaControl>(velocidad*duracionVerde*3)) {
            seccion = EstadoSeccion.BLANCO;
        }

        //Dibujar las barras
        switch (seccion) {
            case VERDE:
                distanciaRecorridaG += velocidad*delta;
                barraGS.renderAvance(distanciaRecorridaG,camara);
                break;
            case ROJO:
                distanciaRecorridaR +=velocidad*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderAvance(distanciaRecorridaR,camara);
                break;
            case AZUL:
                distanciaRecorridaB +=velocidad*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderAvance(distanciaRecorridaB,camara);
                break;
            case BLANCO:
                distanciaRecorridaW +=velocidad*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderEstatico(camara);
                if (distanciaRecorridaW>=velocidadBlanco*duracionBlanco){
                    barraWS.renderEstatico(camara);
                    System.out.println("GANASTE!");
                }else{barraWS.renderAvance(distanciaRecorridaW,camara);}
                break;
        }
        /*
        distanciaRecorridaControl += velocidad*delta;
        System.out.println("distancia recorridaCONTROL:"+ distanciaRecorridaControl);
        //Velocidad
        if (distanciaRecorridaControl<=velocidad*duracionVerde){
            distanciaRecorridaG += velocidad*delta;
            System.out.println("distancia recorridaG:"+ distanciaRecorridaG);
        }else if (distanciaRecorridaControl>velocidad*duracionVerde && distanciaRecorridaControl <= velocidad*duracionVerde*2){
            distanciaRecorridaR += velocidad*delta;
            System.out.println("distancia recorridaR:"+ distanciaRecorridaR);
        }else if (distanciaRecorridaControl>(velocidad*duracionVerde*2) && distanciaRecorridaControl <= (velocidad*duracionVerde*3){
            distanciaRecorridaB += velocidad*delta;
            System.out.println("distancia recorridaR:"+ distanciaRecorridaR);
        }

        //La barra de avance se debe de renderizar fuera del begin y end de batch para que no genere errores.
        barraRS.render(distanciaRecorridaR,camara);
        barraGS.render(distanciaRecorridaG,camara);
            }

         */

    }



    private void actuaizar(float delta) {

        timerCrearBloque += delta;
        if(timerCrearBloque>=tiempoParaCrearBloque) {
            timerCrearBloque = 0;
            crearBloques();
        }

        timerCrearGema += delta;
        if(timerCrearGema>=tiempoParaCrearGema){
            timerCrearGema = 0;
            crearGemas();
        }

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

            if (TimeUtils.timeSinceNanos(startTimeOscuridad) > 2000000000 && estadoOscuridad == EstadoOscuridad.QUIETO) {
                estadoOscuridad = EstadoOscuridad.ADELANTE;
                hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, 0,ALTO/2,3,1,1/8f,2);
            }

            if(oscuridad.sprite.getX() > -500){
                estadoOscuridad = EstadoOscuridad.ATRAS;
            }

            if (oscuridad.sprite.getX() < -780 && estadoOscuridad == EstadoOscuridad.ATRAS) {
                //Gdx.app.log("Distance", "QUIETO");
                startTimeOscuridad = TimeUtils.nanoTime();
                estadoOscuridad = EstadoOscuridad.QUIETO;
            }

            if (TimeUtils.timeSinceNanos(startTimeOscuridad) > 1000000000 && estadoOscuridad == EstadoOscuridad.QUIETO) {
                hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, 0,ALTO/2,3,1,1/8f,2);
            }



            moverLumil(isMooving);
            moverOscuridad(delta, estadoOscuridad);
            moverGemas();
            moverBloques(delta);

            depurarGemas();
            depurarGemas();

        }

    }

    private void hijoOscuridadColision() {
        if(estado == EstadoJuego.JUGANDO && lumil.sprite.getBoundingRectangle().overlaps(hijoOscuridad.sprite.getBoundingRectangle())){
            contadorVidas--;
            hijoOscuridad = null;
            Gdx.app.log("Vidas", Integer.toString(contadorVidas));
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
                Gdx.app.log("Hit", "collision");
                arrGemas.removeIndex(i);
            }
        }
    }

    private void depurarBloques(){
        //Eliminar bloques Fuera del rango de la pantalla

        //for (Bola bola: arrBolas) {
        //Comnetario Personal: Según yo, esto se puede simplifica o hacer mas eficiente y siento que tal vez con lo de arriba, pero no se bien como.
        for (int  i=arrBloques.size-1; i>=0; i--){
            Bloque bloque = arrBloques.get(i);
            //Prueba si la bola debe desaparecer cuando salga de pantalla
            if(bloque.getX()<-(ANCHO/2)) { //Logicamente necesito solo la X del objeto
                //Borrar el objeto
                arrBloques.removeIndex(i);
            }
        }
    }

    private void oscuridadColision() {
        if(estado == EstadoJuego.JUGANDO && lumil.sprite.getBoundingRectangle().overlaps(oscuridad.sprite.getBoundingRectangle())){
            contadorVidas--;
            Gdx.app.log("Vidas", Integer.toString(contadorVidas));
        }
    }

    private void moverGemas(){
        for(Gema gema: arrGemas){
            gema.moverHorizontal(DX_PASO_GEMA);
        }
        //depurarGemas();
    }

    private void moverBloques(float delta){
        //Mover los Enemigos
        for (Bloque bloque:arrBloques) {
            bloque.mover(delta,velocidadBosque);
        }
        //depurarBloques();
    }

    public void moverLumil(boolean Mooving){
        if (Mooving){
            lumil.mover(DELTA_Y,posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}
    }

    private void moverOscuridad(float delta, EstadoOscuridad estado){
        //Gdx.app.log("Distance", Float.toString(oscuridad.sprite.getX()));

        if(estado == EstadoOscuridad.ADELANTE){
            oscuridad.mover(velocidad*0.5f,velocidad*.5f + 100, delta);
        }else if(estado == EstadoOscuridad.ATRAS){
            oscuridad.mover(velocidad*0.5f,velocidad*.5f + 100, -delta);
        }else if(estado == EstadoOscuridad.QUIETO){
            oscuridad.mover(0,0, delta);
        }
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
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
            }else {
                float anchoBack = texturaBack.getWidth();
                float altoBack = texturaBack.getHeight();
                float xBack = margen-20;
                float yBack = ALTO-margen-texturaBack.getHeight()+20;

                //margen-20,ALTO-margen-texturaBack.getHeight()+20

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                // Vamos a verificar el botón de back
                Rectangle rectBack = new Rectangle(xBack, yBack, anchoBack, altoBack);



                if (posicionDedo.x>=ANCHO/4){
                    isMooving = true;
                 // a partir del Else if se van a poner los rectangulos de los botones para detectarlos.
                }else if (rectBack.contains(posicionDedo.x,posicionDedo.y)) {
                    juego.setScreen(new PantallaPausa(juego));
                }
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

            posicionDedo.x=screenX;
            posicionDedo.y=screenY;
            camara.unproject(posicionDedo);
            if (posicionDedo.x>=ANCHO/4){
                isMooving = true;
            }
            return true;
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
