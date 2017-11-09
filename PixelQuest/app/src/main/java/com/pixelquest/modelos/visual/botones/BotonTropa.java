package com.pixelquest.modelos.visual.botones;

import android.graphics.Canvas;

/**
 * Created by Sergio on 08/11/2017.
 */

public interface BotonTropa {

    void dibujar(Canvas canvas);

    void activar();

    void desactivar();

    int getCoste();

    boolean estaPulsado(float x, float y);

    void deseleccionar();

    void seleccionar();

    boolean estaActivo();

    boolean isSelected();
}
