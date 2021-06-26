package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public void listAllPlayers(Map<Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idMap.containsKey(res.getInt("PlayerID"))) {
					Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
					idMap.put(res.getInt("PlayerID"), player);
				}
				
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getVertices(double goal, Map<Integer, Player> idMap){
		String sql = "SELECT P.PlayerID AS id "
				+ "FROM Players P, Actions A "
				+ "WHERE A.PlayerID = P.PlayerID "
				+ "GROUP BY P.PlayerID, P.Name "
				+ "HAVING AVG(A.Goals) > ?";
		Connection conn = DBConnect.getConnection();
		List<Player> result = new ArrayList<>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, goal);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(idMap.containsKey(res.getInt("id"))) {
					result.add(idMap.get(res.getInt("id")));
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}
	
	public List<Adiacenza> getArchi(Map<Integer, Player> idMap){
		String sql = "SELECT A1.PlayerID AS p1, A2.PlayerID AS p2, (SUM(A1.TimePlayed) - SUM(A2.TimePlayed)) AS peso "
				+ "	FROM 	Actions A1, Actions A2 "
				+ "	WHERE A1.TeamID != A2.TeamID "
				+ "	AND A1.MatchID = A2.MatchID "
				+ "	AND A1.starts = 1 AND A2.starts = 1 "
				+ "	AND A1.PlayerID > A2.PlayerID "
				+ "	GROUP BY A1.PlayerID,A2.PlayerID";
		Connection conn = DBConnect.getConnection();
		List<Adiacenza> result = new ArrayList<>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(idMap.containsKey(res.getInt("p1")) && idMap.containsKey(res.getInt("p2"))) {
					Adiacenza a = new Adiacenza (idMap.get(res.getInt("p1")), idMap.get(res.getInt("p2")), res.getDouble("peso"));
					result.add(a);
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		
		
	}
	
	
}
