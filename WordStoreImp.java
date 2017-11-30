//package dictionary;
/*
* @author Usman Alam
*       A dictionary implementation using hash tables
**/

public final class WordStoreImp implements WordStore {

    private int position;
    private int size;
    private Cell[] LinkedCell;
    
    public WordStoreImp(int size) {
        this.size = size;
        LinkedCell = new Cell[this.size];
    }

    public void usedIndexes() {
        int count = 0;
        Cell ptr;
        for(int i=0; i<LinkedCell.length; i++) {
            if(LinkedCell[i]!=null) {
                ptr = LinkedCell[i];
                for(; ptr!=null; ptr = ptr.next)
                    count+=ptr.count;
            }
        }
        System.out.println("Total number of words generated : " + count);
    }

    public void printArray() {
        System.out.println();
        for(int i=0; i<LinkedCell.length; i++) {
            System.out.println("Bucket " + i + ": ");
            Cell start = LinkedCell[i];
            while(start!=null) {
                System.out.print(start.word + " " );
                start = start.next;
            }
            System.out.println();
        }
    }

    /*Takes a String argument and returns an int that refers to 
    * the index where the String is stored
    */
    public int hashCode(String word) {
        int hash = 7;
        for (int i = 0; i < word.length(); i++)
            hash = (hash*31 + word.charAt(i))%size;
        return hash;
        /*
        * This hashCode method uses Java's built in method
        * More efficient than the above
        */
        /*int hash = word.hashCode()%size;
        hash%=size;
        if(hash<0)
            hash+=size;
        return hash;*/
    }

    /*Takes a String as an argument and adds it to the collection.
    * If the String is already present in the collection, it adds
    * another occurrence of it
    */
    public void add(String word) { 
        position = hashCode(word);
        if(LinkedCell[position] == null) {
            LinkedCell[position] = new Cell(word); 
            return;
        }
        else {   
            Cell ptr = LinkedCell[position];
            for(; ptr!=null; ptr = ptr.next) {
                if(ptr.word.equals(word)) {
                    ptr.count++;
                    return;
                }
                if(ptr.next == null) {
                    ptr.next = new Cell(word);
                    return;
                }
            }
        }    
    }

    /*Takes a String as an argument and returns the "count" int that holds the value
    * of how many times the String argument is stored (i.e how many times it has been counted)
    */
    public int count(String word) {
        position = hashCode(word);
        if(LinkedCell[position]!=null) {
            Cell ptr = LinkedCell[position];
            for(; ptr!=null; ptr = ptr.next){
                if(ptr.word.equals(word))
                    return ptr.count;
            }
        }
        return 0;
    }

    /*Takes a String as an argument and removes a single occurrence of the String if it 
    * is present in the table. The collection remains unchanged if the String does not occur
    */
    public void remove(String word) {
        position = hashCode(word);
        Cell ptr = LinkedCell[position];
        if(ptr != null) {
            if(ptr.word.equals(word) && ptr.count == 1){
                LinkedCell[position] = LinkedCell[position].next;
                return;
            }
            Cell lastVal = null;
            Cell val = LinkedCell[position];
            for(; val.next != null && !val.word.equals(word); lastVal = val,val=val.next);
            if (val.word.equals(word))
                if(val.count == 1) {
                    if(val.next!=null)
                        lastVal.next = val.next;
                val.count--;
            }
        }
    }

    private class Cell {
        String word;
        int count;
        Cell next;
        Cell(String word) {
            this.word = word;
            count++;
            this.next = null;
        }
    }
}