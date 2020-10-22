package com.navigus.quizapi.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.navigus.quizapi.models.Course;


@Controller
@RequestMapping("/teacher/")
public class TeacherController {
	public static List<Course> courses=new ArrayList<>();
	int l=1;
	@GetMapping("showForm")
	public String showCourseForm(Course course) {
		return "add-course";
	}
	
	@GetMapping("showall")
	public String courses(Model model) {
		model.addAttribute("courses", courses);
		return "show-all";
	}
	
	@PostMapping("add")
	public String addCourse(@Valid Course course, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-course";
		}
		course.setCourseid(l++);
		courses.add(course);
		model.addAttribute("courses", courses);
		return "redirect:showall";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") int id, Model model) {
		
		Course course=new Course();
		int f=0;
		for(int i=0;i<courses.size();i++) {
			Course c=courses.get(i);
			if(c.getCourseid()==id) {
				course=c;
				f=1;
				break;
			}
		}
		if(f==0) throw new IllegalArgumentException("Invalid student id : " + id);		
		model.addAttribute("course", course);
		return "update-course";
	}
	
	@PostMapping("update/{id}")
	public String updateCourse(@PathVariable("id") int id, @Valid Course course, BindingResult result, Model model) {
		if(result.hasErrors()) {
			course.setCourseid(id);
			return "update-course";
		}
		for(int i=0;i<courses.size();i++) {
			Course c=courses.get(i);
			if(c.getCourseid()==id) {
				c.setCourseName(course.getCourseName());
				c.setPass(course.getPass());
				courses.set(i, c);
				break;
			}
		}
		// get all students ( with update)
		model.addAttribute("courses", courses);
		return "show-all";
	}
	
	@GetMapping("delete/{id}")
	public String deleteCourse(@PathVariable ("id") int id, Model model) {
		Course course=new Course();
		int f=0;
		for(int i=0;i<courses.size();i++) {
			Course c=courses.get(i);
			if(c.getCourseid()==id) {
				course=c;
				f=1;
				break;
			}
		}
		if(f==0) throw new IllegalArgumentException("Invalid student id : " + id);	
		
		courses.remove(course);
		model.addAttribute("courses", courses);
		return "show-all";
	}
}
