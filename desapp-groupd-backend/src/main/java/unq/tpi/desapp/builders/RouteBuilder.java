package unq.tpi.desapp.builders;

import java.util.ArrayList;
import java.util.List;

import unq.tpi.desapp.model.Route;
import unq.tpi.desapp.model.Routine;
import unq.tpi.desapp.model.SubscriptionRequest;
import unq.tpi.desapp.model.Vehicle;

public class RouteBuilder {

	private String startPoint;
	private String endPoint;
	private Routine routine;
	private Vehicle vehicle;
	private List<SubscriptionRequest> subscriptionRequests;

	public RouteBuilder() {
		startPoint = "Roque Sáenz Peña 352";
		endPoint = "Roque Sáenz Peña 150";
		routine = new RoutineBuilder().build();
		vehicle = new VehicleBuilder().build();
		subscriptionRequests = new ArrayList<SubscriptionRequest>();
	}

	public RouteBuilder setStartingPoint(String startingPoint) {
		startPoint = startingPoint;
		return this;
	}

	public RouteBuilder setEndingPoint(String endingingPoint) {
		endPoint = endingingPoint;
		return this;
	}

	public RouteBuilder setRoutine(Routine routine) {
		this.routine = routine;
		return this;
	}

	public RouteBuilder setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		return this;
	}

	public RouteBuilder setSubscriptionRequests(List<SubscriptionRequest> subscribeRequests) {
		this.subscriptionRequests = subscribeRequests;
		return this;
	}

	public Route build() {
		return new Route(startPoint, endPoint, vehicle, routine, subscriptionRequests);
	}

}
