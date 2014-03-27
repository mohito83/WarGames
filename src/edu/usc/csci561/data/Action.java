package edu.usc.csci561.data;

public enum Action {
	PARATROOOP_DROP("Paratroop Drop"), FORCED_MARCH("Forced March");
	private String val;

	private Action(String str) {
		this.val = str;
	}

	public String getValue() {
		return this.val;
	}
}
