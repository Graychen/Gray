package com.xiaobin.security.ui;

import com.example.security.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class AToolActivity extends Activity implements OnClickListener
{
        private TextView tv_atool_query;
        
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
                                Intent intent = new Intent(this, QueryNumberActivity.class);
                                startActivity(intent);
                                break;
                                
                        default : 
                                break;
                }
        }

}
