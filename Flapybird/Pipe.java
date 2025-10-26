import java.awt.*;

public class Pipe {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    boolean passed;


    public Pipe(int posX, int posY, int width, int height,Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        this.velocityX = 0;
        this.passed = false;
    }
    //getter

    public int getPosX(){
        return posX;
    }

    public  int getPosY(){
        return posY;
    }

    public  int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getVelocityX(){
        return velocityX;
    }

    public Image getImage(){
        return image;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    //setter

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setVelocityX(int velocityX){
        this.velocityX = velocityX;
    }


}

