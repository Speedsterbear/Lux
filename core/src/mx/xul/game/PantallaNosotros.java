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
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.security.PrivateKey;

public class PantallaNosotros extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite aboutus;
    private Lux juego;

    private Texture androidImage;

    //Para controlar el movimiento de las pantallas
    private int mover = 0;
    private float avancePantalla = 20;

    //Cristales de colores en los fondos
    private int cristalColor = 0;
    private Texture texturaCristalW;
    private Texture texturaCristalG;
    private Texture texturaCristalR;
    private Texture texturaCristalB;
    private Texture texturaCristalY;
    private Texture texturaCristalC;
    private Texture texturaAndrea;
    private Texture texturaDavid;
    private Texture texturaEduardo;
    private Texture texturaRicardo;
    private Texture texturaCarlos;
    private Texture texturaInicial;
    private Cristal cristalW;
    private Cristal cristalG;
    private Cristal cristalR;
    private Cristal cristalB;
    private Cristal cristalY;
    private Cristal cristalC;
    private float Tiempo = 0f;

    public PantallaNosotros(Lux juego) {

        this.juego=juego;
    }
    private void crearNosotros(){
        androidImage= new Texture("Nosotros/Androidst.png");
        texturafondo= new Texture("Nosotros/fondoAbout.jpg");
        //texturaUs = new   Texture("");
        escenaMenu=new Stage(vista);
        //aboutus= new Sprite(new Texture("Menu/clicknosotros.png"));
        //aboutus.setPosition(ANCHO/2-aboutus.getWidth(),ALTO/2-aboutus.getHeight());
        //aboutus.setSize(1190,454);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));


            }
        });

        Button btnCreditos = crearBoton("Nosotros/flechaDerOff.png", "Nosotros/flechaDerOn.png");
        btnCreditos.setPosition(ANCHO-400,150, Align.center);
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaCreditos(juego));
                //1 Significa que va hacia la derecha
                mover = 1;

            }
        });

        Button btnNosotros = crearBoton("Nosotros/flechaIzqOff.png", "Nosotros/flechaIzqOn.png");
        btnNosotros.setPosition(ANCHO+400,150, Align.center);
        btnNosotros.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaCreditos(juego));
                //2 significa que va hacia la izquierda
                mover = 2;
                cristalColor = 0;

            }
        });

        //Probar El Cambio de color no se va usar

        Button btnC = crearBoton("Nosotros/flechaArrOff.png", "Nosotros/flechaArrOn.png");
        btnC.setPosition(2*ANCHO - 100,150, Align.center);
        btnC.addListener(new ClickListener(){
            @Override
        public void clicked(InputEvent event, float x, float y) {
        // Cambiar de pantalla a Juego
        //juego.setScreen(new PantallaCreditos(juego));
        //2 significa que va hacia la izquierda
                cristalColor +=1;
                if (cristalColor > 5){
                    cristalColor = 0;
                }
        }
        });


        escenaMenu.addActor(btnRegresar);
        escenaMenu.addActor(btnCreditos);
        escenaMenu.addActor(btnNosotros);
        escenaMenu.addActor(btnC);
        Gdx.input.setInputProcessor(escenaMenu);
    }
    @Override
    public void show() {
        crearNosotros();
        crearCristales();
    }

    private void crearCristales() {
        texturaCristalW = new Texture("Nosotros/Cristales-W.png");
        cristalW = new Cristal(texturaCristalW,texturafondo.getWidth()/2,texturaCristalW.getHeight()/2,7,1,1/4f,1);
        texturaCristalG = new Texture("Nosotros/Cristales-G.png");
        cristalG = new Cristal(texturaCristalG,texturafondo.getWidth()/2,texturaCristalG.getHeight()/2,7,1,1/4f,1);
        texturaCristalR = new Texture("Nosotros/Cristales-R.png");
        cristalR = new Cristal(texturaCristalR,texturafondo.getWidth()/2,texturaCristalR.getHeight()/2,7,1,1/4f,1);
        texturaCristalB = new Texture("Nosotros/Cristales-B.png");
        cristalB = new Cristal(texturaCristalB,texturafondo.getWidth()/2,texturaCristalB.getHeight()/2,7,1,1/4f,1);
        texturaCristalY = new Texture("Nosotros/Cristales-Y.png");
        cristalY = new Cristal(texturaCristalY,texturafondo.getWidth()/2,texturaCristalY.getHeight()/2,7,1,1/4f,1);
        texturaCristalC = new Texture("Nosotros/Cristales-C.png");
        cristalC = new Cristal(texturaCristalC,texturafondo.getWidth()/2,texturaCristalC.getHeight()/2,7,1,1/4f,1);
        texturaAndrea = new Texture("Nosotros/andrea.png");
        texturaCarlos = new Texture("Nosotros/carlos.png");
        texturaDavid = new Texture("Nosotros/david.png");
        texturaEduardo = new Texture("Nosotros/eduardo.png");
        texturaRicardo = new Texture("Nosotros/ricardo.png");
        texturaInicial = new Texture("Nosotros/CasoInicial.png");

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
        Tiempo += Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.
        borrarPantalla(0,50,125);

        recorrerPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.draw(androidImage,150,150);
        //aboutus.draw(batch);
        dibujarCristales();
        batch.end();

        escenaMenu.draw();
    }

    private void dibujarCristales() {

        switch (cristalColor) {
            case 0:
                cristalW.animationRender(batch, Tiempo);
                batch.draw(texturaInicial,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 1:
                cristalR.animationRender(batch, Tiempo);
                batch.draw(texturaAndrea,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 2:
                cristalY.animationRender(batch, Tiempo);
                batch.draw(texturaDavid,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 3:
                cristalG.animationRender(batch, Tiempo);
                batch.draw(texturaCarlos,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 4:
                cristalC.animationRender(batch, Tiempo);
                batch.draw(texturaEduardo,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 5:
                cristalB.animationRender(batch, Tiempo);
                batch.draw(texturaRicardo,ANCHO*3/2-200,ALTO/2-400);
                break;
            default :
                cristalW.animationRender(batch, Tiempo);
                break;
        }

    }
    //Case3 Carlos

    private void recorrerPantalla() {
        //Si se oprimen los botones para deslizar la pantalla comienza a cambiar la posición
        if (mover!=0) {

            if (mover==1){
                if (camara.position.x <ANCHO*1.5f) {

                    camara.position.x += avancePantalla;
                } else {mover=0;}
            } else {
                if (camara.position.x >ANCHO/2) {

                    camara.position.x -= avancePantalla;
                } else {mover=0;}
            }


            camara.update();
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
