package com.example.answer.adapter;

import java.util.List;
import java.util.Map;

import com.example.answer.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的错题列表
 * 
 * 
 */
public class MyErrorQuestionListAdapter extends BaseAdapter {
	private ListView listView;
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;
	private Context context;

	public MyErrorQuestionListAdapter(Context context, List<Map<String, Object>> list,
			ListView listView) {
		// this.context = context;
		this.listView = listView;
		this.context=context;
		this.list = list;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> map = list.get(position);
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.my_error_question_item, null);

			viewHolder.title = (TextView) convertView.findViewById(R.id.my_error_item_name);
		}
		if (map != null && map.size() > 0) {

			viewHolder.title.setText(position+1+"."+map.get("title").toString());
			
		}

		convertView.setTag(viewHolder);
		return convertView;
	}

	/**
	 * 得到数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> GetData() {
		return list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		TextView title;
	}
}
