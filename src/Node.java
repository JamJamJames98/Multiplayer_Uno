public class Node {
	
    String name;
    Node next;
    Node prev;
    int index;
    String[][] cards;
    String[][] last_played;
    
    public Node (String name, Node next, Node prev, int index, String[][] cards, String[][] last_played) {
    	
    		this.name = name;
    		this.next = next;
    		this.prev = prev;
    		this.index = index;
    		this.cards = cards;
    		this.last_played = last_played;
    	
    }
    
    public String[][] getLastPlayed() {
    	
    		return last_played;
    	
    }
    
    public void setLastPlayed(String[][] last_played) {
    	
    		this.last_played = last_played;
    	
    }
    
    public String[][] getCards() {
    	
    		return cards;
    		
    }
    
    public void setCards(String[][] cards) {
    		
    		this.cards = cards;
    	
    }
    
    public String getName() {
    	
    		return name;
    		
    }
    
    public void setName(String name) {
    	
    		this.name = name;
    		
    }
    
    public Node getNext() {
    	
		return next; 
		
    }
    
    public void setNext(Node next) {
    	
		this.next = next;
		
    }
    
    public Node getPrev() {
    	
		return prev;
		
    }
    
    public void setPrev(Node prev) {
    	
    		this.prev = prev;
    		
    }
    
    public int getIndex() {
    	
		return index;
		
    }	
    
    public void setIndex(int index) {
    	
    		this.index = index;
    		
    }
    
}