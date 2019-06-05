package businessLogic;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Result;
import domain.Sport;
import domain.User;
import domain.UserContainer;
import domain.UserSingleton;
import domain.VWallet;

import domain.Bet;
import domain.Category;
import domain.Event;
import domain.EventContainer;
import domain.Movement;
import domain.MovementContainer;
import domain.PossibleBet;
import domain.PossibleBetContainer;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import sportsdbConnection.JsonReader;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	
	
	private UserSingleton user = UserSingleton.getInstance();
	private Date date = null;
	private DataAccess dbG;
	private List<PossibleBet> currentBets;
	private Bet b;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.close();
		}
	}

	
	@Override
	public boolean betIsEmpty() {
		return (currentBets == null || currentBets.isEmpty()) ? true : false;
	}

	
	@Override
	public boolean changePassword(String userName, String password) {
		DataAccess db = new DataAccess();
		boolean changed = db.changePassword(userName, password);
		db.close();
		return changed;
	}


	@Override
	public boolean changeUserName(String current, String newUN) {
		DataAccess db = new DataAccess();
		if (!existsUser(newUN)) {
			db.changeUserName(current, newUN);
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}


	@Override
	public boolean checkCredentials(String userName, String passWord) {
		boolean b = false;
		DataAccess db = new DataAccess();
		String pwd = db.getUserPassword(userName);
		if (pwd != null && pwd.equals(db.hash(passWord)))
			b = true;
		db.close();
		return b;

	}


	@Override
	public void clearCurrentBets() {
		currentBets = null;
		b = null;
	}

	
	@Override
	public void closeDB() {
		if (dbG != null)
			dbG.close();
	}


	@Override
	public boolean createAdmin() {
		return new DataAccess().createAdmin();
	}


	@Override
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		DataAccess dBManager = new DataAccess();
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dBManager.createQuestion(event, question, betMinimum);

		dBManager.close();

		return qry;
	}


	@Override
	public void createUser(String userName, String password) {
		DataAccess db = new DataAccess();
		db.createUser(userName, password);
		db.close();
	}


	@Override
	public void createUserSingleton(String userName) {
		DataAccess db = new DataAccess();
		User u = db.getUser(userName);

		db.close();
		if (u != null) {
			user.setId(u.getId());
			user.setPassword(u.getPassword());
			user.setUserName(u.getUserName());
			user.setWallet(u.getWallet());
			user.setAdmin(u.isAdmin());
		}
	}


	@Override
	public boolean decideBets(int id) {
		return new DataAccess().decideBets(id);
		
	}


	@Override
	public void deleteBet(String string) {
		Iterator<PossibleBet> it = currentBets.iterator();
		PossibleBet pb ;
		while(it.hasNext()) {
			pb = it.next();
			System.out.println(pb.toString());
			if (pb.getRelatedEvent().getEventID().equals(Integer.parseInt(string)) ){
				System.out.println(pb.getRelatedEvent().getEventID() + " equals " + string);
				currentBets.remove(pb);
				if ( currentBets.contains(pb) ) System.out.println("LIST CONTAIS THE VALUE");
				break;
			}
		}
	}


	@Override
	public boolean deleteEvent(String eve) {
		boolean deleted;
		DataAccess db = new DataAccess();
		deleted = db.deleteEvent(eve);
		db.close();
		return deleted;
	}


	@Override
	public boolean deleteUser(String ide) {
		boolean deleted;
		DataAccess db = new DataAccess();
		deleted = db.deleteUser(ide);
		db.close();
		return deleted;
	}


	@Override
	public boolean existsAdmin() {
		DataAccess db = new DataAccess();
		return db.existsAdmin();
	}


	@Override
	public boolean existsUser(String userName) {
		DataAccess db = new DataAccess();
		if (db.getUser(userName) != null)
			return true;
		else
			return false;

	}


	@Override
	public JSONArray getAvailableSportLeagues() {
		JSONObject leaguesJson;
		JSONArray array = null;
		try {
			leaguesJson = JsonReader.getJsonFromURL("https://www.thesportsdb.com/api/v1/json/1/all_leagues.php");
			array = leaguesJson.getJSONArray("leagues");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	//////// VWALLET /////////


	@Override
	public String getBalance() {
		return Double.toString(user.getWallet().getBalance());
	}


	@Override
	public Bet getBetByID(String id) {
		List<Bet> us = getBetsLike("betId", id);
		return us.get(0);
	}


	@Override
	public List<Bet> getBetList(String choice) {
		DataAccess db = new DataAccess();
		List<Bet> lista;

		if (choice == null) {
			lista = db.getAllBets();
		} else {
			lista = db.getBetsOrdered(choice);
		}

		db.close();
		return lista;
	}


	@Override
	public List<Bet> getBetsLike(String where, String like) {
		DataAccess db = new DataAccess();
		List<Bet> lista;
		lista = db.getBetsLike(where, like);
		db.close();
		return lista;
	}


	@Override
	public List<Category> getCategoriesList() {
		DataAccess dbManager = new DataAccess();
		List<Category> categories = dbManager.getCategories();
		return categories;
	}


	@Override
	public String[] getCategoriesArray(String sportID) {
		DataAccess dbManager = new DataAccess();
		List<Category> categories = dbManager.getCategories(sportID);

		if (categories == null) {
			String[] resul = { "No categories found" };
			return resul;
		}
		String[] resul = new String[categories.size()];
		int i = 0;
		for (Category c : categories) {
			resul[i] = c.getName();
			i++;
		}
		return resul;
	}


	@Override
	public Category getCategoryById(int catId) {
		DataAccess db = new DataAccess();
		Category c = db.getCategoryById(catId);
		db.close();
		return c;

	}
	

	@Override
	public String getCategoryByName(String categoryName) {

		return null;
	}


	@Override
	public List<Event> getEventByBet(String ide) {
		return new DataAccess().getEventByBet(ide);
	}


	@Override
	public Event getEventByID(String id) {
		List<Event> us = getEventsLike("eventID", id);
		return us.get(0);
	}


	@Override
	public List<Event> getEventList(String choice) {
		DataAccess db = new DataAccess();
		List<Event> lista;

		if (choice == null) {
			lista = db.getAllEvents();
		} else {
			lista = db.getEventsOrdered(choice);
		}

		db.close();
		return lista;
	}


	@Override
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		DataAccess dbManager = new DataAccess();
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	
	@Override
	public List<EventContainer> getEventsByCategory(String categoryName) {
		Category c = new DataAccess().getCategoryByName(categoryName);
		List<EventContainer> resul = new LinkedList<EventContainer>();
		for(Event ev : c.getEvents()) resul.add(new EventContainer(ev));
		return (c != null) ? resul : null;
	}


	@Override
	public List<Event> getEventsBySportCategory(int categoryID, Date date, int sportID) {
		DataAccess dbManager = new DataAccess();
		List<Event> resul = new LinkedList<Event>();
		List<Event> events = dbManager.getEventsBySportCategory(sportID, categoryID);

		for (Event ev : events) {
			if (ev.getEventDate().equals(dbManager.newDate(2019, 3, 1))) {
				resul.add(ev);
			}
		}
		return resul;
	}


	@Override
	public List<Event> getEventsLike(String where, String like) {
		DataAccess db = new DataAccess();
		List<Event> lista;
		lista = db.getEventsLike(where, like);
		db.close();
		return lista;
	}


	@Override
	public ArrayList<Movement> getLastMovements() {
		DataAccess db = new DataAccess();
		ArrayList<Movement> lastMovements = new ArrayList<Movement>();
		lastMovements = db.getLastMovements(user.getWallet());
		db.close();
		
		return lastMovements;
	}


	@Override
	public Movement getMovementByID(String iden) {
		List<MovementContainer> mov = getMovementsLike("id", iden);
		return mov.get(0).getMovement();
	}


	@Override
	public List<Movement> getMovementList(String choice) {
		DataAccess db = new DataAccess();
		List<Movement> lista;

		if (choice == null) {
			lista = db.getAllMovements();
		} else {
			lista = db.getMovementsSorted(choice);
		}

		db.close();
		return lista;
	}


	@SuppressWarnings("null")
	@Override
	public List<MovementContainer> getMovementsLike(String where, String like) {
		DataAccess db = new DataAccess();
		List<Movement> list = new ArrayList<Movement>();
		List<MovementContainer> moco = null;
		list = db.getMovementsLike(where, like);
		
		for(Movement mov : list) moco.add(new MovementContainer(mov));
		db.close();
		return moco;
	}


	@Override
	public JSONObject getNextEventsFromLeague(int leagueId) {
		JSONObject ev = null;
		try {
			ev = JsonReader
					.getJsonFromURL("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=" + leagueId);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return ev;
	}

////////ADMINISTRATOR ////////

	@Override
	public String getPoints() {
		return Double.toString(user.getWallet().getBalance() * 10);
	}


	@Override
	@WebMethod
	public Question getQuestionFromEventAs(Event ev, String que) {
		DataAccess dbManager = new DataAccess();
		Question qu = dbManager.getQuestionFromEventAs(ev, que);
		dbManager.close();
		return qu;
	}


	@Override
	@WebMethod
	public List<Question> getQuestions(Event event) {
		List<Question> queList;
		DataAccess dbManager = new DataAccess();
		queList = dbManager.getQuestions(event);
		dbManager.close();
		return queList;
	}


	@Override
	public User getSavedUser() {
		DataAccess db = new DataAccess();
		User u = db.getSavedUser();
		db.close();
		return u;
	}


	@Override
	public List<String> getUserBets() {
		List<String> result = new LinkedList<String>();
		DataAccess dbM = new DataAccess();
		String username = user.getUserName();
		User u = dbM.getUser(username);
		VWallet vw = u.getWallet();
		List<Bet> userBets = vw.getBets();
		if (userBets == null || userBets.isEmpty())
			return null;
		for (Bet bet : userBets) {
			result.add(bet.toString());
			List<PossibleBet> pb = bet.getBets();
			for (PossibleBet possibleBet : pb) {
				result.add("' ---->" + possibleBet.toString());
			}
			result.add("");
		}
		return result;
	}


	@Override
	public UserContainer getUserByID(String id) {
		List<UserContainer> us = getUsersLike("id", id);
		return us.get(0);
	}


	@Override
	public User getUserByWalletID(String id) {
		List<UserContainer> us = getUsersLike("wallet.id", id);
		return us.get(0).getUser();
	}


	@Override
	public List<UserContainer> getUserList(String choice) {
		DataAccess db = new DataAccess();
		List<User> lista;

		if (choice == null) {
			lista = db.getAllUsers();
		} else {
			lista = db.getUsersOrdered(choice);
		}
		
		ArrayList<UserContainer> uco = new ArrayList<UserContainer>();
		for(User u: lista) uco.add(new UserContainer(u));

		db.close();
		return uco;
	}


	@SuppressWarnings("null")
	@Override
	public List<UserContainer> getUsersLike(String where, String like) {
		DataAccess db = new DataAccess();
		List<User> lista = db.getUsersLike(where, like);
		List<UserContainer> usCont = null;
		for(User con: lista) usCont.add(new UserContainer(con));
		
		db.close();
		return usCont;
	}


	@Override
	public VWallet getWalletByBetID(String id) {
		DataAccess db = new DataAccess();
		List<VWallet> us = db.getWalletsLike("id", id);
		db.close();
		return us.get(0);
	}


	@Override
	public VWallet getWalletByID(String id) {
		DataAccess db = new DataAccess();
		List<VWallet> us = db.getWalletsLike("id", id);
		db.close();
		return us.get(0);
	}


	@Override
	public void insertCategoryEvents(Category category) {
		DataAccess db = new DataAccess();
		db.updateCategory(category);
		db.close();
	}


	@Override
	public void loadEventsFromAPI(JSONArray array, int i) throws JSONException, IOException, ParseException {
		JSONObject categoryInfo = array.getJSONObject(i);
		int categoryId = Integer.parseInt(categoryInfo.getString("idLeague"));
		String sportId = categoryInfo.getString("strSport");
		List<Event> categoryEvs = new ArrayList<>();
		JSONObject ev = JsonReader
				.getJsonFromURL("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=" + categoryId);

		if (ev.get("events") == JSONObject.NULL)
			return;
		JSONArray evs = ev.getJSONArray("events"); // All the events from the category

		// check if all the events are already stored. The speedup is not high.
		// (Erlantz)
		int lastEventId = Integer.parseInt(evs.getJSONObject(evs.length() - 1).getString("idEvent"));
		if (dbG.getEventById(lastEventId) != null)
			return;

		Category category = dbG.getCategoryById(categoryId);
		HashMap<Integer, Event> categoryEvents = new HashMap<>();
		if (category == null) {
			String catName = categoryInfo.getString("strLeague");
			category = new Category(catName, categoryId, new ArrayList<Event>());
		} else {
			for (Event e : category.getEvents())
				categoryEvents.put(e.getEventNumber(), e);
		}

		category.setSportID(sportId);

		System.out.println("Loading category with id: " + categoryId);

		for (int j = evs.length() - 1; j >= 0; j--) {
			if (!categoryEvents.containsKey(Integer.parseInt(evs.getJSONObject(j).getString("idEvent")))) {
				/* If the event is not in the db */

				int eventId = Integer.parseInt(evs.getJSONObject(j).getString("idEvent"));
				String description = evs.getJSONObject(j).getString("strEvent");

				Date evtDate = new Date();
				if (evs.getJSONObject(j).get("strDate") != JSONObject.NULL
						&& evs.getJSONObject(j).get("strTime") != JSONObject.NULL) {
					String date = evs.getJSONObject(j).getString("strDate") + " "
							+ evs.getJSONObject(j).getString("strTime").split("\\+")[0];
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
					evtDate = dateFormat.parse(date);
				} else if (evs.getJSONObject(j).get("dateEvent") != JSONObject.NULL) {
					String date = evs.getJSONObject(j).getString("dateEvent");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					evtDate = dateFormat.parse(date);
				}
				List<Result> result = resultsFromSport(sportId);
				Event e = new Event(eventId, description, evtDate, result, (result.size() == 3), sportId, categoryId);
				dbG.storeEvent(e);
				categoryEvs.add(0, e);

			} else {
				break;
			}
		}

		Sport sport = dbG.getSportById(sportId);

		if (sport == null) {
			sport = new Sport(sportId, new ArrayList<Category>());
		}
		if (!sport.hasCategory(categoryId))
			sport.addCategory(category);

		// if there are new events add to the db
		if (!categoryEvs.isEmpty()) {
			categoryEvs.addAll(category.getEvents());
			category.setEvents(categoryEvs);
			// for (Event e : categoryEvs) dbG.storeEvent(e);
			dbG.updateCategory(category);
			dbG.updateSport(sport);

		}
	}


	@Override
	public void movementUpload(double amount) {
		date = new Date();
		VWallet wal = user.getWallet();
		double moneyAmount = wal.addFounds(amount);
		Movement mov = new Movement(wal, date, amount, '+');
		storeMovement(wal, mov);
		updateFounds(moneyAmount);
	}


	@Override
	public void movementWithdraw(double amount) {
		date = new Date();
		VWallet wal = user.getWallet();
		double moneyAmount = wal.withdrawFounds(amount);
		updateFounds(moneyAmount);
		Movement mov = new Movement(wal, date, amount, '-');
		storeMovement(wal, mov);
	}


	@Override
	public Date newDate(int i, int j, int k) {
		DataAccess dbManager = new DataAccess();
		return dbManager.newDate(i, j, k);
	}


	@Override
	public void openDB() {
		dbG = new DataAccess();
	}

	
	@Override
	public String proccessBet() {
		if (currentBets == null)
			System.out.println("currentBets == null");
		b = new Bet(currentBets);
		return b.earnings();
	}

	
	@Override
	@WebMethod
	public Boolean removeQuestion(Question question) {
		DataAccess dbManager = new DataAccess();
		boolean deleted = dbManager.deleteQuestion(question);
		dbManager.close();
		return deleted;
	}

	
	private List<Result> resultsFromSport(String sportId) {
		LinkedList<Result> result = new LinkedList<Result>();
		int m = 1;
		int M = 5;
		Random r = new Random();
		if (sportId.equalsIgnoreCase("Soccer") || sportId.equalsIgnoreCase("Handball")
				|| sportId.equalsIgnoreCase("Basketball") || sportId.equalsIgnoreCase("Ice Hockey")
				|| sportId.equalsIgnoreCase("Rugby") || sportId.equalsIgnoreCase("Baseball")
				|| sportId.equalsIgnoreCase("Fighting") || sportId.equalsIgnoreCase("Australian football")
				|| sportId.equalsIgnoreCase("Cycling")) {
			result.add(new Result(" 1 ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" X ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" 2 ", m + (M - m) * r.nextDouble()));
		} else if (sportId.equalsIgnoreCase("Motorsport")) {
			result.add(new Result(" L. Hamilton ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. Hamilton ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" V. Bottas ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" S. Vettel ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" C. Lecrec ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" M. Verstappen ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" P. Gasly ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" C. Sainz ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. Norris ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" K. Raikkonen ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" A. Giovinazzi ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" N. Hulkenberg ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" D. Ricciardo ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" R. Grosjean ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" K. Magnussen ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" S. Perez ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. Stroll ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" D. Kvyat ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" A. Albon ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" R. Kubica ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" G. Russell ", m + (M - m) * r.nextDouble()));
		} else if (sportId.equalsIgnoreCase("Cycling") || sportId.equalsIgnoreCase("Golf")
				|| sportId.equalsIgnoreCase("Tennis")) {
			result.add(new Result(" L. Smith ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. Jones ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" V. Taylor ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" S. Williams ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" C. Brown ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" M. Davies ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" P. Evans ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" C. Wilson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. Thomas ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" K. Roberts ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" A. Johnson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" N. Lewis ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" D. Walker ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" R. Robinson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" K. Wood ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" S. Thompson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" L. White ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" D. Watson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" A. Jackson ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" R. Wright ", m + (M - m) * r.nextDouble()));
			result.add(new Result(" G. Grease ", m + (M - m) * r.nextDouble()));

		}
		return result;
	}

	
	@Override
	public void saveBet() {
		movementWithdraw(b.getMoneyBetted() / 10);
		new DataAccess().saveBet(b, user.getUserName());
	}

	
	@Override
	public void saveBetMoney(String text) {
		b.setMoneyBetted(Double.parseDouble(text));
	}

	
	@Override
	public VWallet specialGetWalletByBetID(String id) {
		DataAccess db = new DataAccess();
		List<VWallet> us = db.getWalletForBet(id);
		db.close();
		return us.get(0);
	}

	
	@Override
	public void storeBet(PossibleBetContainer pb) {
		if (currentBets == null) currentBets = new LinkedList<PossibleBet>();
		currentBets.add(pb.getPossibleBet());
		
		System.out.println(pb.toString());
	}

	
	@Override
	public void storeEvent(Event e) {
		DataAccess db = new DataAccess();
		db.storeEvent(e);
		db.close();

	}

	
	@Override
	public void storeMovement(VWallet vw, Movement mov) {
		DataAccess db = new DataAccess();
		db.storeMovement(vw, mov);
		db.close();
	}

	
	@Override
	public void updateFounds(Double quant) {
		DataAccess db = new DataAccess();
		db.updateFounds(user, quant);
		db.close();
		user.getWallet().setBalance(quant);
	}

	
	@Override
	public void updateSavedUser(User oldSavedUser, String userToSave, boolean save) {
		DataAccess db = new DataAccess();
		if (oldSavedUser != null) {
			oldSavedUser.setRemember(false);
			db.updateUser(oldSavedUser);
		}

		User u = db.getUser(userToSave);
		u.setRemember(save);
		db.updateUser(u);
		db.close();

	}

}
