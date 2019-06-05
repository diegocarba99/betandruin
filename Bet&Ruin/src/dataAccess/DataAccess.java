package dataAccess;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import configuration.ConfigXML;
import domain.Bet;
import domain.Category;
import domain.Event;
import domain.Movement;
import domain.Question;
import domain.Sport;
import domain.User;
import domain.UserSingleton;
import domain.VWallet;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
@SuppressWarnings("unchecked")
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;
	String fileName = "";
	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		this.fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/" + fileName, properties);
			try {
			db = emf.createEntityManager();
			}catch(PersistenceException e) {
				JOptionPane.showMessageDialog(null, "Wrong server IP address or port.\nPlease check the configuration XML file.", "Can't connect to the Database", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		
	}

	public DataAccess() {
		new DataAccess(false);
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		q.setEvent(event);
		q.setPosibleResults(event.getPosibleResults());
		db.persist(ev);
		db.getTransaction().commit();
		return q;

	}

	public boolean deleteQuestion(Question question) {
		System.out.println(">> DataAccess: deleting question '" + question + "'");
		try {
			db.getTransaction().begin();
			
			Query que = db.createQuery("SELECT e FROM Event e WHERE e.questions.questionNumber = :queNum", Event.class)
					.setParameter("queNum", question.getQuestionNumber());
			
			Event ev = (Event) que.getResultList().get(0);
			boolean deleted = ev.deleteQuestion(question);
			
			int query = db.createQuery("DELETE FROM Question q WHERE q.questionNumber = :question")
					.setParameter("question", question.getQuestionNumber()).executeUpdate();
			db.getTransaction().commit();
			return (deleted && query != 0);
		} catch (Exception p) {
			System.out.println(p);
			return false;
		}
	}

	public List<Question> getQuestions(Event eve) {
		System.out.println(">> DataAccess: getQuestions from event " + eve.getDescription());
		Vector<Question> res = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event = :event", Question.class)
				.setParameter("event", eve);

		List<Question> list = query.getResultList();
		for (Question q : list)
			res.add(q);
		return (res.isEmpty() ? null : list);
	}

	public Question getQuestionFromEventAs(Event ev, String que) {
		que = que.split(" ")[0];
		System.out
				.println(">> DataAccess: getQuestions from event '" + ev.getDescription() + "' named as '" + que + "'");
		TypedQuery<Question> query = db
				.createQuery("SELECT q FROM Question q WHERE q.event = :event AND q.questionNumber = :que",
						Question.class)
				.setParameter("que", Integer.parseInt(que)).setParameter("event", ev);

		return query.getSingleResult();
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents from date --> " + date.toString());
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public User createUser(String userName, String password) {
		db.getTransaction().begin();

		User user = new User(userName, hash(password), false);
		VWallet wallet = new VWallet();
		db.persist(wallet);
		user.setWallet(wallet);
		db.persist(user);
		db.getTransaction().commit();
		return user;
	}

	public boolean createAdmin() {
		User user = new User("admin", hash("admin"), true);
		VWallet wallet = new VWallet();
		try {
			db.getTransaction().begin();
			db.persist(wallet);
			user.setWallet(wallet);
			db.persist(user);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	public String getUserPassword(String userName) {
		String query = "select u.password from User u where lower(u.userName)= :userName";
		Query q = db.createQuery(query).setParameter("userName", userName.toLowerCase());
		List<String> resul;
		if (!existsDB())
			emf.getMetamodel().entity(User.class);

		resul = q.getResultList();

		if (!resul.isEmpty())
			return resul.get(0);
		else
			return null;
	}

	public boolean changePassword(String userName, String newPassword) {
		System.out.println(">> DataAccess: changeing " + userName + "'s password");
		try {
			db.getTransaction().begin();
			Query q = db.createQuery("SELECT u FROM User u WHERE u.userName = :user", VWallet.class)
					.setParameter("user", userName);
			User user = (User) q.getResultList().get(0);
			user.setPassword(hash(newPassword));
			db.persist(user);
			db.getTransaction().commit();
			System.out.println(">> DataAccess: success!");
			return true;
		} catch (Exception e) {
			db.getTransaction().commit();
			System.out.println(">> DataAccess: failed!");
			return false;
		}
	}
	
	public boolean changeUserName(String current, String newUN) {
		System.out.println(">> DataAccess: changeing " + current + "'s username");
		try {
			db.getTransaction().begin();
			Query q = db.createQuery("SELECT u FROM User u WHERE u.userName = :user", VWallet.class)
					.setParameter("user", current);
			User user = (User) q.getResultList().get(0);
			user.setUserName(newUN);
			db.persist(user);
			db.getTransaction().commit();
			System.out.println(">> DataAccess: success!");
			return true;
		} catch (Exception e) {
			db.getTransaction().commit();
			System.out.println(">> DataAccess: failed!");
			return false;
		}
	}

	public User getUser(String userName) {
		Query q = db.createQuery("select u from User u where lower(u.userName)= :userName", User.class)
				.setParameter("userName", userName.toLowerCase());
		List<User> l = q.getResultList();
		if (l != null && !l.isEmpty())
			return l.get(0);
		else
			return null;
	}

	public String hash(String text) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			BigInteger no = new BigInteger(1, hash);
			String hashText = no.toString(16);
			while (hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEventsBySportCategory(int sportID, int categoryID) {
		System.out.println(">> DataAccess: getEvents from sport --> " + sportID + "; category: " + categoryID);
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.sportID=?1 AND ev.categoryID=?2",
				Event.class);
		query.setParameter(1, sportID);
		query.setParameter(2, categoryID);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(">> Getting from database event --> " + ev.toString());
		}
		return events;
	}

	public List<Event> getEventsByCategory(String categoryID) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.categoryID=?1", Event.class);
		query.setParameter(1, categoryID);
		List<Event> events = query.getResultList();
		return events;
	}

	public Event getEventById(int eventId) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventID=:id", Event.class);
		query.setParameter("id", eventId);
		List<Event> events = query.getResultList();
		return (events != null && !events.isEmpty()) ? events.get(0) : null;
	}

	public Category getCategoryByName(String categoryName) {
		List<Category> c = db.createQuery("SELECT c FROM Category c WHERE c.categoryName=:id", Category.class)
				.setParameter("id", categoryName).getResultList();
		return (c != null && !c.isEmpty()) ? c.get(0) : null;

	}

	private boolean existsDB() {
		File f = new File(fileName);
		if (f.exists())
			return true;
		return false;
	}

	public void saveBet(Bet bet, String username) {
		db.getTransaction().begin();
		User u = db.createQuery("select u from User u where u.userName=:usrnm", User.class)
				.setParameter("usrnm", username).getSingleResult();
		VWallet vw = u.getWallet();
		vw.addBet(bet);
		db.persist(bet);
		db.merge(vw);
		db.getTransaction().commit();
	}

	public void checkBet(Bet bet, String username) {
		db.getTransaction().begin();
		User u = db.createQuery("select u from User u where u.userName=:usrnm", User.class)
				.setParameter("usrnm", username).getSingleResult();
		VWallet vw = u.getWallet();
		// Random decision
		int d = new Random().nextInt(1);
		if (d == 0) {
			// Code to apply result to bet
		} else {
			// Code to apply result to bet
		}
		vw.withdrawFounds(bet.getMoneyBetted());
		db.persist(bet);
		db.getTransaction().commit();

	}

	////////// VWALLET //////////

	public void updateFounds(UserSingleton userSingleton, double quantity) {
		if (userSingleton != null && quantity > 0) {
			db.getTransaction().begin();

			Query q = db.createQuery("select w from VWallet w where w.id = :wallet", VWallet.class)
					.setParameter("wallet", userSingleton.getWallet().getID());
			VWallet wallet = (VWallet) q.getResultList().get(0);
			wallet.setBalance(quantity);
			db.persist(wallet);

			System.out.println(">> DataAccess: founds updated");
			db.getTransaction().commit();
		}
	}

	public void storeMovement(VWallet wal, Movement mov) {
		if (wal != null && mov != null) {
			db.getTransaction().begin();
			db.persist(mov);
			System.out.println(">> DataAccess: movements stored");
			db.getTransaction().commit();
		}
	}

	public ArrayList<Movement> getLastMovements(VWallet wal) {
		System.out.println(">> DataAccess: getLastMovements");
		Query q = db.createQuery("select m from Movement m where m.vwallet.id = :wallet order by m.movDate desc")
				.setParameter("wallet", wal.getID());
		ArrayList<Movement> array = null;

		try {
			array = (ArrayList<Movement>) q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Movement.class);
			array = (ArrayList<Movement>) q.getResultList();
		}

		return array;
	}

	public Category getCategoryById(int cat) {
		List<Category> c = db.createQuery("select c from Category c where c.categoryID=:id", Category.class)
				.setParameter("id", cat).getResultList();
		return (c != null && !c.isEmpty()) ? c.get(0) : null;
	}

	public Sport getSportById(String sportId) {
		List<Sport> c = db.createQuery("select s from Sport s where s.sportID=:id", Sport.class)
				.setParameter("id", sportId).getResultList();
		return (c != null && !c.isEmpty()) ? c.get(0) : null;
	}

	public void updateCategory(Category category) {
		db.getTransaction().begin();
		db.merge(category);
		db.getTransaction().commit();
	}

	public void updateSport(Sport sport) {
		db.getTransaction().begin();
		db.merge(sport);
		db.getTransaction().commit();
	}

	public User getSavedUser() {
		List<User> u = db.createQuery("select u from User u where u.remember=true", User.class).getResultList();
		return (u != null && !u.isEmpty()) ? u.get(0) : null;
	}

	public void updateUser(User u) {
		db.getTransaction().begin();
		db.merge(u);
		db.getTransaction().commit();

	}

	public void storeEvent(Event e) {
		db.getTransaction().begin();
		db.persist(e);
		db.getTransaction().commit();

	}

	public List<Category> getCategories(String sportID) {
		List<Category> categories = db.createQuery("select c from Category c where c.sportID=:id", Category.class)
				.setParameter("id", sportID).getResultList();
		return (!categories.isEmpty()) ? categories : null;
	}

	public List<Category> getCategories() {
		List<Category> categories = db.createQuery("select c from Category c ", Category.class).getResultList();
		for (Category category : categories) {
			System.out.println("DATA: Getting from DB category" + category.toString());
		}
		return (categories.isEmpty()) ? null : categories;
	}

////////ADMINISTRATOR ////////
	public List<User> getAllUsers() {
		System.out.println(">> DataAccess: getList of Users");
		TypedQuery<User> q = db.createQuery("select u from User u", User.class);
		List<User> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(User.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<User> getUsersOrdered(String group) {
		System.out.println(">> DataAccess: getList of Users ordered");
		group = "u." + group;
		TypedQuery<User> q = db.createQuery("select u from User u order by " + group, User.class);
		List<User> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(User.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<User> getUsersLike(String where, String like) {
		System.out.println(">> DataAccess: getList of Users with restrictions");
		TypedQuery<User> q = null;

		if (like.length() != 0) {
			if (Character.isDigit(like.charAt(0))) {
				q = db.createQuery("select u from User u where u." + where + " = " + like, User.class)
						.setParameter("like", Integer.parseInt(like));
			} else {
				q = db.createQuery("select u from User u where u." + where + " like '%" + like + "%'", User.class)
						.setParameter("like", like);
			}
		} else {
			q = db.createQuery("select u from User u ", User.class);
		}

		List<User> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(User.class);
			array = q.getResultList();
		}

		return array;
	}

	public boolean deleteUser(String us) {
		System.out.println(">> DataAccess: delete usr with id " + us);
		try {
			db.getTransaction().begin();
			Query q = db.createQuery("DELETE FROM User u where u.id = :id");
			q.setParameter("id", Integer.parseInt(us)).executeUpdate();
			db.getTransaction().commit();
			return true;
		} catch (Exception p) {
			p.getMessage();
			return false;
		}
	}

	public List<Movement> getAllMovements() {
		System.out.println(">> DataAccess: getList of Movements");
		TypedQuery<Movement> q = db.createQuery("select m from Movement m", Movement.class);
		List<Movement> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Movement.class);
			array = (ArrayList<Movement>) q.getResultList();
		}

		return array;
	}

	public List<Movement> getMovementsSorted(String group) {
		System.out.println(">> DataAccess: getList of Movements sorted");
		group = "m." + group;
		TypedQuery<Movement> q = db.createQuery("select m from Movement m order by " + group, Movement.class);
		List<Movement> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Movement.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Movement> getMovementsLike(String where, String like) {
		System.out.println(">> DataAccess: getList of Movements with restrictions");
		TypedQuery<Movement> q = null;

		if (like.length() != 0) {
			if (Character.isDigit(like.charAt(0))) {
				if (where == "id") {
					q = db.createQuery("select m from Movement m where m." + where + " = :like", Movement.class)
							.setParameter("like", Integer.parseInt(like));
				} else if (where != "vwallet.id" && !like.contains(".")) {
					like += ".00";
					q = db.createQuery("select m from Movement m where m." + where + " = :like", Movement.class)
							.setParameter("like", Double.parseDouble(like));
				} else {
					q = db.createQuery("select m from Movement m where m." + where + " = :like", Movement.class)
							.setParameter("like", Double.parseDouble(like));
				}
			} else
				q = db.createQuery("select m from Movement m where m." + where + " like '%:like%'", Movement.class)
						.setParameter("like", like);
		} else
			q = db.createQuery("select m from Movement m", Movement.class);

		List<Movement> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Movement.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Event> getAllEvents() {
		System.out.println(">> DataAccess: getList of Events");
		TypedQuery<Event> q = db.createQuery("select e from Event e", Event.class);
		List<Event> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Event.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Event> getEventsOrdered(String group) {
		System.out.println(">> DataAccess: getList of Events ordered");
		group = "e." + group;
		TypedQuery<Event> q = db.createQuery("select e from Event e order by " + group, Event.class);
		List<Event> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Event.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Event> getEventsLike(String where, String like) {
		System.out.println(">> DataAccess: getList of Events with restrictions");
		TypedQuery<Event> q = null;

		if (like.length() != 0) {
			if (Character.isDigit(like.charAt(0))) {
				q = db.createQuery("select e from Event e where e." + where + " = :like", Event.class)
						.setParameter("like", Integer.parseInt(like));
			} else {
				q = db.createQuery("select e from Event e where e." + where + " like '%:like%'", Event.class)
						.setParameter("like", like);
			}
		} else
			q = db.createQuery("select e from Event e", Event.class);

		List<Event> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Event.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Event> getEventByBet(String ide) {
		System.out.println(">> DataAccess: getEvent of a bet");
		TypedQuery<Event> q = db
				.createQuery(
						"SELECT pb.relatedEvent FROM Bet b, PossibleBet pb "
								+ "WHERE pb.possibleBetId MEMBER OF b.bets.possibleBetId AND b.betId = :id",
						Event.class)
				.setParameter("id", Integer.parseInt(ide));

		List<Event> ar = null;

		try {
			ar = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Event.class);
			ar = q.getResultList();
		}

		return ar;
	}

	public boolean deleteEvent(String ev) {
		System.out.println(">> DataAccess: delete usr with id " + ev);
		try {
			db.getTransaction().begin();
			Query q = db.createQuery("DELETE FROM Event e where e.eventID = :id");
			q.setParameter("id", Integer.parseInt(ev)).executeUpdate();
			db.getTransaction().commit();
			return true;
		} catch (Exception p) {
			p.getMessage();
			return false;
		}
	}

	public List<Bet> getAllBets() {
		System.out.println(">> DataAccess: getList of Bets");
		TypedQuery<Bet> q = db.createQuery("select b from Bet b", Bet.class);
		List<Bet> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Bet.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Bet> getBetsLike(String where, String like) {
		System.out.println(">> DataAccess: getList of Bets with restrictions");
		TypedQuery<Bet> q = null;

		if (like.length() != 0) {
			if (Character.isDigit(like.charAt(0))) {
				q = db.createQuery("select b from Bet b where " + where + " = :like", Bet.class).setParameter("like",
						Integer.parseInt(like));
			} else {
				q = db.createQuery("select b from Bet b where " + where + " like '%:like%'", Bet.class)
						.setParameter("like", like);
			}
		} else
			q = db.createQuery("select b from Bet b", Bet.class);

		List<Bet> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Bet.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<Bet> getBetsOrdered(String group) {
		System.out.println(">> DataAccess: getList of Bets ordered");
		group = "b." + group;
		TypedQuery<Bet> q = db.createQuery("select b from Bet b order by " + group, Bet.class);
		List<Bet> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Bet.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<VWallet> getWalletsLike(String where, String like) {
		System.out.println(">> DataAccess: getList of VWallet with restrictions");
		TypedQuery<VWallet> q = null;

		if (like.length() != 0) {
			if (Character.isDigit(like.charAt(0))) {
				q = db.createQuery("select b from VWallet b where " + where + " = :like", VWallet.class)
						.setParameter("like", Integer.parseInt(like));
			} else {
				q = db.createQuery("select b from VWallet b where " + where + " like '%:like%'", VWallet.class)
						.setParameter("like", like);
			}
		} else
			q = db.createQuery("select b from VWallet b", VWallet.class);

		List<VWallet> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Bet.class);
			array = q.getResultList();
		}

		return array;
	}

	public List<VWallet> getWalletForBet(String like) {
		System.out.println(">> DataAccess: getWallet of current Bet");
		TypedQuery<VWallet> q = null;

		if (like != null) {
			if (Character.isDigit(like.charAt(0))) {
				q = db.createQuery("SELECT w from VWallet w, Bet b " + "WHERE w.bets.betId = :like "
						+ "AND w.bets.betId MEMBER OF b.betId", VWallet.class)
						.setParameter("like", Integer.parseInt(like));
			}
		}

		List<VWallet> array = null;

		try {
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(Bet.class);
			array = q.getResultList();
		}

		return array;
	}

////////////////////////////////////////

	public void saveUserBets(User u, VWallet vw) {
		db.getTransaction().begin();
		db.merge(u);
		db.merge(vw);
		db.getTransaction().commit();

	}

	public boolean existsAdmin() {
		List<User> array = null;
		TypedQuery<User> q = null;
		try {
			q = db.createQuery("SELECT u FROM User u WHERE u.isAdmin = true", User.class);
			array = q.getResultList();
		} catch (PersistenceException p) {
			emf.getMetamodel().entity(User.class);
			array = q.getResultList();
		}

		return (array != null && !array.isEmpty()) ? true : false;
	}

	public boolean decideBets(int id) {
		
		User user = db.createQuery("SELECT u FROM User u WHERE u.id=?1", User.class).setParameter(1, id).getSingleResult();
		List<Bet> bets = user.getWallet().getBets();
		Random rnd = new Random();
		if(bets != null) {
			for (Bet b : bets) {
				if ( b != null && b.getDueDate().before(new Date()) && b.getStatus() == -1 ) {
					b.setStatus(rnd.nextInt(2));
					if ( b.getStatus() == 1 ) user.getWallet().addFounds( b.getEarnings());
				}	
			}
		}
		
		
		
		return true;
	}
	
}
