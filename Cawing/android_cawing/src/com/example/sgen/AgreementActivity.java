package com.example.sgen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreementActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement);
		
		Button agreebtn = (Button) findViewById(R.id.agreebtn);
        
        agreebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(AgreementActivity.this, CustomerActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agreement, menu);
		return true;
	}

}
