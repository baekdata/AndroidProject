package com.sgenclub.allnote.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MoveImage extends View {
	// 이미지 배열과 선택이미지.
	ArrayList<miData> mImageArr;
	miData selectImage;

	// 배경 이미지.
	Bitmap BackImg;
	Rect BackRect;

	// 터치 움직임 캐치
	Point preMouse;
	Point nowMouse;

	// 매 터치반응시의 이동 간격.
	public int tw;
	public int th;

	// 이동간격 인식 갭.
	public int allowGap = 2;

	// Antialias 용
	public Paint Pnt;

	//캡쳐용 x y
	public int x,y;
	
	
	// 초기화 테스트용 변수들 ====================
	/*
	 * 처음 setBitmap 으로 이미지를 받아오고 나서 이 이미지를 중앙에 배치시키기 위해 getWidth()를 호출하여
	 * 화면 사이즈를 구하려고했더니 0 이 리턴된다.
	 * 일단 생성자가 종료된 이후라야지만 사이즈를 구할수 있는것으로 보인다.
	 * 이것의 초기화를 어디서 해야할지 모르겠다.
	 * 여러 함수를 둘러보다 onLayout 에서 사용해 보려고 한다.
	 * 그러기 위한 테스트 변수들을 잡는다.
	 */
	//int i=0; // 토스트 출력을 위한 녀석.  

	// 생성자 및 초기화==========================================
	public MoveImage(Context context) {
		super(context);

		mImageArr = new ArrayList<miData>();
		selectImage = null;

		BackImg = null;

		preMouse = new Point();
		nowMouse = new Point();

		Pnt = new Paint();
		Pnt.setAntiAlias(true);
	}
	public MoveImage(Context context, AttributeSet attrs) { super(context, attrs); }
	public MoveImage(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }


	// 이미지 초기화 부분========================================
	// 이미지 좌표를 지정하기 위해서 getWidth()를 사용했는데.. 생성자나 setBitmap 에서 사용할수 없었다.
	// 0 이 리턴된다. 방법을 찾다가 이 함수에서 구현했다.
	// 이게 정답인지 아닌지 모르겠다.
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		//toMessage("onLayout: " + i++);
		if(mImageArr.size() > 0){
			for(int i=0; i<mImageArr.size(); i++){
				miData tmp = mImageArr.get(i);
				tmp.moveTo(
						(getWidth() - tmp.getBitmap().getWidth())/2,
						(getHeight() - tmp.getBitmap().getHeight())/2
						);
			}
		}
	}

	// 비트맵과 배경을 set 하는 부분===================================
	// 일단 테스트니까 전부 그냥 화면 가운데 출력하도록 했다.
	public void setImage(Bitmap bitmap){
		//toAlert(this.getLayoutParams().width + " / " + this.getLayoutParams().height);
		miData tmp = new miData(getWidth(), getHeight(), bitmap);
		mImageArr.add(tmp);
	}

	public void setBackground(Bitmap bitmap){
		BackImg = bitmap;
	}

	// 실제 그림을 그리는 부분=========================================
	// 이안에다가 실제 그리는 루틴을 구현해야 한다.
	// clipping 영역을 구현해 주고 싶었지만 실패했다.
	// clipping 영역 자체는 맞게 구한것 같지만... 터치이동시 onDraw 가 끝나기 전에 
	// 새로운 좌표가 set 되는듯 하다. 그래서 그림의 일부가 남아있는것 같은데...
	// 해결방법을 찾아봐야 하겠ㄷ.
	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawColor(Color.WHITE);
		canvas.drawColor(Color.TRANSPARENT);
		
		Bitmap BackBit = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

		Canvas offscreen = new Canvas(BackBit);
		offscreen.drawColor(Color.TRANSPARENT); // 배경 흰색으로 채움


		if(BackImg != null){ // 배경 그리기
			offscreen.drawBitmap(BackImg, (getWidth() - BackImg.getWidth())/2, (getHeight() - BackImg.getHeight())/2, Pnt);
		}

		for(int i=0; i<mImageArr.size(); i++){ // 이미지 그리기
			offscreen.drawBitmap(mImageArr.get(i).getBitmap(), mImageArr.get(i).getXY().x, mImageArr.get(i).getXY().y, Pnt);
		}

		// 그린 배경고 이미지 출력.
		canvas.drawBitmap(BackBit, 0, 0, null);
	}

	// 터치 이벤트 리스너======================================================
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		nowMouse.x = (int)event.getX();
		nowMouse.y = (int)event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(mImageArr.size() > 0){
				//toMessage("ACTION_DOWN");
				if(selectImage == null){
					for(int i=0; i<mImageArr.size(); i++){
						if(mImageArr.get(i).isOverlap(nowMouse.x, nowMouse.y)){
							selectImage = mImageArr.get(i);
						}
					}

					// Array에서 Object를 삭제한뒤 다시 삽입하는 방법으로 해당 이미지의 index를 변경하였는데..
					// 그냥 Array내에서 index만 변경해 주는 방법이 있을듯도 한데 말이다..
					if(selectImage != null){
						mImageArr.remove(selectImage);
						mImageArr.add(selectImage);

						preMouse.x = nowMouse.x;
						preMouse.y = nowMouse.y;
						invalidate();
						return true;
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(selectImage == null){
				return false;
			}else{
				tw = nowMouse.x - preMouse.x;
				th = nowMouse.y - preMouse.y;

				// 이동간격이 -MoveInterval ~ +MoveIntervla 사이일때... 이동하지 않음.
				// 터치시 가만히 있어도 1,2 픽셀씩 계속 좌표값이 왔다갔다 한다.
				// 사람 손가락 굵기라는게 있다보니 정확하게 x,y 의 한 좌표로 인식이 안되는게 당연한것인가보다.
				// 입력값이 작을때는 아예 무시해서 처리속도를 높이도록 하자.
				if(  ( (tw > -allowGap) && (tw < allowGap) ) 
						&& ((th > -allowGap) && (th < allowGap))  ){
					return false; 
				}else{
					selectImage.moveBy(tw, th);

					preMouse.x = nowMouse.x;
					preMouse.y = nowMouse.y;
					invalidate();
					return true;
				}
			}
		case MotionEvent.ACTION_UP:
			x=(int)event.getX();
			y=(int)event.getY();
			if(selectImage != null){
				selectImage = null;
				invalidate();
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
}
