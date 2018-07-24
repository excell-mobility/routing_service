package restrouting.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class TravelTimesConnector {

	private HttpConnector connector;
	
	@Value("${url.traveltimeservice}")
	private String urlTravelTimeService;
	
	@Value("${url.traveltimeservice.auth}")
	private boolean requiresToken;
	
	@Value("${url.authlayer}")
	private String urlAuthLayer;

	@Value("${url.authlayer.user}")
	private String urlAuthLayerUser;

	@Value("${url.authlayer.pw}")
	private String urlAuthLayerUserPw;
	
	public TravelTimesConnector() {
		this.connector = new HttpConnector();
	}
	
	public void updateTravelTimesFromWebservice() {
		
		String result = null;
		
		// check time difference between last update
		long difference = System.currentTimeMillis() - TravelTimesWrapper.getTimestamp();
		if(difference / 1000 < 900) {
			return;
		}
		
		// update the caching time
		TravelTimesWrapper.updateTimestamp();
		
		try {
			if (requiresToken) {
				// get authentication token first
				String POST_PAYLOAD = "{" + "\"username\"" + ":" + "\"" + urlAuthLayerUser
						+ "\"" + "," + "\"password\"" + ":" + "\"" + urlAuthLayerUserPw + "\"" + "}";
				String token = "";
		
				try {
					String jsonResponse = connector.getConnectionAuthenticationString(urlAuthLayer, 
							POST_PAYLOAD);
					JSONObject tokenJSON = new JSONObject(jsonResponse);
					if(tokenJSON.has("token")) {
						token = tokenJSON.getString("token");
					} else {
						return;
					}
				} catch (KeyManagementException | NoSuchAlgorithmException
						| IOException e1) {
					e1.printStackTrace();
				}
				token = "Token " + token;
				
				try {
					result = connector.getConnectionStringWithToken(urlTravelTimeService, token);
				} catch (KeyManagementException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			else {
				result = connector.getConnectionString(urlTravelTimeService);
			}
		} catch (IOException e) {
			System.out.println("ERROR: Could not update graph with recent travel times: " + e.getClass().getSimpleName() + " - " +  e.getMessage());
		}
		
		if(result != null) {
			JSONArray json = new JSONArray(result);
		
			for(int index = 0; index < json.length(); index++) {
				
				JSONObject travelTimeJSONObject = (JSONObject) json.get(index);
				Integer id = !travelTimeJSONObject.getString("sid").isEmpty() 
						? Integer.decode(convertIdToGraphHopperID(travelTimeJSONObject.getString("sid")))
								: null;
				Integer nextId = !travelTimeJSONObject.getString("next_sid").isEmpty() 
						? Integer.decode(convertIdToGraphHopperID(travelTimeJSONObject.getString("next_sid")))
								: null;
				Integer travelTimeValue = travelTimeJSONObject.getInt("traveltime");
				boolean reverse = travelTimeJSONObject.getBoolean("reverse");
				
				// Case 1: only one detector and reverse is true
				if(nextId == null && reverse) {
					
					Map<Integer, Integer> travelTimesForDetectorReverseMap = 
							TravelTimesWrapper.getTravelTimesForDetectorReverseMap();
					travelTimesForDetectorReverseMap.put(id, travelTimeValue);
					
				}
				
				// Case 2: only one detector and reverse is false
				if(nextId == null && !reverse) {
					
					Map<Integer, Integer> travelTimesForDetectorMap = 
							TravelTimesWrapper.getTravelTimesForDetectorMap();
					travelTimesForDetectorMap.put(id, travelTimeValue);
					
				}
				
				// Case 3: for a relation with two identifiers
				if(id != null && nextId != null) {
					
					Map<Integer, Map<Integer, Integer>> travelTimesRelationMap = 
							TravelTimesWrapper.getTravelTimesRelationMap();
					if(travelTimesRelationMap.containsKey(id)) {
						travelTimesRelationMap.get(id).put(nextId, travelTimeValue);
					} else {
						HashMap<Integer, Integer> nextIdMap = Maps.newHashMap();
						nextIdMap.put(nextId, travelTimeValue);
						travelTimesRelationMap.put(id, nextIdMap);
					}
					
				}
				
			}
			
		}
		
	}

	private static String convertIdToGraphHopperID(String id) {
		
		return id.substring(id.indexOf("#") + 1, id.length());
		
	}

}
