package com.example.sgen;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	
/*	Button text01;
	Button text02;
	Button text03;
	
    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_ERROR = 400; */
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button backButton = (Button) findViewById(R.id.backButton);
        Button addPost = (Button) findViewById(R.id.addPost);
   
        backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);
				finish();
			}
		});
        
        addPost.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent i = new Intent(MainActivity.this, AddPostActivity.class);
        		startActivity(i);
        		finish();
        	}
        });
        
        setupTabs();
    }
        
        /*text01 = (Button) findViewById(R.id.text01);
        text02 = (Button) findViewById(R.id.text02);
        text03 = (Button) findViewById(R.id.text03);
        
        text01.setVisibility(View.VISIBLE);
		text02.setVisibility(View.INVISIBLE);
		text03.setVisibility(View.INVISIBLE);
        
        Button startbtn1 = (Button) findViewById(R.id.startbtn1);
        startbtn1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		text01.setVisibility(View.VISIBLE);
        		text02.setVisibility(View.INVISIBLE);
        		text03.setVisibility(View.INVISIBLE);
        	}
        });
		
        Button startbtn2 = (Button) findViewById(R.id.startbtn2);
        startbtn2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		text01.setVisibility(View.INVISIBLE);
        		text02.setVisibility(View.VISIBLE);
        		text03.setVisibility(View.INVISIBLE);
        	}
        });
        
        Button startbtn3 = (Button) findViewById(R.id.startbtn3);
        startbtn3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		text01.setVisibility(View.INVISIBLE);
        		text02.setVisibility(View.INVISIBLE);
        		text03.setVisibility(View.VISIBLE);
        		
        	}
        });*/
    private void setupTabs() {
    	TabHost tabs = getTabHost();
 	    
 	    // TAB 01 
 	    TabHost.TabSpec spec = null;
 	    Intent intent = null;
        
 	    spec = tabs.newTabSpec("tab01");
 	    intent = new Intent(this, SubPage01Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
   	
 	    spec.setContent(intent);

 	    spec.setIndicator("전체확인");
 	    tabs.addTab(spec);
 	    
 	    // TAB 02 
 	    spec = tabs.newTabSpec("tab02");
 	    intent = new Intent(this, SubPage02Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 	    spec.setContent(intent);

 	    spec.setIndicator("주변확인");
 	    tabs.addTab(spec);
 	    
 	    // TAB 03 
 	    spec = tabs.newTabSpec("tab03");
 	    intent = new Intent(this, SubPage03Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 	    spec.setContent(intent);

 	    spec.setIndicator("지역설정");
 	    tabs.addTab(spec);
 	    
 	    // set current tab
 	    tabs.setCurrentTab(0);
 	    
 	    // TAB 04 
 	    spec = tabs.newTabSpec("tab04");
 	    intent = new Intent(this, SubPage04Activity.class);
 	    intent.putExtra("mode", "new");
 	    intent.putExtra("initialize", true);
 	    intent.putExtra("request", true);
 	    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 	    spec.setContent(intent);
 	    
 	    spec.setIndicator("설정");
 	    tabs.addTab(spec);
 	    
 	    // set current tab
 	    tabs.setCurrentTab(0);
 	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}