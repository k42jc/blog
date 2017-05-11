package com.techeffic.blog.dal.entity;

import com.techeffic.blog.common.annotation.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
/**
 * 微信信息回复实体类
 * @author xudong_liao<br/>
 * @date 2016年1月11日<br/>
 */
@XStreamAlias("xml")
public class OutputMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996068917448173325L;

	@XStreamAlias("ToUserName")
	@XStreamCDATA
	private String ToUserName;

	@XStreamAlias("FromUserName")
	@XStreamCDATA
	private String FromUserName;

	@XStreamAlias("CreateTime")
	private Long CreateTime;

	@XStreamAlias("MsgType")
	@XStreamCDATA
	private String MsgType = "text";

	private ImageMessage Image;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public ImageMessage getImage() {
		return Image;
	}

	public void setImage(ImageMessage image) {
		Image = image;
	}

}
