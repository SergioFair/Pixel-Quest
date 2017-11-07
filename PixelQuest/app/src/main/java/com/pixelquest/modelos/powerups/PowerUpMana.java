package com.pixelquest.modelos.powerups;

import android.content.Context;
import com.pixelquest.modelos.Modelo;
import com.pixelquest.modelos.powerups.controles.ControlMana;

/**
 * Created by Sergio.
 */

public class PowerUpMana extends Modelo implements PowerUp {

    private ControlMana control;

    public PowerUpMana(Context context){
        super(context, 0,0, 40, 40);
    }

    @Override
    public void execute() {
        this.control.aumentarMana(10);
    }
}