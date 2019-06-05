package domain;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
	@XmlID
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int resultID;
	private String result;
	private double value;
	
	public Result(String result, double value) {
		this.result = result;
		this.value = value;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String toString() {
		return result;
	}
	
	public String toStringWithValue() {
		DecimalFormat nf = new DecimalFormat("#.00");
		return "[" + result + ": " + nf.format(value) +  "RP ]";
	}
}
