package com.example.datasending_2;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	@TargetApi(9)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        
        Button btn = (Button)findViewById(R.id.submitBtn);

        btn.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText edname = (EditText)findViewById(R.id.nameF);
		EditText edsex = (EditText)findViewById(R.id.sexF);
		String name = edname.getText().toString();
		String sex = edsex.getText().toString();
		Log.d("name", name);
		Log.d("sex", sex);
		
		TextView resultField = (TextView)findViewById(R.id.resultF);
		try{
			//sendData(name, sex);
			resultField.setText(sendData(name, sex));
		}catch (Exception e) {  
            // TODO Auto-generated catch block  
			
            e.printStackTrace();  
            Log.d("test", e.toString());
        } 
	}

	private String sendData(String name, String sex) throws Exception{
		// TODO Auto-generated method stub
		HttpPost request = makeHttpPost( name, sex,"http://ebsud5.cafe24.com/index.php" ) ;  
        HttpClient client = new DefaultHttpClient() ;  
        ResponseHandler<String> reshandler = new BasicResponseHandler() ;  

        String result = client.execute( request, reshandler ) ;  
        Log.d("sending", result);
        return result ;  
    }  
//  
    private HttpGet makeHttpGet(String $name, String $sex, String $url) {  
       // TODO Auto-generated method stub  
       Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;  
        nameValue.add( new BasicNameValuePair( "id", $name ) ) ;  
        nameValue.add( new BasicNameValuePair( "pword", $sex ) ) ;  
          
        String url = $url + "?" + URLEncodedUtils.format( nameValue, null) ;  
        HttpGet request = new HttpGet( url ) ;  
        return request ;  
	}
	private HttpPost makeHttpPost(String $name, String $sex, String $url){
		HttpPost request = new HttpPost($url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;  
        nameValue.add( new BasicNameValuePair( "id", $name ) ) ;  
        nameValue.add( new BasicNameValuePair( "pw", $sex ) ) ;  
        request.setEntity( makeEntity(nameValue) ) ; 
        Log.d("nameValue", nameValue.toString());
        Log.d("request", request.toString());
		return request;
	}

	private HttpEntity makeEntity(Vector<NameValuePair> $nameValue) {
		// TODO Auto-generated method stub
		HttpEntity result = null ;  
        try {  
            result = new UrlEncodedFormEntity( $nameValue ) ;  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		return result;
	}
}
