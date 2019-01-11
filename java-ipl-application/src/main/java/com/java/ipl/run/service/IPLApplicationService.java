package com.java.ipl.run.service;

import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.java.ipl.run.modal.DeliverModal;
import com.java.ipl.run.modal.MatchesModal;

/**
 * @author Rajkumar Banala 05-Jan-2019
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
