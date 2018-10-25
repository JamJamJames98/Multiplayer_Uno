public class Doubly_Linked_List {

	Node head;
	int elements;
	
	public Doubly_Linked_List() {
		
		head = new Node("Default_Head", null, null, 0, new String[108][3], new String[1][3]);
		elements = 1;
		
	}
	
	public Node getHead() {
		return head;
	}
	
	//used for testing
	public void printInformation(Node node) {
		
		System.out.println("Prev is " + node.getPrev().getName());
		System.out.println("Next is " + node.getNext().getName());
		
	}
	
	public boolean checkSameName(String name) {
		
		Node curr = head;
		int counter = 0;
		while (counter < elements-1) {
			if (curr.getName().equals(name)) {
				return true;
			}
			curr = curr.getNext();
			counter = counter + 1;
		}
		if (curr.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void printList() {
		
		Node curr = head;
		int counter = 0;
		while (counter < elements-1) {
			System.out.println("Player" + (counter+1) + " : " + curr.getName());
			printInformation(curr);
			curr = curr.getNext();
			counter = counter + 1;
		}
		System.out.println("Player" + (counter+1) + " : " + curr.getName());
		printInformation(curr);
	}
	
	public void append(String name) {
		
		Node temp = new Node(name, null, null, 0, new String[108][3], new String[1][3]);
		Node curr = head;
		int counter = 0;
		while (counter < elements-1) {
			curr = curr.getNext();
			counter = counter + 1;
		}
		curr.setNext(temp);
		temp.setIndex(elements);
		temp.setPrev(curr);
		//making the circular join
		temp.setNext(head);
		head.setPrev(temp);
		elements = elements + 1;
		
	}
	
	public int getLength() {
		return elements;
	}
	
}