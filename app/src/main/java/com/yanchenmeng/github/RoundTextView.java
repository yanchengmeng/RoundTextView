package com.yanchenmeng.github;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
/**
 * Created by yanchengmeng on 2016/12/22.
 * 自定义圆角、圆形、边框TextView
 */
public class RoundTextView extends View {

    /**
     * 文本内容
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 背景颜色
     */
    private int rtvBackgroundColor;

    /**
     * 圆角大小
     */
    private float mCornerSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mtitleBound;
    private Paint mtitlePaint;

    /**
     * 是否带边框
     */
    private boolean isWithBorder;

    /**
     * 边框大小
     */
    private float borderWidth;

    /**
     * 边框颜色
     */
    private int mBorderColor;


    /**
     * 是否是圆形
     */
    private boolean isCirCle = false;


    public RoundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context) {
        this(context, null);
    }

    /**
     * 设置背景颜色
     *
     * @param rtvBackgroundColor
     */
    public void setBackgroundColor(int rtvBackgroundColor) {
        this.rtvBackgroundColor = rtvBackgroundColor;
        invalidate();
    }

    /**
     * 设置文字颜色
     *
     * @param mTitleTextColor
     */
    public void setTextColor(int mTitleTextColor) {
        this.mTitleTextColor = mTitleTextColor;
        invalidate();
    }

    /**
     * 设置文字字体大小
     *
     * @param mTitleTextSize
     */
    public void setTextSize(int mTitleTextSize) {
        this.mTitleTextSize = mTitleTextSize;
        invalidate();
    }

    /**
     * 设置文字
     *
     * @param mTitleText
     */
    public void setText(String mTitleText) {
        this.mTitleText = StringUtil.isEmpty(mTitleText) ? "" : mTitleText;
        invalidate();
    }


    /**
     * 设置圆角大小
     *
     * @param mCornerSize
     */
    public void setCorner(float mCornerSize) {
        this.mCornerSize = mCornerSize;
        invalidate();
    }

    /**
     * 设置边框大小
     *
     * @param borderWidth
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * 设置是否需要带上边框
     *
     * @param isWithBorder
     */
    public void setIsWithBorder(boolean isWithBorder) {
        this.isWithBorder = isWithBorder;
    }

    /**
     * 设置边框颜色
     *
     * @param mBorderColor
     */
    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
        invalidate();
    }


    /**
     * 设置是否是圆形
     *
     * @param isCirCle
     */
    public void setIsCirCle(boolean isCirCle) {
        this.isCirCle = isCirCle;
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RoundTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundTextView, defStyle, 0);
        mTitleText = a.getString(R.styleable.RoundTextView_rtvText);
        rtvBackgroundColor = a.getColor(R.styleable.RoundTextView_rtvBackground, getResources().getColor(android.R.color.black));// 默认背景黑色
        mTitleTextColor = a.getColor(R.styleable.RoundTextView_rtvTextColor, Color.WHITE);                                       // 默认文字颜色黑色
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.RoundTextView_rtvTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));                       // 默认文字字体大小16sp
        mCornerSize = a.getFloat(R.styleable.RoundTextView_rtvCornerSize, 0);                               // 默认圆角度数为0
        mBorderColor = a.getColor(R.styleable.RoundTextView_rtvBorderColor, Color.BLACK);                     // 默认边框颜色黑色
        borderWidth = a.getFloat(R.styleable.RoundTextView_rtvBorderWidth, DisplayUtil.dip2px(context, 2)); // 默认边框大小2dp
        isWithBorder = a.getBoolean(R.styleable.RoundTextView_rtvIsWithBorder, false);                        // 默认不带边框
        isCirCle = a.getBoolean(R.styleable.RoundTextView_rtvIsCircle, false);                                 // 默认不是圆形
        a.recycle();
        mtitlePaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        mtitlePaint.setAntiAlias(true);
        mtitlePaint.setTextSize(mTitleTextSize);
        mtitlePaint.setTextAlign(Paint.Align.LEFT);
        mtitleBound = new Rect();
        mTitleText = StringUtil.isEmpty(mTitleText) ? "" : mTitleText;
        mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mtitleBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mtitlePaint.setTextSize(mTitleTextSize);
            mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mtitleBound);
            int desired = getPaddingLeft() + mtitleBound.width() + getPaddingRight();
            width = desired <= widthSize ? desired : widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mtitlePaint.setTextSize(mTitleTextSize);
            mtitlePaint.getTextBounds(mTitleText, 0, mTitleText.length(), mtitleBound);
            int desired = getPaddingTop() + mtitleBound.height() + getPaddingBottom();
            height = desired <= heightSize ? desired : heightSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCirCle) {
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
            Paint borderPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
            paint.setAntiAlias(true);
            paint.setDither(true);//防抖动
            paint.setColor(rtvBackgroundColor);
            if (isWithBorder) {
                // 边框
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setStrokeWidth(DisplayUtil.dip2px(getContext(), borderWidth));
                borderPaint.setAntiAlias(true);
                borderPaint.setDither(true);//防抖动
                borderPaint.setColor(mBorderColor);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2 - DisplayUtil.dip2px(getContext(), borderWidth), borderPaint);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2 - DisplayUtil.dip2px(getContext(), borderWidth), paint);
                Log.e("onDraw", "isWithBorder");
            } else {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, paint);
            }
        } else {
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
            Paint borderPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
            RectF rec = new RectF(DisplayUtil.dip2px(getContext(), borderWidth), DisplayUtil.dip2px(getContext(), borderWidth), getMeasuredWidth() - DisplayUtil.dip2px(getContext(), borderWidth), getMeasuredHeight() - DisplayUtil.dip2px(getContext(), borderWidth));
            if (isWithBorder) {
                // 边框
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setStrokeWidth(DisplayUtil.dip2px(getContext(), borderWidth));
                borderPaint.setAntiAlias(true);
                borderPaint.setDither(true);//防抖动
                borderPaint.setColor(mBorderColor);
                canvas.drawRoundRect(rec, DisplayUtil.dip2px(getContext(), mCornerSize), DisplayUtil.dip2px(getContext(), mCornerSize), borderPaint);
                Log.e("onDraw", "isWithBorder");
            } else {
                rec = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
            // 背景
            paint.setAntiAlias(true);
            paint.setDither(true);//防抖动
            paint.setColor(rtvBackgroundColor);
            canvas.drawRoundRect(rec, DisplayUtil.dip2px(getContext(), mCornerSize), DisplayUtil.dip2px(getContext(), mCornerSize), paint);
        }

        // 文字
        mtitlePaint.setColor(mTitleTextColor);

        Paint.FontMetricsInt fontMetrics = mtitlePaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        // 默认居中显示，设置setTextAlign(Paint.Align.LEFT);并设置起点位置
        canvas.drawText(mTitleText, getMeasuredWidth() / 2 - mtitleBound.width() / 2, baseline, mtitlePaint);
//        TypedValue outValue = new TypedValue();
//        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,outValue, true);
//        setBackgroundResource(outValue.resourceId);
//        super.onDraw(canvas);
//        setBackgroundDrawable(this.setbg());
    }
    //选中效果
//    public StateListDrawable setbg() {
//        StateListDrawable bg = new StateListDrawable();
//        Drawable normal = this.getResources().getDrawable(R.color.colorAccent);
//        Drawable selected = this.getResources().getDrawable(R.color.hwpush_bgcolor);
//        Drawable pressed = this.getResources().getDrawable(R.color.hwpush_bgcolor);
//        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
//        bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);
//        bg.addState(View.ENABLED_STATE_SET, normal);
//        bg.addState(View.FOCUSED_STATE_SET, selected);
//        bg.addState(View.EMPTY_STATE_SET, normal);
//        return bg;
//    }
}
