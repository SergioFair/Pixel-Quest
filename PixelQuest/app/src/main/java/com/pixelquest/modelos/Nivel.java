package com.pixelquest.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.tropas.Tropa;
import com.pixelquest.modelos.visual.Fondo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sergio on 01/11/2017.
 */

public class Nivel {
    private Context context;
    private List<Tropa> enemigos, aliados;
    private int numeroNivel;
    private int nivelActual;
    private GameView gameView;
    private boolean nivelPausado;
    private Fondo fondo;

    public Nivel(Context context, int numeroNivel){
        this.context = context;
        this.numeroNivel = 1;
        inicializar();
    }

    private void inicializar(){
        this.enemigos = new LinkedList<>();
        this.aliados = new LinkedList<>();
        fondo = new Fondo(context, CargadorGraficos.cargarBitmap(context,
                R.drawable.background_field), 0);
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void aumentarNivel() {
        this.nivelActual++;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameView getGameView(){
        return this.gameView;
    }

    public void dibujar(Canvas canvas) {
        fondo.dibujar(canvas);
        for(Tropa t : enemigos)
            t.dibujar(canvas);
        for(Tropa t : aliados)
            t.dibujar(canvas);
    }

    public boolean isNivelPausado() {
        return nivelPausado;
    }

    public void setNivelPausado(boolean nivelPausado) {
        this.nivelPausado = nivelPausado;
    }

    public void actualizar(long tiempo) {
        for(Tropa t : enemigos)
            t.actualizar(tiempo);
        for(Tropa t: aliados)
            t.actualizar(tiempo);
    }
}