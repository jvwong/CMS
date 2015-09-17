package org.cms.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cms.domain.Article;
import org.cms.domain.Page;

public class ArticleForm {
	
	private String title;
	private String author;
	private Date publishDate;
	private String description;
	private String keywords;
	private List<Page> pages = new ArrayList<Page>();
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
  
	public Article toArticle() {
		Article article = new Article();
		return article;
	}
}
