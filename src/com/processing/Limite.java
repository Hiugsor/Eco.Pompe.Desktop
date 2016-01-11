package com.processing;

public class Limite {

	
	//Attributs
	private Point BorderNO;
	public Point getBorderNO() {
		return BorderNO;
	}
	public void setBorderNO(Point ptNO) {
		this.BorderNO = ptNO;
	}

	
	private Point BorderSE;
	public Point getBorderSE() {
		return BorderSE;
	}
	public void setBorderSE(Point ptSE) {
		this.BorderSE = ptSE;
	}

	
	
	//Constructor
	/** 
	 * @param NO - Point Nord Ouest du cadre Limite
	 * @param SE - Point Sud Est du cadre Limite
	 */
	public Limite(Point NO, Point SE)
	{
		this.BorderNO = NO;
		this.BorderSE = SE;
	}
}
