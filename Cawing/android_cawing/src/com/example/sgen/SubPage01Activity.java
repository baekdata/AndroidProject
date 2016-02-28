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
	//리스트뷰객체
	DataListView list;
	//어댑터객체
	IconTextListAdapter adapter;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subpage01);
        
     // 리스트뷰 객체 생성
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        list = new DataListView(this);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

		// 아이템 데이터 만들기
		Resources res = getResources();
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon04), "황신하", "21 분전", "지금 2호선 강남역에서 잠실 방향 정전이예요."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "남현진", "3일전", "잠실역에서 열차 고장으로 30분간 지연이라네요..ㅠㅠ"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "김운지", "5일전", "잠실에 위치한 sGen Club 라운지에 불이났어요!!! 불길이 위층으로 퍼지기 시작했어요!!!"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon02), "김준형", "6일전", "신갈오거리 부근에서 5중 추돌사고가 나서 많이 막히네요."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon03), "백종수", "7일전", "시청역이 시위로 인해 폐쇄되었어요. 버스나 택시를 이용하는게 좋을 것 같네요."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon04), "김명훈", "7일전", "용인 43번지 푸르지오 아파트 6층에 방금 강도가 들었다네요."));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "황예린", "8일전", "1"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "김양희", "9일전", "2"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "공민아", "10일전", "3"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "박신영", "11일전", "4"));

		// 리스트뷰에 어댑터 설정
		list.setAdapter(adapter);

		// 새로 정의한 리스너로 객체를 만들어 설정
		list.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				IconTextItem curItem = (IconTextItem) adapter.getItem(position);
				String[] curData = curItem.getData();

				Toast.makeText(getApplicationContext(), "Selected : " + curData[0], 2000).show();
			}
		});


        // 화면을 리스트뷰 객체로 채움
        setContentView(list, params);
        
    }

}