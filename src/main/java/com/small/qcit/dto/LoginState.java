package com.small.qcit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginState {

	private String userName;//人员
	private String ip;//ip地址
	private String[] loginInterface;//登录界面
	public LoginState() {
		super();
	}
	
}
