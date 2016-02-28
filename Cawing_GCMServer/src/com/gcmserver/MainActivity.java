package com.gcmserver;

import java.awt.Menu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
//import android.os.AsyncTask;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.mysql.jdbc.log.Log;

public class MainActivity {
	public static final String TAG = "MainActivity";
	//AsyncTask<Void, Void, Void> mSendTask;
	static Sender sender;
	private static String mysqlUrl = "jdbc:mysql://localhost:3306/cawing";
	private static String mysqlID = "root";
	private static String mysqlPW = "mh0329";
	
	//Database
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet resultSet;
	private static ResultSet rs = null;
		
	public static void main(String[] args) throws SQLException {
		sender = new Sender(BasicInfo.GOOGLE_API_KEY);
        // 서비스에서 보내오는 상태 메시지를 받아 토스트로 보여줄 수신자 등록 
		////String msg = messageInput.getText().toString();
		//db에서 긁어오기
		
		String getQuery = "SELECT * FROM contents";
		String contents = null;
		
		conn = DriverManager.getConnection(mysqlUrl, mysqlID, mysqlPW);
		pstmt = conn.prepareStatement(getQuery);
		resultSet = pstmt.executeQuery();
		if(resultSet.next()) {  
			contents = resultSet.getString(1); 
		} 
		
		contents = resultSet.getString("content");
		Message message = new Message.Builder().addData("content", contents).build();
		try {
			Result result = sender.send(message,"", 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.d("","");
		//sendToDevice(msg);
	}
	/*
	public class PushAndroid {
		private final String GCM_KEY = "AIzaSyACEaLAqcfc-EypeP6Wtk-dFU31r-EYTB0";
				
		public void pushAndroid(String inputString) {
			Sender androidSender = new Sender(GCM_KEY);
			
			//Set message
			com.google.android.gcm.server.Message message = new com.google.android.gcm.server.Message.Builder().
					addData("msg", inputString).build();
			
			try {
				// Send
				while ( androidUdidList.size()>0 ) {
					HashMap<String, String> pushData = androidUdidList.remove(0);

					
					//pushdata = Contentinformation
					
					Result result = androidSender.send(message, pushData.get("UDID"), 1);
					//Result result = androidSender.send(message, pushdata, 1);
					
					if ( result.getMessageId()!=null ) { 
						// Success
					}
					else {
						// Fail
						String error = result.getErrorCodeName();
						break;
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
*/
	
	
	
    /**
     * 서버 : 전송하기
     * 
     * @param msg
     */
    /*private void sendToDevice(final String msg) {
    	mSendTask = new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
            	Message.Builder messageBuilder = new Message.Builder();
    			messageBuilder.addData("msg", msg);
    			messageBuilder.addData("action", "show");
    			Message message = messageBuilder.build();
    			
        		try {
                	Result result = sender.send(message, BasicInfo.RegistrationId, 5);
        			//Log.d(TAG, "Message sent. Result : " + result);
        			
        			// 토스트로 상태 메시지 보여주기
        			String statusMessage = "서버 : 단말로 메시지를 전송했습니다.\n결과 : " + result;
        			//Intent intent = new Intent(TOAST_MESSAGE_ACTION);
        	        //intent.putExtra("message", statusMessage);
        	        //sendBroadcast(intent);
        			
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
      
    }*/
    
}
