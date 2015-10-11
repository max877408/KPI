package com.fantasia.bean.bus;

import java.util.List;

public class MenuBean {
	/**
	 * id
	 */
	public String id;
	
	/**
	 * 节点名
	 */
	public String text;
	
	/**
	 * 节点是否选中
	 */
	public Boolean checked = false;
	
	/**
	 * 子节点
	 */
	public List<MenuBean> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBean> children) {
		this.children = children;
	}

}
