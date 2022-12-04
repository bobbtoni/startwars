package ru.bobb.startwars;

public interface ITurnable {
	Integer getDirection();
	Integer getAngularVelocity();
	Integer getDirectionsNumber();
	void setDirection(Integer newValue);
}
