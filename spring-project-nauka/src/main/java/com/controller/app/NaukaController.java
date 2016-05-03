package com.controller.app;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daoclass.app.SerwisLista;
import com.daoclass.app.SerwisUzytkownik;
import com.daoclass.app.SerwisZestawSlow;
import com.domain.app.Lista;
import com.domain.app.Uzytkownik;
import com.domain.app.ZestawSlow;
import com.daoclass.helpclass.app.*;

@Controller
public class NaukaController {

	@Autowired
	private SerwisLista serwisLista;
	@Autowired
	private SerwisZestawSlow serwisSlow;
	@Autowired
	private SerwisUzytkownik serwisUzytkownik;
	private int i = 0;
	private List<ZestawSlow>lista;
	private Uzytkownik user;
	
	@RequestMapping(value = "/start/nauka",method = RequestMethod.GET)
	public String startNauka(Model model,Principal principal){
		
		System.out.println("start");
		user = serwisUzytkownik.getUser(principal.getName());
		lista = serwisSlow.getUserSlownik(user.getId());
		model.addAttribute("slowo",lista.get(i).getPl());
		return "nauka";
	}
	
	@RequestMapping(value = "/start/naukaspr", method = RequestMethod.POST)
	public String sprawdzSlowo(Principal principal, Model model, HttpServletRequest  request){
		
		String[] txt = request.getParameterValues("submit");
		if(txt[0].equals("sprawdz")){
			
			String[] lis = request.getParameterValues("odp");
			String odp = lis[0];
			System.out.println("sprawdz " + odp);
			String o = odp.replaceAll("\\s+","");
			if(lista.get(i).getEn().equalsIgnoreCase(o)){
				
				model.addAttribute("dobrze","dobra odpowiedü " + lista.get(i).getEn() + " = " + o);
				serwisLista.updateSlowo(lista.get(i).getIdSlowa(),user.getId(),lista.get(i).getLiczbaPoprawnych()+1);
			}else{
				model.addAttribute("info","Niepoprawna odpowiedü");
				model.addAttribute("dobrze","poprawnie " + lista.get(i).getEn() );
				model.addAttribute("zle", "twoja odpowiedü " + o);
			}
		}else{
			System.out.println("nastepne");
			i++;
			if(i == lista.size()){
				i = 0;
			}
			model.addAttribute("slowo",lista.get(i).getPl());
		}

		return "nauka";
	}
	
	
	
}
