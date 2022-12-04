package ru.bobb.startwars;

public interface IFuelable {
	Integer getFuelReserve();
	void setFuelReserve(Integer newValue);
	Integer getFuelSpent();
}
