package com.pixelquest.modelos.powerups.controles;


import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class ControlMana extends Modelo {

    private int mana;

    public ControlMana(Context context){
        super(context, 0,0, 40, 40);
        this.mana = 0;
    }

    public int getMana(){
        return this.mana;
    }

    public void aumentarMana(int mana){
        this.mana+=mana;
    }

    public void restarMana(int mana) {
        this.mana -= mana;
    }

    public void dibujar(Canvas canvas) {

    }
}
