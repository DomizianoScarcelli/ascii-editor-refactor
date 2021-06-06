package model;



    /**
     * A functional interface whose method transform a character.
     */
    public interface TileTransformer {
        /**
         * Transforms the character in position (x,y) with the info (font and colors) from the data object.
         * @param x the x position of the character.
         * @param y the y position of the character.
         * @param data the data that will be overwritten to the one of the character.
         */
        public void transformTile(int x, int y, AsciiCharacterData data);
    }

