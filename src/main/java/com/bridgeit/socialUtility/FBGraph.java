package com.bridgeit.socialUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class FBGraph {
	Logger logger = Logger.getLogger(FBGraph.class);
	private String accessToken;

	public FBGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return
	 * @throws IOException
	 *             retrieve fbProfile in a String format
	 */
	public String getFBGraph() throws IOException {
		String graph = null;

		String g = "https://graph.facebook.com/me?access_token=" + accessToken;
		URL u = new URL(g);
		URLConnection c = u.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		String inputLine;
		StringBuffer b = new StringBuffer();
		while ((inputLine = in.readLine()) != null)
			b.append(inputLine + "\n");
		in.close();
		graph = b.toString();
		logger.debug("Graph String is: " + graph);
		return graph;
	}

	/**
	 * @param fbGraph
	 * @return convert the fb graph data from string to JSON
	 */
	public Map<String, String> getGraphData(String fbGraph) {
		Map<String, String> fbProfile = new HashMap<>();
		try {
			JSONObject json = new JSONObject(fbGraph);
			fbProfile.put("id", json.getString("id"));
			fbProfile.put("name", json.getString("name"));
			if (json.has("email"))
				fbProfile.put("email", json.getString("email"));
			if (json.has("gender"))
				fbProfile.put("gender", json.getString("gender"));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		logger.debug("fbProfile is: " + fbProfile);
		return fbProfile;
	}
}
