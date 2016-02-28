package com.example.sgen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddPostActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_post);
		
		Button backButton = (Button) findViewById(R.id.backButton);
		Button addBtn = (Button) findViewById(R.id.addBtn);
		Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
		
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(AddPostActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		addBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent i = new Intent(AddPostActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(AddPostActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_post, menu);
		return true;
	}

}
