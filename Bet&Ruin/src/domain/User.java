package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * @author Erlantz
 *
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	@Id
	@XmlID
	@GeneratedValue
	private int id;
	private String userName;
	private String password;
	private boolean isAdmin;
	private boolean remember;

	@XmlIDREF
	@OneToOne
	private VWallet wallet;

	public User(String userName, String password, boolean admin) {
		super();
		this.userName = userName;
		this.password = password;
		this.isAdmin = admin;
	}

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public User() {
		super();
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	/**
	 * @param quantity that will be added to the users account
	 * @return users money after the operation.
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public VWallet getWallet() {
		return wallet;
	}

	public void setWallet(VWallet wallet) {
		this.wallet = wallet;
	}

	/**
	 * @param quantity that will be discounted to the users account
	 * @return users money after the operation.
	 */

	public String toString() {
		return this.getId() + "  " + this.getUserName() + "  Admin:" + this.isAdmin + "  VWallet id:" + this.getWallet().id;
	}
}
