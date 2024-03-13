public class EncoderDecoder {

    private static final String REF_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+-./";
    private char offsetChar = 'B'; // Default offset character

    // Setter method for offsetChar, allows the user to change it
    public void setOffsetChar(char newOffsetChar) {
        if (REF_TABLE.indexOf(newOffsetChar) == -1) {
            throw new IllegalArgumentException("Offset character must be in the reference table.");
        }
        this.offsetChar = newOffsetChar;
    }

    /**
     * Sets the offset character for the encoding and decoding operations.
     * This method allows the user to change the offset character to any valid
     * character in the reference table.
     *
     * @param newOffsetChar the new character to be used as the offset character.
     * @throws IllegalArgumentException if the new offset character is not in the
     *                                  reference table.
     */

    public String encode(String plainText) {
        // If it's empty or null, perform no operations and return it as it is.
        if (plainText == null || plainText.isEmpty())
            return "";

        // Determine the offset value using the offset character
        int offset = REF_TABLE.indexOf(this.offsetChar);

        // We utilise Stringbuilder as it allows us to change the existing string
        // without creating a new one, thus saving on memory
        StringBuilder encoded = new StringBuilder("" + this.offsetChar); // Prepend the offset character

        // We go through each character
        for (char currentChar : plainText.toCharArray()) {
            int charIndex = REF_TABLE.indexOf(currentChar);

            if (charIndex != -1) {
                // If the character is found in the reference table, calculate its encoded index
                int encodedIndex = (charIndex - offset + REF_TABLE.length()) % REF_TABLE.length();
                encoded.append(REF_TABLE.charAt(encodedIndex));
            } else {
                // If the character is not found in the reference table, append it as is
                encoded.append(currentChar);
            }
        }

        return encoded.toString();
    }

    /**
     * Decodes the provided encoded string by reversing the encoding operation.
     * The first character of the encoded text determines the offset used for
     * decoding.
     * Any character in the encoded text that is not found in the reference table is
     * mapped to itself.
     *
     * @param encodedText the text to be decoded.
     * @return the original plaintext before encoding.
     */

    public String decode(String encodedText) {
        if (encodedText == null || encodedText.isEmpty())
            return "";
        // Here we determine the offset by looking at the first letter of the encoded
        // text
        this.offsetChar = encodedText.charAt(0);
        int offset = REF_TABLE.indexOf(this.offsetChar);
        StringBuilder decoded = new StringBuilder();

        for (int i = 1; i < encodedText.length(); i++) {
            char currentChar = encodedText.charAt(i);
            int charIndex = REF_TABLE.indexOf(currentChar);

            if (charIndex != -1) {
                // Here we determine the decoded character
                int decodedIndex = (charIndex + offset) % REF_TABLE.length();
                decoded.append(REF_TABLE.charAt(decodedIndex));
            } else {
                // If it's not in the reference table, we append it as it is
                decoded.append(currentChar);
            }
        }

        return decoded.toString();
    }

    // Test the EncoderDecoder
    public static void main(String[] args) {
        EncoderDecoder ed = new EncoderDecoder();

        // This is where you can test what values to input
        String originalText = "RQPp";
        String encoded = ed.encode(originalText);
        System.out.println("Encoded: " + encoded);

        String decoded = ed.decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
