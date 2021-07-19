package com.osmosis.jeopardyserver.tools;

import com.osmosis.jeopardyserver.entities.Board;
import com.osmosis.jeopardyserver.entities.Category;
import com.osmosis.jeopardyserver.exceptions.FileFormatException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardParser {
	private final Scanner scanner;
	private final List<Board> boards;
	private boolean parsed;

	public BoardParser(InputStream inputStream) {
		this.scanner = new Scanner(inputStream);
		this.boards = new ArrayList<>(2);
		this.parsed = false;
	}

	public void parse() {
		try {
			Board board = new Board();
			for (int categoryNumber = 0; categoryNumber < 6; categoryNumber++) {
				if (!scanner.hasNextLine()) {
					throw new FileFormatException("Invalid number of categories");
				}
				Category category = new Category(scanner.nextLine());
				for (int cellNumber = 0; cellNumber < 5; cellNumber++) {
					if (!scanner.hasNextLine()) {
						throw new FileFormatException("Invalid number of cells (questions)");
					}
					String[] line = scanner.nextLine().split("\t");
					if (line.length != 2) {
						throw new FileFormatException("Cell must contain value and question separated by a tab");
					}
					Integer value = Integer.parseInt(line[0]);
					String question = line[1];
					category.addCell(question, value);
				}
				board.addCategory(category);
			}
			boards.add(board);
		} catch (Exception exception) {
			throw new FileFormatException("File not formatted properly: " + exception.getMessage());
		}
		this.parsed = true;
	}

	public Board getFirstBoard() {
		if (!parsed) {
			parse();
		}
		return boards.get(0);
	}

	public Board getSecondBoard() {
		if (!parsed) {
			parse();
		}
		return boards.get(1);
	}
}
