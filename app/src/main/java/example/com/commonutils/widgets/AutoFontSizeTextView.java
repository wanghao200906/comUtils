package example.com.commonutils.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import example.com.commonutils.R;

/**
 * 创建日期: 16/3/2 下午11:29
 * 作者:wanghao
 * 描述:
 * 让字体根据 显示的宽度 高度 来让字体大小缩小,如果可以展示开不用缩小
 * 1,先限定宽度,如果字体宽度大于限定宽度,让字体换行
 * 2,换行后获取他的高度,让换行的高度和 限定的高度对比,不断地轮训让字体减1
 * 3,随着字体不断的减小,当宽度,高度都小于限定的宽高的时候 获取该字体大小并显示
 * <p>
 * float textSize = setStretchTextView(mSwimText[4], 576, 105, paintTag, Layout.Alignment.ALIGN_CENTER, null);
 * paintTag.setTextSize(textSize);
 * <p>
 * <p>
 * 支持设置字体行数
 */

public class AutoFontSizeTextView extends AppCompatTextView {
    private static final String TAG = "AutoFontSizeTextView";
    private CharSequence textContent;//textview的内容
    private float mSpacingMult = 1.0f;//StaticLayout
    private float mSpacingAdd = 0.0f;//StaticLayout
    private int LayoutAlign = 0;//StaticLayout 的LayoutAlign
    private Layout.Alignment alignment;//StaticLayout 的LayoutAlign  设置
    private float minSize = 1.0f;//默认textsize的最小值
    private int widthL;//限制字体的宽度
    private int heightL;//限制字体的高度
    private float cacheTargetsize;//目标字体大小 缓存
    private float newSize = 0;//目标字体大小 最终版
    private boolean isMeasure = false;

    public AutoFontSizeTextView(Context context) {
        this(context, null);
    }

    public AutoFontSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public AutoFontSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoFontSizeTextViewStyle);//获取StaticLayout的三个值
        LayoutAlign = a.getInteger(R.styleable.AutoFontSizeTextViewStyle_layoutalign, 0);
        mSpacingMult = a.getInteger(R.styleable.AutoFontSizeTextViewStyle_mSpacingMult, 1);
        mSpacingAdd = a.getInteger(R.styleable.AutoFontSizeTextViewStyle_mSpacingAdd, 0);
        switch (LayoutAlign) {
            case 0:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            case 1:
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
            case 2:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            default:
                break;
        }
        a.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        isMeasure=true;
        Log.e(TAG, "onMeasure: ");
        autoFitSize();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout: ");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: ");

    }

    private void autoFitSize() {

        if (!isMeasure) {
            return;
        }
        //获取字体打算展示的宽高
        widthL = getMeasuredWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight();
        heightL = getMeasuredHeight() - getCompoundPaddingBottom() - getCompoundPaddingTop();

        textContent = getText();
        Log.i(TAG, textContent.toString());

        TextPaint tPaint = new TextPaint(getPaint());
        int displayW = (int) tPaint.measureText(textContent.toString());
        float size = tPaint.getTextSize();
        int line = getMaxLines((TextView) this);


        if (line == 1) {
            while (displayW / line > widthL) {
                size = size - 1;
                changeTextSize(size);
                TextPaint tPaint1 = new TextPaint(getPaint());
                displayW = (int) tPaint1.measureText((String) textContent);
            }
            TextPaint tPaintTemp = new TextPaint(getPaint());
            Rect bounds = new Rect();
            tPaint.getTextBounds((String) textContent, 0, textContent.length(), bounds);
            Paint.FontMetrics mFontMetrics = tPaint.getFontMetrics();

            int textHeight = bounds.height() + getCompoundPaddingBottom() + getCompoundPaddingTop();
            int textWidth = (int) tPaintTemp.measureText((String) textContent) + getCompoundPaddingLeft() + getCompoundPaddingRight();
            setMeasuredDimension(textWidth, (int) (textHeight + mFontMetrics.bottom * 2));
        } else {
            setNewText(widthL, heightL);
        }
    }

    private static int getMaxLines(TextView view) {
        int maxLines = -1; // No limit (Integer.MAX_VALUE also means no limit)

        TransformationMethod method = view.getTransformationMethod();
        if (method != null && method instanceof SingleLineTransformationMethod) {
            maxLines = 1;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // setMaxLines() and getMaxLines() are only available on android-16+
            maxLines = view.getMaxLines();
        }

        return maxLines;
    }


    /**
     * 更改字体大小
     *
     * @param newTextSize
     */
    public void changeTextSize(float newTextSize) {
        Log.e(TAG, "changeTextSize: " + newTextSize);

        setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
    }


    /**
     * 根据这些参数来配置字体大小
     *
     * @param text
     * @param width
     * @param height
     * @param paint
     * @param alignment
     * @param canvas
     * @return
     */
    public float setStretchTextView(String text, int width, int height, TextPaint paint, Layout.Alignment alignment, Canvas canvas) {

        cacheTargetsize = paint.getTextSize();
        int cacheHeight = getHeightByWidth(text, alignment, paint, width, cacheTargetsize).getHeight();
        while (cacheHeight > height && cacheTargetsize > minSize) {
            cacheTargetsize = Math.max(cacheTargetsize - 1, minSize);
            cacheHeight = getHeightByWidth(text, alignment, paint, width, cacheTargetsize).getHeight();
        }
        if (canvas != null) {
            getHeightByWidth(text, alignment, paint, width, cacheTargetsize).draw(canvas);
        }
        return cacheTargetsize;
    }

    /**
     * 根据宽高 设置字体大小
     *
     * @param widthL
     * @param heightL
     */
    private void setNewText(int widthL, int heightL) {
        this.widthL = widthL;
        this.heightL = heightL;
        if (widthL >= 0 && heightL >= 0 && textContent != null && textContent.length() > 0) {
            TextPaint tPaint = new TextPaint(getPaint());
            newSize = setNewSize(textContent.toString().trim(), widthL, heightL, tPaint, alignment);
        }
        changeTextSize(newSize);
    }


    private float setNewSize(String text, int width, int height, TextPaint paint, Layout.Alignment alignment) {
        cacheTargetsize = setStretchTextView(text, width, height, paint, alignment, null);
        return cacheTargetsize;
    }

    /**
     * 宽度一定的时候,让文字换行 获取他的StaticLayout  (为了让他在自定义view的时候画出字体,获取换行之后的高度)
     *
     * @param source
     * @param align
     * @param paint
     * @param width
     * @param textSize
     * @return
     */
    private StaticLayout getHeightByWidth(CharSequence source, Layout.Alignment align, TextPaint paint, int width, float textSize) {
        paint.setTextSize(textSize);
        mSpacingMult = mSpacingMult < 1.0 ? 1.0f : mSpacingMult;
        StaticLayout layout = new StaticLayout(source, paint, width, align, mSpacingMult, mSpacingAdd, true);
        return layout;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e(TAG, "onTextChanged: " + s);
        Log.e(TAG, "onTextChanged: " + start);
        Log.e(TAG, "onTextChanged: " + before);
        Log.e(TAG, "onTextChanged: " + count);
        autoFitSize();
    }

}
