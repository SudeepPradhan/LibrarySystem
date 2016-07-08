package utilities;

import javafx.scene.image.Image;

public class ImageUtil {

    private static final String PATH_TO_BANNER = "/resources/images/banner.jpg";

    public static Image getBanner() {
        return new Image(ImageUtil.class.getClass().getResourceAsStream(PATH_TO_BANNER));
    }
}
