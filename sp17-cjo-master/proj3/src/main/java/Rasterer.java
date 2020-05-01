import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.
    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    QuadTree allImages;
    public Rasterer(String imgRoot) {
        double x = 37.892195547244356;
        double y = -122.2998046875;
        double a = 37.82280243352756;
        double b = -122.2119140625;
        allImages = new QuadTree(x, y, a, b, "");
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
<<<<<<< HEAD
     *         <li>Has dimensions of at least w by h, where w and h are the user viewport width
     *         and height.</li>
=======
>>>>>>> 1c0df392fa29a10a01640a01900abcf4fb511fb7
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
<<<<<<< HEAD
     *
     * REQUIRED_RASTER_REQUEST_PARAMS
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double width = params.get("w");
        double height = params.get("h");
        double lonDPP = (lrlon - ullon) / width;
        String[][] imageStrings;
        Map<String, Object> damn
                = allImages.learningSucks(allImages.getRoot(), ullat, ullon, lrlat, lrlon, lonDPP);
        if (damn == null) {
            results.put("query_success", false);
            return results;
        }
        imageStrings = (String[][]) damn.get("grid");
        ArrayList<String> imageStringsPngs = new ArrayList<>();
        for (int i = 0; i < imageStrings.length; i++) {
            for (int j = 0; j < imageStrings[0].length; j++) {
                imageStrings[i][j] = "img/" + imageStrings[i][j] + ".png";
            }
        }
        results.put("raster_ul_lon", damn.get("ullon"));
        results.put("raster_ul_lat", damn.get("ullat"));
        results.put("raster_lr_lon", damn.get("lrlon"));
        results.put("raster_lr_lat", damn.get("lrlat"));
        results.put("render_grid", imageStrings);
        results.put("depth", damn.get("depth"));
        results.put("query_success", true);
        return results;
    }

}
