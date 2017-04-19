package com.example.answer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.answer.adapter.ExaminationSubmitAdapter;
import com.example.answer.bean.AnSwerInfo;
import com.example.answer.bean.ErrorQuestionInfo;
import com.example.answer.bean.SaveQuestionInfo;
import com.example.answer.database.DBManager;
import com.example.answer.util.ConstantData;
import com.example.answer.util.ConstantUtil;
import com.example.answer.util.ViewPagerScroller;
import com.example.answer.view.VoteSubmitViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 答题
 * 
 * @author 金钟焕
 */
public class AnalogyExaminationActivity extends Activity {

	private ImageView leftIv;
	private TextView titleTv;
	private TextView right;

	VoteSubmitViewPager viewPager;
	ExaminationSubmitAdapter pagerAdapter;
	List<View> viewItems = new ArrayList<View>();
	List<AnSwerInfo> dataItems = new ArrayList<AnSwerInfo>();
	private ProgressDialog progressDialog;

	private String pageCode;
	private int pageScore;
	private int errortopicNums;// 错题数
	private int errortopicNums1;// 错题数
	private String isPerfectData = "1";// 是否完善资料0完成 1未完成
	private String type = "0";// 0模拟 1竞赛
	private String errorMsg="";
	
	Dialog builderSubmit;

	public List<Map<String, SaveQuestionInfo>> list = new ArrayList<Map<String, SaveQuestionInfo>>();
	public Map<String, SaveQuestionInfo> map2 = new HashMap<String, SaveQuestionInfo>();
	public List<SaveQuestionInfo> questionInfos = new ArrayList<SaveQuestionInfo>();

	Timer timer;
	TimerTask timerTask;
	int minute = 5;
	int second = 0;

	boolean isPause = false;
	int isFirst;

	DBManager dbManager;

	String dateStr = "";
	String imgServerUrl = "";
	
	private boolean isUpload= false;
	
	
	private Handler handlerSubmit = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch (msg.what) {
			case 1:
				showSubmitDialog();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						builderSubmit.dismiss();
						finish();
					}
				}, 3000);
				break;
			default:
				break;
			}
			
		}
	};


	Handler handlerTime = new Handler() {
		public void handleMessage(Message msg) {
			// 判断时间快到前2分钟字体颜色改变
			if (minute < 2) {
				right.setTextColor(Color.RED);
			} else {
				right.setTextColor(Color.WHITE);
			}
			if (minute == 0) {
				if (second == 0) {
					isFirst+=1;
					// 时间到
					if(isFirst==1){
						showTimeOutDialog(true, "0");
					}
					right.setText("00:00");
					if (timer != null) {
						timer.cancel();
						timer = null;
					}
					if (timerTask != null) {
						timerTask = null;
					}
				} else {
					second--;
					if (second >= 10) {
						right.setText("0" + minute + ":" + second);
					} else {
						right.setText("0" + minute + ":0" + second);
					}
				}
			} else {
				if (second == 0) {
					second = 59;
					minute--;
					if (minute >= 10) {
						right.setText(minute + ":" + second);
					} else {
						right.setText("0" + minute + ":" + second);
					}
				} else {
					second--;
					if (second >= 10) {
						if (minute >= 10) {
							right.setText(minute + ":" + second);
						} else {
							right.setText("0" + minute + ":" + second);
						}
					} else {
						if (minute >= 10) {
							right.setText(minute + ":0" + second);
						} else {
							right.setText("0" + minute + ":0" + second);
						}
					}
				}
			}
		};
	};

	private Handler handlerStopTime = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				stopTime();
				break;
			case 1:
				startTime();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_practice_test);
		dbManager = new DBManager(AnalogyExaminationActivity.this);
		dbManager.openDB();
		initView();
		loadData();
		ErrorQuestionInfo[] errorQuestionInfos = dbManager.queryAllData();
		if (errorQuestionInfos != null) {
			// 删除上次保存的我的错题
			int colunm = (int) dbManager.deleteAllData();
		}


	}

	public void initView() {
		leftIv = (ImageView) findViewById(R.id.left);
		titleTv = (TextView) findViewById(R.id.title);
		right = (TextView) findViewById(R.id.right);
		titleTv.setText("模拟答题");
		Drawable drawable1 = getBaseContext().getResources().getDrawable(
				R.drawable.ic_practice_time);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getMinimumHeight());
		right.setCompoundDrawables(drawable1, null, null, null);
		right.setText("15:00");
		viewPager = (VoteSubmitViewPager) findViewById(R.id.vote_submit_viewpager);
		leftIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// finish();
				isPause = true;
				showTimeOutDialog(true, "1");
				Message msg = new Message();
				msg.what = 0;
				handlerStopTime.sendMessage(msg);
			}
		});
		
		initViewPagerScroll();
		
	}
	
	private void loadData(){
		for (int i = 0; i < ConstantData.answerName.length; i++) {
			AnSwerInfo info = new AnSwerInfo();
			info.setQuestionId(ConstantData.answerId[i]);// 试题主键
			info.setQuestionName(ConstantData.answerName[i]);// 试题题目
			info.setQuestionType(ConstantData.answerType[i]);// 试题类型0单选1多选
			info.setQuestionFor("0");// （0模拟试题，1竞赛试题）
			info.setAnalysis(ConstantData.answerAnalysis[i]);// 试题分析
			info.setCorrectAnswer(ConstantData.answerCorrect[i]);// 正确答案
			info.setOptionA(ConstantData.answerOptionA[i]);// 试题选项A
			info.setOptionB(ConstantData.answerOptionB[i]);// 试题选项B
			info.setOptionC(ConstantData.answerOptionC[i]);// 试题选项C
			info.setOptionD(ConstantData.answerOptionD[i]);// 试题选项D
			info.setOptionE(ConstantData.answerOptionE[i]);// 试题选项E
			info.setScore(ConstantData.answerScore[i]);// 分值
			info.setOption_type("0");
			dataItems.add(info);
		}


		for (int i = 0; i < dataItems.size(); i++) {
			viewItems.add(getLayoutInflater().inflate(
					R.layout.vote_submit_viewpager_item, null));
		}
		pagerAdapter = new ExaminationSubmitAdapter(
				AnalogyExaminationActivity.this, viewItems,
				dataItems,imgServerUrl);
		viewPager.setAdapter(pagerAdapter);
		viewPager.getParent()
				.requestDisallowInterceptTouchEvent(false);
	}
	
	/**
     * 设置ViewPager的滑动速度
     * 
     * */
    private void initViewPagerScroll( ){
    try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true); 
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        }catch(NoSuchFieldException e){
       
        }catch (IllegalArgumentException e){
       
        }catch (IllegalAccessException e){
       
        }
    }

	/**
	 * @param index
	 *            根据索引值切换页面
	 */
	public void setCurrentView(int index) {
		viewPager.setCurrentItem(index);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopTime();
		minute = -1;
		second = -1;
		super.onDestroy();
	}

	// 提交试卷
	public void uploadExamination(int errortopicNum) {
		// TODO Auto-generated method stub
		String resultlist = "[";
		errortopicNums = errortopicNum;
		
		if(questionInfos.size()>0){
			//选择过题目
			//全部选中
			if(questionInfos.size()==dataItems.size()){
				for (int i = 0; i < questionInfos.size(); i++) {
					if (i == questionInfos.size() - 1) {
						resultlist += questionInfos.get(i).toString() + "]";
					} else {
						resultlist += questionInfos.get(i).toString() + ",";
					}
					if (questionInfos.size() == 0) {
						resultlist += "]";
					}
					if (questionInfos.get(i).getIs_correct()
							.equals(ConstantUtil.isCorrect)) {
						int score = Integer.parseInt(questionInfos.get(i).getScore());
						pageScore += score;
					}
				}
			}else{
				//部分选中
				for (int i = 0; i < dataItems.size(); i++) {
					if(dataItems.get(i).getIsSelect()==null){
						errortopicNums1+=1;
						//保存数据
						SaveQuestionInfo questionInfo=new SaveQuestionInfo();
						questionInfo.setQuestionId(dataItems.get(i).getQuestionId());
						questionInfo.setQuestionType(dataItems.get(i).getQuestionType());
						questionInfo.setRealAnswer(dataItems.get(i).getCorrectAnswer());
						questionInfo.setScore(dataItems.get(i).getScore());
						questionInfo.setIs_correct(ConstantUtil.isError);
						questionInfos.add(questionInfo);
					}
				}
				
				for (int i = 0; i < dataItems.size(); i++){
					if (i == dataItems.size() - 1) {
						resultlist += questionInfos.get(i).toString() + "]";
					} else {
						resultlist += questionInfos.get(i).toString() + ",";
					}
					if (dataItems.size() == 0) {
						resultlist += "]";
					}
					if (questionInfos.get(i).getIs_correct()
							.equals(ConstantUtil.isCorrect)) {
						int score = Integer.parseInt(questionInfos.get(i).getScore());
						pageScore += score;
					}
				}
			}
		}else{
			//没有选择题目
			for (int i = 0; i < dataItems.size(); i++) {
				if(dataItems.get(i).getIsSelect()==null){
					errortopicNums1+=1;
					//保存数据
					SaveQuestionInfo questionInfo=new SaveQuestionInfo();
					questionInfo.setQuestionId(dataItems.get(i).getQuestionId());
					questionInfo.setQuestionType(dataItems.get(i).getQuestionType());
					questionInfo.setRealAnswer(dataItems.get(i).getCorrectAnswer());
					questionInfo.setScore(dataItems.get(i).getScore());
					questionInfo.setIs_correct(ConstantUtil.isError);
					questionInfos.add(questionInfo);
				}
			}
			
			for (int i = 0; i < dataItems.size(); i++){
				if (i == dataItems.size() - 1) {
					resultlist += questionInfos.get(i).toString() + "]";
				} else {
					resultlist += questionInfos.get(i).toString() + ",";
				}
				if (dataItems.size() == 0) {
					resultlist += "]";
				}
				if (questionInfos.get(i).getIs_correct()
						.equals(ConstantUtil.isCorrect)) {
					int score = Integer.parseInt(questionInfos.get(i).getScore());
					pageScore += score;
				}
			}
		}
		
		System.out.println("提交的已经选择的题目数组给后台===="+resultlist);
		
		
		Message msg = handlerSubmit.obtainMessage();
		msg.what = 1;
		handlerSubmit.sendMessage(msg);

	}

	// 弹出对话框通知用户答题时间到
	protected void showTimeOutDialog(final boolean flag, final String backtype) {
		final Dialog builder = new Dialog(this, R.style.dialog);
		builder.setContentView(R.layout.my_dialog);
		TextView title = (TextView) builder.findViewById(R.id.dialog_title);
		TextView content = (TextView) builder.findViewById(R.id.dialog_content);
		if (backtype.equals("0")) {
			content.setText("您的答题时间结束,是否提交试卷?");
		} else if(backtype.equals("1")){
			content.setText("您要结束本次模拟答题吗？");
		}else{
			content.setText(errorMsg+"");
		}
		final Button confirm_btn = (Button) builder
				.findViewById(R.id.dialog_sure);
		Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
		if (backtype.equals("0")) {
			confirm_btn.setText("提交");
			cancel_btn.setText("退出");
		} else if(backtype.equals("1")){
			confirm_btn.setText("退出");
			cancel_btn.setText("继续答题");
		}else{
			confirm_btn.setText("确定");
			cancel_btn.setVisibility(View.GONE);
		}
		confirm_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (backtype.equals("0")){
					builder.dismiss();
						uploadExamination(pagerAdapter.errorTopicNum());
				}else{
					builder.dismiss();
					finish();
				}
			}
		});

		cancel_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (backtype.equals("0")) {
					finish();
					builder.dismiss();
				} else {
					isPause = false;
					builder.dismiss();
					Message msg = new Message();
					msg.what = 1;
					handlerStopTime.sendMessage(msg);
				}
			}
		});
		builder.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		builder.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {

				return flag;
			}
		});
		builder.show();
	}
	
	
	// 弹出对话框通知用户提交成功
	protected void showSubmitDialog() {
		builderSubmit = new Dialog(this, R.style.dialog);
		builderSubmit.setContentView(R.layout.my_dialog);
		TextView title = (TextView) builderSubmit.findViewById(R.id.dialog_title);
		TextView content = (TextView) builderSubmit.findViewById(R.id.dialog_content);
		content.setText("提交成功，感谢您的参与!");
		final Button confirm_btn = (Button) builderSubmit
				.findViewById(R.id.dialog_sure);
		confirm_btn.setVisibility(View.GONE);
		Button cancel_btn = (Button) builderSubmit.findViewById(R.id.dialog_cancle);
		cancel_btn.setVisibility(View.GONE);
		builderSubmit.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		builderSubmit.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				
				return true;
			}
		});
		builderSubmit.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			isPause = true;
			showTimeOutDialog(true, "1");
			Message msg = new Message();
			msg.what = 0;
			handlerStopTime.sendMessage(msg);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = 0;
		handlerStopTime.sendMessage(msg);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 1;
			handlerStopTime.sendMessage(msg);
		super.onResume();
	}

	private void startTime() {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					Message msg = new Message();
					msg.what = 0;
					handlerTime.sendMessage(msg);
				}
			};
		}
		if (timer != null && timerTask != null) {
			timer.schedule(timerTask, 0, 1000);
		}
	}
	
	private void stopTime(){
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
		if(timerTask!=null){
			timerTask.cancel();
			timerTask=null;
		}
	}

}
