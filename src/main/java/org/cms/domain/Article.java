package org.cms.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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
	//private List<Page> pages = new ArrayList<Page>();
	
	public Article(){
		this(null, null, null);
	}
	
	public Article(String title, String description, String keywords){
		this.title = title;
		this.description = description;
		this.keywords = keywords;
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
		
	
	@Override
	public String toString() {
		return "[Article: id=" + this.getId()
			+ ", title=" + title
			+ ", author=" + this.getCreatedBy()
			+ ", createdDate=" + this.getCreatedDate()
			+ ", description=" + description
			+ ", keywords=" + keywords
			+ "]";
	}
}
