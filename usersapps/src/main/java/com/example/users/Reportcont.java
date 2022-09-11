package com.example.users;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class Reportcont {
	@Autowired
	UserRepo ur;
	@GetMapping("/pdf")
	   public ResponseEntity<byte[]> pdf() throws FileNotFoundException, JRException {
			
		JRBeanCollectionDataSource	beanCollectionDataSource=new JRBeanCollectionDataSource(ur.findAll());
		JasperReport compile=JasperCompileManager.compileReport(new FileInputStream("C://Users//DELL//eclipse-workspace//usersapps//src//main//resources//usrepo.jrxml"));
		HashMap<String, Object> hs=new HashMap<String, Object>();
		JasperPrint jasperPrint=JasperFillManager.fillReport(compile, hs,beanCollectionDataSource);
//		JasperExportManager.exportReportToPdfFile(jasperPrint, "users.pdf");
	byte[]data=	 JasperExportManager.exportReportToPdf(jasperPrint);
	HttpHeaders headrs= new HttpHeaders();
	headrs.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=users.pdf");
		return ResponseEntity.ok().header("headers").contentType(MediaType.APPLICATION_PDF).body(data);
		}
	}


