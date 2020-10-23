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
import com.navigus.quizapi.models.Question;

@Controller
@RequestMapping("/teacher/quiz")
public class QuizController {
	List<Question> questions=new ArrayList<>();
	int l=1;
	@GetMapping("/{id}/showForm")
	public String showquesForm(@PathVariable ("id") int id,Model model,Question question) {
		model.addAttribute("id", id);
		return "add-quiz";
	}
	@GetMapping("/{id}/showall")
	public String questions(@PathVariable ("id") int id, Model model) {
		for(int i=0;i<TeacherController.courses.size();i++) {
			Course c=TeacherController.courses.get(i);
			if(c.getCourseid()==id) {
				questions=c.getQuestions();
				break;
			}
		}
		model.addAttribute("questions", questions);
		model.addAttribute("id", id);
		return "show-quiz";
	}
	
	@PostMapping("/{id}/add")
	public String addQues(@PathVariable ("id") int id,@Valid Question question, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-quiz";
		}
		for(int i=0;i<TeacherController.courses.size();i++) {
			Course c=TeacherController.courses.get(i);
			if(c.getCourseid()==id) {
				questions=c.getQuestions();
				question.setId(l++);
				questions.add(question);
				c.setQuestions(questions);
				c.setNoc(c.getNoc()+1);
				break;
			}
		}
		model.addAttribute("questions", questions);
		return "redirect:showall";
	}
	
	@GetMapping("/{id}/edit/{qid}")
	public String showUpdateForm(@PathVariable ("id") int id,@PathVariable ("qid") int qid, Model model) {
		
		Question q=new Question();
		int f=0;
		for(int i=0;i<TeacherController.courses.size();i++) {
			Course c=TeacherController.courses.get(i);
			if(c.getCourseid()==id) {
				questions=c.getQuestions();
				for(int j=0;j<c.getQuestions().size();j++) {
					q=questions.get(j);
					if(q.getId()==qid) {
						f=1;
						break;
					}
				}
				break;
			}
		}
		if(f==0) throw new IllegalArgumentException("Invalid student id : " + id);		
		model.addAttribute("question", q);
		model.addAttribute("id", id);
		model.addAttribute("qid", qid);
		return "update-quiz";
	}
	
	@PostMapping("/{id}/update/{qid}")
	public String updateCourse(@PathVariable("id") int id,@PathVariable("qid") int qid, @Valid Question question, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "update-quiz";
		}
		Question q=new Question();
		for(int i=0;i<TeacherController.courses.size();i++) {
			Course c=TeacherController.courses.get(i);
			if(c.getCourseid()==id) {
				questions=c.getQuestions();
				for(int j=0;j<c.getQuestions().size();j++) {
					q=questions.get(j);
					if(q.getId()==qid) {
						question.setId(qid);
						questions.set(j,question);
						c.setQuestions(questions);
						break;
					}
				}
			    TeacherController.courses.set(i, c);
				break;
			}
		}
		// get all students ( with update)
		model.addAttribute("questions", questions);
		return "show-quiz";
	}
	
	@GetMapping("/{id}/delete/{qid}")
	public String deleteCourse(@PathVariable ("id") int id,@PathVariable("qid") int qid, Model model) {
		int f=0;
		Question q=new Question();
		for(int i=0;i<TeacherController.courses.size();i++) {
			Course c=TeacherController.courses.get(i);
			if(c.getCourseid()==id) {
				questions=c.getQuestions();
				for(int j=0;j<c.getQuestions().size();j++) {
					q=questions.get(j);
					if(q.getId()==qid) {
						f=1;
						questions.remove(q);
						c.setNoc(c.getNoc()-1);
						c.setQuestions(questions);
						break;
					}
				}
				break;
			}
		}
		
		if(f==0) throw new IllegalArgumentException("Invalid student id : " + id);	
		model.addAttribute("questions", questions);
		return "show-quiz";
	}
	
	
	
	
	
	
	

}
