package com.osmosis.jeopardyserver.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
	private Integer value;

	Cell(Category category, String question, Integer value) {
		this.category = category;
		this.question = question;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Cell{" +
				"question='" + question + '\'' +
				'}';
	}
}
