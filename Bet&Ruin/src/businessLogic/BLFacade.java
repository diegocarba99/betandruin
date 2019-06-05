package businessLogic;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Bet;
import domain.Category;
import domain.Event;
import domain.EventContainer;
import domain.Movement;
import domain.MovementContainer;
import domain.PossibleBet;
import domain.PossibleBetContainer;
import domain.Question;
import domain.User;
import domain.UserContainer;
import domain.VWallet;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public interface BLFacade {

	boolean betIsEmpty();

	boolean changePassword(String userName, String password);

	boolean changeUserName(String current, String newUN);

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	/*
	 * @WebMethod public void initializeBD(int y, int m, int d) { DataAccess
	 * dBManager = new DataAccess(); dBManager.initializeDB(y, m, d);
	 * dBManager.close(); }
	 */

	boolean checkCredentials(String userName, String passWord);

	void clearCurrentBets();

	void closeDB();

	boolean createAdmin();

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	void createUser(String userName, String password);

	void createUserSingleton(String userName);

	boolean decideBets(int id);

	void deleteBet(String string);

	boolean deleteEvent(String eve);

	boolean deleteUser(String ide);

	boolean existsAdmin();

	boolean existsUser(String userName);

	JSONArray getAvailableSportLeagues();

	String getBalance();

	Bet getBetByID(String id);

	List<Bet> getBetList(String choice);

	List<Bet> getBetsLike(String where, String like);

	List<Category> getCategoriesList();

	String[] getCategoriesArray(String sportID);

	Category getCategoryById(int catId);

	String getCategoryByName(String categoryName);

	List<Event> getEventByBet(String ide);

	Event getEventByID(String id);

	List<Event> getEventList(String choice);

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	Vector<Event> getEvents(Date date);

	List<EventContainer> getEventsByCategory(String categoryName);

	List<Event> getEventsBySportCategory(int categoryID, Date date, int sportID);

	List<Event> getEventsLike(String where, String like);

	ArrayList<Movement> getLastMovements();

	Movement getMovementByID(String iden);

	////////ADMINISTRATOR ////////
	List<Movement> getMovementList(String choice);

	List<MovementContainer> getMovementsLike(String where, String like);

	JSONObject getNextEventsFromLeague(int leagueId);

	String getPoints();

	Question getQuestionFromEventAs(Event ev, String que);

	List<Question> getQuestions(Event event);

	User getSavedUser();

	List<String> getUserBets();

	UserContainer getUserByID(String id);

	User getUserByWalletID(String id);

	List<UserContainer> getUserList(String choice);

	List<UserContainer> getUsersLike(String where, String like);

	VWallet getWalletByBetID(String id);

	VWallet getWalletByID(String id);

	void insertCategoryEvents(Category category);

	void loadEventsFromAPI(JSONArray array, int i) throws JSONException, IOException, ParseException;

	void movementUpload(double amount);

	void movementWithdraw(double amount);

	Date newDate(int i, int j, int k);

	void openDB();

	String proccessBet();

	Boolean removeQuestion(Question question);

	void saveBet();

	void saveBetMoney(String text);

	VWallet specialGetWalletByBetID(String id);

	void storeBet(PossibleBetContainer pb);

	void storeEvent(Event e);

	void storeMovement(VWallet vw, Movement mov);

	void updateFounds(Double quant);

	void updateSavedUser(User oldSavedUser, String userToSave, boolean save);

}