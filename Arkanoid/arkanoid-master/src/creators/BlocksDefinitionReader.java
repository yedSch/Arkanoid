package creators;

import general.Utils;
import general.ColorsParser;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * BlocksDefinitonReader class.
 */
public class BlocksDefinitionReader {

    /**
     * Gives a factory of blocks according to symbols from a definitions blocks file.
     *
     * @param reader the reader.
     * @return a factory of blocks according to symbols.
     * @throws IOException throw if needed.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) throws IOException {
        ColorsParser colorsParser = new ColorsParser();

        // maps for spacers, block creators and default definitions
        Map<String, Integer> spacerWidths = new HashMap<>();
        Map<String, BlockCreator> blockCreators = new HashMap<>();
        Map<String, String> defaults = new HashMap<>();

        // blocks properties
        int width = 0;
        int height = 0;
        int hitPoints = 0;
        Color strokeColor = null;
        Map<Integer, Color> fillColors = new HashMap<>();
        Map<Integer, BufferedImage> fillImages = new HashMap<>();

        // line reader
        LineNumberReader lineReader = new LineNumberReader(reader);
        String line;

        // advance to next line while not null
        while ((line = lineReader.readLine()) != null) {

            // remove blanks
            line = line.trim();

            // if not note or empty
            if ((!"".equals(line)) && (!line.startsWith("#"))) {

                // case default
                if (line.startsWith("default")) {
                    String propertiesString = line.substring("default".length()).trim();
                    defaults = parseProperties(propertiesString);

                    // case blocks definition
                } else if (line.startsWith("bdef")) {

                    // remove blanks ang get the prperties string
                    String propertiesStr = line.substring("bdef".length()).trim();
                    Map<String, String> blockMap = parseProperties(propertiesStr);
                    // properties
                    Map<String, String> properties = new HashMap(defaults);
                    properties.putAll(blockMap);

                    // case no stroke color
                    if (!properties.containsKey("stroke")) {
                        strokeColor = null;
                    }

                    // for each key
                    for (String key : properties.keySet()) {
                        String value = properties.get(key);

                        // case width
                        if ("width".equals(key)) {
                            width = Integer.parseInt(value);

                            // case height
                        } else if ("height".equals(key)) {
                            height = Integer.parseInt(value);

                            // case hit points
                        } else if ("hit_points".equals(key)) {
                            hitPoints = Integer.parseInt(value);

                            // case stroke
                        } else if ("stroke".equals(key)) {
                            strokeColor = new ColorsParser().colorFromString(value);

                            // case fill or fill-k
                        } else {
                            if (key.startsWith("fill")) {
                                // index of -1
                                int dividerIndex = key.indexOf("-");

                                // check if color or image
                                if (value.startsWith("color")) {
                                    // case color
                                    if (dividerIndex != -1) {
                                        // case there is - (fill-k) map it to k
                                        fillColors.put(Integer.parseInt(key.substring(dividerIndex + 1)),
                                                colorsParser.colorFromString(value));
                                    } else {
                                        // case no - (fill) map it to 1
                                        fillColors.put(1, colorsParser.colorFromString(value));
                                    }
                                } else if (value.startsWith("image")) {
                                    // case image
                                    String arg = Utils.extract(value, "image(", ")");
                                    InputStream inputStream = null;
                                    try {
                                        inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(arg);
                                        BufferedImage image = ImageIO.read(inputStream);
                                        // case there is - (fill-k) map it to k
                                        if (dividerIndex != -1) {
                                            fillImages.put(Integer.parseInt(
                                                    key.substring(dividerIndex + 1)), image);
                                            // case no - (fill) map it to 1
                                        } else {
                                            fillImages.put(1, image);
                                        }
                                    } catch (IOException e) {
                                        throw new RuntimeException("failed to load image");

                                    } finally {
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e) {
                                                throw new RuntimeException("failed to load image");
                                            }
                                        }
                                    }
                                } else {
                                    // case nor color or image
                                    throw new RuntimeException("fill doesn't match colors or resources format");
                                }
                            }
                        }
                    }
                    // add block creator according to properties
                    blockCreators.put(properties.remove("symbol"), new ColorsImagesBlockCreator(width, height,
                            hitPoints, strokeColor, fillColors, fillImages));

                    // case spacers definition
                } else if (line.startsWith("sdef")) {
                    String propertiesString = line.substring("sdef".length()).trim();
                    Map<String, String> spacerMap = parseProperties(propertiesString);

                    spacerWidths.put(spacerMap.remove("symbol"),
                            Integer.parseInt(spacerMap.get("width")));

                    // case not default, blocks definition or spacers definition
                } else {
                    throw new RuntimeException("line doesn't match format");
                }
            }
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Parses properties string into a map of properties.
     *
     * @param propertiesStr the string of the properties.
     * @return the properties as map.
     */
    private static Map<String, String> parseProperties(String propertiesStr) {
        Map<String, String> properties = new HashMap();
        // parse key:val pairs from the whole properties string
        String[] keysVals = propertiesStr.split(" ");
        // for each key:val put key and val in properties map
        for (String keyVal : keysVals) {
            String[] keyValArr = keyVal.split(":");
            if (keyValArr.length != 2) {
                throw new RuntimeException("properties doesn't match format");
            }
            properties.put(keyValArr[0], keyValArr[1]);
        }
        return properties;
    }
}
