package com.ecsfin.rpp;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InputFile;

@Service
public class ScreenshotImpl implements Screenshot {

	@Override
	public void run() {
		try {
			System.setProperty("java.awt.headless", "false");
			Robot r = new Robot();
			String path = "D://Shot.jpg";
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			capture.setBounds(310, 230, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 925,
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 500);
			BufferedImage img1 = r.createScreenCapture(capture);
			System.out.println("Screenshot 1 Taken");
			Thread.sleep(30000);
			BufferedImage img2 = r.createScreenCapture(capture);
			ImageIO.write(img2, "jpg", new File(path));
			System.out.println("Screenshot 2 Taken");
			int w1 = img1.getWidth();
			int w2 = img2.getWidth();
			int h1 = img1.getHeight();
			int h2 = img2.getHeight();
			if ((w1 != w2) || (h1 != h2)) {
				System.out.println("Both images should have same dimwnsions");
			} else {
				long diff = 0;
				for (int j = 0; j < h1; j++) {
					for (int i = 0; i < w1; i++) {
						int pixel1 = img1.getRGB(i, j);
						int pixel2 = img2.getRGB(i, j);
						if (pixel1 != pixel2) {
							diff = diff + 1;
						}
					}
				}
				System.out.println("Difference: " + diff);
				if (diff > 100) {
					System.out.println("Got a Ticket: ");
					Toolkit.getDefaultToolkit().beep();
					TelegramBot bot = TelegramBotAdapter.build("1493122235:AAGESzcoJUXQu2e0cAEOOIm_0l2XdUG2UjQ");
					//bot.sendMessage("-455224835", "short message sending")
					
					File file = new File("D://Shot.jpg");
					InputFile inpFile = new InputFile("text/plain", file); 
					bot.sendPhoto("-455224835", inpFile.photo(file), "You Have a Ticket", null, null);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}


	
}