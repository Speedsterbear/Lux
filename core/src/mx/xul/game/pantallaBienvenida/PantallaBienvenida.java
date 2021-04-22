package mx.xul.game.pantallaBienvenida;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import javax.swing.ImageIcon;
import javax.xml.soap.Text;

// Autor: Andrea Espinosa Azuela
// Clase que implemente la pantalla de bienvenida

import mx.xul.game.JuegoGS;
import mx.xul.game.Lux;
import mx.xul.game.Pantalla;
import mx.xul.game.PantallaMenu;

public class PantallaBienvenida extends Pantalla {

    private Lux juego;
    private Stage stageLogo;
    public static Texture texturaLogo;

    public PantallaBienvenida(Lux lux) {
        this.juego=lux;
        texturaLogo= new Texture(Gdx.files.internal("PantallaBienvenida/logoBienvenida.jpeg"));
        texturaLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image = new Image(texturaLogo);
        image.setFillParent(true);
        image.setScaling(Scaling.fillY);
        stageLogo=new Stage();
        stageLogo.addActor(image);

        image.addAction(Actions.sequence(Actions.alpha(0)
                , Actions.fadeIn(5.0f),Actions.fadeOut(2),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenu(juego));
                    }
                })
        ));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Borrar pantalla
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageLogo.act();
        stageLogo.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        texturaLogo.dispose();
        stageLogo.dispose();
    }
}
