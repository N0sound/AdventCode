package com.alex.adventCode;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DaySeven {

	private static final String DELIMITER = " ";

	public String getInstructionSteps(File input) throws IOException {
		Set<String[]> lines = Files.lines(Paths.get(input.getAbsolutePath())).map(line -> line.split(DELIMITER))
				.collect(toSet());
		Map<String, Node> idToNodesMap = getIdToNodes(lines);
		return getOrderedIds(idToNodesMap);
	}

	public int getTimeToCompleteInstructions(File input, int numberOfExecutors, int time) throws IOException {
		Set<String[]> lines = Files.lines(Paths.get(input.getAbsolutePath())).map(line -> line.split(DELIMITER))
				.collect(toSet());
		Map<String, Node> idToNodesMap = getIdToNodes(lines, time);
		return getDuration(idToNodesMap, numberOfExecutors);
	}

	private int getDuration(Map<String, Node> idToNodesMap, int numberOfExecutors) {
		int numberOfInstructions = idToNodesMap.size();
		List<String> availableInstructions = new ArrayList<>();
		addReadyNodes(idToNodesMap, availableInstructions);

		List<Executor> executors = createExecutors(numberOfExecutors);
		int completed = 0;
		int seconds;
		for (seconds = 0; seconds < Integer.MAX_VALUE; seconds++) {
			System.out.println("Start of iteration: time=" + seconds + ", availableTasks=" + availableInstructions);
			for (Executor executor : executors) {
				Node finishedNode = executor.getFinishedNode();
				if (finishedNode != null) {
					System.out.println(
							String.format("Finished Task: executor=%d; task=%s", executor.id, finishedNode.id));
					finishedNode.setCompleted();
					completed++;
					addReadyNodes(idToNodesMap, availableInstructions);
				}
				if (executor.isReady()) {
					if (!availableInstructions.isEmpty()) {
						String id = availableInstructions.remove(0);
						System.out.println(
								String.format("Assigning and Execute Task: executor=%d; task=%s", executor.id, id));
						executor.assignNode(idToNodesMap.get(id));
					}
				} else {
					System.out.println(
							String.format("Executing Task: executor=%d; task=%s", executor.id, executor.node.id));
					executor.executeForOneSecond();
				}
			}
			System.out.println("End of iteration: availableTasks=" + availableInstructions + "\n");
			if (completed == numberOfInstructions) {
				break;
			}
		}
		return seconds;

	}

	private List<Executor> createExecutors(int numberOfExecutors) {
		List<Executor> executors = new ArrayList<>();
		for (int n = 0; n < numberOfExecutors; n++) {
			executors.add(new Executor(n));
		}
		return executors;
	}

	private String getOrderedIds(Map<String, Node> idToNodesMap) {
		int numberOfInstructions = idToNodesMap.size();
		List<String> availableInstructions = new ArrayList<>();
		addReadyNodes(idToNodesMap, availableInstructions);

		StringBuilder ids = new StringBuilder();
		while (ids.length() < numberOfInstructions) {
			availableInstructions.sort((s1, s2) -> s1.compareTo(s2));
			String id = availableInstructions.remove(0);
			Node instruction = idToNodesMap.get(id);
			ids.append(id);
			instruction.setCompleted();
			addReadyNodes(idToNodesMap, availableInstructions);
		}
		return ids.toString();
	}

	private void addReadyNodes(Map<String, Node> idToNodesMap, List<String> availableInstructions) {
		for (Map.Entry<String, Node> entry : idToNodesMap.entrySet()) {
			String id = entry.getKey();
			Node instruction = entry.getValue();

			if (instruction.isCompleted || instruction.isReady) {
				continue;
			}

			boolean isReady = true;
			for (String parentId : instruction.parents) {
				Node parentInstruction = idToNodesMap.get(parentId);
				if (!parentInstruction.isCompleted) {
					isReady = false;
				}
			}
			if (isReady) {
				instruction.setReady();
				availableInstructions.add(id);
			}
		}
		availableInstructions.sort((s1, s2) -> s1.compareTo(s2));
	}

	private Map<String, Node> getIdToNodes(Set<String[]> lines) {
		return getIdToNodes(lines, 0);
	}

	private Map<String, Node> getIdToNodes(Set<String[]> lines, int time) {
		Map<String, Node> idToNodesMap = new LinkedHashMap<>();

		for (String[] line : lines) {
			String parentId = line[1];
			String childId = line[7];

			Node parentNode = idToNodesMap.get(parentId);
			if (parentNode == null) {
				parentNode = new Node(parentId, time);
				parentNode.addChild(childId);
				idToNodesMap.put(parentId, parentNode);
			} else {
				parentNode.addChild(childId);
			}

			Node childNode = idToNodesMap.get(childId);
			if (childNode == null) {
				childNode = new Node(childId, time);
				childNode.addParent(parentId);
				idToNodesMap.put(childId, childNode);
			} else {
				childNode.addParent(parentId);
			}
		}
		return idToNodesMap;
	}

	private static class Executor {

		private final int id;
		private Node node;

		public Executor(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return String.valueOf(id);
		}

		public Node getFinishedNode() {
			if (node != null) {
				if (node.isDone()) {
					Node tempNode = node;
					node = null;
					return tempNode;
				}
			}
			return null;
		}

		public boolean isReady() {
			if (node == null) {
				return true;
			}
			return false;
		}

		public void assignNode(Node node) {
			this.node = node;
			node.executeForOneSecond();
		}

		public void executeForOneSecond() {
			node.executeForOneSecond();
		}
	}

	private static class Node {

		private final List<String> children = new ArrayList<>();
		private final List<String> parents = new ArrayList<>();
		private final String id;
		private int timeToComplete;

		private boolean isCompleted = false;
		private boolean isReady = false;

		public Node(String id, int time) {
			this.id = id;
			timeToComplete = id.charAt(0) - time;
		}

		@Override
		public String toString() {
			return id;
		}

		public void addChild(String id) {
			children.add(id);
		}

		public void addParent(String id) {
			parents.add(id);
		}

		public void setReady() {
			isReady = true;
		}

		public void setCompleted() {
			isCompleted = true;
		}

		public boolean isDone() {
			return timeToComplete == 0;
		}

		public void executeForOneSecond() {
			timeToComplete--;
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
}
