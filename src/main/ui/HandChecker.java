package ui;

import model.*;
import java.util.*;

public class HandChecker {

    public HandChecker() {

    }

    public boolean declareWin(Hand hand) {
        List<Integer> idList = new ArrayList<>();
        for (Tile t : hand.getHand()) {
            idList.add(t.getID());
        }
        return checkWinOneState(idList,0);
    }

    private boolean checkWinOneState(List<Integer> idList, int pair) {
        if ((idList.size() == 0) && (pair == 1)) {
            return true;
        } else if (idList.size() == 0) {
            return false;
        } else {
            return checkWinListStates(getNextValidStates(idList, pair));
        }
    }

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

    private List<Integer> removeSequence(List<Integer> idListBackup) throws Exception {
        List<Integer> idList = clone(idListBackup);
        int firstId = idList.get(0);
        if (firstId >= 25) {
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

    private List<Integer> clone(List<Integer> originalList) {
        List<Integer> cloneList = new LinkedList<>();
        for (int i : originalList) {
            cloneList.add(i);
        }
        return cloneList;
    }
}
