package com.fotografocomvc;

import com.fotografocomvc.domain.model.*;
import com.fotografocomvc.domain.model.Image;
import com.fotografocomvc.domain.repository.*;
import com.fotografocomvc.domain.service.AccessTokenServiceImpl;
import com.fotografocomvc.domain.service.PhotographerService;
import com.fotografocomvc.domain.service.RefreshTokenServiceImpl;
import com.fotografocomvc.domain.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FotografocomvcApplicationTests {

	@Autowired
	private PhotographerRepository photographerRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private PhotographerService photographerService;

	@Autowired
	private BaseUserRepository baseUserRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private AccessTokenServiceImpl accessTokenService;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
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
	void clearTokens(){
		BaseUser baseUser1 = BaseUser.builder()
				.id(1L)
				.username("tester1")
				.password("123")
				.build();

		baseUserRepository.save(baseUser1);

		BaseUser baseUser2 = BaseUser.builder()
				.id(2L)
				.username("tester2")
				.password("321")
				.build();

		baseUserRepository.save(baseUser2);

		RefreshToken refreshToken1 = RefreshToken.builder()
				.id(1L)
				.tokenString("refreshTokenString1")
				.baseUser(baseUser1)
				.build();

		refreshTokenRepository.save(refreshToken1);

		RefreshToken refreshToken2 = RefreshToken.builder()
				.id(2L)
				.tokenString("refreshTokenString2")
				.baseUser(baseUser1)
				.build();
		refreshTokenRepository.save(refreshToken2);

		RefreshToken refreshToken3 = RefreshToken.builder()
				.id(3L)
				.tokenString("refreshTokenString3")
				.baseUser(baseUser2)
				.build();
		refreshTokenRepository.save(refreshToken3);

		RefreshToken refreshToken4 = RefreshToken.builder()
				.id(4L)
				.tokenString("refreshTokenString4")
				.build();
		refreshTokenRepository.save(refreshToken4);

		AccessToken accessToken1 = AccessToken.builder()
				.id(1L)
				.tokenString("accesTokenString1")
				.baseUser(baseUser1)
				.build();
		accessTokenRepository.save(accessToken1);

		AccessToken accessToken2 = AccessToken.builder()
				.id(2L)
				.tokenString("accesTokenString2")
				.baseUser(baseUser2)
				.build();
		accessTokenRepository.save(accessToken2);

		accessTokenService.deleteAllByUserId(baseUser1.getId());
		refreshTokenService.deleteAllByUserId(baseUser1.getId());

		BaseUser baseUserX = BaseUser.builder()
					.id(5L)
					.username("tester5")
					.password("123")
					.build();
		baseUserRepository.save(baseUserX);

		accessTokenService.deleteAllByUserId(baseUserX.getId());
		refreshTokenService.deleteAllByUserId(baseUserX.getId());

		System.out.println("tsete");


//		List<RefreshToken> refreshTokenList = refreshTokenRepository.findAllByBaseUserId(baseUser1.getId());
//		refreshTokenRepository.deleteAll(refreshTokenList);


	}

	@Test
	void locationMappingToPhotographer(){
		Photographer photographer = photographerRepository.findById(1L).get();
		Location location = Location.builder()
						.id(1L)
								.locationCity("Reio de janei")
										.locationState("RJ")
												.build();

		location = locationRepository.save(location);

		photographer.setLocation(location);

		photographerService.update(photographer);

		System.out.println(photographer.getLocation());

	}

}
