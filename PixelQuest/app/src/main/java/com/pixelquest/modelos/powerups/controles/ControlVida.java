package com.pixelquest.modelos.powerups.controles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pixelquest.GameView;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class ControlVida extends Modelo {

    private int vidaJugador, vidaEnemigo;

    public ControlVida(Context context){
        super(context, GameView.pantallaAlto*0.1,GameView.pantallaAncho*0.1
                , 40, 40);
        this.vidaJugador = 1;
        this.vidaEnemigo = 10;
    }

    public void aumentarVidaJugador(int vida){
        this.vidaJugador+=vida;
    }

    public void reducirVidaJugador(){
        this.vidaJugador--;
    }

    public void reducirVidaEnemigo(){
        this.vidaEnemigo--;
    }

    public void dibujar(Canvas canvas) {
        //Vida jugador
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(GameView.pantallaAlto/10);
        canvas.drawText(String.valueOf(this.vidaJugador), (int) getX(), (int) getY(), paint);

        //Vida enemigo
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(GameView.pantallaAlto/10);
        canvas.drawText(String.valueOf(this.vidaEnemigo), (int) (GameView.pantallaAncho*0.9)
                , (int) getY(), paint);
    }

    public int getVidaJugador() {
        return vidaJugador;
    }

    public int getVidaEnemigo() {
        return vidaEnemigo;
    }
}
