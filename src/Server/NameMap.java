package Server;

import java.util.ArrayList;

//Controls the mapping of the bibs to the name
public class NameMap {
	
	ArrayList<Integer> bibList;
	ArrayList<String> nameList;
	
	public NameMap(){
		bibList = new ArrayList<Integer>();
		nameList = new ArrayList<String>();
	}

	public void clear() {
		bibList = new ArrayList<Integer>();
		nameList = new ArrayList<String>();
	}

	public boolean containsKey(int bib) {

		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i) == (bib))
				return true;
		}
		
		return false;
	}

	public boolean containsValue(String name) {

		for(int i = 0; i < nameList.size(); i++){
			if(nameList.get(i).equals(name))
				return true;
		}
		
		return false;
	}

	public String get(int bib) {
		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i) == (bib))
				return nameList.get(i);
		}
		
		return null;
	}

	public boolean isEmpty() {
		return (bibList.size() == 0 && nameList.size() == 0);
	}

	public void put(int bib, String name) {
		bibList.add(bib);
		nameList.add(name);
	}

	public boolean remove(int bib) {
		boolean removed = false;
		
		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i) == (bib)){
				nameList.remove(i);
				bibList.remove(i);
				removed = true;
			}
				
		}
		
		return removed;
	}

	public int size() {
		return bibList.size();
	}

	public ArrayList<String> values() {
		return nameList;
	}

}
