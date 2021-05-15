package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static mx.xul.game.Pantalla.ALTO;
import static mx.xul.game.Pantalla.ANCHO;

/**
 * Pantalla intermedia entre el menú y el juego
 *
 * @author Andrea Espinosa Azuela
 */
public class PantallaCargando extends Pantalla {
    private Lux juego;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Imagen cargando
    //private Texture texturaCargando;
    private Texture texturaCarga;
    //private Sprite spriteCargando;
    private BarraAvance barraAvance;
    private float margen=80; // Margen de las barras

    private Pantallasenum siguientePantalla;
    private int avance; // % de carga

    // AssetManager
    private AssetManager manager;

    //private AssetManager assetManager;  // Asset manager principal

    public PantallaCargando(Lux juego, Pantallasenum siguientePantalla) {
        this.juego = juego;
        manager = juego.getAssetManager();
        this.siguientePantalla = siguientePantalla;
    }

    @Override
    public void show() {
        // Crea la cámara con las dimensiones del mundo
        camara = new OrthographicCamera(ANCHO, ALTO);
        // En el centro de la pantalla. (x,y) de la cámara en el centro geométrico
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        // La vista que escala los elementos gráficos
        vista = new StretchViewport(ANCHO, ALTO, camara);
        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
        crearBarra();

        // Cargar recursos
        /*
        texturaCargando = new Texture(Gdx.files.internal("cargando.png"));
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO / 2 - spriteCargando.getWidth() / 2,
                ALTO / 2 - spriteCargando.getHeight() / 2);

         */

        cargarRecursosSigPantalla();

        /*
        cargarRecursosJuego();
        cargarRecursosAyuda();
        cargarRecursosGana();
        cargarRecursosNosotros();
        cargarRecursosSigPantalla();
        cargarRecursosHistoria();

         */

    }

    private void crearBarra() {
        barraAvance = new BarraAvance(1,1,1,0.5f,margen,ALTO/2,15,ANCHO-(2*margen),120);
        texturaCarga = new Texture("PantallaCargando/SiluetaBarra.png");
    }

    private void cargarRecursosHistoria() {
        manager.load("PantallaHistoria/mensaje_inicial.png", Texture.class);
        manager.load("PantallaHistoria/Humo.png", Texture.class);
        manager.load("PantallaHistoria/fondo_humo.jpg", Texture.class);
        manager.load("PantallaHistoria/Letras_1.png", Texture.class);
        manager.load("PantallaHistoria/Letras_2.png", Texture.class);
        manager.load("PantallaHistoria/Letras_3.png", Texture.class);
        manager.load("PantallaHistoria/Letras_4.png", Texture.class);
        manager.load("PantallaHistoria/Letras_5.png", Texture.class);
        manager.load("PantallaHistoria/Letras_6.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No1.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No2.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No3.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No4.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No5.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No6.png", Texture.class);
    }

    private void cargarRecursosSigPantalla() {
        manager = juego.getAssetManager();
        avance = 0;
        switch (siguientePantalla) {
            case JUEGOGS:
                cargarRecursosJuego();
                break;
            case PANTALLAAYUDA:
                cargarRecursosAyuda();
                break;
            case PANTALLAGANA:
                cargarRecursosGana();
                break;
            case PANTALLANOSOTROS:
                cargarRecursosNosotros();
                break;
            case PANTALLAHISTORIA:
                cargarRecursosHistoria();
                break;
        }
    }

    private void cargarRecursosNosotros() {
        manager.load("Nosotros/Androidst.png", Texture.class);
        manager.load("Nosotros/fondoAbout.jpg", Texture.class);
        manager.load("Menu/buttonback.png", Texture.class);
        manager.load("Menu/clickback.png", Texture.class);
        manager.load("Nosotros/flechaDerOff.png", Texture.class);
        manager.load("Nosotros/flechaDerOn.png", Texture.class);
        manager.load("Nosotros/flechaIzqOff.png", Texture.class);
        manager.load("Nosotros/flechaIzqOn.png", Texture.class);
        manager.load("Nosotros/flechaArrOff.png", Texture.class);
        manager.load("Nosotros/flechaArrOn.png", Texture.class);
        manager.load("Nosotros/Cristales-W.png", Texture.class);
        manager.load("Nosotros/Cristales-G.png", Texture.class);
        manager.load("Nosotros/Cristales-R.png", Texture.class);
        manager.load("Nosotros/Cristales-B.png", Texture.class);
        manager.load("Nosotros/Cristales-Y.png", Texture.class);
        manager.load("Nosotros/Cristales-C.png", Texture.class);
        manager.load("Nosotros/andrea.png", Texture.class);
        manager.load("Nosotros/carlos.png", Texture.class);
        manager.load("Nosotros/david.png", Texture.class);
        manager.load("Nosotros/eduardo.png", Texture.class);
        manager.load("Nosotros/ricardo.png", Texture.class);
        manager.load("Nosotros/CasoInicial.png", Texture.class);
    }

    private void cargarRecursosGana() {
        manager.load("PantallaGanar/mensajeGanar.png", Texture.class);
        manager.load("Escenarios/fondo_dia.jpg", Texture.class);
        manager.load("Escenarios/dia_atras.png", Texture.class);
        manager.load("Escenarios/dia_medio.png", Texture.class);
        manager.load("Escenarios/dia_frente.png", Texture.class);
        manager.load("Personajes/Lumil_Sprites.png", Texture.class);
        manager.load("Utileria/brilloLumil.png", Texture.class);
        manager.load("Botones/btnHome_OFF.png", Texture.class);
        manager.load("Botones/btnHome_ON.png", Texture.class);

    }

    private void cargarRecursosAyuda() {
        manager.load("Menu/Fondo_Montana.jpg", Texture.class);    // Cargar imagen ded fondo
        manager.load("Botones/LumilB.png", Texture.class);
        manager.load("Botones/OscuridaB.png", Texture.class);
        manager.load("Botones/btnhijos.png", Texture.class);
        manager.load("Botones/PrimariosB.png", Texture.class);
        manager.load("Menu/buttonback.png", Texture.class);
        manager.load("Menu/clickback.png", Texture.class);
        manager.load("Menu/fondo.jpg", Texture.class);
    }

    // Carga los recursos a través del administrador de assets (siguiente pantalla)
    private void cargarRecursosJuego() {
        manager.load("Escenarios/bosque_fondo.jpg", Texture.class);
        manager.load("Escenarios/bosque_atras.png", Texture.class);
        manager.load("Escenarios/bosque_medio.png", Texture.class);
        manager.load("Escenarios/bosque_frente.png", Texture.class);
        manager.load("BotonesGemas/gemaVerde_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaVerde_OFF.png", Texture.class);
        manager.load("BotonesGemas/gemaRoja_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaRoja_OFF.png", Texture.class);
        manager.load("BotonesGemas/gemaAzul_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaAzul_OFF.png", Texture.class);
        manager.load("Botones/pausa_ON.png", Texture.class);
        manager.load("Botones/pausa_OFF.png", Texture.class);
        manager.load("Utileria/atrasBarraAvance.png", Texture.class);
        manager.load("Utileria/brilloLumil.png", Texture.class);
        manager.load("Utileria/vida.png", Texture.class);
        manager.load("Personajes/Lumil_Sprites.png", Texture.class);
        manager.load("Personajes/lumilG.png", Texture.class);
        manager.load("Personajes/lumilR.png", Texture.class);
        manager.load("Personajes/lumilB.png", Texture.class);
        manager.load("Personajes/oscuridad.png", Texture.class);
        manager.load("Personajes/HijoOsc_sprite.png", Texture.class);
        manager.load("Personajes/bloque.png", Texture.class);
        manager.load("Personajes/Esgrun.png", Texture.class);
        manager.load("Personajes/Rojel.png", Texture.class);
        manager.load("Personajes/Shiblu.png", Texture.class);
        manager.load("Sonidos/coin.wav", Sound.class);
    }

    @Override
    public void render(float delta) {
        // Actualizar carga
        actualizar();


        // Dibujar
        borrarPantalla();
        //spriteCargando.setRotation(spriteCargando.getRotation() + 15);

        //Dibujar Barra
        barraAvance.renderAvanceCarga(avance*1.2f,camara);

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();
        batch.draw(texturaCarga,(margen+(avance*1.2f*(ANCHO-(margen*2))/120))-(texturaCarga.getWidth()/2)-20,(ALTO/2)-(texturaCarga.getHeight()/2));
        //spriteCargando.draw(batch);
        batch.end();

        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if (manager.update()) { // Terminó?
            switch (siguientePantalla) {
                case JUEGOGS:
                    juego.setScreen(new JuegoGS(juego));   // 100% de carga
                    break;
                case PANTALLAAYUDA:
                    juego.setScreen(new PantallaAyuda(juego));   // 100% de carga
                    break;
                case PANTALLAGANA:
                    juego.setScreen(new PantallaGana(juego));
                    break;
                case PANTALLANOSOTROS:
                    juego.setScreen(new PantallaNosotros(juego));
                    break;
                case PANTALLAHISTORIA:
                    juego.setScreen(new PantallaHistoria(juego));
                    break;
            }
        }
        avance = (int) (manager.getProgress() * 100);
    }



   // private void borrarPantalla() {
      //  Gdx.gl.glClearColor(0,0,0,0);    // r, g, b, alpha
       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    //}

    private void actualizar() {

        if (manager.update()==false) {
            // Aún no termina la carga de assets, leer el avance
            float avance = manager.getProgress()*100;
            Gdx.app.log("Cargando",avance+"%");
        }

        /*
        if (manager.update()) {
            // Terminó la carga, cambiar de pantalla
            //juego.setScreen(new JuegoGS(juego));
        } else {
            // Aún no termina la carga de assets, leer el avance
            float avance = manager.getProgress()*100;
            Gdx.app.log("Cargando",avance+"%");
        }
        */
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //texturaCargando.dispose();
        texturaCarga.dispose();
        // Los assets de PantallaJuego se liberan en el método dispose de PantallaJuego
    }
}