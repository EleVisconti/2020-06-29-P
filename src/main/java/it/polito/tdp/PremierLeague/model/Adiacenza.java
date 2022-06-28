package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class Adiacenza {
 int m1;
 int m2;
 int peso;
public int getM1() {
	return m1;
}
public void setM1(int m1) {
	this.m1 = m1;
}
public int getM2() {
	return m2;
}
public void setM2(int m2) {
	this.m2 = m2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
public Adiacenza(int m1, int m2, int peso) {
	super();
	this.m1 = m1;
	this.m2 = m2;
	this.peso = peso;
}
@Override
public int hashCode() {
	return Objects.hash(m1, m2);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Adiacenza other = (Adiacenza) obj;
	return m1 == other.m1 && m2 == other.m2;
}
 
 
}
