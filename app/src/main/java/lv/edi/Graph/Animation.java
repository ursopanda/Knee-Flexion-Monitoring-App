package lv.edi.Graph;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import lv.edi.SmartWear.DisplayCalculations;
import lv.edi.SmartWear.SmartWearApplication;

/**
 * Created by Emil Syundyukov on 12/04/15.
 */
public class Animation extends View {
    Paint paint;
    private SmartWearApplication application; // refference to application instance stores global data

    public Animation(Context context) {
        super(context);
        init();
    }

    public Animation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Animation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(360,550,650,550,paint);
        canvas.rotate(DisplayCalculations.flexionsAngleValue, 360, 550);
        canvas.drawLine(360, 550, 650, 550, paint);
    }
}
