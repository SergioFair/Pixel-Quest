package com.pixelquest.modelos.tropas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class Flecha extends Modelo {

    private Drawable imagen;
    private double velocidad;

    public Flecha(Context context, double x, double y, int direccion) {
        super(context, x, y, GameView.pantallaAncho/20, GameView.pantallaAlto/20);
        imagen = direccion == 1?CargadorGraficos.cargarDrawable(context, R.drawable.flecha_derecha)
                :CargadorGraficos.cargarDrawable(context, R.drawable.flecha_izquierda);
        this.velocidad = 15 * direccion;
    }

    public void dibujar(Canvas canvas) {
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }

    public double getVelocidad() {
        return velocidad;
    }
}
