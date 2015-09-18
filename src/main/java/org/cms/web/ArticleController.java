package org.cms.web;

import java.io.IOException;

import org.cms.config.annotation.WebController;
import org.cms.data.ArticleRepository;
import org.cms.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Jeffrey V Wong
 */
@WebController
@RequestMapping(value = "/articles")
public class ArticleController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private ArticleRepository articleRepository;

	@Autowired
	public ArticleController(ArticleRepository articleRepository) {
	    this.articleRepository = articleRepository;
	}
	
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
		logger.debug("Creating article: {}", article);
		
		// articleDao.createOrUpdate(article);
		return "redirect:/article/articleList";
	}
	
	/**
	 * @param model model
	 * @return article list
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getArticleList(Model model) {
		logger.debug("Getting article list");
		
		model.addAttribute("articleList", articleRepository.findAll());		
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
	
	private String getFullViewName(String path){
		return "/articles/" + path;
	}
}
