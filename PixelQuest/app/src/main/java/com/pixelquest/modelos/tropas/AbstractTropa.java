package com.pixelquest.modelos.tropas;

import android.content.Context;

import com.pixelquest.GameView;
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

    public void mover(){
        acelerar(this.velocidad/15);
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

    public int estaAliadoEnPantalla(){
        if ( getX() + getAncho()/2 < 0){
            return 0; // Va a aparecer
        } if ( getX() + getAncho()/2 >= 0 && getX() - getAncho()/2 < GameView.pantallaAncho){
            return 1; // Está en pantalla
        }
        return -1; // Se ha salido por la derecha
    }

    public int estaEnemigoEnPantalla(){
        if ( getX() + getAncho()/2 > GameView.pantallaAncho){
            return 0; // Va a aparecer
        } if ( getX() + getAncho()/2 >= 0 && getX() - getAncho()/2 < GameView.pantallaAncho){
            return 1; // Está en pantalla
        }
        return -1; // Se ha salido por la izquierda
    }

    public boolean colisiona(Tropa tropa){
        boolean result = false;
        Modelo model = (Modelo) tropa;
        if(model.getY() == getY()){
            if (model.getX() - model.getAncho() / 2 <= (getX() + getAncho() / 2)
                    && (model.getX() + model.getAncho() / 2) >= (getX() - getAncho() / 2)
                    && (getY() + getAlto() / 2) >= (model.getY() - model.getAlto() / 2)
                    && (getY() - getAlto() / 2) < (model.getY() + model.getAlto() / 2)) {
                result = true;
            }
        }
        return result;
    }
}
