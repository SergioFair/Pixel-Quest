package com.pixelquest.configuracion;

import android.content.Context;

import com.pixelquest.modelos.tropas.Tropa;
import com.pixelquest.modelos.tropas.aliadas.TropaBossAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaDistanciaAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaLigeraAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaPesadaAliada;

/**
 * Created by Sergio.
 */

public class GestorTropas {

    private final static int LIGERA = 0
                    , DISTANCIA = 1
                    , PESADA = 2
                    , BOSS = 3
                    , DEFAULT = -1;
    private int tropaElegida;
    private static GestorTropas INSTANCE;

    private GestorTropas(){}

    public static GestorTropas getInstance(){
        if (INSTANCE == null)
            INSTANCE = new GestorTropas();
        return INSTANCE;
    }

    public void setTropaElegida(int valor){
        this.tropaElegida = valor;
    }

    public Tropa createTropa(Context context, double y){
        Tropa tropa = null;
        switch(tropaElegida){
            case LIGERA:
                tropa = new TropaLigeraAliada(context, y);
                break;
            case DISTANCIA:
                tropa = new TropaDistanciaAliada(context, y);
                break;
            case PESADA:
                tropa = new TropaPesadaAliada(context, y);
                break;
            case BOSS:
                tropa = new TropaBossAliada(context, y);
                break;
        }
        return tropa;
    }

    public boolean isTropaElegida() {
        return tropaElegida != DEFAULT;
    }

    public int getTropaElegida() {
        return tropaElegida;
    }
}