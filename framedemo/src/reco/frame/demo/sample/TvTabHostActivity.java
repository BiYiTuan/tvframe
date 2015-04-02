package reco.frame.demo.sample;

import reco.frame.demo.R;
import reco.frame.tv.view.TvTabHost;
import reco.frame.tv.view.TvTabHost.ScrollPageChangerListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class TvTabHostActivity extends FragmentActivity {

	private final String TAG="TvTabHostActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvtabhost);
		loadFrag();
	}

	private void loadFrag() {

		/**
		 * ���ҳ��
		 */
		TvTabHost tth_container = (TvTabHost) findViewById(R.id.tth_container);
		tth_container.addPage(getSupportFragmentManager(), new FragmentA(),
				"��ҳ");
		tth_container.addPage(getSupportFragmentManager(), new FragmentB(),
				"��ҳ");
		tth_container.addPage(getSupportFragmentManager(), new FragmentC(),
				"βҳ");
		tth_container.buildLayout();
		
		/**
		 * �����
		 */
		tth_container.setOnPageChangeListener(new ScrollPageChangerListener() {
			
			@Override
			public void onPageSelected(int pageCurrent) {
				
				Log.i(TAG, "�� "+(pageCurrent+1)+" ҳ");
				
			}
		});
		/**
		 * ҳ����ת
		 */
		tth_container.setCurrentPage(0);
	}

}
