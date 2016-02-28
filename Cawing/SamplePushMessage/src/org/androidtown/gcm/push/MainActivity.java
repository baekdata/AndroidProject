package org.androidtown.gcm.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import static org.androidtown.gcm.push.BasicInfo.TOAST_MESSAGE_ACTION;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	EditText messageInput;
	TextView messageOutput;

	/**
	 * ���� : Task for sending messages
	 */
	AsyncTask<Void, Void, Void> mSendTask;
	
	/**
	 * ���� : Sender ��ü ����
	 */
	Sender sender;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ���� : GOOGLE_API_KEY�� �̿��� Sender �ʱ�ȭ
        sender = new Sender(BasicInfo.GOOGLE_API_KEY);

        // ���� : ������ �޽��� �Է� �ڽ�
        messageInput = (EditText) findViewById(R.id.messageInput);
        
        // ������ �޽��� ��� �ڽ�
        messageOutput = (TextView) findViewById(R.id.messageOutput);

        // ���񽺿��� �������� ���� �޽����� �޾� �佺Ʈ�� ������ ������ ���
        registerReceiver(mToastMessageReceiver, new IntentFilter(TOAST_MESSAGE_ACTION));
        
        
        
        // �ܸ� ����ϱ� ��ư
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		try {
        			// �ܸ� ����ϰ� ��� ID �ޱ�
        			registerDevice();

        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
        	}
        });

        // ���� : �����ϱ� ��ư
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
    			String msg = messageInput.getText().toString();
    			Log.d("�ù�","������");
    			sendToDevice(msg);
    			
        	}
        });

    }

    /**
     * �ܸ� ���
     */
    private void registerDevice() {
    	// ����̽� üũ
        GCMRegistrar.checkDevice(this);
        // �Ŵ��佺Ʈ üũ ���� �ɼ�
        GCMRegistrar.checkManifest(this);

        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
        	// �ܸ� ��� ȣ��
        	GCMRegistrar.register(getBaseContext(), BasicInfo.PROJECT_ID);
        	
        } else {
            // �ܸ� ��ϵǾ� ����
        	if (GCMRegistrar.isRegisteredOnServer(this)) {
        		Log.d(TAG, "�ܸ��� �̹� ��ϵǾ� �ֽ��ϴ�.");
        	} else {
        		// �ܸ� ��� ȣ��
            	GCMRegistrar.register(getBaseContext(), BasicInfo.PROJECT_ID);
        	}
            
        }
    }
    
    /**
     * ���� : �����ϱ�
     * 
     * @param msg
     */
    private void sendToDevice(final String msg) {
    	mSendTask = new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
            	Message.Builder messageBuilder = new Message.Builder();
    			messageBuilder.addData("msg", msg);
    			messageBuilder.addData("action", "show");
    			Message message = messageBuilder.build();
    			
        		try {
                	Result result = sender.send(message, BasicInfo.RegistrationId, 5);
        			Log.d(TAG, "Message sent. Result : " + result);
        			
        			// �佺Ʈ�� ���� �޽��� �����ֱ�
        			String statusMessage = "���� : �ܸ��� �޽����� �����߽��ϴ�.\n��� : " + result;
        			Intent intent = new Intent(TOAST_MESSAGE_ACTION);
        	        intent.putExtra("message", statusMessage);
        	        sendBroadcast(intent);
        			
        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
        		
                return null;
            }

            protected void onPostExecute(Void result) {
                mSendTask = null;
            }

        };
        mSendTask.execute(null, null, null);
      
    }
    
    
    @Override
    protected void onDestroy() {
    	if (mSendTask != null) {
            mSendTask.cancel(true);
        }
    	
    	unregisterReceiver(mToastMessageReceiver);
    	
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
	protected void onNewIntent(Intent intent) {
    	Log.d(TAG, "onNewIntent() called.");

    	String msg = intent.getStringExtra("msg");
        String from = intent.getStringExtra("from");
        String action = intent.getStringExtra("action");

        Log.d(TAG, "DATA : " + from + ", " + action + ", " + msg);
        messageOutput.setText("[" + from + "]�κ��� ������ �޽��� : " + msg);

		super.onNewIntent(intent);
	}

    /**
     * Receiver for showing status messages from GCMIntentService as a Toast
     */
    private final BroadcastReceiver mToastMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String message = intent.getExtras().getString("message");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
    
}
