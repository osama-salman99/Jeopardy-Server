package com.osmosis.jeopardyserver.entities;

import com.osmosis.jeopardyserver.exceptions.CategoryFullException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
	@Id
	@Setter(AccessLevel.NONE)
	@SequenceGenerator(
			name = "category_sequence",
			sequenceName = "category_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "category_sequence"
	)
	private Long id;
	private String name;
	@ManyToOne(cascade = CascadeType.ALL)
	private Board board;
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Cell> cells;

	public Category(String name) {
		this.name = name;
		this.cells = new ArrayList<>();
	}

	public void addCell(String question, Integer value) {
		if (cells.size() >= 5) {
			throw new CategoryFullException("Cannot add cell: category is already full (size = " + cells.size() + ")");
		}
		cells.add(new Cell(this, question, value));
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		String prefix = "";
		for (Cell cell : cells) {
			stringBuilder.append(prefix);
			prefix = ", ";
			stringBuilder.append('\'').append(cell).append('\'');
		}
		stringBuilder.append("}");
		return "Category{" +
				"categoryName='" + name + '\'' +
				"cells=" + stringBuilder +
				'}';
	}
}
