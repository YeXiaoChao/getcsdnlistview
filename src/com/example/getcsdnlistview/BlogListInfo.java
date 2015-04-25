package com.example.getcsdnlistview;

public class BlogListInfo {
	private String  blogTitle;//博文标题
	private String  blogUrl;//博文地址
	private String  blogSummary;//博文摘要
	private String  blogTime;//博文发布时间
	private String  blogReply;//博文回复量
	private String  blogReadNum;//博文浏览量

	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getBlogUrl() {
		return blogUrl;
	}
	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
	public String getBlogSummary() {
		return blogSummary;
	}
	public void setBlogSummary(String blogSummary) {
		this.blogSummary = blogSummary;
	}
	public String getBlogTime() {
		return blogTime;
	}
	public void setBlogTime(String blogTime) {
		this.blogTime = blogTime;
	}
	public String getBlogReply() {
		return blogReply;
	}
	public void setBlogReply(String blogReply) {
		this.blogReply = blogReply;
	}
	public String getBlogReadNum() {
		return blogReadNum;
	}
	public void setBlogReadNum(String blogReadNum) {
		this.blogReadNum = blogReadNum;
	}
}
