package com.xiaobin.security.receiver;


import com.example.security.R;
import com.xiaobin.security.engine.GPSInfoProvider;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver
{

        @Override
        public void onReceive(Context context, Intent intent)
        {
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                for(Object pdu : pdus)
                {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        //�õ���������
                        String content = smsMessage.getMessageBody();
                        //�õ������˵ĵ绰����
                        String sender = smsMessage.getOriginatingAddress();
                        
                        //�����ͨ�����ŷ���ָ�Ȼ�����һЩ������
                        if(content.equals("#*location*#"))
                        {
                                abortBroadcast();//��ֹ�㲥�������С͵��������
                                GPSInfoProvider gpsInfoProvider = GPSInfoProvider.getInstance(context);
                                String location = gpsInfoProvider.getLocation();
                                System.out.println(location);
                                if(!location.equals(""))
                                {
                                        //���Ͷ���
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(sender, null, location, null, null);
                                }
                        }
                        else if(content.equals("#*lockscreen*#"))
                        {
                                DevicePolicyManager manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                                //�����������룬��һ�������������룬�ڶ���������ʱû���ã���Ҫ����Ϊ0
                                manager.resetPassword("123", 0);
                                //��������
                                manager.lockNow();
                                abortBroadcast();
                        }
                        else if(content.equals("#*wipe*#"))
                        {
                                DevicePolicyManager manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                                //�ָ�������ģʽ��������ʱû���ã���Ҫ��Ϊ0
                                manager.wipeData(0);
                                abortBroadcast();
                        }
                        else if(content.equals("#*alarm*#"))
                        {
                                //��������Ѿ����õ�prepare����������������Բ����Լ�����prepare�������
                                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.jxmzf);
                                //��������Ϊ�����
                                mediaPlayer.setVolume(1.0f, 1.0f);
                                mediaPlayer.start();
                                abortBroadcast();
                        }
                }
        }

}
