package com.xiaobin.security.ui;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.security.R;
import com.xiaobin.security.domain.UpdateInfo;
import com.xiaobin.security.engine.DownloadTask;
import com.xiaobin.security.engine.UpdateInfoService;

public class SplashActivity extends Activity
{
        private TextView tv_version;
        private LinearLayout ll;
        private ProgressDialog progressDialog;
        
        private UpdateInfo info;
        private String version;
        
        private static final String TAG = "Security";
        
        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler()
        {
                public void handleMessage(Message msg) 
                {
                        if(isNeedUpdate(version))
                        {
                                showUpdateDialog();
                        }
                };
        };
        
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.splash);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
                tv_version = (TextView) findViewById(R.id.tv_splash_version);
                version = getVersion();
                tv_version.setText("�汾��  " + version);
                
                ll = (LinearLayout) findViewById(R.id.ll_splash_main);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setDuration(2000);
                ll.startAnimation(alphaAnimation);
                
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("��������...");
                
                new Thread()
                {
                        public void run() 
                        {
                                try
                                {
                                        sleep(3000);
                                        handler.sendEmptyMessage(0);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                        };
                }.start();
                
        }
        
        private void showUpdateDialog()
        {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("��������");
                builder.setMessage(info.getDescription());
                builder.setCancelable(false);
                
                builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
                {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                                {
                                        File dir = new File(Environment.getExternalStorageDirectory(), "/security/update");
                                        if(!dir.exists())
                                        {
                                                dir.mkdirs();
                                        }
                                        String apkPath = Environment.getExternalStorageDirectory() + "/security/update/new.apk";
                                        UpdateTask task = new UpdateTask(info.getUrl(), apkPath);
                                        progressDialog.show();
                                        new Thread(task).start();
                                }
                                else
                                {
                                        Toast.makeText(SplashActivity.this, "SD�������ã������SD��", Toast.LENGTH_SHORT).show();
                                        loadMainUI();
                                }
                        }
                });
                builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
                {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                                loadMainUI();
                        }
                        
                });
                builder.create().show();
        }

        private boolean isNeedUpdate(String version)
        {
                UpdateInfoService updateInfoService = new UpdateInfoService(this);
                try
                {
                        info = updateInfoService.getUpdateInfo(R.string.serverUrl);
                        String v = info.getVersion();
                        if(v.equals(version))
                        {
                                Log.i(TAG, "��ǰ�汾��" + version);
                                Log.i(TAG, "���°汾��" + v);
                                loadMainUI();
                                return false;
                        }
                        else
                        {
                                Log.i(TAG, "��Ҫ����");
                                return true;
                        }
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        Toast.makeText(this, "��ȡ������Ϣ�쳣�����Ժ�����", Toast.LENGTH_SHORT).show();
                        loadMainUI();
                }
                return false;
        }

        private String getVersion()
        {
                try
                {
                        PackageManager packageManager = getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                        
                        return packageInfo.versionName;
                }
                catch (NameNotFoundException e)
                {
                        e.printStackTrace();
                        return "�汾��δ֪";
                }
        }
        
        private void loadMainUI()
        {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        
        /**
         * ��װapk
         * @param file Ҫ��װ��apk��Ŀ¼
         */
        private void install(File file)
        {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                finish();
                startActivity(intent);
        }
        
        //===========================================================================================
        
        /**
         * ���ص��߳�
         *
         */
        class UpdateTask implements Runnable
        {
                private String path;
                private String filePath;
                
                public UpdateTask(String path, String filePath)
                {
                        this.path = path;
                        this.filePath = filePath;
                }

                @Override
                public void run()
                {
                        try
                        {
                                File file = DownloadTask.getFile(path, filePath, progressDialog);
                                progressDialog.dismiss();
                                install(file);
                        }
                        catch (Exception e)
                        {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(SplashActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
                                loadMainUI();
                        }
                }
                
        }

}
