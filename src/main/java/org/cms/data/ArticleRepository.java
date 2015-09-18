package org.cms.data;

import org.cms.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, 
										   ArticleRepositoryCustomization{	
}