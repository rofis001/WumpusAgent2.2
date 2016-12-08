package de.fh;

import java.util.ArrayList;
import java.util.List;

import de.fh.connection.Vector2;

public class Karte {

	private List<List<Feld>> map = new ArrayList<List<Feld>>();
	
	
	public void Karte(){
		
		initKarte();
	}
	
	public void initKarte(){
		
		for (int i=0; i < 3; i++){
			map.add(new ArrayList<Feld>());
			for (int j = 0; j < 3; j++){
				map.get(i).add(new Feld(new Vector2(j+1, i+1)));
			}
		}
	}
	
	//TODO: Beim Laufen Felder hinzufügen.
}
