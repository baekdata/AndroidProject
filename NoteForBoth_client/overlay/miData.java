package com.sgenclub.allnote.overlay;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

public class miData {
	// 비트맵 데이터와 위치.
	// 비트맵 위치를 x,y 좌표 Point 로 받지 않고 Rect 로 받은 이유는
	// 확대 출소를 위해서다.
	private Bitmap bitmap;
	private Rect rect;
	
	// 생성자들=====================================================
	public miData(){ }
	public miData(int ScreenWidth, int ScreenHeight, Bitmap tbitmap){
		super();
		bitmap = tbitmap;

		int x = ((ScreenWidth - tbitmap.getWidth()) / 2);
		int y = ((ScreenHeight - tbitmap.getHeight()) / 2);

		rect = new Rect(x, y, x + tbitmap.getWidth(), y + tbitmap.getHeight());
	}
	public miData(Bitmap tbitmap, Rect trect){
		super();
		bitmap = tbitmap;
		rect = trect;
	}
	public miData(Bitmap tbitmap, int left, int top, int right, int bottom){
		super();
		bitmap = tbitmap;
		rect = new Rect(left, top, right, bottom);
	}
	public miData(Bitmap tbitmap, int x, int y){
		super();
		bitmap = tbitmap;
		rect = new Rect(x, y, x + tbitmap.getWidth(), y + tbitmap.getHeight());
	}
	//=============================================================
	
	
	// 비트맵 셋, 겟
	public Bitmap getBitmap(){
		return bitmap;
	}
	public void setBitmap(Bitmap tbitBitmap){
		bitmap = tbitBitmap;
	}

	// 이미지 출력 좌표 변경 함수 세개=========================
	public void moveTo(int x, int y){
		int w = rect.right - rect.left;
		int h = rect.bottom - rect.top;
		
		rect.left = x;
		rect.top = y;
		rect.right = x + w;
		rect.bottom = y + h;
	}
	public void moveTo(Point pnt){
		int w = rect.right - rect.left;
		int h = rect.bottom - rect.top;

		rect.left = pnt.x;
		rect.top = pnt.y;
		rect.right = pnt.x + w;
		rect.bottom = pnt.y + h;
	}	
	public void moveBy(int tw, int th){
		rect.left += tw;
		rect.right += tw;
		rect.top += th;
		rect.bottom += th;
	}
	//========================================================
	
	// 이미지 x, y 좌표 리턴.
	public Point getXY(){
		return new Point(rect.left, rect.top);
	}
	// 해당 좌표에 이미지가 겹치는지 체크
	public boolean isOverlap(int x, int y){
		//Rect tmp = new Rect(x, y, x, y);
		//return Rect.intersects(rect, tmp);
		int bx = x - rect.left;
		int by = y - rect.top;
		int tmp;
		
		//여기 if문을 넣지 않으면 가끔 에러가난다.
		//getPixel 시 범위를 벗어나는 좌표에 대해서는 에러가 나는듯 하다.
		if(  ( (bx >= 0) && (bx < bitmap.getWidth()) ) &&
				( (by >= 0) && (by < bitmap.getHeight()) )
				){
			tmp = bitmap.getPixel(bx, by);
		}else{
			tmp = 0;
		}
		return (Color.alpha(tmp) > 0) ? true : false;  
	}
}
