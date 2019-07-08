package com.idemia.oauth.controller;

import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.idemia.oauth.collection.ClientDetailsCollection;
import com.idemia.oauth.collection.UserDataCollection;
import com.idemia.oauth.controller.exception.CustomMessageException;
import com.idemia.oauth.controller.service.AuthService;
import com.idemia.oauth.controller.service.ClientDetailService;
import com.idemia.oauth.response.RequestModel;
import com.idemia.oauth.response.ResponseModel;

@Controller
public class AuthController {
	// http://localhost:8080/oauth/authorize?response_type=code&client_id=epfo&redirect_url=http://www.google.com
	@Autowired
	private ClientDetailService clientDetailService;

	@Autowired
	private AuthService authService;

	@GetMapping(value = "/oauth/authorize")
	public ModelAndView authorize(@RequestParam(value = "response_type", required = true) String responseType,
			@RequestParam(value = "client_id", required = true) String clientId, @RequestParam(value = "redirect_url", required = true) String redirectUrl,
			HttpSession session,Model model) throws CustomMessageException {

		String url = "/login";
		if (session.isNew()) {
			// validate client id from db
			Optional<ClientDetailsCollection> optional = clientDetailService.getClient(clientId);
			if (optional.isPresent()) {
				// add request model in session
				session.setAttribute("clientId", new RequestModel(responseType, clientId, redirectUrl));
				  model.addAttribute("userCollection", new UserDataCollection());
			} else {
				session.invalidate();
				throw new CustomMessageException("Invalid client id");
			}

		} else {
			ResponseModel model1 = (ResponseModel) session.getAttribute("code");
			if (model1 != null) {
				url = "redirect:" + redirectUrl + "?code=" + model1.getCode();
			} else {
				model.addAttribute("userCollection", new UserDataCollection());
			}
		}
		return new ModelAndView(url);
	}

	@PostMapping(value = "/oauth/client")
	@ResponseBody
	public void saveClient(@RequestBody ClientDetailsCollection clientDetailsCollection) {
		clientDetailService.saveClient(clientDetailsCollection);
	}
	
	@PostMapping(value = "/oauth/login")
	public ModelAndView login(@ModelAttribute UserDataCollection userCollection, HttpSession session) throws CustomMessageException {
		RequestModel requestModel = (RequestModel) session.getAttribute("clientId");
		boolean res = authService.validateUser(userCollection,requestModel);
		ResponseModel model = new ResponseModel();
		if (res) {
			// TODO - generate token and code
			model.setUserName(userCollection.getName());
			model.setAccessToken("");
			model.setRefreshToken("");
			Random rand = new Random();
			int num = rand.nextInt(90000) + 10000;
			model.setCode(num+"");
			session.setAttribute("code", model);
		}
		String url = "redirect:" + requestModel.getRedirectUrl() + "?code=" + model.getCode();
		return new ModelAndView(url);
	}

	@PostMapping(value = "/oauth/user")
	@ResponseBody
	public void saveUser(@RequestBody UserDataCollection userCollection) {
		authService.saveUser(userCollection);
	}
}