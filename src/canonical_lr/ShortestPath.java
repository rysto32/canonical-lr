/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

import lr_runtime.*;

/**
 *
 * @author rstone
 */
public class ShortestPath {
    public static Map<Integer, List<Integer>> shortestPath(ParseTable parseTable, ProductionFactory productions, int numStates) {
        Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
        Map<Integer, Integer> prev = new HashMap<Integer, Integer>();
        Map<Integer, Integer> prevSymbol = new HashMap<Integer, Integer>();
        
        dist.put(0, 0);
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(numStates, new DistComparator(dist));
        
        for(int i = 0; i < numStates; i++) {
            queue.add(i);
        }
        
        while(!queue.isEmpty()) {
            int u = queue.poll();
            
            if(!dist.containsKey(u)) {
                //throw new Error("" + u);
                continue;
            }
            
            int uDist = dist.get(u) + 1;
            for(ParseTable.Neighbour neighbour : parseTable.getNeighbours(u)) {
                Integer neighbourDist = dist.get(neighbour.state);
                
                if(neighbourDist == null || uDist < neighbourDist) {
                    queue.remove(neighbour.state);
                    dist.put(neighbour.state, uDist);
                    prev.put(neighbour.state, u);
                    prevSymbol.put(neighbour.state, neighbour.symbol);
                    queue.offer(neighbour.state);
                }
            }
        }
        
        Map<Integer,  List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < numStates; i++) {
            List<Integer> path = new LinkedList<Integer>();
            
            map.put(i, path);
            
            Integer current = i;
            
            while(current != null && current != 0) {
                if(prevSymbol.get(current) == null) {
                    System.out.println("Warning: State " + i + " is never reached.");
                    map.remove(i);
                    break;
                }
                
                path.add(0, prevSymbol.get(current));
                current = prev.get(current);
            }
        }
        
        return map;
    }
    
    private static class DistComparator implements Comparator<Integer> {
        private final Map<Integer, Integer> dist;

        public DistComparator(Map<Integer, Integer> dist) {
            this.dist = dist;
        }

        public int compare(Integer o1, Integer o2) {
            Integer dist1 = dist.get(o1);
            Integer dist2 = dist.get(o2);
            
            if(dist1 == null && dist2 == null) {
                return 0;
            } else if(dist1 == null) {
                return 1;
            } else if(dist2 == null) {
                return -1;
            }
            
            return dist1 - dist2;
        }
        
    }
}
