package com.pixelquest.modelos.tropas.enemigas;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.configuracion.Estados;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.graficos.Sprite;
import com.pixelquest.modelos.tropas.AbstractTropa;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergio.
 */

public class TropaLigeraEnemigo extends AbstractTropa {

    private Sprite sprite;
    private Map<String,Sprite> sprites;
    private final static String MOVIENDOSE = "moviendose";
    private final static String ATACANDO = "atacando";
    private final static String MURIENDO = "muriendo";

    public TropaLigeraEnemigo(Context context, double y){
        super(context, GameView.pantallaAncho, y
                ,GameView.pantallaAlto/9, GameView.pantallaAlto/8);
        setVida(getContext().getResources().getInteger(R.integer.tropaLigeraVida));
        setAtaque(getContext().getResources().getInteger(R.integer.tropaLigeraAtaque));
        setVelocidad(-getContext().getResources().getInteger(R.integer.tropaLigeraVelocidad));
        inicializar();
    }

    private void inicializar(){
        this.sprites = new HashMap<>();
        Sprite actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_ligero_enemigo_mov) , getAncho(), getAlto()
                , 2, 2, true);
        sprites.put(MOVIENDOSE, actual);

        sprite = actual;

        actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_ligero_enemigo_atq) , getAncho(), getAlto()
                , 6, 6, true);
        sprites.put(ATACANDO, actual);

        actual = new Sprite(CargadorGraficos.cargarDrawable(getContext()
                , R.drawable.animacion_ligero_enemigo_muerte) , getAncho(), getAlto()
                , 7, 7, true);
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
