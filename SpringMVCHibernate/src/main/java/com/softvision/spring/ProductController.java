package com.softvision.spring;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.softvision.mail.Mail;
import com.softvision.spring.dao.ProductDAOImpl;
import com.softvision.spring.model.ProductBo;

@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private Set<ProductBo> buyNowList = new HashSet();

	@Autowired
	private ProductDAOImpl productDAO;

	@RequestMapping("/")
	public String viewProduct1() {
		return "test";
	}

	@RequestMapping("/viewProduct")
	public ModelAndView viewProduct() {
		List<ProductBo> list = productDAO.getProduct();
		logger.info("view product successfull");

		return new ModelAndView("viewProduct", "list", list);
	}

	@RequestMapping(value = "/cart/{productId}/{quantity}/", method = RequestMethod.POST)
	public ModelAndView cart(@PathVariable String productId, Integer quantity) {
		System.out.println("-------------------------------------------" + quantity);
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), quantity, productBo.getImage());
		logger.info("productList");

		buyNowList.add(productList);
		return new ModelAndView("redirect:/viewProduct");
	}

	@RequestMapping(value = "/Men")
	public ModelAndView men() {
		List<ProductBo> product = productDAO.getProductByCategory("men");
		logger.info("productList men " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductMen", product);
		mv.setViewName("Men");
		return mv;
	}

	@RequestMapping(value = "/Women")
	public ModelAndView women() {
		List<ProductBo> product = productDAO.getProductByCategory("women");
		logger.info("productList women " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductWomen", product);
		mv.setViewName("Women");
		return mv;
	}

	@RequestMapping(value = "/Kid")
	public ModelAndView kid() {

		List<ProductBo> product = productDAO.getProductByCategory("Kids");
		logger.info("productList kid " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductKid", product);
		mv.setViewName("Kid");
		return mv;
	}

	@RequestMapping(value = "/buyNow/{productId}/{quantity}/", method = RequestMethod.POST)
	public ModelAndView buyNow(@PathVariable String productId, Integer quantity) {
		System.out.println("-------------------------------------------" + quantity);
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), quantity, productBo.getImage());
		logger.info("productList of buyNow " + productList);
		buyNowList.add(productList);
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyNowList", buyNowList);
		mv.setViewName("buyNow");
		return mv;
	}

	@RequestMapping("/buyNowOrder")
	public ModelAndView buyNowOrder() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyNowList", buyNowList);
		mv.setViewName("buyNowOrder");
		return mv;
	}

	@RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
	public String confirmOrder(HttpSession session) {

		if (session.getAttribute("username") != null) {
			buyNowList.clear();
			logger.info("order confirmed");
			String recipient = (String) session.getAttribute("emailId");
			logger.info("dynamic email id received" + recipient);
			Mail mail = new Mail();
			mail.sendMail("yes", session);
			return ("confirmOrder");
		} else {
			return ("Login");
		}
	}

	@RequestMapping(value = "/remove/{productId}/", method = RequestMethod.POST)
	public ModelAndView remove(@PathVariable String productId) {
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), productBo.getQuantity(), productBo.getImage());
		logger.info("product list" + productList);
		buyNowList.remove(productList);
		return new ModelAndView("redirect:/buyNowOrder");
	}
}