package com.masterpiecesoft.tenkw.layout;

import com.masterpiecesoft.tenkw.R;
import com.masterpiecesoft.tenkw.etc.*;
import com.masterpiecesoft.tenkw.pedometer.PedometerSettings;
import com.masterpiecesoft.tenkw.pedometer.StepService;
import com.masterpiecesoft.tenkw.pedometer.Utils;
import com.masterpiecesoft.tenkw.store.RunInfo;
import com.masterpiecesoft.tenkw.store.Team;
import com.masterpiecesoft.tenkw.store.UserInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;

public class GroupActivity extends FragmentActivity implements ActionBar.TabListener{

	private TabPagerAdapter mTabPagerAdapter;
	private ViewPager mViewPager;
	private RunInfo runInfo = new RunInfo();
	private ImageButton strBtn;
	private int groupID;
	private int beforeStepNum;
	
/////////////////////////////////////////////
	String TAG = "TAG";
	private StepService mService;
	private SharedPreferences mSettings;
	private PedometerSettings mPedometerSettings;
	private Utils mUtils;
	private int mStepValue;
	private float mDesiredPaceOrSpeed;
	private int mMaintain;
	private boolean mQuitting = false;
	private boolean mIsRunning ;
	//////////////////////////////////////////////
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_group);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		strBtn = (ImageButton)findViewById(R.id.main_start_btn);
		
		Intent intent = getIntent();
		groupID = intent.getIntExtra("GroupID", 1);
		//?????????????????????????????
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		mIsRunning = intent.getBooleanExtra("mIsRunning", false);

		if(mIsRunning && runInfo.getGroupIndex() == groupID ){
			strBtn.setBackgroundResource(R.drawable.btn_stop);
		}else{
			strBtn.setBackgroundResource(R.drawable.btn_start);
		}
//		

		strBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if(mIsRunning && runInfo.getGroupIndex() == groupID ){
					// ���� ���� �� �����Ŷ� stop ��Ű�� �ȴ�.
	                Log.d("Running",""+mIsRunning);
	                Team tempTeam = UserInfo.getTeamList().get(runInfo.getGroupIndex());
					tempTeam.getUserList().get(0).addSubStep(mStepValue);
					unbindStepService();
	                stopStepService();
	                resetValues(true);
					strBtn.setBackgroundResource(R.drawable.btn_start);
				}else{
					// �����ϱ� ���ؼ� ������ �̹Ƿ� ������ ���������� �� Ȯ���ϰ� ���� ��ų �� �ֵ��� �ؾ��Ѵ�. 
					strBtn.setBackgroundResource(R.drawable.btn_stop);
					if(mIsRunning){
						Team tempTeam = UserInfo.getTeamList().get(runInfo.getGroupIndex());
						tempTeam.getUserList().get(0).addSubStep(mStepValue);
						resetValues(true);
						//�ٸ��濡�� �������� �� �� ������ �ٲ㼭 ���� ���� ���ش�. 
						//runInfo.setGroupID(groupID);
					}else if(mPedometerSettings.isNewStart()){
						//���� ����
						Team tempTeam = UserInfo.getTeamList().get(groupID);
						Log.d("Running",""+mPedometerSettings.isNewStart());
						startStepService();
						bindStepService();
						//runInfo.setGroupID(groupID);
					}else{
						//�Ͻ� ���� �̴ٰ� ���� �� ��
						bindStepService();
						//runInfo.setGroupID(groupID);
					}
					runInfo.setGroupIndex(groupID);
				}
			}
		});
//		
		mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), groupID);
		
		mViewPager = (ViewPager) findViewById(R.id.group_pager);
		mViewPager.setAdapter(mTabPagerAdapter);
		
		//main �� ���������� pager �� �̿��ؼ� swipe , action bar �� tab ���� �����Ű�� ������ �Ʒ��� �Լ� �߰���
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				actionBar.setSelectedNavigationItem(pos);		// actionbar�� ���õ� �����۵� ����		
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
		});
		
		// add tab (&set icon) 
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.tapbar_1).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.tapbar_2).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.tapbar_3).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.tapbar_4).setTabListener(this));
	}
	
	////////////
    private void resetValues(boolean updateDisplay) {
          mService.resetValues();               }     
	///////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}// �޴� ��ư ������ �� �����°� �޴� ���� �˾Ҵµ� �׼ǹٿ����� �޴��� �׼ǹ� �����ʿ� ��Ÿ���� �� ���� ���� xml �� item
		// �߰��Ͽ� + ��ư �߰�

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			break;
		case R.id.action_create:
			startActivity(new Intent(this, CreateGroupActivity.class));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		int pos = tab.getPosition();
		switch(pos+1)
		{
		case 1:
			tab.setIcon(R.drawable.tapbar_1_o);
			break;
		case 2:
			tab.setIcon(R.drawable.tapbar_2_o);
			break;
		case 3:
			tab.setIcon(R.drawable.tapbar_3_o);
			break;
		case 4:
			tab.setIcon(R.drawable.tapbar_4_o);
			break;
		}
		mViewPager.setCurrentItem(pos);
	}// tab �� ���õǸ� pager �� position �� ���� , ���� switch ���� �ܼ��� ���� icon ��� ������ ���� �� 

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
		int pos = tab.getPosition();
		switch (pos+1){
		case 1:
			tab.setIcon(R.drawable.tapbar_1);
			break;
		case 2:
			tab.setIcon(R.drawable.tapbar_2);
			break;
		case 3:
			tab.setIcon(R.drawable.tapbar_3);
			break;
		case 4:
			tab.setIcon(R.drawable.tapbar_4);
			break;
		}
	}// ���� ������ �����Ǹ� ( �ٸ��� ���õǸ� ���õǾ� �ִ��� �����Ǵϱ� ) icon����� �ٽ� ���� 

	////////////////////////////////////////////////////////////////////////

	@Override
	public void onStart() {
		Log.i(TAG, "[ACTIVITY] onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, "[ACTIVITY] onResume");
		super.onResume();

		mUtils = Utils.getInstance();

		mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		mPedometerSettings = new PedometerSettings(mSettings);

		mUtils.setSpeak(mSettings.getBoolean("speak", false));

		// Read from preferences if the service was running on the last onPause
		Intent intent = getIntent();
		mIsRunning = intent.getBooleanExtra("mIsRunning", false);

		// Start the service if this is considered to be an application start
		// (last onPause was long ago)
		if (!mIsRunning && mPedometerSettings.isNewStart()) {
			startStepService();
			bindStepService();
		} else if (mIsRunning) {
			bindStepService();
		}

		mPedometerSettings.clearServiceRunning();

		mMaintain = mPedometerSettings.getMaintainOption();
		if (mMaintain == PedometerSettings.M_PACE) {
            mDesiredPaceOrSpeed = (float)mPedometerSettings.getDesiredPace();
        }
        else 
        if (mMaintain == PedometerSettings.M_SPEED) {
            mDesiredPaceOrSpeed = mPedometerSettings.getDesiredSpeed();
        }
        if (mMaintain != PedometerSettings.M_NONE) {
        }
	}

	@Override
	public void onPause() {
		Log.i(TAG, "[ACTIVITY] onPause");
		if (mIsRunning) {
			unbindStepService();
		}
		if (mQuitting) {
			mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
		} else {
			mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
		}

		super.onPause();
		savePaceSetting();
	}

	private void savePaceSetting() {
		mPedometerSettings.savePaceOrSpeedSetting(mMaintain,
				mDesiredPaceOrSpeed);
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = ((StepService.StepBinder) service).getService();
			mService.registerCallback(mCallback);
			mService.reloadSettings();
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
		}
	};

	private void startStepService(/*final String TAG*/) {
		if (!mIsRunning) {
			mIsRunning = true;
			Intent intent = new Intent(this, StepService.class);
//			intent.addCategory(TAG);
			startService(intent);
		}
	}

	private void bindStepService(/*final String TAG*/) {
		Intent intent = new Intent(this, StepService.class);
//		intent.addCategory(TAG);
		bindService(
				intent,
				mConnection,
				BIND_AUTO_CREATE
						+ BIND_DEBUG_UNBIND);
	}

	private void unbindStepService() {
		unbindService(mConnection);
	}

	private void stopStepService(/*final String TAG*/) {
		if (mService != null) {
			Intent intent = new Intent(this , StepService.class);
//			intent.addCategory(TAG);
			stopService(intent);
		}
		mIsRunning = false;
	}

	private StepService.ICallback mCallback = new StepService.ICallback() {
		public void stepsChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
		}

		public void paceChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
		}

		public void distanceChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG,
					(int) (value * 1000), 0));
		}

		public void speedChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG,
					(int) (value * 1000), 0));
		}

		public void caloriesChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG,
					(int) (value), 0));
		}
	};

	private static final int STEPS_MSG = 1;
	private static final int PACE_MSG = 2;
	private static final int DISTANCE_MSG = 3;
	private static final int SPEED_MSG = 4;
	private static final int CALORIES_MSG = 5;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STEPS_MSG:
				mStepValue = (int) msg.arg1;
				//mStepValueView.setText("" + mStepValue);
				break;
			case PACE_MSG:
				break;
			case DISTANCE_MSG:
				break;
			case SPEED_MSG:
				break;
			case CALORIES_MSG:
				break;
			default:
				super.handleMessage(msg);
			}
		}

	};

	
	////////////////////////////////////////////////////////////////////////
}
