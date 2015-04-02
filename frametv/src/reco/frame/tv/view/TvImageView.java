package reco.frame.tv.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import reco.frame.tv.TvBitmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
/**
 * �˿ؼ����ý���
 * @author reco
 *
 */
public class TvImageView extends View {

	public Boolean showLoading = true;
	

	

	public TvImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TvImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TvImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	/**
	 * ��������ͼƬ
	 * @param url
	 */
	public void configImageUrl(String url) {
		
		TvBitmap.create(getContext()).display(this, url);
		
	}
}
