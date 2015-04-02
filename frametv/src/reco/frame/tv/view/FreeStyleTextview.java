package reco.frame.tv.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * �˿ؼ���ֻ֧����ͤ�� ��δ���� ������ʹ��
 * @author keYence
 *
 */
public class FreeStyleTextview extends TextView {

	public FreeStyleTextview(Context context) {
		super(context);
	}

	public FreeStyleTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FreeStyleTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void changeStyle(){
		Typeface type=Typeface.createFromAsset(getContext().getAssets(), "fonts/lth.ttf");
		this.setTypeface(type);
	}
	
	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(text, type);
		changeStyle();
	}
}
