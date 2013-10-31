package com.xiaobin.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver{
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
		boolean isProtected = sp.getBoolean("isProtected", false);
		//�����ǲ��ǿ����˱���
		if(isProtected)
		{
			//�õ�һ�����ŵĹ�������Ҫע�ⲻҪ�����������android.telephony�µ�
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String currentSim = telephonyManager.getSimSerialNumber();
			String protectedSim = sp.getString("simSerial", "");
			if(!currentSim.equals(protectedSim))
			{
				SmsManager smsManager = SmsManager.getDefault();
				String number = sp.getString("number", "");
				//���Ͷ��ţ���5����������һ����Ҫ���͵��ĵ�ַ���ڶ����Ƿ����ˣ���������Ϊnull����������Ҫ���͵���Ϣ�����ĸ��Ƿ���״̬��������Ƿ��ͺ�ģ���������Ϊnull
				smsManager.sendTextMessage(number, null, "Sim���Ѿ�����ˣ��ֻ����ܱ���", null, null);
			}
		}
	}

}
