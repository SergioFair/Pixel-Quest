package com.pixelquest.modelos.powerups;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.graficos.Sprite;
import com.pixelquest.modelos.Modelo;
import com.pixelquest.modelos.powerups.controles.ControlVida;
import com.pixelquest.modelos.tropas.Tropa;


/**
 * Created by Sergio.
 */

public class PowerUpVida extends Modelo implements PowerUp {

    private ControlVida control;
    private Sprite sprite;

    public PowerUpVida(Context context, ControlVida control, int x, int y){
        super(context, x,y, GameView.pantallaAncho/15, GameView.pantallaAlto/12);
        this.control = control;
        sprite = new Sprite(CargadorGraficos.cargarDrawable(context
                , R.drawable.animacion_powerup_vida),getAncho(), getAlto()
                ,1,3, true);
    }

    @Override
    public void execute() {
        this.control.aumentarVidaJugador(2);
    }

    @Override
    public boolean colisiona(Tropa t){
        boolean result = false;
        if(t.getVelocidadInicial()>0){
            Modelo model = (Modelo) t;
            if(model.getY() == getY()){
                if (model.getX() - model.getAncho() / 2 <= (getX() + getAncho() / 2)
                        && (model.getX() + model.getAncho() / 2) >= (getX() - getAncho() / 2)
                        && (getY() + getAlto() / 2) >= (model.getY() - model.getAlto() / 2)
                        && (getY() - getAlto() / 2) < (model.getY() + model.getAlto() / 2)) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) getX(), (int) getY(), false);
    }

    @Override
    public boolean actualizar(long tiempo) {
        return sprite.actualizar(tiempo);
    }
}