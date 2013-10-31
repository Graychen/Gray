package com.xiaobin.security.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.security.R;
import com.xiaobin.security.engine.NumberAddressService;

public class QueryNumberActivity extends Activity{
	private TextView tv_result;
	private EditText et_query_number;

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
		
		String number = et_query_number.getText().toString().trim();
		if(TextUtils.isEmpty(number))
		{
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_query_number.startAnimation(shake);
		}else
		{
			   String address = NumberAddressService.getAddress(number);
               tv_result.setText("πÈ Ùµÿ–≈œ¢£∫" + address);
		}
	}
	
	
	
}
