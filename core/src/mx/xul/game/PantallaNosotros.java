package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaNosotros extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite aboutus;
    private Lux juego;

    public PantallaNosotros(Lux juego) {
        this.juego=juego;
    }
    private void crearNosotros(){
        texturafondo= new Texture("Menu/Fondo_Montana.jpg");
        escenaMenu=new Stage(vista);
        aboutus= new Sprite(new Texture("Menu/clicknosotros.png"));
        aboutus.setPosition(ANCHO/2-aboutus.getWidth(),ALTO/2-aboutus.getHeight());
        aboutus.setSize(1190,454);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Button btnCreditos = crearBoton("Menu/buttoncredits.png", "Menu/clickcredits.png");
        btnCreditos.setPosition(ANCHO/2+80,120, Align.center);
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaCreditos(juego));
            }
        });


        escenaMenu.addActor(btnRegresar);
        escenaMenu.addActor(btnCreditos);
        Gdx.input.setInputProcessor(escenaMenu);
    }
    @Override
    public void show() {
        crearNosotros();
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

        batch.begin();
        batch.draw(texturafondo,0,0);
        aboutus.draw(batch);
        batch.end();

        escenaMenu.draw();
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

