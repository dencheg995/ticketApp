package com.ticket.app.controllers;


import com.ticket.app.exeptions.PassworNoEquals;
import com.ticket.app.exeptions.user.UserExistsException;
import com.ticket.app.form.AppUserForm;
import com.ticket.app.module.AppUser;
import com.ticket.app.module.Role;
import com.ticket.app.repository.RoleRepositories;
import com.ticket.app.security.util.WebUtils;
import com.ticket.app.service.interfaces.ClientService;
import com.ticket.app.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@Transactional
public class MainController {

	private final ClientService clientService;

	private final RoleRepositories roleRepositories;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	@Autowired
	private UsersConnectionRepository connectionRepository;

	private final EventService eventService;

	public MainController(ClientService clientService, RoleRepositories roleRepositories, EventService eventService) {
		this.clientService = clientService;
		this.roleRepositories = roleRepositories;
		this.eventService = eventService;
	}

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String welcomePage() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return "login";
		} else {
			return "redirect:/lk";
		}

	}

	@GetMapping("/role/add/for/our/app")
	public ResponseEntity addRole() {
		Role roleForAdmin = new Role();
		roleForAdmin.setRoleName("ADMIN");
		roleRepositories.saveAndFlush(roleForAdmin);
		Role roleForUser = new Role();
		roleForUser.setRoleName("USER");
		roleRepositories.saveAndFlush(roleForUser);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	@RequestMapping(value = "/lk", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("User Name: " + userName);
		UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("clientEvents", eventService.getAll());
		return "lk";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginedUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);
		}
		return "403Page";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
	public String signInPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signupPage(WebRequest request, Model model) {
		ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator,
				connectionRepository);
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
		AppUserForm myForm;
		if (connection != null) {
			myForm = new AppUserForm(connection);
		} else {
			myForm = new AppUserForm();
		}
		model.addAttribute("myForm", myForm);
		return "user-registration";
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String signupSave(WebRequest request,
							 Model model,
							 @ModelAttribute("myForm") @Validated AppUserForm appUserForm,
							 BindingResult result) {
		if (appUserForm.getPassword().equals(appUserForm.getPasswordRep())) {
			if (result.hasErrors()) {
				return "user-registration";
			}
			AppUser registered;
			try {
				if (clientService.getClientByEmail(appUserForm.getEmail()) != null & clientService.getByVkId(appUserForm.getUserName()) != null) {
					model.addAttribute("errorMessage", "Error ".concat(new UserExistsException().getMessage()));
					return "user-registration";
				} else {
					registered = clientService.addClient(appUserForm);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				model.addAttribute("errorMessage", "Error " + ex.getMessage());
				return "user-registration";
			}
			if (appUserForm.getSignInProvider() != null) {
				ProviderSignInUtils providerSignInUtils //
						= new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
				providerSignInUtils.doPostSignUp(registered.getVkId(), request);
			}
			return "redirect:/";
		}
		else {
			model.addAttribute("errorMessage", "Error ".concat(new PassworNoEquals().getMessage()));
			return "user-registration";
		}
	}
}
