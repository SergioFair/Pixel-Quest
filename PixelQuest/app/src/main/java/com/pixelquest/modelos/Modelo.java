package com.pixelquest.modelos;

import android.content.Context;

/**
 * Created by Sergio.
 */

public abstract class Modelo {

    private double x, y;
    private int ancho, alto;
    private Context context;

    public Modelo(Context context, double x, double y, int ancho, int alto) {
        setContext(context);
        setX(x);
        setY(y);
        this.ancho = ancho;
        this.alto = alto;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getAncho(){
        return this.ancho;
    }

    public int getAlto(){
        return this.alto;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public void acelerar(double velocidad) {
        this.x += velocidad;
    }
}
