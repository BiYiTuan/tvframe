package reco.frame.demo.sample;

import java.util.ArrayList;
import java.util.List;
import reco.frame.demo.R;
import reco.frame.demo.adapter.TvGridAdapter;
import reco.frame.demo.entity.AppInfo;
import reco.frame.tv.view.TvGridView;
import reco.frame.tv.view.TvGridView.OnItemClickListener;
import reco.frame.tv.view.TvGridView.OnItemSelectListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;

public class TvGridViewActivity extends Activity {

	private String TAG = "TvGridViewActivity";
	private TvGridView tgv_imagelist;
	private TvGridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvgridview);
		tgv_imagelist = (TvGridView) findViewById(R.id.tgv_imagelist);
		load();

	}

	/**
	 * ��ʼ����45������ Ҳ��һ�μ��� 1000+���� TvGridView�������ܸ߶�Ϊ��Ļ�߶�2λ������ ���赣�����
	 * ����ƶ���ҳβʱ,���Զ����ظ���
	 */
	private void load() {

		// ��ʼ����ģ������

		List<AppInfo> appList = new ArrayList<AppInfo>();
		for (int i = 0; i < 45; i++) {
			AppInfo app = new AppInfo();
			app.title = "ȫ�Һп��" + i;
			appList.add(app);

		}
		adapter = new TvGridAdapter(getApplicationContext(), appList);
		tgv_imagelist.setAdapter(adapter);
		
		/**
		 * �˴�����100����������
		 */
//		TvHttp http = new TvHttp(this);
//		http.get(Config.TEST_DATA_API, new AjaxCallBack<Object>() {
//			@Override
//			public void onSuccess(Object t) {
//				// Log.i(TAG, t.toString());
//				try {
//					List<AppInfo> appList = new ArrayList<AppInfo>();
//					JSONArray jsonArray = new JSONObject(t.toString())
//							.getJSONArray("data_list");
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject jObject = jsonArray.getJSONObject(i);
//						AppInfo app = new AppInfo();
//						app.title = jObject.getString("title");
//						appList.add(app);
//					}
//					 adapter=new TvGridAdapter(getApplicationContext(),
//					 appList);
//					 tgv_imagelist.setAdapter(adapter);
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				super.onSuccess(t);
//			}
//		});
		

		tgv_imagelist.setOnItemSelectListener(new OnItemSelectListener() {

			@Override
			public void onItemSelect(View item, int position) {

				Log.i(TAG, "select=" + position);

			}
		});

		tgv_imagelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View item, int position) {
				Log.i(TAG, "click=" + position);

			}
		});

	}

	/**
	 * ���ظ������� �����ڷ�ҳ���� �˴�ÿ�μ���45������
	 * 
	 * @param v
	 */
	public void add(View v) {
		for (int i = 0; i < 45; i++) {
			AppInfo app = new AppInfo();
			app.title = "ȫ�Һп��" + i;
			adapter.addItem(app);
		}

		adapter.notifyDataSetChanged();
	}

}
