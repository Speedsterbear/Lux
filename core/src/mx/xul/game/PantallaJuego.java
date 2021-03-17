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

public class PantallaJuego extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite personaje;
    private float dx=10;
    private float angulo=0;
    private Lux juego;

    public PantallaJuego(Lux juego) {
        this.juego=juego;
    }

    private void crearJuego(){
        texturafondo= new Texture("Menu/fondo.jpg");
        personaje= new Sprite(new Texture("Menu/personaje.png"));
        escenaMenu=new Stage(vista);

        Button btnRegresar = crearBoton("Menu/buttonregresar.png", "Menu/clickregresar.png");
        btnRegresar.setPosition(2*ANCHO/3,1.5f*ALTO/2, Align.center);
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

        batch.begin();
        batch.draw(texturafondo,0,0);
        personaje.draw(batch);
        batch.end();

        escenaMenu.draw();
    }
    private void rebotarObjeto() {
        float y= MathUtils.sinDeg(3*angulo)*100+200;
        angulo+=5;
       // personaje.setY(y);
        personaje.setX(personaje.getX()+dx);
        // Probar choque a la derecha
        if(personaje.getX()>=ANCHO-personaje.getWidth() || personaje.getX()<=0) {
            dx=-dx; // Cambia direccion
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


