package edu.toronto.dbservice.types;

import java.io.Serializable;

public class RankedProposal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pName;
	private String pRank;
	
	public RankedProposal(String proposalName, String proposalRank) {
		pName = proposalName;
		pRank = proposalRank;
	}
	
	public String getProposalName() {
		return pName;
	}
	
	public String getProposalRank() {
		return pRank;
	}

}