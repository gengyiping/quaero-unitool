package com.small.qcit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.security.Principal;

/**
 * 用户信息
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User implements Principal, Serializable {

    private static final long serialVersionUID = 5114506546129512029L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 项目
     */
    private String projectname;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户状态
     */
    private int status;
    /**
     * 登录界面
     */
    private String rootPage;
    /**
     * 一级界面
     */
	private String firstPage;
	/**
	 * 二级界面
	 */
	private String secondPage;
	/**
	 * 三级级界面
	 */
	private String thirdPage;
	/**
	 * 操作
	 */
	private String actionDo;
	/**
	 * 操作是否完成
	 */
	private boolean actionfinish;
	/**
	 * 连接的Ip
	 */
	private String ip;
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return userId;
	}
	public User() {
		super();
	}
	

}
