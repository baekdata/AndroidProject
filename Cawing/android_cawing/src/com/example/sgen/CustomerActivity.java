package com.example.sgen;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
 
public class CustomerActivity extends Activity {
/*    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_ERROR = 400;
 
    TextView titleText;*/
	
    public static final int REQUEST_CODE_LOGIN = 2001;
    
    EditText usernameInput;
    EditText passwordInput;
    EditText password2Input;
    EditText phoneInput;
    RadioGroup radioGroup;
    CheckBox checkBox;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
 
 /*       titleText = (TextView) findViewById(R.id.titleText);
 
        // process received intent
        Intent receivedIntent = getIntent();
        String titleMsg = receivedIntent.getStringExtra("titleMsg");
 
        Toast.makeText(this, "titleMsg : " + titleMsg, Toast.LENGTH_LONG).show();
 
        if (titleText != null) {
            titleText.setText(titleMsg);
        }*/
 
        Button agreement = (Button) findViewById(R.id.agreement);
        Button addBtn = (Button) findViewById(R.id.addBtn);
        checkBox = (CheckBox)findViewById(R.id.checkBox1);
        
        agreement.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(CustomerActivity.this, AgreementActivity.class);
				startActivity(i);
			}
		});
        
        
        addBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                /*Intent resultIntent = new Intent();
                resultIntent.putExtra("message", "result message is OK!");
 
                setResult(RESPONSE_CODE_OK, resultIntent);
                finish();*/
            	/*Intent i = new Intent(CustomerActivity.this, LoginActivity.class);
				startActivity(i);
				finish();*/
            	String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String password2 = password2Input.getText().toString();
                String phone = phoneInput.getText().toString();
                
                if (username.length() < 1) {
                    Toast.makeText(getApplicationContext(), "ID를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 1) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password2.length() < 1) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone.length() < 1) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!checkBox.isChecked()) {
                	Toast.makeText(getApplicationContext(), "약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
                	return;
                }
                
                if (checkBox.isChecked()) {
        			checkBox.setChecked(false);
        		}
        		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        			
        			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        				if (isChecked) {
        					Intent i = new Intent(CustomerActivity.this, LoginActivity.class);
        					startActivity(i);
        					finish();
        				} else {
        					Toast.makeText(getApplicationContext(), "동의함에 체크해주세요.", Toast.LENGTH_SHORT).show();
        				}
        			}
        		});
                
 
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("password2", password2);
                intent.putExtra("phone", phone);
                 
				Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, REQUEST_CODE_LOGIN);
                finish();
            }
                        
        });
        
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        password2Input = (EditText) findViewById(R.id.password2Input);
        phoneInput = (EditText) findViewById(R.id.phoneInput);
        

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        	
        	public void onCheckedChanged(RadioGroup group, int checkedId) {
        		switch(checkedId) {
        		case R.id.radio0 :
        			break;
        		case R.id.radio1 :
        			break;
        		}
        	}
        });
        
       
		
		
	}

}
