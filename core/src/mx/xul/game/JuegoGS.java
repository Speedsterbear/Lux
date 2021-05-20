package mx.xul.game;

/*
Esta clase representa la sección verde (Green Section) del juego, por eso se llama GS
Autor: Carlos Arroyo.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mx.xul.game.pantallaBienvenida.ColoresLumil;



public class JuegoGS extends Pantalla {

    private Lux juego;

    boolean boolBack   = false;
    boolean boolRojo = false;
    boolean boolAzul = false;
    boolean boolVerde = false;

    // AssetManager
    private AssetManager manager;

    //Duración de las secciones
    private float duracionVerde = 20; //Valor que representa los segundos de duración aproximados de la sección 1.
    private float duracionRojo = 20; //Valor que representa los segundos de duración aproximados de la sección 2.
    private float duracionAzul = 20; //Valor que representa los segundos de duración aproximados de la sección 3.
    private float duracionBlanco = 20; //Valor que representa los segundos de duración aproximados de la sección 4.

    //Velocidad normal de las secciones
    private final float velocidadVerde = 300; //Valor que repreenta la velocidad normal de la sección 1.
    private final float velocidadRojo = 600; //Valor que repreenta la velocidad normal de la sección 2.
    private final float velocidadAzul = 700; //Valor que repreenta la velocidad normal de la sección 3.
    private final float velocidadBlanco = 800; //Valor que repreenta la velocidad normal de la sección 4.

    //Velocidad normal de la oscuridad según las secciones
    private final float velocidadOscVerde = velocidadVerde; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 1.
    private final float velocidadOscRojo = velocidadRojo + 15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 2.
    private final float velocidadOscAzul = velocidadAzul + 15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 3.
    private final float velocidadOscBlanco = velocidadBlanco + 15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 4.

    //Velocidad normal de los hijos de la oscuridad según las secciones
    private final float velocidadHijoOscVerde = velocidadVerde + 200; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 1.
    private final float velocidadHijoOscRojo = velocidadRojo + 35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 2.
    private final float velocidadHijoOscAzul = velocidadAzul + 35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 3.
    private final float velocidadHijoOscBlanco = velocidadBlanco + 35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 4.

    //Velocidades generales
    private float velocidadInicial = velocidadVerde;
    private float velocidad = velocidadVerde;

    //Escena para Botones
    //private Stage escenaJuego;


    //Márgenes
    private float margen = 20;
    //Valor mínimo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMinExt = ANCHO + 200;
    //Valor máximo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMaxExt = ANCHO * 1.5f;
    //Valor mínimo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMinMarg = 0;
    //Valor máximo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMaxMarg = ALTO;

    //Fondo de bosque en movimiento
    private Texture bosquefondo;
    private Texture bosqueatras;
    private Texture bosquemedio;
    private Texture bosquefrente;
    private FondoEnMovimiento bosque;

    // Texto
    //Muestra los valores en la pantalla
    private Texto texto;

    //Personaje principal: Lumil
    private Lumil lumil;
    private Texture texturaLumilJugando;
    float tiempoLumil = 0f; //Acumulador
    private float DELTA_Y = 10; //Avance vertical de lumil
    private final float duracionFrameLumil = 1/10f; //Duración en segundos  inicial de los frames de la animación d Lumil
    private final float incrementoVelocidadVerde = 1.5f; //Numero de veces en las que se incrementará la velocidad al usar la habilidad de la gema verde
    private Vector3 posicionDedo;
    private boolean isMoving = false; //indica si el perosnaje principal debe moverse hacia arriba o abajo
    private ColoresLumil colorLumil = ColoresLumil.BLANCO; // Representa el color que tiene el personaje principal en el momento.
    private Texture texturaBrillo;
    private BrilloLumil brilloLumil;

    private Lumil lumilG;
    private Texture texturaLumilG;

    private Lumil lumilR;
    private Texture texturaLumilR;

    private Lumil lumilB;
    private Texture texturaLumilB;


    //Enemigo principal: La Oscuridad
    private Oscuridad oscuridad;
    private Texture texturaOscuridad;
    float tiempoOsc =0f; //Acumulador
    private float velocidadOscuridad = velocidadOscVerde;//Velocidad Actual de la oscuridad
    private float velocidadHijosOscuridad = velocidadHijoOscVerde;//Velocidad Actual de hijos de la oscuridad
    //private float positionXStart = -900;
    private float positionX = (-ANCHO/2)+margen*2;
    private float positionY = ALTO/2;
    private long startTimeOscuridad = 0;
    //private EstadoOscuridad estadoOscuridad = EstadoOscuridad.QUIETO;

    //General
    //private float velocidad = velocidadBosque;

    //Vidas
    private int contadorVidas = 3;
    private Texture texturaVida;
    private LuzVida luzVida;
    private Vidas vidas;


    //Estado Juego
    private EstadoJuego estado = EstadoJuego.JUGANDO;

    //Personajes
    private Esgrun esgrun;
    private Rojel rojel;
    private Shiblu shiblu;
    private float DX_PASO_ESGRUN=2.5f;
    private float DX_PASO_ROJEL=2.5f;
    private float DX_PASO_SHIBLU=2.5f;
    public static float aparicion = 0;

    //Luz Final
    private PersonajeSecundario luzFinal;//Para la luz final que es el ultimo personaje con el que Lumil debe reunirse para derrotar a la oscuridad.
    private Texture texturaLuz;

    //Hijos de la Oscuridad: Del tipo que Bloquean el paso
    private Array<Bloque> arrBloques;
    private Bloque bloque;
    private Texture texturaBloque;
    private float timerCrearBloque = 0;//Acumulador
    private float tiempoParaCrearBloque =4; //Se espera esos segundos en crear el bloque.

    //Hijos de la Oscuridad: Del tipo que quitan vidas
    private Array<HijoOscuridad> arrHijosOscuridad;
    private HijoOscuridad hijoOscuridad;
    private Texture texturaHijoOscuridad;
    private float timerCrearHijoOscuridad = 0;//Acumulador
    private float tiempoParaCrearHijoOscuridad =4; //Se espera esos segundos en crear el bloque.


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
    private Texture fondoBarra; //Representa el Fondo para la barra de avance

    //Secciones
    private EstadoSeccion seccion = EstadoSeccion.VERDE;
    private EstadoSeccion seccionAnterior = EstadoSeccion.VERDE;

    //Botones

    //Gemas
    private float timerGemas = 0;//Acumulador
    private float tiempoParaGemas = 3f; //Se espera esos segundos en crear el bloque.
    private boolean powerups = true;
    private boolean powerupActive = true;
    //TODO Poder Activado

    private Boton gemaVerde; //Gema verde  para activar el poder
    private Texture texturaGemaVerdeOFF;
    private Texture texturaGemaVerdeON;
    private boolean boolPowerUpGemaVerde = false;
    private boolean activarPowerUpGemaVerde = false;
    private float timerPowerUpGemaVerde = 0;
    private float tiempoParaPowerUpGemaVerde = 0.5f;

    private Boton gemaRoja; //Gema roja para activar el poder
    private Texture texturaGemaRojaOFF;
    private Texture texturaGemaRojaON;
    private boolean boolPowerUpGemaRoja = false;
    private boolean activarPowerUpGemaRoja = false;
    private float timerPowerUpGemaRoja = 0;
    private float tiempoParaPowerUpGemaRoja = 3f;

    private Boton gemaAzul; //Gema Azul para activar el poder
    private Texture texturaGemaAzulON;
    private Texture texturaGemaAzulOFF;
    private boolean boolPowerUpGemaAzul = false;
    private boolean colisionPowerUpGemaAzul = false;
    private boolean activarPowerUpGemaAzul = false;
    private float timerPowerUpGemaAzul = 0;
    private float tiempoParaPowerUpGemaAzul = 3f;

    boolean colisionLumilFront = false;
    boolean colisionLumilUp = false;
    boolean colisionLumilDown = false;

    private Boton botonPausa; //Boton para activar la pausa
    private Texture texturaPausaON;//Botón de Regreso
    private Texture texturaPausaOFF;


    //Fade a Negro
    private ShapeRenderer rectNegro;
    private float alfaRectanguloNegro = 0;

    //Sonidos y musica
    private Sound sonidocomeOscuridad;
    private Sound sonidoPoderActivado;
    private Sound sonidoquitavidas;
    private Sound sonidoTocaLuzBlanca;
    //private Sound sonidoTocaPrimario;

    //Musica de Fondo
    private Music musicaFondo;
    private Sound sonidoJuego;
    private long idSonidoJuego;
    private float valorPitch = 1;//Valor del pitch del sonido principal
    private float volumenSonidoPrincipal = 0;
    private boolean isPitchChanging = false;
    private boolean isSonidoJuegoFadingIn = false;
    private boolean isSonidoJuegoFadingOut = false;
    private final float VALOR_MAXIMO_PITCH=1.09f;
    private final float VOLUMEN_MAXIMO_SONIDOJUEGO = 0.5f;
    private final float TIEMPO_FADE_SONIDOJUEGO = 10; //Tiempo que tarda en realizar los fade in y out.

    //private float valPitch =1;
    //private boolean bolPitch=false;


    public JuegoGS (Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }


    @Override
    public void show() {
        posicionDedo = new Vector3(0, 0, 0); //Posición del dedo
        crearFondo();
        crearPersonajes();
        crearBrillo();
        crearVidas();
        crearTexturaHijoOscuridad();
        crearBloques();
        crearBarra();
        crearBotones();
        crearRectangulo();
        crearSonidos();

        //cargarRecursos();
        crearObjetos();



        //Escena y Botón
        //escenaJuego=new Stage(vista);


        // escenaJuego.addActor(btnRegresar);
        //Gdx.input.setInputProcessor(escenaJuego);

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());

    }

    private void crearObjetos() {
    }

    private void crearSonidos() {
        sonidocomeOscuridad = manager.get("Sonidos/sonidoComeOscuridad.wav");
        sonidoPoderActivado = manager.get("Sonidos/sonidoPoderActivado.wav");
        //sonidoPoderActivado.setVolume(0.5f,0.2f);
        sonidoquitavidas = manager.get("Sonidos/sonidoquitavidas.wav");
        sonidoTocaLuzBlanca = manager.get("Sonidos/sonidoTocaLuzBlanca.wav");
        musicaFondo = manager.get("Sonidos/musicaJuego.mp3");
        sonidoJuego = manager.get("Sonidos/musicaJugar.ogg");

        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.2f);
        //musicaFondo.play();
        idSonidoJuego= sonidoJuego.play();
        sonidoJuego.setVolume(idSonidoJuego,0);
        sonidoJuego.setLooping(idSonidoJuego,true);
        isSonidoJuegoFadingIn=true; //Se inicializa la entrada

    }

    private void crearRectangulo() {
        //Rectangulo usado para hacer Fade a negro
        rectNegro = new ShapeRenderer();
    }

    private void crearBrillo() {
        texturaBrillo = manager.get("Utileria/brilloLumil.png");
        brilloLumil = new BrilloLumil(texturaBrillo,ANCHO/2+(texturaBrillo.getWidth()/6),ALTO/2);
    }

    private void crearBotones() {

        //Gema Verde
        texturaGemaVerdeON= manager.get("BotonesGemas/gemaVerde_ON.png");
        texturaGemaVerdeOFF = manager.get("BotonesGemas/gemaVerde_OFF.png");
        gemaVerde = new Boton(texturaGemaVerdeOFF,texturaGemaVerdeON,margen+(texturaGemaVerdeOFF.getWidth()/2),margen+(texturaGemaVerdeOFF.getHeight()/2));

        //Gema Roja
        texturaGemaRojaON = manager.get("BotonesGemas/gemaRoja_ON.png");
        texturaGemaRojaOFF = manager.get("BotonesGemas/gemaRoja_OFF.png");
        gemaRoja = new Boton(texturaGemaRojaOFF, texturaGemaRojaON,margen+(3*texturaGemaRojaOFF.getWidth()/2),margen+(texturaGemaRojaOFF.getHeight()/2));

        //Gema Azul
        texturaGemaAzulON =manager.get("BotonesGemas/gemaAzul_ON.png");
        texturaGemaAzulOFF = manager.get("BotonesGemas/gemaAzul_OFF.png");
        gemaAzul = new Boton(texturaGemaAzulOFF, texturaGemaAzulON,margen+(texturaGemaVerdeOFF.getWidth()),(3*texturaGemaVerdeOFF.getHeight()/2)-10);

        //Boton Pausa
        texturaPausaON = manager.get("Botones/pausa_ON.png");
        texturaPausaOFF = manager.get("Botones/pausa_OFF.png");
        botonPausa = new Boton(texturaPausaOFF,texturaPausaON,margen+(texturaGemaVerdeOFF.getWidth()),ALTO-(margen/2) -(texturaPausaOFF.getHeight()/2));
    }

    private void crearBarra() {
        //Los parametros de ancho final y distancia son para escalar el avance.
        fondoBarra = manager.get("Utileria/atrasBarraAvance.png");
        barraGS = new BarraAvance(0,1,0,1,(ANCHO/4), ALTO-margen*3,12,ANCHO/8,velocidadVerde*duracionVerde);//27000 es 1 minuto y medio
        barraRS = new BarraAvance(1,0,0,1,(ANCHO/4)+(ANCHO/8), ALTO-margen*3,12,ANCHO/8,velocidadRojo*duracionRojo);//54000 es para 3 minutos
        barraBS = new BarraAvance(0,0,1,1,(ANCHO/4)+(ANCHO*2/8), ALTO-margen*3,12,ANCHO/8,velocidadAzul*duracionAzul);//54000 es para 3 minutos
        barraWS = new BarraAvance(1,1,1,1,(ANCHO/4)+(ANCHO*3/8), ALTO-margen*3,12,ANCHO/8,velocidadBlanco*duracionBlanco);//54000 es para 3 minutos
    }

    private void crearVidas() {
        texturaVida = manager.get("Utileria/vida.png");
        //Se usa getHeight en ambas (x y) para que quede el mismo margen tanto arriba como a la derecha
        vidas = new Vidas(texturaVida,ANCHO-margen-(texturaVida.getHeight()/2),margen+(texturaVida.getHeight()/2));
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=manager.get(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=manager.get(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    private void crearPersonajes() {
        //Personaje principal: Lumil (Blanco)
        texturaLumilJugando = new Texture ("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(texturaLumilJugando,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);

        //Personaje principal: Lumil (Verde)
        texturaLumilG = new Texture ("Personajes/lumilG.png");
        lumilG = new Lumil(texturaLumilG,ANCHO/2, ALTO/2,4,1,duracionFrameLumil *  (1/incrementoVelocidadVerde),1);

        //Personaje principal: Lumil (Rojo)
        texturaLumilR = new Texture ("Personajes/lumilR.png");
        lumilR = new Lumil(texturaLumilR,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);

        //Personaje principal: Lumil (Azul)
        texturaLumilB = new Texture ("Personajes/lumilB.png");
        lumilB = new Lumil(texturaLumilB,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);

        //Enemigo principal: Oscuridad
        texturaOscuridad = new Texture("Personajes/oscuridad.png");
        oscuridad = new Oscuridad(texturaOscuridad,positionX,positionY,4,1,1/8f,2);

        texto=new Texto();

        //Luz Final
        texturaLuz = new Texture ("Personajes/luzPersonaje.png");

        luzFinal = new PersonajeSecundario(texturaLuz,3*ANCHO/2,ANCHO/2,5,1,1/10f,2);
    }

    private void crearTexturaHijoOscuridad(){
        //Para el que quita vida
        texturaHijoOscuridad = new Texture("Personajes/HijoOsc_sprite.png");
        arrHijosOscuridad= new Array<>();

        //Para el que bloquea
        texturaBloque = new Texture("Personajes/bloque.png");

        arrBloques = new Array<>();
    }

    private void crearEsgrun(){
        Texture texturaEsgrun = new Texture("Personajes/Esgrun.png");
        esgrun = new Esgrun(texturaEsgrun, ANCHO,positionY);
    }

    private void crearRojel(){
        Texture texturaRojel = new Texture("Personajes/Rojel.png");
        rojel = new Rojel(texturaRojel, ANCHO,positionY);
    }

    private void crearShiblu(){
        Texture texturaShiblu = new Texture("Personajes/Shiblu.png");
        shiblu = new Shiblu(texturaShiblu, ANCHO,positionY);
    }

    //Este método sirve para crear los objetos que se moveran y bloquearán el paso al jugador
    private void crearBloques() {
        //Crear Enemigo
        float xBloque = MathUtils.random(valorXMinExt,valorXMaxExt);
        float yBloque = MathUtils.random(valorYMinMarg,valorYMaxMarg);
        Bloque bloque = new Bloque(texturaBloque,xBloque,yBloque,3,1,1/8f,1);
        arrBloques.add(bloque);
    }

    private void crearHijosOscuridad(){
        //Crear Enemigo
        float xHijo = MathUtils.random(ANCHO - valorXMaxExt,ANCHO-valorXMinExt);
        float yHijo = MathUtils.random(valorYMinMarg,valorYMaxMarg);
        HijoOscuridad hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad,xHijo,yHijo,3,1,1/10f,1);
        arrHijosOscuridad.add(hijoOscuridad);
    }

    //Codigo de Ricardo (Hijos Oscuridad)
    /*
    private void crearHijosOscuridad(){
        hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad, 0,ALTO/2,3,1,1/8f,2);
    }
     */

    private void crearFondo() {
        bosquefondo = new Texture("Escenarios/bosque_fondo.jpg");
        bosqueatras = new Texture("Escenarios/bosque_atras.png");
        bosquemedio = new Texture("Escenarios/bosque_medio.png");
        bosquefrente = new Texture("Escenarios/bosque_frente.png");
        bosque = new FondoEnMovimiento(bosquefondo,bosqueatras,bosquemedio,bosquefrente);
    }

    @Override
    public void render(float delta) {

        controlSonidoPrincipal(delta);

        actualizar(delta);


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

        //Dibujar brillo del personaje principal
        brilloLumil.render(batch);

        //Dibujar personaje principal
        dibujarLumil(batch);


        /*

        //Dibujar hijos Oscuridad
        if (hijoOscuridad != null) {
            hijoOscuridad.animationRender(batch, tiempoOsc);
        }

         */

        //Dibujar los bloques
        for (HijoOscuridad hijoOscuridad : arrHijosOscuridad) {
            hijoOscuridad.animationRender(batch, tiempoOsc);
        }

        //Dibujar los bloques
        for (Bloque bloque : arrBloques) {
            bloque.animationRender(batch, tiempoLumil);
        }

        //Dibujar
        if(esgrun!=null)
            esgrun.render(batch);

        if(rojel!=null)
            rojel.render(batch);

        if(shiblu!=null)
            shiblu.render(batch);

        //Dibujar enemigo oscuridad
        oscuridad.animationRender(batch, tiempoOsc);


        // Dibuja el marcador
        //texto.mostrarMensaje(batch, "SCORE", ANCHO / 2, ALTO - 25);

        //Botones
        //escenaJuego.draw();

        /* Cidogo Antiguo

        // Dibujar back
        if (boolBack == true){
            batch.draw(texturaPausaOFF,margen-20,ALTO-margen- texturaPausaOFF.getHeight()+20);
        }else {
            batch.draw(texturaPausaON,margen-20,ALTO-margen- texturaPausaON.getHeight()+20);
        }

         */

        // Dibujar gemas

        gemaVerde.render(batch);
        gemaRoja.render(batch);
        gemaAzul.render(batch);

        botonPausa.render(batch);

        /* Codigo Antiguo de Gemas

        if (boolAzul ==true) {
            batch.draw(texturaGemaAzul, margen+(texturaGemaAzul.getWidth()/2), texturaGemaVerdeOFF.getHeight());
        }else{
            batch.draw(texturaGemaAzulOn, margen+(texturaGemaAzul.getWidth()/2), texturaGemaVerdeOFF.getHeight());
        }
       if (boolRojo == true) {
           batch.draw(texturaGemaRoja, texturaGemaRoja.getWidth()+margen, margen);
       }else{
           batch.draw(texturaGemaRojaOn, texturaGemaRojaOn.getWidth()+margen, margen);
       }
       if (boolVerde == true){
           batch.draw(texturaGemaVerdeON,margen,margen);
       }else{
           batch.draw(texturaGemaVerdeOFF,margen,margen);
       }

         */

        //Dibujar vidas
        vidas.vidasRender(contadorVidas, batch);

       //Dibujar lo de atras de la barra de avance
        batch.draw(fondoBarra,(ANCHO/4)-4,ALTO-fondoBarra.getHeight());

        batch.end();

        distanciaRecorridaControl += velocidad *delta;

        //Velocidad
        //Eso divide la pantalla en las secciones de cada color.
        //Para cambiar de seccion se debe cumpir la condición de la distancia y de que chocaste con el monito
        aparicion += delta;
        //System.out.println(aparicion);
        if (aparicion>= 20 && aparicion<40){
            seccion = EstadoSeccion.ROJO;
            //System.out.println("Rojo");
        }else if (aparicion>= 40 && aparicion<60){
            seccion = EstadoSeccion.AZUL;
            //System.out.println("azul");
        } else if(aparicion>= 60) {
            seccion = EstadoSeccion.BLANCO;
            //System.out.println("blanco");
        }



        //Dibujar barra progreso
        dibujarBarras(delta);

        if (oscuridad.getYaMordio()){
            fadePerder();
        }

    }

    private void controlSonidoPrincipal(float delta) {

        sonidoPrincipalFadeIn(delta);
        sonidoPricipalFadeOut(delta);
        sonidoPrincipalCambioPitch(delta);


    }

    private void sonidoPrincipalCambioPitch(float delta) {

        //Control para el cambio del Pitch del sonido principal
        if (isPitchChanging){
            float valorPitchDeseado = 1;
            switch (seccion) {
                case VERDE:
                    valorPitchDeseado = 1;
                    break;
                case ROJO:
                    valorPitchDeseado = 1+((VALOR_MAXIMO_PITCH-1)/3);
                    break;
                case AZUL:
                    valorPitchDeseado = 1+(2*(VALOR_MAXIMO_PITCH-1)/3);
                    break;
                case BLANCO:
                    valorPitchDeseado = VALOR_MAXIMO_PITCH;
                    break;
            }
            valorPitch +=0.005f;
            if (valorPitch>=valorPitchDeseado){
                valorPitch = valorPitchDeseado;
                isPitchChanging = false;
            }

            sonidoJuego.setPitch(idSonidoJuego,valorPitch); //asignar el nuevo valor del pitch



        }


    }

    private void sonidoPricipalFadeOut(float delta) {


        //Control para el Fade out
        if (isSonidoJuegoFadingOut){
            volumenSonidoPrincipal-=(5*delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoPrincipal<=0){
                volumenSonidoPrincipal = 0;
                isSonidoJuegoFadingOut=false;
            }
            sonidoJuego.setVolume(idSonidoJuego,volumenSonidoPrincipal);//Asignar el nuevo valor de volumen.

        }

    }

    private void sonidoPrincipalFadeIn(float delta) {

        //Control para el Fade in
        if (isSonidoJuegoFadingIn){
            volumenSonidoPrincipal+=(delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoPrincipal>=VOLUMEN_MAXIMO_SONIDOJUEGO){
                volumenSonidoPrincipal = VOLUMEN_MAXIMO_SONIDOJUEGO;
                isSonidoJuegoFadingIn=false;
            }
            sonidoJuego.setVolume(idSonidoJuego,volumenSonidoPrincipal);//Asignar el nuevo valor de volumen.

        }

    }

    private void fadePerder() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rectNegro.setProjectionMatrix(camara.combined);
        rectNegro.setColor(0, 0, 0, alfaRectanguloNegro);
        rectNegro.begin(ShapeRenderer.ShapeType.Filled);
        rectNegro.box(0,0,0,ANCHO,ALTO,0);
        alfaRectanguloNegro += (Gdx.graphics.getDeltaTime()*1.5f);
        rectNegro.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (alfaRectanguloNegro >= 1){
            sonidoJuego.stop();//Detener por completo el sonido del juego principal de fondo
            juego.setScreen(new PantallaPerdida(juego));
        }

    }

    private void dibujarLumil(SpriteBatch batch) {

        switch (colorLumil) {
            case VERDE:
                lumilG.animationRender(batch, tiempoLumil);
                break;
            case ROJO:
                lumilR.animationRender(batch, tiempoLumil);
                break;
            case AZUL:
                lumilB.animationRender(batch, tiempoLumil);
                break;
            case BLANCO:
                lumil.animationRender(batch, tiempoLumil);
                break;
        }

    }

    private void actualizar(float delta) {

        //Cuando la velocidad sea = 0, la oscuridad avanzará rápido por nuestro personaje.
        if (estado == EstadoJuego.JUGANDO) {

            //Detecta el cambio de sección
            if (seccion!=seccionAnterior){
                //Se ejecuta una sola vez cada que se cambia de sección
                cambioSeccion();
            }

            if(aparicion>= duracionVerde - 1 && aparicion<=duracionVerde){
                crearEsgrun();
            }else if(aparicion>= duracionRojo*2 - 1 && aparicion<=duracionRojo*2){
                crearRojel();
            }else if(aparicion>= duracionAzul*3 - 1 && aparicion<=duracionAzul*3){
                crearShiblu();
            }
            /*if(seccion == EstadoSeccion.VERDE){
                crearEsgrun();
            }else if(seccion == EstadoSeccion.ROJO){
                crearRojel();
            }else if(seccion == EstadoSeccion.AZUL){
                crearShiblu();
            }*/


            if(!boolPowerUpGemaAzul){
                colisionLumilFront = hijoOscuridadColisionFront();
                colisionLumilUp = hijoOscuridadColisionUp();
                colisionLumilDown = hijoOscuridadColisionDown();
            }

            timerCrearBloque += delta;
            if (timerCrearBloque >= tiempoParaCrearBloque && colisionLumilFront == false && colisionLumilUp == false && colisionLumilDown == false ) {
                timerCrearBloque = 0;
                crearBloques();
            }

            timerCrearHijoOscuridad += delta;
            if (timerCrearHijoOscuridad >= tiempoParaCrearHijoOscuridad) {
                timerCrearHijoOscuridad = 0;
                crearHijosOscuridad();
            }

            //Actualizar el Brillo (Posición y forma)
            actualizarBrillo();

            //Mover Lumil



            //Mover Oscuridad
           // velocidadOscuridad = velocidadOscVerde;
            moverOscuridad(delta);

            //Mover Hijo de Oscuridad (va afuera porque se sige moviendo aunque lúmil no se mueva, es como la oscuridad grande)
            moverHijoOscuridad(delta);

            //Mover y Depurar Oscuridad
            if (colisionLumilFront == false && colisionLumilUp == false && colisionLumilDown == false) {
                moverLumil(isMoving, DELTA_Y);
                moverBloques(delta);
                //Aqui creo que se debería optimizar esto para que no se esté asignando constantemente la velocidad
                returnVelocidadBosque();
            }

            if (colisionLumilFront) {
                moverLumil(isMoving, DELTA_Y);
                velocidad = 0;
                velocidadOscuridad = velocidadOscVerde;
                oscuridadColision();
            }

            if (colisionLumilFront && colisionLumilUp) {
                moverLumilxy(isMoving, 0, DELTA_Y);
                velocidad = 0;
                velocidadOscuridad = velocidadOscVerde;
                oscuridadColision();
            }

            if (colisionLumilFront && colisionLumilDown) {
                moverLumilxy(isMoving, DELTA_Y, 0);
                velocidad = 0;
                velocidadOscuridad = velocidadOscVerde;
                oscuridadColision();
            }

            if (colisionLumilDown){
                moverLumilxy(isMoving, DELTA_Y, 0);
                oscuridadColision();
            }

            if (colisionLumilUp){
                moverLumilxy(isMoving,0 ,DELTA_Y);
                oscuridadColision();
            }

            if(boolPowerUpGemaVerde){
                powerUpGemaVerde();
            }

            if(boolPowerUpGemaRoja){
                powerUpGemaRoja();
            }

            if(boolPowerUpGemaAzul){
                powerUpGemaAzul();
            }

            if(!powerups){
                activePowerups();
            }

            if(esgrun!=null){
                moverEsgrun();
                depurarEsgrun();
            }

            if(rojel!=null){
                moverRojel();
                depurarRojel();
            }

            if(shiblu!=null){
                moverShiblu();
                depurarShiblu();
            }

            // Código Ricardo
            /*
            if (hijoOscuridad != null) {
                moverHijoOscuridad(delta);
            }

             */
            //Depurar Elementos
            depurarBloques();
            depurarHijosOscuridad();

            /*Codigo de Ricardo (Hijos)
            if (hijoOscuridad != null) {
                depurarHijosOscuridad();
            }

             */


        } //----------------------------------------------------------------------------------------
        if (estado == EstadoJuego.PIERDE) {
            velocidad = 0;
            velocidadOscuridad = 0;
            aparicion = 0;
            //Aqui se debe agregar la animación antes de cambiar de pantlla
            isSonidoJuegoFadingOut=true; //comenzar a desaparecer el sonido
            oscuridad.granMordida();


            //juego.setScreen(new PantallaPerdida(juego));
            //Aqui se llama la secuencia de final (o sea la pantalla de andrea)
        }

        if (estado == EstadoJuego.GANA) {
            velocidad = 0;
            velocidadOscuridad = 0;
            sonidoTocaLuzBlanca.play(); //Este sonido debe de estar en la colisión
            //falta la transición a Blanco, en esta poner isPitchChanging = true;
            sonidoJuego.stop();
            juego.setScreen(new PantallaGana(juego));
            //Aqui se llama la secuencia de final (o sea la pantalla de andrea)
        }

    }



    private void cambioSeccion() {
        //Actualizan las velocidaddades de los frames en las animacioens de Lumil
        //El número 1 en Tipo, representa que es un Loop infinito el tipo de animación de los frames.
        lumil.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidad),1);
        lumilR.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidad),1);
        lumilG.animationUpdate((duracionFrameLumil/incrementoVelocidadVerde)*(velocidadVerde/ velocidad),1);
        lumilB.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidad),1);

        //Cambiar el pitch porque cambió la sección
        isPitchChanging=true;

        //Aqui se colocan las modificaciones de queden ocurrir al inicio de una sección en específico
        //la sección verde no se incluye porque los valores para ésta son los iniciales.
        switch (seccion){
            case ROJO:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                break;
            case AZUL:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                break;
            case BLANCO:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                break;
        }
        seccionAnterior = seccion;
    }

    private void actualizarBrillo() {
        //Mover Brillo
        brilloLumil.mover(lumil.sprite.getY() + (lumil.sprite.getHeight() / 2));

        //Asignar Color de brillo y simular la acción del brillo
        switch (colorLumil) {
            case VERDE:
                brilloLumil.actualizar(0,1,0,0.9f);
                break;
            case ROJO:
                brilloLumil.actualizar(1,0,0,0.9f);
                break;
            case AZUL:
                brilloLumil.actualizar(0,0,1,0.9f);
                break;
            case BLANCO:
                brilloLumil.actualizar(1,1,1,0.9f);
                break;
        }

    }

    private void returnVelocidadBosque(){
        velocidad = velocidadInicial;
    }

    private void depurarHijosOscuridad() {

        //Eliminar bloques Fuera del rango de la pantalla
        for (int  i=arrHijosOscuridad.size-1; i>=0; i--){
            HijoOscuridad hijoOscuridad = arrHijosOscuridad.get(i);
            if(arrHijosOscuridad!= null && lumil.getRectangle().overlaps(hijoOscuridad.sprite.getBoundingRectangle())) {
                arrHijosOscuridad.removeIndex(i);
                if(!boolPowerUpGemaRoja)
                    contadorVidas --;
                sonidoquitavidas.play();
            }
            if(arrHijosOscuridad!= null && hijoOscuridad.getX()>(3*ANCHO/2)) { //Logicamente necesito solo la X del objeto
                arrHijosOscuridad.removeIndex(i);
            }
        }

    }



    private void depurarEsgrun() {
        if(lumil.getRectangle().overlaps(esgrun.sprite.getBoundingRectangle())){
            esgrun = null;
            activarPowerUpGemaVerde = true;
        }
    }

    private void depurarRojel() {
        if(lumil.getRectangle().overlaps(rojel.sprite.getBoundingRectangle())){
            rojel = null;
            activarPowerUpGemaRoja = true;
        }
    }

    private void depurarShiblu() {
        if(lumil.getRectangle().overlaps(shiblu.sprite.getBoundingRectangle())){
            shiblu = null;
            activarPowerUpGemaAzul = true;
        }
    }

    private void depurarBloques(){
        //Eliminar bloques Fuera del rango de la pantalla
        for (int  i=arrBloques.size-1; i>=0; i--){
            Bloque bloque = arrBloques.get(i);
            if(arrBloques!= null && bloque.getX()<-(ANCHO/2)) { //Logicamente necesito solo la X del objeto
                arrBloques.removeIndex(i);
            }
        }
    }

    private boolean hijoOscuridadColisionFront(){
        for(Bloque bloque: arrBloques){
            if(lumil.getFrontRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }

    private boolean hijoOscuridadColisionUp(){
        for(Bloque bloque: arrBloques){
            if(lumil.getUpperRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }

    private boolean hijoOscuridadColisionDown(){
        for(Bloque bloque: arrBloques){
            if(lumil.getLowerRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }


    private void oscuridadColision() {
        //Si la posición en X (izquierda) de Lumil es mayor que la posición en X (derecha) de la oscuridad - 100 px, quiere decir que ya lo tocó
        //El 100 es un número arbitrario elegido de acuerdo al Sprite de Lumil
        if(lumil.sprite.getX()<=(oscuridad.sprite.getX()+oscuridad.sprite.getWidth()-120)) {
            contadorVidas=0;//Muere =(
            sonidocomeOscuridad.play();
            isSonidoJuegoFadingOut=true; //Comenzar el fade de la musica principal del juego
        }


        /* Código Anterior (Antes del 5 de Mayo)
        if(lumil.sprite.getBoundingRectangle().overlaps(oscuridad.sprite.getBoundingRectangle())){
            contadorVidas=0;//Muere =(
            Gdx.app.log("Vidas", Integer.toString(contadorVidas));
        }

         */
    }

    private void moverEsgrun(){
        esgrun.moverHorizontal(DX_PASO_ESGRUN);
    }

    private void moverRojel(){
        rojel.moverHorizontal(DX_PASO_ROJEL);
    }

    private void moverShiblu(){
        shiblu.moverHorizontal(DX_PASO_SHIBLU);
    }

    private void moverBloques(float delta){
        //Mover los Enemigos
        for (Bloque bloque:arrBloques) {
            bloque.mover(delta, velocidad);
        }
        //depurarBloques();
    }

    public void moverLumil(boolean Moving, float DELTAY){
        if (Moving){
            lumil.mover(DELTAY,posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}

        //Realmente el que realiza la función de moverse y girar es Lumil (blanco), cuando se cambia de color, éstos toman la posición y la rotación de Lumil blanco.
        switch (colorLumil){
            case VERDE:
                lumilG.sprite.setY(lumil.sprite.getY());
                lumilG.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case ROJO:
                lumilR.sprite.setY(lumil.sprite.getY());
                lumilR.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case AZUL:
                lumilB.sprite.setY(lumil.sprite.getY());
                lumilB.sprite.setRotation(lumil.sprite.getRotation());
                break;
        }

    }

    public void moverLumilxy(boolean Moving, float up, float down){
        if (Moving){
            lumil.moverxy(up, down, posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}

        //Realmente el que realiza la función de moverse y girar es Lumil (blanco), cuando se cambia de color, éstos toman la posición y la rotación de Lumil blanco.
        switch (colorLumil){
            case VERDE:
                lumilG.sprite.setY(lumil.sprite.getY());
                lumilG.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case ROJO:
                lumilR.sprite.setY(lumil.sprite.getY());
                lumilR.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case AZUL:
                lumilB.sprite.setY(lumil.sprite.getY());
                lumilB.sprite.setRotation(lumil.sprite.getRotation());
                break;
        }

    }

    private void activePowerups() {
        timerGemas += Gdx.graphics.getDeltaTime();
        if(timerGemas >= tiempoParaGemas){
            powerups = true;
            timerGemas = 0;
        }
    }

    private void powerUpGemaVerde(){
        velocidadOscuridad = 0;
        velocidad = velocidad*2f;
        timerPowerUpGemaVerde += Gdx.graphics.getDeltaTime();
        if(timerPowerUpGemaVerde >= tiempoParaPowerUpGemaVerde){
            //Power Up
            velocidadOscuridad = velocidadOscVerde;
            velocidad = velocidadVerde;

            //Set original values
            powerupActive = true;
            boolPowerUpGemaVerde = false;
            gemaVerde.inactive();
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaVerde = 0;
        }
    }

    private void powerUpGemaRoja(){

        timerPowerUpGemaRoja += Gdx.graphics.getDeltaTime();
        if(timerPowerUpGemaRoja >= tiempoParaPowerUpGemaRoja){

            //TODO: Red Power Up Stuff

            powerupActive = true;
            boolPowerUpGemaRoja = false;
            gemaRoja.inactive();
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaRoja = 0;
        }
    }

    private void powerUpGemaAzul(){

        colisionLumilFront = false;
        colisionLumilUp = false;
        colisionLumilDown = false;

        timerPowerUpGemaAzul+= Gdx.graphics.getDeltaTime();
        if(timerPowerUpGemaAzul >= tiempoParaPowerUpGemaAzul){

            //TODO: Blue Power Up Stuff

            powerupActive = true;
            boolPowerUpGemaAzul = false;
            gemaRoja.inactive();
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaAzul = 0;
        }
    }

    private void moverOscuridad(float delta){
            oscuridad.mover(velocidad, velocidadOscuridad, delta);
    }

    private void moverHijoOscuridad(float delta){
        for (HijoOscuridad hijoOscuridad:arrHijosOscuridad) {
            hijoOscuridad.mover(velocidad, velocidadHijosOscuridad,delta);
        }

        //hijoOscuridad.mover(velocidadBosque,velocidadHijosOsc, delta);
    }


    public void dibujarBarras(float delta){
        //Dibujar las barras
        switch (seccion) {
            case VERDE:
                distanciaRecorridaG += velocidad *delta;
                barraGS.renderAvance(distanciaRecorridaG,camara);
                break;
            case ROJO:
                distanciaRecorridaR += velocidad *delta;
                barraGS.renderEstatico(camara);
                barraRS.renderAvance(distanciaRecorridaR,camara);
                break;
            case AZUL:
                distanciaRecorridaB += velocidad *delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderAvance(distanciaRecorridaB,camara);
                break;
            case BLANCO:
                distanciaRecorridaW += velocidad *delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderEstatico(camara);
                if (distanciaRecorridaW>=velocidadBlanco*duracionBlanco){
                    barraWS.renderEstatico(camara);
                    estado = EstadoJuego.GANA;
                    //System.out.println("GANASTE!");
                }else{barraWS.renderAvance(distanciaRecorridaW,camara);}
                break;
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
        // Los assets se liberan a través del assetManager
       // AssetManager assetManager = juego.getAssetManager();
        manager.unload("Escenarios/bosque_fondo.jpg");
        manager.unload("Escenarios/bosque_atras.png");
        manager.unload("Escenarios/bosque_medio.png");
        manager.unload("Escenarios/bosque_frente.png");
        manager.unload("BotonesGemas/gemaVerde_ON.png");
        manager.unload("BotonesGemas/gemaVerde_OFF.png");
        manager.unload("BotonesGemas/gemaRoja_ON.png");
        manager.unload("BotonesGemas/gemaRoja_OFF.png");
        manager.unload("BotonesGemas/gemaAzul_ON.png");
        manager.unload("BotonesGemas/gemaAzul_OFF.png");
        manager.unload("Botones/pausa_ON.png");
        manager.unload("Botones/pausa_OFF.png");
        manager.unload("Utileria/atrasBarraAvance.png");
        manager.unload("Utileria/brilloLumil.png");
        manager.unload("Utileria/vida.png");
        manager.unload("Personajes/Lumil_Sprites.png");
        manager.unload("Personajes/lumilG.png");
        manager.unload("Personajes/lumilR.png");
        manager.unload("Personajes/lumilB.png");
        manager.unload("Personajes/oscuridad.png");
        manager.unload("Personajes/HijoOsc_sprite.png");
        manager.unload("Personajes/bloque.png");
        manager.unload("Personajes/Esgrun.png");
        manager.unload("Personajes/Rojel.png");
        manager.unload("Personajes/Shiblu.png");
        manager.unload("Personajes/luzPersonaje.png");
        manager.unload("Sonidos/sonidoComeOscuridad.wav");
        manager.unload("Sonidos/sonidoPoderActivado.wav");
        manager.unload("Sonidos/sonidoquitavidas.wav");
        manager.unload("Sonidos/sonidoTocaLuzBlanca.wav");
        manager.unload("Sonidos/musicaJuego.mp3");
        manager.unload("Sonidos/musicaJugar.ogg");

        texturaLumilJugando.dispose();
        texturaOscuridad.dispose();
        texturaHijoOscuridad.dispose();
        arrBloques.clear();
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
                velocidad =0;
                velocidadOscuridad =0;
                //Creo que esto puede sacarse del touch Down y estar junto a los otros estados.

                //Codigo Ricardo
                /*
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
                 */

            }else {

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x>=ANCHO/4) {
                    isMoving = true;
                    //System.out.println(posicionDedo);


                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaVerde &&  powerups && powerupActive ){
                    gemaVerde.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaVerde = true;
                    powerupActive = false;

                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaRoja && powerups && powerupActive) {
                    gemaRoja.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaRoja = true;
                    powerupActive = false;

                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaAzul &&  powerups && powerupActive) {
                    gemaAzul.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaAzul = true;
                    powerupActive = false;

                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x,posicionDedo.y)) {
                    botonPausa.active();
                    //sonidoPoderActivado.play();
                }


                /*Codigo Antiguo

                float anchoBack = texturaPausaON.getWidth();
                float altoBack = texturaPausaON.getHeight();
                float xBack = margen-20;
                float yBack = ALTO-margen- texturaPausaON.getHeight()+20;

                // Boton Gema Roja
                float anchoRoja = texturaGemaRojaOFF.getWidth();
                float altoRoja = texturaGemaRojaOFF.getHeight();
                float xRoja = texturaGemaRojaOFF.getWidth()-50;
                float yRoja = 20;

                // Boton Gema Verde
                float anchoVerde = texturaGemaVerdeON.getWidth();
                float altoVerde = texturaGemaVerdeON.getHeight();
                float xVerde = texturaGemaVerdeON.getWidth();
                float yVerde = texturaGemaVerdeON.getHeight();

                // Boton Gema Azul
                float anchoAzul = texturaGemaAzulON.getWidth();
                float altoAzul = texturaGemaAzulON.getHeight();
                float xAzul = texturaGemaAzulON.getWidth()+50;
                float yAzul =20;

                //margen-20,ALTO-margen-texturaBack.getHeight()+20

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                // Vamos a verificar el botón de back
                Rectangle rectBack = new Rectangle(xBack, yBack, anchoBack, altoBack);

                // Vamos a verificar el botón de la gema roja
                Rectangle rectRoja = new Rectangle(xRoja, yRoja, anchoRoja, altoRoja);

                // Vamos a verificar el botón de la gema verde
                Rectangle rectVerde = new Rectangle(xVerde, yVerde, anchoVerde, altoVerde);

                // Vamos a verificar el botón de la gema azul
                Rectangle rectAzul = new Rectangle(xAzul, yAzul, anchoAzul, altoAzul);

                /*

                    /*Antiguo de Gemas

                 // a partir del Else if se van a poner los rectangulos de los botones para detectarlos.
                }else if (rectBack.contains(posicionDedo.x,posicionDedo.y)) {
                    boolBack = true;
                    //juego.setScreen(new PantallaPausa(juego));
                }else if (rectRoja.contains(posicionDedo.x,posicionDedo.y)) {
                    boolRojo = true;
                   // System.out.println("HABILIDAD ROJA");
                }else if (rectVerde.contains(posicionDedo.x,posicionDedo.y)) {
                    boolVerde = true;
                    //System.out.println("HABILIDAD VERDE");
                }
                else if (rectAzul.contains(posicionDedo.x,posicionDedo.y)) {
                    boolAzul = true;
                    //System.out.println("HABILIDAD AZUL");
                }

                     */
            }

            return true; //Porque el juego ya procesó el evento (si si hacemos algo hay que regresar TRUE)
        }

        // se ejecuta justo cuando el usuario quita el dedo de la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(estado==EstadoJuego.PIERDE){
                velocidad =0;
                velocidadOscuridad =0;
                //Creo que esto puede sacarse del touch Down y estar junto a los otros estados.

                //Codigo Ricardo
                /*
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
                 */

            } /*else {


                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x>=ANCHO/4) {
                    //isMoving = true;

                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    //gemaVerde.active();
                    //System.out.println("Poder Verde");
                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    //gemaRoja.active();
                    //System.out.println("Poder Rojo");
                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    //gemaAzul.active();
                    //System.out.println("Poder Azul");
                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x,posicionDedo.y)) {
                    juego.setScreen(new PantallaPausa(juego));
                }

                /*
                float anchoBack = texturaPausaON.getWidth();
                float altoBack = texturaPausaON.getHeight();
                float xBack = margen-20;
                float yBack = ALTO-margen- texturaPausaON.getHeight()+20;

                // Boton Gema Roja
                float anchoRoja = texturaGemaRojaOFF.getWidth();
                float altoRoja = texturaGemaRojaOFF.getHeight();
                float xRoja = texturaGemaRojaOFF.getWidth()-50;
                float yRoja = 20;

                // Boton Gema Verde
                float anchoVerde = texturaGemaVerdeON.getWidth();
                float altoVerde = texturaGemaVerdeON.getHeight();
                float xVerde = texturaGemaVerdeON.getWidth();
                float yVerde = texturaGemaVerdeON.getHeight();

                // Boton Gema Azul
                float anchoAzul = texturaGemaAzulON.getWidth();
                float altoAzul = texturaGemaAzulON.getHeight();
                float xAzul = texturaGemaAzulON.getWidth()+50;
                float yAzul =20;

                //margen-20,ALTO-margen-texturaBack.getHeight()+20

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                // Vamos a verificar el botón de back
                Rectangle rectBack = new Rectangle(xBack, yBack, anchoBack, altoBack);

                // Vamos a verificar el botón de la gema roja
                Rectangle rectRoja = new Rectangle(xRoja, yRoja, anchoRoja, altoRoja);

                // Vamos a verificar el botón de la gema verde
                Rectangle rectVerde = new Rectangle(xVerde, yVerde, anchoVerde, altoVerde);

                // Vamos a verificar el botón de la gema azul
                Rectangle rectAzul = new Rectangle(xAzul, yAzul, anchoAzul, altoAzul);

                */

                    /* Codigo Antiguo
                    // a partir del Else if se van a poner los rectangulos de los botones para detectarlos.
                }else if (rectBack.contains(posicionDedo.x,posicionDedo.y)) {
                    boolBack = true;
                    juego.setScreen(new PantallaPausa(juego));
                }else if (rectRoja.contains(posicionDedo.x,posicionDedo.y)) {
                    boolRojo = true;
                    System.out.println("HABILIDAD ROJA");
                }else if (rectVerde.contains(posicionDedo.x,posicionDedo.y)) {
                    boolVerde = true;
                    System.out.println("HABILIDAD VERDE");
                }
                else if (rectAzul.contains(posicionDedo.x,posicionDedo.y)) {
                    boolAzul = true;
                    System.out.println("HABILIDAD AZUL");
                }


            } */

            isMoving = false;

            return true;

        }

        //Cuando arrastro el dedo por la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {

            if(estado==EstadoJuego.PIERDE){
                velocidad =0;
                velocidadOscuridad =0;
                //Creo que esto puede sacarse del touch Down y estar junto a los otros estados.

                //Codigo Ricardo
                /*
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
                 */

            }/*else {

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x>=ANCHO/4) {
                    isMoving = true;
                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    gemaVerde.active();
                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    gemaRoja.active();
                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x,posicionDedo.y)) {
                    gemaAzul.active();
                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x,posicionDedo.y)) {
                    botonPausa.active();
                } else {
                    gemaVerde.inactive();
                    gemaRoja.inactive();
                    gemaAzul.inactive();
                    botonPausa.inactive();
                }

                /* Codigo Antiguo
                float anchoBack = texturaPausaON.getWidth();
                float altoBack = texturaPausaON.getHeight();
                float xBack = margen-20;
                float yBack = ALTO-margen- texturaPausaON.getHeight()+20;

                // Boton Gema Roja
                float anchoRoja = texturaGemaRojaOFF.getWidth();
                float altoRoja = texturaGemaRojaOFF.getHeight();
                float xRoja = texturaGemaRojaOFF.getWidth()-50;
                float yRoja = 20;

                // Boton Gema Verde
                float anchoVerde = texturaGemaVerdeON.getWidth();
                float altoVerde = texturaGemaVerdeON.getHeight();
                float xVerde = texturaGemaVerdeON.getWidth();
                float yVerde = texturaGemaVerdeON.getHeight();

                // Boton Gema Azul
                float anchoAzul = texturaGemaAzulON.getWidth();
                float altoAzul = texturaGemaAzulON.getHeight();
                float xAzul = texturaGemaAzulON.getWidth()+50;
                float yAzul =20;

                //margen-20,ALTO-margen-texturaBack.getHeight()+20

                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                // Vamos a verificar el botón de back
                Rectangle rectBack = new Rectangle(xBack, yBack, anchoBack, altoBack);

                // Vamos a verificar el botón de la gema roja
                Rectangle rectRoja = new Rectangle(xRoja, yRoja, anchoRoja, altoRoja);

                // Vamos a verificar el botón de la gema verde
                Rectangle rectVerde = new Rectangle(xVerde, yVerde, anchoVerde, altoVerde);

                // Vamos a verificar el botón de la gema azul
                Rectangle rectAzul = new Rectangle(xAzul, yAzul, anchoAzul, altoAzul);

                 */

                    /* Codigo Antiguo
                    // a partir del Else if se van a poner los rectangulos de los botones para detectarlos.
                }else if (rectBack.contains(posicionDedo.x,posicionDedo.y)) {
                    boolBack = true;
                   // juego.setScreen(new PantallaPausa(juego));
                }else if (rectRoja.contains(posicionDedo.x,posicionDedo.y)) {
                    boolRojo = true;
                   // System.out.println("HABILIDAD ROJA");
                }else if (rectVerde.contains(posicionDedo.x,posicionDedo.y)) {
                    boolVerde = true;
                    //System.out.println("HABILIDAD VERDE");
                }
                else if (rectAzul.contains(posicionDedo.x,posicionDedo.y)) {
                    boolAzul = true;
                    //System.out.println("HABILIDAD AZUL");
                }else{
                    boolBack = false;
                    boolRojo = false;
                    boolVerde = false;
                    boolAzul = false;
                }


            } */

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
