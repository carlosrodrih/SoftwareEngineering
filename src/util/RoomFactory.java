package util;

import world.*;

public class RoomFactory {
	public static Room createRoom(String roomName) {
		Room room = new Room(roomName);
		World.getInstance().addToWorld(room);
		
		return room;
	}
	
	public static Room createRoom(String roomName, String description) {
		Room room = new Room(roomName);
		room.setDescription(description);
		World.getInstance().addToWorld(room);
		
		return room;
	}
}
