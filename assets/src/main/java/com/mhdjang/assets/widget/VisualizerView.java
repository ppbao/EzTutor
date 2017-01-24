package com.mhdjang.assets.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisualizerView extends View {

    private class Point {

        public static final int MIN_VELOCITY = 2;
        public static final int MAX_VELOCITY = 6;

        private float xRatio;
        private int x;
        private int y;
        private int targetY;
        private float yMulti;
        private boolean reached;
        private boolean xCalculated;

        public Point(float xRatio) {
            this(xRatio, 1f);
        }

        public Point(float xRatio, float yMulti) {
            this.xRatio = Math.min(Math.max(xRatio, 0f), 1f);
            this.yMulti = yMulti;
        }

        public int getX(Rect rect) {
            if (!xCalculated) {
                this.x = (int) (rect.width() * xRatio);
                this.xCalculated = true;
            }
            return x;
        }

        public int getY(Rect rect) {
            return rect.height() - y;
        }

        public void setTargetY(int targetY) {
            this.targetY = (int) (targetY * yMulti);
            this.reached = false;
        }

        public void update() {
            if (reached) {
                y = Math.max(y - MIN_VELOCITY, 0);
            } else {
                int diff = Math.abs(y - targetY);
                int velocity = Math.max(Math.min(diff / 10, MAX_VELOCITY), MIN_VELOCITY);

                if (y > targetY) {
                    y = Math.max(y - velocity, targetY);
                } else if (y < targetY) {
                    y = Math.min(y + velocity, targetY);
                } else {
                    reached = true;
                }
            }
        }

        public boolean hasSomethingToDraw() {
            return !reached || y > 0;
        }
    }

    private class Wave {

        private Paint paint;
        private Path path;
        private Point head;
        private Point tail;
        private List<Point> body;
        private List<Point> points;
        private int position;

        public Wave(Point... points) {
            this(null, points);
        }

        public Wave(Paint paint, Point... points) {
            if (paint != null) {
                this.paint = paint;
            } else {
                this.paint = new Paint();
                this.paint.setColor(Color.argb(140, 39, 198, 177));
            }

            if (points.length < 2) {
                throw new IllegalArgumentException("must provide at least 2 points");
            }

            final int length = points.length;
            this.points = Arrays.asList(points);
            this.head = this.points.get(0);
            this.tail = this.points.get(length - 1);
            this.body = this.points.subList(1, length - 1);

            this.path = new Path();
        }

        public void startIterator() {
            position = 0;
        }

        public Point next() {
            if (position >= 0 && position < points.size()) {
                return points.get(position++);
            }
            return null;
        }

        public void update() {
            for (Point point : points) {
                point.update();
            }
        }

        public void draw(Canvas canvas, Rect rect) {
            path.reset();
            path.moveTo(0, rect.height());
            path.lineTo(head.getX(rect), head.getY(rect));

            Point control = points.get(1);
            path.quadTo(control.getX(rect), control.getY(rect), tail.getX(rect), tail.getY(rect));

            path.lineTo(rect.width(), rect.height());
            path.lineTo(0, rect.height());

            canvas.drawPath(path, paint);
        }

        public boolean hasSomethingToDraw() {
            for (Point point : points) {
                if (point.hasSomethingToDraw()) {
                    return true;
                }
            }
            return false;
        }

    }

    public static final int THRESHOLD = 10;
    public static final String TAG = "Visualizer";

    private byte[] fftBytes;
    private boolean flash = false;
    private Visualizer visualizer;
    private List<Wave> waves;
    private Rect rect = new Rect();

    public VisualizerView(Context context) {
        this(context, null, 0);
    }

    public VisualizerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisualizerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        fftBytes = null;

        waves = new ArrayList<>();
        waves.add(new Wave(new Point(0f, 1.3f), new Point(0.5f, 2f), new Point(1f, 1.5f)));
        waves.add(new Wave(new Point(0f, 1.5f), new Point(0.4f, 1.5f), new Point(0.8f, 0f)));
        waves.add(new Wave(new Point(0.2f, 0f), new Point(0.8f, 2f), new Point(1f, 1.5f)));
    }

    public void link(MediaPlayer player) {
        if (player == null) {
            return;
        }

        if (visualizer != null) {
            return;
        }

        flash = false;

        try {
            setupVisualizer(player.getAudioSessionId());
        } catch (Exception e) {
            e.printStackTrace();
            // visualizer state is not accepted
            // or cannot initialie visualizer engine, error -4
            // or effect library not loaded
        }
    }

    private void setupVisualizer(int audioSessionId) throws IllegalStateException {
        visualizer = new Visualizer(audioSessionId);
        visualizer.setEnabled(false);
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
                                         int samplingRate) {
                updateVisualizerFft(bytes);
            }
        };

        visualizer.setDataCaptureListener(captureListener,
                Visualizer.getMaxCaptureRate() / 4, true, true);
        visualizer.setEnabled(true);
    }

    public void updateVisualizerFft(byte[] bytes) {
        fftBytes = bytes;

        int wavePosition = 0;
        Wave currentWave = null;
        for (int i = 0, l = fftBytes.length / 4; i < l; i++) {
            byte rfk = fftBytes[4 * i];
            byte ifk = fftBytes[4 * i + 1];
            final int dbValue = getDbValue(rfk, ifk);

            if (wavePosition >= waves.size()) {
                break;
            }

            if (currentWave == null) {
                currentWave = waves.get(wavePosition);
                currentWave.startIterator();
            }

            Point point = currentWave.next();
            if (point != null) {
                if (dbValue > THRESHOLD) {
                    point.setTargetY(50 + dbValue);
                } else {
                    point.setTargetY(0);
                }
            } else {
                wavePosition++;
                currentWave = null;
            }
        }

        invalidate();
    }

    private int getDbValue(byte rfk, byte ifk) {
        float magnitude = Math.max(rfk * rfk + ifk * ifk, 1f);
        return (int) (20 * Math.log10(magnitude));
    }

    public void setVisualizerEnabled(boolean enabled) {
        if (visualizer != null) {
            visualizer.setEnabled(enabled);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (flash) {
            return;
        }

        rect.set(0, 0, canvas.getWidth(), canvas.getHeight());

        if (hasSomethingToDraw()) {
            for (Wave wave : waves) {
                wave.update();
                wave.draw(canvas, rect);
            }

            invalidate();
        }
    }

    private boolean hasSomethingToDraw() {
        for (Wave wave : waves) {
            if (wave.hasSomethingToDraw()) {
                return true;
            }
        }
        return false;
    }

    public void flash() {
        flash = true;
        invalidate();
    }

    public void release() {
        if (visualizer != null) {
            visualizer.setDataCaptureListener(null, 0, false, false);
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
    }
}