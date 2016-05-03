package com.controller.app;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.daoclass.app.SerwisLista;
import com.daoclass.app.SerwisPlan;
import com.daoclass.app.SerwisSlownik;
import com.daoclass.app.SerwisUzytkownik;
import com.daoclass.app.SerwisZestaw;
import com.domain.app.Slownik;
import com.domain.app.Uzytkownik;
import com.domain.app.Zestaw;

@Controller
public class ZestawController {

	@Autowired
	private SerwisZestaw zestawSerwis;
	@Autowired
	private SerwisPlan planSerwis;
	@Autowired
	private SerwisUzytkownik uzytkownikSerwis;
	@Autowired
	private SerwisLista listaSerwis;
	@Autowired
	private SerwisSlownik slownikSerwis;

	@RequestMapping(value = "/start/zestawy",method = RequestMethod.GET)
	public String zestawy(Model model){
		
		List<Zestaw> list = zestawSerwis.getZestawlist();
		for(Zestaw z : list){
			System.out.println("nazwa zestawu " + z.getNazwa());
		}	
		model.addAttribute("lista",list);
		return "zestawy";
	}
	
	@RequestMapping(value = "/start/zestaw", method = RequestMethod.GET)
	public String dodajZestaw(@RequestParam(value="id") String id,HttpServletRequest request, Principal principal,Model model) {
		
		System.out.println("przechwycono : " + id + " dla uzytkownika: " + principal.getName());
		Uzytkownik user = uzytkownikSerwis.getUser(principal.getName());
		System.out.println("znaleziono uzytkownika" + user.getUser());
		
		model.addAttribute("lista",zestawSerwis.getZestawlist());
		
		if(planSerwis.addZestaw(user.getId(), Integer.valueOf(id))){
			System.out.println("dodano zestaw " + Integer.valueOf(id));
			listaSerwis.organizujPlan(slownikSerwis.getSlownik(Integer.valueOf(id)),user.getId());
		}else{
			System.out.println("zestaw juz istnieje");
			model.addAttribute("error","Masz juz ten zestaw przypisany do konta");
			model.addAttribute("lista",zestawSerwis.getZestawlist());
		}
		
		return "zestawy";
	}
	
	@RequestMapping(value = "/start/zestawid", method = RequestMethod.GET)
	public String pokazListe(@RequestParam(value="id") String idZestaw,HttpServletRequest request, Principal principal,Model model) {
		
		List<Slownik> list = slownikSerwis.getSlownik(Integer.valueOf(idZestaw));
		model.addAttribute("zestaw",list);
		System.out.println(idZestaw);
		
		for(Slownik z : list){
			System.out.println("nazwa zestawu " + z.getEn());
		}
		
		return "zestawy";
	}
	
	@RequestMapping(value = "/start/delete", method = RequestMethod.GET)
	public String deleteZestaw(@RequestParam(value="id") String idZestaw,HttpServletRequest request, Principal principal,Model model) {
		

		Uzytkownik user = uzytkownikSerwis.getUser(principal.getName());
		planSerwis.delete(user.getId(),Integer.valueOf(idZestaw));
		model.addAttribute("lista",zestawSerwis.getZestawlist());
		model.addAttribute("message","usuniêto zestaw");
		
		return "zestawy";
	}
	
	
}
