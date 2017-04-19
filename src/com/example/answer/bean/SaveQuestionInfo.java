package com.example.answer.bean;

public class SaveQuestionInfo {
	
	private String questionId;//题目id
	private String questionType;//题目类型
	private String realAnswer;//题目答案
	private String is_correct;//是否正确
	private String score;//分值
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getRealAnswer() {
		return realAnswer;
	}

	public void setRealAnswer(String realAnswer) {
		this.realAnswer = realAnswer;
	}

	public String getIs_correct() {
		return is_correct;
	}

	public void setIs_correct(String is_correct) {
		this.is_correct = is_correct;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String toString() {
		return "{'question_id':'"+getQuestionId()+"','question_type':'"+getQuestionType()+"','realAnswer':'"+getRealAnswer()+"','is_correct':'"+getIs_correct()+"','score':'"+getScore()+"'}";
	}
	
}
