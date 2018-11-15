package myMath;

import java.util.ArrayList;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Test {

	
	
	public static void main(String[] args) {
		
		System.out.println("Monom test:");
		MonomTest();
		System.out.println("--------------------------------------------------------");
		System.out.println("Polynom test:");
		PolynomTest();
		System.out.println("--------------------------------------------------------");
	  
	}
	
	public static void MonomTest(){
		
		//constructors
		Monom m1=new Monom (7.2, 5);
		Monom m2=new Monom (3.1, 2);
		Monom m3=new Monom (m2);
		Monom m4=new Monom (2.5, 3);
		Monom m5=new Monom (6,2);
		
		
		
		System.out.println("m1=7.2x^5 ->"+m1);
		System.out.println("m2=3.1x^2 ->"+m2);
		System.out.println("m3=m2 ->"+m3);
		System.out.println("m4=2.5x^3 ->"+m4);
		System.out.println("m5=6.0x^2 ->"+m5);
		
		//derivative
		System.out.println("m1= "+m1+" derivative -> "+m1.derivative());
		System.out.println("m3= "+m3+" derivative ->"+m3.derivative());
		System.out.println("m5= "+m5+" derivative ->"+m5.derivative());
		
		
		//add
		m1.add(m2);
		System.out.println("m1=m1+m2 ->"+m1);
		m2.add(m5);
		System.out.println("m2=m2+m5 ->"+m2);
		
		
		//multiply
		m4.multiply(m1);
		System.out.println("m4=m4*m1 ->"+m4);
		
		
		//f
		System.out.println("f(3)=m3 ->"+m3.f(3));
		
	}
	
	
	public static void PolynomTest(){
		
		//constructor
		Polynom p1=new Polynom("2.3x^5+4x^7-5.1x^3-1.98x^15");
		Polynom p2=new Polynom("2.3*X^5+0+4x^7-5.1x^3-1.98^15");
		Polynom_able p3=new Polynom();
		
		System.out.println(p1.equals(p2));
		
		System.out.println("p1= "+p1);
		System.out.println("p2= "+p2);
		System.out.println("p3= "+p3);
		
		
		
		//add
		p1.add(p2);
		System.out.println("p1=p1+p2 ->"+p1);
		p2.add(new Monom(3.1,2));
		System.out.println("p2=p2+(3.1x^2) ->"+p2);
		
		
		//copy
		p3=p1.copy();
		System.out.println("p3=p1 ->"+p3);
		
		
		//subtract
		p1.subtract(p2);
		System.out.println("p1=p1-p2 ->"+p1);
		System.out.println("p3 ->"+p3);
		
		
		//multiply
		p2.multiply(p1);
		System.out.println("p2=p2*p1 ->"+p2);
		
		
		//equals
		System.out.println("is p1=p3? ->"+p1.equals(p3));
		
		
		//root
		System.out.println("root of p2 ->"+p2.root(-100, 100, 0.1));
		System.out.println("root of p3 ->"+p3.root(-100, 100, 0.19));
		
		
		//derivative
		System.out.println("p1="+p1+" -- its derivative is ->"+p1.derivative());
		System.out.println("p2="+p2+" -- its derivative is ->"+p2.derivative());
		System.out.println("p3="+p3+" --its derivative is ->"+p3.derivative());
		
		
		//area
		System.out.println("p1="+p1+" --riemann's integral is ->"+p1.area(0, 2, 0.1));
		System.out.println("p2="+p2+" --riemann's integral is ->"+p2.area(0, 2, 0.39));
		System.out.println("p3="+p3+" --riemann's integral is ->"+p3.area(0, 2, 0.23));
		
		
		//f
		System.out.println("f(3)=p1 ->"+p1.f(3));
		
		//------------------//
		//------------------//
		
		//areaBelowX
		Polynom p=new Polynom("1x^3");
		System.out.println("p is x^3 -> "+p.area(-1, 2, 0.1));
		System.out.println("p is x^3 -> "+p.areaBelowX(-1, 0));
		
		//MaximaMinima
		p=new Polynom("-5*x^5+7x^2");
		p.PGraph(-4, 4);
		
	}
	

}
