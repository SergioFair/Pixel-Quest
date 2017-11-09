package com.pixelquest.modelos.tropas;

import android.graphics.Canvas;

import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public interface Tropa{

    void atacar(Tropa tropa);

    void restarVida(int vida);

    void mover();

    void setEstado(int inactivo);

    int getEstado();

    int getAtaque();

    int getVida();

    double getVelocidad();

    void setVelocidad(int i);

    void setAtaque(int i);

    void setVida(int i);

    void dibujar(Canvas canvas);

    void actualizar(long tiempo);
}
