package com.pixelquest.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

import com.pixelquest.GameView;
import com.pixelquest.R;
import com.pixelquest.configuracion.Estados;
import com.pixelquest.configuracion.GestorTropas;
import com.pixelquest.gestores.CargadorGraficos;
import com.pixelquest.modelos.powerups.PowerUpMana;
import com.pixelquest.modelos.powerups.PowerUpVida;
import com.pixelquest.modelos.tropas.Flecha;
import com.pixelquest.modelos.tropas.Tropa;
import com.pixelquest.modelos.tropas.aliadas.TropaDistanciaAliada;
import com.pixelquest.modelos.tropas.enemigas.TropaDistanciaEnemigo;
import com.pixelquest.modelos.visual.Fondo;

import java.util.ArrayList;
import java.util.Iterator;
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
    private List<Flecha> proyectiles;
    private PowerUpMana pUpMana;
    private PowerUpVida pUpVida;
    private boolean nivelFinalizado;

    public Nivel(Context context){
        this.context = context;
        inicializar();
    }

    private void inicializar(){
        this.enemigos = new ArrayList<>();
        this.aliados = new ArrayList<>();
        this.proyectiles = new ArrayList<>();
        fondo = new Fondo(context, CargadorGraficos.cargarBitmap(context,
                R.drawable.background_field), 0);
        this.nivelPausado = false;
        nivelFinalizado = false;
        this.nivelActual = 1;
    }

    public void inicializarPowerUps() {
        this.pUpMana = new PowerUpMana(context, gameView.getControlMana()
                ,(int) (GameView.pantallaAncho/1.5),GameView.FIRST_ROW);
        this.pUpVida = new PowerUpVida(context, gameView.getControlVida()
                ,GameView.pantallaAncho/3, GameView.THIRD_ROW);
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
        Tropa t;
        Iterator<Tropa> iterator;
        fondo.dibujar(canvas);
        if(pUpMana!=null)
            pUpMana.dibujar(canvas);
        if(pUpVida!=null)
            pUpVida.dibujar(canvas);
        synchronized (this.enemigos){
            iterator = enemigos.iterator();
            while(iterator.hasNext()) {
                t = iterator.next();
                t.dibujar(canvas);
            }
        }
        synchronized (this.aliados){
            iterator = aliados.iterator();
            while(iterator.hasNext()) {
                t = iterator.next();
                t.dibujar(canvas);
            }
        }
        for(Flecha f : proyectiles)
            f.dibujar(canvas);
    }

    public boolean isNivelPausado() {
        return nivelPausado;
    }

    public void setNivelPausado(boolean nivelPausado) {
        this.nivelPausado = nivelPausado;
    }

    public void actualizar(long tiempo) {
        Tropa tropa;
        Iterator<Tropa> iterator;
        if(!isNivelPausado()) {
            if (pUpMana != null)
                pUpMana.actualizar(tiempo);
            if (pUpVida != null)
                pUpVida.actualizar(tiempo);
            synchronized (this.enemigos) {
                iterator = enemigos.iterator();
                while (iterator.hasNext()) {
                    tropa = iterator.next();
                    tropa.actualizar(tiempo);
                    if (tropa.getEstado() == Estados.DESTRUIDO
                            || tropa.getEstado() == Estados.CRUZANDO) {
                        iterator.remove();
                        if(tropa.getEstado() == Estados.CRUZANDO) {
                            gameView.getControlVida().reducirVidaJugador();
                            if(gameView.getControlVida().getVidaJugador() <= 0)
                                gameView.jugadorPierde();
                        }
                    }
                }
            }
            synchronized (aliados) {
                iterator = aliados.iterator();
                while (iterator.hasNext()) {
                    tropa = iterator.next();
                    tropa.actualizar(tiempo);
                    if (tropa.getEstado() == Estados.DESTRUIDO
                            || tropa.getEstado() == Estados.CRUZANDO) {
                        iterator.remove();
                        if(tropa.getEstado() == Estados.CRUZANDO){
                            gameView.getControlVida().reducirVidaEnemigo();
                            if(gameView.getControlVida().getVidaEnemigo() <= 0)
                                gameView.jugadorGana();
                        }
                    }
                }
            }
            aplicarReglasMovimiento();
        }
    }

    private void aplicarReglasMovimiento() {
        Tropa t = null, aux = null;
        Iterator<Tropa> iterator, iteratorAliados;
        for(Flecha f : proyectiles)
            f.acelerar(f.getVelocidad());
        synchronized (enemigos) {
            iterator = enemigos.iterator();
            while (iterator.hasNext()) {
                t = iterator.next();
                if (t != null) {
                    if (t.estaEnemigoEnPantalla() == -1) {
                        t.setEstado(Estados.CRUZANDO);
                    } else
                        t.mover();
                }
                synchronized (aliados) {
                    iteratorAliados = aliados.iterator();
                    while (iteratorAliados.hasNext()) {
                        aux = iteratorAliados.next();
                        if (aux != null) {
                            if (aux.estaAliadoEnPantalla() == -1) {
                                aux.setEstado(Estados.CRUZANDO);
                            } else
                                aux.mover();
                        }
                        if (aux != null && aux.colisiona(t)) {
                            if (aux.isSpriteFinalizado() && t.getEstado() != Estados.INACTIVO
                                    && t.getEstado() != Estados.DESTRUIDO) {
                                aux.setVelocidad(0);
                                aux.setEstado(Estados.ATACANDO);
                                if (aux instanceof TropaDistanciaAliada)
                                    proyectiles.add(new Flecha(context
                                            , ((TropaDistanciaAliada) aux).getX()
                                            , ((TropaDistanciaAliada) aux).getY()
                                            , 1));
                                aux.atacar(t);
                            }
                        }
                        if (t != null && t.colisiona(aux)) {
                            if (t.isSpriteFinalizado() && aux.getEstado() != Estados.INACTIVO
                                    && t.getEstado() != Estados.DESTRUIDO) {
                                t.setVelocidad(0);
                                t.setEstado(Estados.ATACANDO);
                                if (t instanceof TropaDistanciaEnemigo)
                                    proyectiles.add(new Flecha(context
                                            , ((TropaDistanciaEnemigo) t).getX()
                                            , ((TropaDistanciaEnemigo) t).getY()
                                            , -1));
                                t.atacar(aux);
                            }
                        }
                        if (aux.getVida() <= 0) {
                            aux.setEstado(Estados.INACTIVO);
                            t.setEstado(Estados.ACTIVO);
                            t.setVelocidad(t.getVelocidadInicial());
                        }
                        if (t.getVida() <= 0) {
                            t.setEstado(Estados.INACTIVO);
                            aux.setEstado(Estados.ACTIVO);
                            aux.setVelocidad(aux.getVelocidadInicial());
                        }
                        if (pUpMana != null && pUpMana.colisiona(aux)) {
                            pUpMana.execute();
                            pUpMana = null;
                        }
                        if (pUpVida != null && pUpVida.colisiona(aux)) {
                            pUpVida.execute();
                            pUpVida = null;
                        }
                    }
                }
            }
        }
    }

    public void crearTropaAliada(double y) {
        if(GestorTropas.getInstance().isTropaElegida())
            synchronized (aliados) {
                aliados.add(GestorTropas.getInstance().createAliado(this.context, y));
            }
    }

    public void crearTropaEnemiga(double y, int tipoEnemigo) {
        synchronized (enemigos) {
            enemigos.add(GestorTropas.getInstance().createEnemigo(this.context, y, tipoEnemigo));
        }
    }

    public List<Tropa> getEnemigos() {
        return enemigos;
    }

    public boolean isNivelFinalizado() {
        return nivelFinalizado;
    }

    public void setNivelFinalizado(boolean nivelFinalizado) {
        setNivelPausado(nivelFinalizado);
        this.nivelFinalizado = nivelFinalizado;
    }
}
