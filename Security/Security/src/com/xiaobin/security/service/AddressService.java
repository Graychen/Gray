package com.xiaobin.security.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaobin.security.engine.NumberAddressService;

public class AddressService extends Service{
	
	private WindowManager windowManager;
	private MyPhoneListener listener;
	private TextView tv;
	private TelephonyManager telephonyManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		listener = new MyPhoneListener();
		telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	//===================================================
	private void showLocation(String address) {
		// TODO Auto-generated method stub
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width=WindowManager.LayoutParams.WRAP_CONTENT;
		params.height=WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
		|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format=PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");
		
		tv = new TextView(AddressService.this);
		tv.setText("归属地："+address);
		windowManager.addView(tv, params);
	}
	
	   private class MyPhoneListener extends PhoneStateListener
       {
               @Override
               public void onCallStateChanged(int state, String incomingNumber)
               {
                       super.onCallStateChanged(state, incomingNumber);
                       
                       switch(state)
                       {
                               case TelephonyManager.CALL_STATE_IDLE : //空闲状态
                                       if(tv != null)
                                       {
                                               windowManager.removeView(tv);//移除显示归属地的那个view
                                               tv = null;
                                       }
                                       break;
                                       
                               case TelephonyManager.CALL_STATE_OFFHOOK : //接通电话
                                       if(tv != null)
                                       {
                                               windowManager.removeView(tv);//移除显示归属地的那个view
                                               tv = null;
                                       }
                                       break;
                                       
                               case TelephonyManager.CALL_STATE_RINGING : //铃响状态
                                       String address = NumberAddressService.getAddress(incomingNumber);
                                       showLocation(address);
                                       break;
                                       
                               default : 
                                       break;
                       }
               }

			
       }

}