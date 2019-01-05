package com.fragma.ipl.modal;

/**
 * @author Bhavani Boda 05-Jan-2019
 *
 * 
 */

public class DeliverModal {

	String MATCH_ID;

	String INNING;

	String BATTING_TEAM;

	String BOWLING_TEAM;

	String OVER;

	String BALL;
	
	String BATSMAN;
	
	String BOWLER;
	
	String WIDE_RUNS;
	
	String BYE_RUNS;
	
	String LEGBYE_RUNS;
	
	String NOBALL_RUNS;
	
	String PENALTY_RUNS;
	
	String BATSMAN_RUNS;
	
	String EXTRA_RUNS;
	
	String TOTAL_RUNS;

	// setters and getters
	public String getMATCH_ID() {
		return MATCH_ID;
	}

	public void setMATCH_ID(String mATCH_ID) {
		MATCH_ID = mATCH_ID;
	}

	public String getINNING() {
		return INNING;
	}

	public void setINNING(String iNNING) {
		INNING = iNNING;
	}

	public String getBATTING_TEAM() {
		return BATTING_TEAM;
	}

	public void setBATTING_TEAM(String bATTING_TEAM) {
		BATTING_TEAM = bATTING_TEAM;
	}

	public String getBOWLING_TEAM() {
		return BOWLING_TEAM;
	}

	public void setBOWLING_TEAM(String bOWLING_TEAM) {
		BOWLING_TEAM = bOWLING_TEAM;
	}

	public String getOVER() {
		return OVER;
	}

	public void setOVER(String oVER) {
		OVER = oVER;
	}

	public String getBALL() {
		return BALL;
	}

	public void setBALL(String bALL) {
		BALL = bALL;
	}

	public String getBATSMAN() {
		return BATSMAN;
	}

	public void setBATSMAN(String bATSMAN) {
		BATSMAN = bATSMAN;
	}

	public String getBOWLER() {
		return BOWLER;
	}

	public void setBOWLER(String bOWLER) {
		BOWLER = bOWLER;
	}

	public String getWIDE_RUNS() {
		return WIDE_RUNS;
	}

	public void setWIDE_RUNS(String wIDE_RUNS) {
		WIDE_RUNS = wIDE_RUNS;
	}

	public String getBYE_RUNS() {
		return BYE_RUNS;
	}

	public void setBYE_RUNS(String bYE_RUNS) {
		BYE_RUNS = bYE_RUNS;
	}

	public String getLEGBYE_RUNS() {
		return LEGBYE_RUNS;
	}

	public void setLEGBYE_RUNS(String lEGBYE_RUNS) {
		LEGBYE_RUNS = lEGBYE_RUNS;
	}

	public String getNOBALL_RUNS() {
		return NOBALL_RUNS;
	}

	public void setNOBALL_RUNS(String nOBALL_RUNS) {
		NOBALL_RUNS = nOBALL_RUNS;
	}

	public String getPENALTY_RUNS() {
		return PENALTY_RUNS;
	}

	public void setPENALTY_RUNS(String pENALTY_RUNS) {
		PENALTY_RUNS = pENALTY_RUNS;
	}

	public String getBATSMAN_RUNS() {
		return BATSMAN_RUNS;
	}

	public void setBATSMAN_RUNS(String bATSMAN_RUNS) {
		BATSMAN_RUNS = bATSMAN_RUNS;
	}

	public String getEXTRA_RUNS() {
		return EXTRA_RUNS;
	}

	public void setEXTRA_RUNS(String eXTRA_RUNS) {
		EXTRA_RUNS = eXTRA_RUNS;
	}

	public String getTOTAL_RUNS() {
		return TOTAL_RUNS;
	}

	public void setTOTAL_RUNS(String tOTAL_RUNS) {
		TOTAL_RUNS = tOTAL_RUNS;
	}

	@Override
	public String toString() {
		return "DeliverModal [MATCH_ID=" + MATCH_ID + ", INNING=" + INNING + ", BATTING_TEAM=" + BATTING_TEAM
				+ ", BOWLING_TEAM=" + BOWLING_TEAM + ", OVER=" + OVER + ", BALL=" + BALL + ", BATSMAN=" + BATSMAN
				+ ", BOWLER=" + BOWLER + ", WIDE_RUNS=" + WIDE_RUNS + ", BYE_RUNS=" + BYE_RUNS + ", LEGBYE_RUNS="
				+ LEGBYE_RUNS + ", NOBALL_RUNS=" + NOBALL_RUNS + ", PENALTY_RUNS=" + PENALTY_RUNS + ", BATSMAN_RUNS="
				+ BATSMAN_RUNS + ", EXTRA_RUNS=" + EXTRA_RUNS + ", TOTAL_RUNS=" + TOTAL_RUNS + "]";
	}
}
