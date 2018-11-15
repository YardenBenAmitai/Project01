package myMath;

import java.util.ArrayList;

import java.util.Iterator;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;


import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	
	
	private ArrayList<Monom> poly;
	
	
	/**
	 * empty constructor
	 */
	public Polynom(){
		ArrayList<Monom> poly=new ArrayList<Monom>();
		this.poly=poly;
	}
	
	
	/**
	 * a constructor that creates a Polynom based on a String
	 * the constructor will throw a NumberFormatException when a polynom is written incorrectly.
	 * @param p1
	 */
	public Polynom(String p1){
		
		ArrayList<Monom> str_poly=new ArrayList<Monom>();
		
		String coefficient="";
		String power="";
		int i=0;
		
		while (i<p1.length())
		{
			while ((i<p1.length())&&(p1.charAt(i)!='*')&&(p1.charAt(i)!='x')&&(p1.charAt(i)!='X')){
				if (((coefficient.contains("+"))||(coefficient.contains("-")))&&((p1.charAt(i)=='+')||(p1.charAt(i)=='-')))
					break;
				coefficient+=p1.charAt(i);
				i++;
			}
			
			if ((coefficient=="")&&((p1.charAt(i)=='*')||(p1.charAt(i)=='x')||(p1.charAt(i)=='X'))){
				coefficient="1";
			}
			
			
			while ((i<p1.length())&&(p1.charAt(i-1)!='^')&&((p1.charAt(i)!='+')&&(p1.charAt(i)!='-'))){
				i++;
			}
			
			while ((i<p1.length())&&(p1.charAt(i)!='+')&&(p1.charAt(i)!='-')){
				power+=p1.charAt(i);
				i++;
			}
			
			if(coefficient!=""){
				try {
					Monom m=new Monom(Double.parseDouble(coefficient), Integer.parseInt(power));
					str_poly.add(m);
				}
				catch(NumberFormatException e){
					System.err.println("Caught NumberFormatException: "
	                        +  e.getMessage());
				}
			}
			
			coefficient="";
			power="";
		}
		
		this.poly=str_poly;
		this.poly.sort(new Monom_Comperator());
		this.removeZero();
		
	}
	
	
	
	
	/**
	 * updates this Polynom plus p1
	 * @param p1
	 */
	public void add(Polynom_able p1)
	{
		
		p1=p1.copy();
		Iterator<Monom> it_p1=p1.iteretor();
		while(it_p1.hasNext())
		{
			this.add(it_p1.next());
		}
		
	}
	
	
	
	/**
	 * Add m1 to this Polynom
	 * @param m1 Monom
	 */
	public void add(Monom m1){
		
		boolean b=false;
		
		for (int i=0; i<this.poly.size(); i++)
		{
			
			Monom m;
			if (this.poly.get(i).get_power()==m1.get_power())
			{
				m=this.poly.get(i);
				m.add(m1);
				this.poly.set(i, m);
				b=true;
				break;
			}
		}
		if (b==false)
		{
			this.poly.add(m1);
			this.poly.sort(new Monom_Comperator());
		}	
		this.removeZero();
	}
	
	
	
	/**
	 * Subtract p1 from this Polynom
	 * @param p1
	 */
	public void subtract(Polynom_able p1){
		
		p1=p1.copy();
		Iterator<Monom> it_p1=p1.iteretor();
		while(it_p1.hasNext())
		{
			this.subtract(it_p1.next());
		}
		
	}
	
	
	/**
	 * subtract m1 from this Polynom
	 * @param m1 Monom
	 */
	public void subtract(Monom m1){
		
		boolean b=false;
		Monom m;
		for (int i=0; i<this.poly.size(); i++)
		{
			if (this.poly.get(i).get_power()==m1.get_power())
			{
				m=this.poly.get(i);
				m.subtract(m1);
				this.poly.set(i,m);
				b=true;
				break;
			}
		}
		if (b==false)
		{
			m=new Monom((-1*m1.get_coefficient()), m1.get_power());
			this.poly.add(m);
			this.poly.sort(new Monom_Comperator());
		}	
		this.removeZero();
	}
	
	
	
	/**
	 * Multiply this Polynom by p1
	 * @param p1
	 */
	public void multiply(Polynom_able p1){
		
		
		p1=p1.copy();
		Polynom_able copyPoly= this.copy();
		this.poly.clear();
		
		Iterator <Monom> it_p1=p1.iteretor();
		Iterator <Monom> it_copy=copyPoly.iteretor();
		Monom m;
		
		while (it_copy.hasNext())
		{
			m=it_copy.next();
			while (it_p1.hasNext())
			{	
				m.multiply(it_p1.next());
				this.add(m);
			}
		}
		
	}
	
	
	
	/**
	 * Test if this Polynom is logically equals to p1.
	 * @param p1
	 * @return true if this Polynom represents the same function as p1
	 */
	public boolean equals (Polynom_able p1){
		
		Iterator <Monom> it_poly=this.poly.iterator();
		Iterator <Monom> it_p1=p1.iteretor();
		Monom m1;
		Monom m2;
		
		while ((it_poly.hasNext())&&(it_p1.hasNext()))
		{
			m1=it_poly.next();
			m2=it_p1.next();
			if ((m1.get_coefficient()!=m2.get_coefficient())||(m1.get_power()!=m2.get_power())){
				
				return false;
			}
			
		}
		if((!it_poly.hasNext())&&(!it_p1.hasNext())){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Test if this is the Zero Polynom
	 * @return
	 */
	public boolean isZero(){
		
		Iterator <Monom> it_poly=this.poly.iterator();
		while (it_poly.hasNext())
		{
			if (it_poly.next().get_coefficient()!=0)
				return false;
		}
		return true;
	}
	
	
	
	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, returns f(x2) such that:
	 * *	(i) x0<=x2<=x2 && (ii) f(x2)<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value
	 * @return
	 */
	public double root(double x0, double x1, double eps){
		
		double middle=(x0+x1)/2;
		
		while (this.f(middle)>=eps)
		{
			if (((this.f(x0)>0)&&(this.f(middle)<0))||((this.f(x0)<0)&&(this.f(middle)>0)))
			{
				x1=middle;
				middle=(x0+x1)/2;
			}
			
			else
			{
				x0=middle;
				middle=(x0+x1)/2;
			}
		}
		return middle;
	}
	
	
	/**
	 * create a deep copy of this Polynom
	 * @return 
	 */
	public Polynom_able copy(){
		
		Polynom_able copyP=new Polynom();
		Iterator <Monom> it_poly=this.poly.iterator();
		while (it_poly.hasNext())
		{
			copyP.add(new Monom (it_poly.next()));
		}
		
		return copyP;
	}
	
	
	
	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return
	 */
	public Polynom_able derivative(){
		
		Polynom_able drv=new Polynom();
		Iterator <Monom> it_poly=this.poly.iterator();
		Monom m;
		
		while (it_poly.hasNext())
		{
			m=it_poly.next();
			drv.add(m.derivative());
		}
		
	return drv;
	}
	
	
	
	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps,
	 * see: https://en.wikipedia.org/wiki/Riemann_integral
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.
	 */
	public double area(double x0,double x1, double eps){
		
		double rectangles= Math.ceil((x1-x0)/eps);
		eps=(x1-x0)/rectangles;
		double rmn=0;
		
		for (int i=1; i<rectangles;i++)
		{
			if (this.f(x0+i*eps)>=0){
				rmn+=this.f(x0+(i*eps));
			}
		}
		return rmn*eps;
	}
	
	
	
	/**
	 * @return an Iterator (of Monoms) over this Polynom
	 * @return
	 */
	public Iterator<Monom> iteretor(){
		Iterator<Monom> itP=this.poly.iterator();
		return itP;
	}
	
	
	
	/**
	 * the method is inherited from function interface.
	 * The method calculates the value of the Polynom at x according to the function y=f(x)
	 * @param x
	 * @return f(x)
	 */
	public double f(double x){
		
		double d=0;
		Iterator <Monom> it_poly=this.poly.iterator();
		
		while(it_poly.hasNext())
		{
			d+=it_poly.next().f(x);
		}
		return d;
		
	}
	
	
	/**
	 * return a string of this Polynom
	 */
	public String toString(){
		
		Iterator <Monom> it_poly=this.poly.iterator();
		String str="";
		Monom m;
		while (it_poly.hasNext())
		{
			m=it_poly.next();
			if (m.get_coefficient()>0)
			{
				str+='+';
			}
			str+=m.get_coefficient()+"x^"+m.get_power();
		}
		return str;
	}
	
	
	//--------------------------------------------------------------//
	//--------------------------------------------------------------//
	
	
	
	/**
	 * the method finds Maxima and Minima values between the bounds x0 and x1
	 * the method searches for f(x) that is higher/lower than both its neighbors with eps of 0.01
	 * @param x0
	 * @param x1
	 * @return array[double] where [x,y] are inserted accordingly.
	 */
	public ArrayList<Double> MaximaMinima(double x0, double x1){
		
		Polynom_able p=this.derivative();
		ArrayList<Double> arr=new ArrayList<Double>();
		
		for (double i=x0; i<=x1; i=i+0.01)
		{
			if (((this.f(i)>this.f(i-0.01))&&(this.f(i)>this.f(i+0.01)))||((this.f(i)<this.f(i-0.01))&&(this.f(i)<this.f(i+0.01))))
			{
				if((p.f(i)<0.001)&&(p.f(i)>-0.001)){
					arr.add(i);
					arr.add(f(i));
				}
			}
			
		}
		return arr;
	}
	
	
	/**
	 * the method calculates the area above this polynom and below xAxis
	 * @param x0
	 * @param x1
	 * @return Riemann's sum for the area below xAxis
	 */
	public double areaBelowX(double x0, double x1){
		
		double rmn=0;
		
		for (double i=x0; i<=x1; i=i+0.01)
		{
			if (this.f(i)<0){
				rmn+=this.f(i);
			}
		}
		return rmn*0.01*-1;
	}
	
	
	/**
	 * the method creates a 2D graph for this Polynom, using XChart library
	 * and also mark its Maxima and Minima between the bounds x0 and x1
	 * @param x0
	 * @param x1
	 */
	public void PGraph(double x0, double x1){
		
		ArrayList<Double> xData = new ArrayList<Double>();
	    ArrayList<Double> yData = new ArrayList<Double>();
	    
	    for (double i=x0; i<=x1; i=i+0.01){
	    	xData.add(i);
	    	yData.add(this.f(i));
	    }
	    
	    // Create Chart
	    XYChart chart = QuickChart.getChart(this.toString(), "X", "Y","y(x)", xData, yData);
	    
	    //getting Max/Min from this Polynom
	    ArrayList<Double> arr=new ArrayList<Double>();
	    arr=this.MaximaMinima(x0, x1);
	    xData=new ArrayList<Double>();
	    yData=new ArrayList<Double>();
	    
	    for (int i=0; i<arr.size(); i+=2){
	    	xData.add(arr.get(i));
	    	yData.add(arr.get(i+1));
	    }
	    
	    //creating an additional Series for Max/Min, adding it to chart as scattered dots
	    chart.addSeries("Maxima/Minima",xData, yData).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
	 
	    // Show it
	    new SwingWrapper(chart).displayChart();
	}
	
	
	/**
	 * remove all zero-Monoms from this Polynom
	 */
	public void removeZero(){
		
		int counter=-1;
		Iterator<Monom> it=this.poly.iterator();
		
		while(it.hasNext())
		{
			counter++;
			if(it.next().get_coefficient()==0)
			{
				this.poly.remove(counter);
				counter--;
			}
		}
	}
}
