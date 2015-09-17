package org.cms.web;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cms.domain.Article;
import org.cms.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Jeffrey V Wong
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	/**
	 * @param file multipart file
	 * @return logical view name
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String createArticle(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "redirect:/article/articles";
		}
		
		// Article article = articleConverter.convert(file);
		Article article = new Article();
		log.debug("Creating article: {}", article);
		
		// articleDao.createOrUpdate(article);
		return "redirect:/article/articles";
	}
	
	/**
	 * @param model model
	 * @return article list
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getArticleList(Model model) {
		log.debug("Getting article list");
		//model.addAttribute("articleList", articleDao.getAll());
		
		Page p1 = new Page("Content for Page 1");
		Page p2 = new Page("Content for Page 2");
		Page p3 = new Page("Content for Page 3");
		Page p4 = new Page("Content for Page 4");
		List<Page> pages1 = new ArrayList<Page>(Arrays.asList(p1,p2));
		List<Page> pages2 = new ArrayList<Page>(Arrays.asList(p3,p4));
		Article a1 = new Article("Article1", "Description 1", "Keywords 1", pages1);
		Article a2 = new Article("Article2", "Description 2", "Keywords 2", pages2);
		List<Article> articles = new ArrayList<Article>(Arrays.asList(a1,a2));
		
		model.addAttribute("articleList", articles);
		
		return getFullViewName("articleList");
	}
	
	/**
	 * @param id article ID
	 * @param pageNumber page number
	 * @param req request
	 * @param res response
	 * @return logical view name
	 * @throws IOException if there's an I/O exception
	 */
//	@RequestMapping(value = "/{id}/{page}", method = RequestMethod.GET)
//	public String getArticlePage(@PathVariable String id, @PathVariable("page") Integer pageNumber, Model model) {
//		log.debug("Serving {}, page {}", id, pageNumber);
//		Article article = articleDao.getPage(id, pageNumber);
//		Page page = article.getPages().get(pageNumber - 1);
//		model.addAttribute(article);
//		model.addAttribute("articlePage", page);
//		model.addAttribute("pageNumber", pageNumber);
//		return getFullViewName("articlePage");
//	}
	
	private String getFullViewName(String viewName) {
		return "article/" + viewName;
	}
}
