package main;

//A. Stewart- 
//G. Watson-
//Chevis Hutchinson -1601446

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Driver extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel parseDataLabel, sortLabel, searchLabel, addWordLabel, printWordsLabel, validateLabel;
	private JTextField searchField, addWordField, validateField;
	private JButton fileButton, sortButton, printWordsButton;
	private JFileChooser fChooser;
	private LinkedList linkedList;
	private Tree tree;
	private NumberFormat numberFormatter;

	private void printWordsButtonOptions() {
		this.printWordsButton.setEnabled(false);
		this.showAllItemsInTheDictionary();
	}

	private void showAllItemsInTheDictionary() {
		this.setVisible(false);

		JFrame frame = new JFrame();
		frame.setSize(900, 350);
		frame.setLayout(new GridLayout(1, 1));
		frame.setLocationRelativeTo(this);

		DefaultTableModel tableModel = new DefaultTableModel();

		tableModel.addColumn("Words from List");
		tableModel.addColumn("POS from List");
		tableModel.addColumn("Def from List");
		tableModel.addColumn("");
		tableModel.addColumn("Words from Tree");
		tableModel.addColumn("POS from Tree");
		tableModel.addColumn("Def from Tree");

		JTable table = new JTable(tableModel);
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		long treeStartTime = System.currentTimeMillis();
		LinkedList linkedList = this.tree.getAll();
		long treeEndTime = System.currentTimeMillis();

		Node temp = this.linkedList.getHeadNode();
		Node temp2 = linkedList.getHeadNode();
		long linkedListStartTime = System.currentTimeMillis();

		while ((temp != null) && (temp2 != null)) {
			String data[] = { temp.getData().getWord(), temp.getData().getPartOfSpeech(),
					temp.getData().getDefinition(), "", temp2.getData().getWord(), temp2.getData().getPartOfSpeech(),
					temp2.getData().getDefinition() };
			tableModel.addRow(data);

			temp = temp.getNextNode();
			temp2 = temp2.getNextNode();
		}

		long linkedListEndTime = System.currentTimeMillis();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				printWordsButton.setEnabled(true);
				linkedList.destroy();
				setVisible(true);
			}
		});
		frame.add(scroll);
		frame.setVisible(true);
		frame.setTitle("Data from Data Structures.     " + "LinkedList Time is: "
				+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d) + " secs."
				+ "     Tree time is: " + numberFormatter.format((treeEndTime - treeStartTime) / 1000d) + " secs.");
	}

	public static String upperCaseFirstLetter(String word) {
		char firstLetterInWord = word.charAt(0);

		if (Character.isLowerCase(firstLetterInWord)) {
			firstLetterInWord = Character.toUpperCase(firstLetterInWord);
			word = firstLetterInWord + word.substring(1);
		}

		return word;
	}

	public static final String parsePOS(String pos) {
		if (pos.equals("a")) {
			pos = "Adjective";
		} else if (pos.equals("n")) {
			pos = "Noun";
		} else if (pos.equals("pr")) {
			pos = "Pronoun";
		} else if (pos.equals("v")) {
			pos = "Verb";
		} else if (pos.equals("i")) {
			pos = "Interjection";
		}

		return pos;
	}

	public static boolean checkPOS(String pos) {
		boolean bool = false;
		String partsOfSpeech[] = { "Noun", "Pronoun", "Verb", "Adjective", "Adverb", "Conjunction", "Preposition",
				"Interjection" };

		for (String text : partsOfSpeech) {
			if (text.equals(pos)) {
				bool = true;
			}
		}

		return bool;
	}

	private void search(String word) {
		word = Driver.upperCaseFirstLetter(word);

		long treeStartTime = System.currentTimeMillis();
		Data data1 = this.tree.search(word);
		long treeEndTime = System.currentTimeMillis();

		long linkedListStartTime = System.currentTimeMillis();
		Data data2 = this.linkedList.search(word);
		long linkedListEndTime = System.currentTimeMillis();

		if ((data1 != null) && (data2 != null)) {
			searchField.setText("");

			this.showMessageDialog(data2.getWord() + " was found\nPart of Speech: " + data2.getPartOfSpeech()
					+ "\nDefinition: " + data2.getDefinition() + "\nFound at index: " + data2.getFoundIndex()
					+ " in linkedlist\nTree Time: " + numberFormatter.format((treeEndTime - treeStartTime) / 1000d)
					+ " secs." + "\nLinkedList Time: "
					+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d) + " secs.");
		} else {
			this.showMessageDialog(word + " was not found");
		}
	}

	private void fileButtonOptions() {
		this.fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			this.tree.read(fChooser.getSelectedFile().getAbsolutePath().toString());

			long treeStartTime = System.currentTimeMillis();
			this.tree.read(fChooser.getSelectedFile().getAbsolutePath().toString());
			long treeEndTime = System.currentTimeMillis();

			long linkedListStartTime = System.currentTimeMillis();
			this.linkedList.read(fChooser.getSelectedFile().getAbsolutePath().toString());
			long linkedListEndTime = System.currentTimeMillis();

			this.showMessageDialog(
					"Time to load tree is: " + numberFormatter.format((treeEndTime - treeStartTime) / 1000d)
							+ " secs.\n" + "Time to load linkedlist is: "
							+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d) + " secs.");
		}
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private void sortButtonOptions() {
		long treeStartTime = System.currentTimeMillis();
		this.tree.sort();
		this.tree.setSorted(true);
		long treeEndTime = System.currentTimeMillis();

		long linkedListStartTime = System.currentTimeMillis();
		this.linkedList.sort();
		long linkedListEndTime = System.currentTimeMillis();

		JOptionPane.showMessageDialog(this,
				"Time to sort tree is: " + numberFormatter.format((treeEndTime - treeStartTime) / 1000d) + " seconds\n"
						+ "Time to sort linkedlist is: = "
						+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d) + " seconds\n");
	}

	public Driver() {
		this.fileButton = new JButton("Select Dictionary File");
		this.sortButton = new JButton("Sort!");
		this.printWordsButton = new JButton("Show Words");

		this.fChooser = new JFileChooser();

		this.searchField = new JTextField("");
		this.addWordField = new JTextField("");
		this.validateField = new JTextField("");

		this.parseDataLabel = new JLabel("Parse Data:");
		this.sortLabel = new JLabel("Sort:");
		this.searchLabel = new JLabel("Search:");
		this.addWordLabel = new JLabel("Add Word:");
		this.printWordsLabel = new JLabel("Print Words:");
		this.validateLabel = new JLabel("Validate Sentence:");

		this.panel = new JPanel(new GridLayout(6, 2));

		this.panel.add(this.parseDataLabel);
		this.panel.add(this.fileButton);
		this.panel.add(this.addWordLabel);
		this.panel.add(this.addWordField);
		this.panel.add(this.searchLabel);
		this.panel.add(this.searchField);
		this.panel.add(this.sortLabel);
		this.panel.add(this.sortButton);
		this.panel.add(this.printWordsLabel);
		this.panel.add(this.printWordsButton);
		this.panel.add(this.validateLabel);
		this.panel.add(this.validateField);

		this.add(this.panel);

		this.fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileButtonOptions();
			}
		});
		this.sortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortButtonOptions();
			}
		});
		this.printWordsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printWordsButtonOptions();
			}
		});

		this.searchField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				char character = event.getKeyChar();
				if (((character < 'a') || (character > 'z')) && (character != KeyEvent.VK_BACK_SPACE)) {
					event.consume();
				}
			}

			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (searchField.getText().toString().trim().isEmpty()) {
						JOptionPane.showMessageDialog(Driver.this, "Enter a valid word");
					} else {
						search(searchField.getText().toString());
					}
				}
			}
		});

		this.addWordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				char character = event.getKeyChar();
				if (((character < 'a') || (character > 'z')) && (character != KeyEvent.VK_BACK_SPACE)) {
					event.consume();
				}
			}

			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (addWordField.getText().toString().trim().isEmpty()) {
						JOptionPane.showMessageDialog(Driver.this, "Enter a valid word");
					} else {
						String word = addWordField.getText().trim();

						if (linkedList.search(word) == null) {
							word = upperCaseFirstLetter(word);
							String def = JOptionPane.showInputDialog("Enter a definition for: " + word);

							if (def != null) {
								if (!def.trim().isEmpty()) {
									def = upperCaseFirstLetter(def);

									String partOfSpeech = JOptionPane
											.showInputDialog("Enter the part of speech for: " + word);

									if (partOfSpeech != null) {
										if (!partOfSpeech.trim().isEmpty()) {
											partOfSpeech = upperCaseFirstLetter(partOfSpeech);

											if (Driver.checkPOS(partOfSpeech)) {
												addWordField.setText("");

												long treeStartTime = System.currentTimeMillis();
												tree.insertNode(new Data(word, def, partOfSpeech));
												long treeEndTime = System.currentTimeMillis();

												long linkedListStartTime = System.currentTimeMillis();
												linkedList.insertAtBack(new Data(word, def, partOfSpeech));
												long linkedListEndTime = System.currentTimeMillis();

												showMessageDialog(word + " was inserted\nTree time is: "
														+ numberFormatter.format((treeEndTime - treeStartTime) / 1000d)
														+ " secs." + "\nLinkedList time is: "
														+ numberFormatter.format(
																(linkedListEndTime - linkedListStartTime) / 1000d)
														+ " secs.");
											} else {
												showMessageDialog("Invalid part of speech for: " + word);
											}
										}
									} else {
										showMessageDialog("Enter a part of speech");
									}
								} else {
									showMessageDialog("Enter a definition");
								}
							} else {
								showMessageDialog("Enter a definition");
							}
						} else {
							showMessageDialog("The word: " + word + " already exists");
						}
					}
				}
			}
		});

		this.validateField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				char character = event.getKeyChar();
				if (character == KeyEvent.VK_SPACE) {
					char array[] = validateField.getText().toCharArray();

					if (array.length > 0) {
						if (array[array.length - 1] == ' ') {
							event.consume();
						}
					}
				} else {
					if (((character < 'a') || (character > 'z')) && (character != KeyEvent.VK_BACK_SPACE)) {
						event.consume();
					}
				}
			}

			public void keyPressed(KeyEvent event) {

				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (validateField.getText().toString().trim().isEmpty()) {
						JOptionPane.showMessageDialog(Driver.this, "Enter a valid word");
					} else {
						boolean foundWord = false;
						long treeStartTime = System.currentTimeMillis();
						long linkedListStartTime = System.currentTimeMillis();
						long linkedListEndTime = System.currentTimeMillis();
						long treeEndTime = System.currentTimeMillis();

						String array[] = validateField.getText().trim().split(" ");

						for (String word : array) {
							if ((linkedList.search(word) == null)) {
								foundWord = true;
								word = upperCaseFirstLetter(word);
								int result = JOptionPane.showConfirmDialog(Driver.this,
										word + " does exist.\nDo you want to add it to the list?\n1 -> Yes\n2 -> No");

								if (result == JOptionPane.OK_OPTION) {
									String def = JOptionPane.showInputDialog("Enter definition for: " + word);

									if (def != null) {
										if (!def.trim().isEmpty()) {
											def = upperCaseFirstLetter(def);

											String pos = JOptionPane
													.showInputDialog("Enter part of speech for: " + word);

											if (pos != null) {
												if (!pos.trim().isEmpty()) {
													pos = upperCaseFirstLetter(pos);

													if (Driver.checkPOS(pos)) {
														validateField.setText("");

														linkedList.insertAtBack(new Data(word, def, pos));
														tree.insertNode(new Data(word, def, pos));
														linkedListEndTime = System.currentTimeMillis();
														 treeEndTime = System.currentTimeMillis();

													} else {
														showMessageDialog("Invalid part of speech");
													}
												}
											} else {
												showMessageDialog("Enter a part of speech");
											}
										}
									} else {
										showMessageDialog("Enter a definition");
									}
								}
							}
						}

						if (!foundWord) {
							showMessageDialog("No new words\nTime for validating sentence is: "
									+ numberFormatter.format((treeEndTime - treeStartTime) / 1000d) + " secs."
									+ "\nLinkedList time is: "
									+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d)
									+ " secs.");
						} else {
							showMessageDialog("Time to validate is: "
									+ numberFormatter.format((treeEndTime - treeStartTime) / 1000d) + " secs."
									+ "\nLinkedList time is: "
									+ numberFormatter.format((linkedListEndTime - linkedListStartTime) / 1000d)
									+ " secs.");
						}
					}
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				linkedList.destroy();
				tree.destroy(tree.getRoot());
			}
		});

		this.setResizable(false);
		this.setSize(350, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 1));
		this.setTitle("Dictionary");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.numberFormatter = new DecimalFormat("#0.00000");
		this.linkedList = new LinkedList();
		this.tree = new Tree();
	}

	public static void main(String[] args) {
		new Driver();
	}
}