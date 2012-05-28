package tiraht.util;

/**
 *
 * @author jgsavola
 */
public class GeneralUnaryEncoder {
    private int start;
    private int step;
    private int stop;

    public GeneralUnaryEncoder(int start, int step, int stop) {
        this.start = start;
        this.step = step;
        this.stop = stop;
    }
    
    public String encode(int num) {
        int a;
        int offset = 0;
        
        for (int n = 0; ; n++) {
            a = start + n*step;
            //System.out.println("a: " + a + ", offset: " + offset);
            if (num < offset + (1 << a)) {
                return unary(a, n + 1) + "|" + binary(a, num - offset);
            }
            offset += 1 << a;
        }

//        return s;
//        System.out.printf("num: %06d, a: %2d %28s\n", num, a, unary((a-start) / step + 1) + "|" + binary(a, num));
////        System.out.println("num: " + num + ", a: " + a + " " + unary((a-start) / step + 1) + "|" + binary(a, num));
//        
//        return "";
    }

    private String unary(int bits, int num) {
        String s = "";
        
        for (int n = 0; n < num - 1; n++)
            s += "1";

        if (bits != stop)
            s += "0";
        
        return s;
    }
    
    private String binary(int bits, int num) {
        String s = "";
        
        while (bits-- > 0) {
            if ((num & 0x1) != 0)
                s = "1" + s;
            else
                s = "0" + s;

            num = num >> 1;
        }
        
        return s;
    }
}
