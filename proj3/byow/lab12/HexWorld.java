package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 50;
    private static final int HEX_SIZE = 3;
    private static final int SEED = 114514;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        beginWorld(world);
        generateHexPattern(world, WIDTH / 2, HEIGHT / 2, HEX_SIZE);
        ter.renderFrame(world);
    }

    private static void beginWorld(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private static void addHexagon(TETile[][] world, int x, int y, int size, TETile tile) {
        for (int i = 0; i < size; i++) {
            int rowWidth = size + 2 * i;
            int xPos = x - i;
            int yPos = y + i;
            addHexRow(world, xPos, yPos, rowWidth, tile);
        }

        for (int i = 0; i < size; i++) {
            int rowWidth = size + 2 * (size - 1 - i);
            int xPos = x - (size - 1 - i);
            int yPos = y + size + i;
            addHexRow(world, xPos, yPos, rowWidth, tile);
        }
    }

    private static void addHexRow(TETile[][] world, int x, int y, int width, TETile tile) {
        for (int i = 0; i < width; i++) {
            int currentX = x + i;
            if (currentX >= 0 && currentX < world.length && y >= 0 && y < world[0].length) {
                world[currentX][y] = TETile.colorVariant(tile, 32, 32, 32, new Random());
            }
        }
    }

    private static TETile getRandomTile() {
        TETile[] tiles = {Tileset.FLOWER, Tileset.GRASS, Tileset.SAND, Tileset.WALL, Tileset.WATER};
        return tiles[RANDOM.nextInt(tiles.length)];
    }

    private static void generateHexPattern(TETile[][] world, int centerX, int centerY, int size) {
        addHexagon(world, centerX, centerY, size, getRandomTile());
        // First layer
        int[][] firstLayerOffsets = {
                {2 * size - 1, -size}, {2 * size - 1, size},
                {0, 2 * size}, {-2 * size + 1, size},
                {-2 * size + 1, -size}, {0, -2 * size}
        };
        for (int[] offset : firstLayerOffsets) {
            addHexagon(world, centerX + offset[0], centerY + offset[1], size, getRandomTile());
        }
        // Second layer
        int[][] secondLayerOffsets = {
                {4 * size - 2, -2 * size}, {4 * size - 2, 0}, {4 * size - 2, 2 * size},
                {2 * size - 1, 3 * size}, {0, 4 * size}, {-2 * size + 1, 3 * size},
                {-4 * size + 2, 2 * size}, {-4 * size + 2, 0}, {-4 * size + 2, -2 * size},
                {-2 * size + 1, -3 * size}, {0, -4 * size}, {2 * size - 1, -3 * size}
        };
        for (int[] offset : secondLayerOffsets) {
            addHexagon(world, centerX + offset[0], centerY + offset[1], size, getRandomTile());
        }
    }
}
