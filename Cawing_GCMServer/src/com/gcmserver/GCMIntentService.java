package com.gcmserver;

import javax.naming.Context;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService {
	 public void onRegistered(Context context, String registrationId) {
	    	BasicInfo.RegistrationId = registrationId;
	    	//sendToastMessage(context, "단말이 등록되어 등록 ID를 받았습니다.");
	    }
}
