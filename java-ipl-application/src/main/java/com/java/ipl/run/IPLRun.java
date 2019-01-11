/**
 * 
 */
package com.java.ipl.run;

import com.java.ipl.run.service.IPLApplicationService;
import com.java.ipl.run.service.IPLApplicationServiceImpl;

/**
 * @author Rajkumar Banala 05-Jan-2019
 */

public class IPLRun {

    public static void main(String[] args) {

        IPLApplicationService iplApplicationService = new IPLApplicationServiceImpl();

        iplApplicationService.readMatchesData();
        iplApplicationService.readDeliverData();
        iplApplicationService.top4TeamsElectedField();
        iplApplicationService.totalFoursSixesTotalScore();
        iplApplicationService.top10BestEconomy();
        iplApplicationService.HighestRunRateTeam();
    }
    
}
