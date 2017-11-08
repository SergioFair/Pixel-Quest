package com.pixelquest.modelos.tropas.aliadas;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.R;
import com.pixelquest.configuracion.Estados;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.graficos.Sprite;
import com.pixelquest.modelos.tropas.AbstractTropa;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergio on 26/10/2017.
 */

public class TropaLigeraAliada extends AbstractTropa {

    private Sprite sprite;
    private Map<String,Sprite> sprites;
    private final static String MOVIENDOSE = "moviendose";
    private final static String ATACANDO = "atacando";
    private final static String MURIENDO = "muriendo";

    public TropaLigeraAliada(Context context, int drawable){
        super(context, 0, 0, 277, 380);
        setVida(R.integer.tropaLigeraVida);
        setAtaque(R.integer.tropaLigeraAtaque);
        setVelocidad(R.integer.tropaLigeraVelocidad);
        inicializar();
    }

    private void inicializar(){
        this.sprites = new HashMap<>();
        Sprite actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_ligero_aliado_mov) , getAncho(), getAlto()
                , 4, 4, true);
        sprites.put(MOVIENDOSE, actual);

        sprite = actual;

        actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_ligero_aliado_atq) , 435, getAlto()
                , 7, 7, true);
        sprites.put(ATACANDO, actual);

        actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_muerte_defecto) , 315, getAlto()
                , 4, 4, true);
        sprites.put(MURIENDO, actual);
    }

    @Override
    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int)getX(), (int)getY(), false);
    }

    @Override
    public void actualizar(long tiempo) {
        boolean finSprite = sprite.actualizar(tiempo);

        if (getEstado() == Estados.INACTIVO && finSprite){
            setEstado(Estados.DESTRUIDO);
        }

        if (getEstado() == Estados.INACTIVO){
            sprite = sprites.get(MURIENDO);

        } else if (getEstado() == Estados.ATACANDO){
            sprite = sprites.get(ATACANDO);
        } else{
            sprite = sprites.get(MOVIENDOSE);
        }
    }

}