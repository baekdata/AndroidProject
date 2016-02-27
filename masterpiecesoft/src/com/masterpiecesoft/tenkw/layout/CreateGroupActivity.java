package com.masterpiecesoft.tenkw.layout;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.masterpiecesoft.tenkw.R;


public class CreateGroupActivity extends Activity{
	
	private ImageButton leftBtn, rightBtn;
	private ListView contactList;
	private Cursor cursor;
	private static Uri contactURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private final String SORTINGORDER = android.provider.ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	//�˻��� �÷�
	static final String[] item = new String[] {
		ContactsContract.CommonDataKinds.Phone._ID,
		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
		ContactsContract.CommonDataKinds.Phone.NUMBER,
		ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
		ContactsContract.CommonDataKinds.Phone.CONTACT_ID
	};
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		
		contactList = (ListView)findViewById(R.id.create_listView);
		
		leftBtn = (ImageButton)findViewById(R.id.create_left_btn);
		leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Toast.makeText(getApplicationContext(), "���", Toast.LENGTH_SHORT).show();
			}//��ҹ�ư
		});
		
		rightBtn = (ImageButton)findViewById(R.id.create_right_btn);
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CreateGroupActivity.this, GroupActivity.class));
			}// Ȯ�� ��ư ����  ListView �� Adapter ���� ������ checked[] �迭 �̿��ؼ� create table Db �� ����Ǿ�� �Ѵ�. ���̾ƿ� ���̸� �ϳ��� ������ �ҵ�?
		});
		
		// �ּҷ� ��������
		// DB���� �ؼ� ���� �о�� 
		cursor = getContentResolver().query(contactURI, item, null, null, SORTINGORDER);
		// ���� ��� ��ȭ��ȣ�� ������ ������ �׷��� �츮 DB�� �������Ѽ� ��ġ�� �͸� �������� �ؾ��Ѵ�. 
		// ListView Adapter , Cursor ��뿡 ����? ����? ResourceCursorAdapter ��ӹ��� 
		ContactListItemAdapter contactListAdapt = new ContactListItemAdapter(this, R.layout.list_create_group_row, cursor);
		contactList.setAdapter(contactListAdapt);
		
	}


	public final class ContactListItemAdapter extends ResourceCursorAdapter{

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = super.newView(context, cursor, parent);
			 
			// listView�� item �� �ϳ��� ������? �׶� ���� �Ǵ� �� ���� �̶� layout �� ���� ��ҵ�� java ��Ҹ� ���� ���Ѽ� ��� 
			// bind view �� �ݺ��Ǹ鼭 list �� ä�� 
			// static �ϳ��� ������ ������ ��� ��� 
			ContactListItemCache cache = new ContactListItemCache();
			cache.nameView = (TextView) view
					.findViewById(R.id.create_item_name_txt);
			cache.phoneView = (TextView) view
					.findViewById(R.id.create_item_phone_txt);
			cache.photoView = (ImageView) view
					.findViewById(R.id.create_item_image);
			
			cache.checkBox = (CheckBox)view.findViewById(R.id.create_item_checkbox);
			
			view.setTag(cache);
			return view;
		}

		private boolean[] checked;  // list ���Ʒ��� �����϶� bindView�� ��� ����� ���� check �ڽ��� �������� ���� �׷��� checkBox�� ����� ���� �ʿ� 
		@SuppressWarnings("deprecation")
		public ContactListItemAdapter(Context context, int layout, Cursor c) {
			super(context, layout, c);
			checked = new boolean[cursor.getCount()]; //�����߸��Զ�
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ContactListItemCache cache = (ContactListItemCache) view.getTag();
			TextView nameView = cache.nameView;
			TextView phoneView = cache.phoneView;
			ImageView photoView = cache.photoView;
			CheckBox checkBox = cache.checkBox;
			// contactListItemCache�� �Ʒ��ʿ� ����Ǿ� ����, List �� �࿡ �� ���� class
			final int position = cursor.getPosition(); // ���� ǥ�õǴ� db item �� index 
			
			//�̸�
			int nameidx = cursor.getColumnIndex(Contacts.DISPLAY_NAME); // DB���� �̸��� �ִ°� ���° ��ġ���� �˻�
			String name = cursor.getString(nameidx); // �� ��ġ�� ���� ������ 
			nameView.setText(name); // �̸� �����ͼ� text ���� 
			
			//��ȭ��ȣ
			int phoneidx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			String phone = cursor.getString(phoneidx);
			phoneView.setText(phone);
			
			// ����  ������ �� �𸣰��� ���� ���� �帧���� �ѵ� �� �𸣰��� - �� ������ �� �𸣰���.
			ContentResolver cr = getContentResolver();
			int contactId_idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
			Long contactId = cursor.getLong(contactId_idx);
			
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
			
			if(input != null){// DB�� �ִ� ������ Bitmap ���� �ٲ㼭 ImageView�� ǥ��
				Bitmap contactPhoto = BitmapFactory.decodeStream(input);
				photoView.setImageBitmap(contactPhoto);
			}
			
			//checkbox Ŭ���Ǹ� �迭�� �� ��ġ�� ���� ������ ���� �ϰ� 
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					checked[position] = isChecked;
				}
			});
			checkBox.setChecked(checked[position]); // �� ������ ���� üũ�ڽ��� üũ ���� ���� 
		}
	
	}


	final static class ContactListItemCache {
		public TextView nameView, phoneView;
		public ImageView photoView;
		public CheckBox checkBox ;
	}
}
