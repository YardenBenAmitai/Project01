
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */

/**
 * importing java.lang.Math from library to use the function Math.pow()
 */

import java.lang.Math;


public class Monom implements function{
	
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	// ***************** add your code below **********************
	
	/**
	 * the method extracts and return the coefficient of the Monom type Object
	 * @param 
	 * @return the coefficient of the Monom
	 */
	
	public double get_coefficient(){
		return this._coefficient;
	}
	
	
	/**
	 * the method extracts and return the power of the Monom type Object
	 * @param 
	 * @return the power of the Monom
	 */
	
	public int get_power(){
		return this._power;
	}
	
	/**
	 * the method calculate the derivative of a Monom.
	 * @return Monom type derivative. y'=a*b*x^(b-1).
	 */
	
	public Monom derivative() {
		Monom deriv=new Monom(this._coefficient*this._power, (this._power)-1);
		return deriv;
	}
	
	/**
	 * the method receives a Monom and adds it to this Monom.
	 * the method can sum two Monoms only if their powers are equal.
	 * @param m, a Monom.
	 * @return void.
	 */
	
	public void add(Monom m){
		if (this.get_power()==m.get_power()){
			this.set_coefficient(this._coefficient+m._coefficient);
			this.set_power(this._power);
		}
		else
			System.out.println("addition of two Monoms with different powers is not possible");
		
	}
	
	/**
	 * the method subtract one monomfrom the other
	 * @param m1
	 */
	public void subtract(Monom m1){
		if(this.get_power()==m1.get_power()){
			this.set_coefficient(this.get_coefficient()-m1.get_coefficient());
		}
		else
			System.out.println("subtraction addition of two Monoms with different powers is not possible");
	}
	
	/**
	 * the method receives a Monom then multiply and store it in this monom.
	 * @param m, a Monom.
	 * @return void.
	 */
	
	public void multiply(Monom m){
		this.set_coefficient((this._coefficient)*(m._coefficient));
		this.set_power((this._power)+(m._power));
	}
	
	/**
	 * the method is inherited from function interface.
	 * The method calculates the value of the Monom at x according to the function f(x)=a*x^b
	 * @param x
	 * @return f(x)
	 */
	
	public double f(double x){
		return(this._coefficient*Math.pow(x,this._power));
	}
	
	/**
	 * the method returns a string of Monom
	 */
	public String toString(){
		String str=String.valueOf(this._coefficient)+"x^"+String.valueOf(this._power);
		return str;
	}


	//****************** Private Methods and Data *****************
	
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		this._power = p;
	}
	
	private double _coefficient; // 
	private int _power; 
}
