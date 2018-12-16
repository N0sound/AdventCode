package com.alex.adventCode;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayEight {

	private static final String DELIMITER = " ";
	private int idCounter = 65;

	public int findValueOfRoot(File input) throws IOException {
		String[] metaData = Files.lines(Paths.get(input.getAbsolutePath())).map(line -> line.split(DELIMITER))
				.collect(toSet()).iterator().next();
		Node rootNode = buildTree(metaData);
		return getValue(rootNode);
	}

	private int getValue(Node node) {
		int value = 0;
		List<Node> children = node.children;
		if (children.isEmpty()) {
			for (MetaDataEntry metaData : node.metaData) {
				value = value + metaData.value;
			}
		} else {
			int size = children.size();
			for (MetaDataEntry metaData : node.metaData) {
				int childPosition = metaData.value - 1;
				if (childPosition > -1 && childPosition < size) {
					Node childNode = children.get(childPosition);
					value = value + getValue(childNode);
				}
			}
		}
		return value;
	}

	public int findSumOfMetaData(File input) throws IOException {
		String[] metaData = Files.lines(Paths.get(input.getAbsolutePath())).map(line -> line.split(DELIMITER))
				.collect(toSet()).iterator().next();
		Node rootNode = buildTree(metaData);
		return getSumOfMetaData(rootNode);
	}

	private Node buildTree(String[] number) {
		Node rootNode = new Node(generateId(idCounter++));
		addNodeDetail(number, rootNode, 0);
		return rootNode;
	}

	private String generateId(int id) {
		return Character.toString((char) id);
	}

	private int addNodeDetail(String[] number, Node node, int position) {
		int numberOfChildren = Integer.valueOf(number[position++]);
		int numberOfMetaData = Integer.valueOf(number[position++]);
		if (numberOfChildren == 0) {
			position = addMetaData(number, position, numberOfMetaData, node);
		} else {
			for (int i = 0; i < numberOfChildren; i++) {
				position = createAndAddNode(number, position, node);
			}
			position = addMetaData(number, position, numberOfMetaData, node);
		}
		return position;
	}

	private int createAndAddNode(String[] number, int startingPosition, Node parentNode) {
		int position = startingPosition;
		Node node = new Node(generateId(idCounter++));
		parentNode.addChild(node);
		return addNodeDetail(number, node, position);
	}

	private int addMetaData(String[] number, int position, int numberOfMetaData, Node node) {
		for (int n = 0; n < numberOfMetaData; n++) {
			node.addMetaData(new MetaDataEntry(Integer.valueOf(number[position++])));
		}
		return position;
	}

	private int getSumOfMetaData(Node node) {
		int sum = 0;
		for (MetaDataEntry metaData : node.metaData) {
			sum = sum + metaData.value;
		}
		for (Node child : node.children) {
			sum = sum + getSumOfMetaData(child);
		}
		return sum;
	}

	private static class Node {

		private final String id;
		private List<Node> children = new ArrayList<>();
		private List<MetaDataEntry> metaData = new ArrayList<>();

		public Node(String id) {
			this.id = id;
		}

		public void addChild(Node child) {
			children.add(child);
		}

		public void addMetaData(MetaDataEntry entry) {
			metaData.add(entry);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}

	private static class MetaDataEntry {
		private final int value;

		public MetaDataEntry(int value) {
			this.value = value;
		}
	}
}
