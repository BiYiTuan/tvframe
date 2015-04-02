package reco.frame.tv.view;

import reco.frame.tv.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * ר���ڵ��ӣ������� ���� ���ַ��
 * 
 * @author xiaanming
 * 
 */
public class TvProgressBar extends View {

	/**
	 * ���ʶ��������
	 */
	private Paint paint;

	/**
	 * Բ������ɫ
	 */
	private int backgroundColor;

	/**
	 * Բ�����ȵ���ʼ��ɫ
	 */
	private int progressStartColor;
	/**
	 * Բ�����ȵ���ĩ��ɫ
	 */
	private int progressEndColor;

	/**
	 * �м���Ȱٷֱȵ��ַ�������ɫ
	 */
	private int textColor;

	/**
	 * �м���Ȱٷֱȵ��ַ���������
	 */
	private float textSize;

	/**
	 * Բ���Ŀ��
	 */
	private float roundWidth;

	/**
	 * Բ�ǰ뾶 Ĭ��Ϊ0 ��ʱ���߽�����Ϊ����
	 */
	private float rectRadius;

	/**
	 * ������
	 */
	private int max;
	/**
	 * ���ȵ�λ
	 */
	private int progressPercent;

	/**
	 * ��ǰ����
	 */
	private int progress;
	private int targetProgress;
	/**
	 * �Ƿ���ʾ�м�Ľ���
	 */
	private boolean textDisplayable;

	/**
	 * ���ȵķ��ʵ�Ļ��߿���
	 */
	private int style;

	public static final int RING = 0;
	public static final int FAN = 1;
	public static final int RECT = 2;

	public TvProgressBar(Context context) {
		this(context, null);
	}

	public TvProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TvProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);


		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.TvProgressBar);

		// ��ȡ�Զ������Ժ�Ĭ��ֵ
		backgroundColor = mTypedArray.getColor(
				R.styleable.TvProgressBar_backgroundColor, Color.TRANSPARENT);
		progressStartColor = mTypedArray.getColor(
				R.styleable.TvProgressBar_progressStartColor, Color.GREEN);
		progressEndColor = mTypedArray.getColor(
				R.styleable.TvProgressBar_progressEndColor, 0);
		textColor = mTypedArray.getColor(R.styleable.TvProgressBar_textColor,
				Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.TvProgressBar_textSize,
				15);
		// textSize=CommonUtil.Px2Dp(context, textSize);//PX ת DP
		rectRadius = mTypedArray.getDimension(
				R.styleable.TvProgressBar_rectRadius, 0);
		roundWidth = mTypedArray.getDimension(
				R.styleable.TvProgressBar_roundWidth, 5);
		max = mTypedArray.getInteger(R.styleable.TvProgressBar_max, 100);
		progressPercent = max / 100;
		textDisplayable = mTypedArray.getBoolean(
				R.styleable.TvProgressBar_textDisplayable, false);
		style = mTypedArray.getInt(R.styleable.TvProgressBar_style, 0);

		mTypedArray.recycle();
		
		 
	}

	private boolean init = true;

	private Shader sweep;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// /**
		// * ���ڽ���ƽ��
		// */
		// if (targetProgress > progress) {
		// progress+=progressPercent;
		// if (progress>=targetProgress) {
		// progress=targetProgress;
		// }
		// }else if(targetProgress<progress){
		// progress=0;
		// }
		paint=new Paint();
		int centre = getWidth() / 2; // ��ȡԲ�ĵ�x����

		paint.setStyle(Paint.Style.STROKE); // ���ÿ���
		paint.setColor(backgroundColor);
		paint.setStrokeWidth(roundWidth); // ����Բ���Ŀ��
		paint.setAntiAlias(true); // �������
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		switch (style) {
		case RING: {
			int radius = (int) (centre - roundWidth / 2); // Բ���İ뾶
			/**
			 * ������ɫ
			 */
			canvas.drawCircle(centre, centre, radius, paint); // ����Բ��

			/**
			 * ��������
			 */
			paint.setStrokeWidth(roundWidth); // ����Բ���Ŀ��
			// ���ý��ȵ���ɫ
			if (progressEndColor == 0) {
				paint.setColor(progressStartColor);
			} else {
				// ����ɫ
				Shader  sweep = new SweepGradient(centre, centre, 
						new int[] {progressStartColor,progressEndColor,progressStartColor
						}, null); 
				paint.setShader(sweep);
			}

			RectF oval = new RectF(centre - radius, centre - radius, centre
					+ radius, centre + radius); // ���ڶ����Բ������״�ʹ�С�Ľ���
			paint.setStyle(Paint.Style.STROKE);
			if (progress != 0)
				canvas.drawArc(oval, -90, 360 * progress / max, false, paint); // ���ݽ��Ȼ�Բ��
			break;
		}
		case FAN: {
			int radius = (int) (centre - roundWidth / 2) + 1; // Բ���İ뾶
			paint.setStyle(Paint.Style.FILL);
			/**
			 * ���ȵ�ɫ
			 */
			if (backgroundColor!=0) {
				canvas.drawCircle(centre, centre, radius, paint); // ����Բ��
			}
			/**
			 * ���Ƚ���
			 */
			// ���ý��ȵ���ɫ
			if (progressEndColor == 0) {
				paint.setColor(progressStartColor);
			} else {
				// ����ɫ
				Shader sweep = new SweepGradient(centre, centre, 
						new int[] {progressStartColor,progressEndColor,progressStartColor
						}, null); 
				paint.setShader(sweep);
			}
			RectF oval = new RectF(centre - radius, centre - radius, centre
					+ radius, centre + radius); // �����Բ������״�ʹ�С�Ľ���
			if (progress != 0)
				canvas.drawArc(oval, -90, 360 * progress / max, true, paint); // ���ݽ��Ȼ�Բ��
				
				
			break;
		}

		case RECT: {
			paint.setStyle(Paint.Style.FILL);
			int current = getWidth() * progress / max;
			/**
			 * ���ߵ�ɫ
			 */
			if (backgroundColor!=0) {
				if (rectRadius>getHeight()) {
					rectRadius=getHeight();
				}
				RectF rectF = new RectF(0, 0, getWidth(), getHeight());
				canvas.drawRoundRect(rectF, rectRadius, rectRadius, paint);
			}
			
			/**
			 * ���߽���
			 */
			// ���ý��ȵ���ɫ
			if (progressEndColor == 0) {
				paint.setColor(progressStartColor);
			} else {
				// ����ɫ
				Shader shader = new LinearGradient(0, 0, current, 0,
						new int[]{progressStartColor, progressEndColor},
						null,Shader.TileMode.REPEAT);
				paint.setShader(shader);
			}

			RectF rectF2 = new RectF(0, 0, current, getHeight());
			canvas.drawRoundRect(rectF2, rectRadius, rectRadius, paint);
			break;
		}
		}

		/**
		 * ����������
		 */
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // ��������
		int percent = (int) (((float) progress / (float) max) * 100); // �м�Ľ��Ȱٷֱȣ���ת����float�ڽ��г������㣬��Ȼ��Ϊ0
		float textWidth = paint.measureText(percent + "%"); // ���������ȣ�������Ҫ��������Ŀ��������Բ���м�

		if (textDisplayable && percent != 0) {

			if (percent < 10) {
				canvas.drawText(" " + percent + "%", centre - textWidth / 2,
						centre + textSize / 2, paint); // �������Ȱٷֱ�
			} else {
				canvas.drawText(percent + "%", centre - textWidth / 2, centre
						+ textSize / 2, paint); // �������Ȱٷֱ�
			}
		}

	}

	public synchronized int getMax() {
		return max;
	}

	/**
	 * ���ý��ȵ����ֵ
	 * 
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * ��ȡ����.��Ҫͬ��
	 * 
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * ���ý��ȣ���Ϊ�̰߳�ȫ�ؼ������ڿ��Ƕ��ߵ����⣬��Ҫͬ�� ˢ�½������postInvalidate()���ڷ�UI�߳�ˢ��
	 * 
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (progress > max) {
			progress = max;
		}
		if (progress <= max) {
			// this.targetProgress=progress;
			this.progress = progress;
			postInvalidate();
		}

	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int cricleColor) {
		this.backgroundColor = cricleColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

}
