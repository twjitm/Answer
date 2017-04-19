package com.example.answer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button startBtn;
	
	private ImageView left;
	private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        left=(ImageView) findViewById(R.id.left);
        title=(TextView) findViewById(R.id.title);
        startBtn=(Button) findViewById(R.id.start);
        
        left.setVisibility(View.GONE);
        title.setText("答题测试");
        
        startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,AnalogyExaminationActivity.class);
				startActivity(intent);
			}
		});
        
    }


}
