package ru.bobb.startwars;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Vector {
	private final int x;
	private final int y;
	
	public Vector add(final Vector vector) {
		return new Vector(this.x + vector.x, this.y + vector.y);
	}
}
