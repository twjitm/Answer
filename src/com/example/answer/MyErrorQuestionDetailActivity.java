package com.example.answer;

import com.example.answer.util.ConstantUtil;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 错题详情
 * 
 * @author 金钟焕
 */
public class MyErrorQuestionDetailActivity extends Activity {
	
	private ImageView left;
	private TextView title;
	
	private TextView questionTypeTV;
	private TextView questionNameTV;
	private LinearLayout layoutA;
	private LinearLayout layoutB;
	private LinearLayout layoutC;
	private LinearLayout layoutD;
	private LinearLayout layoutE;
	private ImageView ivA;
	private ImageView ivB;
	private ImageView ivC;
	private ImageView ivD;
	private ImageView ivE;
	private TextView tvA;
	private TextView tvB;
	private TextView tvC;
	private TextView tvD;
	private TextView tvE;
	private ImageView ivA_;
	private ImageView ivB_;
	private ImageView ivC_;
	private ImageView ivD_;
	private ImageView ivE_;
	private LinearLayout wrongLayout;
	private TextView explaindetailTv;
	
	private String questionName="";
	private String questionType="";
	private String questionAnswer="";
	private String questionSelect="";
	private String isRight="";
	private String Analysis="";
	private String optionA="";
	private String optionB="";
	private String optionC="";
	private String optionD="";
	private String optionE="";
	private String optionType="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_error_question_detail);
		initView();
	}
	
	private void initView(){
		questionName=getIntent().getStringExtra("questionName");
		questionType=getIntent().getStringExtra("questionType");
		questionAnswer=getIntent().getStringExtra("questionAnswer");
		questionSelect=getIntent().getStringExtra("questionSelect");
		isRight=getIntent().getStringExtra("isRight");
		Analysis=getIntent().getStringExtra("Analysis");
		optionA=getIntent().getStringExtra("optionA");
		optionB=getIntent().getStringExtra("optionB");
		optionC=getIntent().getStringExtra("optionC");
		optionD=getIntent().getStringExtra("optionD");
		optionE=getIntent().getStringExtra("optionE");
		optionType=getIntent().getStringExtra("optionType");
		left = (ImageView) findViewById(R.id.left);
		title = (TextView) findViewById(R.id.title);
		title.setText("我的错题");
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		questionTypeTV=(TextView) findViewById(R.id.activity_prepare_test_no);
		questionNameTV=(TextView) findViewById(R.id.activity_prepare_test_question);
		layoutA=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_a);
		layoutB=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_b);
		layoutC=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_c);
		layoutD=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_d);
		layoutE=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_e);
		ivA=(ImageView) findViewById(R.id.vote_submit_select_image_a);
		ivB=(ImageView) findViewById(R.id.vote_submit_select_image_b);
		ivC=(ImageView) findViewById(R.id.vote_submit_select_image_c);
		ivD=(ImageView) findViewById(R.id.vote_submit_select_image_d);
		ivE=(ImageView) findViewById(R.id.vote_submit_select_image_e);
		tvA=(TextView) findViewById(R.id.vote_submit_select_text_a);
		tvB=(TextView) findViewById(R.id.vote_submit_select_text_b);
		tvC=(TextView) findViewById(R.id.vote_submit_select_text_c);
		tvD=(TextView) findViewById(R.id.vote_submit_select_text_d);
		tvE=(TextView) findViewById(R.id.vote_submit_select_text_e);
		ivA_=(ImageView) findViewById(R.id.vote_submit_select_image_a_);
		ivB_=(ImageView) findViewById(R.id.vote_submit_select_image_b_);
		ivC_=(ImageView) findViewById(R.id.vote_submit_select_image_c_);
		ivD_=(ImageView) findViewById(R.id.vote_submit_select_image_d_);
		ivE_=(ImageView) findViewById(R.id.vote_submit_select_image_e_);
		wrongLayout=(LinearLayout) findViewById(R.id.activity_prepare_test_wrongLayout);
		explaindetailTv=(TextView) findViewById(R.id.activity_prepare_test_explaindetail);
		
			questionNameTV.setText(""+questionName);
			
			if(optionA.equals("")){
				layoutA.setVisibility(View.GONE);
			}if(optionB.equals("")){
				layoutB.setVisibility(View.GONE);
			}if(optionC.equals("")){
				layoutC.setVisibility(View.GONE);
			}if(optionD.equals("")){
				layoutD.setVisibility(View.GONE);
			}if(optionE.equals("")){
				layoutE.setVisibility(View.GONE);
			}
			
				//文字题目
				ivA_.setVisibility(View.GONE);
				ivB_.setVisibility(View.GONE);
				ivC_.setVisibility(View.GONE);
				ivD_.setVisibility(View.GONE);
				ivE_.setVisibility(View.GONE);
				tvA.setVisibility(View.VISIBLE);
				tvB.setVisibility(View.VISIBLE);
				tvC.setVisibility(View.VISIBLE);
				tvD.setVisibility(View.VISIBLE);
				tvE.setVisibility(View.VISIBLE);
				tvA.setText("A." + optionA);
				tvB.setText("B." + optionB);
				tvC.setText("C." + optionC);
				tvD.setText("D." + optionD);
				tvE.setText("E." + optionE);
			
			if(questionType.equals("0")){
				questionTypeTV.setText("(单选题)");
				//显示正确选项
				if(questionAnswer.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_right);
					tvA.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_right);
					tvB.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_right);
					tvC.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_right);
					tvD.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_right);
					tvE.setTextColor(Color.parseColor("#61bc31"));
				}
				
				if(questionSelect.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_wrong);
					tvA.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_wrong);
					tvB.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_wrong);
					tvC.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_wrong);
					tvD.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_wrong);
					tvE.setTextColor(Color.parseColor("#d53235"));
				}
				
			}else if(questionType.equals("1")){
				questionTypeTV.setText("(多选题)");
				//显示正确选项
				if(questionAnswer.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_right);
					tvA.setTextColor(Color.parseColor("#61bc31"));
				}if(questionAnswer.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_right);
					tvB.setTextColor(Color.parseColor("#61bc31"));
				}if(questionAnswer.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_right);
					tvC.setTextColor(Color.parseColor("#61bc31"));
				}if(questionAnswer.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_right);
					tvD.setTextColor(Color.parseColor("#61bc31"));
				}if(questionAnswer.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_right);
					tvE.setTextColor(Color.parseColor("#61bc31"));
				}
				
				if(questionSelect.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_wrong);
					tvA.setTextColor(Color.parseColor("#d53235"));
				}if(questionSelect.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_wrong);
					tvB.setTextColor(Color.parseColor("#d53235"));
				}if(questionSelect.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_wrong);
					tvC.setTextColor(Color.parseColor("#d53235"));
				}if(questionSelect.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_wrong);
					tvD.setTextColor(Color.parseColor("#d53235"));
				}if(questionSelect.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_wrong);
					tvE.setTextColor(Color.parseColor("#d53235"));
				}
			}else if(questionType.equals("2")){
				questionTypeTV.setText("(判断题)");
				//显示正确选项
				if(questionAnswer.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_right);
					tvA.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_right);
					tvB.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_right);
					tvC.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_right);
					tvD.setTextColor(Color.parseColor("#61bc31"));
				}else if(questionAnswer.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_right);
					tvE.setTextColor(Color.parseColor("#61bc31"));
				}
				if(questionSelect.contains("A")){
					ivA.setImageResource(R.drawable.ic_practice_test_wrong);
					tvA.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("B")){
					ivB.setImageResource(R.drawable.ic_practice_test_wrong);
					tvB.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("C")){
					ivC.setImageResource(R.drawable.ic_practice_test_wrong);
					tvC.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("D")){
					ivD.setImageResource(R.drawable.ic_practice_test_wrong);
					tvD.setTextColor(Color.parseColor("#d53235"));
				}else if(questionSelect.contains("E")){
					ivE.setImageResource(R.drawable.ic_practice_test_wrong);
					tvE.setTextColor(Color.parseColor("#d53235"));
				}
				
				layoutC.setVisibility(View.GONE);
				layoutD.setVisibility(View.GONE);
			}
			
			if(isRight.equals(ConstantUtil.isError)){
				wrongLayout.setVisibility(View.VISIBLE);
				explaindetailTv.setText(""+Analysis);
			}else{
				wrongLayout.setVisibility(View.GONE);
			}
	}

}
