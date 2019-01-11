package com.java.ipl.run.service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.java.ipl.run.modal.DeliverModal;
import com.java.ipl.run.modal.MatchesModal;

/**
 * @author Rajkumar Banala 05-Jan-2019
 */

public class IPLApplicationServiceImpl implements IPLApplicationService {

    private static Logger log = LoggerFactory.getLogger(IPLApplicationServiceImpl.class);

    List<DeliverModal> deliverModalList = null;
    List<MatchesModal> matchesModalList = null;
    Map<String, DeliverModal> deliverModalMap = null;
    Map<String, MatchesModal> matchesModalMap = null;

    String folderPath = "/java-ipl-application/src/main/resources/files/";

    String deliverFilePath = "deliveries.csv";
    String matchesFilePath = "matches.csv";

    public List<MatchesModal> readMatchesData() {
        log.debug("readMatchesData()..........");

        try {

            File csvFile = new File(folderPath + matchesFilePath);

            if (!csvFile.exists())
                throw new RuntimeException("file not found:" + csvFile.getAbsolutePath());

            ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(csvFile), CsvPreference.STANDARD_PREFERENCE);

            String[] headerE = beanReader.getHeader(true);

            final CellProcessor[] processors = getCellProcessors(headerE.length);

            MatchesModal matchesModal;

            matchesModalList = new ArrayList<MatchesModal>();
            matchesModalMap = new HashMap<>();

            while ((matchesModal = beanReader.read(MatchesModal.class, headerE, processors)) != null) {

                if (matchesModal != null) {
                    matchesModalList.add(matchesModal);
                    matchesModalMap.put(matchesModal.getMATCH_ID(), matchesModal);
                }
            }

//            log.debug("readMatchesData()..........matchesModalList:" + matchesModalList);
            log.debug("readMatchesData()..........count:" + matchesModalList.size());

            // close streams
            beanReader.close();

            return matchesModalList;

        } catch (Exception e) {
            log.debug("main()..........Exception:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DeliverModal> readDeliverData() {
        log.debug("readDeliverData()..........");

        try {

            File csvFile = new File(folderPath + deliverFilePath);

            if (!csvFile.exists())
                throw new RuntimeException("file not found:" + csvFile.getAbsolutePath());

            ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(csvFile), CsvPreference.STANDARD_PREFERENCE);

            String[] headerE = beanReader.getHeader(true);

            final CellProcessor[] processors = getCellProcessors(headerE.length);

            DeliverModal deliverModal;

            deliverModalList = new ArrayList<DeliverModal>();
            deliverModalMap = new HashMap<>();

            while ((deliverModal = beanReader.read(DeliverModal.class, headerE, processors)) != null) {

                if (deliverModal != null) {
                    deliverModalList.add(deliverModal);
                    deliverModalMap.put(deliverModal.getMATCH_ID(), deliverModal);

                }
            }

//            log.debug("readDeliverData()..........deliverModalList:" + deliverModalList);
            log.debug("readDeliverData()..........count:" + deliverModalList.size());

            // close streams
            beanReader.close();

            return deliverModalList;

        } catch (Exception e) {
            log.debug("main()..........Exception:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public CellProcessor[] getCellProcessors(int size) {

        CellProcessor[] cellProcessorArray = new CellProcessor[size];

        for (int i = 0; i < size; i++) {
            cellProcessorArray[i] = new Optional();
        }

        return cellProcessorArray;

    }

    public void top4TeamsElectedField() {
        log.debug("top4TeamsElectedField()..........");

        Map<String, Map<String, Object>> map_2016_2017 = new HashMap<>();

        for (MatchesModal matchesModal : matchesModalList) {

            if (matchesModal.getTOSS_DECISION().equals("field") && (matchesModal.getSEASON().equals("2016") || matchesModal.getSEASON().equals("2017"))) {

                Map<String, Object> matchMap = map_2016_2017.get(matchesModal.getTOSS_WINNER());

                int count = 0;
                if (matchMap != null) {
                    count = (int) matchMap.get("count");
                } else {
                    matchMap = new HashMap<>();
                }
                count++;
                matchMap.put("count", count);
                matchMap.put("matchesModal", matchesModal);

                map_2016_2017.put(matchesModal.getTOSS_WINNER(), matchMap);

            }
        }
        List<Integer> list2016_2017 = new LinkedList<>();
        map_2016_2017.entrySet().forEach(e -> {
            list2016_2017.add((Integer) e.getValue().get("count"));
            // System.out.println(e.getValue().get("matchesModal"));
        });
        Collections.sort(list2016_2017, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) return -1;
                return 0;
            }
        });
        int cnt = 0;
        List<MatchesModal> top4Teams = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (Map.Entry<String, Map<String, Object>> map : map_2016_2017.entrySet()) {
                if ((Integer) map.getValue().get("count") == list2016_2017.get(cnt)) {
                    top4Teams.add((MatchesModal) map.getValue().get("matchesModal"));
                    cnt++;
                }
            }
        }

        String tab = "                              ";
        String key1 = "YEAR";
        String key2 = "TEAM";
        String key3 = "COUNT";

        System.out.println("\n1).Top 4 teams which elected to field first after winning toss in the year 2016 and 2017.");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(key1 + tab + key2 + tab + key3);
        System.out.println("----------------------------------------------------------------------------");
        for (int k = 0; k < 4; k++) {
            String year = top4Teams.get(k).getSEASON();
            String teamName = top4Teams.get(k).getTOSS_WINNER();
            teamName += displaySpace(tab.length() + key2.length() - teamName.length());
            System.out.println("2016 and 2017 AVG" + "         " + teamName + map_2016_2017.get(top4Teams.get(k).getTOSS_WINNER()).get("count"));
        }
    }

    public void totalFoursSixesTotalScore() {
        log.debug("totalFoursSixesTotalScore()..........");

        Map<String, Map<String, Map<String, Integer>>> scoreDetailsMap = new HashMap<>();
        Map<String, Map<String, Integer>> scoreDetailsYearMap = null;
        Map<String, Integer> deliveryMap = null;
        Integer fourCount = null;
        Integer sixCount = null;
        Integer totalRuns = null;


        for (DeliverModal deliverModal : deliverModalList) {

            MatchesModal matchesModal = matchesModalMap.get(deliverModal.getMATCH_ID());

            if (matchesModal == null)
                throw new RuntimeException("matche not found MatchID:" + deliverModal.getMATCH_ID());

            scoreDetailsYearMap = scoreDetailsMap.get(matchesModal.getSEASON());

            if (scoreDetailsYearMap == null)
                scoreDetailsYearMap = new HashMap<>();

            deliveryMap = scoreDetailsYearMap.get(deliverModal.getBATTING_TEAM());

            if (deliveryMap == null)
                deliveryMap = new HashMap<>();

            fourCount = deliveryMap.get("fourCount");
            sixCount = deliveryMap.get("sixCount");
            totalRuns = deliveryMap.get("totalRuns");

            if (fourCount == null)
                fourCount = 0;

            if (sixCount == null)
                sixCount = 0;

            if (totalRuns == null)
                totalRuns = 0;

            Integer batsManRuns = Integer.parseInt(deliverModal.getBATSMAN_RUNS());

            if (batsManRuns == 4) {
                fourCount++;
            } else if (batsManRuns == 6) {
                sixCount++;
            }

            totalRuns += Integer.parseInt(deliverModal.getTOTAL_RUNS());

            deliveryMap.put("fourCount", fourCount);
            deliveryMap.put("sixCount", sixCount);
            deliveryMap.put("totalRuns", totalRuns);

            scoreDetailsYearMap.put(deliverModal.getBATTING_TEAM(), deliveryMap);
            scoreDetailsMap.put(matchesModal.getSEASON(), scoreDetailsYearMap);

        }

        String tab = "                                 ";
        String key1 = "YEAR";
        String key2 = "TEAM";
        String key3 = "FOURS";
        String key4 = "SIX";
        String key5 = "TOTAL_RUNS";

        System.out.println("\n2).List total number of fours, sixes, total score with respect to team and year.");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(key1 + tab + key2 + tab + key3 + tab + key4 + tab + key5);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Entry<String, Map<String, Map<String, Integer>>> scoreDetailsEntry : scoreDetailsMap.entrySet()) {

            String year = scoreDetailsEntry.getKey();

            for (Entry<String, Map<String, Integer>> scoreDetailsYearEntry : scoreDetailsEntry.getValue().entrySet()) {

                String teamName = scoreDetailsYearEntry.getKey();
                deliveryMap = scoreDetailsYearEntry.getValue();

                fourCount = deliveryMap.get("fourCount");
                sixCount = deliveryMap.get("sixCount");
                totalRuns = deliveryMap.get("totalRuns");

                year += displaySpace(tab.length() + key1.length() - year.length());
                teamName += displaySpace(tab.length() + key2.length() - teamName.length());
                String fourCountString = fourCount + displaySpace(tab.length() + key3.length() - fourCount.toString().length());
                String sixCountString = sixCount + displaySpace(tab.length() + key4.length() - sixCount.toString().length());
                String totalRunsString = totalRuns + displaySpace(tab.length() + key5.length() - totalRuns.toString().length());

                System.out.println(year + teamName + fourCountString + sixCountString + totalRunsString);

            }

        }

    }

    public String displaySpace(int size) {

        String space = "";

        while (size > 0) {
            space += " ";
            size--;
        }
        return space;
    }

    @Override
    public void top10BestEconomy() {
        log.debug("top10BestEconomy()..........");
        Map<String, Map<String, Map<String, Double>>> bowlerDetailsMap = new HashMap<>();
        Map<String, Map<String, Double>> bowlerDetailsYearMap = null;
        Map<String, Double> bowlerMap = null;
        double overs = 0.0;
        double score = 0.0;
        double economy = 0.0;

        for (DeliverModal deliverModal : deliverModalList) {

            MatchesModal matchesModal = matchesModalMap.get(deliverModal.getMATCH_ID());

            if (matchesModal == null)
                throw new RuntimeException("matche not found MatchID:" + deliverModal.getMATCH_ID());

            bowlerDetailsYearMap = bowlerDetailsMap.get(matchesModal.getSEASON());
            if (bowlerDetailsYearMap == null)
                bowlerDetailsYearMap = new HashMap<>();

            overs = 0.0;
            score = 0.0;
            economy = 0.0;

            bowlerMap = bowlerDetailsYearMap.get(deliverModal.getBOWLER());

            if (bowlerMap == null)
                bowlerMap = new HashMap<>();


            score = Integer.parseInt(deliverModal.getTOTAL_RUNS()) - Integer.parseInt(deliverModal.getBYE_RUNS()) - Integer.parseInt(deliverModal.getLEGBYE_RUNS());
            if (bowlerMap.get("overs") == null) {
                bowlerMap.put("overs", 0.16669);
            } else {
                bowlerMap.put("overs", bowlerMap.get("overs") + 0.167);
            }

            if (bowlerMap.get("score") != null) {
                bowlerMap.put("score", new Double(score) + bowlerMap.get("score"));
            } else {
                bowlerMap.put("score", new Double(score));
            }


            bowlerDetailsYearMap.put(deliverModal.getBOWLER(), bowlerMap);
            bowlerDetailsMap.put(matchesModal.getSEASON(), bowlerDetailsYearMap);

        }

        //economy and sorting
        System.out.println("\n3). Top 10 best economy rate bowler with respect to year ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Entry<String, Map<String, Map<String, Double>>> bowlerDetailsEntry : bowlerDetailsMap.entrySet()) {
            String year = bowlerDetailsEntry.getKey();
            Map<String, Double> bowlerSortedMap = new LinkedHashMap<>();
            for (Entry<String, Map<String, Double>> scoreDetailsYearEntry : bowlerDetailsEntry.getValue().entrySet()) {
                bowlerMap = scoreDetailsYearEntry.getValue();
                if (bowlerMap.get("score") > 0 && bowlerMap.get("overs") >= 10)
                    economy = bowlerMap.get("score") / bowlerMap.get("overs");
                bowlerMap.put("economy", economy);
                scoreDetailsYearEntry.setValue(bowlerMap);
                bowlerSortedMap.put(scoreDetailsYearEntry.getKey(), scoreDetailsYearEntry.getValue().get("economy"));
            }
            Set<Map.Entry<String, Double>> bowlersset = bowlerSortedMap.entrySet();
            List<Map.Entry<String, Double>> bowlersList = new ArrayList<Map.Entry<String, Double>>(bowlersset);
            Collections.sort(bowlersList, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1,
                                   Map.Entry<String, Double> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>(bowlersList.size());
            for (Map.Entry<String, Double> entry : bowlersList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }


            int cnt = 0;
            for (Map.Entry<String, Double> map : sortedMap.entrySet()) {
                cnt++;
                System.out.println(year + "             " + map.getKey() + "                  " + map.getValue());
                if (cnt > 10)
                    break;
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void HighestRunRateTeam() {
        log.debug("HighestRunRateTeam()..........");
        Map<String, Map<String, Map<String, Double>>> netRunDetailsMap = new HashMap<>();
        Map<String, Map<String, Double>> netRunDetailsYearMap = null;
        Map<String, Double> netRunMap_batting = null;
        Map<String, Double> netRunMap_bowling = null;

        for (DeliverModal deliverModal : deliverModalList) {
            MatchesModal matchesModal = matchesModalMap.get(deliverModal.getMATCH_ID());

            if (matchesModal == null)
                throw new RuntimeException("matche not found MatchID:" + deliverModal.getMATCH_ID());

            netRunDetailsYearMap = netRunDetailsMap.get(matchesModal.getSEASON());


            if (netRunDetailsYearMap == null)
                netRunDetailsYearMap = new HashMap<>();

            netRunMap_batting = netRunDetailsYearMap.get(deliverModal.getBATTING_TEAM());

            if (netRunMap_batting == null)
                netRunMap_batting = new HashMap<>();
            if (netRunMap_batting.get("overs_faced") != null) {
                netRunMap_batting.put("overs_faced", netRunMap_batting.get("overs_faced") + 0.1666);
            } else {
                netRunMap_batting.put("overs_faced", 0.1666);
            }

            if (netRunMap_batting.get("runs_scored") != null) {
                netRunMap_batting.put("runs_scored", (netRunMap_batting.get("runs_scored") + Double.valueOf(Integer.parseInt(deliverModal.getTOTAL_RUNS()))));
            } else {
                netRunMap_batting.put("runs_scored", Double.valueOf(Integer.parseInt(deliverModal.getTOTAL_RUNS())));
            }


            netRunMap_bowling = netRunDetailsYearMap.get(deliverModal.getBOWLING_TEAM());

            if (netRunMap_bowling == null)
                netRunMap_bowling = new HashMap<>();
            if (netRunMap_bowling.get("overs_bowled") != null) {
                netRunMap_bowling.put("overs_bowled", netRunMap_bowling.get("overs_bowled") + 0.1666);
            } else {
                netRunMap_bowling.put("overs_bowled", 0.1666);
            }

            if (netRunMap_bowling.get("runs_conceded") != null) {
                netRunMap_bowling.put("runs_conceded", (netRunMap_bowling.get("runs_conceded") + Double.valueOf(Integer.parseInt(deliverModal.getTOTAL_RUNS()))));
            } else {
                netRunMap_bowling.put("runs_conceded", Double.valueOf(Integer.parseInt(deliverModal.getTOTAL_RUNS())));
            }

            netRunDetailsYearMap.put(deliverModal.getBATTING_TEAM(), netRunMap_batting);
            netRunDetailsYearMap.put(deliverModal.getBOWLING_TEAM(), netRunMap_bowling);

            netRunDetailsMap.put(matchesModal.getSEASON(), netRunDetailsYearMap);
        }

        System.out.println("\n3).  Highest Net Run Rate with respect to year ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        double netRunrate = 0.0;
        //calculate runrate and sort
        for (Entry<String, Map<String, Map<String, Double>>> netRunDetailsEntry : netRunDetailsMap.entrySet()) {
            Map<String, Double> netRunSortedMap = new LinkedHashMap<>();
            for (Entry<String, Map<String, Double>> netRunDetailsYearEntry : netRunDetailsEntry.getValue().entrySet()) {
                netRunrate = (netRunDetailsYearEntry.getValue().get("runs_scored") / netRunDetailsYearEntry.getValue().get("overs_faced")) - (netRunDetailsYearEntry.getValue().get("runs_conceded") / netRunDetailsYearEntry.getValue().get("overs_bowled"));
                netRunSortedMap.put(netRunDetailsYearEntry.getKey(), netRunrate);
            }

            Set<Map.Entry<String, Double>> netRunset = netRunSortedMap.entrySet();
            List<Map.Entry<String, Double>> netRunList = new ArrayList<Map.Entry<String, Double>>(netRunset);
            Collections.sort(netRunList, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1,
                                   Map.Entry<String, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>(netRunList.size());
            for (Map.Entry<String, Double> entry : netRunList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            int cnt = 0;
            for (Map.Entry<String, Double> map : sortedMap.entrySet()) {
                cnt++;
                System.out.println(netRunDetailsEntry.getKey() + "             " + map.getKey() + "                  " + map.getValue());
                if (cnt == 1)
                    break;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}