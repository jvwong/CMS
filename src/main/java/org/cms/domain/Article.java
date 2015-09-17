package org.cms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jvwong
 */
@XmlRootElement(name = "article")
@Entity
@Table(name = "Article", uniqueConstraints = {})
@AttributeOverride(name = "id", column = @Column(name = "ArticleId"))
public class Article extends DateByAuditedEntity {
	
	private String title;
	private String description;
	private String keywords;
	private List<Page> pages = new ArrayList<Page>();
	
	public Article(){
		this(null, null, null, null);
	}
	
	public Article(String title, String description, String keywords, List<Page> pages){
		this.title = title;
		this.description = description;
		this.keywords = keywords;
		this.pages = pages;
	}
	
	public String getTitle() { 
		return title; 
	}

	public void setTitle(String title) { 
		this.title = title; 
	}
		
	public String getDescription() { 
		return description; 
	}
	
	public void setDescription(String description) { 
		this.description = description; 
	}
	
	public String getKeywords() { 
		return keywords; 
	}
	
	public void setKeywords(String keywords) { 
		this.keywords = keywords; 
	}
	
	@XmlTransient
	public List<Page> getPages() { 
		return pages; 
	}
	
	public void setPages(List<Page> pages) { 
		this.pages = pages; 
	}
	
	@Override
	public String toString() {
		return "[Article: id=" + this.getId()
			+ ", title=" + title
			+ ", author=" + this.getCreatedBy()
			+ ", createdDate=" + this.getCreatedDate()
			+ ", description=" + description
			+ ", keywords=" + keywords
			+ ", numPages=" + (pages == null ? 0 : pages.size())
			+ "]";
	}
}
