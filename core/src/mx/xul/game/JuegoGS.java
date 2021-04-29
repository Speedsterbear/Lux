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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.TimeUtils;
//import com.sun.org.apache.xpath.internal.objects.XBoolean;


public class JuegoGS extends Pantalla {

    private Lux juego;

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
    private final float velocidadOscRojo = velocidadRojo+15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 2.
    private final float velocidadOscAzul = velocidadAzul+15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 3.
    private final float velocidadOscBlanco = velocidadBlanco+15; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 4.

    //Velocidad normal de los hijos de la oscuridad según las secciones
    private final float velocidadHijoOscVerde = velocidadVerde+200; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 1.
    private final float velocidadHijoOscRojo = velocidadRojo+35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 2.
    private final float velocidadHijoOscAzul = velocidadAzul+35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 3.
    private final float velocidadHijoOscBlanco = velocidadBlanco+35; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 4.


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
    private final float velocidadInicialBosque = 300;
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
    private float velocidadOsc = velocidadOscVerde;//Velocidad Actual de la oscuridad
    private float velocidadHijosOsc = velocidadHijoOscVerde;//Velocidad Actual de hijos de la oscuridad
    private float positionXStart = -900;
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

    //Esgrun
    private Esgrun esgrun, rojel, shiblu;
    private float DX_PASO_ESGRUN=2.5f;
    public static float aparicion = 0;

    //Hijo de Oscuridad: Del tipo que quita vidas (esto no se esta usando por el momento)
    //private  HijoOscuridad hijoOscuridad;
    //private Texture texturaHijoOscuridad;
    //private float DX_PASO_HIJOOSCURIDAD=150;
    //private float timerCrearHijoOscuridad= 0;//Acumulador
    //private float tiempoParaCrearHijoOscuridad = 2; //Se espera esos segundos en crear el bloque.

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

    //Secciones
    private EstadoSeccion seccion = EstadoSeccion.VERDE;

    //Botones
    private Texture texturaBack;//Botón de Regreso
    private Texture texturaGemaRoja;
    private Texture texturaGemaAzul;
    private Texture texturaGemaVerde;


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
        crearBloques();
        crearBarra();
        crearBotones();

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

        Button btnGemaAzul = crearBoton("BotonesGemas/Gema_Azul_OFF.png", "BotonesGemas/Gema_Azul_ON.png");
        btnGemaAzul.setPosition(160,ALTO-80, Align.center);
        btnGemaAzul.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Se activa el poder azul
                System.out.println("HABILIDAD AZUL");
            }
        });

        Button btnGemaRoja = crearBoton("BotonesGemas/Gema_Roja_OFF.png", "BotonesGemas/Gema_Roja_ON.png");
        btnGemaRoja.setPosition(160,ALTO-80, Align.center);
        btnGemaRoja.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Se activa el poder rojo
                System.out.println("HABILIDAD ROJA");
            }
        });

        Button btnGemaVerde = crearBoton("BotonesGemas/Gema_Verde_OFF.png", "BotonesGemas/Gema_Verde_ON.png");
        btnGemaVerde.setPosition(160,ALTO-80, Align.center);
        btnGemaVerde.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Se activa el poder rojo
                System.out.println("HABILIDAD ROJA");
            }
        });

        // escenaJuego.addActor(btnRegresar);
        //Gdx.input.setInputProcessor(escenaJuego);

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());
    }

    private void crearBotones() {
        texturaBack= new Texture("Menu/buttonback.png");
        texturaGemaAzul= new Texture("BotonesGemas/Gema_Azul_ON.png");
        texturaGemaRoja= new Texture("BotonesGemas/Gema_Roja_ON.png");
        texturaGemaVerde= new Texture("BotonesGemas/Gema_Verde_ON.png");
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
        esgrun = new Esgrun(texturaRojel, ANCHO,positionY);
    }

    private void crearShiblu(){
        Texture texturaShiblu = new Texture("Personajes/Shiblu.png");
        esgrun = new Esgrun(texturaShiblu, ANCHO,positionY);
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
        bosque.movSeccionesCompletas(velocidadBosque, delta, batch, true);

        //Dibujar vidas
        vidas.vidasRender(contadorVidas, batch);

        //Dibujar personaje principal
        lumil.animationRender(batch, tiempoLumil);

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

        //Dibujar Esgrun
        if(esgrun!=null)
            esgrun.render(batch);

        //Dibujar enemigo oscuridad
        oscuridad.animationRender(batch, tiempoOsc);


        // Dibuja el marcador
        //texto.mostrarMensaje(batch, "SCORE", ANCHO / 2, ALTO - 25);

        //Botones
        //escenaJuego.draw();


        // Dibujar back
        batch.draw(texturaBack,margen-20,ALTO-margen-texturaBack.getHeight()+20);

        // Dibujar gemas
        batch.draw(texturaGemaAzul,texturaGemaAzul.getWidth()+50,20);
        batch.draw(texturaGemaRoja,texturaGemaRoja.getWidth()-50,20);
        batch.draw(texturaGemaVerde,texturaGemaVerde.getWidth(),texturaGemaVerde.getHeight());

        batch.end();

        distanciaRecorridaControl += velocidadBosque*delta;

        //Velocidad
        //Eso divide la pantalla en las secciones de cada color.
        //Para cambiar de seccion se debe cumpir la condición de la distancia y de que chocaste con el monito
        aparicion += delta;
        if (aparicion>= 20){
            seccion = EstadoSeccion.ROJO;
        }else if (aparicion>= 40){
            seccion = EstadoSeccion.AZUL;
        } else if(aparicion>= 60) {
            seccion = EstadoSeccion.BLANCO;
        }



        //Dibujar barra progreso
        dibujarBarras(delta);

    }

    private void actuaizar(float delta) {

        //Cuando la velocidad sea = 0, la oscuridad avanzará rápido por nuestro personaje.
        if (estado == EstadoJuego.JUGANDO) {

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

            boolean colisionLumil = hijoOscuridadColision();
            timerCrearBloque += delta;
            if (timerCrearBloque >= tiempoParaCrearBloque && colisionLumil == false) {
                timerCrearBloque = 0;
                crearBloques();
            }

            timerCrearHijoOscuridad += delta;
            if (timerCrearHijoOscuridad >= tiempoParaCrearHijoOscuridad) {
                timerCrearHijoOscuridad = 0;
                crearHijosOscuridad();
            }

            //Mover Lumil
            moverLumil(isMooving);

            //Mover Oscuridad
            moverOscuridad(delta);

            //Mover Hijo de Oscuridad (va afuera porque se sige moviendo aunque lúmil no se mueva, es como la oscuridad grande)
            moverHijoOscuridad(delta);

            //Mover y Depurar Oscuridad
            if (colisionLumil == false) {
                moverBloques(delta);
                returnVelocidadBosque();
            }

            if (colisionLumil) {
                velocidadBosque = 0;
                //moverOscuridad(delta);
                oscuridadColision();
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
            velocidadBosque = 0;
            velocidadOsc = 0;
            aparicion = 0;
            juego.setScreen(new PantallaPerdida(juego));
            //Aqui se llama la secuencia de final (o sea la pantalla de andrea)
        }

        if (estado == EstadoJuego.GANA) {
            velocidadBosque = 0;
            velocidadOsc = 0;
            juego.setScreen(new PantallaGana(juego));
            //Aqui se llama la secuencia de final (o sea la pantalla de andrea)
        }

    }

    private void returnVelocidadBosque(){
        velocidadBosque = velocidadInicialBosque;
    }

    private void depurarHijosOscuridad() {

        //Eliminar bloques Fuera del rango de la pantalla
        for (int  i=arrHijosOscuridad.size-1; i>=0; i--){
            HijoOscuridad hijoOscuridad = arrHijosOscuridad.get(i);
            if(arrHijosOscuridad!= null && lumil.sprite.getBoundingRectangle().overlaps(hijoOscuridad.sprite.getBoundingRectangle())) {
                arrHijosOscuridad.removeIndex(i);
                contadorVidas --;
            }
            if(arrHijosOscuridad!= null && hijoOscuridad.getX()>(3*ANCHO/2)) { //Logicamente necesito solo la X del objeto
                arrHijosOscuridad.removeIndex(i);
            }
        }

    }

    private void depurarEsgrun() {
        if(lumil.sprite.getBoundingRectangle().overlaps(esgrun.sprite.getBoundingRectangle())){

        }
    }

    private void depurarRojel() {
        if(lumil.sprite.getBoundingRectangle().overlaps(rojel.sprite.getBoundingRectangle())){

        }
    }

    private void depurarShiblu() {
        if(lumil.sprite.getBoundingRectangle().overlaps(shiblu.sprite.getBoundingRectangle())){

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

    private boolean hijoOscuridadColision(){
        for(Bloque bloque: arrBloques){
            if(lumil.sprite.getBoundingRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }

    private void oscuridadColision() {
        if(lumil.sprite.getBoundingRectangle().overlaps(oscuridad.sprite.getBoundingRectangle())){
            contadorVidas=0;//Muere =(
            Gdx.app.log("Vidas", Integer.toString(contadorVidas));
        }
    }

    private void moverEsgrun(){
        esgrun.moverHorizontal(DX_PASO_ESGRUN);
    }

    private void moverRojel(){
        rojel.moverHorizontal(DX_PASO_ESGRUN);
    }

    private void moverShiblu(){
        shiblu.moverHorizontal(DX_PASO_ESGRUN);
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

    private void moverOscuridad(float delta){
            oscuridad.mover(velocidadBosque,velocidadOsc, delta);
    }

    private void moverHijoOscuridad(float delta){
        for (HijoOscuridad hijoOscuridad:arrHijosOscuridad) {
            hijoOscuridad.mover(velocidadBosque,velocidadHijosOsc,delta);
        }

        //hijoOscuridad.mover(velocidadBosque,velocidadHijosOsc, delta);
    }


    public void dibujarBarras(float delta){
        //Dibujar las barras
        switch (seccion) {
            case VERDE:
                distanciaRecorridaG += velocidadBosque*delta;
                barraGS.renderAvance(distanciaRecorridaG,camara);
                break;
            case ROJO:
                distanciaRecorridaR +=velocidadBosque*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderAvance(distanciaRecorridaR,camara);
                break;
            case AZUL:
                distanciaRecorridaB +=velocidadBosque*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderAvance(distanciaRecorridaB,camara);
                break;
            case BLANCO:
                distanciaRecorridaW +=velocidadBosque*delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderEstatico(camara);
                if (distanciaRecorridaW>=velocidadBlanco*duracionBlanco){
                    barraWS.renderEstatico(camara);
                    estado = EstadoJuego.GANA;
                    System.out.println("GANASTE!");
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
                velocidadBosque=0;
                velocidadOsc=0;
                //Creo que esto puede sacarse del touch Down y estar junto a los otros estados.

                //Codigo Ricardo
                /*
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
                 */

            }else {
                float anchoBack = texturaBack.getWidth();
                float altoBack = texturaBack.getHeight();
                float xBack = margen-20;
                float yBack = ALTO-margen-texturaBack.getHeight()+20;

                // Boton Gema Roja
                float anchoRoja = texturaGemaRoja.getWidth();
                float altoRoja = texturaGemaRoja.getHeight();
                float xRoja = texturaGemaRoja.getWidth()-50;
                float yRoja = 20;

                // Boton Gema Verde
                float anchoVerde = texturaGemaVerde.getWidth();
                float altoVerde = texturaGemaVerde.getHeight();
                float xVerde = texturaGemaVerde.getWidth();
                float yVerde = texturaGemaVerde.getHeight();

                // Boton Gema Azul
                float anchoAzul = texturaGemaAzul.getWidth();
                float altoAzul = texturaGemaAzul.getHeight();
                float xAzul = texturaGemaAzul.getWidth()+50;
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


                if (posicionDedo.x>=ANCHO/4){
                    isMooving = true;
                 // a partir del Else if se van a poner los rectangulos de los botones para detectarlos.
                }else if (rectBack.contains(posicionDedo.x,posicionDedo.y)) {
                    juego.setScreen(new PantallaPausa(juego));
                }else if (rectRoja.contains(posicionDedo.x,posicionDedo.y)) {
                    System.out.println("HABILIDAD ROJA");
                }else if (rectVerde.contains(posicionDedo.x,posicionDedo.y)) {
                    System.out.println("HABILIDAD VERDE");
                }
                else if (rectAzul.contains(posicionDedo.x,posicionDedo.y)) {
                    System.out.println("HABILIDAD AZUL");
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
