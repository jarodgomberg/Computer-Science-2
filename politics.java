//Jarod Gomberg - ja545977 - 5106937
//1/18/2022
//COP3503
//Project 1 - politics.java

import java.util.*;

/*  
    Scans and outputs list of supporters in order, sorted by their preferred candidate. 
    
*/

public class politics {
    public static void main(String args[]) {
        
        Scanner stdin = new Scanner(System.in);
        int nCandidates = stdin.nextInt();
        int nSupporters = stdin.nextInt();

        while(nCandidates != 0) {

            // HashMap for candidates key = string for each name
            // value = integer which will be unique id incremented accordingly
            HashMap<String, Integer> candidates = new HashMap<String, Integer>();

            // HashMap for supporters key = integer, value = arraylist of strings. 
            // that way we can store all the supporters per unique id integer given to candidates.
            HashMap<Integer, ArrayList<String>> supporters = new HashMap<Integer, ArrayList<String>>();

            int cId = 0; //to hold ID number per each candidate

            // iterate through candidates, scan to map and assign ID
            // increment ID value each time
            for(int i = 0; i < nCandidates; i++) {
                String candidate = stdin.next();
                candidates.put(candidate, cId);

                //For each candidate create a list of strings for the names in supporter map
                supporters.put(cId, new ArrayList<String>());

                cId++;
            }

            // increment through Supporters and scan name of both candidate and supporter 
            for(int i = 0; i < nSupporters; i++) {
                String candidate, supporter; 
                supporter = stdin.next();
                candidate = stdin.next();
                
                // check if candidate name is already declared and has ID
                // if true, get the ID of that candidate and add supporter name per ID 
                if(candidates.containsKey(candidate)) {
                    int candidate_id = candidates.get(candidate);
                   supporters.get(candidate_id).add(supporter);
                }
                else {
                    // if it isn't declared, get ID value from ID counter
                   int candidate_id = cId;
                    
                   // add that candidate to the map 
                   candidates.put(candidate, candidate_id);
                    // create new list of strings for that candidate's supporters names
                    // add supporter to supporter map
                   supporters.put(candidate_id, new ArrayList<String>());
                   supporters.get(candidate_id).add(supporter);

                   // increment cID per new candidate also
                   cId++; 
                }  
            }
            
            //print output for each case
            // go through map and iterate through list in map
            for(Map.Entry<Integer, ArrayList<String>> list : supporters.entrySet()){
                for(String name : list.getValue()){
                  System.out.println(name);
                }
            }

            //scan for next case
            nCandidates = stdin.nextInt();
            nSupporters = stdin.nextInt();
        }
    }
}


