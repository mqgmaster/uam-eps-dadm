package es.uam.eps.dadm.mqg.minesweeper.database;

public class Match {

	public static final String TABLE_NAME = "match";
	
	public static final String ID = "_id";
	public static final String DATE = "date";
	public static final String PLAYER1_NAME = "player1_name";
	public static final String PLAYER1_TIME_SPENT = "player1_time_spent";
	public static final String PLAYER1_SCORE = "player1_score";
	public static final String PLAYER2_NAME = "player2_name";
	public static final String PLAYER2_TIME_SPENT = "player2_time_spent";
	public static final String PLAYER2_SCORE = "player2_score";
	
	private Integer id;
	private String date;
	private String player1_name;
	private Integer player1_time_spent;
	private Integer player1_score;
	private String player2_name;
	private Integer player2_time_spent;
	private Integer player2_score;
	
	public Match(Integer id, String date, String player1_name, Integer player1_time_spent,
			Integer player1_score, String player2_name,
			Integer player2_time_spent, Integer player2_score) {
		this.id = id;
		this.date = date;
		this.player1_name = player1_name;
		this.player1_score = player1_score;
		this.player1_time_spent = player1_time_spent;
		this.player2_name = player2_name;
		this.player2_score = player2_score;
		this.player2_time_spent = player2_time_spent;
	}
	
	public Match(String date, String player1_name, Integer player1_time_spent,
			Integer player1_score, String player2_name,
			Integer player2_time_spent, Integer player2_score) {
		this(null, date, player1_name, player1_time_spent, player1_score, player2_name, player2_time_spent, player2_score);
	}
	
	public Match(String player1_name, 
			Integer player1_score, String player2_name,
			Integer player2_score) {
		this(null, null, player1_name, null, player1_score, player2_name, null, player2_score);
	}
	
	public Integer getPlayer1_time_spent() {
		return player1_time_spent;
	}
	public void setPlayer1_time_spent(Integer player1_time_spent) {
		this.player1_time_spent = player1_time_spent;
	}
	public Integer getPlayer2_time_spent() {
		return player2_time_spent;
	}
	public void setPlayer2_time_spent(Integer player2_time_spent) {
		this.player2_time_spent = player2_time_spent;
	}
	public Integer getPlayer1_score() {
		return player1_score;
	}
	public void setPlayer1_score(Integer player1_score) {
		this.player1_score = player1_score;
	}
	public Integer getPlayer2_score() {
		return player2_score;
	}
	public void setPlayer2_score(Integer player2_score) {
		this.player2_score = player2_score;
	}
	public String getPlayer2_name() {
		return player2_name;
	}
	public void setPlayer2_name(String player2_name) {
		this.player2_name = player2_name;
	}
	public String getPlayer1_name() {
		return player1_name;
	}
	public void setPlayer1_name(String player1_name) {
		this.player1_name = player1_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	
}
