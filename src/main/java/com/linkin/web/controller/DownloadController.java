package com.linkin.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.utils.FileStore;

@Controller
public class DownloadController {
	@RequestMapping(value = "/user/file/{file:.+}")
	public void download(@PathVariable(value = "file") String fileName, HttpServletResponse response) {
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));

			FileInputStream fileInputStream = new FileInputStream(fileName);
			IOUtils.copy(fileInputStream, response.getOutputStream());
		} catch (IOException e) {
		}
	}
	@RequestMapping(value = "/survey-history/file/{file:.+}")
	public void downloadSurveyHistory(@PathVariable(value = "file") String fileName, HttpServletResponse response) {
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));

			FileInputStream fileInputStream = new FileInputStream(fileName);
			IOUtils.copy(fileInputStream, response.getOutputStream());
		} catch (IOException e) {
		}
	}
	@RequestMapping(value = "/display-content/file/{file:.+}")
	public void downloadDisplayContentHistory(@PathVariable(value = "file") String fileName, HttpServletResponse response) {
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));

			FileInputStream fileInputStream = new FileInputStream(fileName);
			IOUtils.copy(fileInputStream, response.getOutputStream());
		} catch (IOException e) {
		}
	}
	@RequestMapping(value = "/image/{file:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] download(@PathVariable(value = "file") String fileName) {
		String filePath = FileStore.UPLOAD_FOLDER + File.separator + fileName;
		File file = new File(filePath);
		try {
			return IOUtils.toByteArray(new FileInputStream(file));
		} catch (IOException e) {

		}
		return new byte[0];
	}
}
