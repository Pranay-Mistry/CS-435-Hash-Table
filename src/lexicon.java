import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class lexicon {
    public MakeHash T;
    public String a;
    public int m;
    public int insert = 10;
    public int delete = 11;
    public int search = 12;
    public int print = 13;
    public int create = 14;
    public int comment = 15;

    public void HashCreate(int size) {
        m = size;
        T = new MakeHash(m);
        char[] ar = new char[15 * m];
        Arrays.fill(ar, ' ');
        a = new String(ar);
    }

    public void insertInHash(String w) {
        T.HashInsert(w, a, freeSpace(w));
        int in = freeSpace(w);
        w = w + '\0';
        a = a.substring(0, in) + w + a.substring(in + 1);
    }

    public void increaseSize(String str) {
        char[] copy = a.toCharArray();
        copy = Arrays.copyOf(copy, a.length() * 2);
        a = new String(copy);
    }

    public int freeSpace(String w) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == ' ') {
                if (a.length() - i > w.length())
                    return i;
                else {
                    increaseSize(a);
                    return i;
                }
            }
        }
        return 0;
    }

    public void searchWord(String word) {
        int idx = T.HashSearch(word, a);
        if (idx == -1) {
            System.out.println(word + " not found");
        } else {
            System.out.println(word + " is at index: " + idx);
        }
    }

    public void deleteWord(String word) {
        T.HashDelete(word, a);
    }

    public void printHash() {
        System.out.print("Hash Table [T]:\t\t\t\t\t\t\t A:");
        for (int k = 0; k < a.length(); k++) {
            char l = a.charAt(k);
            if (l == '\0') {
                System.out.print("\\");
            } else {
                System.out.print(l);
            }
        }
        System.out.println();
        for (int i = 0; i < T.table.length; i++) {
            if (T.table[i] == -1 || T.table[i] == -2) {
                System.out.println(i + " = ");
                continue;
            }
            String word = "";
            for (int j = T.table[i]; j < a.length(); j++) {
                if (a.charAt(j) != '\0') {
                    word += a.charAt(j);
                } else {
                    System.out.println(i + " = " + word);
                    break;
                }

            }
        }
        System.out.println();

    }

    public void HashBatch(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String temp[] = line.split(" ");
                String op = "";
                String value = "";
                op = temp[0];
                int operator = Integer.parseInt(op);
                if (temp.length > 1)
                    value = temp[1];
                    if(operator == create)
                        HashCreate(Integer.parseInt(value));
                    else if(operator == insert)
                        insertInHash(value);
                    else if(operator == delete)
                        deleteWord(value);
                    else if(operator == search)
                        searchWord(value);
                    else if(operator == comment)
                        continue;
                else if(temp.length == 1) {
                    if (operator == print)
                        printHash();
                    else if(operator == comment)
                        continue;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
