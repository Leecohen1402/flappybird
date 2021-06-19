import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameScene extends JPanel {
    private int sceneId;
    private Player bird;
    private ImageIcon player;
    private Obstacles obstacles;
    private ObstacleOne obstacleOne;
    private ObstacleTwo obstaclesTwo;
    private ObstacleThree obstacleThree;
    private JLabel welcome = new JLabel();
    private JLabel gameRules1 = new JLabel();
    private JLabel gameRules2 = new JLabel();
    private JLabel gameRules3 = new JLabel();

    public GameScene() {
        this.setLayout(null);
        JButton iAgree = new JButton("Let's start!");
        iAgree.setBounds(Definition.BUTTON_X, Definition.BUTTON_Y, Definition.BUTTON_WIDTH, Definition.BUTTON_HIGHT);
        iAgree.addActionListener((event -> {
            this.sceneId = Definition.GAME_SCENE;
        }));
        this.add(iAgree);
        iAgree.setBackground(Color.CYAN);
        this.bird = new Player(300, 400);
        rules();
        this.player = new ImageIcon("images/flappy birds.jpg");
        Image image = player.getImage(); // transform it
        Image newimg = image.getScaledInstance(Definition.PLAYER_X4, Definition.PLAYER_Y4, Image.SCALE_SMOOTH); // scale it the smooth way
        player = new
                ImageIcon(newimg);  // transform it back
        this.obstacles = new Obstacles(300, 0, 50, 250);
        this.obstacleOne = new ObstacleOne(400, 400, 50, 250);
        this.obstaclesTwo = new ObstacleTwo(600, 0, 50, 300);
        this.obstacleThree = new ObstacleThree(800, 250, 50, 450);
        this.mainGameLoop();
    }

    public void mainGameLoop() {
        new Thread(() -> {
            while (true) {
                this.bird.moveRight();
                if (collision(bird,obstacles,obstacleOne,obstaclesTwo,obstacleThree)){
                    this.bird.setAlive(false);
                    //this.player.setX(0);
                }
                repaint();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        switch (this.sceneId) {
            case Definition.MENU:
                this.welcome.setVisible(true);
                this.gameRules1.setVisible(true);
                this.gameRules2.setVisible(true);
                this.gameRules3.setVisible(true);
                break;
            case Definition.GAME_SCENE:
                this.welcome.setVisible(false);
                this.gameRules1.setVisible(false);
                this.gameRules2.setVisible(false);
                this.gameRules3.setVisible(false);
                this.bird.paint(graphics, this);
                this.setBackground(Color.blue);
                this.player.paintIcon(this, graphics, 100, 100);
                this.obstacles.paint(graphics);
                this.obstacleOne.paint(graphics);
                this.obstaclesTwo.paint(graphics);
                this.obstacleThree.paint(graphics);

                //game.drawString("these are the instractions" , 50,50);
                break;
        }
        if (this.bird.isAlive())
            this.bird.paint(graphics);


    }

    private void rules() {
        welcome.setText("Welcome to Crazy bird ! ! rules of the game:");
        welcome.setBounds(Definition.TEXT_X, Definition.TEXT_Y, Definition.TEXT_WIDTH, Definition.TEXT_HIGHT);
        this.add(welcome);
        gameRules1.setText("1. use the arrows on the keyboard keys");
        gameRules1.setBounds(Definition.TEXT_X1, Definition.TEXT_Y1, Definition.TEXT_WIDTH1, Definition.TEXT_HIGHT1);
        this.add(gameRules1);
        gameRules2.setText("2. don't touch the walls or you lose!!");
        gameRules2.setBounds(Definition.TEXT_X2, Definition.TEXT_Y2, Definition.TEXT_WIDTH2, Definition.TEXT_HIGHT2);
        this.add(gameRules2);
        gameRules3.setText("3. point will be counted according to the obstacles you pass");
        gameRules3.setBounds(Definition.TEXT_X3, Definition.TEXT_Y3, Definition.TEXT_WIDTH3, Definition.TEXT_HIGHT3);
        this.add(gameRules3);
    }

    private boolean collision(Player player, Obstacles obstacles, ObstacleOne obstacleOne, ObstacleTwo obstacleTwo, ObstacleThree obstacleThree) {
        Rectangle playerRectangle = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHight());
        Rectangle rockRectangle = new Rectangle(obstacles.getX(), obstacles.getY(), obstacles.getWidth(), obstacles.getHight());
        boolean collision = playerRectangle.intersects(rockRectangle);
        return collision;

    }

}
