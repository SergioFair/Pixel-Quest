package com.pixelquest.modelos.powerups;

import android.content.Context;

import com.pixelquest.modelos.Modelo;
import com.pixelquest.modelos.powerups.controles.ControlVida;


/**
 * Created by Sergio.
 */

public class PowerUpVida extends Modelo implements PowerUp {

    private ControlVida control;

    public PowerUpVida(Context context){
        super(context, 0,0, 40, 40);
    }

    @Override
    public void execute() {
        this.control.aumentarVidaJugador(2);
    }
}