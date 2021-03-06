package unq.tpi.desapp.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import unq.tpi.desapp.model.Inscription;
import unq.tpi.desapp.model.Product;
import unq.tpi.desapp.model.Route;
import unq.tpi.desapp.model.User;
import unq.tpi.desapp.model.Vehicle;
import unq.tpi.desapp.model.manager.CommentManager;
import unq.tpi.desapp.model.manager.ScoreManager;
import unq.tpi.desapp.services.UserService;
import unq.tpi.desapp.services.request.CommentRequest;
import unq.tpi.desapp.services.request.CommentedPointRequest;
import unq.tpi.desapp.services.request.RequestRoute;
import unq.tpi.desapp.services.request.UserProfile;

@Path("/users")
public class UserRest {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public List<User> getUsers() {
		return getUserService().retriveAll();
	}

	@GET
	@Path("/top")
	@Produces("application/json")
	public List<UserProfile> getTop() {
		List<User> users = getUserService().retriveAll();
		Collections.sort(users, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.managerImplementing(ScoreManager.class).getScore()
						.compareTo(o2.managerImplementing(ScoreManager.class).getScore());
			}
		});
		Collections.reverse(users);
		List<UserProfile> usersProfiles = new ArrayList<UserProfile>();
		for(int i = 0; i < users.size(); i++){
			if(i < 5){
				usersProfiles.add(new UserProfile(users.get(i)));
			}
		}
		return usersProfiles;
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public User getUser(@PathParam("id") final Long id) {
		User user = getUserService().getUser(id);
		user.removeManager(CommentManager.class);
		return user;
	}

	@GET
	@Path("/{id}/vehicles/{page}")
	@Produces("application/json")
	public List<Vehicle> getVehicles(@PathParam("id") final Long id, @PathParam("page") final Integer page) {
		return getUserService().getVehicles(id, page, 10);
	}

	@GET
	@Path("/{id}/allvehicles")
	@Produces("application/json")
	public List<Vehicle> getAllVehicles(@PathParam("id") final Long id) {
		return getUserService().getAllVehicles(id);
	}

	@GET
	@Path("/{id}/howMuchVehicles")
	@Produces("application/json")
	public Integer howMuchVehicles(@PathParam("id") final Long id) {
		return getUserService().getCountVehiclesFor(id, 10);
	}

	@POST
	@Path("/{id}/newvehicle")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVehicle(@PathParam("id") final Long id, Vehicle vehicle) {
		try {
			getUserService().addNewVehicle(id, vehicle);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/like/{userName}")
	@Produces("application/json")
	public List<User> getUsersLike(@PathParam("userName") final String userName) {
		return getUserService().getUsersLike(userName);
	}

	@POST
	@Path("/{id}/newroute")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRoute(@PathParam("id") final Long id, RequestRoute requestRoute) {
		try {
			getUserService().addNewRoute(id, requestRoute);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/routes/{page}")
	@Produces("application/json")
	public List<Route> getRoutes(@PathParam("id") final Long id, @PathParam("page") final Integer page) {
		return getUserService().getRoutes(id, page, 10);
	}

	@GET
	@Path("/{id}/howMuchMyRoutes")
	@Produces("application/json")
	public Integer howMuchMyRoutes(@PathParam("id") final Long id) {
		return getUserService().getCountMyRoutesFor(id, 10);
	}

	@POST
	@Path("/{id}/newproduct")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam("id") final Long id, Product product) {
		try {
			getUserService().addNewProduct(id, product);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/products/{page}")
	@Produces("application/json")
	public List<Product> getProducts(@PathParam("id") final Long id, @PathParam("page") final Integer page) {
		return getUserService().getProducts(id, page, 10);
	}

	@GET
	@Path("/{id}/howMuchProducts")
	@Produces("application/json")
	public Integer howMuchProducts(@PathParam("id") final Long id) {
		return getUserService().getCountProductsFor(id, 10);
	}

	@POST
	@Path("/{id}/suscribeRoute/{idRoute}/from/{idOwner}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribeToRoute(@PathParam("id") final Long id, @PathParam("idRoute") final Long idRoute,
			@PathParam("idOwner") final Long idOwner) {
		try {
			getUserService().subscribeToRoute(id, idRoute, idOwner);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/{id}/removeproduct")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeProduct(@PathParam("id") final Long id, Product product) {
		try {
			getUserService().removeProduct(id, product);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/profile/{id}")
	@Produces("application/json")
	public UserProfile getProfile(@PathParam("id") final Long id) {
		return getUserService().getUserProfile(id);
	}

	@POST
	@Path("/rate/{id}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rateUser(@PathParam("id") final Long id, CommentedPointRequest commentedPointWithId) {
		try {
			getUserService().rateUser(id, commentedPointWithId);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/commentedPoints/{page}")
	@Produces("application/json")
	public List<CommentedPointRequest> getCommentedPoints(@PathParam("id") final Long id,
			@PathParam("page") final Integer page) {
		return getUserService().getCommentedPointRequests(id, page, 20);
	}

	@POST
	@Path("/comment/{id}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response commentUser(@PathParam("id") final Long id, CommentRequest commentRequest) {
		try {
			getUserService().commentUser(id, commentRequest);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/comments/{page}")
	@Produces("application/json")
	public List<CommentRequest> getCommentRequests(@PathParam("id") final Long id,
			@PathParam("page") final Integer page) {
		return getUserService().getCommentRequests(id, page, 20);
	}

	@POST
	@Path("{id}/route/{routeID}/denyRequest/{subscriptionID}")
	@Produces("application/json")
	public Response denyRequest(@PathParam("id") final Long id, @PathParam("routeID") final Long routeID,
			@PathParam("subscriptionID") final Long subscriptionID) {
		try {
			getUserService().denyRequest(id, routeID, subscriptionID);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("{id}/route/{routeID}/acceptedRequest/{subscriptionID}")
	@Produces("application/json")
	public Response acceptedRequest(@PathParam("id") final Long id, @PathParam("routeID") final Long routeID,
			@PathParam("subscriptionID") final Long subscriptionID) {
		try {
			getUserService().acceptedRequest(id, routeID, subscriptionID);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/inscriptions/{page}")
	@Produces("application/json")
	public List<Inscription> getInscriptions(@PathParam("id") final Long id, @PathParam("page") final Integer page) {
		return getUserService().getInscriptions(id, page, 10);
	}

	@GET
	@Path("/{id}/howMuchInscriptions")
	@Produces("application/json")
	public Integer howMuchInscriptions(@PathParam("id") final Long id) {
		return getUserService().getCountInscriptionsFor(id, 10);
	}

}
