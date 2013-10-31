package com.xiaobin.security.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * ����࣬����Ҫ�����˵���ģʽ����Ϊ�ֻ�����ֻ��һ��gps�������ÿ�ζ��¿�һ������
 * @author Administrator
 *
 */
public class GPSInfoProvider
{
        private static GPSInfoProvider gpsInfoProvider;
        private static Context context;
        private static MyLocationListener listener;
        private LocationManager locationManager;
        
        private GPSInfoProvider()
        {
                
        }
        
        /**
         * Ϊ�����������һ��ִ���꣬�������Ǽ�����synchronized������
         * @return
         */
        public static synchronized GPSInfoProvider getInstance(Context context)
        {
                if(gpsInfoProvider == null)
                {
                        gpsInfoProvider = new GPSInfoProvider();
                        GPSInfoProvider.context = context;
                }
                return gpsInfoProvider;
        }
        
        public String getLocation()
        {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                
                String provider = getBestProvider();
                
                //���������λ�ø��µĵĲ��������ĸ�����
                //��һ����������ʹ�õĶ�λ�豸������gps����վ��λ��
                //�ڶ����������Ƕ೤ʱ�����һ�ζ�λ��Ϣ��̫Ƶ���˻�ܺĵ磬�����Լ������ʵ����Ҫ������ȷ��
                //���������������û�λ���˶�����֮�󣬾����»�ȡһ�ζ�λ��Ϣ��̫Ƶ���˻�ܺĵ磬�����Լ������ʵ����Ҫ������ȷ��
                //���һ������������λ�÷����仯�Ļص�����
                locationManager.requestLocationUpdates(provider, 60000, 50, getListener());
                //locationManager.getAllProviders();//���������֧�ֵĶ�λ��ʽ�����г����������Ϳ���֪���ֻ���֧�ֵĶ�λ��
                
                SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                String location = sp.getString("lostLocation", "");
                return location;
        }
        
        //ֹͣgps
        public void stopGPSListener()
        {
                if(locationManager != null)
                {
                        locationManager.removeUpdates(getListener());
                }
        }
        
        private String getBestProvider()
        {
                Criteria criteria = new Criteria();
                //����Ƕ������Ķ�λ���ȵ�
                //Criteria.ACCURACY_COARSE  �����һ��Ķ�λ
                //Criteria.ACCURACY_FINE  ����Ǿ�׼��λ
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                
                //�����ǲ��ǶԺ������е�
                criteria.setAltitudeRequired(false);
                
                //���ö��ֻ��ĺĵ�������λҪ��Խ�ߣ�Խ�ĵ�
                criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
                
                //���ö��ٶȱ仯�ǲ�������
                criteria.setSpeedRequired(true);
                
                //�����ڶ�λʱ���ǲ�����������Ӫ�̽������ݵĿ���
                criteria.setCostAllowed(true);
                
                //��������������õ���õĶ�λ��ʽ�ģ���������������һ����Criteria(������Map����)������һЩ����������˵�Լ��ٶ����а���ʲô����������Щ��
                //�ڶ����������ǣ����������Ϊfalse����ô���ǵõ���Ҳ�п������Ѿ��ص��˵��豸�������true��ô����ֻ��õ��Ѿ����˵��豸
                return locationManager.getBestProvider(criteria, true);
        }
        
        //���ɵ���ģʽ
        private synchronized MyLocationListener getListener()
        {
                if(listener == null)
                {
                        listener = new MyLocationListener();
                }
                return listener;
        }
        
        //=========================================================================
        
        private class MyLocationListener implements LocationListener
        {

                @Override
                public void onLocationChanged(Location location)
                {
                        //�ֻ�λ�÷����ı�ʱ���õķ���
                        String latitude = "γ�ȣ�" + location.getLatitude();//γ��
                        String longitude = "���ȣ�" + location.getLongitude();//����
                        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                        Editor editor = sp.edit();
                        editor.putString("lastLocation", latitude + " - " + longitude);
                        editor.commit();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras)
                {
                        //��λ�豸��״̬�����ı��ʱ����õķ���������˵�û����豸�򿪣���رգ��ڶ������������豸��״̬��
                }

                @Override
                public void onProviderEnabled(String provider)
                {
                        //�豸�򿪵�ʱ����õķ���
                }

                @Override
                public void onProviderDisabled(String provider)
                {
                        //�豸�رյ�ʱ����õķ���
                }
                
        }

}