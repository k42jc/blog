package com.techeffic.blog.entity;

import com.techeffic.blog.annotation.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 多媒体消息类型 mediaId实体类
 * @author xudong_liao<br/>
 * @date 2016年1月5日<br/>
 */
public class MediaIdMessage {


	@XStreamAlias("MediaId")
	@XStreamCDATA
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
