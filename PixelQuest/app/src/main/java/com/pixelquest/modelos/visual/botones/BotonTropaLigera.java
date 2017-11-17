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

public class BotonTropaLigera extends Modelo implements BotonTropa{

    private Drawable imagen;
    private int coste;
    private boolean selected;

    public BotonTropaLigera(Context context) {
        super(context, GameView.pantallaAncho*0.4,GameView.pantallaAlto*0.9
                , (int) (GameView.pantallaAncho*0.2), (int) (GameView.pantallaAlto*0.2));
        imagen = CargadorGraficos.cargarDrawable(getContext(), R.drawable.boton_ligero);
        this.coste = getContext().getResources().getInteger(R.integer.tropaLigeraCoste);
        this.selected = false;
        desactivar();
    }

    public void dibujar(Canvas canvas){
        int yArriva = (int)  getY() - getAlto() / 2;
        int xIzquierda = (int) getX() - getAncho() / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + getAncho(), yArriva + getAlto());
        imagen.draw(canvas);
    }

    @Override
    public void activar() {
        this.imagen.setAlpha(255);
    }

    @Override
    public void desactivar() {
        this.imagen.setAlpha(150);
    }

    @Override
    public int getCoste() {
        return this.coste;
    }

    @Override
    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (getX() + getAncho() / 2) && clickX >= (getX() - getAncho() / 2)
                && clickY <= (getY() + getAlto() / 2) && clickY >= (getY() - getAlto() / 2)) {
            estaPulsado = true;
        }
        return estaPulsado;
    }

    @Override
    public boolean estaActivo() {
        return this.imagen.getAlpha() == 255;
    }

    @Override
    public void deseleccionar() {
        this.imagen = CargadorGraficos.cargarDrawable(getContext(), R.drawable.boton_ligero);
        this.selected = false;
    }

    @Override
    public void seleccionar() {
        this.imagen = CargadorGraficos.cargarDrawable(getContext(), R.drawable.boton_ligero_activo);
        this.selected = true;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }
}
