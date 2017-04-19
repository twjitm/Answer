package com.example.answer.database;

import com.example.answer.bean.ErrorQuestionInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作类
 * 
 * @author jinzufan
 * 
 */
public class DBManager {

	private Context context;
	private SQLiteDatabase database;

	public DBManager(Context context) {
		this.context = context;
	}

	/**
	 * 打开数据库，如果不存在则创建一个数据库
	 */
	public void openDB() {
		DBHelper dbHelper = new DBHelper(context);
		if (database == null || !database.isOpen()) {
			database = dbHelper.getWritableDatabase();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (database != null && database.isOpen()) {
			database.close();
		}
	}

	/**
	 * 删除我的题库所有数据
	 * 
	 * @return
	 */
	public long deleteLibraryAllData() {
		return database.delete(DBHelper.TABLE_NAME_TEST_LIBRARY, null,
				null);
	}
	
	/**
	 * 添加一条我的错题数据
	 * 
	 * @param info
	 * @return
	 */
	public long insertErrorQuestion(ErrorQuestionInfo info) {
		ContentValues newValues = new ContentValues();

		newValues.put(DBHelper.MY_ERROR_QUESTION_NAME, info.getQuestionName());
		newValues.put(DBHelper.MY_ERROR_QUESTION_TYPE, info.getQuestionType());
		newValues.put(DBHelper.MY_ERROR_QUESTION_ANSWER, info.getQuestionAnswer());
		newValues.put(DBHelper.MY_ERROR_QUESTION_SELECTED, info.getQuestionSelect());
		newValues.put(DBHelper.MY_ERROR_QUESTION_ISRIGHT, info.getIsRight());
		newValues.put(DBHelper.MY_ERROR_QUESTION_ANALYSIS, info.getAnalysis());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_A, info.getOptionA());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_B, info.getOptionB());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_C, info.getOptionC());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_D, info.getOptionD());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_E, info.getOptionE());
		newValues.put(DBHelper.MY_ERROR_QUESTION_OPTION_TYPE, info.getOptionType());

		return database.insert(DBHelper.TABLE_NAME_MY_ERROR_QUESTION, null,
				newValues);
	}

	/**
	 * 删除我的错题所有数据
	 * 
	 * @return
	 */
	public long deleteAllData() {
		return database.delete(DBHelper.TABLE_NAME_MY_ERROR_QUESTION, null,
				null);
	}

	/**
	 * 查询全部我的错题数据
	 * 
	 * @return
	 */
	public ErrorQuestionInfo[] queryAllData() {
		Cursor result = database.query(DBHelper.TABLE_NAME_MY_ERROR_QUESTION,
				null, null, null, null,
				null, null);
		return ConvertToQuestion(result);
	}

	/**
	 * ConvertToPeople(Cursor cursor)是私有函数， 作用是将查询结果转换为用来存储数据自定义的People类对象
	 * People类的包含四个公共属性，分别为ID、Name、Age和Height，对应数据库中的四个属性值
	 */
	private ErrorQuestionInfo[] ConvertToQuestion(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		ErrorQuestionInfo[] peoples = new ErrorQuestionInfo[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			peoples[i] = new ErrorQuestionInfo();
			peoples[i].questionId = cursor.getInt(0);
			peoples[i].questionName = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_NAME));
			peoples[i].questionType = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_TYPE));
			peoples[i].questionAnswer = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_ANSWER));
			peoples[i].questionSelect = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_SELECTED));
			peoples[i].isRight = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_ISRIGHT));
			peoples[i].Analysis = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_ANALYSIS));
			peoples[i].optionA = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_A));
			peoples[i].optionB = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_B));
			peoples[i].optionC = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_C));
			peoples[i].optionD = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_D));
			peoples[i].optionE = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_E));
			peoples[i].optionType = cursor.getString(cursor
					.getColumnIndex(DBHelper.MY_ERROR_QUESTION_OPTION_TYPE));
			cursor.moveToNext();
		}
		return peoples;
	}
	
}
