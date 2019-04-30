package general;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * ColorsParser class.
 */
public class ColorsParser {

    /**
     * Parse color definition by defaults or rgb.
     *
     * @param str the color definition string.
     * @return color by default or rgb.
     */
    public Color colorFromString(String str) {
        // parse rgb
        if (str.startsWith("color(RGB(") && str.endsWith("))")) {
            String arg = Utils.extract(str, "color(RGB(", "))");
            String[] params = arg.split(",");
            int r = Integer.parseInt(params[0].trim());
            int g = Integer.parseInt(params[1].trim());
            int b = Integer.parseInt(params[2].trim());
            return new Color(r, g, b);

            // parse regular colors
        } else if ((str.startsWith("color(")) && (str.endsWith(")"))) {
            String color = Utils.extract(str, "color(", ")");
            try {
                Field field = Color.class.getField(color);
                return (Color) field.get(null);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("unsupported color name");
            }
        }
        return null;
    }
}