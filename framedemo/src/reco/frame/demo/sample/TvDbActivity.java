package reco.frame.demo.sample;

import java.util.ArrayList;
import java.util.List;

import reco.frame.demo.MainActivity;
import reco.frame.demo.R;
import reco.frame.demo.adapter.DbListAdapter;
import reco.frame.demo.entity.AppInfo;
import reco.frame.demo.entity.Config;
import reco.frame.tv.TvDb;
import reco.frame.tv.view.TvButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class TvDbActivity extends Activity {

	private TvDb tvDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvdb);
		init();
	}

	private void init() {
		lv_list = (ListView) findViewById(R.id.lv_list);
		findViewById(R.id.tb_select).setOnClickListener(mClickListener);
		findViewById(R.id.tb_insert).setOnClickListener(mClickListener);
		findViewById(R.id.tb_delete).setOnClickListener(mClickListener);
		findViewById(R.id.tb_update).setOnClickListener(mClickListener);
		appList = new ArrayList<AppInfo>();
		tvDb = TvDb.create(getApplicationContext());
		
		
		//�����
		try {
			tvDb.deleteAll(AppInfo.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		// �˴����7��ģ������

		for (int i = 1; i < 8; i++) {
			AppInfo app = new AppInfo();
			app.num= i;
			app.title = "������" + i;
			app.imageUrl = Config.TEST_DATA_API;
			tvDb.insert(app);
//			boolean result=tvDb.saveBindId(app);
//			Log.e("", "result="+result);
		}
	}

	public void select() {

		// �˴�ʾ������ ���� ��������
		appList.clear();
		appList=tvDb.findAll(AppInfo.class);
		lv_list.setAdapter(new DbListAdapter(this, appList));
	}

	public void insert() {
		int id = appList.size() + 1;
		AppInfo app = new AppInfo();
		app.num = id;
		app.title = "������";
		app.imageUrl = Config.TEST_DATA_API;
		tvDb.insert(app);
		select();

	}

	public void delete() {

		// �˴�ʾ�� ɾ������ num�ֶ�Ϊż��������
		if (appList.size() > 0)
			tvDb.deleteByWhere(AppInfo.class, "num%2=0");
		
		select();
	}

	public void update() {
		// �˴�ʾ�� �޸��ֶ� num Ϊ����������
		AppInfo newApp = new AppInfo();
		newApp.imageUrl = "·�����޸�";
		if (appList.size() > 0)
			tvDb.update(newApp, "num%2=1");
		
		select();
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tb_select:
				select();
				break;
			case R.id.tb_insert:
				insert();
				break;
			case R.id.tb_delete:
				delete();
				break;
			case R.id.tb_update:
				update();
				break;
			}

		}
	};
	private ListView lv_list;
	private List<AppInfo> appList;

}
