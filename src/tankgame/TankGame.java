package tankgame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;




import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;


import java.awt.Font;
import java.io.InputStream;

public final class TankGame extends JFrame {
	
	JComponent gamePanel1, gamePanel2;
	JComponent thumbPanel;
	HealthBar energy1, energy2;
	JLabel live1, live2;


	public TankGame() {
        this.runnable = () -> {
            try {
                while (true) {
                    
                    
                    world.update();
                    
                    if (world.gameover) {
                        break;
                    }
                    
                    
                    gamePanel1.repaint();
                    gamePanel2.repaint();
                    
                    thumbPanel.repaint();
                    
                    
                    energy1.setCurrent(world.player1.getEnergy());
                    energy2.setCurrent(world.player2.getEnergy());
                    
                    live1.setText(world.player1Live + "");
                    live2.setText(world.player2Live + "");
                    
                    energy1.repaint();
                    energy2.repaint();
                    
                    Thread.sleep(1000 / 144);
                }
                
                JOptionPane.showMessageDialog(TankGame.this, "GAME OVER");
                TankGame.this.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(TankGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
		setTitle("TANK GAME");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1010, 700);
		this.setResizable(false);
		setLocationRelativeTo(null);

		init();

		JPanel contentPane = new JPanel();

		thumbPanel = new ThumbPanel();
		thumbPanel.setBounds(400, 350, 200, 200);
		contentPane.add(thumbPanel);

		contentPane.setLayout(null);
		gamePanel1 = new GamePanel1();
		gamePanel2 = new GamePanel2();
		gamePanel1.setBounds(0, 0, 500, 600);
		gamePanel2.setBounds(500, 0, 500, 600);
		contentPane.add(gamePanel1);
		contentPane.add(gamePanel2);

		setContentPane(contentPane);

		live1 = new JLabel("3");
		live1.setFont(new Font("Arial", Font.PLAIN, 25));
		live1.setHorizontalAlignment(SwingConstants.CENTER);
		live1.setBounds(14, 614, 72, 26);
		contentPane.add(live1);

		live2 = new JLabel("3");
		live2.setHorizontalAlignment(SwingConstants.CENTER);
		live2.setFont(new Font("Arial", Font.PLAIN, 25));
		live2.setBounds(903, 614, 72, 26);
		contentPane.add(live2);

		energy1 = new HealthBar(Settings.totalEnergy, Settings.totalEnergy);
		energy1.setBounds(81, 622, 313, 18);
		contentPane.add(energy1);

		energy2 = new HealthBar(Settings.totalEnergy, Settings.totalEnergy);
		energy2.setBounds(590, 622, 313, 18);
		contentPane.add(energy2);

		startUpdateThread();
	}


	World world;
	TankControl control1, control2;

	
	public void init() {

		world = new World();
		world.tankGame = this;
		
		control1 = new TankControl(world.getPlayer1(), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
				KeyEvent.VK_Q

		);
		addKeyListener(control1);

		control2 = new TankControl(world.getPlayer2(), KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
		addKeyListener(control2);
	}

    private static class Player {

        public Player() {
        }

        private Player(InputStream resourceAsStream) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void play() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

	
	class GamePanel1 extends JComponent {
		public GamePanel1() {

		}

		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			drawBackground(graphics, getWidth(), getHeight());
			world.paint(graphics, world.vPoint1);
		}
	}

	
	class GamePanel2 extends JComponent {
		public GamePanel2() {

		}

		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			drawBackground(graphics, getWidth(), getHeight());
			world.paint(graphics, world.vPoint2);
		}
	}

	 
	class ThumbPanel extends JComponent {
		public ThumbPanel() {
			setBackground(null);
		}

		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			world.paint(graphics, getWidth(), getHeight());
		}
	}

	
	public void drawBackground(Graphics graphics, int width, int height) {
		int imageWidth = GameResource.background.getWidth(null);
		int imageHeight = GameResource.background.getHeight(null);
		int rows = height / imageHeight + 1;
		int cols = width / imageWidth + 1;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				graphics.drawImage(GameResource.background, j * imageWidth, i * imageHeight, null);
			}
		}
	}

	
	Runnable runnable;

	Player player;

	void play() {
		Runnable playRunnable;
            playRunnable = () -> {
                try {
                    while (true) {
                        player = new Player(TankGame.this.getClass().getResourceAsStream(GameResource.musicFile));
                        player.play();
                        System.out.println("PLAY FINISH");
                    }
                }catch (Exception e) {
                    System.out.println(e);
                }
                };
		(new Thread(playRunnable)).start();
	}

	public void playBullet(){
		Runnable playRunnable;
            playRunnable = () -> {
                try {
                    Player player1 = new Player(getClass().getResourceAsStream(GameResource.musicBulletFile));
                    player1.play();
                }catch (Exception e) {
                    System.out.println("bullet: " + e);
                }
            };
		(new Thread(playRunnable)).start();
	}
	
	public void startUpdateThread() {
		play();
		new Thread(runnable).start();
	}

	public static void main(String[] args) {
		GameResource.load();
		TankGame trex = new TankGame();
		trex.setVisible(true);
	}
}
