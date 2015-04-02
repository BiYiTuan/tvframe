package reco.frame.tv.view;

import reco.frame.tv.R;
import reco.frame.tv.TvBitmap;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * ר���ڵ��ӣ��丸������Ϊ RelativeLayout
 * 
 * @author reco
 * 
 */
public class TvRelativeLayout extends RelativeLayout {
	private final int SCREEN_720 = 0, SCREEN_1080 = 1;
	/**
	 * ���
	 */
	private ImageView cursor;
	private final String cursorTag = "TvRelativeLayout";
	/**
	 * �����Դ
	 */
	private int cursorRes;
	/**
	 * �ɷ�����
	 */
	private boolean scalable;
	/**
	 * �Ŵ����
	 */
	private float scale;
	/**
	 * ���Ʈ�ƶ��� Ĭ����Ч��
	 */
	private int animationType;
	public final static int ANIM_DEFAULT = 0;// ��Ч��
	public final static int ANIM_TRASLATE = 1;// ƽ��
	/**
	 * �Ŵ���ʱ
	 */
	private int durationLarge = 100;
	/**
	 * ��С��ʱ
	 */
	private int durationSmall = 100;
	/**
	 * �����ӳ�
	 */
	private int delay = 110;
	/**
	 * ���߿��� ������Ӱ
	 */
	private int boarder;
	/**
	 * �����߿��� ����Ӱ
	 */
	private int boarderLeft;
	/**
	 * ��궥�߿��� ����Ӱ
	 */
	private int boarderTop;
	/**
	 * ����ұ߿��� ����Ӱ
	 */
	private int boarderRight;
	/**
	 * ���ױ߿��� ����Ӱ
	 */
	private int boarderBottom;

	private boolean initFlag;

	public TvRelativeLayout(Context context) {
		this(context, null);
	}

	public TvRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TvRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray custom = getContext().obtainStyledAttributes(attrs,
				R.styleable.TvRelativeLayout);
		this.cursorRes = custom.getResourceId(
				R.styleable.TvRelativeLayout_cursorRes, 0);
		this.scalable = custom.getBoolean(
				R.styleable.TvRelativeLayout_scalable, true);
		this.scale = custom.getFloat(R.styleable.TvRelativeLayout_scale, 1.1f);
		this.animationType = custom.getInt(
				R.styleable.TvRelativeLayout_animationType, 0);
		this.delay = custom.getInteger(R.styleable.TvRelativeLayout_delay, 10);
		this.durationLarge = custom.getInteger(
				R.styleable.TvRelativeLayout_durationLarge, 100);
		this.durationSmall = custom.getInteger(
				R.styleable.TvRelativeLayout_durationSmall, 100);
		this.boarder = (int) custom.getDimension(
				R.styleable.TvRelativeLayout_boarder, 0);

		if (boarder == 0) {
			this.boarderLeft = (int) custom.getDimension(
					R.styleable.TvRelativeLayout_boarderLeft, 0);
			this.boarderTop = (int) custom.getDimension(
					R.styleable.TvRelativeLayout_boarderTop, 0);
			this.boarderRight = (int) custom.getDimension(
					R.styleable.TvRelativeLayout_boarderRight, 0);
			this.boarderBottom = (int) custom.getDimension(
					R.styleable.TvRelativeLayout_boarderBottom, 0);
		} else {
			this.boarderLeft = boarder;
			this.boarderTop = boarder;
			this.boarderRight = boarder;
			this.boarderBottom = boarder;
		}

		/**
		 * �˴���С�ģ��������Բ�� �ұ߿��߼�ϸ ���ӷֱ��ʲ�ͬ ��Բ�����Ŵ���밴ť�����Ǻ� ����������������ֱ���
		 * ��������������.9.png��ʽѡ��ͼ��������������ֱ��ʣ�
		 * 
		 */
		// int screenType = Integer.parseInt(getResources().getString(
		// R.string.screen_type));
		// if (cursorRes == 0) {
		// switch (screenType) {
		// case SCREEN_720:
		// cursorRes = R.drawable.cursor_720;
		// break;
		//
		// case SCREEN_1080:
		// cursorRes = R.drawable.cursor_1080;
		// break;
		// }
		// }
		custom.recycle();

		// Ĭ�Ͽɵý���
		setFocusable(true);

		// �ر��ӿؼ��������� ʹǶ�׶���������
		setAnimationCacheEnabled(false);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					moveCover();
				}
			}, delay);

		} else {
			removeCover();
		}
	}

	/**
	 * ����ƶ� ����� ��ؼ�ͬʱ�Ŵ�
	 */
	private void moveCover() {

		cursor = (ImageView) ((RelativeLayout) getParent())
				.findViewWithTag(cursorTag);
		if (cursor == null) {
			cursor = new ImageView(getContext());
			cursor.setTag(cursorTag);
			cursor.setBackgroundResource(cursorRes);

			((RelativeLayout) getParent()).addView(cursor);
		}
		switch (animationType) {
		case ANIM_DEFAULT:
			setBorderParams();
			this.bringToFront();
			cursor.bringToFront();
			if (scalable) {
				scaleToLarge();
			}
			break;

		// case ANIM_TRASLATE:
		// ObjectAnimator transAnimatorX = ObjectAnimator.ofFloat(cursor,
		// "x", this.getX());
		// ObjectAnimator transAnimatorY = ObjectAnimator.ofFloat(cursor,
		// "y", this.getY());
		// break;

		}

	}

	/**
	 * ��ԭ�ؼ�״̬
	 */

	public void removeCover() {
		if (cursor != null) {
			cursor.setVisibility(View.INVISIBLE);
		}

		if (scalable) {
			scaleToNormal();
		}
	}

	private AnimatorSet animatorSet;
	private ObjectAnimator largeX;

	private void scaleToLarge() {

		if (!this.isFocused()) {
			return;
		}

		animatorSet = new AnimatorSet();
		largeX = ObjectAnimator.ofFloat(this, "ScaleX", 1f, scale);
		ObjectAnimator largeY = ObjectAnimator.ofFloat(this, "ScaleY", 1f,
				scale);
		ObjectAnimator cursorX = ObjectAnimator.ofFloat(cursor, "ScaleX", 1f,
				scale);
		ObjectAnimator cursorY = ObjectAnimator.ofFloat(cursor, "ScaleY", 1f,
				scale);

		animatorSet.setDuration(durationLarge);
		animatorSet.play(largeX).with(largeY).with(cursorX).with(cursorY);

		animatorSet.start();
	}

	public void scaleToNormal() {
		if (animatorSet == null) {
			return;
		}
		// float scaleNow=(Float) largeX.getAnimatedValue("ScaleX");
		// if (scaleNow<=0) {
		// scaleNow=1;
		// }
		if (animatorSet.isRunning()) {
			animatorSet.cancel();
		}
		// Log.e(VIEW_LOG_TAG, "scaleNow="+scaleNow);

		ObjectAnimator oa = ObjectAnimator.ofFloat(this, "ScaleX", 1f);
		oa.setDuration(durationSmall);
		oa.start();
		ObjectAnimator oa2 = ObjectAnimator.ofFloat(this, "ScaleY", 1f);
		oa2.setDuration(durationSmall);
		oa2.start();
	}

	/**
	 * ��������ͼƬ��ַ
	 * 
	 * @param url
	 */
	public void configImageUrl(String url) {

		TvBitmap.create(getContext()).display(this, url);

	}

	/**
	 * ָ��������λ��
	 */
	private void setBorderParams() {
		cursor.clearAnimation();
		cursor.setVisibility(View.VISIBLE);

		// �ж�����

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cursor
				.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_LEFT, this.getId());
		params.addRule(RelativeLayout.ALIGN_TOP, this.getId());

		int coverLeft = 0 - boarderLeft;
		int coverTop = 0 - boarderTop;

		params.leftMargin = coverLeft;
		params.topMargin = coverTop;

		params.width = boarderLeft + getWidth() + boarderRight;
		params.height = boarderBottom + getHeight() + boarderTop;
		
		
		cursor.setLayoutParams(params);

		
	}

}
