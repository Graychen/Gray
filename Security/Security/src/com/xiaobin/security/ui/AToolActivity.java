package com.xiaobin.security.ui;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.security.R;
import com.xiaobin.security.engine.DownloadTask;



public class AToolActivity extends Activity implements OnClickListener
{
    private static final int ERROR = 0;
    private static final int SUCCESS = 1;
	
        private TextView tv_atool_query;
        private ProgressDialog pd;
        
        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler()
        {
                public void handleMessage(Message msg) 
                {
                        switch(msg.what)
                        {
                                case ERROR : 
                                        Toast.makeText(AToolActivity.this, "下载数据库失败，请检查网络！", Toast.LENGTH_SHORT).show();
                                        break;
                                        
                                case SUCCESS : 
                                        Toast.makeText(AToolActivity.this, "数据库下载成功！", Toast.LENGTH_SHORT).show();
                                        break;
                                        
                                default : 
                                        break;
                        }
                }
        };
        
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.atool);
                
                tv_atool_query = (TextView) findViewById(R.id.tv_atool_query);
                tv_atool_query.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
                switch(v.getId())
                {
                        case R.id.tv_atool_query : 
                        		if(isDBExist())
                        		{
                                Intent intent = new Intent(this, QueryNumberActivity.class);
                                startActivity(intent);
                        		}
                        		else
                        		{
                        			pd=new ProgressDialog(this);
                        			pd.setMessage("正在下载数据库。。。");
                        			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        			pd.setCancelable(false);
                        			pd.show();
                        			new Thread()
                        			{
                        				public void run()
                        				{
                        					String path=getResources().getString(R.string.serverdb);
                        					File dir = new File(Environment.getExternalStorageDirectory(),"/security/db");
                        					if(!dir.exists())
                        					{
                        						dir.mkdir();
                        					}
                        					String dbPath = Environment.getExternalStorageState()+"/security/db/data.db";
                        					try
                        					{
                        					    DownloadTask.getFile(path, dbPath, pd);
                                                pd.dismiss();
                        					}
                        					catch(Exception e){
                        						e.printStackTrace();
                        						pd.dismiss();
                        						Message message = new Message();
                        						message.what=ERROR;
                        						handler.sendMessage(message);
                        					}
                        				}
                        			}.start();
                        		}
                                break;
                                
                        default : 
                                break;
                }
        }

        private boolean isDBExist()
        {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                        File file = new File(Environment.getExternalStorageDirectory() + "/security/db/data.db");
                        if(file.exists())
                        {
                                return true;
                        }
                }
                return false;
        }

}
