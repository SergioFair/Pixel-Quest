package com.pixelquest.modelos.visual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.pixelquest.GameView;
import com.pixelquest.modelos.Modelo;


public class Fondo extends Modelo {
    private Bitmap fondo;
    private Bitmap fondoAux;
    private float velocidadX;

    public Fondo(Context context, Bitmap imagen, float velocidadX) {
        super(context,
                GameView.pantallaAncho/2,
                GameView.pantallaAlto/2,
                GameView.pantallaAncho,
                GameView.pantallaAlto );

        this.fondo = imagen;
        if (velocidadX > 0) {
            fondoAux = imagen;
        }
        this.velocidadX = velocidadX;
    }

    public void mover(double movimientoScroll){
        if (movimientoScroll > 0.1) {
            setX(getX() - velocidadX) ;
        } else if (movimientoScroll < -0.1) {
            setX(getX() + velocidadX);
        }

        if (getX() > getAncho() / 2) {
            setX(-getAncho() / 2);
        }
        if (getX() < -getAncho() / 2) {
            setX(getAncho() / 2);
        }
    }

    public void dibujar(Canvas canvas) {
        int xIzquierda = (int) getX() - getAncho() / 2;

        Rect origen = new Rect(0,0 ,
                fondo.getWidth(),fondo.getHeight());

        Rect destino = new Rect((int) (getX() - getAncho() / 2),
                (int) (getY() - getAlto() / 2),
                (int) (getX() + getAncho() / 2),
                (int) (getY() + getAlto() / 2));

        canvas.drawBitmap(fondo,origen,destino,null);

        if (velocidadX > 0){

            // colocar detras
            if (xIzquierda > 0) {
                destino = new Rect((int) (getX() - getAncho() / 2) - getAncho(),
                        (int) (getY() - getAlto() / 2),
                        (int) (getX() + getAncho() / 2 - getAncho()),
                        (int) (getY() + getAlto() / 2));

                canvas.drawBitmap(fondoAux, origen, destino, null);
            }

            // colocar delante
            if (xIzquierda < 0) {
                destino = new Rect((int) (getX() - getAncho() / 2) + getAncho(),
                        (int) (getY() - getAlto() / 2),
                        (int) (getX() + getAncho() / 2 + getAncho()),
                        (int) (getY() + getAlto() / 2));

                canvas.drawBitmap(fondoAux, origen, destino, null);
            }
        }
    }
}