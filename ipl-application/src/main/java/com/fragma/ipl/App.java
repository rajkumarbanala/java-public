package com.fragma.ipl;

import com.fragma.ipl.service.IPLApplicationService;
import com.fragma.ipl.service.IPLApplicationServiceImpl;

/**
 * @author Bhavani Boda 05-Jan-2019
 *
 * 
 */

public class App {
	public static void main(String[] args) {

		IPLApplicationService iplApplicationService = new IPLApplicationServiceImpl();

		iplApplicationService.readMatchesData();
		iplApplicationService.readDeliverData();
		iplApplicationService.top4TeamsElectedField();
		iplApplicationService.totalFoursSixesTotalScore();

	}
}
