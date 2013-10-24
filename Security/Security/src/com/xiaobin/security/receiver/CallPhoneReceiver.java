package com.xiaobin.security.receiver;

import com.xiaobin.security.ui.LostProtectedActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CallPhoneReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String outPhoneNumber = this.getResultData();
		if(outPhoneNumber.equals("1314"))
		{
			Intent i = new Intent(context, LostProtectedActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			setResultData(null);
		}
	}

}
