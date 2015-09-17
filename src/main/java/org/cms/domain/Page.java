package org.cms.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jvwong
 */
@Entity
@Table(name = "Page", uniqueConstraints = {})
@AttributeOverride(name = "id", column = @Column(name = "PageId"))
public class Page extends DateByAuditedEntity {
	private String content;
	
	public Page(){
		this(null);
	}
	
	public Page(String content){
		this.content = content;
	}
	
	public String getContent() { 
		return content; 
	}

	public void setContent(String content) { 
		this.content = content; 
	}
}
