package routingapi;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class RoutingResponse {

	private double distance;
	private long timeInMs;
	private List<Double[]> pointList;
	
	public RoutingResponse(double distance,
			long timeInMs,
			List<Double[]> pointList) {
		this.distance = distance;
		this.timeInMs = timeInMs;
		this.pointList = pointList;
	}
	
	@ApiModelProperty(dataType = "double")
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@ApiModelProperty(dataType = "long")
	public long getTimeInMs() {
		return timeInMs;
	}

	public void setTimeInMs(long timeInMs) {
		this.timeInMs = timeInMs;
	}

	@ApiModelProperty(dataType = "double[]")
	public List<Double[]> getPointList() {
		return pointList;
	}

	public void setPointList(List<Double[]> pointList) {
		this.pointList = pointList;
	}	

}
