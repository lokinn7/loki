//package com.kuaiyou.lucky.api;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Calendar;
//
//import javax.imageio.ImageIO;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.kuaiyou.lucky.common.ResponseMessage;
//import com.kuaiyou.lucky.config.UploadFilePropertyConfig;
//import com.kuaiyou.lucky.enums.FileTypeEnum;
//import com.kuaiyou.lucky.res.FileEntity;
//
///**
// * @author liuyijie
// * @date 2017/6/7.
// */
//@RestController
//@RequestMapping("/lucky/upload")
//public class FileUploadApi {
//
//	protected final Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	UploadFilePropertyConfig uploadFilePropertyConfig;
//
//	@PostMapping("/file/{type}")
//	public ResponseMessage<Object> handleFileUpload(
//			@RequestPart(name = "file", required = false) MultipartFile multipartFile, @PathVariable String type) {
//		if (multipartFile == null) {
//			return ResponseMessage.error("文件数据为null");
//		}
//
//		FileTypeEnum typeByName = FileTypeEnum.getTypeByName(type);
//		if (FileTypeEnum.getTypeByName(type) == null) {
//			return ResponseMessage.error("无文件类型");
//		}
//
//		String imageName = this.getImgName(multipartFile, type);
//		if (StringUtils.isBlank(imageName)) {
//			return ResponseMessage.error("上传失败");
//		}
//
//		String typePath = typeByName.name().toLowerCase();
//		String relPath = getRelPath(typePath);
//		String dir = getStoreLocation() + getStoreDir() + relPath;
//		Path directoriesPath = Paths.get(dir);
//
//		try {
//			Files.createDirectories(directoriesPath);
//			Files.copy(multipartFile.getInputStream(), Paths.get(dir, imageName), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return ResponseMessage
//				.ok(new FileEntity(uploadFilePropertyConfig.getUri(), getStoreDir() + relPath + imageName, imageName));
//	}
//
//	private String getStoreLocation() {
//		return uploadFilePropertyConfig.getDirectory();
//	}
//
//	private String getStoreDir() {
//		return uploadFilePropertyConfig.getFolder();
//	}
//
//	private String getRelPath(String type) {
//		StringBuilder path = new StringBuilder();
//		Calendar calendar = Calendar.getInstance();
//		path.append("/");
//		path.append(type);
//		path.append("/");
//		path.append(calendar.get(Calendar.YEAR));
//		path.append("/");
//		path.append(calendar.get(Calendar.MONTH) + 1);
//		path.append("/");
//		return path.toString();
//	}
//
//	private String getImgType(String imgName) {
//		return imgName.substring(imgName.lastIndexOf(".") + 1).trim().toLowerCase();
//	}
//
//	String getImgName(MultipartFile multipartFile, String type) {
//		String orgImageName = multipartFile.getOriginalFilename().trim();
//		if (orgImageName.indexOf(".") == -1) {
//			return "";
//		}
//		String imgType = this.getImgType(orgImageName);
//		String selfImgName = String.valueOf(System.currentTimeMillis());
//		// 获取长宽高加入命名
//		// if (FileTypeEnum.TASKMATERIAL.name().equalsIgnoreCase(type)) {
//		// String widthAndHeight = this.getImgWidthAndHeight(multipartFile);
//		// if (StringUtils.isBlank(widthAndHeight)) {
//		// return "";
//		// }
//		// selfImgName = String.format("%s-%s", selfImgName, widthAndHeight);
//		// }
//		return String.format("%s.%s", selfImgName, imgType);
//	}
//
//	/**
//	 * 获取图片高度和宽度，单位px
//	 * 
//	 * @param multipartFile
//	 * @return
//	 */
//	String getImgWidthAndHeight(MultipartFile multipartFile) {
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(multipartFile.getInputStream());
//		} catch (IOException e) {
//			logger.info("upload ImageIO:{}", e);
//		}
//		String whStr = "";
//		if (image == null) {
//			return whStr;
//		}
//		whStr = String.format("%s_%s", image.getWidth(), image.getHeight());
//		return whStr;
//	}
//}
