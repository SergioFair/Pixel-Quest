package com.pixelquest;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pixelquest.modelos.Nivel;
import com.pixelquest.modelos.powerups.controles.ControlMana;
import com.pixelquest.modelos.powerups.controles.ControlVida;
import com.pixelquest.modelos.tropas.botones.BotonTropa;
import com.pixelquest.modelos.tropas.botones.BotonTropaBoss;
import com.pixelquest.modelos.tropas.botones.BotonTropaDistancia;
import com.pixelquest.modelos.tropas.botones.BotonTropaLigera;
import com.pixelquest.modelos.tropas.botones.BotonTropaPesada;

import java.util.LinkedList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private boolean iniciado = false;
    private Context context;
    private GameLoop gameLoop;

    public static int pantallaAncho;
    public static int pantallaAlto;

    private List<BotonTropa> botones;

    private Nivel nivel;
    private ControlMana controlMana;
    private ControlVida controlVida;
    private Handler handler;

    public GameView(Context context) {
        super(context);
        iniciado = true;

        getHolder().addCallback(this);
        setFocusable(true);

        this.context = context;
        gameLoop = new GameLoop(this);
        gameLoop.setRunning(true);
    }

    public void nivelCompleto() throws Exception {
        if (nivel.getNivelActual() < 1) { // Número Máximo de Nivel
            nivel.aumentarNivel();
        } else {
            nivel.setNivelActual(0);
        }
        inicializar();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;
    }

    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];

    public void procesarEventosTouch() {
        boolean pulsacionPadMover = false;

        for (int i = 0; i < 6; i++) {
            if (accion[i] != NO_ACTION) {
                if (accion[i] == ACTION_DOWN)
                    if (nivel.isNivelPausado())
                        nivel.setNivelPausado(false);

                /*if (botonBoss.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        //nivel.botonSaltarPulsado = true;
                    }
                }
                if (botonLigera.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        //nivel.botonSaltarPulsado = true;
                    }
                }
                if (botonDistancia.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        //nivel.botonSaltarPulsado = true;
                    }
                }
                if (botonPesada.estaPulsado(x[i], y[i])) {
                    if (accion[i] == ACTION_DOWN) {
                        //nivel.botonSaltarPulsado = true;
                    }
                }*/
            }
        }
    }

    protected void inicializar() throws Exception {
        nivel = new Nivel(context, 1);
        this.botones = new LinkedList<>();
        inicializarBotonesTropas();
        nivel.setGameView(this);
        controlMana = new ControlMana(context);
        controlVida = new ControlVida(context);
    }

    private void inicializarBotonesTropas() {
        this.botones.add(new BotonTropaLigera(context));
        this.botones.add(new BotonTropaPesada(context));
        this.botones.add(new BotonTropaDistancia(context));
        this.botones.add(new BotonTropaBoss(context));
    }

    public void actualizar(long tiempo) throws Exception {
        if (!nivel.isNivelPausado()) {
            nivel.actualizar(tiempo);
        }
    }

    protected void dibujar(Canvas canvas) {
        nivel.dibujar(canvas);
        controlMana.dibujar(canvas);
        controlVida.dibujar(canvas);
        if (!nivel.isNivelPausado()) {
            for(BotonTropa boton : botones)
                boton.dibujar(canvas);
        }
        controlMana.dibujar(canvas);
        controlVida.dibujar(canvas);
    }

    public void surfaceChanged(SurfaceHolder holder, int format
            , int width, int height) {
        pantallaAncho = width;
        pantallaAlto = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (iniciado) {
            iniciado = false;
            if (gameLoop.isAlive()) {
                iniciado = true;
                gameLoop = new GameLoop(this);
            }

            gameLoop.setRunning(true);
            gameLoop.start();
        } else {
            iniciado = true;
            gameLoop = new GameLoop(this);
            gameLoop.setRunning(true);
            gameLoop.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        iniciado = false;

        boolean intentarDeNuevo = true;
        gameLoop.setRunning(false);
        while (intentarDeNuevo) {
            try {
                gameLoop.join();
                intentarDeNuevo = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ControlMana getControlMana() {
        return controlMana;
    }
}

