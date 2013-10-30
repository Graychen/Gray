package com.xiaobin.security.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.security.R;
import com.xiaobin.security.receiver.MyAdminReceiver;

public class SetupGuide4Activity extends Activity implements OnClickListener{
	
	private Button bt_pervious;
	private Button bt_finish;
	private CheckBox cb_protected;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide4);
		
		bt_pervious = (Button)findViewById(R.id.bt_guide_pervious);
		bt_finish = (Button)findViewById(R.id.bt_guide_finish);
		bt_finish.setOnClickListener(this);
		bt_finish.setOnClickListener(this);
		
		cb_protected = (CheckBox)findViewById(R.id.cb_guide_protected);
		
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean isProtecting = sp.getBoolean("isProtected", false);
		if(isProtecting)
		{
			cb_protected.setText("已经开启保护");
			cb_protected.setChecked(true);
		}
		
		   cb_protected.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				  if(isChecked){
					cb_protected.setText("已经开启保护");
					Editor editor=sp.edit();
					editor.putBoolean("isProtected",true);
					editor.commit();
				}else{
					cb_protected.setText("没有开启保护");
					Editor editor = sp.edit();
					editor.putBoolean("isProtected", false);
					editor.commit();
				}
			}
			   
		}
		);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_guide_finish:
			if(cb_protected.isChecked())
			{
				finishSetupGuide();
				finish();
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("提醒");
				builder.setMessage("强烈建议您开启保护，是否完成设置");
				builder.setCancelable(false);
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finishSetupGuide();
						finish();
					}
				});
				builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finishSetupGuide();
					}
				});
				builder.create().show();
			}
			break;
			
		case R.id.bt_guide_pervious:
		    Intent intent = new Intent(this, SetupGuide3Activity.class);
		    finish();
		    startActivity(intent);
		    overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
		    break;
		
		default:
			break;
	}
		}
	
	private void finishSetupGuide()
	{
		Editor editor = sp.edit();
		editor.putBoolean("setupGuide", true);//记录是否已经进行过设置向导了
		editor.commit();
		
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager)
		getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);
		if(!devicePolicyManager.isAdminActive(componentName))
		{
			 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			 intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, componentName);
			 startActivity(intent);
		}
	}
	
}
