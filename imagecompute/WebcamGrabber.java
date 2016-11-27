package imagecompute;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class WebcamGrabber implements Runnable{
	
	private VideoCapture capture = new VideoCapture();
	private static int cameraID = 0;
	
	public WebcamGrabber()
	{
		this.capture.open(cameraID);
	}
	
	public void run()
	{
		try
		{

			Mat frame = new Mat();
			capture.read(frame);
			ImageConnector.setImage(frame);
			ImageConnector.updateImages();
			//Thread.sleep(33);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void kill()
	{
		capture.release();
	}
}
