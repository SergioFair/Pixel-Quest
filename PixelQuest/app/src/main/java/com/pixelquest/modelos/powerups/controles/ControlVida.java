package com.pixelquest.modelos.powerups.controles;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class ControlVida extends Modelo {

    private int vidaJugador, vidaEnemigo;

    public ControlVida(Context context){
        super(context, 0,0, 40, 40);
        this.vidaJugador = 10;
        this.vidaEnemigo = 100;
    }

    public int getVidaJugador(){
        return this.vidaJugador;
    }

    public int getVidaEnemigo(){
        return this.vidaEnemigo;
    }

    public void aumentarVidaJugador(int vida){
        this.vidaJugador+=vida;
    }

    public void reducirVidaJugador(){
        this.vidaJugador--;
    }

    public void reducirVidaEnemigo(){
        this.vidaEnemigo -= 10;
    }

    public void dibujar(Canvas canvas) {

    }
}
