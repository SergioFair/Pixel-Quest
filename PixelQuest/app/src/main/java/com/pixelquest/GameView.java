package com.pixelquest;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pixelquest.configuracion.GestorTropas;
import com.pixelquest.modelos.Nivel;
import com.pixelquest.modelos.powerups.controles.ControlMana;
import com.pixelquest.modelos.powerups.controles.ControlVida;
import com.pixelquest.modelos.visual.botones.BotonFlecha;
import com.pixelquest.modelos.visual.botones.BotonTropa;
import com.pixelquest.modelos.visual.botones.BotonTropaBoss;
import com.pixelquest.modelos.visual.botones.BotonTropaDistancia;
import com.pixelquest.modelos.visual.botones.BotonTropaLigera;
import com.pixelquest.modelos.visual.botones.BotonTropaPesada;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private boolean iniciado = false;
    private Context context;
    private GameLoop gameLoop;

    public static int pantallaAncho, pantallaAlto;
    public static int FIRST_ROW, SECOND_ROW, THIRD_ROW;

    private List<BotonTropa> botonesTropas;
    private List<BotonFlecha> botonesFlechas;

    private Nivel nivel;
    private ControlMana controlMana;
    private ControlVida controlVida;
    private boolean arrowsEnabled;
    private Random random;
    private int randtime;

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

        for (int i = 0; i < 6; i++) {
            if (accion[i] != NO_ACTION) {
                if (accion[i] == ACTION_DOWN) {
                    if (nivel.isNivelPausado())
                        nivel.setNivelPausado(false);
                    else {
                        for (BotonTropa bt : botonesTropas)
                            if (bt.estaPulsado(x[i], y[i])) {
                                if (!bt.isSelected()
                                        && bt.getCoste() <= controlMana.getMana()) {
                                    deseleccionarBotones();
                                    bt.seleccionar();
                                    setArrowButtons(true);
                                    GestorTropas.getInstance()
                                            .setTropaElegida(botonesTropas.indexOf(bt));
                                } else {
                                    bt.deseleccionar();
                                    setArrowButtons(false);
                                    GestorTropas.getInstance().setTropaElegida(-1);
                                }
                            }
                        for(BotonFlecha bt : botonesFlechas){
                            if(bt.estaPulsado(x[i],y[i]) && arrowsEnabled) {
                                nivel.crearTropaAliada(bt.getY());
                                controlMana.restarMana(botonesTropas.get(
                                        GestorTropas.getInstance().getTropaElegida()).getCoste());
                                this.arrowsEnabled = false;
                                GestorTropas.getInstance().setTropaElegida(-1);
                                deseleccionarBotones();
                            }
                        }
                    }
                }
            }
        }
    }

    private void setArrowButtons(boolean option) {
        this.arrowsEnabled = option;
    }

    private void deseleccionarBotones() {
        for(BotonTropa bt : botonesTropas)
            bt.deseleccionar();
    }

    protected void inicializar() throws Exception {
        nivel = new Nivel(context);
        this.botonesTropas = new LinkedList<>();
        this.botonesFlechas = new LinkedList<>();
        inicializarBotonesTropas();
        inicializarBotonesFlechas();
        nivel.setGameView(this);
        controlMana = new ControlMana(context);
        controlVida = new ControlVida(context);
        random = new Random();
        randtime = random.nextInt(5000)+7000;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int randEnemy = random.nextInt(4)
                        , randRow = random.nextInt(3);
                int row = FIRST_ROW;
                switch(randRow){
                    case 0:
                        row = FIRST_ROW;
                        break;
                    case 1:
                        row = SECOND_ROW;
                        break;
                    case 2:
                        row = THIRD_ROW;
                        break;
                }
                if(nivel.getEnemigos().size()<5)
                    nivel.crearTropaEnemiga(row, randEnemy);
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                controlMana.aumentarMana(1);
            }
        };
        timer.schedule(timerTask,0,randtime);
        timer.schedule(timerTask2,0,1000);
    }

    private void inicializarBotonesFlechas() {
        this.botonesFlechas.add(new BotonFlecha(context,pantallaAncho*0.025,FIRST_ROW));
        this.botonesFlechas.add(new BotonFlecha(context,pantallaAncho*0.025,SECOND_ROW));
        this.botonesFlechas.add(new BotonFlecha(context,pantallaAncho*0.025,THIRD_ROW));
    }

    private void inicializarBotonesTropas() {
        this.botonesTropas.add(new BotonTropaLigera(context));
        this.botonesTropas.add(new BotonTropaDistancia(context));
        this.botonesTropas.add(new BotonTropaPesada(context));
        this.botonesTropas.add(new BotonTropaBoss(context));
    }

    public void actualizar(long tiempo) throws Exception {
        if (!nivel.isNivelPausado()) {
            nivel.actualizar(tiempo);
            for(BotonTropa bt : botonesTropas){
                if(bt.getCoste() <= controlMana.getMana())
                    bt.activar();
                else
                    bt.desactivar();
            }
        }
    }

    protected void dibujar(Canvas canvas) {
        if (!nivel.isNivelPausado()) {
            nivel.dibujar(canvas);
            controlMana.dibujar(canvas);
            for(BotonTropa boton : botonesTropas)
                boton.dibujar(canvas);
            if(arrowsEnabled)
                for(BotonFlecha bt : botonesFlechas)
                    bt.dibujar(canvas);
            controlVida.dibujar(canvas);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format
            , int width, int height) {
        pantallaAncho = width;
        pantallaAlto = height;
        FIRST_ROW = (int) (pantallaAlto*0.25);
        SECOND_ROW = (int) (pantallaAlto*0.45);
        THIRD_ROW = (int) (pantallaAlto*0.63);
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

