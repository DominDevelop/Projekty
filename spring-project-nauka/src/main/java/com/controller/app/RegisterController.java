package com.controller.app;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.daoclass.app.SerwisUzytkownik;
import com.domain.app.Uzytkownik;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
    @Autowired
    private SerwisUzytkownik serwis;

	@RequestMapping(method = RequestMethod.GET)
	public String viewRegistration(Model model){

		model.addAttribute("userForm", new Uzytkownik());
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") @Valid Uzytkownik user,
			BindingResult result, 
			Model model){
		
		System.out.println("dane "+ user.getUser() + " " + user.getPassword());
		
		if(result.hasErrors()){
			
			System.out.println("nie prszes³o POST" + result.getNestedPath());
			model.addAttribute("error","Nie prawid³owe dane");
			return "register";
		}else{
			
			System.out.println("Zapis do bazy");
				serwis.dodajUzytkownika(user);
			System.out.println("Po zapisie");
		}
			
		return "registerSuccess";	
	}
	
}
