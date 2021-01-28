package com.small.qcit.domain.dto;

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
	private String rootPage;//登录界面
	private String firstPage;//一级界面
	private String secondPage;//二级界面
	private String thirdPage;//三级级界面
	public LoginState() {
		super();
	}
	
}
