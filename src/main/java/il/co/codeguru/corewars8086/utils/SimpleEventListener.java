package il.co.codeguru.corewars8086.utils;


import il.co.codeguru.corewars8086.war.CompetitionEventListener;

public class SimpleEventListener implements CompetitionEventListener {
    public int currentWar = 0;
    @Override
    public void onWarStart() {
        currentWar++;
    }

    @Override
    public void onWarEnd(int reason, String winners) {

    }

    @Override
    public void onRound(int round) {

    }

    @Override
    public void onWarriorBirth(String warriorName) {

    }

    @Override
    public void onWarriorDeath(String warriorName, String reason) {

    }

    @Override
    public void onCompetitionStart() {

    }

    @Override
    public void onCompetitionEnd() {

    }

    @Override
    public void onEndRound() {

    }
}
