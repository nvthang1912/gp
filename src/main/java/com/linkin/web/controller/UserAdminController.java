package com.linkin.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchUserDTO;
import com.linkin.model.UserDTO;
import com.linkin.service.UserService;
import com.linkin.utils.RoleEnum;

@Controller
@RequestMapping(value = "/admin")
public class UserAdminController extends BaseWebController{

	@Autowired
	private UserService userService;

	@GetMapping("/accounts")
	public String listUser() {
		return "admin/userAccount/listUser";
	}

	@PostMapping(value = "/accounts")
	public ResponseEntity<ResponseDTO<UserDTO>> finds(@RequestBody SearchUserDTO searchUserDTO) {
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.findUsers(searchUserDTO));
		responseDTO.setRecordsTotal(userService.countTotalUsers(searchUserDTO));
		responseDTO.setRecordsFiltered(userService.countUsers(searchUserDTO));
		return new ResponseEntity<ResponseDTO<UserDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/account/add")
	public String addUser(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userAccountDTO", userDTO);
		return "admin/userAccount/addUser";
	}

	@PostMapping("/account/add")
	public String addUser(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult,
			Model model) {
		validateAddUser(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/addUser";
		}
		try {
			userDTO.setEnabled(true);
			userDTO.setRoleId((long) RoleEnum.ADMIN.getRoleId());
			userService.addUser(userDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("phone", "error.msg.existed.account.phone");
			return "admin/userAccount/addUser";
		}
		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/update/{id}")
	public String updateUser(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userAccountDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userAccountDTO);

		return "admin/userAccount/updateUser";
	}

	@PostMapping("/account/update")
	public String updateUser(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult) {
		this.validateUpdateUser(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/updateUser";
		}
		try {
			// save database
			userService.updateUser(userDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("phone", "error.msg.existed.account.phone");
			return "admin/userAccount/updateUser";
		}

		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/change-lock/{id}")
	public ResponseEntity<String> changeLockedUserStatus(@PathVariable(name = "id") Long id) {
		userService.changeAccountLock(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/delete-multi/{ids}")
	public ResponseEntity<String> deleteMultiUser(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				userService.deleteUser(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/reset-password/{id}")
	public String resetPassword(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userDTO);
		return "admin/userAccount/resetPassword";
	}

	@PostMapping("/account/reset-password")
	public String resetPassword(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult) {
		validateUserPassword(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/resetPassword";
		}
		userService.setupUserPassword(userDTO);

		return "redirect:/admin/accounts";
	}
	
	private void validateAddUser(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.msg.empty.account.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
		if (!accountDTO.getPhone().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("phone", "error.msg.invalid.phone");
		}
		if (accountDTO.getPassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getPassword())) {
			errors.rejectValue("password", "error.msg.empty.account.password");
		}
	}

	private void validateUpdateUser(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.msg.empty.account.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
		if (!accountDTO.getPhone().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("phone", "error.msg.invalid.phone");
		}
	}

	private void validateUserPassword(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
		if (accountDTO.getPassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getPassword())) {
			errors.rejectValue("password", "error.msg.empty.account.password");
		}
	}

}
