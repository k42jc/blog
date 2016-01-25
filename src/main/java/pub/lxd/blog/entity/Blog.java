package pub.lxd.blog.entity;

import java.util.Date;

public class Blog {
    private Long id;

    private String title;

    private Long typeId;

    private Long userId;

    private String keyword;

    private Integer viewNums;

    private Date createDate;

    private Date updateDate;

    private String contentView;

    private String content;
    
    private SysData blogType;
    
    private User user;
    
    public SysData getBlogType() {
		return blogType;
	}

	public void setBlogType(SysData blogType) {
		this.blogType = blogType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long typeId) {
        this.typeId = typeId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getViewNums() {
        return viewNums;
    }

    public void setViewNums(Integer viewNums) {
        this.viewNums = viewNums;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getContentView() {
        return contentView;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView == null ? null : contentView.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}