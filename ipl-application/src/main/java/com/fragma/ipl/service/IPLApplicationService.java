package com.fragma.ipl.service;

import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.fragma.ipl.modal.DeliverModal;
import com.fragma.ipl.modal.MatchesModal;

/**
 * @author Bhavani Boda 05-Jan-2019
 *
 * 
 */

public interface IPLApplicationService {

	public List<MatchesModal> readMatchesData();

	public List<DeliverModal> readDeliverData();

	public CellProcessor[] getCellProcessors(int size);

	public void top4TeamsElectedField();

	public void totalFoursSixesTotalScore();

	public void top10BestEconomy();

	public void HighestRunRateTeam();

}
