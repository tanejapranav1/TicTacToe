package Lecture24;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TTT extends JFrame implements ActionListener {
	public static final int BOARD_SIZE = 3;
	public JButton[][] board = new JButton[BOARD_SIZE][BOARD_SIZE];
	
	private enum GameStatus {
		XWins,
		ZWins,
		Tie,
		Incomplete
	}
	
	private boolean crossTurn = true;
	
	public TTT(){
		super.setTitle("Tic Tac Toe");
		super.setSize(800, 800);
		super.setResizable(false);
		
		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(layout);
		
		Font font = new Font("Times New Roman", 1, 250);
		for(int row = 0; row < BOARD_SIZE; row++){
			for(int col = 0; col < BOARD_SIZE; col++){
				JButton button = new JButton("");
				
				button.setFont(font);
				button.addActionListener(this);
				
				board[row][col] = button;
				super.add(button);
			}
		}
		
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton)e.getSource();
		
		makeMove(clickedButton);
		GameStatus gs = getGameStatus();
		
		if(gs != GameStatus.Incomplete){
			declareWinner(gs);
			
			int selected = JOptionPane.showConfirmDialog(this, "Restart?");
			
			if(selected == JOptionPane.YES_OPTION){
				for(int row = 0; row < BOARD_SIZE; row++){
					for(int col = 0; col < BOARD_SIZE; col++){
						board[row][col].setText("");
					}
				}
				
				crossTurn = true;
			} else {
				super.dispose();
			}
		}
	}
	
	// makes the move - 0 or X or invalid
	public void makeMove(JButton btn){
		if(btn.getText().length() > 0){
			JOptionPane.showMessageDialog(this, "Invalid Move");
			return;
		}
		
		if(crossTurn){
			btn.setText("X");
		} else {
			btn.setText("0");
		}
		
		crossTurn = !crossTurn;
	}
	
	// x wins, z wins, tie, incomplete
	public GameStatus getGameStatus(){
		int row = 0, col = 0;
		String text1 = "", text2 = "";
		
		// check in rows for a win
		row = 0;
		while(row < BOARD_SIZE){
			col = 0;
			
			while(col < BOARD_SIZE - 1){
				text1 = board[row][col].getText();
				text2 = board[row][col + 1].getText();
				
				if(!text1.equals(text2) || text1.length() == 0){
					break;
				}
				
				col++;
			}
			
			if(col == BOARD_SIZE - 1){
				return text1.equals("X")? GameStatus.XWins: GameStatus.ZWins;
			}
			
			row++;
		}
		
		// check in columns for a win
		col = 0;
		while(col < BOARD_SIZE){
			row = 0;
			while(row < BOARD_SIZE - 1){
				text1 = board[row][col].getText();
				text2 = board[row + 1][col].getText();
				
				if(!text1.equals(text2) || text1.length() == 0){
					break;
				}
				
				row++;
			}
			
			if(row == BOARD_SIZE - 1){
				return text1.equals("X")? GameStatus.XWins: GameStatus.ZWins;
			}
			
			col++;
		}
		
		// check in diagonal one (row++, col++)
		row = 0;
		col = 0;
		while(row < BOARD_SIZE - 1){
			text1 = board[row][col].getText();
			text2 = board[row + 1][col + 1].getText();
			
			if(!text1.equals(text2) || text1.length() == 0){
				break;
			}
			
			row++;
			col++;
		}
		
		if(row == BOARD_SIZE - 1){
			return text1.equals("X")? GameStatus.XWins: GameStatus.ZWins;
		}
		
		// check in diagonal two (row++, col--)
		row = 0;
		col = BOARD_SIZE - 1;
		while(row < BOARD_SIZE - 1){
			text1 = board[row][col].getText();
			text2 = board[row + 1][col - 1].getText();
			
			if(!text1.equals(text2) || text1.length() == 0){
				break;
			}
			
			row++;
			col--;
		}
		
		if(row == BOARD_SIZE - 1){
			return text1.equals("X")? GameStatus.XWins: GameStatus.ZWins;
		}
		
		// check for incompleteness
		row = 0;
		while(row < BOARD_SIZE){
			col = 0;
			while(col < BOARD_SIZE){
				text1 = board[row][col].getText();
				
				if(text1.length() == 0){
					return GameStatus.Incomplete;
				}
				
				col++;
			}
			
			row++;
		}
		
		return GameStatus.Tie;
	}
	
	public void declareWinner(GameStatus gs){
		if(gs == GameStatus.XWins){
			JOptionPane.showMessageDialog(this, "X wins");
		} else if(gs == GameStatus.ZWins){
			JOptionPane.showMessageDialog(this, "0 wins");
		} else if(gs == GameStatus.Tie){
			JOptionPane.showMessageDialog(this, "It's a tie");
		}
	}
	
	
}
