package com.pixelquest.modelos.tropas.botones;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio on 08/11/2017.
 */

public class BotonTropaLigera extends Modelo implements BotonTropa{

    private Drawable imagen;

    public BotonTropaLigera(Context context) {
        super(context, GameView.pantallaAncho*0.3,GameView.pantallaAlto*0.9
                , (int) (GameView.pantallaAncho*0.2), (int) (GameView.pantallaAlto*0.2));
        imagen = CargadorGraficos.cargarDrawable(getContext(), R.drawable.boton_ligero);
    }

    public void dibujar(Canvas canvas){
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }
}
