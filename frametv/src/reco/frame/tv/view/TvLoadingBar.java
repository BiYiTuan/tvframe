package reco.frame.tv.view;

import reco.frame.tv.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ������ ����ʹ�� *����ͼ�������Ϊ���� ����Բ����ȫ�Գ�
 * 
 * @author reco
 * 
 */
public class TvLoadingBar extends RelativeLayout {

	private final static int FLUSH = 0;
	/**
	 * ��ǰ����
	 */
	private int progress;
	/**
	 * 
	 */
	private final static int MAX = 100;
	/**
	 * �Ƿ���ʾ�м�Ľ���
	 */
	private boolean textDisplayable;
	/**
	 * �м���Ȱٷֱȵ��ַ�������ɫ
	 */
	private int textColor;

	/**
	 * �м���Ȱٷֱȵ��ַ���������
	 */
	private float textSize;
	private boolean clockWise;
	/**
	 * �������� ԽСת��Խ��
	 */
	private int period;
	private ObjectAnimator rotateAnimator;
	private View loadingBar;
	private int imageRes;
	private TextView tv_progress;

	public TvLoadingBar(Context context) {
		this(context, null);
	}

	public TvLoadingBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TvLoadingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray custom = context.obtainStyledAttributes(attrs,
				R.styleable.TvLoadingBar);

		// ��ȡ�Զ������Ժ�Ĭ��ֵ
		imageRes = custom.getResourceId(R.styleable.TvLoadingBar_imageRes, 0);
		clockWise = custom.getBoolean(R.styleable.TvLoadingBar_clockwise, true);
		period = custom.getInteger(R.styleable.TvLoadingBar_period, 1000);
		textDisplayable = custom.getBoolean(
				R.styleable.TvLoadingBar_textDisplayable, false);
		textColor = custom.getColor(R.styleable.TvLoadingBar_textColor,
				Color.GREEN);
		textSize = custom.getDimension(R.styleable.TvLoadingBar_textSize, 15);
		custom.recycle();

	}

	private boolean initFlag=true;
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (initFlag) {
			initFlag=false;
			initChild();
		}
		super.onLayout(changed, l, t, r, b);
	}

	private void initChild() {
		
		loadingBar = new View(getContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				getWidth(), getHeight());
		params.addRule(RelativeLayout.ALIGN_LEFT);
		params.addRule(RelativeLayout.ALIGN_TOP);
		params.setMargins(1, 1, 1, 1);
		loadingBar.setBackgroundResource(imageRes);
		this.addView(loadingBar, params);

		tv_progress = new TextView(getContext());
		tv_progress.setTextSize(textSize);
		tv_progress.setTextColor(textColor);
		tv_progress.setGravity(Gravity.CENTER);
		this.addView(tv_progress, params);
		startAnim();

	}

	/**
	 * ������ת
	 */
	public void startAnim() {
		rotateAnimator = ObjectAnimator.ofFloat(loadingBar, "rotation", 0.0F,
				359.0F).setDuration(period);
		rotateAnimator.setRepeatCount(-1);
		rotateAnimator.setInterpolator(new LinearInterpolator());
		rotateAnimator.start();

	}

	/**
	 * ֹͣ��ת
	 */
	public void stopAnim() {
		rotateAnimator.cancel();
	}

	/**
	 * ��ȡ����.��Ҫͬ��
	 * 
	 * @return
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * ���ý���
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (progress > MAX) {
			progress = MAX;
		}
		if (progress <= MAX&&textDisplayable) {
			this.progress = progress;
			if (progress < 10) {
				tv_progress.setText(" " + progress + "%");
			} else {
				tv_progress.setText(progress + "%");
			}
		}
	}

}
