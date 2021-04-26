package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

//import sun.security.util.AnchorCertificates;

public class PantallaJuego extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite Lumil;
    private Sprite Esgrun;
    private Sprite Rojel;
    private Sprite Shiblu;
    private Sprite gemaazul;
    private Sprite gemaroja;
    private Sprite gemaverde;
    private Sprite contadorgemas;
    private Sprite progress;
    private float dx=5;
    private float angulo=0;
    private float angulorotacion=-60;
    private float D_angulorotacion=1;
    private Lux juego;

    public PantallaJuego(Lux juego) {
        this.juego=juego;
    }

    private void crearJuego(){
        texturafondo= new Texture("Menu/Fondo_Montana.jpg");
        Lumil= new Sprite(new Texture("Personajes/Lumil.png"));
        Esgrun= new Sprite(new Texture("Personajes/Esgrun.png"));
        Rojel= new Sprite(new Texture("Personajes/Rojel.png"));
        Shiblu= new Sprite(new Texture("Personajes/Shiblu.png"));
        gemaazul= new Sprite(new Texture("Personajes/gemaazul.png"));
        gemaroja= new Sprite(new Texture("Personajes/gemaroja.png"));
        gemaverde= new Sprite(new Texture("Personajes/gemaverde.png"));
        progress= new Sprite(new Texture("Personajes/progress.png"));

        contadorgemas= new Sprite(new Texture("Personajes/contadorgemas.png"));

        Rojel.setPosition(100,400);
        Esgrun.setPosition(600,400);
        Shiblu.setPosition(1000,400);
        gemaazul.setPosition(1150,500);
        gemaverde.setPosition(700,500);
        gemaroja.setPosition(250,500);
        contadorgemas.setPosition(ANCHO-125,ALTO-50);
        progress.setPosition(ANCHO/2-50,ALTO-150);
        escenaMenu=new Stage(vista);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaMenu.addActor(btnRegresar);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void show() {
       crearJuego();
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        rebotarObjeto();
        rotarObjeto();

        batch.begin();
        batch.draw(texturafondo,0,0);
        Lumil.draw(batch);
        Esgrun.draw(batch);
        Rojel.draw(batch);
        Shiblu.draw(batch);
        gemaazul.draw(batch);
        gemaverde.draw(batch);
        gemaroja.draw(batch);
        contadorgemas.draw(batch);
        progress.draw(batch);
        batch.end();

        escenaMenu.draw();
    }
    private void rebotarObjeto() {
        float y= MathUtils.sinDeg(0.3f*angulo)*50+350;
        angulo+=5;
        Shiblu.setY(y);
        Rojel.setY(y);
        Esgrun.setY(y);
        Lumil.setX(Lumil.getX()+dx);
        // Probar choque a la derecha
        if(Lumil.getX()>=ANCHO-Lumil.getWidth() || Lumil.getX()<=0) {
            dx=-dx; // Cambia direccion
        }
    }

    private void rotarObjeto() {
        gemaverde.setRotation(angulorotacion);
        gemaazul.setRotation(angulorotacion);
        gemaroja.setRotation(angulorotacion);
        angulorotacion += D_angulorotacion;
        if(angulorotacion>=180 || angulorotacion<=-180){
            D_angulorotacion=-D_angulorotacion;
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
}


