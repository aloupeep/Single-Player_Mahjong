package ui;

import model.*;
import java.util.*;

// Represents an automatic checker for a given hand
public class HandChecker {

    // Effects: Initializes a new hand checker
    public HandChecker() {

    }

    // Requires: hand is sorted
    // Effects: returns true if given hand is valid (winning), false otherwise
    public boolean declareWin(Hand hand) {
        List<Integer> idList = new ArrayList<>();
        for (Tile t : hand.getHand()) {
            idList.add(t.getID());
        }
        return checkWinOneState(idList,0);
    }

    // Effects: returns true if the given list of tile ids correspond to a winning hand;
    //          false otherwise
    private boolean checkWinOneState(List<Integer> idList, int pair) {
        if ((idList.size() == 0) && (pair == 1)) {
            return true;
        } else if (idList.size() == 0) {
            return false;
        } else {
            return checkWinListStates(getNextValidStates(idList, pair));
        }
    }

    // Effects: returns true if one of the given list of tile ids in the key set of the map
    //          is a winning hand, false otherwise
    private boolean checkWinListStates(LinkedHashMap<List<Integer>, Integer> statesMap) {
        int mapSize = statesMap.size();
        if (mapSize == 0) {
            return false;
        } else {
            List<Integer> idList = statesMap.keySet().iterator().next();
            int pair = statesMap.get(idList);
            statesMap.remove(idList);
            return (checkWinOneState(idList, pair) || checkWinListStates(statesMap));
        }
    }

    // Effects: returns a map of each possible reduced state of the hand (by taking away one triple, one sequence,
    //          or one pair from its list-of-id form) mapped to the number of pairs taken away from the hand
    //          since the beginning.
    private LinkedHashMap<List<Integer>, Integer> getNextValidStates(List<Integer> idList, int pair) {
        LinkedHashMap<List<Integer>, Integer> nextStates = new LinkedHashMap<>();

        try {
            nextStates.put(removeTriple(idList), pair);
        } catch (Exception e) {
            // does nothing
        }

        try {
            nextStates.put(removeSequence(idList), pair);
        } catch (Exception e) {
            // does nothing
        }

        try {
            nextStates.put(removePair(idList), pair + 1);
        } catch (Exception e) {
            // does nothing
        }

        return nextStates;
    }

    // Effects: Attempts to remove a triple of the first tile's id from the list of ids, throws exception if unable
    //          to do so (list of ids does not contain a triple of the first id)
    private List<Integer> removeTriple(List<Integer> idListBackup) throws Exception {
        List<Integer> idList = clone(idListBackup);
        int firstId = idList.get(0);
        if (Collections.frequency(idList, firstId) >= 3) {
            idList.remove(Integer.valueOf(firstId));
            idList.remove(Integer.valueOf(firstId));
            idList.remove(Integer.valueOf(firstId));
            return idList;
        } else {
            throw new Exception("invalid triple");
        }
    }

    // Requires: The list is sorted
    // Effects: Attempts to remove a sequence with the first tile's id being the smallest id in the sequence from
    //          the list of ids, throws exception if unable to do so
    //          (list of ids does not contain a sequence starting from the first id)
    private List<Integer> removeSequence(List<Integer> idListBackup) throws Exception {
        List<Integer> idList = clone(idListBackup);
        int firstId = idList.get(0);
        if ((firstId >= 25) || (firstId % 9 >= 7)) {
            throw new Exception("invalid sequence");
        } else if ((idList.contains(firstId)) && (idList.contains(firstId + 1)) && (idList.contains(firstId + 2))) {
            idList.remove(Integer.valueOf(firstId));
            idList.remove(Integer.valueOf(firstId + 1));
            idList.remove(Integer.valueOf(firstId + 2));
            return idList;
        } else {
            throw new Exception("invalid sequence");
        }
    }

    // Effects: attempts to remove a pair of the first tile's id from the list of ids, throws exception if unable
    //          to do so (list of ids does not contain a pair of the first id)
    private List<Integer> removePair(List<Integer> idListBackup) throws Exception {
        List<Integer> idList = clone(idListBackup);
        int firstId = idList.get(0);
        if (Collections.frequency(idList, firstId) >= 2) {
            idList.remove(Integer.valueOf(firstId));
            idList.remove(Integer.valueOf(firstId));
            return idList;
        } else {
            throw new Exception("invalid pair");
        }
    }

    // Effects: returns a clone of the list inputted
    private List<Integer> clone(List<Integer> originalList) {
        List<Integer> cloneList = new LinkedList<>();
        for (int i : originalList) {
            cloneList.add(i);
        }
        return cloneList;
    }
}
