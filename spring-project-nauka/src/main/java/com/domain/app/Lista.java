package com.domain.app;

public class Lista {

	private int id;
	private int id_uzytkownik;
	private int id_slowa;
	private int liczba_poprawnych;
	private boolean nauczone;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_uzytkownik() {
		return id_uzytkownik;
	}
	public void setId_uzytkownik(int id_uzytkownik) {
		this.id_uzytkownik = id_uzytkownik;
	}
	public int getId_slowa() {
		return id_slowa;
	}
	public void setId_slowa(int id_slowa) {
		this.id_slowa = id_slowa;
	}
	public int getLiczba_poprawnych() {
		return liczba_poprawnych;
	}
	public void setLiczba_poprawnych(int liczba_poprawnych) {
		this.liczba_poprawnych = liczba_poprawnych;
	}
	public boolean isNauczone() {
		return nauczone;
	}
	public void setNauczone(boolean nauczone) {
		this.nauczone = nauczone;
	}
}
