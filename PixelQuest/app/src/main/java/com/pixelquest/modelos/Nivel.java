package com.pixelquest.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.configuracion.Estados;
import com.pixelquest.configuracion.GestorTropas;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.tropas.Tropa;
import com.pixelquest.modelos.tropas.enemigas.TropaLigeraEnemigo;
import com.pixelquest.modelos.visual.Fondo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sergio.
 */

public class Nivel {
    private Context context;
    private List<Tropa> enemigos, aliados;
    private int nivelActual;
    private GameView gameView;
    private boolean nivelPausado;
    private Fondo fondo;

    public Nivel(Context context){
        this.context = context;
        inicializar();
    }

    private void inicializar(){
        this.enemigos = new LinkedList<>();
        this.aliados = new LinkedList<>();
        fondo = new Fondo(context, CargadorGraficos.cargarBitmap(context,
                R.drawable.background_field), 0);
        this.nivelPausado = false;
        this.nivelActual = 1;
        this.enemigos.add(GestorTropas.getInstance().createEnemigo(context
                , GameView.FIRST_ROW));
        this.enemigos.add(GestorTropas.getInstance().createEnemigo(context
                , GameView.SECOND_ROW));
        this.enemigos.add(GestorTropas.getInstance().createEnemigo(context
                , GameView.THIRD_ROW));
        this.enemigos.add(GestorTropas.getInstance().createEnemigo(context
                , GameView.FIRST_ROW));
        this.nivelActual = 1;
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
        Tropa tropa;
        for(Iterator<Tropa> iterator = enemigos.iterator();
            iterator.hasNext(); ) {
            tropa = iterator.next();
            tropa.actualizar(tiempo);
            if(tropa.getEstado() == Estados.DESTRUIDO)
                iterator.remove();
        }
        for(Iterator<Tropa> iterator = aliados.iterator();
            iterator.hasNext(); ) {
            tropa = iterator.next();
            tropa.actualizar(tiempo);
            if (tropa.getEstado() == Estados.DESTRUIDO)
                iterator.remove();
        }
        aplicarReglasMovimiento();
    }

    private void aplicarReglasMovimiento() {
        Tropa t, aux;
        for(Iterator<Tropa> iterator = enemigos.iterator();
            iterator.hasNext(); ) {
            t = iterator.next();
            if(t.estaEnemigoEnPantalla()==-1)
                t.setEstado(Estados.DESTRUIDO);
            t.mover();
            for(Iterator<Tropa> iteratorAliado = aliados.iterator();
                iteratorAliado.hasNext(); ) {
                aux = iteratorAliado.next();
                if (t.colisiona(aux))
                    t.setEstado(Estados.DESTRUIDO);
            }
        }
        for(Iterator<Tropa> iterator = aliados.iterator();
            iterator.hasNext(); ){
            t = iterator.next();
            if(t.estaAliadoEnPantalla()==-1)
                t.setEstado(Estados.DESTRUIDO);
            t.mover();
        }

    }

    public void crearTropa(double y) {
        if(GestorTropas.getInstance().isTropaElegida())
            aliados.add(GestorTropas.getInstance().createAliado(this.context, y));
    }
}
