package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import imagecompute.ImageConnector;
import imagecompute.WebcamGrabber;

public class SampleController {
	
	@FXML
	private ImageView imgColour;
	
	@FXML
	private ImageView imgBW;
	
	@FXML
	private ImageView imgSobel;
	
	@FXML
	private ImageView imgHough;
	
	@FXML
	private Button btnCamera;
	
	private boolean cameraUp = false;
	private ScheduledExecutorService timer;
	
	private WebcamGrabber wg;
	
	@FXML
	protected void startCamera(ActionEvent event)
	{
		System.out.println("Stuff");
		ImageConnector.setImageView(imgColour, imgBW, imgSobel, imgHough);
		if(!cameraUp)
		{
			wg = new WebcamGrabber();
			timer = Executors.newSingleThreadScheduledExecutor();
			timer.scheduleAtFixedRate(wg, 0, 33, TimeUnit.MILLISECONDS);
			cameraUp = true;
		}
	}
	
	private void killCamera()
	{
		if(this.timer!=null && !this.timer.isShutdown())
		{
			try
			{
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
				System.out.println("Kill");
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	protected void setClosed()
	{
		System.out.println("KillCamera");
		killCamera();
		wg.kill();
	}
	
}
