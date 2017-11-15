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

import java.util.ArrayList;
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
        this.enemigos = new ArrayList<>();
        this.aliados = new ArrayList<>();
        fondo = new Fondo(context, CargadorGraficos.cargarBitmap(context,
                R.drawable.background_field), 0);
        this.nivelPausado = false;
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

    public void dibujar(Canvas canvas) {
        fondo.dibujar(canvas);
        for(Iterator<Tropa> iterator = enemigos.iterator();
            iterator.hasNext(); )
            iterator.next().dibujar(canvas);
        for(Iterator<Tropa> iterator = aliados.iterator();
            iterator.hasNext(); )
            iterator.next().dibujar(canvas);
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
        Tropa t = null, aux = null;
        for(Iterator<Tropa> iterator = enemigos.iterator();
            iterator.hasNext(); ){
            t = iterator.next();
            if (t!=null) {
                if(t.estaEnemigoEnPantalla() == -1)
                    t.setEstado(Estados.DESTRUIDO);
                t.mover();
            }
            for(Iterator<Tropa> iteratorAliados = aliados.iterator();
                iteratorAliados.hasNext(); ) {
                aux = iteratorAliados.next();
                if (aux!=null) {
                    if(aux.estaAliadoEnPantalla() == -1)
                        aux.setEstado(Estados.DESTRUIDO);
                    aux.mover();
                }
                if (aux!=null && t!=null && t.colisiona(aux)) {
                    if (t.isSpriteFinalizado() && aux.getEstado() != Estados.INACTIVO
                            && t.getEstado() != Estados.DESTRUIDO) {
                        t.setVelocidad(0);
                        t.setEstado(Estados.ATACANDO);
                        t.atacar(aux);
                    }
                    if (aux.isSpriteFinalizado() && t.getEstado() != Estados.INACTIVO
                            && t.getEstado() != Estados.DESTRUIDO) {
                        aux.setVelocidad(0);
                        aux.setEstado(Estados.ATACANDO);
                        aux.atacar(t);
                    }
                    if(aux.getVida() <= 0) {
                        aux.setEstado(Estados.INACTIVO);
                        t.setEstado(Estados.ACTIVO);
                        t.setVelocidad(t.getVelocidadInicial());
                    }
                    if(t.getVida() <= 0) {
                        t.setEstado(Estados.INACTIVO);
                        aux.setEstado(Estados.ACTIVO);
                        aux.setVelocidad(aux.getVelocidadInicial());
                    }
                }
            }
        }
    }

    public void crearTropaAliada(double y) {
        if(GestorTropas.getInstance().isTropaElegida())
            aliados.add(GestorTropas.getInstance().createAliado(this.context, y));
    }

    public void crearTropaEnemiga(double y, int tipoEnemigo) {
        enemigos.add(GestorTropas.getInstance().createEnemigo(this.context, y, tipoEnemigo));
    }

    public List<Tropa> getEnemigos() {
        return enemigos;
    }
}
