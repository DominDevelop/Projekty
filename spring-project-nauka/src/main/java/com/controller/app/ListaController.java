package com.controller.app;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daoclass.app.SerwisLista;
import com.daoclass.app.SerwisSlownik;
import com.daoclass.app.SerwisUzytkownik;
import com.daoclass.app.SerwisZestaw;
import com.daoclass.app.SerwisZestawSlow;
import com.domain.app.Lista;
import com.domain.app.Slownik;
import com.domain.app.Uzytkownik;
import com.domain.app.ZestawSlow;

@Controller
public class ListaController {
	
	@Autowired
	private SerwisUzytkownik uzytkownikSerwis;
	@Autowired
	private SerwisZestawSlow zestawSlowSeriws;
	
	@RequestMapping(value = "/start/listaSlow",method = RequestMethod.GET)
	public String getWidokLista(Principal principal, Model model){
		
		System.out.println("ok weszlo");
		Uzytkownik user = uzytkownikSerwis.getUser(principal.getName());
		List<ZestawSlow> lista = zestawSlowSeriws.getUserSlownik(user.getId());
		
		for(ZestawSlow z : lista){
			System.out.println("pl: " + z.getPl());
		}
		
		model.addAttribute("lista",lista);
		System.out.println("po metodzie");
		return "listaSlow";
	}
}
