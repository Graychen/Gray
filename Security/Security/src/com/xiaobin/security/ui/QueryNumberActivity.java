package com.xiaobin.security.ui;

import com.example.security.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QueryNumberActivity extends Activity{
	private TextView tv_result;
	private EditText et_query_number;
	private Button bt_query;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_number);
		
		tv_result=(TextView)findViewById(R.id.tv_query_result);
		et_query_number=(EditText)findViewById(R.id.et_query_number);
		
	}
	
	public void query(View v)
	{
		
		
	}
	
	
	
}
