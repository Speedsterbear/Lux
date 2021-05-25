package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
// Clase que implementa la pantalla cuando el jugador gana
// Autor: Carlos, Andrea, David

public class PantallaGana extends Pantalla{

    private Lux juego;

    //Fondo de bosque en movimiento
    private Texture bosqueFondoDia;
    private Texture bosqueAtrasDia;
    private Texture bosqueMedioDia;
    private Texture bosqueFrenteDia;
   // private Texture musicaPantallasSecundarias;
    private FondoEnMovimiento bosqueDia;

    //Personaje principal: Lumil
    private Lumil lumil;
    private Texture texturaLumilJugando;
    float tiempoLumil = 0f; //Acumulador
    private float DELTA_Y = 2; //Avance vertical de lumil
    private final float duracionFrameLumil = 1/8f; //Duración en segundos  inicial de los frames de la animación d Lumil
    private Texture texturaBrillo;
    private BrilloLumil brilloLumil;
    private float alturaLumil = ALTO/6; // Representa la altura que se le sumará a ANCHO/2 para que lumil suba y baje
    private float contadorDireccionLumil = 0; //Cuenta el tiempo que lumil esperará para cambiar de posicion.
    private final float ESPERA_CAMBIO_DIRECCION = 3; //Segundos que espera lumil para cambiar de direccion

    private Stage escenaGana;

    //Fade a Blanco
    private ShapeRenderer rectangulo;
    private float alfaRectangulo = 1;
    private Transicion transicionBlanca;

    //Mensaje Victoria
    private Texture mensajeGanar; //Textura que muestra el mensaje de que se ganó el juego

    // AssetManager
   // private AssetManager manager;

    public PantallaGana(Lux juego) {
        this.juego=juego;
     //   manager = juego.getAssetManager();
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick= new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        crearFondoDia();
        crearGana();
        crearPersonaje();
        crearBrillo();
        crearRectangulo();
        crearMensaje();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearMensaje() {
        mensajeGanar = new Texture("PantallaGanar/mensajeGanar.png");
    }

    private void crearRectangulo() {
        rectangulo = new ShapeRenderer();
        //rectangulo.setColor(1,1,1,1);
        transicionBlanca = new Transicion(1,1,1,1.2f,ALTO,ANCHO);
    }

    //Aqui se crea el fondo similar a la pantalla de juego
    private void crearFondoDia() {
        bosqueFondoDia = new Texture("Escenarios/fondo_dia.jpg");
        bosqueAtrasDia = new Texture("Escenarios/dia_atras.png");
        bosqueMedioDia = new Texture("Escenarios/dia_medio.png");
        bosqueFrenteDia =new Texture("Escenarios/dia_frente.png");
        bosqueDia = new FondoEnMovimiento(bosqueFondoDia,bosqueAtrasDia,bosqueMedioDia,bosqueFrenteDia);
    }

    private void crearPersonaje() {
        //Personaje principal: Lumil (Blanco)
        texturaLumilJugando = new Texture ("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(texturaLumilJugando,ANCHO/4, ALTO/2,4,1,duracionFrameLumil,1);
    }

    private void crearBrillo() {
        texturaBrillo = new Texture("Utileria/brilloLumil.png");
        brilloLumil = new BrilloLumil(texturaBrillo,ANCHO/4+(texturaBrillo.getWidth()/6),ALTO/2);

    }
    private void crearGana() {
        final Music musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundarias.ogg"));

        //musicaPantallasSecundarias.setLooping(true);
        //musicaPantallasSecundarias.setVolume(0.2f);
        //musicaPantallasSecundarias.play();

        escenaGana = new Stage(vista);

        Button btnExit = crearBoton("Botones/btnHome_OFF.png","Botones/btnHome_ON.png");
        btnExit.setPosition(3*ANCHO/4, 160, Align.center);
        escenaGana.addActor(btnExit);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicaPantallasSecundarias.stop();
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaGana);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
        }

        actualizar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //Dibujar elementos del bosque
        bosqueDia.movSeccionesCompletas(200, delta, batch, true);
        //Dibujar a Lumil
        brilloLumil.render(batch);
        lumil.animationRender(batch, tiempoLumil);

        //Dibujar Mensjae de Ganar
        batch.draw(mensajeGanar,ANCHO/2,0);

        batch.end();

        escenaGana.draw();
        if (tiempoLumil<=2.5f) {
            if (tiempoLumil>=1){
                transicionBlanca.fadeIn(delta,1);
            }
            transicionBlanca.render(camara);
        }


        /*
        //Hacer el efecto de Fade de entrada
        if (tiempoLumil<=2.5f){
            renderFadeInicial(tiempoLumil);
        }
        */

    }

    /* Codigo Antiguo

    private void renderFadeInicial(float tiempoLumil) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rectangulo.setProjectionMatrix(camara.combined);
        rectangulo.setColor(1, 1, 1, alfaRectangulo);
        rectangulo.begin(ShapeRenderer.ShapeType.Filled);
        rectangulo.box(0,0,0,ANCHO,ALTO,0);

        if (tiempoLumil>=1){
            alfaRectangulo -= (Gdx.graphics.getDeltaTime()/1.5f);
            if (alfaRectangulo<=0){
                alfaRectangulo = 0;
            }
            //System.out.println(alfaRectangulo);
        }

        rectangulo.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }
    */

    private void actualizar(float delta) {
        //Tiempo que pasó entre render.
        tiempoLumil += Gdx.graphics.getDeltaTime();
        brilloLumil.actualizar(1,1,1,0.9f);
        moverPersonaje(delta);

    }

    private void moverPersonaje(float delta) {
        contadorDireccionLumil += Gdx.graphics.getDeltaTime();//incrementar el contador
        //Invierte la el signo para que cambie de dirección
        if ((lumil.sprite.getY()+(lumil.sprite.getHeight()/2)==alturaLumil+(ALTO/2))&&contadorDireccionLumil>=ESPERA_CAMBIO_DIRECCION) {
            alturaLumil = -alturaLumil;
            contadorDireccionLumil=0; //Resetear el contador
            System.out.println(alturaLumil);
        }
        lumil.mover(DELTA_Y,(ALTO/2)+alturaLumil);
        brilloLumil.mover(lumil.sprite.getY() + (lumil.sprite.getHeight() / 2));
        //System.out.println((ANCHO/2)+alturaLumil);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    bosqueAtrasDia.dispose();
    bosqueFondoDia.dispose();
    bosqueFrenteDia.dispose();
    bosqueMedioDia.dispose();
    texturaBrillo.dispose();
    texturaLumilJugando.dispose();
    mensajeGanar.dispose();
    }
}

