package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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

    // AssetManager
    private AssetManager manager;

    //Cristales de colores en los fondos
    private int cristalColor = 0;
    //Texturas de los cristales (una por color)
    private Texture texturaCristalW;
    private Texture texturaCristalG;
    private Texture texturaCristalR;
    private Texture texturaCristalB;
    private Texture texturaCristalY;
    private Texture texturaCristalC;

    //Imagenes de la información de las personas
    private Texture texturaAndrea;
    private Texture texturaDavid;
    private Texture texturaEduardo;
    private Texture texturaRicardo;
    private Texture texturaCarlos;
    private Texture texturaInicial;

    //Para crear el El cristal
    private Cristal cristalW;
    private Cristal cristalG;
    private Cristal cristalR;
    private Cristal cristalB;
    private Cristal cristalY;
    private Cristal cristalC;
    private float Tiempo = 0f;

    public PantallaNosotros(Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }
    private void crearNosotros(){

        androidImage= manager.get("Nosotros/Androidst.png");
        //androidImage= new Texture("Nosotros/Androidst.png");

        texturafondo= manager.get("Nosotros/fondoAbout.jpg");
       // texturafondo= new Texture("Nosotros/fondoAbout.jpg");

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
        //cargarRecursos();
    }

    //private void cargarRecursos() {
        // Cargar las texturas/mapas
        //AssetManager assetManager = juego.getAssetManager();   // Referencia al assetManager

        // Se bloquea hasta que cargue todos los recursos
        //assetManager.finishLoading();
    //}

    private void crearCristales() {

        texturaCristalW = manager.get("Nosotros/Cristales-W.png");
        cristalW = new Cristal(texturaCristalW,texturafondo.getWidth()/2,texturaCristalW.getHeight()/2,7,1,1/4f,1);

        texturaCristalG = manager.get("Nosotros/Cristales-G.png");
        cristalG = new Cristal(texturaCristalG,texturafondo.getWidth()/2,texturaCristalG.getHeight()/2,7,1,1/4f,1);

        texturaCristalR = manager.get("Nosotros/Cristales-R.png");
        cristalR = new Cristal(texturaCristalR,texturafondo.getWidth()/2,texturaCristalR.getHeight()/2,7,1,1/4f,1);

        texturaCristalB = manager.get("Nosotros/Cristales-B.png");
        cristalB = new Cristal(texturaCristalB,texturafondo.getWidth()/2,texturaCristalB.getHeight()/2,7,1,1/4f,1);

        texturaCristalY =manager.get("Nosotros/Cristales-Y.png");
        cristalY = new Cristal(texturaCristalY,texturafondo.getWidth()/2,texturaCristalY.getHeight()/2,7,1,1/4f,1);

        texturaCristalC = manager.get("Nosotros/Cristales-C.png");
        cristalC = new Cristal(texturaCristalC,texturafondo.getWidth()/2,texturaCristalC.getHeight()/2,7,1,1/4f,1);

        texturaAndrea = manager.get("Nosotros/andrea.png");
        texturaCarlos = manager.get("Nosotros/carlos.png");
        texturaDavid = manager.get("Nosotros/david.png");
        texturaEduardo = manager.get("Nosotros/eduardo.png");
        texturaRicardo = manager.get("Nosotros/ricardo.png");
        texturaInicial = manager.get("Nosotros/CasoInicial.png");

    }

    private Button crearBoton(String archivo, String archivoclick) {
       // Texture texturaBoton=new Texture(archivo);
        Texture texturaBoton= manager.get(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick= manager.get(archivoclick);
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
        // Los assets se liberan a través del assetManager
        manager.unload("Nosotros/Androidst.png");
        manager.unload("Nosotros/fondoAbout.jpg");
        manager.unload("Menu/buttonback.png");
        manager.unload("Menu/clickback.png");
        manager.unload("Nosotros/flechaDerOff.png");
        manager.unload("Nosotros/flechaDerOn.png");
        manager.unload("Nosotros/flechaIzqOff.png");
        manager.unload("Nosotros/flechaIzqOn.png");
        manager.unload("Nosotros/flechaArrOff.png");
        manager.unload("Nosotros/flechaArrOn.png");
        manager.unload("Nosotros/Cristales-W.png");
        manager.unload("Nosotros/Cristales-G.png");
        manager.unload("Nosotros/Cristales-R.png");
        manager.unload("Nosotros/Cristales-B.png");
        manager.unload("Nosotros/Cristales-Y.png");
        manager.unload("Nosotros/Cristales-C.png");
        manager.unload("Nosotros/andrea.png");
        manager.unload("Nosotros/carlos.png");
        manager.unload("Nosotros/david.png");
        manager.unload("Nosotros/eduardo.png");
        manager.unload("Nosotros/ricardo.png");
        manager.unload("Nosotros/CasoInicial.png");

    }
}
