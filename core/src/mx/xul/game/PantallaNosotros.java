package mx.xul.game;
/*
Esta pantalla sirve para mostrar la información de la aplicación y sus creadores.
Autores: Carlos Arroyo y David
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;


public class PantallaNosotros extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite aboutus;
    private Lux juego;

    private Texture androidImage;

    private  Texture tecImage;

    private  Texture mailImage;
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

    //Brillo
    private Texture texturaBrillo;
    private BrilloLumil brilloCentro;
    private BrilloLumil brilloCentroMedio;
    private BrilloLumil brilloCentroFrente;
    private BrilloLumil brilloHorizontal;
    private BrilloLumil brilloVertical;
    private BrilloLumil brilloDiagonalAscendente;
    private BrilloLumil brilloDiagonalDescendente;
    private final float SCALA_INICIAL = 0.5f;
    private final float CAMBIO_ESCALA = 0.2f;
    private float angulo = 0; //Acumulador para movimiento
    private final float AVANCE_BRILLO = 110;//Movimiento horizontal del brillo
    private float colorR = 1; //Color Rojo
    private float colorG = 1; //Color Verde
    private float colorB = 1; //Color Azul

    //Cristal
    private Texture texturaRomboPerder;
    private Objeto rombo;
    private final float DY_ROMBO = 10;



    public PantallaNosotros(Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }
    private void crearNosotros(){

        androidImage= manager.get("Nosotros/Androidst.png");

        tecImage = manager.get("Nosotros/tecst.png");

        texturafondo= manager.get("Nosotros/fondoAbout.jpg");

        mailImage = manager.get("Nosotros/mailst.png");

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

        Button btnCreditos = crearBoton("Nosotros/btnFlechaDer_OFF.png", "Nosotros/btnFlechaDer_ON.png");
        btnCreditos.setPosition(ANCHO-450,150, Align.center);
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaCreditos(juego));
                //1 Significa que va hacia la derecha
                mover = 1;

            }
        });

        Button btnNosotros = crearBoton("Nosotros/btnFlechaIzq_OFF.png", "Nosotros/btnFlechaIzq_ON.png");
        btnNosotros.setPosition(ANCHO+450,150, Align.center);
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

        Button btnC = crearBoton("Nosotros/btnFlechaArriba_OFF.png", "Nosotros/btnFlechaArriba_ON.png");
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
        crearBrillos();
        crearNosotros();
        crearCristales();
        crearRombo();
        //cargarRecursos();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearRombo() {
        texturaRomboPerder =  manager.get("Utileria/cristalRomboBlanco.png");
        rombo = new Objeto(texturaRomboPerder,(3*ANCHO/2)+150,5*ALTO/8);
        rombo.sprite.setScale(0.3f);
    }

    private void crearBrillos() {
        texturaBrillo = manager.get("Utileria/brilloLumil.png");
        brilloCentro = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloCentroMedio = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloCentroFrente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloHorizontal = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloVertical = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloDiagonalAscendente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloDiagonalDescendente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);


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

        actualizarBrillo();
        actualizarRombo();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaMenu(juego));
        }

        recorrerPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        batch.draw(androidImage,75,350);
        batch.draw(tecImage, 25,100);
        batch.draw(mailImage, 75,0);

        //aboutus.draw(batch);
        dibujarCristales();

        dibujarBtillo();

        batch.end();

        escenaMenu.draw();
    }

    private void actualizarRombo() {
        rombo.sprite.setY((5*ALTO/8)-(rombo.sprite.getHeight()/2)+(MathUtils.sinDeg(angulo)*DY_ROMBO));
        brilloCentro.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));
        brilloCentroMedio.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));
        brilloCentroFrente.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));

    }

    private void dibujarBtillo() {

        if (angulo>=90 && angulo <180) {
            brilloHorizontal.render(batch);
            brilloVertical.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloDiagonalAscendente.render(batch);
            brilloDiagonalDescendente.render(batch);

        }else if (angulo>=180 && angulo <270) {
            brilloHorizontal.render(batch);
            brilloDiagonalDescendente.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloDiagonalAscendente.render(batch);
            brilloVertical.render(batch);

            }else if (angulo>=0 && angulo <90){
            brilloDiagonalAscendente.render(batch);
            brilloVertical.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloHorizontal.render(batch);
            brilloDiagonalDescendente.render(batch);
        } else {
            brilloDiagonalAscendente.render(batch);
            brilloDiagonalDescendente.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloHorizontal.render(batch);
            brilloVertical.render(batch);
        }
    }

    private void actualizarBrillo() {
        angulo +=1;
        if (angulo>=360){
            angulo=0;
        }

        switch (cristalColor){
            case 1:
                colorR = 1;
                colorG = 0;
                colorB= 0;
                break;
            case 2:
                colorR = 1;
                colorG = 1;
                colorB= 0;
                break;
            case 3:
                colorR = 0;
                colorG = 1;
                colorB= 0;
                break;
            case 4:
                colorR = 0;
                colorG = 1;
                colorB= 1;
                break;
            case 5:
                colorR = 0;
                colorG = 0;
                colorB= 1;
                break;
            default:
                colorR = 1;
                colorG = 1;
                colorB= 1;
                break;

        }
        brilloCentro.actualizar(colorR,colorG,colorB,1f);
        brilloCentroMedio.actualizar(colorR,colorG,colorB,0.8f);
        brilloCentroFrente.actualizar(colorR,colorG,colorB,0.6f);
        // Cambiar color del rombo
        rombo.sprite.setColor(0.8f+(colorR*.2f),0.8f+(colorG*.2f),0.8f+(colorB*.2f),1);
        //rombo.sprite.setColor(colorR,colorG,colorB,1);

        brilloHorizontal.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(-(angulo-90))*CAMBIO_ESCALA)));
        brilloHorizontal.moverX(((3*ANCHO/2)+150)+((MathUtils.sinDeg(angulo)*AVANCE_BRILLO)));

        brilloVertical.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(-(angulo))*CAMBIO_ESCALA)));
        brilloVertical.mover((5*ALTO/8)+((MathUtils.cosDeg(angulo)*AVANCE_BRILLO)));

        brilloDiagonalAscendente.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(angulo-90)*CAMBIO_ESCALA)));
        brilloDiagonalAscendente.moverX(((3*ANCHO/2)+150)+((MathUtils.sinDeg(-angulo)*AVANCE_BRILLO)));

        brilloDiagonalDescendente.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(angulo)*CAMBIO_ESCALA)));
        brilloDiagonalDescendente.mover((5*ALTO/8)-((MathUtils.cosDeg(-angulo)*AVANCE_BRILLO)));

    }

    private void dibujarCristales() {

        switch (cristalColor) {
            case 0:
                cristalW.animationRender(batch, Tiempo);
                //batch.draw(texturaInicial,ANCHO*3/2-200,ALTO/2-400);
                break;
            case 1:
                cristalR.animationRender(batch, Tiempo);
                batch.draw(texturaAndrea,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 2:
                cristalY.animationRender(batch, Tiempo);
                batch.draw(texturaDavid,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 3:
                cristalG.animationRender(batch, Tiempo);
                batch.draw(texturaCarlos,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 4:
                cristalC.animationRender(batch, Tiempo);
                batch.draw(texturaEduardo,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 5:
                cristalB.animationRender(batch, Tiempo);
                batch.draw(texturaRicardo,ANCHO*3/2-400,ALTO/2-250);



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
