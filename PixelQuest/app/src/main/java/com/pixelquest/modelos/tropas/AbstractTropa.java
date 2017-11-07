package com.pixelquest.modelos.tropas;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public abstract class AbstractTropa extends Modelo implements Tropa {

    private int vida, ataque, estado;
    private double velocidad;

    public AbstractTropa(Context context, double x, double y, int ancho, int alto) {
        super(context, x, y, ancho, alto);
    }

    public void atacar(Tropa tropa){
        tropa.restarVida(this.ataque);
    }

    public int getVida(){
        return this.vida;
    }

    public void setVida(int vida) {this.vida = vida;}

    public int getAtaque(){
        return this.ataque;
    }

    public void setAtaque(int ataque) {this.ataque = ataque;}

    public void mover(int tipoTropa){
        acelerar(this.velocidad*tipoTropa);
    }

    public double getVelocidad(){
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {this.velocidad = velocidad;}

    public void restarVida(int vida){
        this.vida -= vida;
    }

    public int getEstado(){
        return this.estado;
    }

    public void setEstado(int estado){
        this.estado = estado;
    }
}
