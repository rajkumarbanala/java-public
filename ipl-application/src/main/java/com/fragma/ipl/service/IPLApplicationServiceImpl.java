package com.fragma.ipl.service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.fragma.ipl.modal.DeliverModal;
import com.fragma.ipl.modal.MatchesModal;

/**
 * @author Bhavani Boda 05-Jan-2019
 *
 * 
 */

public class IPLApplicationServiceImpl implements IPLApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(IPLApplicationServiceImpl.class);
	
	List<DeliverModal> deliverModalList = null;
	List<MatchesModal> matchesModalList = null;
	Map<String, DeliverModal> deliverModalMap = null;
	Map<String, MatchesModal> matchesModalMap = null;
	
	String folderPath = "/Ipl_Problem/ipl-application/files/";
	
	String deliverFilePath = "deliveries.csv";
	String matchesFilePath = "matches.csv";
	
	public List<MatchesModal> readMatchesData() {
//		log.debug("readMatchesData()..........");
		
		try {
			
			File csvFile = new File(folderPath + matchesFilePath);
			
			if(!csvFile.exists())
				throw new RuntimeException("file not found:" + csvFile.getAbsolutePath());
			
		    ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(csvFile), CsvPreference.STANDARD_PREFERENCE);
		    
		    String[] headerE = beanReader.getHeader(true);
		    
            final CellProcessor[] processors = getCellProcessors(headerE.length);
            
            MatchesModal matchesModal;
            
            matchesModalList = new ArrayList<MatchesModal>();
            matchesModalMap = new HashMap<>();
            
            while ((matchesModal = beanReader.read(MatchesModal.class, headerE, processors)) != null) {
            	
                if(matchesModal!=null){
                	matchesModalList.add(matchesModal);
            		matchesModalMap.put(matchesModal.getMATCH_ID(), matchesModal);
                }
            }
            
//            log.debug("readMatchesData()..........matchesModalList:" + matchesModalList);
//            log.debug("readMatchesData()..........count:" + matchesModalList.size());
            
            // close streams
            beanReader.close();
            
            return matchesModalList;
            
		} catch (Exception e) {
			log.debug("main()..........Exception:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DeliverModal> readDeliverData() {
//		log.debug("readDeliverData()..........");
		
		try {
			
			File csvFile = new File(folderPath +deliverFilePath);
			
			if(!csvFile.exists())
				throw new RuntimeException("file not found:" + csvFile.getAbsolutePath());
			
		    ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(csvFile), CsvPreference.STANDARD_PREFERENCE);
		    
		    String[] headerE = beanReader.getHeader(true);
		    
            final CellProcessor[] processors = getCellProcessors(headerE.length);
            
            DeliverModal deliverModal;
            
            deliverModalList = new ArrayList<DeliverModal>();
            deliverModalMap = new HashMap<>();
            
            while ((deliverModal = beanReader.read(DeliverModal.class, headerE, processors)) != null) {
            	
                if(deliverModal!=null){
                	deliverModalList.add(deliverModal);
                	deliverModalMap.put(deliverModal.getMATCH_ID(), deliverModal);
                	
                }
            }
            
//            log.debug("readDeliverData()..........deliverModalList:" + deliverModalList);
//            log.debug("readDeliverData()..........count:" + deliverModalList.size());
            
            // close streams
            beanReader.close();
            
            return deliverModalList;
            
		} catch (Exception e) {
			log.debug("main()..........Exception:"+e.getMessage());
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
		//log.debug("top4TeamsElectedField()..........");
		
		Map<String, Map<String, Object>> map_2016 = new HashMap<>();
		Map<String, Map<String, Object>> map_2017 = new HashMap<>();
		
        for (MatchesModal matchesModal : matchesModalList) {
        	
        	if(matchesModal.getTOSS_DECISION().equals("field") && matchesModal.getSEASON().equals("2016")){
        		
        		Map<String, Object> matchMap =map_2016.get(matchesModal.getTOSS_WINNER());
        		
        		int count = 0;
        		if(matchMap != null){
            		count = (int) matchMap.get("count");
        		}else{
        			matchMap = new HashMap<>();
        		}
        		count++;
        		matchMap.put("count", count);
        		matchMap.put("matchesModal", matchesModal);
        		
        		map_2016.put(matchesModal.getTOSS_WINNER(), matchMap);
        		
        	}else if(matchesModal.getTOSS_DECISION().equals("field") && matchesModal.getSEASON().equals("2017")){
        		
        		Map<String, Object> matchMap =map_2017.get(matchesModal.getTOSS_WINNER());
        		
        		int count = 0;
        		if(matchMap != null){
            		count = (int) matchMap.get("count");
        		}else{
        			matchMap = new HashMap<>();
        		}
        		count++;
        		matchMap.put("count", count);
        		matchMap.put("matchesModal", matchesModal);
        		
        		map_2017.put(matchesModal.getTOSS_WINNER(), matchMap);
        	}
		}
        
        List<MatchesModal> top4Teams_2016 = new ArrayList<>();
        List<MatchesModal> top4Teams_2017 = new ArrayList<>();
        int top1_2016 = 0;
        int top2_2016 = 0;
        int top3_2016 = 0;
        int top4_2016 = 0;
        
        int top1_2017 = 0;
        int top2_2017 = 0;
        int top3_2017 = 0;
        int top4_2017 = 0;
        
        for (Entry<String, Map<String, Object>> entry : map_2016.entrySet()) {
        	
    		Map<String, Object> matchMap =entry.getValue();
    		int count = (int) matchMap.get("count");
    		
    		if(count == top1_2016 || count == top2_2016 || count == top3_2016 || count == top4_2016)
    			continue;
    		
        	if(count > top1_2016){
        		top4_2016= top3_2016;
        		top3_2016 = top2_2016;
        		top2_2016=top1_2016;
        		top1_2016=count;
        	}else if(count > top2_2016){
        		top4_2016= top3_2016;
        		top3_2016 = top2_2016;
        		top2_2016=count;
        	}else if(count > top3_2016){
        		top4_2016= top3_2016;
        		top3_2016 = count;
        	}else if(count > top4_2016){
        		top4_2016= count;
        	}
			
		}
        
        for (Entry<String, Map<String, Object>> entry : map_2017.entrySet()) {
        	
    		Map<String, Object> matchMap =entry.getValue();
    		int count = (int) matchMap.get("count");
    		
    		if(count == top1_2017 || count == top2_2017 || count == top3_2017 || count == top4_2017)
    			continue;
    		
        	if(count > top1_2017){
        		top4_2017= top3_2017;
        		top3_2017 = top2_2017;
        		top2_2017=top1_2017;
        		top1_2017=count;
        	}else if(count > top2_2017){
        		top4_2017= top3_2017;
        		top3_2017 = top2_2017;
        		top2_2017=count;
        	}else if(count > top3_2017){
        		top4_2017= top3_2017;
        		top3_2017 = count;
        	}else if(count > top4_2017){
        		top4_2017= count;
        	}
			
		}
        
        for (Entry<String, Map<String, Object>> entry : map_2016.entrySet()) {
        	
    		Map<String, Object> matchMap =entry.getValue();
    		int count = (int) matchMap.get("count");
    		
        	if(count == top4_2016)
        		top4Teams_2016.add((MatchesModal)matchMap.get("matchesModal"));
			
		}
        
        for (Entry<String, Map<String, Object>> entry : map_2017.entrySet()) {
        	
    		Map<String, Object> matchMap =entry.getValue();
    		int count = (int) matchMap.get("count");
    		
        	if(count == top4_2017)
        		top4Teams_2017.add((MatchesModal)matchMap.get("matchesModal"));
			
		}
        
        String tab = "                              ";
        String key1 = "YEAR";
        String key2 = "TEAM";
        String key3 = "COUNT";

        System.out.println("\n1).Top 4 teams which elected to field first after winning toss in the year 2016 and 2017.");
        System.out.println("----------------------------------------------------------------------------");
		System.out.println(key1 + tab + key2 + tab + key3);
        System.out.println("----------------------------------------------------------------------------");
        
        for (MatchesModal matchesModal : top4Teams_2016) {
        	
        	String year = matchesModal.getSEASON();
        	String teamName = matchesModal.getTOSS_WINNER();
        	
			year+=displaySpace(tab.length()+key1.length()-year.length());
        	teamName+=displaySpace(tab.length()+key2.length()-teamName.length());
			
			System.out.println(year + teamName + map_2016.get(matchesModal.getTOSS_WINNER()).get("count"));

		}
        for (MatchesModal matchesModal : top4Teams_2017) {
        	String year = matchesModal.getSEASON();
        	String teamName = matchesModal.getTOSS_WINNER();
        	
			year+=displaySpace(tab.length()+key1.length()-year.length());
        	teamName+=displaySpace(tab.length()+key2.length()-teamName.length());
			
			System.out.println(year + teamName + map_2016.get(matchesModal.getTOSS_WINNER()).get("count"));
//			System.out.println(matchesModal.getSEASON() + "\t\t\t" + matchesModal.getTOSS_WINNER()  + "\t\t\t" +  map_2017.get(matchesModal.getTOSS_WINNER()).get("count"));
		}
	}
	
	public void totalFoursSixesTotalScore() {
		//log.debug("totalFoursSixesTotalScore()..........");
		
		Map<String, Map<String, Map<String, Integer>>> scoreDetailsMap = new HashMap<>();
		Map<String, Map<String, Integer>> scoreDetailsYearMap = null;
		Map<String, Integer> deliveryMap = null;
		Integer fourCount = null;
		Integer sixCount = null;
		Integer totalRuns = null;
		
		
        for (DeliverModal deliverModal : deliverModalList) {
        	
        	MatchesModal matchesModal = matchesModalMap.get(deliverModal.getMATCH_ID());
        	
        	if(matchesModal == null)
        		throw new RuntimeException("matche not found MatchID:"+deliverModal.getMATCH_ID());
        	
    		scoreDetailsYearMap = scoreDetailsMap.get(matchesModal.getSEASON());
    		
    		if(scoreDetailsYearMap == null)
    			scoreDetailsYearMap = new HashMap<>();
    		
			deliveryMap = scoreDetailsYearMap.get(deliverModal.getBATTING_TEAM());
			
			if(deliveryMap == null)
				deliveryMap = new HashMap<>();
			
			fourCount = deliveryMap.get("fourCount");
			sixCount = deliveryMap.get("sixCount");
			totalRuns = deliveryMap.get("totalRuns");
			
			if(fourCount == null)
				fourCount = 0;
			
			if(sixCount == null)
				sixCount = 0;
			
			if(totalRuns == null)
				totalRuns = 0;
			
			Integer batsManRuns = Integer.parseInt(deliverModal.getBATSMAN_RUNS());
        	
        	if(batsManRuns == 4){
        		fourCount++;
        	}else if(batsManRuns == 6){
        		sixCount++;
        	}
        	
        	totalRuns+=Integer.parseInt(deliverModal.getTOTAL_RUNS());
        	
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
    			
    			year+=displaySpace(tab.length()+key1.length()-year.length());
    			teamName+=displaySpace(tab.length()+key2.length()-teamName.length());
    			String fourCountString =fourCount+displaySpace(tab.length()+key3.length()-fourCount.toString().length());
    			String sixCountString =sixCount+displaySpace(tab.length()+key4.length()-sixCount.toString().length());
    			String totalRunsString = totalRuns+displaySpace(tab.length()+key5.length()-totalRuns.toString().length());
    			
				System.out.println(year + teamName + fourCountString + sixCountString + totalRunsString);
    			
        	}
        	
		}
		
	}
	
	public String displaySpace(int size){
		
		String space = "";
		
		while(size>0){
			space+=" ";
			size--;
		}
		return space;
	}
	
	public void top10BestEconomy() {
		log.debug("top10BestEconomy()..........");
		
	}
	
	public void HighestRunRateTeam() {
		log.debug("HighestRunRateTeam()..........");
		
	}

}
