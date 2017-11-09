package com.pixelquest.modelos.visual.botones;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio on 09/11/2017.
 */

public class BotonFlecha extends Modelo {

    private Drawable imagen;

    public BotonFlecha(Context context, double x, double y) {
        super(context, x, y, 105, 113);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.boton_flecha);
    }

    public void dibujar(Canvas canvas) {
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }
}
