package com.pixelquest.modelos.powerups;


import android.graphics.Canvas;

import com.pixelquest.modelos.tropas.Tropa;

/**
 * Created by Sergio.
 */

public interface PowerUp {

    void execute();

    boolean colisiona(Tropa t);

    void dibujar(Canvas canvas);

    boolean actualizar(long tiempo);
}
