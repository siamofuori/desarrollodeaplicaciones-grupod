package unq.tpi.desapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import unq.tpi.desapp.model.Route;
import unq.tpi.desapp.model.User;
import unq.tpi.desapp.model.Vehicle;
import unq.tpi.desapp.model.manager.RouteManager;
import unq.tpi.desapp.model.manager.VehicleManager;

public class UserService extends GenericService<User> {

	@Transactional
	public void addRouteToUser(User user, Route route) {
		user.managerImplementing(RouteManager.class).add(route);
		this.update(user);
	}

	@Transactional
	public User getUser(Long id) {
		return this.getRepository().findById(id);
	}

	@Transactional
	public List<Vehicle> getVehicles(Long id, Integer page, Integer quantity) {
		User user = this.getRepository().findById(id);
		return new ArrayList<Vehicle>(user.managerImplementing(VehicleManager.class).getVehicles());
	}
	
	@Transactional
	public void addNewVehicle(Long id, Vehicle vehicle) throws Exception {
		User user = this.getRepository().findById(id);
		user.managerImplementing(VehicleManager.class).add(vehicle);
		this.update(user);
	}
}
