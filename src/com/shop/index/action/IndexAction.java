package com.shop.index.action;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 登录action
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	public String execute() {
		return "index";
	}
}