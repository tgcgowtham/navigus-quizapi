package com.navigus.quizapi.controllers;

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
import com.navigus.quizapi.models.Question;

@Controller
@RequestMapping("/student/")
public class StudentController {
	List<Course> courses=TeacherController.courses;
	
	@GetMapping("profile")
	public String courses(Model model) {
		model.addAttribute("courses", courses);
		return "profile";
	}
	
	@GetMapping("{id}/start/{qno}")
	public String showCourseForm(@PathVariable ("id") int id,@PathVariable ("qno") int qno,Model model) {
		Course course=new Course();
		for(int i=0;i<courses.size();i++) {
			if(id==courses.get(i).getCourseid()) {
				course=courses.get(i);
				break;
			}
		}
		if(course.getQuestions().size()<=qno) {
			model.addAttribute("courses", courses);
			return "profile";
		}
		
		model.addAttribute("qno", qno);
		model.addAttribute("id", id);
		model.addAttribute("question",course.getQuestions().get(qno));
		model.addAttribute("time",course.getQuestions().get(qno).getTime());
		System.out.println(course.getQuestions().get(qno).getTime());
		return "showques";
	}
	
	@PostMapping("/{id}/update/{qno}")
	public String addCourse(@PathVariable ("id") int id,@PathVariable ("qno") int qno,@Valid Question question, BindingResult result, Model model) {
		Course course=new Course();
		Question q=new Question();
		int pos=0;
		for(int i=0;i<courses.size();i++) {
			if(id==courses.get(i).getCourseid()) {
				course=courses.get(i);
				pos=i;
				break;
			}
		}
		q=course.getQuestions().get(qno);
		model.addAttribute("qno", qno);
		model.addAttribute("id", id);
		model.addAttribute("question",course.getQuestions().get(qno));
		model.addAttribute("time",course.getQuestions().get(qno).getTime());
		//System.out.println(q.getAnswer()+" "+question.getUanswer());
		if(q.getAnswer().equals(question.getUanswer())) {
			if(q.getDone()==0) {
				course.setScore(course.getScore()+q.getMarks());
				q.setDone(1);
			}
			TeacherController.courses.set(pos, course);
			model.addAttribute("course",course );
			if(course.getScore()>=course.getPass()) return "corrpass";
			else return "corrfail";
		}
		else {
			model.addAttribute("course",course );
			if(course.getScore()>=course.getPass())return "wrongpass";
			else return "wrongfail";
		}
	}
	
	
	
	
	
}
