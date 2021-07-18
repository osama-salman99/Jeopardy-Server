package com.osmosis.jeopardyserver.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class Cell {
	@Id
	@Setter(AccessLevel.NONE)
	@SequenceGenerator(
			name = "cell_sequence",
			sequenceName = "cell_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "cell_sequence"
	)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;
	private String question;

	public Cell() {

	}

	Cell(Category category, String question) {
		this.category = category;
		this.question = question;
	}

	@Override
	public String toString() {
		return "Cell{" +
				"question='" + question + '\'' +
				'}';
	}
}
