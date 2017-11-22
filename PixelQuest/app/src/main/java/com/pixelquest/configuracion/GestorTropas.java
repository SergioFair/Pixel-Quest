package com.pixelquest.configuracion;

import android.content.Context;

import com.pixelquest.modelos.tropas.Tropa;
import com.pixelquest.modelos.tropas.aliadas.TropaBossAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaDistanciaAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaLigeraAliada;
import com.pixelquest.modelos.tropas.aliadas.TropaPesadaAliada;
import com.pixelquest.modelos.tropas.enemigas.TropaBossEnemigo;
import com.pixelquest.modelos.tropas.enemigas.TropaDistanciaEnemigo;
import com.pixelquest.modelos.tropas.enemigas.TropaLigeraEnemigo;
import com.pixelquest.modelos.tropas.enemigas.TropaPesadaEnemigo;

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

    public Tropa createAliado(Context context, double y){
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

    public Tropa createEnemigo(Context context, double y, int tipoEnemigo){
        Tropa tropa = null;
        switch(tipoEnemigo){
            case LIGERA:
                tropa = new TropaLigeraEnemigo(context, y);
                break;
            case DISTANCIA:
                tropa = new TropaDistanciaEnemigo(context, y);
                break;
            case PESADA:
                tropa = new TropaPesadaEnemigo(context, y);
                break;
            case BOSS:
                tropa = new TropaBossEnemigo(context, y);
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