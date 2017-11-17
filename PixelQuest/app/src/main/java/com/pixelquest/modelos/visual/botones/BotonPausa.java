package com.pixelquest.modelos.visual.botones;

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

public class BotonPausa extends Modelo {

    private Drawable imagen;

    public BotonPausa(Context context) {
        super(context, GameView.pantallaAncho/2, GameView.pantallaAlto*0.1
                , (int) (GameView.pantallaAncho*0.05), (int) (GameView.pantallaAlto*0.05));
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.pause);
    }

    public void dibujar(Canvas canvas){
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }
}