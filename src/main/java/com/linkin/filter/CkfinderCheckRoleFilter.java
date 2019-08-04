package com.linkin.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import com.linkin.utils.RoleEnum;

public class CkfinderCheckRoleFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		((HttpServletRequest)request).getSession(false).setAttribute("CKFinder_UserRole", RoleEnum.ADMIN.getRoleName());
		chain.doFilter(request, response);
	}

}
