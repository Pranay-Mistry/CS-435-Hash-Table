import java.util.Arrays;

public class MakeHash {
    public int collisions;
    int table[];

    public MakeHash(int m){
        table = new int[m];
        Arrays.fill(table,-1);
    }

    public int getHash(String str){
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash += (int) str.charAt(i);
        }
        int code = hash % table.length;
        return code;
    }


    public boolean HashEmpty  (){
        for (int i = 0; i < table.length; i++) {
            if(table[i] != -1){
                return false;
            }
        }
        return true;
    }

    public boolean HashFull(){
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            if(table[i] == -1){
                count++;
            }
        }
        if(count <= table.length/2){
            return true;
        }
        return false;
    }

    public void HashInsert (String  w, String a, int idx) {//Insert w  into L (and T and A)
        int index = getHash(w);

        int prob = 1;
        if(HashFull()){
            increaseTableSize(a); //increase size
        }

        while(table[index] != -1 && table[index] != -2){
            index = (index + (prob * prob)) % table.length;
            prob++;
        }
        table[index] = idx;
    }

    public void increaseTableSize(String a) {
        int size = table.length;
        int tableSize = table.length * 2;
        int[] temp = table;
        table = new int[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = -1;
        }
        for (int i = 0; i < size; i++) {
            String word = "";
            int k = temp[i];
            if(k == -1 || k == -2)
                continue;
            for (int j = k; j < a.length(); j++) {
                if (a.charAt(j) != '\0') {
                    word += a.charAt(j);
                } else {
                    break;
                }
            }
            if(!word.equals("")){
                HashInsert(word,a,temp[i]);
            }
        }
    }

    public int HashSearch (String  w, String a) { //Search for string in L (and this means T)
        int hashIndex = getHash(w);
        int stringIndex = table[hashIndex];
        int probIdx = 1;

        while(stringIndex != -1) {
            if(stringIndex == -2){
                hashIndex = (hashIndex + (probIdx * probIdx)) % table.length;
                stringIndex = table[hashIndex];
                probIdx++;
                continue;
            }
            String word = "";
            for (int j = stringIndex; j < a.length(); j++) {
                if (a.charAt(j) != '\0') {
                    word += a.charAt(j);
                }
                else{
                    break;
                }
            }
            if(word.equals(w)){
                return hashIndex;
            }
            else{
                hashIndex = (hashIndex + (probIdx * probIdx)) % table.length;
                stringIndex = table[hashIndex];
                probIdx++;
            }
        }
        return -1;

    }

    public int HashDelete (String  w, String a) { //Delete  w from L (but not necessarily from A)
        int hashIdx = HashSearch(w,a);
        if (hashIdx != -1){
            table[hashIdx] = -2;
            System.out.println(w + " is deleted from index " + hashIdx);
            return hashIdx;
        }
        return -1;
    }

}

