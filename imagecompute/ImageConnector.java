package imagecompute;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageConnector {
	
	private static Mat baseImage;
	private static Mat bw;
	private static Mat sobel;
	private static Mat hough;
	
	private static ImageView imgColour;
	private static ImageView imgBW;
	private static ImageView imgSobel;
	private static ImageView imgHough;
	
	private static Image colour;
	private static Image blacknwhite;
	private static Image isobel;
	private static Image ihough;
	
	public static void setImageView(ImageView colour, ImageView bw, ImageView sob, ImageView hou)
	{
		imgColour = colour;
		imgBW = bw;
		imgSobel = sob;
		imgHough = hou;
	}
	
	public static void setImage(Mat f)
	{
		baseImage = f;
		bw = baseImage.clone();
		
		Imgproc.cvtColor(bw, bw, Imgproc.COLOR_BGR2GRAY);
		sobel();
		hough();
	}
	
	private static void buildImages()
	{
		colour = custom.Utils.mat2Image(baseImage);
		blacknwhite = custom.Utils.mat2Image(bw); 
	}
	
	private static void sobel()
	{
		Mat ver = bw.clone();
		Mat hor = bw.clone();
		
		Imgproc.Sobel(bw, hor, -1, 1, 0);
		Imgproc.Sobel(bw, ver, -1, 0, 1);
		
		//System.out.println("sobel");
		sobel = new Mat(bw.rows(), bw.cols(), bw.type());
		//Imgproc.filter2D(bw, sobel, -1, kernel);
		org.opencv.core.Core.addWeighted(hor, 0.5, ver, 0.5, 1.0, sobel);
		//custom.Utils.mat2Image(sobel);
		
	}
	
	private static void drawCircles()
	{

	}
	
	private static void hough()
	{
		Imgproc.GaussianBlur(sobel, sobel, new Size(11,11), 2, 2);

		Mat circles = new Mat();
		int minRadius = 10;
		int maxRadius = 18;
		//Imgproc.HoughCircles(sobel, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minRadius, 120, 10, minRadius, maxRadius);
		Imgproc.HoughCircles(sobel, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 200);
		//System.out.println(circles);
		//hough = circles;
	    for (int i = 0; i < circles.cols(); i++) {
	        double[] vCircle = circles.get(0, i);

	        Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
	        int radius = (int)Math.round(vCircle[2]);
	        Imgproc.circle(baseImage, pt, radius, new Scalar(255, 255, 0), 2);
	        
	        
	        
	    }
		hough = circles;
	}
	

	public static void updateImages()
	{
		Image colour = custom.Utils.mat2Image(baseImage);
		Image black = custom.Utils.mat2Image(bw);
		Image sob = custom.Utils.mat2Image(sobel);
		//Image hou = custom.Utils.mat2Image(hough);
		imgColour.imageProperty().set(colour);
		//System.out.println("ImageUpdate");
		custom.Utils.onFXThread(imgBW.imageProperty(), black);
		//imgBW.imageProperty().set(black);
		custom.Utils.onFXThread(imgSobel.imageProperty(), sob);
		//custom.Utils.onFXThread(imgHough.imageProperty(), hou);
	}
	
}
