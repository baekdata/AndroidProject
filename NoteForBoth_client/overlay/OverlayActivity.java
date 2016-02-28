/*package com.sgenclub.allnote.overlay;

import java.util.LinkedList;
import android.app.ActionBar.LayoutParams;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import com.sgenclub.allnote.R;

public class OverlayActivity extends FragmentActivity implements OnCheckedChangeListener {
	PaintView paintview;
	LinkedList<ImageView> list;
	FrameLayout frame;
	FrameLayout background;
	int idnum = 1;
	CheckBox User1, User2;
	Button Edit;
	private Bitmap bm;
	private Bitmap bm1;
	Gallery gallery; // gallery
	PaintAdapter mAdapter;
	private int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main2);
		mAdapter = new PaintAdapter(this);
		User1 = (CheckBox) findViewById(R.id.checkBox1);
		User2 = (CheckBox) findViewById(R.id.checkBox2);
		User1.setOnCheckedChangeListener(this);
		User2.setOnCheckedChangeListener(this);

		list = new LinkedList<>();
	//	frame = (FrameLayout) findViewById(R.id.frame);
		background=(FrameLayout) findViewById(R.id.background);
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.sample_p);
		bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sample1);

		Edit = (Button) findViewById(R.id.button1);
		Edit.setOnClickListener(new OnClickListener() {
			boolean flag = true;
			@Override
			public void onClick(View v) {

				if (flag) {
					flag=false;
					background.setVisibility(View.VISIBLE);
				} else {
					flag=true;
					EditOut();
					background.setVisibility(View.GONE);
				}
			}
		});

		gallery = (Gallery) findViewById(R.id.thumbnailimg);
		gallery.setAdapter(mAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Object clickedView = gallery.getItemAtPosition(position); // 
				for (int i = 0; i < 2; i++) {// 
					if (clickedView == gallery.getItemAtPosition(i)) {
						BringtoFront(i + 1);
						Toast.makeText(OverlayActivity.this, i + "",Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		AddPaint();

	}

	private void AddPaint() {
		paintview = new PaintView(getBaseContext());
		paintview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		paintview.setId(0);
		frame.addView(paintview);
	}

	private void AddImage(Bitmap bm, int idnum) {
		ImageView imageview = new ImageView(getBaseContext());
		imageview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		imageview.setId(idnum);
		imageview.setAlpha(130);
		imageview.setImageBitmap(bm); // �̹����� ����Ѵ�.
		frame.addView(imageview);
		frame.bringChildToFront(paintview);
		list.add(imageview);
	}

	public View FindView(int id) {
		View Findview = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				Findview = list.get(i);
				break;
			}
		}
		return Findview;
	}

	public void EditOut(){
		for (int i = 0; i < list.size(); i++) {

			list.get(i).setAlpha(130);

		}
		frame.bringChildToFront(paintview);
	}
	@SuppressWarnings("deprecation")
	private void BringtoFront(int id) {
		View Findview = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				Findview = list.get(i);
				list.get(i).setAlpha(200);
			} else {
				list.get(i).setAlpha(50);
			}
		}

		frame.bringChildToFront(Findview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		int id = buttonView.getId();
		switch (id) {
		case R.id.checkBox1:

			if (isChecked) {
				AddImage(bm1, 1);
				count = 0;
				mAdapter.add(ImageManager.imageIds[count]);
			} else {
				count = 0;
				View view = FindView(1);
				frame.removeView(view);
				list.remove(view);
				mAdapter.del(ImageManager.imageIds[count]);

			}
			break;
		case R.id.checkBox2:
			if (isChecked) {
				AddImage(bm, 2);
				count = 1;
				mAdapter.add(ImageManager.imageIds[count]);
			} else {
				count = 1;
				View view = FindView(2);

				frame.removeView(view);
				list.remove(view);
				mAdapter.del(ImageManager.imageIds[count]);
			}
			break;

		}
	}
}*/