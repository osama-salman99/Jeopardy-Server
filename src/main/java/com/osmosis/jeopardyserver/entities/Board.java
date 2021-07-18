package com.osmosis.jeopardyserver.entities;

import com.osmosis.jeopardyserver.exceptions.BoardFullException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
@Getter
@Setter
public class Board {
	@Id
	@Setter(AccessLevel.NONE)
	@SequenceGenerator(
			name = "board_sequence",
			sequenceName = "board_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "board_sequence"
	)
	private Long id;
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Category> categories;

	public Board() {
		this.categories = new ArrayList<>();
	}

	public void addCategory(Category category) {
		if (categories.size() >= 6) {
			throw new BoardFullException("Cannot add category: Board already full (size = " + categories.size() + ")");
		}
		category.setBoard(this);
		categories.add(category);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		String prefix = "";
		for (Category category : categories) {
			stringBuilder.append(prefix);
			prefix = ", ";
			stringBuilder.append('\'').append(categories).append('\'');
		}
		stringBuilder.append("}");
		return "Board{" +
				"categories:" + stringBuilder +
				"}";
	}
}
