package com.xiaobin.security.adapter;

import com.example.security.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class MainUIAdapter extends BaseAdapter
{
        private static final String[] NAMES = new String[] {"�ֻ�����", "ͨѶ��ʿ", "��������", "��������", "�������", "�ֻ�ɱ��", 
                        "ϵͳ�Ż�", "�߼�����", "��������"};
        
        private static final int[] ICONS = new int[] {R.drawable.widget01, R.drawable.widget02, R.drawable.widget03, 
                        R.drawable.widget04, R.drawable.widget05, R.drawable.widget06, R.drawable.widget07, 
                        R.drawable.widget08, R.drawable.widget09};
        
        //�����ɾ�̬����һ�����Ż����ã�����adapter���б���Ż������ģ��л���������˵
        private static ImageView imageView;
        private static TextView textView;
        
        private Context context;
        private LayoutInflater inflater;
        private SharedPreferences sp;
        
        public MainUIAdapter(Context context)
        {
                this.context = context;
                inflater = LayoutInflater.from(this.context);
                sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        @Override
        public int getCount()
        {
                return NAMES.length;
        }

        @Override
        public Object getItem(int position)
        {
                return position;
        }

        @Override
        public long getItemId(int position)
        {
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
                View view = inflater.inflate(R.layout.main_item, null);
                imageView = (ImageView) view.findViewById(R.id.iv_main_icon);
                textView = (TextView) view.findViewById(R.id.tv_main_name);
                imageView.setImageResource(ICONS[position]);
                textView.setText(NAMES[position]);
                        
                if(position == 0)
                {
                        String name = sp.getString("lostName", "");
                        if(!name.equals(""))
                        {
                                textView.setText(name);
                        }
                }
                
                return view;
        }
        
}