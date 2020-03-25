package it.polito.tdp.libretto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author aless memorizza e gestisce un insieme di voti superati
 * 
 */

public class Libretto {

	private List<Voto> voti = new ArrayList<>();

	public Libretto() {
		
	}
	/**
	 * copy constructor
	 * "Shallow" copia superficiale
	 * @param lib
	 */
	public Libretto(Libretto lib) {
		this.voti.addAll(lib.voti);

	}

	/**
	 * aggiunge un nuovo voto al libretto
	 * 
	 * @param v voto da aggiungere
	 * @return {@code true} se ha inserito elemento di voto, {@code false} se non ha inserito per conflitto o duplicato
	 */
	public boolean add(Voto v) {
		if(this.isConflitto(v)|| this.isDuplicato(v)) {
			return false; //segnalo al chiamante che nonn ha avuto successo
		} else { //inserisci il voto, è ok
		this.voti.add(v);
		return true;
		}
		/*
		 * meglio questo, se dovessi modificare la classe voto aggiungendo dei campi
		 * dovrei apportare eccessive modifiche al programma. creeremmo la dipendenza
		 * della classe libretto dalla classe voto. meno dipendenze = meno lavoro
		 */
	}

	/*
	 * public void add(String corso, int voto, LocalDate data) {
	 * 
	 * }
	 */

	public String toString() {
		String s = "";
		for (Voto v : this.voti) {
			s += v.toString() + "\n";
		}
		return s;
	}

	/**
	 * Dato un libretto restituisce una stringa contenente solo gli esami il cui
	 * voto è uguale al parametro passato
	 * 
	 * @param voto voto cercato
	 * @return stringa formattata
	 */

	public String stampaVotiUguali(int voto) {
		String s = "";
		for (Voto v : this.voti) {
			if (v.getVoto() == voto)
				s += v.toString() + "\n";
		}
		return s;
	}

	/**
	 * Genera un nuovo libretto a partire da quello esistente che conterrà
	 * esclusivamente i voti con valutazione pari a quella specificata
	 * 
	 * @param voto votazione specificata
	 * @return nuovo libretto "ridotto"
	 */

	public Libretto estraiVotiUguali(int voto) {
		Libretto nuovo = new Libretto();
		for (Voto v : this.voti) {
			if (v.getVoto() == voto) {
				nuovo.add(v);
			}
		}
		return nuovo;
	}

	/**
	 * Dato il nome di un corso cerca se esame esiste nel libretto e in caso
	 * affermativo restituisce l'oggetto {@link Voto} corrispondente Se l'esame non
	 * esiste restituisce {@code null}
	 * 
	 * @param nomeCorso nome esame da cercare
	 * @return voto corrispondente oppure null, se non esiste
	 */
	public Voto cercaNomeCorso(String nomeCorso) {
		/*
		 * for (Voto v : this.voti) { if (nomeCorso.equals(v.getCorso())) { return v; }
		 * }
		 * 
		 * return null;
		 */
		int pos = this.voti.indexOf(new Voto(nomeCorso, 0, null));
		if (pos != -1) {
			return this.voti.get(pos);
		} else
			return null;
	}

	/**
	 * Ritorna {@code true} se il corso specificato da {@code v} esiste nel
	 * libretto, con la stessa valutazione Se non esiste, o se la valutazione è
	 * diversa, ritorna {@code false}
	 * 
	 * @param v il {@link Voto} da cercare
	 * @return l'esistenza di un duplicato
	 */
	public boolean isDuplicato(Voto v) {
		Voto esiste = this.cercaNomeCorso(v.getCorso());
		if (esiste == null) // non l'ho trovato => non è duplicato
			return false;
		/*
		 * if (esiste.getVoto() == v.getVoto()) return true; else return false;
		 */

		return (esiste.getVoto() == v.getVoto());
	}

	
/**
 * Determina se esiste un voto con lo stesso nome corso ma con valutazione diversa
 * @param v
 * @return
 */

	public boolean isConflitto(Voto v) {
		Voto esiste = this.cercaNomeCorso(v.getCorso());
		if (esiste == null) // non l'ho trovato => non è duplicato
			return false;
		return (esiste.getVoto() != v.getVoto());
	}
	/**
	 * Restituisce un nuovo libretto, migliorando i voti del libretto attuale
	 * 
	 * @return
	 */
	public Libretto creaLibrettoMigliorato() {
		Libretto nuovo = new Libretto();
		
		for(Voto v : this.voti) {
			Voto v2 = new Voto(v); 
			// Voto v2 = v.clone();
			// NON CI PIACE Voto v2 = new Voto(v.getCorso(), v.getVoto(), v.getData());
			
			if(v2.getVoto()>= 24) {
				v2.setVoto(v2.getVoto()+2);
				if(v2.getVoto()>30)
					v2.setVoto(30);
			} else if (v2.getVoto()>=18) {
				v2.setVoto(v2.getVoto()+1);
			}
			
			nuovo.add(v2);
		}
		return nuovo;
	}
	/**
	 * Riordina i voti presenti nel libretto corrente
	 * alfabeticamente per corso
	 */
	public void ordinaPerCorso() {
		Collections.sort(this.voti);
	}
	
	public void ordinaPerVoto() {
		Collections.sort(this.voti, new ConfrontaVotiPerValutazione());
		//this.voti.sort(new ConfrontaVotiPerValutazione());
	}
	/**
	 * cancella dal libretto tutti i voti inferiori a 24
	 */
	public void CancellaVotiScarsi() {
		List<Voto> daRimuovere= new ArrayList<Voto>();
		for(Voto v : this.voti) {
			if(v.getVoto()<24) {
				daRimuovere.add(v);
			}
		}
		this.voti.removeAll(daRimuovere);
//		for(Voto v : daRimuovere) {
//			this.voti.remove(v);
//		}
	}
}
