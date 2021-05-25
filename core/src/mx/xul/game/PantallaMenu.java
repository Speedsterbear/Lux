package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaMenu extends Pantalla {
    private Lux juego;
    private Texture texturafondo;
    private Stage escenaMenu;

    //Para el Fade
    private Transicion fadeNegro;
    private float tiempoFadeIn = 0; //Acumulador para controlar el fade in
    private boolean isTransicionFadingOut = false; //Al volverse true, se activa el fade out

    //Para pasar a la siguiente pantalla
    private PantallaSiguienteMenu pantallaSiguienteMenu = PantallaSiguienteMenu.PLAY;

   // private Music musicaPantallasSecundarias;
    //private final Music musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundarias.mp3"));
/*
    //Musica
    private Music musicaPantallasSecundarias;
    private Music musicaPantallasSecundariasIntro;

 */
   private boolean musicaON = true;

    public PantallaMenu(Lux juego) {
        this.juego=juego;

       // Gdx.app.log("Juego:", String.valueOf(juego));
    }

    @Override
    public void show() {
        crearMenu();

        //Fade
        fadeNegro = new Transicion(0,0,0,1,ALTO,ANCHO);
        Gdx.input.setCatchKey(Input.Keys.BACK,false); // Si regresa al sistema operativo
    }

    private void crearMenu() {

        //Fondo
        texturafondo= new Texture("Menu/fondo.jpg");

        // Escena
        escenaMenu=new Stage(vista);

        //Musica
       // Leer de las preferencias si la musica quedo prendida o apagada
        // Si el valo leído es musica prendida entonces se ejecuta...
        juego.playMusica();
        /*

        musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundarias.ogg"));
        musicaPantallasSecundarias.setLooping(true);
        musicaPantallasSecundarias.setVolume(0.7f);

        musicaPantallasSecundariasIntro = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundariasIntro.ogg"));
        musicaPantallasSecundariasIntro.setVolume(0.7f);
        musicaPantallasSecundariasIntro.play();

         */

        //musicaPantallasSecundarias.setLooping(false);


        // Actores (botones)
        Button btnJuego = crearBoton("Menu/buttonjuego.png", "Menu/clickjuego.png");
        btnJuego.setPosition(2*ANCHO/3,1.5f*ALTO/2, Align.center);
        btnJuego.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaJuego(juego));
                /*
                musicaPantallasSecundarias.stop();
                musicaPantallasSecundariasIntro.stop();
                 */
                //juego.stopMusica();
                //juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.PLAY;
            }
        });


        Button btnNosotros = crearBoton("Menu/buttonnosotros.png","Menu/clicknosotros.png");
        btnNosotros.setPosition(2*ANCHO/3,1.2f*ALTO/2.5f, Align.center);
        // Registrar el evento click para el boton
        btnNosotros.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLANOSOTROS));
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.ABOUT_US;
            }
        });

        Button btnAyuda= crearBoton("Menu/buttonayuda.png", "Menu/clickayuda.png");
        btnAyuda.setPosition(2*ANCHO/3,0.8f*ALTO/4, Align.center);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.HELP;
            }
        });

        Texture texturaON = new Texture("Botones/VolumeON.png");
        Texture texturaOFF = new Texture("Botones/volumeOFF.png");
        TextureRegionDrawable trdON = new TextureRegionDrawable(texturaON);
        TextureRegionDrawable trdOFF =  new TextureRegionDrawable(texturaOFF);

        Button.ButtonStyle estiloON = new Button.ButtonStyle(trdON, trdOFF, trdOFF);
        // Button.ButtonStyle estiloOFF = new Button.ButtonStyle(trdOFF, trdON, trdON);

        final Button btnVolumen = crearBoton("Botones/volumeON.png", "Botones/volumeOFF.png");
        btnVolumen.setStyle(estiloON);
        btnVolumen.setPosition(9.7f*ANCHO/10.2f, ALTO/1.1f, Align.center);
        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
                // isTransicionFadingOut = true;
                // pantallaSiguienteMenu = PantallaSiguienteMenu.HELP;
                if (musicaON == true){ // Si la musica esta prendida
                    juego.stopMusica();
                    musicaON = false;
                }else { // La musica esta apagada
                    juego.playMusica();
                    musicaON = true;

                }
                // Guardar en las preferencias la variable musicaON
            }
            //musicaON
        });

        // Agregar a la escena el boton
        escenaMenu.addActor(btnJuego);
        escenaMenu.addActor(btnNosotros);
        escenaMenu.addActor(btnAyuda);
        escenaMenu.addActor(btnVolumen);
        // escenaMenu.addActor(btnVolumenOFF);

        // Escena se encarga de atender los eventos de entrada
        Gdx.input.setInputProcessor(escenaMenu);
    }


    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    // Se ejecuta automaticamente
    // delta es el tiempo que ha transcurrido el frame
    @Override
    public void render(float delta) {

        //Fade in
        if (tiempoFadeIn<=0.3f){
            tiempoFadeIn+=delta;
            fadeNegro.fadeIn(delta,0.3f);
        }

        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.end();

        // Escena despues del fondo
        escenaMenu.draw();

        if (isTransicionFadingOut){
            fadeNegro.fadeOut(delta,0.5f);
            if (pantallaSiguienteMenu==PantallaSiguienteMenu.PLAY){
                juego.fadeOutMusica();
            }
        }

        fadeNegro.render(camara);
        if (fadeNegro.isFadeOutFinished){
            switch (pantallaSiguienteMenu){
                case PLAY:
                    juego.stopMusica();
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
                    break;
                case ABOUT_US:
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLANOSOTROS));
                    break;
                case HELP:
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
                    break;
            }
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


    }

    private enum PantallaSiguienteMenu
    {
        PLAY,
        ABOUT_US,
        HELP
    }

}


