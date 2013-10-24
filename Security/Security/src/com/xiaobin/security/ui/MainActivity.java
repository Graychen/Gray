package com.xiaobin.security.ui;


import com.example.security.R;
import com.xiaobin.security.adapter.MainUIAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener
{
        private GridView gridView;
        
        private MainUIAdapter adapter ;
        private SharedPreferences sp;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                
                sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
                gridView = (GridView) findViewById(R.id.gv_main);
                adapter = new MainUIAdapter(this);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(this);
                gridView.setOnItemLongClickListener(new OnItemLongClickListener()
                {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id)
                        {
                                if(position == 0)        //�������Ϊ��������ǵ��ֻ������ˣ��û�һ������һ���ֻ������������϶�����ж�����ǵĳ���ģ������������ֻ��������item���棬������һ���������Ĺ���
                                {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("����");
                                        builder.setMessage("������Ҫ���Ե�����");
                                        final EditText et = new EditText(MainActivity.this);
                                        et.setHint("������");
                                        builder.setView(et);
                                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                                        {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                        String name = et.getText().toString();
                                                        if(name.equals(""))
                                                        {
                                                                Toast.makeText(MainActivity.this, "�������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                                Editor editor = sp.edit();
                                                                editor.putString("lostName", name);
                                                                editor.commit();
                                                                
                                                                TextView tv = (TextView) view.findViewById(R.id.tv_main_name);
                                                                tv.setText(name);
                                                                adapter.notifyDataSetChanged();
                                                        }
                                                }
                                        });
                                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
                                        {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                        // TODO Auto-generated method stub
                                                        
                                                }
                                        });
                                        builder.create().show();
                                }
                                return false;
                        }
                });
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
                switch(position)
                {
                        case 0 : //�ֻ�����
                                break;
                                
                        case 1 : //ͨѶ��ʿ
                                break;
                                
                        case 2 : //�������
                                break;
                                
                        case 3 : //��������
                                break;
                                
                        case 4 : //�������
                                break;
                                
                        case 5 : //�ֻ�ɱ��
                                break;
                                
                        case 6 : //ϵͳ�Ż�
                                break;
                                
                        case 7 : //�߼�����
                                break;
                                
                        case 8 : //��������
                                break;
                                
                        default : 
                                break;
                }
        }

}
