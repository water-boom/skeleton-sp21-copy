package byow.Core;
import java.util.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.TileEngine.TERenderer;

public class WorldGenerator {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 60;
    private static final int MAX_ROOMS = 30;
    private static final int MIN_ROOMS = 20;
    private static final int MAX_ROOM_SIZE = 15;
    private static final int MIN_ROOM_SIZE = 8;
    private static final int HALLWAY_WIDTH = 2;
    private static Random RANDOM;

    public static void main(String[] args) {
        long seed = Long.parseLong(args[0]);
        RANDOM = new Random(seed);

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);

        List<Room> rooms = generateRooms(world);
        connectRooms(world, rooms);

        ter.renderFrame(world);
    }

    private static void initializeWorld(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private static List<Room> generateRooms(TETile[][] world) {
        List<Room> rooms = new ArrayList<>();
        int roomCount = RANDOM.nextInt(MAX_ROOMS - MIN_ROOMS + 1) + MIN_ROOMS;
        int currentCount = 0;
        while (currentCount < roomCount) {
            int roomWidth = RANDOM.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
            int roomHeight = RANDOM.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
            int x = RANDOM.nextInt(WIDTH - roomWidth);
            int y = RANDOM.nextInt(HEIGHT - roomHeight);

            Room room = new Room(x, y, roomWidth, roomHeight);
            if (isValidRoom(world, room)) {
                createRoom(world, room);
                rooms.add(room);
                currentCount++;
            }
        }
        return rooms;
    }

    private static boolean isValidRoom(TETile[][] world, Room room) {
        for (int i = room.x; i < room.x + room.width; i++) {
            for (int j = room.y; j < room.y + room.height; j++) {
                if (world[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void createRoom(TETile[][] world, Room room) {
        for (int i = room.x; i < room.x + room.width; i++) {
            for (int j = room.y; j < room.y + room.height; j++) {
                if (i == room.x || i == room.x + room.width - 1 || j == room.y || j == room.y + room.height - 1) {
                    world[i][j] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    private static void connectRooms(TETile[][] world, List<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room room1 = rooms.get(i);
            Room room2 = rooms.get(i + 1);
            createHallway(world, room1, room2);
        }
    }

    private static void createHallway(TETile[][] world, Room room1, Room room2) {
        int x1 = room1.centerX();
        int y1 = room1.centerY();
        int x2 = room2.centerX();
        int y2 = room2.centerY();

        while (x1 != x2) {
            world[x1][y1] = Tileset.FLOOR;
            if (x1 < x2) {
                x1++;
            } else {
                x1--;
            }
        }
        while (y1 != y2) {
            world[x1][y1] = Tileset.FLOOR;
            if (y1 < y2) {
                y1++;
            } else {
                y1--;
            }
        }

        addHallwayWalls(world, room1, room2);
    }

    private static void addHallwayWalls(TETile[][] world, Room room1, Room room2) {
        int x1 = room1.centerX();
        int y1 = room1.centerY();
        int x2 = room2.centerX();
        int y2 = room2.centerY();

        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            if (world[x][y1] != Tileset.FLOOR) {
                world[x][y1] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
            if (y1 + 1 < HEIGHT && world[x][y1 + 1] != Tileset.FLOOR) {
                world[x][y1 + 1] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
            if (y1 - 1 >= 0 && world[x][y1 - 1] != Tileset.FLOOR) {
                world[x][y1 - 1] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
        }

        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            if (world[x2][y] != Tileset.FLOOR) {
                world[x2][y] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
            if (x2 + 1 < WIDTH && world[x2 + 1][y] != Tileset.FLOOR) {
                world[x2 + 1][y] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
            if (x2 - 1 >= 0 && world[x2 - 1][y] != Tileset.FLOOR) {
                world[x2 - 1][y] = TETile.colorVariant(Tileset.WALL, 32, 32, 32, RANDOM);
            }
        }
    }


    private static class Room {
        int x, y, width, height;

        Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        public int centerX() {
            return x + width / 2;
        }
        public int centerY(){
            return y + height / 2;
        }

        public boolean isEdge(int x, int y) {
            return x == this.x || x == this.x + width -1 || y == this.y || y == this.y + height -1;
        }
    }

}


































