/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.helpmessage.entity;


import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 帮助信息Entity
 * @author 刘凯
 * @version 2017-03-23
 */
public class HelpMessage extends DataEntity<HelpMessage> {
	
	private static final long serialVersionUID = 1L;
	private String question;		// 问题
	private String answer;		// 答案
	
	public HelpMessage() {
		super();
	}

	public HelpMessage(String id){
		super(id);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}