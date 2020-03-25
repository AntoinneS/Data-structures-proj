//A. Stewart-
//G. Watson-
//Chevis Hutchinson -1601446
package main;

import java.io.File;
import java.io.FileReader;

public class LinkedList { 
	private Node head; 
    private Node tail; 
    private int count;
    
    public LinkedList() { 
    	this.head = this.tail = null;
    	this.count = 0;
    }
    
	public void read(String file) { 
		try {
			this.destroy(); 
			
			FileReader reader = new FileReader(new File(file));  
			
			String sentence = ""; char letter; int val = 0; 
			
			while((val = reader.read()) != -1) { 
				letter = (char) val; 
				
				if(letter == '\n') { 
					this.parse(sentence); 
					sentence = "";  
				}
				else {
					sentence += letter;
				}
			}
			
			if(!sentence.isEmpty()) {  
				this.parse(sentence);
			}
			
			reader.close(); 
		}
		catch (Exception e) {
			System.err.println(e.getMessage()); 
		}
	}
	
	public void parse(String sentence) {
		String array[] = sentence.split("	");  
		String word = "", definition = "", pos = ""; 
		
		if(array.length == 3) {  
			word = array[0].replace("-", ""); word = word.replace("-", ""); 
			
			String array2[] = array[1].split(". "); 
			int count = 0;
			
			for(String string: array2) { 
				String newSpeech = Driver.parsePOS(string.replace(".", ""));
				pos += (count==0?"": ", ") + newSpeech; count++; 
			}
			
			definition = array[2].replace("-", ""); definition = definition.replace("-", ""); 
			
			if((!word.trim().isEmpty() && !definition.trim().isEmpty() && !pos.trim().isEmpty()) && (this.search(word.trim()) == null) ) { 
				word = Driver.upperCaseFirstLetter(word.trim()); definition = Driver.upperCaseFirstLetter(definition.trim()); pos = Driver.upperCaseFirstLetter(pos.trim()); 
				
				this.insertAtBack(new Data(word, definition, pos)); 
			}
		}
	}
    
	@SuppressWarnings("unused") 
	public void insertAtFront(Data data) { 
		Node temp = new Node(data, null); 
        
        if(temp != null) { 
            if(this.isEmpty()) { 
                this.head = temp; this.tail = temp; 
                this.count++; 
            }
            else { 
                temp.setNextNode(this.head);
                this.head = temp; 
                this.count++;
            }
        } else {
            System.out.println("An error occured when inserting at front");
        }
    }
    
    @SuppressWarnings("unused")
	public void insertAtBack(Data data) { 
    	Node temp = new Node(data, null); 
        
        if(temp != null) { 
            if(this.isEmpty()) { 
                this.head = temp; this.tail = temp; 
                this.count++; 
            }
            else { 
                this.tail.setNextNode(temp); this.tail = temp;
                this.count++;
            }
        } else { 
            System.out.println("An error occured when inserting at back");
        }
    }
    
    @SuppressWarnings("unused")
	public void insertAtMiddle(Data data) { 
    	Node previousNode, nextNode, temp = new Node(data, null); 
		previousNode = this.head; 
		nextNode = this.head; 

		if(temp != null) { 
			while(previousNode != null) { 
				nextNode = previousNode.getNextNode(); 

				if(nextNode != null) { 
					String word = nextNode.getData().getWord(); 
					int result = data.getWord().compareToIgnoreCase(word); 
					
					if(result < 0) { 
						previousNode.setNextNode(temp); 
						temp.setNextNode(nextNode); 
						this.count++; 
						break; 
					}
					previousNode = previousNode.getNextNode(); 
				} else { 
					break;
				}
			}
		} else { 
			System.out.println("An error occured when inserting at middle");
		}
	}
    
    public final boolean isEmpty() { 
    	if(this.head == null) { 
    		return true;
    	} else { 
    		return false;
    	}
    }
    
    public final int getCount() { 
    	return this.count;
    }
    
    public void sort() { 
    	Node temp = this.head, nextNode = null; 
   	
    	while(temp != null) { 
    		nextNode = temp.getNextNode(); 
    		
    		if(nextNode != null) {
    			while(nextNode != null) { 
    				int result = nextNode.getData().getWord().compareTo(temp.getData().getWord());
    				
    				if(result < 0) {
    					Data data = nextNode.getData(); 
    					nextNode.setData(temp.getData()); 
    					temp.setData(data);     				}
    				nextNode = nextNode.getNextNode(); 
    			}
    		} else { 
    			break;
    		}
    		
    		temp = temp.getNextNode();  
		}
    }
    
    public final Data search(String word) { 
    	Node temp = this.head;
    	Data data = null;
    	int counter = 0;
    	
    	while(temp != null) { 
    		if(temp.getData().getWord().toLowerCase().equals(word.toLowerCase())) {  
    			data = temp.getData();
    			data.setFoundIndex(counter); 
    		}
    		
    		counter++;
    		temp = temp.getNextNode();
    	}
    	
    	return data; 
    }
    
    @SuppressWarnings("unused")
	public void destroy() { 
    	Node temp = null;
		
    	while(!this.isEmpty()) {
			temp = this.head;
			this.head = this.head.getNextNode();
			temp = null;
			this.count--;
		}
	}
    
    public final Node getHeadNode() { 
    	return this.head;
    }
}