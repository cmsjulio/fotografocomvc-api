package com.fotografocomvc;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Image;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.ImageRepository;
import com.fotografocomvc.domain.repository.PhotographerRepository;
import com.fotografocomvc.domain.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
class FotografocomvcApplicationTests {

	@Autowired
	private PhotographerRepository photographerRepository;

	@Autowired
	private BaseUserRepository baseUserRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Test
	void contextLoads() throws IOException {

		File resultImageFile = new File("/tmp/fodase");

		File origFile = new File("/tmp/source.png");
		ImageIcon icon = new ImageIcon(origFile.getPath());

		// create BufferedImage object of same width and height as of original image
		BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);

		// create graphics object and add original image to it
		Graphics graphics = bufferedImage.getGraphics();
		graphics.drawImage(icon.getImage(), 0, 0, null);

		File watermarkImageFile = new File("/tmp/watermark.png");
		ImageIcon icon2 = new ImageIcon(watermarkImageFile.getPath());

		// create BufferedImage object of same width and height as of original image
		BufferedImage bufferedImage2 = new BufferedImage(icon2.getIconWidth(),
				icon2.getIconHeight(), BufferedImage.TYPE_INT_RGB);

		// create graphics object and add original image to it
		Graphics graphics2 = bufferedImage2.getGraphics();
		graphics2.drawImage(icon2.getImage(), 0, 0, null);

		// initializes necessary graphic properties
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		g2d.setComposite(alphaChannel);

		// calculates the coordinate where the image is painted
		int topLeftX = (bufferedImage.getWidth() - bufferedImage2.getWidth()) / 2;
		int topLeftY = (bufferedImage.getHeight() - bufferedImage2.getHeight()) / 2;

		// paints the image watermark
		g2d.drawImage(bufferedImage2, topLeftX, topLeftY, null);

		ImageIO.write(bufferedImage, "png", resultImageFile);
		g2d.dispose();

		Files.readAllBytes(resultImageFile.toPath());


		// FUNCIONA ABAIXO:
//		File origFile = new File("/tmp/teste.jpeg");
//		ImageIcon icon = new ImageIcon(origFile.getPath());
//
//		// create BufferedImage object of same width and height as of original image
//		BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
//				icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
//
//		// create graphics object and add original image to it
//		Graphics graphics = bufferedImage.getGraphics();
//		graphics.drawImage(icon.getImage(), 0, 0, null);
//
//		// set font for the watermark text
//		graphics.setFont(new Font("Arial", Font.BOLD, 30));
//
//		//unicode characters for (c) is \u00a9
//		String watermark = "\u00a9 JavaXp.com";
//
//		// add the watermark text
//		graphics.drawString(watermark, 0, icon.getIconHeight() / 2);
//		graphics.dispose();
//
//		File newFile = new File("/tmp/WatermarkedImage.jpg");
//		try {
//			ImageIO.write(bufferedImage, "jpg", newFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(newFile.getPath() + " created successfully!");


//		BaseUser baseUser = BaseUser.builder()
//				.id(1L)
//				.username("email")
//				.password("password")
//				.build();
//
//		baseUserRepository.save(baseUser);
//
//		Photographer photographer = Photographer.builder()
//				.name("julio")
//				.id(1L)
//				.baseUser(baseUser)
//				.build();
//
//		photographer = photographerRepository.save(photographer);
//		System.out.println(photographer);
//
//		Optional<Photographer> photographer2 =		photographerRepository.findById(1L);
//
//
//		Optional<Photographer> photographer3 = photographerRepository.findById(3L);
//
//		System.out.println(photographer2);
//		System.out.println(photographer3);

	}

	@Test
	void writeFile(){

	}

}
