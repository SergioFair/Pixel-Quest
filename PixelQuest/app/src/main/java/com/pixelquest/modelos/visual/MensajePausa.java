package com.pixelquest.modelos.visual;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class MensajePausa extends Modelo {

    private Drawable imagen;

    public MensajePausa(Context context) {
        super(context, GameView.pantallaAncho/2, GameView.pantallaAlto/2, GameView.pantallaAncho, GameView.pantallaAlto);
        this.imagen = context.getResources().getDrawable(R.drawable.pause_message);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (getX() + getAncho() / 2) && clickX >= (getX() - getAncho() / 2)
                && clickY <= (getY() + getAlto() / 2) && clickY >= (getY() - getAlto() / 2)

                ) {
            estaPulsado = true;
        }
        return estaPulsado;
    }

    public void dibujar(Canvas canvas){
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }

    public void setImagen(Drawable imagen){
        this.imagen = imagen;
    }
}
