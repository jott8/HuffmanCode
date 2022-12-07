import java.util.HashMap;

public class HuffCompressor {

    private String uncompressedStr;
    private String compressedStr;
    private HashMap<Character, String> codes;
    public HuffTree tree;

    public HuffCompressor(String uncompressedStr) {
        this.uncompressedStr = plainTextConversion(uncompressedStr);
        this.tree = new HuffTree(this.countEachChar(this.uncompressedStr));
        this.codes = tree.getLetterCodes();
        this.compressedStr = compression();
    }

    public String plainTextConversion(String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            // Remove spaces, commas, slashes, semicolons and dots
            if ((int) s.charAt(i) == 32 || (int) s.charAt(i) == 46 || (int) s.charAt(i) == 44
                    || (int) s.charAt(i) == 59 || (int) s.charAt(i) == 47) {
                continue;
            }
            result += s.charAt(i);
        }

        return result.toUpperCase();
    }

    // Counts amount of char c in given string str
    private int countChar(char c, String str) {
        int counter = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                counter++;
            }
        }
        return counter;
    }

    // Returns HashMap of each char in string as key and its amount as value
    private HashMap<Character, Integer> countEachChar(String str) {
        HashMap<Character, Integer> entries = new HashMap<>();
        int i = 0;

        while (i < str.length()) {
            entries.put(str.charAt(i), countChar(str.charAt(i), str));
            i++;
        }

        return entries;
    }

    private String compression() {
        String result = "";

        for (int i = 0; i < uncompressedStr.length(); i++) {
            result += codes.get(uncompressedStr.charAt(i));
        }

        return result;
    }

    public void printLengthComparison() {
        System.out.println("Uncompressed length: " + this.getUncompressedString().length() + " | Compressed length: "
                + this.getCompressedStr().length());
    }

    public void printData() {
        System.out.println("Tree:");
        tree.printTree();
        System.out.println();
        System.out.println("Amount nodes: " + tree.getAmountNodes());
        System.out.println("Depth: " + tree.getDepth());
        System.out.println("Codes: " + tree.getLetterCodes());
        System.out.println();
        System.out.println("Uncompressed string: " + getUncompressedString());
        System.out.println();
        System.out.println("Compressed string: " + getCompressedStr());
        System.out.println();
        printLengthComparison();
    }

    public String getUncompressedString() {
        return this.uncompressedStr;
    }

    public String getCompressedStr() {
        return this.compressedStr;
    }
}