package com.fragma.ipl.modal;

/**
 * @author Bhavani Boda 05-Jan-2019
 *
 * 
 */

public class MatchesModal {

	String MATCH_ID;

	String SEASON;
	
	String CITY;
	
	String DATE;
	
	String TEAM1;
	
	String TEAM2;
	
	String TOSS_WINNER;
	
	String TOSS_DECISION;
	
	String RESULT;
	
	String WINNER;

	// setters and getters
	public String getMATCH_ID() {
		return MATCH_ID;
	}

	public void setMATCH_ID(String mATCH_ID) {
		MATCH_ID = mATCH_ID;
	}

	public String getSEASON() {
		return SEASON;
	}

	public void setSEASON(String sEASON) {
		SEASON = sEASON;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public String getTEAM1() {
		return TEAM1;
	}

	public void setTEAM1(String tEAM1) {
		TEAM1 = tEAM1;
	}

	public String getTEAM2() {
		return TEAM2;
	}

	public void setTEAM2(String tEAM2) {
		TEAM2 = tEAM2;
	}

	public String getTOSS_WINNER() {
		return TOSS_WINNER;
	}

	public void setTOSS_WINNER(String tOSS_WINNER) {
		TOSS_WINNER = tOSS_WINNER;
	}

	public String getTOSS_DECISION() {
		return TOSS_DECISION;
	}

	public void setTOSS_DECISION(String tOSS_DECISION) {
		TOSS_DECISION = tOSS_DECISION;
	}

	public String getRESULT() {
		return RESULT;
	}

	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}

	public String getWINNER() {
		return WINNER;
	}

	public void setWINNER(String wINNER) {
		WINNER = wINNER;
	}

	@Override
	public String toString() {
		return "MatchesModal [MATCH_ID=" + MATCH_ID + ", SEASON=" + SEASON + ", CITY=" + CITY + ", DATE=" + DATE
				+ ", TEAM1=" + TEAM1 + ", TEAM2=" + TEAM2 + ", TOSS_WINNER=" + TOSS_WINNER + ", TOSS_DECISION="
				+ TOSS_DECISION + ", RESULT=" + RESULT + ", WINNER=" + WINNER + "]";
	}
	
}
