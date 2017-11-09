package com.pixelquest.modelos.powerups.controles;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.Modelo;

/**
 * Created by Sergio.
 */

public class ControlMana extends Modelo {

    private int mana;
    private Drawable imagen;

    public ControlMana(Context context){
        super(context, GameView.pantallaAncho*0.1,GameView.pantallaAlto*0.9
                , (int) (GameView.pantallaAncho*0.2), (int) (GameView.pantallaAlto*0.2));
        this.mana = 0;
        imagen = CargadorGraficos.cargarDrawable(getContext(), R.drawable.boton_mana);
    }

    public int getMana(){
        return this.mana;
    }

    public void aumentarMana(int mana){
        this.mana+=mana;
    }

    public void restarMana(int mana) {
        this.mana -= mana;
    }

    public void dibujar(Canvas canvas) {
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(42);
        paint.setTypeface(Typeface.create("Cambria",Typeface.BOLD));
        canvas.drawText(String.valueOf(mana), (int) (getX()*0.9)
                , (int) (getY()+getAlto()*0.3), paint);
    }
}
