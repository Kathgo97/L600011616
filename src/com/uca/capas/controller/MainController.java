package com.uca.capas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.dao.StudentDAO;
import com.uca.capas.domain.Student;

@Controller
public class MainController {
	
	@Autowired
	private StudentDAO studentDao;
	
	//Controlador que lleva a formulario
	@RequestMapping(value= "/save", method=RequestMethod.POST)
	public ModelAndView insert() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("student",new Student());
		mav.setViewName("form");
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView initMain(){
		ModelAndView mav = new ModelAndView();
		List<Student> students = null;
		try {
		 students = studentDao.findAll();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		mav.addObject("students",students);
		mav.setViewName("main");
		return mav;
	}
		
		@RequestMapping(value="/formData")
		public ModelAndView save(@ModelAttribute Student s){
			ModelAndView mav= new ModelAndView(); 
			List<Student> students = null;
			try {
				studentDao.save(s,1);
			}catch(Exception e) {
				}
			students = studentDao.findAll(); 
			mav.addObject("students",students); 
			mav.setViewName("main");
			return mav; 
			}
		@RequestMapping("/deletestudent")
		public ModelAndView deleteStudent(@RequestParam(value="") Integer id) {
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("result");
			
			Student student = null;
			
			if(id == 0) {
				student =  new Student();
			}else {
				try {
					student = studentDao.findOne(id);
				} catch (Exception e) {
					e.printStackTrace();
					student = new Student();
				}
			}
			
			mav.addObject("student", student);
			
			try {
				mav.addObject("message", "Registro eliminado");
				studentDao.delete(student);
			} catch (Exception e) {
				mav.addObject("message", "Registro no eliminado");
			}
			
			return mav;
			}
	}

