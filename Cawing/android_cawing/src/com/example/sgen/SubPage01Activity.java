package com.example.sgen;

import java.util.ArrayList;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class SubPage01Activity extends Activity {
    /** Called when the activity is first created. */
	//����Ʈ�䰴ü
	DataListView list;
	//����Ͱ�ü
	IconTextListAdapter adapter;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subpage01);
        
     // ����Ʈ�� ��ü ����
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        list = new DataListView(this);

        // ����� ��ü ����
        adapter = new IconTextListAdapter(this);

		// ������ ������ �����
		Resources res = getResources();
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon04), "Ȳ����", "21 ����", "���� 2ȣ�� ���������� ��� ���� �����̿���."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "������", "3����", "��ǿ����� ���� �������� 30�а� �����̶�׿�..�Ф�"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "�����", "5����", "��ǿ� ��ġ�� sGen Club ������� ���̳����!!! �ұ��� �������� ������ �����߾��!!!"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon02), "������", "6����", "�Ű����Ÿ� �αٿ��� 5�� �ߵ���� ���� ���� �����׿�."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon03), "������", "7����", "��û���� ������ ���� ���Ǿ����. ������ �ýø� �̿��ϴ°� ���� �� ���׿�."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon04), "�����", "7����", "���� 43���� Ǫ������ ����Ʈ 6���� ��� ������ ����ٳ׿�."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "Ȳ����", "8����", "1"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "�����", "9����", "2"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "���ξ�", "10����", "3"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "�ڽſ�", "11����", "4"));

		// ����Ʈ�信 ����� ����
		list.setAdapter(adapter);

		// ���� ������ �����ʷ� ��ü�� ����� ����
		list.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				IconTextItem curItem = (IconTextItem) adapter.getItem(position);
				String[] curData = curItem.getData();

				Toast.makeText(getApplicationContext(), "Selected : " + curData[0], 2000).show();
			}
		});


        // ȭ���� ����Ʈ�� ��ü�� ä��
        setContentView(list, params);
        
    }

}